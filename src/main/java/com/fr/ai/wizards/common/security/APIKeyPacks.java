package com.fr.ai.wizards.common.security;

import com.fr.ai.wizards.AIWizards;
import com.fr.stable.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author anner
 * @version 11.0
 * Created on 2023/7/21
 * <p>
 * 统一识别一下 api key
 */
public class APIKeyPacks {

    private final static String OPENAI_API_KEY = "OPENAI_API_KEY";

    private final static String OPENGLM_API_KEY = "OPENGLM_API_KEY";

    private final static Map<String, String> apiKeyCache = new ConcurrentHashMap<>();


    public static void online() {
        apiKeyCache.put(OPENAI_API_KEY, StringUtils.EMPTY);
        apiKeyCache.put(OPENGLM_API_KEY, StringUtils.EMPTY);


        // 先读取程序启动参数，不存在读取系统环境变量
        readFromDebugParam();
        readFromSystemEnv();
        readFromConfig();

        // 打印秘钥可用性日志
        apiKeyCache.forEach((k, v) -> {
            if (StringUtils.isNotEmpty(v)) {
                AIWizards.log("load {} success", k);
            }
        });
    }


    public static void offline() {
        apiKeyCache.clear();
    }


    public static String getOpenaiAPIKey() {
        return apiKeyCache.get(OPENAI_API_KEY);
    }

    public static String getOpenglmAPIKey() {
        return apiKeyCache.get(OPENGLM_API_KEY);
    }

    private static void readFromDebugParam() {
        Map<String, String> tmpCache = new HashMap<>();
        apiKeyCache.forEach((keyName, keyVal) -> {
            if (StringUtils.isEmpty(keyVal)) {
                tmpCache.put(keyName, System.getProperty(keyName, StringUtils.EMPTY));
            }
        });
        apiKeyCache.putAll(tmpCache);
    }

    private static void readFromSystemEnv() {
        if (shouldIgnore()) {
            return;
        }
        Map<String, String> tmpCache = new HashMap<>();
        apiKeyCache.forEach((keyName, keyVal) -> {
            if (StringUtils.isEmpty(keyVal)) {
                // 注意运行环境，IDE内置可能无法正常获取到 env
                tmpCache.put(keyName, Optional.ofNullable(System.getenv(keyName)).orElse(StringUtils.EMPTY));
            }
        });
        apiKeyCache.putAll(tmpCache);
    }

    private static void readFromConfig() {
        if (shouldIgnore()) {
            return;
        }
        try {
            Properties properties = new Properties();
            properties.load(APIKeyPacks.class.getResourceAsStream("key.properties"));
            Map<String, String> tmpCache = new HashMap<>();
            apiKeyCache.forEach((keyName, keyVal) -> {
                if (StringUtils.isEmpty(keyVal)) {
                    tmpCache.put(keyName, properties.getProperty(keyName, StringUtils.EMPTY));
                }
            });
            apiKeyCache.putAll(tmpCache);
        } catch (Exception e) {
            AIWizards.log("read api key from config file fail : {}", e.getMessage());
        }
    }

    private static boolean shouldIgnore() {
        return apiKeyCache.values().stream().allMatch(StringUtils::isNotEmpty);
    }
}
