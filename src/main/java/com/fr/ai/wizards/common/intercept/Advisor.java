package com.fr.ai.wizards.common.intercept;

import com.fr.third.net.bytebuddy.description.method.MethodDescription;
import com.fr.third.net.bytebuddy.description.type.TypeDescription;
import com.fr.third.net.bytebuddy.matcher.ElementMatcher;
import org.jetbrains.annotations.NotNull;

/**
 * @author anner
 * @version 11.0
 * Created on 2023/7/17
 */
public interface Advisor {
    /**
     * 返回被监听类的匹配器
     */
    @NotNull ElementMatcher<? super TypeDescription> targetTypeMatcher();

    /**
     * 返回被监听方法的匹配器
     */
    @NotNull ElementMatcher.Junction<? super MethodDescription> targetMethodMatcher();
}
