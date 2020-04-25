package com.dpstudio.dev.excel.analysis.impl;

import com.dpstudio.dev.excel.analysis.ISheetHandler;
import com.dpstudio.dev.excel.analysis.annotation.Excle;
import com.dpstudio.dev.excel.analysis.annotation.ImportColumn;
import com.dpstudio.dev.excel.analysis.annotation.Validate;
import com.dpstudio.dev.excel.analysis.bean.*;
import com.dpstudio.dev.excel.exception.ExcleException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.dpstudio.dev.excel.utils.ExcleUtils.getTitle;
import static com.dpstudio.dev.excel.utils.ExcleUtils.isRowNotEmpty;


/**
 * @Author: 徐建鹏.
 * @Date: 2019-07-10.
 * @Time: 17:01.
 * @Description: 默认excle导入处理实现
 */
public class DefaultSheetHandler implements ISheetHandler {

    /**
     * exlce取值方式
     */
    private Excle.TYPE type;

    /**
     * vo对象class
     */
    private Class cls;

    public DefaultSheetHandler create(Class cls) throws ExcleException {
        this.cls = cls;
        Excle excle = (Excle) cls.getAnnotation(Excle.class);
        if (excle == null) {
            throw new ExcleException("vo对象未包含Excle注解");
        }
        this.type = excle.type();
        return this;
    }

    private Map<Object, Field> getFilesMap() throws Exception {
        Field[] fields = cls.getDeclaredFields();
        Map<Object, Field> fieldMap = new HashMap<>();
        for (Field field : fields) {
            ImportColumn importColumn = field.getAnnotation(ImportColumn.class);
            if (importColumn != null) {
                switch (type) {
                    case IDX:
                        fieldMap.put(importColumn.idx(), field);
                        break;
                    case TITLE:
                        fieldMap.put(importColumn.title(), field);
                        break;
                    default:
                        break;
                }
            }
        }
        return fieldMap;
    }


    /**
     * 读取单元格内容
     *
     * @param field
     * @param cell
     * @return
     * @throws Exception
     */
    private CellResult parseCell(Field field, Cell cell, String title) throws Exception {
        ImportColumn importColumn = field.getAnnotation(ImportColumn.class);
        if (importColumn == null) {
            throw new ExcleException("vo类注解错误");
        }
        HandlerBean importHandlerBean = HandlerBean.create(importColumn);
        Validate validate = field.getAnnotation(Validate.class);
        ValidateBean validateBean = null;
        if (validate != null) {
            validateBean = ValidateBean.create(validate);
        }
        if (cell == null) {
            return CellHandler.handleBlank(validateBean, field, cell, title);
        }
        CellResult cellResult = new CellResult();
        switch (cell.getCellTypeEnum()) {
            case STRING:
                cellResult = CellHandler.handleString(importHandlerBean, validateBean, cell, title);
                break;
            case NUMERIC:
                cellResult = CellHandler.handleNumeric(importColumn, importHandlerBean, validateBean, field, cell, title);
                break;
            case BLANK:
                cellResult = CellHandler.handleBlank(validateBean, field, cell, title);
                break;
            default:
                break;
        }
        return cellResult;
    }


    @Override
    public ResultBean handle(Sheet sheet) throws Exception {

        ResultBean resultBean = new ResultBean();

        //Vo数据
        List<Object> result = new ArrayList<>();
        //错误信息
        List<ErrorInfo> errorInfoList = new ArrayList<>();

        /**
         * 字段信息
         */
        Map<Object, Field> fieldMap = getFilesMap();

        for (int rowId = sheet.getFirstRowNum(); rowId <= sheet.getLastRowNum(); rowId++) {
            if (rowId != sheet.getFirstRowNum()) {
                Row _row = sheet.getRow(rowId);
                boolean isError = false;
                if (isRowNotEmpty(_row)) {
                    Object objectVo = cls.newInstance();
                    for (int _cellIdx = _row.getFirstCellNum(); _cellIdx <= _row.getLastCellNum(); _cellIdx++) {
                        Field field = fieldMap.get(_cellIdx);
                        Object title = getTitle(sheet.getRow(sheet.getFirstRowNum()).getCell(_cellIdx));
                        if (type.equals(Excle.TYPE.TITLE)) {
                            field = fieldMap.get(title);
                        }
                        if (field != null) {
                            CellResult cellResult = parseCell(field, _row.getCell(_cellIdx), String.valueOf(title));
                            if (cellResult.getErrorInfo() != null) {
                                errorInfoList.add(cellResult.getErrorInfo());
                                isError = true;
                                continue;
                            } else {
                                //对私有字段的访问取消权限检查。暴力访问。
                                field.setAccessible(true);
                                field.set(objectVo, cellResult.getValue());
                            }
                        }
                    }
                    if (!isError) {
                        result.add(objectVo);
                    }
                }
            }
        }
        resultBean.setResultData(result);
        resultBean.setErrorInfoList(errorInfoList);
        return resultBean;
    }
}