package ${servicePackageName}.impl;

import com.dpstudio.dev.core.CommonResult;
import com.dpstudio.dev.core.code.CommonCode;
import ${modelPackageName}.${modelName?cap_first};
import ${daoPackageName}.I${modelName?cap_first}Dao;
import ${servicePackageName}.I${modelName?cap_first}Service;
import net.ymate.platform.core.beans.annotation.Bean;
import net.ymate.platform.core.beans.annotation.Inject;
import net.ymate.platform.core.util.UUIDUtils;
import net.ymate.platform.persistence.IResultSet;
import java.util.List;
import java.util.ArrayList;

/**
* @Author: CurdGenerateImpl.
* @Date: ${lastUpdateTime?string("yyyy/MM/dd")}.
* @Time: ${lastUpdateTime?string("HH:mm:ss")}.
* @Description: ${modelName?cap_first}ServiceImpl generated By CurdGenerateImpl on ${lastUpdateTime?string("yyyy/MM/dd a HH:mm:ss")}
*/
@Bean
public class ${modelName?cap_first}ServiceImpl implements I${modelName?cap_first}Service {

    @Inject
    private I${modelName?cap_first}Dao i${modelName?cap_first}Dao;

    /**
    * 添加
    *
    <#list fieldList as field>
        <#if (field.varName!"id") != 'id'>
    * @param ${field.varName} ${field.remarks}
        </#if>
    </#list>
    * @return 添加成功的数据信息
    * @throws Exception 添加异常
    */
    @Override
    public CommonResult create(<#list fieldList as field><#if (field.varName!"id") != 'id'>${field.varType} ${field.varName}<#if field_has_next>, </#if></#if></#list>) throws Exception {
        ${modelName?cap_first} ${modelName?uncap_first}Bean = ${modelName?cap_first}.builder()
                                    .id(UUIDUtils.UUID())
                                <#list fieldList as field>
                                    <#if (field.varName!"id") != 'id'>
                                        .${field.varName}(${field.varName})
                                    </#if>
                                </#list>
                                    .build();
        ${modelName?uncap_first}Bean = i${modelName?cap_first}Dao.create(${modelName?uncap_first}Bean);
        return CommonResult.toResult(${modelName?uncap_first}Bean);
    }

    /**
    * 修改
    *
    <#list fieldList as field>
    * @param ${field.varName} ${field.remarks}
    </#list>
    * @return 修改成功的数据信息
    * @throws Exception 修改异常
    */
    @Override
    public CommonResult update(<#list fieldList as field>${field.varType} ${field.varName}<#if field_has_next>, </#if></#list>) throws Exception {
        ${modelName?cap_first} ${modelName?uncap_first}Bean = i${modelName?cap_first}Dao.findById(id);
        if (${modelName?uncap_first}Bean == null) {
            return CommonResult.create(CommonCode.COMMON_OPTION_NO_DATA.getCode())
                    .msg(CommonCode.COMMON_OPTION_NO_DATA.getMessage());
        }
        <#list fieldList as field>
            <#if (field.varName!"id") != 'id'>
                ${modelName?uncap_first}Bean.set${field.varName?cap_first}(${field.varName});
            </#if>
        </#list>
        ${modelName?uncap_first}Bean = i${modelName?cap_first}Dao.update(${modelName?uncap_first}Bean,<#list fieldList as field><#if (field.columnName!"ID") != 'ID'>${modelName?cap_first}.FIELDS.${field.columnName}<#if field_has_next>, </#if></#if></#list>);
        return CommonResult.toResult(${modelName?uncap_first}Bean);
    }

    /**
    * 删除
    *
    * @param ids 要删除的id数组
    * @throws Exception 删除异常
    */
    @Override
    public CommonResult delete(String[] ids) throws Exception {
        if (ids != null && ids.length > 0) {
            List<${modelName?cap_first}> ${modelName?uncap_first}List = new ArrayList<>();
            for (String id : ids) {
                ${modelName?uncap_first}List.add(${modelName?cap_first}.builder().id(id).build());
            }
            i${modelName?cap_first}Dao.delete(${modelName?uncap_first}List);
            return CommonResult.successResult();
        }
        return CommonResult.errorResult();
    }

    /**
    * 根据id查询
    *
    * @param id     数据id
    * @param fields 要查询哪些字段
    * @return 查询的数据
    * @throws Exception 查询异常
    */
    @Override
    public ${modelName?cap_first} findById(String id, String... fields) throws Exception {
        return i${modelName?cap_first}Dao.findById(id);
    }

    /**
    * 查询所有
    *
    <#list fieldList as field>
    *@param ${field.varName} ${field.remarks}
    </#list>
    * @param page     第几页
    * @param pageSize 每页显示多少条
    * @return 查询的数据
    * @throws Exception 查询异常
    */
    @Override
    public IResultSet<${modelName?cap_first}> findAll(<#list fieldList as field>${field.varType} ${field.varName}<#if field_has_next>, </#if></#list>, int page, int pageSize) throws Exception {
        return i${modelName?cap_first}Dao.findAll(<#list fieldList as field>${field.varName}<#if field_has_next>, </#if></#list>, page, pageSize);
    }

}
