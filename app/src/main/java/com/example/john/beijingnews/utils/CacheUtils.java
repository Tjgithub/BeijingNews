package com.example.john.beijingnews.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.john.beijingnews.activity.GuideActivity;

/**
 * Created by john on 2017/8/7.
 */

public class CacheUtils {

    //  得到缓存值
    public static boolean getBoolean(Context context, String key) {

        //  创建一个 文件 用于存储 是否进入过主界面的 标志位
        SharedPreferences sPreferences = context.getSharedPreferences(key, context.MODE_PRIVATE);  //  文件名  只有自己 app 可以使用文件内部内容

        return sPreferences.getBoolean(key, false);  // 默认值 为 false
    }

    //  保存软件参数
    public static void putBoolean(Context context, String key, boolean value) {
        SharedPreferences sPreferences = context.getSharedPreferences(key, Context.MODE_PRIVATE);

        //  sp.edit() 为 创建文本文件， putBoolean() 为添加键值对， commit 为 提交，上面那句 getSharedPreference() 并没有创建文件
        sPreferences.edit().putBoolean(key, value).commit();
    }
}
