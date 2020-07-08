package com.dpstudio.dev.doc.utils;


import com.dpstudio.dev.doc.tag.AbstractDocTag;

import java.util.ArrayList;
import java.util.List;

/**
 * @author mengxiang
 * @Date 2020.01.02.
 * @Time: 14:30.
 * @Description:
 */
public class TagUtils {

    /**
     * 查找List里面tag名称符合的第一个Tag信息
     *
     * @param list Tag集合
     * @param name DocTag.name, 如@return
     * @return 符合的第一个Tag信息, 如果没有则返回null
     */
    public static AbstractDocTag<?> findTag(List<AbstractDocTag<?>> list, String name) {
        for (AbstractDocTag<?> docTag : list) {
            if (docTag.getTagName().equals(name)) {
                return docTag;
            }
        }
        return null;
    }

    /**
     * 查找List里面tag名称符合的多个Tag信息
     *
     * @param list Tag集合
     * @param name DocTag.name, 如@param
     * @return 符合的所有Tag信息, 如果没有则返回size=0的List
     */
    public static List<AbstractDocTag<?>> findTags(List<AbstractDocTag<?>> list, String name) {
        List<AbstractDocTag<?>> docTags = new ArrayList<>();
        for (AbstractDocTag<?> docTag : list) {
            if (docTag.getTagName().equals(name)) {
                docTags.add(docTag);
            }
        }
        return docTags;
    }

}
