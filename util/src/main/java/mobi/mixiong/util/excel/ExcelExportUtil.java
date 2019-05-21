package mobi.mixiong.util.excel;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

/**
 */
@Slf4j
public class ExcelExportUtil {

    /**
     * 导出Excel对象
     *
     * @param dataListArr Excel数据
     * @return
     */
    public static Workbook exportWorkbook(List<?>... dataListArr) {

        if (dataListArr == null || dataListArr.length == 0) {
            throw new RuntimeException("create excel error, data array can not be empty.");
        }

        Workbook workbook = new HSSFWorkbook();

        for (List<?> dataList : dataListArr) {
            makeSheet(workbook, dataList);
        }

        return workbook;
    }

    private static void makeSheet(Workbook workbook, List<?> dataList) {
        if (dataList == null || dataList.size() == 0) {
            log.error("create excel error, data can not be empty.");
            return;
        }

        Class<?> sheetClass = dataList.get(0).getClass();
        ExcelSheet excelSheet = sheetClass.getAnnotation(ExcelSheet.class);

        String sheetName = dataList.get(0).getClass().getSimpleName();
        HSSFColor.HSSFColorPredefined headColor = null;
        if (excelSheet != null) {
            if (excelSheet.name() != null && excelSheet.name().trim().length() > 0) {
                sheetName = excelSheet.name().trim();
            }
            headColor = excelSheet.headColor();
        }

        Sheet existSheet = workbook.getSheet(sheetName);
        if (existSheet != null) {
            for (int i = 2; i <= 1000; i++) {
                String newSheetName = sheetName.concat(String.valueOf(i));
                existSheet = workbook.getSheet(newSheetName);
                if (existSheet == null) {
                    sheetName = newSheetName;
                    break;
                }
            }
        }

        Sheet sheet = workbook.createSheet(sheetName);

        // sheet field
        List<Field> fields = new ArrayList<Field>();
        if (sheetClass.getDeclaredFields() != null && sheetClass.getDeclaredFields().length > 0) {
            for (Field field : sheetClass.getDeclaredFields()) {
                if (Modifier.isStatic(field.getModifiers()) || field.isAnnotationPresent(ExcelFieldIgnore.class)) {
                    continue;
                }
                fields.add(field);
            }
        }

        if (fields.isEmpty()) {
            throw new RuntimeException("create excel error, data field can not be empty.");
        }

        // sheet header row
        CellStyle headStyle = null;
        if (headColor != null) {
            headStyle = workbook.createCellStyle();
            headStyle.setFillForegroundColor(headColor.getIndex());
            headStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            headStyle.setFillBackgroundColor(headColor.getIndex());
        }

        Row headRow = sheet.createRow(0);
        boolean ifSetWidth = false;
        for (int i = 0; i < fields.size(); i++) {
            Field field = fields.get(i);
            ExcelFieldIgnore ignore = field.getAnnotation(ExcelFieldIgnore.class);
            if(ignore != null ){
                continue;
            }
            ExcelField excelField = field.getAnnotation(ExcelField.class);
            String fieldName = (excelField != null && excelField.name() != null && excelField.name().trim().length() > 0) ? excelField.name() : field.getName();
            int fieldWidth = (excelField != null) ? excelField.width() : 0;

            Cell cellX = headRow.createCell(i, CellType.STRING);
            if (headStyle != null) {
                cellX.setCellStyle(headStyle);
            }
            if (fieldWidth > 0) {
                sheet.setColumnWidth(i, fieldWidth);
                ifSetWidth = true;
            }
            cellX.setCellValue(String.valueOf(fieldName));
        }

        for (int dataIndex = 0; dataIndex < dataList.size(); dataIndex++) {
            int rowIndex = dataIndex + 1;
            Object rowData = dataList.get(dataIndex);

            Row rowX = sheet.createRow(rowIndex);

            for (int i = 0; i < fields.size(); i++) {
                Field field = fields.get(i);
                try {
                    field.setAccessible(true);
                    Object fieldValue = field.get(rowData);

                    String fieldValueString = FieldReflectionUtil.formatValue(field, fieldValue);

                    Cell cellX = rowX.createCell(i, CellType.STRING);
                    cellX.setCellValue(fieldValueString);
                } catch (IllegalAccessException e) {
                    log.error(e.getMessage(), e);
                    throw new RuntimeException(e);
                }
            }
        }

        if (!ifSetWidth) {
            for (int i = 0; i < fields.size(); i++) {
                sheet.autoSizeColumn((short) i);
            }
        }
    }
}
