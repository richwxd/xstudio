package com.xstudio.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;
import java.util.UUID;

/**
 * 字符串工具类
 *
 * @author xiaobiao
 */
public class RandomUtil {
    /**
     * 定义验证码字符.去除了0、O、I、L等容易混淆的字母
     */
    protected static final char[] ALPHA = {'2', '3', '4', '5', '6', '7', '8', '9',
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'J', 'K', 'M', 'N', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'j', 'k', 'm', 'n', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};

    private static final Logger logger = LoggerFactory.getLogger(RandomUtil.class);
    /**
     * 所有字符串
     */
    private static final String ALLCHAR = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    /**
     * 字母字符串
     */
    private static final String LETTERCHAR = "abcdefghijkllmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    /**
     * 数字字符串
     */
    private static final String NUMBERCHAR = "0123456789";

    private static Random random;

    static {
        try {
            if (System.getProperties().getProperty("os.name").contains("Windows")) {
                random = SecureRandom.getInstanceStrong();
            } else {
                random = SecureRandom.getInstance("NativePRNGNonBlocking");
            }
        } catch (NoSuchAlgorithmException e) {
            logger.error("", e);
        }
    }

    private RandomUtil() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * 返回ALPHA中的随机字符
     *
     * @return 随机字符
     */
    public static char alpha() {
        return ALPHA[num(ALPHA.length)];
    }

    /**
     * 产生0-num的随机数,不包括num
     *
     * @param num 最大值
     * @return 随机数
     */
    public static int num(int num) {
        return random.nextInt(num);
    }

    /**
     * 返回ALPHA中第0位到第num位的随机字符
     *
     * @param num 到第几位结束
     * @return 随机字符
     */
    public static char alpha(int num) {
        return ALPHA[num(num)];
    }

    /**
     * 返回ALPHA中第min位到第max位的随机字符
     *
     * @param min 从第几位开始
     * @param max 到第几位结束
     * @return 随机字符
     */
    public static char alpha(int min, int max) {
        return ALPHA[num(min, max)];
    }

    /**
     * 产生两个数之间的随机数
     *
     * @param min 最小值
     * @param max 最大值
     * @return 随机数
     */
    public static int num(int min, int max) {
        return min + random.nextInt(max - min);
    }

    public static String uuid(int length) {
        UUID uuid = UUID.randomUUID();
        return uuid.toString().replace("-", "").substring(0, length);
    }

    /**
     * 返回一个定长的随机纯大写字母字符串(只包含大小写字母)
     *
     * @param length 随机字符串长度
     * @return 随机字符串
     */
    public static String lower(int length) {
        return mix(length).toLowerCase();
    }

    /**
     * 返回一个定长的随机纯字母字符串(只包含大小写字母)
     *
     * @param length 随机字符串长度
     * @return 随机字符串
     */
    public static String mix(int length) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append(ALLCHAR.charAt(random.nextInt(LETTERCHAR.length())));
        }
        return sb.toString();
    }

    /**
     * 获取不带<code>-</code>的UUID
     *
     * @return {@link String}
     */
    public static String nonLineUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    /**
     * 每次生成的len位数都不相同
     *
     * @param len   长度
     * @param param 参数
     * @return 定长的数字
     */
    public static int notSimple(int[] param, int len) {
        for (int i = param.length; i > 1; i--) {
            int index = random.nextInt(i);
            int tmp = param[index];
            param[index] = param[i - 1];
            param[i - 1] = tmp;
        }
        int result = 0;
        for (int i = 0; i < len; i++) {
            result = result * 10 + param[i];
        }
        return result;
    }

    /**
     * 获取指定长度的纯数字码
     *
     * @param length 长度
     * @return 数字码
     */
    public static String number(int length) {
        StringBuilder sb = new StringBuilder();
        random.setSeed(System.currentTimeMillis());
        StringBuilder buffer = new StringBuilder(NUMBERCHAR);
        int range = buffer.length();
        for (int i = 0; i < length; ++i) {
            sb.append(buffer.charAt(random.nextInt(range)));
        }
        return sb.toString();
    }

    /**
     * 返回一个定长的随机字符串(只包含大小写字母、数字)
     *
     * @param length 随机字符串长度
     * @return 随机字符串
     */
    public static String random(int length) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append(ALLCHAR.charAt(random.nextInt(ALLCHAR.length())));
        }
        return sb.toString();
    }

    /**
     * 生成时间字符串
     *
     * @param length 附带随机字（字符+数字）符串长度
     * @return String
     */
    public static String timeStringPlus(int length) {
        //当前时间戳
        String timeString = String.valueOf(System.currentTimeMillis());
        //加入?位随机数
        if (length > 0) {
            timeString = timeString.concat(mix(length));
        }
        return timeString;
    }

    /**
     * 生成一个定长的纯0字符串
     *
     * @param length 字符串长度
     * @return 纯0字符串
     */
    public static String zero(int length) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append('0');
        }
        return sb.toString();
    }

    /**
     * 返回一个定长的随机纯大写字母字符串(只包含大写字母)
     *
     * @param length 随机字符串长度
     * @return 随机字符串
     */
    public static String upper(int length) {
        return mix(length).toUpperCase();
    }
}
