package com.fr.ai.wizards.gpt;

import com.fr.ai.wizards.AIWizards;

import java.util.List;

/**
 * @author anner
 * @version 11.0
 * Created on 2023/7/27
 * <p>
 * {
 * "code": 200,
 * "msg": "操作成功",
 * "data": {
 * "request_id": "7789521153846538868",
 * "task_id": "7789521153846538868",
 * "task_status": "SUCCESS",
 * "choices": [
 * {
 * "role": "assistant",
 * "content": "\" 好\\n\\n这个技创新力度，为经济社会发展注入强大的动力。\""
 * }
 * ],
 * "usage": {
 * "total_tokens": 181
 * }
 * },
 * "success": true
 * }
 */
public class ChatGLMResponse {
    private int code;

    private String msg;

    private Data data;

    private boolean success;


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }


    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public static class Data {
        private String request_id;
        private String task_id;

        private String task_status;

        private List<Choice> choices;

        private Usage usage;

        public String getRequest_id() {
            return request_id;
        }

        public Usage getUsage() {
            return usage;
        }

        public void setUsage(Usage usage) {
            this.usage = usage;
        }

        public void setRequest_id(String request_id) {
            this.request_id = request_id;
        }

        public String getTask_id() {
            return task_id;
        }

        public void setTask_id(String task_id) {
            this.task_id = task_id;
        }

        public String getTask_status() {
            return task_status;
        }

        public void setTask_status(String task_status) {
            this.task_status = task_status;
        }

        public List<Choice> getChoices() {
            return choices;
        }

        public void setChoices(List<Choice> choices) {
            this.choices = choices;
        }
    }

    public static class Choice {
        private String role;

        private String content;

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

    public static class Usage {
        private int total_tokens;

        public int getTotal_tokens() {
            return total_tokens;
        }

        public void setTotal_tokens(int total_tokens) {
            this.total_tokens = total_tokens;
        }
    }

    public static void main(String[] args) {
        AIWizards.fromJson("{\n" +
                "    \"code\": 200,\n" +
                "    \"msg\": \"操作成功\",\n" +
                "    \"data\": {\n" +
                "        \"request_id\": \"7789521153846538868\",\n" +
                "        \"task_id\": \"7789521153846538868\",\n" +
                "        \"task_status\": \"SUCCESS\",\n" +
                "        \"choices\": [\n" +
                "            {\n" +
                "                \"role\": \"assistant\",\n" +
                "                \"content\": \"\\\" 好的，这次我给您讲一个关于我国科技创新的故事。\\\\n\\\\n近年来，我国科技创新成果举世瞩目。其中一个典型的例子是华为公司研发的 5G 技术。5G 技术被誉为“新一代移动通信技术”，具有高速度、低时延、高稳定性等特点，对于我国经济社会发展具有重要意义。\\\\n\\\\n为了研发 5G 技术，华为公司投入了大量的研发资源，累计投入超过 1000 亿元人民币。在历经数年的努力后，华为成功研发出全球领先的 5G 技术，并成为全球最大的 5G 设备供应商。华为的 5G 技术已经成为我国科技创新的一张亮丽名片，为我国在科技领域取得世界领先地位做出了重要贡献。\\\\n\\\\n这个故事说明了我国在科技创新方面的巨大进步和成就，也展示了我国企业强大的创新能力和竞争力。在未来，我国将继续加大科技创新力度，为经济社会发展注入强大的动力。\\\"\"\n" +
                "            }\n" +
                "        ],\n" +
                "        \"usage\": {\n" +
                "            \"total_tokens\": 181\n" +
                "        }\n" +
                "    },\n" +
                "    \"success\": true\n" +
                "}", ChatGLMResponse.class);
    }
}
