package com.example.john.beijingnews.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.example.john.beijingnews.base.BasePager;

import java.util.ArrayList;

/**
 *  Created by john on 2017/8/12.
 */

public class ContentFragmentAdapter extends PagerAdapter {

    private ArrayList<BasePager> basePagers;

    public ContentFragmentAdapter(ArrayList<BasePager> basePagers){
        this.basePagers = basePagers;
    }

    @Override
    public int getCount() {
        return basePagers.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        BasePager basePager = basePagers.get(position);
        //  得到各个子页面的 view
        View rootView = basePager.rootView;
        //  初始化 数据
        //basePager.initData();

        //  添加到 ViewPager
        container.addView(rootView);

        return rootView;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

}
