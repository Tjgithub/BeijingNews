package com.example.john.beijingnews.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 *  Created by john on 2017/8/9.
 *  作用 : 基本的 Fragment， leftmenuFragemnt, ContentFragment 类 继承 BaseFragment
 */

public abstract class BaseFragment extends Fragment {

    public Activity context;  // 实例对象就是 MainActivity


    /**
     * 当 Fragment 被创建的时候回调这个方法  fragment 是由系统创建，主动回调
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.context = getActivity();   //获取 Activity

    }


    /**
     *  当视图被创建的时候回调
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
        return initView();
    }


    /**
     * 让孩子实现自己的视图，达到自己特有的效果
     * @return
     */
    public abstract View initView();


    /**
     * 当 Activity 被创建之后 被 回调
     * @param savedInstanceState
     */
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
    }

    /**
     * 把数据绑定到 initView 视图上
     */
    public void initData(){

    };
}
