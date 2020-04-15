package com.dpstudio.dev.jdbc;

import com.dpstudio.dev.jdbc.annotation.FieldInfo;
import com.dpstudio.dev.jdbc.annotation.JoinInfo;
import com.dpstudio.dev.jdbc.annotation.Query;
import com.dpstudio.dev.jdbc.exception.SqlException;
import net.ymate.platform.persistence.Fields;
import net.ymate.platform.persistence.jdbc.query.Cond;
import net.ymate.platform.persistence.jdbc.query.Join;
import net.ymate.platform.persistence.jdbc.query.Select;
import org.apache.commons.lang.StringUtils;

import java.lang.reflect.Field;

/**
 * @Author: 徐建鹏.
 * @Date: 2020/4/10.
 * @Time: 3:14 下午.
 * @Description:
 */
public class SqlHelper {

    private Class cls;

    private String prefix;

    private String from;
    private String alias;

    public SqlHelper create(Class cls) throws SqlException {
        this.cls = cls;
        Query select = (Query) cls.getAnnotation(Query.class);
        if (select == null) {
            throw new SqlException("vo对象不包含Query注解");
        }
        prefix = select.prefix();
        from = select.from();
        alias = select.alias();
        return this;
    }

    public Select select(){
        Field[] fieldList = cls.getDeclaredFields();
        Select select = Select.create(prefix,from,alias);
        Fields fields = Fields.create();
        for (Field field : fieldList) {
            FieldInfo fieldInfo = field.getAnnotation(FieldInfo.class);
            if(fieldInfo!=null){
                String dbFiled = StringUtils.defaultIfBlank(fieldInfo.dbField(),field.getName());
                if(StringUtils.isNotBlank(fieldInfo.filed())){
                    fields.add(fieldInfo.filed());
                } else{
                    fields.add(fieldInfo.tableAlias(),dbFiled,StringUtils.defaultIfBlank(fieldInfo.alias(),field.getName()));
                }
            }
            JoinInfo joinInfo = field.getAnnotation(JoinInfo.class);
            if(joinInfo!=null){
                Join join = null;
                if(joinInfo.joinWay().equals("left")){
                    join = Join.left(prefix,joinInfo.tableName()).alias(joinInfo.alias()).on(Cond.create().opt(alias,StringUtils.defaultIfBlank(joinInfo.dbFiled(),field.getName()), Cond.OPT.EQ,joinInfo.alias(),joinInfo.joinFiled()));
                }else if(joinInfo.joinWay().equals("right")){
                    join = Join.right(prefix,joinInfo.tableName()).alias(joinInfo.alias()).on(Cond.create().opt(alias,StringUtils.defaultIfBlank(joinInfo.dbFiled(),field.getName()), Cond.OPT.EQ,joinInfo.alias(),joinInfo.joinFiled()));
                }else if(joinInfo.joinWay().equals("inner")){
                    join = Join.inner(prefix,joinInfo.tableName()).alias(joinInfo.alias()).on(Cond.create().opt(alias,StringUtils.defaultIfBlank(joinInfo.dbFiled(),field.getName()), Cond.OPT.EQ,joinInfo.alias(),joinInfo.joinFiled()));
                }
                select.join(join);
            }
        }
        select.field(fields);

        return select;
    }
}
