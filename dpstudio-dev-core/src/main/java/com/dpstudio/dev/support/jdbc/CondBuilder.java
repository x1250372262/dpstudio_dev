package com.dpstudio.dev.support.jdbc;

import cn.hutool.core.util.StrUtil;
import com.dpstudio.dev.support.jdbc.annotation.CondConf;
import net.ymate.platform.persistence.jdbc.query.Cond;

import java.lang.reflect.Field;

/**
 * @Author: mengxiang.
 * @create: 2021-02-01 09:26
 * @Description:
 */
public class CondBuilder {

    private static Cond condTarget;

    private CondBuilder(boolean eqOne) {
        if (eqOne) {
            condTarget = Cond.create().eqOne();
        } else {
            condTarget = Cond.create();
        }
    }

    public static CondBuilder create(boolean eqOne) {
        return new CondBuilder(eqOne);
    }


    public CondBuilder build(Object condBean) throws Exception {
        Class<?> clazz = condBean.getClass();
        if (clazz.isAnnotationPresent(com.dpstudio.dev.support.jdbc.annotation.CondBuilder.class)) {
            Field[] fields = clazz.getDeclaredFields();
            if (fields.length <= 0) {
                return this;
            }
            for (Field field : fields) {
                if (field.isAnnotationPresent(CondConf.class)) {
                    field.setAccessible(true);
                    Object value = field.get(condBean);
                    CondConf condConf = field.getAnnotation(CondConf.class);
                    switch (condConf.opt()) {
                        case EQ:
                            if (condConf.exprNotEmpty()) {
                                condTarget.exprNotEmpty(value, cond -> cond.and().eqWrap(condConf.prefix(), condConf.field()).param(value));
                            } else {
                                condTarget.and().eqWrap(condConf.prefix(), condConf.field()).param(value);
                            }
                            break;
                        case NOT_EQ:
                            if (condConf.exprNotEmpty()) {
                                condTarget.exprNotEmpty(value, cond -> cond.and().notEqWrap(condConf.prefix(), condConf.field()).param(value));
                            } else {
                                condTarget.and().notEqWrap(condConf.prefix(), condConf.field()).param(value);
                            }
                            break;
                        case LT:
                            if (condConf.exprNotEmpty()) {
                                condTarget.exprNotEmpty(value, cond -> cond.and().ltWrap(condConf.prefix(), condConf.field()).param(value));
                            } else {
                                condTarget.and().ltWrap(condConf.prefix(), condConf.field()).param(value);
                            }
                            break;
                        case GT:
                            if (condConf.exprNotEmpty()) {
                                condTarget.exprNotEmpty(value, cond -> cond.and().gtWrap(condConf.prefix(), condConf.field()).param(value));
                            } else {
                                condTarget.and().gtWrap(condConf.prefix(), condConf.field()).param(value);
                            }
                            break;
                        case LT_EQ:
                            if (condConf.exprNotEmpty()) {
                                condTarget.exprNotEmpty(value, cond -> cond.and().ltEqWrap(condConf.prefix(), condConf.field()).param(value));
                            } else {
                                condTarget.and().ltEqWrap(condConf.prefix(), condConf.field()).param(value);
                            }
                            break;
                        case GT_EQ:
                            if (condConf.exprNotEmpty()) {
                                condTarget.exprNotEmpty(value, cond -> cond.and().gtEqWrap(condConf.prefix(), condConf.field()).param(value));
                            } else {
                                condTarget.and().gtEqWrap(condConf.prefix(), condConf.field()).param(value);
                            }
                            break;
                        case LIKE:
                            if (condConf.exprNotEmpty()) {
                                condTarget.exprNotEmpty(value, cond -> cond.and().likeWrap(condConf.prefix(), condConf.field()).param(StrUtil.format("%{}%", value)));
                            } else {
                                condTarget.and().likeWrap(condConf.prefix(), condConf.field()).param(StrUtil.format("%{}%", value));
                            }
                            break;
                        default:
                            break;
                    }
                }
            }
        }
        return this;
    }


