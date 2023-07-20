package com.fr.ai.wizards.common.format.table;

import com.fr.ai.wizards.common.format.WizardFormat;

import java.util.List;

/**
 * @author anner
 * @version 11.0
 * Created on 2023/7/20
 */
public abstract class TableFormat implements WizardFormat {
    @Override
    public String format(Object... o) {
        if (accept(o)) {
            return doFormat((List<String>) o[0], (List<String[]>) o[1]);
        }
        return null;
    }


    /**
     * @param columnNames
     * @param table
     * @return
     */
    protected abstract String doFormat(List<String> columnNames, List<String[]> table);


    @Override
    public boolean accept(Object... o) {
        boolean acceptOne = ((o[0] instanceof List) && !((List<?>) o[0]).isEmpty() && ((List<?>) o[0]).get(0) instanceof String);
        boolean acceptSecond = ((o[1] instanceof List) && !((List<?>) o[1]).isEmpty() && ((List<?>) o[1]).get(0) instanceof Object[]);
        return acceptOne && acceptSecond;
    }
}
