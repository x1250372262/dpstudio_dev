package com.dpstudio.dev.doc.core.utils;


import com.dpstudio.dev.doc.core.tag.DocTag;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 徐建鹏
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
    public static DocTag findTag(List<DocTag> list, String name) {
        for (DocTag docTag : list) {
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
    public static List<DocTag> findTags(List<DocTag> list, String name) {
        List<DocTag> docTags = new ArrayList<>();
        for (DocTag docTag : list) {
            if (docTag.getTagName().equals(name)) {
                docTags.add(docTag);
            }
        }
        return docTags;
    }

}
