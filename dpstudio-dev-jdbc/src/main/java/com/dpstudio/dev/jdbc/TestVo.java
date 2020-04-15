//package com.dpstudio.dev.jdbc;
//
//
//import com.dpstudio.dev.jdbc.annotation.Query;
//
///**
// * @Author: 徐建鹏.
// * @Date: 2020/4/10.
// * @Time: 3:30 下午.
// * @Description:
// */
//@Query(prefix = "juncheng_", from = Vote.TABLE_NAME, alias = "v")
//public class TestVo {
//
//    @FieldInfo(tableAlias = "v")
//    private String id;
//
//    @FieldInfo(tableAlias = "v", alias = "vName")
//    private String name;
//
//    @FieldInfo(tableAlias = "v", dbField = "join_time")
//    private String joinTime;
//
//    @FieldInfo(tableAlias = "c", dbField = "name")
//    private String categoryName;
//
//    @FieldInfo(tableAlias = "v", dbField = Vote.FIELDS.CATEGORY_ID)
//    @JoinInfo(joinWay = "inner", tableName = Category.TABLE_NAME, alias = "c", joinFiled = Category.FIELDS.ID, dbFiled = Vote.FIELDS.CATEGORY_ID)
//    private String categoryId;
//
//    @FieldInfo(tableAlias = "vr", dbField = VoteRecord.FIELDS.RESULT)
//    @JoinInfo(joinWay = "left", tableName = VoteRecord.TABLE_NAME, alias = "vr", joinFiled = VoteRecord.FIELDS.VOTE_ID, dbFiled = Vote.FIELDS.ID)
//    private String result;
//
//
//    public static void main(String[] args) {
//        int a = 1;
//        long b = 3L;
//        System.out.println(b-a);
//    }
//
//}