    public CondBuilder build(String prefix, String field, Cond.OPT opt, Object param, boolean exprNotEmpty) throws Exception {
        switch (opt) {
            case EQ:
                if (exprNotEmpty) {
                    condTarget.exprNotEmpty(param, cond -> cond.and().eqWrap(prefix, field).param(param));
                } else {
                    condTarget.and().eqWrap(prefix, field).param(param);
                }
                break;
            case NOT_EQ:
                if (exprNotEmpty) {
                    condTarget.exprNotEmpty(param, cond -> cond.and().notEqWrap(prefix, field).param(param));
                } else {
                    condTarget.and().notEqWrap(prefix, field).param(param);
                }
                break;
            case LT:
                if (exprNotEmpty) {
                    condTarget.exprNotEmpty(param, cond -> cond.and().ltWrap(prefix, field).param(param));
                } else {
                    condTarget.and().ltWrap(prefix, field).param(param);
                }
                break;
            case GT:
                if (exprNotEmpty) {
                    condTarget.exprNotEmpty(param, cond -> cond.and().gtWrap(prefix, field).param(param));
                } else {
                    condTarget.and().gtWrap(prefix, field).param(param);
                }
                break;
            case LT_EQ:
                if (exprNotEmpty) {
                    condTarget.exprNotEmpty(param, cond -> cond.and().ltEqWrap(prefix, field).param(param));
                } else {
                    condTarget.and().ltEqWrap(prefix, field).param(param);
                }
                break;
            case GT_EQ:
                if (exprNotEmpty) {
                    condTarget.exprNotEmpty(param, cond -> cond.and().gtEqWrap(prefix, field).param(param));
                } else {
                    condTarget.and().gtEqWrap(prefix, field).param(param);
                }
                break;
            case LIKE:
                if (exprNotEmpty) {
                    condTarget.exprNotEmpty(param, cond -> cond.and().likeWrap(prefix, field).param(StrUtil.format("%{}%", param)));
                } else {
                    condTarget.and().likeWrap(prefix, field).param(StrUtil.format("%{}%", param));
                }
                break;
            default:
                break;
        }
        return this;
    }


    public CondBuilder build(String field, Cond.OPT opt, Object param, boolean exprNotEmpty) throws Exception {
        switch (opt) {
            case EQ:
                if (exprNotEmpty) {
                    condTarget.exprNotEmpty(param, cond -> cond.and().eqWrap(field).param(param));
                } else {
                    condTarget.and().eqWrap(field).param(param);
                }
                break;
            case NOT_EQ:
                if (exprNotEmpty) {
                    condTarget.exprNotEmpty(param, cond -> cond.and().notEqWrap(field).param(param));
                } else {
                    condTarget.and().notEqWrap(field).param(param);
                }
                break;
            case LT:
                if (exprNotEmpty) {
                    condTarget.exprNotEmpty(param, cond -> cond.and().ltWrap(field).param(param));
                } else {
                    condTarget.and().ltWrap(field).param(param);
                }
                break;
            case GT:
                if (exprNotEmpty) {
                    condTarget.exprNotEmpty(param, cond -> cond.and().gtWrap(field).param(param));
                } else {
                    condTarget.and().gtWrap(field).param(param);
                }
                break;
            case LT_EQ:
                if (exprNotEmpty) {
                    condTarget.exprNotEmpty(param, cond -> cond.and().ltEqWrap(field).param(param));
                } else {
                    condTarget.and().ltEqWrap(field).param(param);
                }
                break;
            case GT_EQ:
                if (exprNotEmpty) {
                    condTarget.exprNotEmpty(param, cond -> cond.and().gtEqWrap(field).param(param));
                } else {
                    condTarget.and().gtEqWrap(field).param(param);
                }
                break;
            case LIKE:
                if (exprNotEmpty) {
                    condTarget.exprNotEmpty(param, cond -> cond.and().likeWrap(field).param(StrUtil.format("%{}%", param)));
                } else {
                    condTarget.and().likeWrap(field).param(StrUtil.format("%{}%", param));
                }
                break;
            default:
                break;
        }
        return this;
    }

    public Cond cond(){
        return condTarget;
    }
}
