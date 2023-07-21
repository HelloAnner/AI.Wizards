package com.fr.ai.wizards.gpt;

import com.fr.ai.wizards.AIWizards;
import com.fr.ai.wizards.AIWizardsContext;
import com.fr.ai.wizards.common.format.WizardFormatMaster;
import com.fr.ai.wizards.common.format.table.TableExcelFormat;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author anner
 * @version 11.0
 * Created on 2023/7/20
 */
public class AIBox {

    // 这里可以思考一下是不是可以多个模型线性调用，类似 langchain 的机制
    private final static List<AIAgent> agents = new CopyOnWriteArrayList<>();


    public static void registerAgent(AIAgent agent) {
        Optional.ofNullable(agent).ifPresent(agents::add);
    }


    public static void sse() {

    }


    /**
     * 一轮的对话结果，先测试一下
     * <p>
     * 完成后丢弃这个 sessionID 的信息
     *
     * @param sessionID sessionID信息
     * @return 对这个sessionID 查到的信息的咨询
     */
    public static String oneway(String sessionID) {
        try {
            WizardCombineInfo combineInfo = AIWizardsContext.get(sessionID);
            StringBuilder res = new StringBuilder();

            // 格式化一下信息 , 测试一下二维表的可视化
            combineInfo.getWizardTableDataMap().forEach((sem, data) -> {
                res.append(WizardFormatMaster.format(TableExcelFormat.class, sem.getColumnNames(), data.getData())).append("\n");
            });

            return res.toString();

        } catch (Exception e) {
            AIWizards.error(e, "handle oneway request error ,sessionID is {}", sessionID);
        } finally {
            AIWizardsContext.release(sessionID);
        }
        return null;
    }
}
