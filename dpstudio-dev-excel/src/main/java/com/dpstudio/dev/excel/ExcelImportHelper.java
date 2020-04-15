package com.dpstudio.dev.excel;

import com.dpstudio.dev.excel.analysis.ISheetHandler;
import com.dpstudio.dev.excel.analysis.bean.ResultBean;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import java.io.*;

/**
 * @author 徐建鹏
 * @Date 2019.02.10.
 * @Time: 14:30.
 * @Description: Excel文件数据导入助手类
 */
public class ExcelImportHelper implements Closeable {

    private Workbook workbook;

    private String[] sheetNames;

    public static ExcelImportHelper bind(File file) throws IOException, InvalidFormatException {
        return new ExcelImportHelper(new FileInputStream(file));
    }

    public static ExcelImportHelper bind(InputStream inputStream) throws IOException, InvalidFormatException {
        return new ExcelImportHelper(inputStream);
    }

    private ExcelImportHelper(InputStream inputStream) throws IOException, InvalidFormatException {
        workbook = WorkbookFactory.create(inputStream);
        sheetNames = new String[workbook.getNumberOfSheets()];
        for (int idx = 0; idx < sheetNames.length; idx++) {
            sheetNames[idx] = workbook.getSheetName(idx);
        }
    }

    /**
     * @return 返回SHEET名称集合
     */
    public String[] getSheetNames() {
        return sheetNames;
    }

    public ResultBean openSheet(int sheetIdx, ISheetHandler handler) throws Exception {
        Sheet sheet = workbook.getSheetAt(sheetIdx);
        return handler.handle(sheet);
    }

    public ResultBean openSheet(String sheetName, ISheetHandler handler) throws Exception {
        Sheet sheet = workbook.getSheet(sheetName);
        return handler.handle(sheet);
    }

    @Override
    public void close() throws IOException {
        if (workbook != null) {
            workbook.close();
        }
    }


}
