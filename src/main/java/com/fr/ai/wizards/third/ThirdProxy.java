package com.fr.ai.wizards.third;

import com.fr.ai.wizards.AIWizards;
import com.fr.ai.wizards.common.security.APIKeyPacks;
import com.fr.third.org.apache.http.client.config.RequestConfig;
import com.fr.third.org.apache.http.client.methods.CloseableHttpResponse;
import com.fr.third.org.apache.http.client.methods.HttpPost;
import com.fr.third.org.apache.http.entity.ContentType;
import com.fr.third.org.apache.http.entity.StringEntity;
import com.fr.third.org.apache.http.impl.client.CloseableHttpClient;
import com.fr.third.org.apache.http.impl.client.HttpClients;
import com.fr.third.org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URLEncoder;

/**
 * @author anner
 * @version 11.0
 * Created on 2023/7/25
 */
public class ThirdProxy {

    public static String ocr(String token, String img) {
        String responseBody = null;
        CloseableHttpClient httpClient = HttpClients.createDefault();

        try {
            HttpPost httpPost = new HttpPost("https://aip.baidubce.com/rest/2.0/ocr/v1/general?access_token=" + token);
            httpPost.addHeader("Content-Type", "application/x-www-form-urlencoded");
            httpPost.setEntity(new StringEntity("image=" + URLEncoder.encode(img, "UTF-8")));

            CloseableHttpResponse response = httpClient.execute(httpPost);
            responseBody = EntityUtils.toString(response.getEntity());
        } catch (Exception e) {
            AIWizards.error(e, "do post request fail");
        } finally {
            try {
                httpClient.close();
            } catch (IOException e) {
                AIWizards.error(e, "close http client fail");
            }
        }

        return responseBody;
    }


    public static String glm(String prompt) {
        String responseBody = null;
        CloseableHttpClient httpClient = HttpClients.custom()
                .setDefaultRequestConfig(RequestConfig.custom()
                        .setConnectTimeout(10 * 60 * 1000)    // 设置连接超时时间为 10 分钟
                        .setSocketTimeout(10 * 60 * 1000)     // 设置 socket 超时时间为 10 分钟
                        .setConnectionRequestTimeout(10 * 60 * 1000)   // 设置请求超时时间为 10 分钟
                        .build())
                .build();

        try {
            HttpPost httpPost = new HttpPost("https://open.bigmodel.cn/api/paas/v3/model-api/chatglm_std/invoke");
            httpPost.addHeader("Authorization", "Bearer " + APIKeyPacks.getGlmToken());
            httpPost.setEntity(new StringEntity(prompt, ContentType.APPLICATION_JSON));

            CloseableHttpResponse response = httpClient.execute(httpPost);
            responseBody = EntityUtils.toString(response.getEntity());
        } catch (Exception e) {
            AIWizards.error(e, "do post request fail");
        } finally {
            try {
                httpClient.close();
            } catch (IOException e) {
                AIWizards.error(e, "close http client fail");
            }
        }

        return responseBody;
    }
}
