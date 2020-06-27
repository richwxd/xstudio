package com.xstudio.http;


import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author xiaobiao
 * @version 2020/3/1
 */
public class RequestUtilTest {

    @Test
    public void testClient() throws InterruptedException {
        // URL列表数组
        String[] urisToGet = {
                "https://restapi.amap.com/v3/ip?key=0f0435e9f4b657862cc5036693c041df&ip=114.247.50.2",
                "https://restapi.amap.com/v3/ip?key=0f0435e9f4b657862cc5036693c041df&ip=114.247.50.2",
                "https://restapi.amap.com/v3/ip?key=0f0435e9f4b657862cc5036693c041df&ip=114.247.50.2",
                "https://restapi.amap.com/v3/ip?key=0f0435e9f4b657862cc5036693c041df&ip=114.247.50.2",

                "https://restapi.amap.com/v3/ip?key=0f0435e9f4b657862cc5036693c041df&ip=114.247.50.2",
                "https://restapi.amap.com/v3/ip?key=0f0435e9f4b657862cc5036693c041df&ip=114.247.50.2",
                "https://restapi.amap.com/v3/ip?key=0f0435e9f4b657862cc5036693c041df&ip=114.247.50.2",
                "https://restapi.amap.com/v3/ip?key=0f0435e9f4b657862cc5036693c041df&ip=114.247.50.2",

                "https://restapi.amap.com/v3/ip?key=0f0435e9f4b657862cc5036693c041df&ip=114.247.50.2",
                "https://restapi.amap.com/v3/ip?key=0f0435e9f4b657862cc5036693c041df&ip=114.247.50.2",
                "https://restapi.amap.com/v3/ip?key=0f0435e9f4b657862cc5036693c041df&ip=114.247.50.2",
                "https://restapi.amap.com/v3/ip?key=0f0435e9f4b657862cc5036693c041df&ip=114.247.50.2",

                "https://restapi.amap.com/v3/ip?key=0f0435e9f4b657862cc5036693c041df&ip=114.247.50.2",
                "https://restapi.amap.com/v3/ip?key=0f0435e9f4b657862cc5036693c041df&ip=114.247.50.2",
                "https://restapi.amap.com/v3/ip?key=0f0435e9f4b657862cc5036693c041df&ip=114.247.50.2",
                "https://restapi.amap.com/v3/ip?key=0f0435e9f4b657862cc5036693c041df&ip=114.247.50.2",

                "https://restapi.amap.com/v3/ip?key=0f0435e9f4b657862cc5036693c041df&ip=114.247.50.2",
                "https://restapi.amap.com/v3/ip?key=0f0435e9f4b657862cc5036693c041df&ip=114.247.50.2",
                "https://restapi.amap.com/v3/ip?key=0f0435e9f4b657862cc5036693c041df&ip=114.247.50.2",
                "https://restapi.amap.com/v3/ip?key=0f0435e9f4b657862cc5036693c041df&ip=114.247.50.2",

                "https://restapi.amap.com/v3/ip?key=0f0435e9f4b657862cc5036693c041df&ip=114.247.50.2",
                "https://restapi.amap.com/v3/ip?key=0f0435e9f4b657862cc5036693c041df&ip=114.247.50.2",
                "https://restapi.amap.com/v3/ip?key=0f0435e9f4b657862cc5036693c041df&ip=114.247.50.2",
                "https://restapi.amap.com/v3/ip?key=0f0435e9f4b657862cc5036693c041df&ip=114.247.50.2" };

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
                System.out.println(RequestUtil.get(url));
            } finally {
                countDownLatch.countDown();
            }
        }
    }
}