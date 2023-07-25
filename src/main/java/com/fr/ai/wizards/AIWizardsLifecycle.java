package com.fr.ai.wizards;

import com.fr.ai.wizards.common.intercept.WizardsDataAgent;
import com.fr.ai.wizards.common.security.APIKeyPacks;
import com.fr.plugin.context.PluginContext;
import com.fr.plugin.observer.inner.AbstractPluginLifecycleMonitor;
import com.fr.plugin.transform.FunctionRecorder;

/**
 * @author anner
 * @version 11.0
 * Created on 2023/7/17
 * <p>
 * 生命周期管理 , 如果是插件，接入系统的生命周期逻辑； 如果是调用 ， 直接在系统的生命周期引入
 */
@FunctionRecorder
public class AIWizardsLifecycle extends AbstractPluginLifecycleMonitor {
    @Override
    public void afterRun(PluginContext context) {
        // 拦截业务数据
        WizardsDataAgent.install();
        AIWizards.log("fine data agent tools attached.");

        // 读取API KEY
        APIKeyPacks.online();

    }

    @Override
    public void beforeStop(PluginContext context) {
        WizardsDataAgent.uninstall();
        AIWizards.log("fine data agent tools detached.");

        APIKeyPacks.offline();
    }
}
