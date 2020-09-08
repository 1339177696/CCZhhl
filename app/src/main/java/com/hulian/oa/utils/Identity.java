package com.hulian.oa.utils;

/**
 * 作者：qgl 时间： 2020/6/28 10:00
 * Describe: 身份判断方法
 */
public class Identity {
    /**
     * id = 1-->普通员工（common）
     * id = 2-->各部门领导（eachLead）
     * id = 3-->综合管理领导（synthesizeLead）
     * id = 4-->总经理（大领导）（boss）
     * @param id
     * @return
     */
    public static int aa(String id){
        int i = 0;
        if (id.contains("boss")){
            i = 4;
        }else if (id.contains("synthesizeLead")){
            i = 3;
        }else if (id.contains("eachLead")){
            i = 2;
        }else if (id.contains("common")){
            i = 1;
        }
        return i;
    }
}
