package com.fr.ai.wizards.semantic;

import java.util.ArrayList;
import java.util.List;

/**
 * @author anner
 * @version 11.0
 * Created on 2023/7/18
 * <p>
 * 二维表信息语料 - 表名的含义
 */
public class WizardTableRowSemantic implements Semantic {

    private String tableName;

    private List<String> columnNames = new ArrayList<>();

    public WizardTableRowSemantic(String tableName) {
        this.tableName = tableName;
    }

    public WizardTableRowSemantic tableName(String tableName) {
        this.tableName = tableName;
        return this;
    }

    public WizardTableRowSemantic appendColumn(int index, String name) {
        columnNames.add(index, name);
        return this;
    }


    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public List<String> getColumnNames() {
        return columnNames;
    }

    public void setColumnNames(List<String> columnNames) {
        this.columnNames = columnNames;
    }
}
