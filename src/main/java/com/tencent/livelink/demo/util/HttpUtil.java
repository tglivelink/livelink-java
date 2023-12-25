package com.tencent.livelink.demo.util;

import com.alibaba.fastjson2.JSONObject;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;


@Slf4j
public class HttpUtil {

    /**
     * 发送请求
     * @param url url
     * @param paramObj body参数
     * @param headers headers
     * @param connectTimeout 连接超时
     * @param socketTimeout 通讯超时
     * @return 返回结构
     */
    public static String sendPost(
            String url,
            JSONObject paramObj,
            Map<String, String> headers,
            int connectTimeout,
            int socketTimeout
    ) {
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(connectTimeout) // 设置连接超时时间
                .setSocketTimeout(socketTimeout) // 设置读取超时时间
                .build();

        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
        HttpPost post = new HttpPost(url);
        String result = null;
        InputStream respIs = null;

        try (CloseableHttpClient closeableHttpClient = httpClientBuilder
                .setDefaultRequestConfig(requestConfig)
                .build()) {

            HttpEntity entity = new StringEntity(
                    JsonUtil.objectToJsonString(paramObj),
                    StandardCharsets.UTF_8
            );
            post.setEntity(entity);
            post.setHeader(
                    "Content-type",
                    "application/json"
            );

            for (Map.Entry<String, String> entry : headers.entrySet()) {
                post.setHeader(
                        entry.getKey(),
                        entry.getValue()
                );
            }

            // 防止ml
            try (CloseableHttpResponse resp = closeableHttpClient.execute(post)) {
                respIs = resp
                        .getEntity()
                        .getContent();
                byte[] respBytes = IOUtils.toByteArray(respIs);
                result = new String(
                        respBytes,
                        StandardCharsets.UTF_8
                );
            } catch (Exception e) {
                log.error(
                        "post url failed! url:{}",
                        url
                );
                log.error(
                        "read failed! e:",
                        e
                );
                e.printStackTrace();
            } finally {
                IOUtils.closeQuietly(respIs);
            }

            return result;

        } catch (IOException e) {
            log.error(
                    "post failed! e:",
                    e
            );
        }
        return result;
    }


}
