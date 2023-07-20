package com.fr.ai.wizards.common.intercept;

import com.fr.ai.wizards.AIWizards;
import com.fr.third.net.bytebuddy.ByteBuddy;
import com.fr.third.net.bytebuddy.agent.ByteBuddyAgent;
import com.fr.third.net.bytebuddy.agent.builder.AgentBuilder;
import com.fr.third.net.bytebuddy.agent.builder.ResettableClassFileTransformer;
import com.fr.third.net.bytebuddy.asm.Advice;
import com.fr.third.net.bytebuddy.description.type.TypeDescription;
import com.fr.third.net.bytebuddy.dynamic.DynamicType;
import com.fr.third.net.bytebuddy.dynamic.scaffold.TypeValidation;
import com.fr.third.net.bytebuddy.utility.JavaModule;

import java.lang.instrument.Instrumentation;

/**
 * @author anner
 * @version 11.0
 * Created on 2023/7/17
 */
public class DataAgent {
    private static final int REDEFINE_FIXED_SIZE = 1;

    private static final Advisor[] advisors = {
            new NamedTableDataAdvisor()
    };

    private static ResettableClassFileTransformer resettable;
    private static Instrumentation instrumentation;


    static {
        try {
            instrumentation = ByteBuddyAgent.getInstrumentation();
            AIWizards.log("version supply byte-buddy instrumentation loaded by cached.");
        } catch (IllegalStateException e) {
            try {
                instrumentation = ByteBuddyAgent.install(
                        new ByteBuddyAgent.AttachmentProvider.Compound(ByteBuddyAgent.AttachmentProvider.DEFAULT));
                AIWizards.log("shadow-version supply byte-buddy instrumentation loaded by install.");
            } catch (Throwable t) {
                AIWizards.log("shadow-version supply byte-buddy instrumentation load error " + t.getMessage(), t);
            }
        }
    }

    private DataAgent() {
    }

    /**
     * 安装监听组件
     */
    public static void install() {
        if (instrumentation == null) {
            return;
        }

        AgentBuilder.Identified.Extendable extendable = null;
        for (Advisor advisor : advisors) {
            AgentBuilder agentBuilder = extendable;
            if (agentBuilder == null) {
                agentBuilder = new AgentBuilder.Default(new ByteBuddy().with(TypeValidation.DISABLED));
            }
            extendable = agentBuilder
                    .type(advisor.targetTypeMatcher())
                    .transform(
                            (builder, typeDescription, classLoader, javaModule)
                                    ->
                                    builder.visit(Advice.to(advisor.getClass()).on(advisor.targetMethodMatcher())));

        }

        if (extendable != null) {
            resettable = extendable.with(new Listener())
                    .disableClassFormatChanges()
                    /*
                    {@code com.fr.third.net.bytebuddy.agent.builder.AgentBuilder.RedefinitionStrategy.RETRANSFORMATION}
                    使得分析和主 jar 埋点同时生效
                    */
                    .with(AgentBuilder.RedefinitionStrategy.RETRANSFORMATION)
                    .with(AgentBuilder.InitializationStrategy.NoOp.INSTANCE)
                    .with(AgentBuilder.TypeStrategy.Default.REDEFINE)
                    .installOn(instrumentation);
        }
    }

    /**
     * 卸载监听组件
     */
    public static void uninstall() {
        if (resettable != null) {
            resettable.reset(instrumentation,
                    /*
                    {@code com.fr.third.net.bytebuddy.agent.builder.AgentBuilder.RedefinitionStrategy.RETRANSFORMATION}
                    使得插件禁用后，主 jar 埋点仍然生效且不出现异常
                    */
                    AgentBuilder.RedefinitionStrategy.RETRANSFORMATION,
                    AgentBuilder.RedefinitionStrategy.BatchAllocator.ForFixedSize.ofSize(REDEFINE_FIXED_SIZE)
            );
            AIWizards.log("[DEBUG-PLUGIN] shadow-version supply transform resetted."
            );
        }
    }

    private static class Listener extends AgentBuilder.Listener.Adapter {
        @Override
        public void onTransformation(TypeDescription typeDescription,
                                     ClassLoader classLoader,
                                     JavaModule module,
                                     boolean loaded,
                                     DynamicType dynamicType) {
            AIWizards.log(
                    "shadow-version supply transform {} success.",
                    typeDescription.getName()
            );
        }

        @Override
        public void onError(String typeName,
                            ClassLoader classLoader,
                            JavaModule module,
                            boolean loaded,
                            Throwable throwable) {
            AIWizards.error(
                    throwable,
                    "shadow-version supply transform {} failed.",
                    typeName
            );
        }
    }
}
