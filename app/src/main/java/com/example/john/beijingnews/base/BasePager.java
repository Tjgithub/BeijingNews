package com.example.john.beijingnews.base;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.john.beijingnews.R;
import com.example.john.beijingnews.activity.MainActivity;

import org.xutils.view.annotation.ViewInject;

/**
 *  Created by john on 2017/8/11.
 *  基类  或说 公共类
 *  子类   HomePager、NewsCenterPager、 SmartServicePager、GovaffairPager、SettingPager 都是继承 Basepager
 */

public class BasePager {

    public final Context context;  // MainActivity

    /**
     * 视图 代表各个不同的页面
     * @param context
     */
    public View rootView;

    /**
     * 显示标题栏
     */
    public TextView tv_title;

    /**
     * 点击侧滑  打开 侧滑 菜单栏
     */
    public ImageButton ib_menu;

    /**
     * 加载 各个子页面  FrameLayout
     */
    public FrameLayout fl_content;



    public BasePager(Context context){
        this.context = context;
        rootView = initView();
    }


    public View initView(){

        View view = View.inflate(context, R.layout.base_pager, null);

        tv_title = (TextView) view.findViewById(R.id.tv_title);
        fl_content = (FrameLayout) view.findViewById(R.id.fl_content);
        ib_menu = (ImageButton) view.findViewById(R.id.ib_menu);
        //  给 ib_menu 添加点击事件， 控制点击开关
        ib_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity mainActivity = (MainActivity) context;
                mainActivity.getSlidingMenu().toggle();
            }
        });

        return view;

    }


    //  加载数据
    public void initData(){

    }
}
