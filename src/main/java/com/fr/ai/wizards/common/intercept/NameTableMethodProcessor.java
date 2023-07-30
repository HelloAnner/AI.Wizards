package com.fr.ai.wizards.common.intercept;

import com.fr.ai.wizards.AIWizards;
import com.fr.ai.wizards.AIWizardsContext;
import com.fr.ai.wizards.data.WizardTableData;
import com.fr.ai.wizards.gpt.CombineTplDataInfo;
import com.fr.ai.wizards.semantic.WizardTableRowSemantic;
import com.fr.general.data.DataModel;
import com.fr.general.data.TableDataException;

/**
 * @author anner
 * @version 11.0
 * Created on 2023/7/18
 * <p>
 * 拦截二维表
 */
public class NameTableMethodProcessor {

    /**
     * 关联的反射调用 : {@link NamedTableDataAdvisor#onMethodExit}
     * <p>
     * 解析二维表表名、数据内容、sessionID
     *
     * @param model 二维表模型
     * @throws TableDataException 查询异常，正常抛出
     */
    public static void fetchTableData(DataModel model) throws TableDataException {
        String sessionID = AIWizards.fetchSessionID();
        CombineTplDataInfo combineInfo = AIWizardsContext.getSessionIDInfo(sessionID);

        // 解析表名称
        WizardTableRowSemantic tableRowSemantic = new WizardTableRowSemantic(model.getMetric().getDsName());
        for (int columnIndex = 0; columnIndex < model.getColumnCount(); columnIndex++) {
            tableRowSemantic.appendColumn(columnIndex, model.getColumnName(columnIndex));
        }

        // 解析表数据
        WizardTableData tableData = new WizardTableData(model.getRowCount(), model.getColumnCount());
        for (int row = 0; row < model.getRowCount(); row++) {
            for (int col = 0; col < model.getColumnCount(); col++) {
                tableData.set(row, col, model.getValueAt(row, col).toString());
            }
        }
        combineInfo.appendData(tableRowSemantic, tableData);
        AIWizards.log("fetch data success , session = {} ", sessionID);
    }

}
