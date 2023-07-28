package com.fr.ai.wizards.gpt;

import java.util.HashMap;
import java.util.Map;

/**
 * @author anner
 * @version 11.0
 * Created on 2023/7/28
 */
public class MossTplDataInfo {

    private String sessionID;

    // 表名称 -> 表可视化数据
    private Map<String, String> data = new HashMap<>();

    public String getSessionID() {
        return sessionID;
    }

    public void setSessionID(String sessionID) {
        this.sessionID = sessionID;
    }

    public Map<String, String> getData() {
        return data;
    }

    public void setData(Map<String, String> data) {
        this.data = data;
    }
}
