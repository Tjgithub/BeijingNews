package com.example.john.beijingnews;

import android.app.Application;

import org.xutils.x;


/**
 *  Created by john on 2017/8/11.
 *  代表整个软件
 */

public class BeijingNewsApplication extends Application {

    /**
     * 所有组件被创建之前执行
     */
    @Override
    public void onCreate() {
        super.onCreate();

        x.Ext.setDebug(true);
        x.Ext.init(this);
    }
}
