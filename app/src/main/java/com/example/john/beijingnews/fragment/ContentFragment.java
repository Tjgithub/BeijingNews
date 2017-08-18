package com.example.john.beijingnews.fragment;


import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import com.example.john.beijingnews.R;
import com.example.john.beijingnews.activity.MainActivity;
import com.example.john.beijingnews.adapter.ContentFragmentAdapter;
import com.example.john.beijingnews.base.BaseFragment;
import com.example.john.beijingnews.base.BasePager;
import com.example.john.beijingnews.pager.GovaffairPager;
import com.example.john.beijingnews.pager.HomePager;
import com.example.john.beijingnews.pager.NewsCenterPager;
import com.example.john.beijingnews.pager.SettingPager;
import com.example.john.beijingnews.view.NoScrollViewPager;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.sql.SQLOutput;
import java.util.ArrayList;

/**
 *  Created by john on 2017/8/9.
 */

public class ContentFragment extends BaseFragment {

    @ViewInject(R.id.viewPager)   //  实例化 viewPager 并把引用赋值给 viewPager
    private NoScrollViewPager viewPager;
    //private ViewPager viewPager;

    @ViewInject(R.id.rg_main)
    private RadioGroup rg_main;


    //  存储 BasePager 对象的容器
    private ArrayList<BasePager> basePagers;

    @Override
    public View initView() {

        //  加载布局文件  context  使用的是 父类 context
        View view = View.inflate(context, R.layout.content_fragmnet, null);

        /*//  获取 viewPager  ViewGroup 对象实例
        viewPager = (ViewPager) view.findViewById(R.id.viewPager);
        rg_main = (RadioGroup) view.findViewById(R.id.rg_main);*/

        //  1.  把视图注入到框架中，让 ContentFragment.this  和 view  关联起来
        x.view().inject(ContentFragment.this, view);
        return view;
    }

    @Override
    public void initData() {
        super.initData();

        //  初始化 五个 页面 ，并且放入 集合中
        basePagers = new ArrayList<>();

        basePagers.add(new HomePager(context));  //  主页面
        basePagers.add(new NewsCenterPager(context));  //  新闻页面
        basePagers.add(new SettingPager(context));  //  智慧页面
        basePagers.add(new GovaffairPager(context));  //  政要页面
        basePagers.add(new SettingPager(context));  //  设置页面


//        //  添加 ViewPager 适配器
        viewPager.setAdapter(new ContentFragmentAdapter(basePagers));
//        viewPager.setAdapter(new ContentFragmentAdapter());

        //  RadioGroup  添加 点击事件
        rg_main.setOnCheckedChangeListener(new MyOnCheckedChangeListener());

        //  屏蔽掉 ViewPager 预加载 下一个视图的数据，只可以 点击某个视图的时候在加载数据(调用 initData)
        //  监听 某个页面被选中， 初始化对应的页面数据
        viewPager.addOnPageChangeListener(new MyOnPageChangeListener());

        //  设置 默认选中项 为 "主页"
        rg_main.check(R.id.rb_home);

        //  去掉 instantiateItem(ViewGroup container, int position) 方法中的 initData() 方法，视图上就不会初始化 数据了
        //  默认 进入程序 选中 主页， 并加载主页数据
        basePagers.get(0).initData();

        //  默认主页面是不可以滑动的
        isEnableSlidingMenu(false);
    }



    //  监听某个选中页面是否改变
    class MyOnPageChangeListener implements ViewPager.OnPageChangeListener{

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        //  这个方法 代表的是 当前选中的页面
        @Override
        public void onPageSelected(int position) {
            //  选中页面初始化
            basePagers.get(position).initData();
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }

    //  RadioGroup  点击事件
    class MyOnCheckedChangeListener implements RadioGroup.OnCheckedChangeListener{

        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            switch (checkedId){
                case R.id.rb_home:
                    // ① ViewPager 当前显示的 视图 参数为值为集合中第 0 页面个元素 ， ②  viewPager 切换时是否有动画
                    viewPager.setCurrentItem(0, false);
                    isEnableSlidingMenu(false);
                    break;
                case R.id.rb_newscenter:
                    viewPager.setCurrentItem(1, false);
                    isEnableSlidingMenu(true);
                    break;
                case R.id.rb_smartservice:
                    viewPager.setCurrentItem(2, false);
                    isEnableSlidingMenu(false);
                    break;
                case R.id.rb_govaffair:
                    viewPager.setCurrentItem(3, false);
                    isEnableSlidingMenu(false);
                    break;
                case R.id.rb_setting:
                    viewPager.setCurrentItem(4, false);
                    isEnableSlidingMenu(false);
                    break;
            }
        }
    }

    //   根据选中的页面 设置 是否可以滑动出 菜单页面
    private void isEnableSlidingMenu(Boolean isEnable) {
        MainActivity mainActivity = (MainActivity) context;
        if (isEnable){
            //  添加只有 "新闻页面" 才可以划出 菜单栏， 其它页面都不可以
            mainActivity.getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        }else{
            mainActivity.getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
        }
    }


    /**
     *  得到新闻中心
     */
    public NewsCenterPager getNewsCenterPager() {

        return (NewsCenterPager) basePagers.get(1);
    }






//    class ContentFragmentAdapter extends PagerAdapter{
//
//        @Override
//        public int getCount() {
//            return basePagers.size();
//        }
//
//        @Override
//        public Object instantiateItem(ViewGroup container, int position) {
//
//            BasePager basePager = basePagers.get(position);
//            //  得到各个子页面的 view
//            View rootView = basePager.rootView;
//            //  初始化 数据
//            //basePager.initData();
//
//            //  添加到 ViewPager
//            container.addView(rootView);
//
//            return rootView;
//        }
//
//        @Override
//        public boolean isViewFromObject(View view, Object object) {
//            return view == object;
//        }
//
//        @Override
//        public void destroyItem(ViewGroup container, int position, Object object) {
//            container.removeView((View) object);
//        }
//    }
}
