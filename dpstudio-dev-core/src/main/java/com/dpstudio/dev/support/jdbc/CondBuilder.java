package com.dpstudio.dev.support.jdbc;

import cn.hutool.core.util.StrUtil;
import com.dpstudio.dev.support.jdbc.annotation.CondConf;
import net.ymate.platform.commons.lang.BlurObject;
import net.ymate.platform.persistence.jdbc.query.Cond;

import java.lang.reflect.Field;
import java.util.Map;

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


    public Cond build(Object condBean) throws Exception {
        Class<?> clazz = condBean.getClass();
        if (clazz.isAnnotationPresent(com.dpstudio.dev.support.jdbc.annotation.CondBuilder.class)) {
            Field[] fields = clazz.getDeclaredFields();
            if (fields.length <= 0) {
                return condTarget;
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
                                condTarget.exprNotEmpty(value, cond -> cond.and().likeWrap(condConf.prefix(), condConf.field()).param(StrUtil.format(condConf.likeExpression(), value)));
                            } else {
                                condTarget.and().likeWrap(condConf.prefix(), condConf.field()).param(StrUtil.format(condConf.likeExpression(), value));
                            }
                            break;
                        default:
                            break;
                    }
                }
            }
        }
        return condTarget;
    }

    public interface CallBack {
        Map<String, Object> handle(Cond condTarget, String prefix, String field, Cond.OPT opt, boolean exprNotEmpty, String likeExpression, Object value);
    }

    public Cond build(Object condBean, CallBack callBack) throws Exception {
        Class<?> clazz = condBean.getClass();
        if (clazz.isAnnotationPresent(com.dpstudio.dev.support.jdbc.annotation.CondBuilder.class)) {
            Field[] fields = clazz.getDeclaredFields();
            if (fields.length <= 0) {
                return condTarget;
            }
            for (Field field : fields) {
                if (field.isAnnotationPresent(CondConf.class)) {
                    field.setAccessible(true);
                    Object value = field.get(condBean);
                    CondConf condConf = field.getAnnotation(CondConf.class);
                    Map<String, Object> result = callBack.handle(condTarget, condConf.prefix(), condConf.field(), condConf.opt(), condConf.exprNotEmpty(), condConf.likeExpression(), value);
                    String boolKey = condConf.prefix() + condConf.field() + "bool";
                    String condKey = condConf.prefix() + condConf.field() + "cond";
                    boolean condFlag = BlurObject.bind(result.get(boolKey)).toBooleanValue();
                    switch (condConf.opt()) {
                        case EQ:
                            if (condFlag) {
                                condTarget = (Cond) result.get(condKey);
                            } else {
                                if (condConf.exprNotEmpty()) {
                                    condTarget.exprNotEmpty(value, cond -> cond.and().eqWrap(condConf.prefix(), condConf.field()).param(value));
                                } else {
                                    condTarget.and().eqWrap(condConf.prefix(), condConf.field()).param(value);
                                }
                            }
                            break;
                        case NOT_EQ:
                            if (condFlag) {
                                condTarget = (Cond) result.get(condKey);
                            } else {
                                if (condConf.exprNotEmpty()) {
                                    condTarget.exprNotEmpty(value, cond -> cond.and().notEqWrap(condConf.prefix(), condConf.field()).param(value));
                                } else {
                                    condTarget.and().notEqWrap(condConf.prefix(), condConf.field()).param(value);
                                }
                            }
                            break;
                        case LT:
                            if (condFlag) {
                                condTarget = (Cond) result.get(condKey);
                            } else {
                                if (condConf.exprNotEmpty()) {
                                    condTarget.exprNotEmpty(value, cond -> cond.and().ltWrap(condConf.prefix(), condConf.field()).param(value));
                                } else {
                                    condTarget.and().ltWrap(condConf.prefix(), condConf.field()).param(value);
                                }
                            }
                            break;
                        case GT:
                            if (condFlag) {
                                condTarget = (Cond) result.get(condKey);
                            } else {
                                if (condConf.exprNotEmpty()) {
                                    condTarget.exprNotEmpty(value, cond -> cond.and().gtWrap(condConf.prefix(), condConf.field()).param(value));
                                } else {
                                    condTarget.and().gtWrap(condConf.prefix(), condConf.field()).param(value);
                                }
                            }
                            break;
                        case LT_EQ:
                            if (condFlag) {
                                condTarget = (Cond) result.get(condKey);
                            } else {
                                if (condConf.exprNotEmpty()) {
                                    condTarget.exprNotEmpty(value, cond -> cond.and().ltEqWrap(condConf.prefix(), condConf.field()).param(value));
                                } else {
                                    condTarget.and().ltEqWrap(condConf.prefix(), condConf.field()).param(value);
                                }
                            }
                            break;
                        case GT_EQ:
                            if (condFlag) {
                                condTarget = (Cond) result.get(condKey);
                            } else {
                                if (condConf.exprNotEmpty()) {
                                    condTarget.exprNotEmpty(value, cond -> cond.and().gtEqWrap(condConf.prefix(), condConf.field()).param(value));
                                } else {
                                    condTarget.and().gtEqWrap(condConf.prefix(), condConf.field()).param(value);
                                }
                            }
                            break;
                        case LIKE:
                            if (condFlag) {
                                condTarget = (Cond) result.get(condKey);
                            } else {
                                if (condConf.exprNotEmpty()) {
                                    condTarget.exprNotEmpty(value, cond -> cond.and().likeWrap(condConf.prefix(), condConf.field()).param(StrUtil.format(condConf.likeExpression(), value)));
                                } else {
                                    condTarget.and().likeWrap(condConf.prefix(), condConf.field()).param(StrUtil.format(condConf.likeExpression(), value));
                                }
                            }
                            break;
                        default:
                            break;
                    }
                }
            }
        }
        return condTarget;
    }
}
