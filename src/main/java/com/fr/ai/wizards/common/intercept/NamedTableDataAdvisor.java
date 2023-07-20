package com.fr.ai.wizards.common.intercept;

import com.fr.ai.wizards.AIWizards;
import com.fr.general.data.DataModel;
import com.fr.invoke.Reflect;
import com.fr.plugin.manage.PluginManager;
import com.fr.script.Calculator;
import com.fr.third.net.bytebuddy.asm.Advice;
import com.fr.third.net.bytebuddy.description.method.MethodDescription;
import com.fr.third.net.bytebuddy.description.type.TypeDescription;
import com.fr.third.net.bytebuddy.implementation.bytecode.assign.Assigner;
import com.fr.third.net.bytebuddy.matcher.ElementMatcher;
import com.fr.third.net.bytebuddy.matcher.ElementMatchers;
import org.jetbrains.annotations.NotNull;

import static com.fr.third.net.bytebuddy.matcher.ElementMatchers.isPublic;
import static com.fr.third.net.bytebuddy.matcher.ElementMatchers.returns;

/**
 * @author anner
 * @version 11.0
 * Created on 2023/7/17
 * <p>
 * 拦截一下从数据源获取数据的逻辑
 */
public class NamedTableDataAdvisor implements Advisor {
    @Override
    public @NotNull ElementMatcher<? super TypeDescription> targetTypeMatcher() {
        return ElementMatchers.named("com.fr.data.impl.NameTableData");
    }

    @Override
    public @NotNull ElementMatcher.Junction<? super MethodDescription> targetMethodMatcher() {
        return ElementMatchers
                .named("createDataModel")
                .and(isPublic())
                .and(ElementMatchers.isMethod())
                .and(ElementMatchers.takesArguments(Calculator.class))
                .and(returns(DataModel.class));
    }

    @Advice.OnMethodExit
    public static void onMethodExit(@Advice.Return(typing = Assigner.Typing.DYNAMIC, readOnly = false) DataModel model,
                                    @Advice.AllArguments(typing = Assigner.Typing.DYNAMIC) Object[] args) {
        try {
            Class<?> proxyClass = PluginManager
                    .getContext("com.fr.plugin.ai.wizards")
                    .classForName("com.fr.ai.wizards.common.intercept.NameTableMethodProcessor");
            Reflect.on(proxyClass).call("fetchTableData", model);
        } catch (ClassNotFoundException e) {
            AIWizards.log(e.getMessage(), e);
        }
    }
}
