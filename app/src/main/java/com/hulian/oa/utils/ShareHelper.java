package com.hulian.oa.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * 一般情况下，一个轻量级数据保存就够了
 * 如果数据量大的话直接就用数据库了
 * 所以这里我改用了单例模式
 * Created by Lyan on 17/2/10.
 */

public class ShareHelper {

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private static final String ERROR_RESULT = "错误查询";
    private static Context mContext;

    private ShareHelper() {
        sharedPreferences = mContext.getSharedPreferences(mContext.getPackageName(), Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    private static class SingleTon {
        public static ShareHelper instance = new ShareHelper();
    }

    /**
     * 初始化
     *
     * @return
     */
    public static void init(Context context) {
        mContext = context.getApplicationContext();
    }

    /**
     * 获取单例
     *
     * @return
     */
    public static ShareHelper getInstance() {
        return SingleTon.instance;
    }

    /**
     * 查询数据
     *
     * @param key
     * @param defaultValues
     * @return
     */
    public Object query(String key, Object defaultValues) {
        if (defaultValues instanceof String) {
            return sharedPreferences.getString(key, (String) defaultValues);
        } else if (defaultValues instanceof Integer) {
            return sharedPreferences.getInt(key, (Integer) defaultValues);
        } else if (defaultValues instanceof Boolean) {
            return sharedPreferences.getBoolean(key, (Boolean) defaultValues);
        } else if (defaultValues instanceof Float) {
            return sharedPreferences.getFloat(key, (Float) defaultValues);
        } else if (defaultValues instanceof Long) {
            return sharedPreferences.getLong(key, (Long) defaultValues);
        } else {
            return ERROR_RESULT;
        }
    }
    /**
     * 插入数据或者修改数据
     *
     * @param key
     * @param values
     * @return
     */
    public ShareHelper save(String key, Object values) {
        if (values instanceof String) {
            editor.putString(key, (String) values);
        } else if (values instanceof Integer) {
            editor.putInt(key, (Integer) values);
        } else if (values instanceof Boolean) {
            editor.putBoolean(key, (Boolean) values);
        } else if (values instanceof Float) {
            editor.putFloat(key, (Float) values);
        } else if (values instanceof Long) {
            editor.putLong(key, (Long) values);
        }
        return this;
    }

    /**
     * 删除指定数据
     *
     * @param key
     * @return
     */
    public ShareHelper remove(String key) {
        editor.remove(key);
        return this;
    }

    /**
     * 清空数据
     *
     * @returnll
     */
    public ShareHelper removeAll() {
        editor.clear();
        return this;
    }

    /**
     * 提交
     *
     * @return
     */
    public boolean commit() {
        return editor.commit();
    }
}
