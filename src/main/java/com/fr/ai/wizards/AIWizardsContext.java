package com.fr.ai.wizards;

import com.fr.ai.wizards.gpt.CombinePromptInfo;
import com.fr.ai.wizards.gpt.PromptInfo;
import com.fr.ai.wizards.gpt.WizardCombineInfo;
import com.fr.essential.caffeine.cache.Cache;
import com.fr.essential.caffeine.cache.Caffeine;
import com.fr.third.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;

/**
 * @author anner
 * @version 11.0
 * Created on 2023/7/18
 * <p>
 * 考虑并发控制插件运行context
 */
public class AIWizardsContext {

    private AIWizardsContext() {
    }

    private final static Cache<String, WizardCombineInfo> sessionIDWizardInfoCache = Caffeine.newBuilder().build();

    // id - 上下文信息
    private final static Cache<String, CombinePromptInfo> promptCache = Caffeine.newBuilder().build();


    public static CombinePromptInfo getPrompt(String id) {
        return promptCache.get(id, k -> new CombinePromptInfo());
    }


    public static void appendPrompt(String id, List<PromptInfo> appendInfo) {
        if (appendInfo == null) {
            return;
        }
        CombinePromptInfo combinePromptInfo = getPrompt(id);
        combinePromptInfo.getPrompt().addAll(appendInfo);
    }


    public static void cleanPrompt(String id) {
        promptCache.invalidate(id);
    }


    /**
     * 获取/ 新建 一个 缓存
     *
     * @param sessionID 预览的sessionID
     * @return 缓存信息
     */
    public static WizardCombineInfo getSessionIDInfo(String sessionID) {
        AIWizards.log("get or new cache info for sessionID {} , now cache size is {}", sessionID, sessionIDWizardInfoCache.estimatedSize());
        return sessionIDWizardInfoCache.get(sessionID, WizardCombineInfo::new);
    }


    /**
     * 释放 sessionID 缓存信息
     * <p>
     * 释放时机： 向GPT完成一次发送就需要释放 , 释放后所有关联的cache的对象都会被回收
     *
     * @param sessionID
     */
    public static void releaseSessionIDInfo(String sessionID) {
        sessionIDWizardInfoCache.invalidate(sessionID);
        AIWizards.log("release sessionID {} cache success , now cache estimated size is {}", sessionID, sessionIDWizardInfoCache.estimatedSize());
    }

    /**
     * 打印缓存调试使用，注意这里很耗时
     *
     * @return map
     */
    public static String print() throws JsonProcessingException {
        StringBuilder stringBuilder = new StringBuilder();
        sessionIDWizardInfoCache.asMap().forEach((id, info) -> {
            try {
                stringBuilder.append(info.getSessionID()).append("->").append(info.toJson()).append("\n");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        return stringBuilder.toString();
    }


    public static void clearAll() {
        sessionIDWizardInfoCache.invalidateAll();
    }
}
