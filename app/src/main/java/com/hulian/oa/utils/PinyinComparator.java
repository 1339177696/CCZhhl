package com.hulian.oa.utils;

import com.hulian.oa.bean.SortModel;

import java.util.Comparator;

/**
 * 作者：qgl 时间： 2020/5/15 14:55
 * Describe:
 */
public class PinyinComparator implements Comparator<SortModel> {

    public int compare(SortModel o1, SortModel o2) {
        if (o1.getSortLetters().equals("@") || o2.getSortLetters().equals("#")) {
            return -1;
        } else if (o1.getSortLetters().equals("#")
                || o2.getSortLetters().equals("@")) {
            return 1;
        } else {
            return o1.getSortLetters().compareTo(o2.getSortLetters());
        }
    }

}
