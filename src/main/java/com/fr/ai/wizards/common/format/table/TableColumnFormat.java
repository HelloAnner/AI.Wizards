package com.fr.ai.wizards.common.format.table;

import com.fr.ai.wizards.AIWizards;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author anner
 * @version 11.0
 * Created on 2023/7/20
 * <p>
 * 按列输出，格式为json字符串
 */
public class TableColumnFormat extends TableFormat {

    @Override
    protected String doFormat(List<String> columnNames, List<String[]> table) {
        // 列名 -> 列数据
        Map<String, List<String>> tableData = new LinkedHashMap<>();
        for (int col = 0; col < columnNames.size(); col++) {
            tableData.computeIfAbsent(columnNames.get(col), k -> new ArrayList<>()).addAll(getOneColumn(col, table));
        }
        return AIWizards.toJson(tableData);
    }

    public List<String> getOneColumn(int colInx, List<String[]> table) {
        List<String> oneColumns = new ArrayList<>();
        for (int row = 0; row < table.size(); row++) {
            for (int col = 0; col < table.get(0).length; col++) {
                if (col == colInx) {
                    oneColumns.add(table.get(row)[col]);
                    break;
                }
            }
        }
        return oneColumns;
    }
}
