package com.example.john.beijingnews.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.example.john.beijingnews.R;
import com.example.john.beijingnews.fragment.ContentFragment;
import com.example.john.beijingnews.fragment.LeftmenuFragment;
import com.example.john.beijingnews.utils.DensityUtil;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;

/**
 *  当前类 继承  SlidingFragmentActivity 类 可以实现 "右侧滑动效果"
 *  SlidingMenu 库实现了这个效果，其实 SlidingMenu 类 也是继承自 "FramentActivity" 类
 *
 *  一般 使用 FragmentActivity 类 布局文件都是用 帧式布局， 用于实现 视图的 左右滑动
 */

public class MainActivity extends SlidingFragmentActivity {

    public static final String MAIN_CONTENT_TAG = "main_content_tag";
    public static final String LEFTMENU_TAG = "leftmenu_tag";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //  初始化 SlidingMenu
        initSlidingMenu();

        //  初始化 Fragment
        initFragment();
    }

    /**
     * 初始化 SlidingMenu
     */
    private void initSlidingMenu() {
        //  1.  设置的是 主页面
        setContentView(R.layout.activity_main);

        //  2.  设置左侧彩单  使用的是 帧式布局
        setBehindContentView(R.layout.activity_leftmenu);

        //  3.  设置右侧菜单  默认左侧菜单已经生成
        SlidingMenu slidingMenu = getSlidingMenu();
//        slidingMenu.setSecondaryMenu(R.layout.activity_rightmenu);  //  设置右侧菜单

        //  4.  设置显示模式 :  左侧菜单 + 主页 (left)    左侧菜单 + 主页 + 右侧菜单 (left_right)     主页 + 右侧菜单 (right)
        slidingMenu.setMode(SlidingMenu.LEFT);


        //  5.  设置滑动模式 :  滑动边缘 ( Touchmode_margin)，  全屏滑动 (Touchmode_fullscreen)，  不可以滑动 (Touchmode_none)
        slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);

        //  6.  设置 主页面 占据的 宽度  ( 如果不设置 屏幕的宽度， 在向左右滑动主界面的时候，左右菜单栏将覆盖 主界面)
        slidingMenu.setBehindOffset(DensityUtil.dip2px(MainActivity.this, 200));
    }

    //  初始化 Fragement
    private void initFragment() {

        /**
         * 为什么要获取 FragmentManager 呢？ 因为一个 FragmentActivity 中 可以包含很多 Fragment
         * 那这么多 Fragment 由谁负责能，就是 FragmentManager 来负责
         */

        // 1.  获取 FragmentManager  是 V4 包的
        FragmentManager fmanager = getSupportFragmentManager();  //  进行了 Fragment 与当前 Activity

        // 2.  开启事务
        FragmentTransaction ftranscation = fmanager.beginTransaction();

        /**
         *  3.  替换
         *      ① 参 需要替换的容器,容器不动还是"帧布局"(改变的是 布局里面的其他内容)
         *      ②    Fragment  需要显示在哪个 Fragment，  也就是 "替换成的布局"
         *      ③    记录哪个 Fragment
         */
        ftranscation.replace(R.id.fl_main_content, new ContentFragment(), MAIN_CONTENT_TAG);
        //  布局文件为  setContentView(R.layout.); 用 new leftmenuFragment 中指定的布局替换
        ftranscation.replace(R.id.fl_leftmenu, new LeftmenuFragment(),  LEFTMENU_TAG);

        // 4.  提交
        ftranscation.commit();

    }


    //  得到 左侧 Fragment
    public LeftmenuFragment getLeftmenuFragment() {
        FragmentManager fm = getSupportFragmentManager();

        //  通过 标记 在 FragmentManager 管理器中查找  左侧 Fragment
        LeftmenuFragment leftFragment = (LeftmenuFragment) fm.findFragmentByTag(LEFTMENU_TAG);

        return leftFragment;
    }

    //  得到 正文 Fragment
    public ContentFragment getContentFragment() {
        FragmentManager fm = getSupportFragmentManager();

        //  通过 标记 在 FragmentManager 管理器中查找  正文 Fragment
        ContentFragment contentFragment = (ContentFragment) fm.findFragmentByTag(MAIN_CONTENT_TAG);

        return contentFragment;
    }
}
