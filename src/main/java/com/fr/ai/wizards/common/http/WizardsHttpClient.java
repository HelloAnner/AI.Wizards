package com.fr.ai.wizards.common.http;

import com.fr.ai.wizards.AIWizards;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * @author anner
 * @version 11.0
 * Created on 2023/7/21
 * <p>
 * 支持 http 协议传输信息的 公共底座
 * 包括 sse 协议调用实现 、 简单的 http 调用
 * 侧重点偏向于 大模型 调用部分的逻辑 , 但是需要注意不要参与业务逻辑
 * 支持自动组装读取的秘钥，调用方不需要感知认证过程，只做业务处理
 */
public abstract class WizardsHttpClient {

    public static String get(String url) {
        return null;
    }

    public static String post() {
        return null;
    }

    public static String sse() {
        return null;
    }

    protected static String doGet(String url, String requestBody, String token) {
// 创建 OkHttpClient 实例
        OkHttpClient httpClient = new OkHttpClient();


        // 创建 JSON 请求体
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create("1212", JSON);

        // 创建 GET 请求，并添加 Authorization 请求头
        Request request = new Request.Builder()
                .url(url)
                .addHeader("Authorization", "Bearer " + token)
                .post(body)
                .build();

        try {
            // 发送请求并获取响应
            Response response = httpClient.newCall(request).execute();

            // 输出响应内容
            String responseBody = response.body().string();
            System.out.println(responseBody);

            // 关闭响应
            response.close();
        } catch (Exception e) {
            AIWizards.error(e, "do get request fail");
        } finally {

        }


        return null;
    }
}
