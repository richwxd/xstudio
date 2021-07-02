package io.github.xbeeant.http;


import io.github.xbeeant.core.JsonHelper;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.http.client.utils.DateUtils;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author xiaobiao
 * @version 2020/3/1
 */
class RequestsTest {
    private static final Logger logger = LoggerFactory.getLogger(RequestsTest.class);

    @Test
    void test() throws IOException, InterruptedException {
        // Construct BufferedReader from FileReader
        BufferedReader br = new BufferedReader(new FileReader(new File("D:\\Documents\\Desktop\\b.csv")));
        String line;
        Map<String, String> headers = new HashMap<>();
        headers.put("Cookie", "SESSION=MTEzY2FhNDAtOTVkYi00OTVhLWEyZWEtMzgzYTc1YWIzMWY5");
        while ((line = br.readLine()) != null) {
            if (line.startsWith("L")) {
                String vin = line;
                Map<String, Object> params = new HashMap<>();
                params.put("vin", vin);
                ClientResponse clientResponse = Requests.postForm("https://iov.changan.com.cn/mt/admin/api/maintain/history/recalc", params, headers);
                Maintain maintain = JsonHelper.toObject(clientResponse.getContent(), Maintain.class);
                if (Boolean.TRUE.equals(maintain.getSuccess()) && maintain.getData().getDayLeft() > 60) {
                    logger.info("处理 {} {}", vin, clientResponse.getContent());
                } else {
                    logger.debug("正常 {} {}", vin, clientResponse.getContent());
                }
            }
        }
        br.close();
    }

    @Test
    void testClient() throws InterruptedException {
        // URL列表数组
        String[] urisToGet = {
                "https://restapi.amap.com/v3/ip?key=0f0435e9f4b657862cc5036693c041df&ip=114.247.50.2",
                "https://restapi.amap.com/v3/ip?key=0f0435e9f4b657862cc5036693c041df&ip=114.247.50.2"};

        long start = System.currentTimeMillis();
        try {
            int pagecount = urisToGet.length;
            ExecutorService executors = Executors.newFixedThreadPool(pagecount);
            CountDownLatch countDownLatch = new CountDownLatch(pagecount);
            for (int i = 0; i < pagecount; i++) {
                // 启动线程抓取
                executors.execute(new GetRunnable(urisToGet[i], countDownLatch));
            }

            countDownLatch.await();
            executors.shutdown();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            System.out.println("线程" + Thread.currentThread().getName() + ","
                    + System.currentTimeMillis() + ", 所有线程已完成，开始进入下一步！");
        }

        long end = System.currentTimeMillis();
        System.out.println("consume -> " + (end - start));
        Thread.sleep(50000);
    }

    @Test
    void testControl() throws InterruptedException {

        String controlUrl = "http://preprod.changan.com.cn/app2/api/v2/control/execute";
        String securityUrl = "http://preprod.changan.com.cn/app2/api/v2/security/key?token=XXeCvEX2OdtBvWjT7zyiIPjfbM2esjJM";
        Map<String, String> params = new HashMap<>();
        params.put("token", "XXeCvEX2OdtBvWjT7zyiIPjfbM2esjJM");
        params.put("pin", "");
        params.put("type", "CarForward");
        params.put("pinVerifyCode","47270cc862734dc686b3b95b96508087");
        params.put("carId", "1356203722980708352");
        Date start;
        Date end;
        while (true) {
            start = new Date();
            ClientResponse clientResponse = Requests.get(securityUrl);
            clientResponse = Requests.postForm(controlUrl, params);
            end = new Date();
            String startFormat = DateFormatUtils.format(start, "HH:mm:ss.SSS");
            String endFormat = DateFormatUtils.format(end, "HH:mm:ss.SSS");

            long from2 = start.getTime();
            long to2 = end.getTime();
            int mil = (int) (to2 - from2);
            clientResponse.getOrigin().getHeaders("TRACE_ID");
            logger.info("发起指令 {} 返回时间 {} 时间差 {} {}", startFormat, endFormat, mil, clientResponse.getOrigin().getHeaders("TRACE_ID")[0]);
            Thread.currentThread().sleep(1000);
        }
    }

    static class GetRunnable implements Runnable {
        private CountDownLatch countDownLatch;
        private String url;

        public GetRunnable(String url, CountDownLatch countDownLatch) {
            this.url = url;
            this.countDownLatch = countDownLatch;
        }

        @Override
        public void run() {
            try {
                System.out.println(Requests.get(url));
            } finally {
                countDownLatch.countDown();
            }
        }
    }
}