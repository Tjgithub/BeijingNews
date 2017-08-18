package com.example.john.beijingnews.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 *  Created by john on 2017/8/12.
 *
 *  作用  屏蔽掉 ViewPager 左右滑动
 */

public class NoScrollViewPager extends ViewPager{

    public NoScrollViewPager(Context context) {
        super(context);
    }

    public NoScrollViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return true;
    }
}
