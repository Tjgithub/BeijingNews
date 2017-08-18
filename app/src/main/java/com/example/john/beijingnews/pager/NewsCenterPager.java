package com.example.john.beijingnews.pager;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.example.john.beijingnews.activity.MainActivity;
import com.example.john.beijingnews.base.BasePager;
import com.example.john.beijingnews.base.MenuDetailBasePager;
import com.example.john.beijingnews.domain.NewsCenterPargerBean;
import com.example.john.beijingnews.fragment.LeftmenuFragment;
import com.example.john.beijingnews.menudetailpager.InteracMenuDetailPager;
import com.example.john.beijingnews.menudetailpager.NewsMenuDetailPager;
import com.example.john.beijingnews.menudetailpager.PhotosMenuDetailPager;
import com.example.john.beijingnews.menudetailpager.TopicMenuDetailPager;
import com.example.john.beijingnews.utils.Contants;
import com.example.john.beijingnews.utils.LogUtil;
import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;


public class NewsCenterPager extends BasePager{

    public List<NewsCenterPargerBean.DataBean> data;

    //  菜单 栏中对应的 新闻页面
    public List<MenuDetailBasePager> detailBasePager;

    public NewsCenterPager(Context context) {
        super(context);
    }

    @Override
    public void initData() {
        super.initData();
        //  显示出来 标题栏上的 "菜单" 按钮
        ib_menu.setVisibility(View.VISIBLE);
        //1.  设置标题
        tv_title.setText("新闻中心页面");

        //2.  联网请求  得到数据   创建视图
        TextView textView = new TextView(context);
        textView.setGravity(Gravity.CENTER);
        textView.setTextColor(Color.RED);
        textView.setTextSize(25);

        //  3.  把子视图添加到 BasePager 的 FrameLayout 中
        fl_content.addView(textView);
        textView.setText("新闻中心内容");

        //  联网请求新闻数据
        getDataFromNet();
    }

    /**
     *  联网请求数据  使用 xUtils3 第三方库请求数据
     */
    private void getDataFromNet() {

        //  ① 联网请求参数 params  ②  回调函数
        RequestParams params = new RequestParams(Contants.NEWSRUL);
        LogUtil.d("params ===" + params);
        x.http().get(params, new Callback.CommonCallback<String>() {
            //  请求成功返回的是 Json 格式的数据
            @Override
            public void onSuccess(String result) {
                LogUtil.d("result ===" + result);
                //  解析 json 数据和显示
                processData(result);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                LogUtil.d("ex ===" + ex);
            }

            @Override
            public void onCancelled(CancelledException cex) {
                LogUtil.d("cex ===" + cex);

            }

            @Override
            public void onFinished() {
                LogUtil.d("这在执行请求");

            }
        });
    }

    //  解析 json 数据和显示
    private void processData(String json) {

        //  解析 json
        NewsCenterPargerBean bean = paramsJson(json);
        String title = bean.getData().get(0).getChildren().get(1).getTitle();
        LogUtil.d("title===="+ title);

        //  给左侧菜单栏传递数据 (返回 json 数据下 data 节点下的所有 数据)
        data = bean.getData();

        //  初始化 左侧 菜单栏，给菜单栏添加数据
        MainActivity mainActivity = (MainActivity) context;

        //  得到左侧菜单栏 类对象
        LeftmenuFragment leftFragment = mainActivity.getLeftmenuFragment();

        //  创建 菜单栏 对应的页面对象
        detailBasePager = new ArrayList<>();
        detailBasePager.add(new NewsMenuDetailPager(context));
        detailBasePager.add(new PhotosMenuDetailPager(context));
        detailBasePager.add(new TopicMenuDetailPager(context));
        detailBasePager.add(new InteracMenuDetailPager(context));

        //  给左侧菜单栏添加 数据
        leftFragment.setData(data);
    }

    //  解析  json 数据  1. 使用系统 json 解析数据  2. 使用第三方 方式解析   Gson  fastJson
    private NewsCenterPargerBean paramsJson(String json) {

        //  ① json 格式的数据   ② 把 json 数据解析后 放到指定的类中
        Gson gson = new Gson();
        NewsCenterPargerBean bean = gson.fromJson(json, NewsCenterPargerBean.class);  //  返回来的是 NewsCenterParamsBean  类型对象

        //  return new Gson().fromJson(json, NewsCenterPargerBean.class);
        return  bean;
    }



    /**
     *  根据位置切换详情页面
     */
    public void switchPager(int position){

        // 1.  设置标题栏上显示的信息
        tv_title.setText(data.get(position).getTitle());

        // 2.  移除之前 "新闻中心上显示的内容 textView "
        fl_content.removeAllViews();

        // 3.  添加新内容
        MenuDetailBasePager mdetailBasePager = detailBasePager.get(position);  //  得到 数组中指定位置的对象
        View rootView = mdetailBasePager.rootView;
        LogUtil.d("rootView===" + rootView);
        mdetailBasePager.initData();  // 初始化数据

        fl_content.addView(rootView);

    }
}
