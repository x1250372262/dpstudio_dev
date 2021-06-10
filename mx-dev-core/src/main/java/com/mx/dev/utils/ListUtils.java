package com.mx.dev.utils;

import java.util.*;

/**
 * @Author: mengxiang.
 * @Date: 2018/8/21.
 * @Time: 下午3:36.
 * @Description: 集合工具类
 */
public class ListUtils {

    /**
     * 判断集合是否为空
     */
    public static <T> boolean isEmpty(List<T> list) {
        return list == null || list.isEmpty();
    }

    /**
     * 判断集合是否不为空
     */
    public static <T> boolean isNotEmpty(List<T> list) {
        return !isEmpty(list);
    }

    /**
     * 判断集合是否包含另一个集合
     *
     * @param bigResult   大集合
     * @param smallResult 小集合
     */
    public static <T> boolean checkSame(List<T> bigResult, List<T> smallResult) {
        return bigResult.containsAll(smallResult);
    }

    /**
     * 去掉包含的集合 没有重复 返回大集合
     *
     * @param bigResult   大集合
     * @param smallResult 小集合
     */
    public static <T> List<T> doRemove(List<T> bigResult, List<T> smallResult) {
        if (checkSame(bigResult, smallResult)) {
            LinkedList<T> bigResultLink = new LinkedList<>(bigResult);
            HashSet<T> smallResultLink = new HashSet<>(smallResult);

            bigResultLink.removeIf(smallResultLink::contains);
            return bigResultLink;
        }
        return bigResult;

    }

    /**
     * list 集合分组
     *
     * @param list    待分组集合
     * @param groupBy 分组Key算法
     * @param <K>     分组Key类型
     * @param <V>     行数据类型
     * @return 分组后的Map集合
     */
    public static <K, V> Map<K, List<V>> groupBy(List<V> list, GroupBy<K, V> groupBy) {
        return groupBy((Collection<V>) list, groupBy);
    }

    /**
     * list 集合分组
     *
     * @param list    待分组集合
     * @param groupBy 分组Key算法
     * @param <K>     分组Key类型
     * @param <V>     行数据类型
     * @return 分组后的Map集合
     */
    public static <K, V> Map<K, List<V>> groupBy(Collection<V> list, GroupBy<K, V> groupBy) {
        Map<K, List<V>> resultMap = new LinkedHashMap<>();
        for (V e : list) {
            K k = groupBy.groupBy(e);
            if (resultMap.containsKey(k)) {
                resultMap.get(k).add(e);
            } else {
                List<V> tmp = new LinkedList<V>();
                tmp.add(e);
                resultMap.put(k, tmp);
            }
        }
        return resultMap;
    }

    /**
     * List分组
     *
     * @param <K> 返回分组Key
     * @param <V> 分组行
     */
    public interface GroupBy<K, V> {

        /**
         * 分组方法
         *
         * @param row
         * @return
         */
        K groupBy(V row);
    }


    /**
     * 根据数量分组
     *
     * @param list 集合
     * @param num  数量
     */
    @SuppressWarnings("unchecked")
    public static <T> List<Map<String, List<T>>> groupByNumMap(List<T> list, int num) {
        List<Map<String, List<T>>> mapList = new ArrayList<>();
        if (list == null || list.size() == 0) {
            return mapList;
        }
        if (num <= 0) {
            throw new RuntimeException("数量小于0");
        }

        int count = 0;
        int key = 0;
        while (count < list.size()) {
            Map<String, List<T>> map = new HashMap<>();
            List<T> wrapList = list.subList(count, Math.min((count + num), list.size()));
            map.put(key + "", wrapList);
            mapList.add(map);
            key++;
            count += num;
        }

        return mapList;
    }


    /**
     * 根据数量分组
     *
     * @param list 集合
     * @param num  数量
     */
    @SuppressWarnings("unchecked")
    public static <T> List<List<T>> groupByNum(List<T> list, int num) {
        List<List<T>> mapList = new ArrayList<>();
        if (list == null || list.size() == 0) {
            return mapList;
        }
        if (num <= 0) {
            throw new RuntimeException("数量小于0");
        }
        int count = 0;
        while (count < list.size()) {
            List<T> wrapList = list.subList(count, Math.min((count + num), list.size()));
            mapList.add(wrapList);
            count += num;
        }
        return mapList;
    }

    public static <T> List<T> createList(T object){
        List<T> returnValue = new ArrayList<>();
        returnValue.add(object);
        return returnValue;
    }
}
