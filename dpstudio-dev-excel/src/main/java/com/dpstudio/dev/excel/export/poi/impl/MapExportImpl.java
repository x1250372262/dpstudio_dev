package com.dpstudio.dev.excel.export.poi.impl;

import com.dpstudio.dev.excel.export.poi.IPoiExport;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

/**
 * @Author: 徐建鹏.
 * @Date: 2019/8/31.
 * @Time: 5:23 下午.
 * @Description:
 */
public class MapExportImpl implements IPoiExport {

    public void exportExcel(String sheetName, List<String> headersName, List<String> headersId,
                            List<Map<String, Object>> dtoList) {
                 /*
          （一）表头--标题栏
          */
        Map<Integer, String> headersNameMap = new HashMap<>();
        int key = 0;
        for (int i = 0; i < headersName.size(); i++) {
            if (!headersName.get(i).equals(null)) {
                headersNameMap.put(key, headersName.get(i));
                key++;
            }
        }
                 /*
           （二）字段---标题对应的字段
           */
        Map<Integer, String> titleFieldMap = new HashMap<>();
        int value = 0;
        for (int i = 0; i < headersId.size(); i++) {
            if (!headersId.get(i).equals(null)) {
                titleFieldMap.put(value, headersId.get(i));
                value++;
            }
        }
                /*
           (三）声明一个工作薄：包括构建工作簿、表格、样式
         */
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet(sheetName);
        sheet.setDefaultColumnWidth(15);
        // 生成一个样式
        HSSFCellStyle style = wb.createCellStyle();
        HSSFRow row = sheet.createRow(0);
        HSSFCell cell;
        cell = row.createCell(0);
        cell.setCellValue("学生基本信息表Map");
        cell.setCellStyle(style);
        //CellRangeAddress(int firstRow, int lastRow, int firstCol, int lastCol)
        wb.getSheet(sheetName).addMergedRegion(new CellRangeAddress(0, 0, 0, headersNameMap.size() - 1));
        //创建列标题栏所在行
        row = sheet.createRow(1);
        //拿到表格所有标题的value的集合
        Collection c = headersNameMap.values();
        //表格标题的迭代器
        Iterator<String> headersNameIt = c.iterator();
        /*
            （四）导出数据：包括导出标题栏以及内容栏
          */
        //根据选择的字段生成表头--标题
        int size = 0;
        while (headersNameIt.hasNext()) {
            cell = row.createCell(size);
            cell.setCellValue(headersNameIt.next());
            cell.setCellStyle(style);
            size++;
        }
        //表格一行的字段的集合，以便拿到迭代器
        Collection headersFieldCo = titleFieldMap.values();
        //总记录的迭代器
        Iterator<Map<String, Object>> dtoMapIt = dtoList.iterator();
        int zdRow = 2;
        //记录的迭代器，遍历总记录
        while (dtoMapIt.hasNext()) {
            Map<String, Object> mapTemp = dtoMapIt.next();
            row = sheet.createRow(zdRow);
            zdRow++;
            int zdCell = 0;
            Iterator<String> headersFieldIt = headersFieldCo.iterator();
            while (headersFieldIt.hasNext()) {
                String tempField = headersFieldIt.next();
                if (mapTemp.get(tempField) != null) {
                    row.createCell(zdCell).setCellValue(String.valueOf(mapTemp.get(tempField)));
                    zdCell++;
                } else {
                    //字段内容值为null时，进行处理
                    row.createCell(zdCell).setCellValue("*");
                }
                zdCell++;
            }
        }
        //行列分组
        try {
            FileOutputStream exportXls = new FileOutputStream("E://工单信息表Map.xls");
            wb.write(exportXls);
            exportXls.close();
            System.out.println("导出成功!");
        } catch (FileNotFoundException e) {
            System.out.println("导出失败!");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("导出失败!");
            e.printStackTrace();
        }
    }
}
