package com.fr.ai.wizards;

import com.fr.general.web.ParameterConstants;
import com.fr.log.FineLoggerFactory;
import com.fr.third.fasterxml.jackson.core.JsonProcessingException;
import com.fr.third.fasterxml.jackson.databind.ObjectMapper;
import com.fr.third.springframework.web.context.request.RequestContextHolder;
import com.fr.third.springframework.web.context.request.ServletRequestAttributes;
import com.fr.web.utils.WebUtils;

/**
 * @author anner
 * @version 11.0
 * Created on 2023/7/18
 */
public class AIWizards {

    public final static String TAG = " [AI Wizards] ";

    public final static String PLUGIN_ID = "com.fr.plugin.ai.wizards";

    // 插件序列化器 - 这里统一一下
    private final static ObjectMapper mapper = new ObjectMapper();

    public static void log(String msg, Object... params) {
        FineLoggerFactory.getLogger().info(TAG + msg, params);
    }

    public static void error(Throwable stack, String msg, Object... params) {
        FineLoggerFactory.getLogger().error(stack, msg, params);
    }

    public static ObjectMapper objectMapper() {
        return mapper;
    }

    public static String toJson(Object obj) {
        try {
            return mapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static String fetchSessionID() {
        return WebUtils.getHTTPRequestParameter(((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest(), ParameterConstants.SESSION_ID);
    }
}
