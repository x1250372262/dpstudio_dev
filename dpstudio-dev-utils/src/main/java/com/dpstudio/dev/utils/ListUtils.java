package com.dpstudio.dev.utils;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

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
    public Boolean checkSame(List<?> bigResult, List<?> smallResult) {
        return bigResult.containsAll(smallResult);
    }

    /**
     * 去掉包含的集合 没有重复 返回大集合
     *
     * @param bigResult   大集合
     * @param smallResult 小集合
     * @return
     */
    public List<?> doRemove(List<?> bigResult, List<?> smallResult) {
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

}
