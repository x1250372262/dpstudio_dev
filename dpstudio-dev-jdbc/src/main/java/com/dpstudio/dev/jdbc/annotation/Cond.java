package com.dpstudio.dev.jdbc.annotation;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Documented
public @interface Cond {

    String tableAlias() default "";

    String paramName() default "";

    String dbFiled() default "";

    OPT opt();


    enum OPT {
        EQ("="),
        NOT_EQ("!="),
        LT("<"),
        GT(">"),
        LT_EQ("<="),
        GT_EQ(">="),
        LIKE("LIKE");

        private final String __opt;

        OPT(String opt) {
            this.__opt = opt;
        }

        @Override
        public String toString() {
            return __opt;
        }
    }
}