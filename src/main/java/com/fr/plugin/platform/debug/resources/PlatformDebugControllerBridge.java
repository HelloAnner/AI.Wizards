package com.fr.plugin.platform.debug.resources;

import com.fr.decision.fun.impl.AbstractControllerRegisterProvider;
import com.fr.plugin.transform.FunctionRecorder;

/**
 * Create by anner on 2022/3/29
 */
@FunctionRecorder
public class PlatformDebugControllerBridge extends AbstractControllerRegisterProvider {
    @Override
    public Class<?>[] getControllers() {
        return new Class[]{
                AIWizardController.class
        };
    }
}
