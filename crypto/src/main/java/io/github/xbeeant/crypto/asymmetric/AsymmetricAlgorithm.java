package io.github.xbeeant.crypto.asymmetric;

/**
 * 非对称加密算法类型
 *
 * @author xiaobiao
 * @version 2019/5/16
 */
public enum AsymmetricAlgorithm {
    /**
     * RSA算法
     */
    RSA("RSA");

    private String value;

    /**
     * 构造
     *
     * @param value 算法字符表示，区分大小写
     */
    private AsymmetricAlgorithm(String value) {
        this.value = value;
    }

    /**
     * 获取算法字符串表示，区分大小写
     *
     * @return 算法字符串表示
     */
    public String getValue() {
        return this.value;
    }
}
