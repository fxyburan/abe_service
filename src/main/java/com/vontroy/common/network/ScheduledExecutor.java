package com.vontroy.common.network;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ScheduledExecutor {
    public static final String WEBHOOK
            = "https://oapi.dingtalk"
            + ".com/robot/send?access_token=46282f49cb448272ced940986475c906b653a0442af6a1b60e4c7ba1ad5e678a";
    public static void main(String[] args) {
        Runnable runnable = new Runnable() {
            public void run() {
                try {
                    HttpClient httpClient = HttpClients.createDefault();

                    HttpPost httpPost = new HttpPost(WEBHOOK);
                    httpPost.addHeader("Content-Type", "application/json; charset=utf-8");

                    String textMsg = "{ \"msgtype\": \"text\", \"text\":{\"content\":\"Content Test\"}}";
                    StringEntity stringEntity = new StringEntity(textMsg, "utf-8");
                    httpPost.setEntity(stringEntity);

                    HttpResponse response = httpClient.execute(httpPost);
                    if (HttpStatus.SC_OK == response.getStatusLine().getStatusCode()) {
                        String result = EntityUtils.toString(response.getEntity(), "utf-8");
                        System.out.println(result);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };

        ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        scheduledExecutorService.scheduleWithFixedDelay(runnable, 1, 3, TimeUnit.SECONDS);
    }
}
