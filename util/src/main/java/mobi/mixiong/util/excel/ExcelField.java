package mobi.mixiong.util.excel;

import java.lang.annotation.*;

/**
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface ExcelField {

    /**
     * 列名称
     *
     * @return
     */
    String name() default "";

    /**
     * 列宽 (大于0时生效; 如果不指定列宽，将会自适应调整宽度；)
     *
     * @return
     */
    int width() default 0;

    /**
     * 时间格式化，日期类型时生效
     *
     * @return
     */
    String dateformat() default "yyyy-MM-dd HH:mm:ss";

}

