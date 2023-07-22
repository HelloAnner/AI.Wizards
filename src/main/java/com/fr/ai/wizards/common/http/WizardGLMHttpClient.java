package com.fr.ai.wizards.common.http;

import com.fr.ai.wizards.AIWizards;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author anner
 * @version 11.0
 * Created on 2023/7/22
 */
public class WizardGLMHttpClient extends WizardsHttpClient {
    private final static String TOKEN = "";

    private static List<Prompt> prompts = Arrays.asList(
            new Prompt().role("user").content("你好"),
            new Prompt().role("assistant").content("我是人工智能助手")
    );


    public static String syncStd(String msg) {
        List<Prompt> ps = new ArrayList<>();
        ps.add(new Prompt().role("user").content(msg));


        doGet("https://open.bigmodel.cn/api/paas/v3/model-api/chatglm_std/invoke",
                AIWizards.toJson(ps), TOKEN);

        return null;
    }

    public static void main(String[] args) {
        syncStd("hello");
    }

    // 每一个模型的 prompt 是固定的
    private static class Prompt {
        private String role;
        private String content;

        public Prompt() {
        }

        public Prompt(String role, String content) {
            this.role = role;
            this.content = content;
        }

        public Prompt role(String role) {
            this.role = role;
            return this;
        }

        public Prompt content(String content) {
            this.content = content;
            return this;
        }

        public String getRole() {
            return role;
        }

        public void setRole(String role) {
            this.role = role;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }
}
