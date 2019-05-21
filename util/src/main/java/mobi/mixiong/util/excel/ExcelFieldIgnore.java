package mobi.mixiong.util.excel;

import java.lang.annotation.*;

/**导出表格忽略字段
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface ExcelFieldIgnore {

}

