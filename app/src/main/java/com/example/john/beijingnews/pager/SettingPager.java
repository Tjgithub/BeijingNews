package com.example.john.beijingnews.pager;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.TextView;

import com.example.john.beijingnews.base.BasePager;

public class SettingPager extends BasePager {


    public SettingPager(Context context) {
        super(context);
    }

    @Override
    public void initData() {
        super.initData();

        //1.  设置标题
        tv_title.setText("设置页面");

        //2.  联网请求  得到数据   创建视图
        TextView textView = new TextView(context);
        textView.setGravity(Gravity.CENTER);
        textView.setTextColor(Color.RED);
        textView.setTextSize(25);

        //  3.  把子视图添加到 BasePager 的 FrameLayout 中
        fl_content.addView(textView);

        textView.setText("设置内容");
    }
}
