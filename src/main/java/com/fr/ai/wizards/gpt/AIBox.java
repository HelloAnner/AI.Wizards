package com.fr.ai.wizards.gpt;

import com.fr.ai.wizards.common.format.WizardFormatMaster;
import com.fr.ai.wizards.common.format.table.TableExcelFormat;

/**
 * @author anner
 * @version 11.0
 * Created on 2023/7/20
 */
public class AIBox {

    /**
     * 一轮的对话结果，先测试一下
     * <p>
     * 完成后丢弃这个 sessionID 的信息
     *
     * @return 对这个sessionID 查到的信息的咨询
     */
    public static MossTplDataInfo oneway(CombineTplDataInfo combineInfo) {
        MossTplDataInfo mossTplDataInfo = new MossTplDataInfo();
        // 格式化一下信息 , 测试一下二维表的可视化
        combineInfo.getWizardTableDataMap().forEach((sem, data) -> {
            mossTplDataInfo.setSessionID(combineInfo.getSessionID());
            mossTplDataInfo.getData().put(sem.getTableName(), WizardFormatMaster.format(TableExcelFormat.class, sem.getColumnNames(), data.getData()));
        });

        return mossTplDataInfo;
    }
}
