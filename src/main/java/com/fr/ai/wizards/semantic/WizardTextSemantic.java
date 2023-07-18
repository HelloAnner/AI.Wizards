package com.fr.ai.wizards.semantic;

/**
 * @author anner
 * @version 11.0
 * Created on 2023/7/18
 * <p>
 * 直接获取的模板上的文字信息，提供给GPT作为辅助信息参考
 */
public class WizardTextSemantic implements Semantic {
    private String msg;

    public WizardTextSemantic() {
    }

    public WizardTextSemantic(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
