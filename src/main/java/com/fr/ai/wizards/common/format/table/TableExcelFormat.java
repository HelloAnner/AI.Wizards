package com.fr.ai.wizards.common.format.table;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author anner
 * @version 11.0
 * Created on 2023/7/20
 * <p>
 * 按照可视化的格式输出，类似 mycli 输出格式 , 这个格式 gpt 可以比较好的理解
 * <p>
 * +------+------+-----+-----+--------+
 * | name | role | did | pid | post   |
 * +------+------+-----+-----+--------+
 * | u1   | CE   | 0   | 1   | p1     |
 * | u2   | CE   | 0   | 1   | p3     |
 * | u3   | CE   | 0   | 1   | <null> |
 * +------+------+-----+-----+--------+
 */
public class TableExcelFormat extends TableFormat {

    private final static String COLUMN_NAME_ROW_CHAR = "------+";
    private final static String COLUMN_SPLIT_CHAR = "\t|\t";

    @Override
    protected String doFormat(List<String> columnNames, List<String[]> table) {
        int columnSize = columnNames.size();
        StringBuilder buffer = new StringBuilder();
        // 列名
        appendSplitRow(buffer, columnSize);
        appendOneRowData(buffer, columnNames);
        appendSplitRow(buffer, columnSize);

        // 数据
        for (String[] row : table) {
            appendOneRowData(buffer, Arrays.stream(row).collect(Collectors.toList()));
        }

        appendSplitRow(buffer, columnSize);

        return buffer.toString();
    }

    private void appendOneRowData(StringBuilder buffer, List<String> rowData) {
        buffer.append(COLUMN_SPLIT_CHAR);
        for (String rowDatum : rowData) {
            buffer.append(rowDatum).append(COLUMN_SPLIT_CHAR);
        }
        buffer.append("\n");
    }

    private void appendSplitRow(StringBuilder buffer, int size) {
        buffer.append("+");
        for (int i = 0; i < size; i++) {
            buffer.append(COLUMN_SPLIT_CHAR);
        }
        buffer.append("\n");
    }
}
