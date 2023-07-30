package com.fr.ai.wizards.gpt;

import java.util.ArrayList;
import java.util.List;

/**
 * @author anner
 * @version 11.0
 * Created on 2023/7/27
 */
public class CombinePromptInfo {

    private List<PromptInfo> prompt = new ArrayList<>();

    public List<PromptInfo> getPrompt() {
        return prompt;
    }

    public void setPrompt(List<PromptInfo> prompt) {
        this.prompt = prompt;
    }
}
