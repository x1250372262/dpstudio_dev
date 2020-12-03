package ${packageName};

import net.ymate.platform.core.beans.annotation.PropertyState;
import net.ymate.platform.persistence.annotation.Comment;
import net.ymate.platform.persistence.annotation.Default;
import net.ymate.platform.persistence.annotation.Property;
import net.ymate.platform.persistence.annotation.Readonly;
import net.ymate.platform.persistence.base.IEntityPK;

/**
 * ${modelName?cap_first}PK generated By ModelGenerateImpl on ${lastUpdateTime?string("yyyy/MM/dd a HH:mm:ss")}
 *
 * @author 框架
 * @version 1.0
 */
@PK
public class ${modelName?cap_first}PK implements IEntityPK {

	private static final long serialVersionUID = 1L;

	<#list primaryKeyList as field><#if (field.columnName!"undefined") != "undefined">
	@Property(name = ${modelName?cap_first}.FIELDS.${field.columnName?upper_case}<#if (field.autoIncrement)>, autoincrement=true</#if><#if (field.nullable)>, nullable = false</#if><#if (!field.signed)>, unsigned = true</#if><#if (field.precision > 0)>, length = ${field.precision?string('#')}</#if><#if (field.scale > 0)>, decimals = ${field.scale}</#if>)<#if (field.defaultValue!"undefined") != "undefined">
	@Default("${field.defaultValue}")</#if><#if (field.remarks!"undefined") != "undefined">
	@Comment("${field.remarks}")</#if><#if (useStateSupport)>
	@PropertyState(propertyName = ${modelName?cap_first}.FIELDS.${field.columnName?upper_case})</#if><#if (field.readonly)>
	@Readonly</#if></#if>
    private ${field.varType} ${field.varName};

	</#list>

	/**
	 * 默认构造器
	 */
	public ${modelName?cap_first}PK() {
	}

	/**
	 * 构造器
	 <#list primaryKeyList as field>
	 *	@param ${field.varName}
	</#list>
	 */
	public ${modelName?cap_first}PK(<#list primaryKeyList as field>${field.varType} ${field.varName}<#if field_has_next>, </#if></#list>) {
		<#list primaryKeyList as field>
		this.${field.varName} = ${field.varName};
		</#list>
	}

	<#list primaryKeyList as field>

	/**
	 * @return the ${field.varName}
	 */
	public ${field.varType} get${field.varName?cap_first}() {
		return ${field.varName};
	}

	/**
	 * @param ${field.varName} the ${field.varName} to set
	 */
	public void set${field.varName?cap_first}(${field.varType} ${field.varName}) {
		this.${field.varName} = ${field.varName};
	}

	</#list>

}