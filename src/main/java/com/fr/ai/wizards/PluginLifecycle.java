package com.fr.ai.wizards;

import com.fr.ai.wizards.common.DataAgent;
import com.fr.log.FineLoggerFactory;
import com.fr.plugin.context.PluginContext;
import com.fr.plugin.observer.inner.AbstractPluginLifecycleMonitor;
import com.fr.plugin.transform.FunctionRecorder;

/**
 * @author anner
 * @version 11.0
 * Created on 2023/7/17
 * <p>
 * 插件生命周期管理
 */
@FunctionRecorder
public class PluginLifecycle extends AbstractPluginLifecycleMonitor {
    @Override
    public void afterRun(PluginContext context) {
        DataAgent.install();

        FineLoggerFactory.getLogger().info("[AI Wizards] debug tools attached.");
    }

    @Override
    public void beforeStop(PluginContext context) {
        DataAgent.uninstall();

        FineLoggerFactory.getLogger().info("[AI Wizards] debug tools detached.");
    }
}
