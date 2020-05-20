package com.dpstudio.dev.excel.utils;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;

/**
 * @Author: 徐建鹏.
 * @Date: 2019-07-18.
 * @Time: 11:32.
 * @Description: excel工具类
 */
public class ExcleUtils {

    /**
     * 获取title信息
     *
     * @param cell
     * @return
     * @throws Exception
     */
    public static Object getTitle(Cell cell) throws Exception {
        Object value = null;
        if (cell != null) {
            switch (cell.getCellTypeEnum()) {
                case STRING:
                    value = cell.getStringCellValue();
                    break;
                case NUMERIC:
                    if (HSSFDateUtil.isCellDateFormatted(cell) && cell.getDateCellValue() != null) {
                        value = cell.getDateCellValue().getTime();
                    } else {
                        value = cell.getNumericCellValue();
                    }
                    break;
                case FORMULA:
                    value = cell.getCellFormula();
                    break;
                case BOOLEAN:
                    value = cell.getBooleanCellValue();
                    break;
                case BLANK:
                case ERROR:
                default:
                    value = "";
            }
        }
        return value;
    }

    /**
     * 判断行的内容是否为空
     *
     * @return
     */
    public static boolean isRowEmpty(Row row) {
        if (row != null) {
            for (int c = row.getFirstCellNum(); c < row.getLastCellNum(); c++) {
                Cell cell = row.getCell(c);
                if (cell != null && cell.getCellTypeEnum() != CellType.BLANK) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * 判断行的内容是否不为空
     *
     * @return
     */
    public static boolean isRowNotEmpty(Row row) {
        return !isRowEmpty(row);
    }

    /**
     * 读取exlceint内容
     *
     * @param cell
     * @return
     */
    public static Object readNumericCell(Cell cell) {
        Object result = null;
        double value = cell.getNumericCellValue();
        if (((int) value) == value) {
            result = (int) value;
        } else {
            result = value;
        }
        return result;
    }
}
