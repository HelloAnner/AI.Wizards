package com.fr.ai.wizards.data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author anner
 * @version 11.0
 * Created on 2023/7/18
 * <p>
 * 二维表数据
 */
public class WizardTableData implements WizardData {

    private int rowSize;

    private int colSize;

    private List<String[]> data;

    public WizardTableData() {
    }

    public WizardTableData(int rowSize, int colSize) {
        this.rowSize = rowSize;
        this.colSize = colSize;

        data = new ArrayList<>(rowSize);

        for (int r = 0; r < rowSize; r++) {
            data.add(r, new String[colSize]);
        }
    }

    public void set(int row, int col, String val) {
        data.get(row)[col] = val;
    }

    public int getRowSize() {
        return rowSize;
    }

    public void setRowSize(int rowSize) {
        this.rowSize = rowSize;
    }

    public int getColSize() {
        return colSize;
    }

    public void setColSize(int colSize) {
        this.colSize = colSize;
    }

    public List<String[]> getData() {
        return data;
    }

    public List<String> getOneColumn(int colInx) {
        List<String> oneColumns = new ArrayList<>();
        for (int row = 0; row < rowSize; row++) {
            for (int col = 0; col < colSize; col++) {
                if (col == colInx) {
                    oneColumns.add(data.get(row)[col]);
                    break;
                }
            }
        }
        return oneColumns;
    }
}
