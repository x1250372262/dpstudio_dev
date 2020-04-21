package com.dpstudio.dev.utils;

import java.util.*;

/**
 * @Author: 徐建鹏.
 * @Date: 2018/8/21.
 * @Time: 下午3:36.
 * @Description: 集合工具类
 */
public class ListUtils {

    /**
     * 判断集合是否包含另一个集合
     *
     * @param bigResult   大集合
     * @param smallResult 小集合
     * @return
     */
    public static Boolean checkSame(List<?> bigResult, List<?> smallResult) {
        return bigResult.containsAll(smallResult);
    }

    /**
     * 去掉包含的集合 没有重复 返回大集合
     *
     * @param bigResult   大集合
     * @param smallResult 小集合
     * @return
     */
    public static List<?> doRemove(List<?> bigResult, List<?> smallResult) {
        if (checkSame(bigResult, smallResult)) {
            LinkedList<?> bigResultLink = new LinkedList<>(bigResult);
            HashSet<?> smallResultLink = new HashSet<>(smallResult);
            Iterator<?> iter = bigResultLink.iterator();

            while (iter.hasNext()) {
                if (smallResultLink.contains(iter.next())) {
                    iter.remove();
                }
            }
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
        Map<K, List<V>> resultMap = new LinkedHashMap<K, List<V>>();
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
        K groupBy(V row);
    }


    /**
     * 根据数量分组
     *
     * @param list
     * @param quantity
     * @return
     */
    public static List<Map<String, List<Object>>> groupByQuantityMap(List<?> list, int quantity) {
        List<Map<String, List<Object>>> mapList = new ArrayList<>();
        if (list == null || list.size() == 0) {
            return mapList;
        }
        if (quantity <= 0) {
            throw new IllegalArgumentException("Wrong quantity.");
        }

        int count = 0;
        int key = 0;
        while (count < list.size()) {
            Map<String,  List<Object>> map = new HashMap<>();
            List<Object> wrapList = (List<Object>) list.subList(count, (count + quantity) > list.size() ? list.size() : count + quantity);
            map.put(key + "", wrapList);
            mapList.add(map);
            key++;
            count += quantity;
        }

        return mapList;
    }


    /**
     * 根据数量分组
     *
     * @param list
     * @param quantity
     * @return
     */
    public static List<List<Object>> groupByQuantity(List<?> list, int quantity) {
        List<List<Object>> mapList = new ArrayList<>();
        if (list == null || list.size() == 0) {
            return mapList;
        }
        if (quantity <= 0) {
            throw new IllegalArgumentException("Wrong quantity.");
        }

        int count = 0;
        int key = 0;
        while (count < list.size()) {
            List<Object> wrapList = (List<Object>) list.subList(count, (count + quantity) > list.size() ? list.size() : count + quantity);
            mapList.add(wrapList);
            key++;
            count += quantity;
        }

        return mapList;
    }

}
