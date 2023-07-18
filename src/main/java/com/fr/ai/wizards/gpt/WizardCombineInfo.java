package com.fr.ai.wizards.gpt;

import com.fr.ai.wizards.AIWizards;
import com.fr.ai.wizards.data.WizardTableData;
import com.fr.ai.wizards.semantic.WizardTableRowSemantic;
import com.fr.ai.wizards.semantic.WizardTextSemantic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author anner
 * @version 11.0
 * Created on 2023/7/18
 * <p>
 * 数据和语料信息组装完成之后，这里汇总，开始交给大模型黑盒子
 * <p>
 * TODO 这里如果是前台调用模型接口，可以先不加内容，只做 data 和 semantic 内容的组装，给前台使用
 */
public class WizardCombineInfo {

    private String sessionID;

    // 单独的文本语义 ， 可以理解为背景
    private List<WizardTextSemantic> textSemantics = new ArrayList<>();

    // 二维表类型的 语义 -> 数据 ， 每一个数据都是存在其业务含义的
    private Map<WizardTableRowSemantic, WizardTableData> wizardTableDataMap = new HashMap<>();


    public WizardCombineInfo() {
    }

    public WizardCombineInfo(String sessionID) {
        this.sessionID = sessionID;
    }

    public void appendData(WizardTableRowSemantic rowSemantic, WizardTableData data) {
        wizardTableDataMap.put(rowSemantic, data);
    }


    public List<WizardTextSemantic> getTextSemantics() {
        return textSemantics;
    }

    public void setTextSemantics(List<WizardTextSemantic> textSemantics) {
        this.textSemantics = textSemantics;
    }

    public Map<WizardTableRowSemantic, WizardTableData> getWizardTableDataMap() {
        return wizardTableDataMap;
    }

    public void setWizardTableDataMap(Map<WizardTableRowSemantic, WizardTableData> wizardTableDataMap) {
        this.wizardTableDataMap = wizardTableDataMap;
    }

    public String getSessionID() {
        return sessionID;
    }

    /**
     * 内存结构转换为直观理解的json内容
     * <p>
     * 内存结构 : 语义 -> 数据
     * JSON结构 : ID -> 语义 + 数据
     * <p>
     * 注意耗时
     *
     * @return JSON结构，给前台 , 返回数据GPT是无法理解的，需要语言润色
     * @throws Exception 序列化异常
     */
    public String toJson() throws Exception {
        return CombineJsonBean.from(this).toJson();
    }

    /**
     * 重新组织一下信息结构，方便理解
     */
    private final static class CombineJsonBean {

        // 背景
        private List<String> backgrounds = new ArrayList<>();

        // 数据和数据的含义
        // 表名 -> 列名 -> 列数据
        private Map<String, Map<String, List<String>>> tableBean = new LinkedHashMap<>();

        static CombineJsonBean from(WizardCombineInfo info) {
            CombineJsonBean bean = new CombineJsonBean();

            info.textSemantics.forEach(sem -> bean.backgrounds.add(sem.getMsg()));
            info.wizardTableDataMap.forEach((sem, data) -> {
                // 列名 -> 列数据
                Map<String, List<String>> tableData = new LinkedHashMap<>();
                for (int col = 0; col < data.getColSize(); col++) {
                    tableData.computeIfAbsent(sem.getColumnNames().get(col), k -> new ArrayList<>()).addAll(data.getOneColumn(col));
                }
                bean.tableBean.put(sem.getTableName(), tableData);
            });
            return bean;
        }

        public List<String> getBackgrounds() {
            return backgrounds;
        }

        public Map<String, Map<String, List<String>>> getTableBean() {
            return tableBean;
        }

        String toJson() {
            return AIWizards.toJson(this);
        }
    }
}
