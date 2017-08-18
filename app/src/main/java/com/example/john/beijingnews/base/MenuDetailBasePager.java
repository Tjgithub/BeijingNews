package com.example.john.beijingnews.base;

import android.content.Context;
import android.view.View;

import com.example.john.beijingnews.utils.LogUtil;

/**
 * Created by john on 2017/8/17.
 */

public abstract class MenuDetailBasePager {

    public final Context context;

    public View rootView;

    public MenuDetailBasePager(Context context){
        this.context = context;
        this.rootView = initView();
    }

    /**
     * 抽象方法， 强制孩子实现的方法， 每个页面实现不同的效果
     * @return
     */
    public abstract View initView();

    /**
     * 子页面需要绑定数据，联网请求数据的时候，重写方法
     */
    public void initData(){

    }
}
