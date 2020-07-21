package ${packageName};

import net.ymate.platform.core.beans.annotation.PropertyState;
import net.ymate.platform.persistence.annotation.Comment;
import net.ymate.platform.persistence.annotation.Default;
import net.ymate.platform.persistence.annotation.Entity;
import net.ymate.platform.persistence.annotation.Id;
import net.ymate.platform.persistence.annotation.Property;
import net.ymate.platform.persistence.jdbc.support.BaseEntity;
import net.ymate.platform.persistence.IShardingable;
import net.ymate.platform.persistence.jdbc.IConnectionHolder;
import org.apache.commons.lang.StringUtils;
import java.util.Scanner;

/**
* @Author: ModelGenerateImpl.
* @Date: ${lastUpdateTime?string("yyyy/MM/dd")}.
* @Time: ${lastUpdateTime?string("HH:mm:ss")}.
* @Description: ${modelName?cap_first} generated By ModelGenerateImpl on ${lastUpdateTime?string("yyyy/MM/dd a HH:mm:ss")}
*/
@Entity(${modelName?cap_first}.TABLE_NAME)
public class ${modelName?cap_first} extends BaseEntity<${modelName?cap_first}, ${primaryKeyType}>{

	private static final long serialVersionUID = 1L;

	<#list fieldList as field>
	<#if primaryKeyName = field.varName>@Id</#if><#if (field.columnName!"undefined") != "undefined">
	@Property(name = FIELDS.${field.columnName?upper_case}<#if (field.autoIncrement)>, autoincrement=true</#if><#if (!field.nullable)>, nullable = false</#if><#if (!field.signed)>, unsigned = true</#if><#if (field.precision > 0)>, length = ${field.precision?string('#')}</#if><#if (field.scale > 0)>, decimals = ${field.scale}</#if>)<#if (field.defaultValue!"undefined") != "undefined">
	@Default("${field.defaultValue}")</#if><#if (field.remarks!"undefined") != "undefined">
	@Comment("${field.remarks}")</#if>
    @PropertyState(propertyName = FIELDS.${field.columnName?upper_case})</#if>
	private ${field.varType} ${field.varName};
	</#list>

	/**
	 * 构造器
	 */
	public ${modelName?cap_first}() {
	}

    <#if (notNullableFieldList?size > 0)>
    /**
     * 构造器
    <#list notNullableFieldList as field>
     *	@param ${field.varName}
    </#list>
     */
    public ${modelName?cap_first}(<#list notNullableFieldList as field>${field.varType} ${field.varName}<#if field_has_next>, </#if></#list>) {
        <#list notNullableFieldList as field>
        this.${field.varName} = ${field.varName};
        </#list>
    }
    </#if>

	/**
	 * 构造器
	<#list fieldList as field>
	 *	@param ${field.varName}
	</#list>
	 */
	public ${modelName?cap_first}(<#list fieldList as field>${field.varType} ${field.varName}<#if field_has_next>, </#if></#list>) {
		<#list fieldList as field>
		this.${field.varName} = ${field.varName};
		</#list>
	}

    @Override
	public ${primaryKeyType} getId() {
		return ${primaryKeyName};
	}

    @Override
	public void setId(${primaryKeyType} id) {
		this.${primaryKeyName} = id;
	}

	<#list fieldList as field>
	<#if field.varName != 'id'>
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
	<#elseif field.varName != primaryKeyName>
    /**
     * @return the ${field.varName}
     */
    public ${field.varType} get_${field.varName?cap_first}() {
    	return ${field.varName};
    }

    /**
     * @param ${field.varName} the ${field.varName} to set
     */
    public void set_${field.varName?cap_first}(${field.varType} ${field.varName}) {
    	this.${field.varName} = ${field.varName};
    }
	</#if>

	</#list>

	public static ${modelName?cap_first}Builder builder() {
		return new ${modelName?cap_first}Builder();
	}

	public ${modelName?cap_first}Builder bind() {
    	return new ${modelName?cap_first}Builder(this);
    }

	public static class ${modelName?cap_first}Builder {

		private ${modelName?cap_first} _model;

		public ${modelName?cap_first}Builder() {
			_model = new ${modelName?cap_first}();
		}

		public ${modelName?cap_first}Builder(${modelName?cap_first} model) {
			_model = model;
		}

		public ${modelName?cap_first} build() {
			return _model;
		}

		public IConnectionHolder connectionHolder() {
			return _model.getConnectionHolder();
		}

		public ${modelName?cap_first}Builder connectionHolder(IConnectionHolder connectionHolder) {
			_model.setConnectionHolder(connectionHolder);
			return this;
		}

		public String dataSourceName() {
			return _model.getDataSourceName();
		}

		public ${modelName?cap_first}Builder dataSourceName(String dsName) {
			_model.setDataSourceName(dsName);
			return this;
		}

		public IShardingable shardingable() {
			return _model.getShardingable();
		}

		public ${modelName?cap_first}Builder shardingable(IShardingable shardingable) {
    		_model.setShardingable(shardingable);
    		return this;
    	}

	<#list fieldList as field>
		public ${field.varType} ${field.varName}() {
			return _model.get${field.varName?cap_first}();
		}

		public ${modelName?cap_first}Builder ${field.varName}(${field.varType} ${field.varName}) {
			_model.set${field.varName?cap_first}(${field.varName});
			return this;
		}

	</#list>
	}

	<#if (isOutGetSet)>
		/**
		* 生成set/set方法
		*/
		public static void outGetSet(){
		System.out.println("请输入变量:");
		Scanner scanner = new Scanner(System.in);
		String pre =  StringUtils.defaultIfBlank(scanner.nextLine(),"${modelName?uncap_first}");
		System.out.println("==========1.set方法常规==========");
		<#list fieldList as field>
		System.out.println(pre+".set${field.varName?cap_first}(${field.varName?uncap_first});");
		</#list>
		System.out.println("==========2.set方法链式调用==========");
		StringBuffer setSb = new StringBuffer("${modelName?cap_first}.builder()");
		<#list fieldList as field>
		setSb.append(".${field.varName?uncap_first}(${field.varName?uncap_first})");
		</#list>
		setSb.append(".build();");
		System.out.println(setSb.toString());
		System.out.println("==========1.get方法常规==========");
		<#list fieldList as field>
		System.out.println(pre+".get${field.varName?cap_first}();");
		</#list>
		}
	</#if>

	/**
	 * ${modelName?cap_first} 字段常量表
	 */
	public class FIELDS {
	<#list allFieldList as field>
		public static final ${field.varType} ${field.varName} = "${field.columnName}";
	</#list>
	}

	public static final String TABLE_NAME = "${tableName}";

}