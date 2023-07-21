package com.fr.ai.wizards.common.http;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

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
public class WizardsHttpClient {

    private final static CloseableHttpClient client = HttpClients.createDefault();


    public static String get(String url) {
        return null;
    }

    public static String post() {
        return null;
    }

    public static String sse() {
        return null;
    }

    private static String doGet(String url, String requestBody) {
        return null;
    }
}
