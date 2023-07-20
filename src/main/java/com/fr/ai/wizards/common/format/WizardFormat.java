package com.fr.ai.wizards.common.format;

/**
 * @author anner
 * @version 11.0
 * Created on 2023/7/20
 */
public interface WizardFormat {

    /**
     * 格式化为字符串 - 不考虑其他格式，毕竟仅仅是给 gpt 使用的
     *
     * @param o 对象
     * @return 格式化的结果
     */
    String format(Object... o);


    /**
     * @param o 对象
     * @return 是否允许此类格式的对象格式化
     */
    boolean accept(Object... o);
}
