package com.fr.ai.wizards.common.format;

import com.fr.ai.wizards.common.format.table.TableColumnFormat;
import com.fr.ai.wizards.common.format.table.TableExcelFormat;
import com.fr.third.guava.collect.Lists;

import java.util.ArrayList;
import java.util.List;

/**
 * @author anner
 * @version 11.0
 * Created on 2023/7/20
 * <p>
 * TODO 按照 from 和 to 格式来匹配 format器
 */
public class WizardFormatMaster {

    private final static List<WizardFormat> formats = new ArrayList<>();

    static {
        formats.addAll(Lists.newArrayList(
                new TableExcelFormat(),
                new TableColumnFormat()
        ));
    }


    /**
     * 自动解析类型，按照匹配到的第一个符合条件的格式化器完成
     *
     * @param o 对象
     * @return 解析结果
     */
    public static String format(Object... o) {
        return formats.stream()
                .filter(ft -> ft.accept(o))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("format not exist"))
                .format(o);
    }

    /**
     * 指定解析器
     *
     * @param o         对象
     * @param formatCls 解析器类型
     * @return 格式化结果
     */
    public static String format(Class<? extends WizardFormat> formatCls, Object... o) {
        // 解析器不多，直接遍历吧
        return formats.stream()
                .filter(ft -> ft.getClass() == formatCls && ft.accept(o))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("format not exist"))
                .format(o);
    }
}
