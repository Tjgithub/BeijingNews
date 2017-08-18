package com.example.john.beijingnews.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.john.beijingnews.R;
import com.example.john.beijingnews.SplachActivity;
import com.example.john.beijingnews.utils.CacheUtils;
import com.example.john.beijingnews.utils.DensityUtil;

import java.util.ArrayList;

public class GuideActivity extends Activity {

    private ViewPager viewPager;
    private Button btn_start_main;
    private LinearLayout ll_point_group;
    private ArrayList<ImageView> imageViews;
    private ImageView iv_red_point;
    private int leftMax;
    private int widthdpi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);

        //  初始化数据
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        btn_start_main = (Button) findViewById(R.id.btn_start_main);
        ll_point_group = (LinearLayout) findViewById(R.id.ll_point_group);
        iv_red_point = (ImageView) findViewById(R.id.iv_red_point);

        int[] ids = new int[]{
                R.drawable.guide_1,
                R.drawable.guide_2,
                R.drawable.guide_3
        };

        //  把 像素值 改为 dip 类型   滑动过程中返回的是 dpi 类型
        widthdpi = DensityUtil.dip2px(this, 10);

        imageViews = new ArrayList<>();

        //  动态生成 ImageView 对象
        for (int i = 0; i < ids.length; i++){
            ImageView imageView = new ImageView(this);
            imageView.setBackgroundResource(ids[i]);   //  设置背景图片
            imageViews.add(imageView);

            //  把引导标识(小圆点) 添加到 LinearLayout 布局中
            //  创建 小圆点
            ImageView point = new ImageView(this);
            point.setBackgroundResource(R.drawable.point_normal);

            //  设置 ImageView(point)  在 LinearLayout 布局中 的高度 和 宽度。这两个 10 代表的 像素，需要使用 dip 单位
            //  把 10 像素 转换成 dip  DensityUtil.px2dip()
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(widthdpi, widthdpi);   //  如果使用 像素 在不同分辨率的手机上会变形

            //  设置每个小圆点之间的 间距 为 10 dp
            if (i != 0){
                params.leftMargin = widthdpi;   //  第 0 个小圆点( 也就是第一个小圆点) 左边没有小圆点，所以不用设置 左边距
            }

            point.setLayoutParams(params);
            //  添加点
            ll_point_group.addView(point);
        }

        //  给 ViewPager  添加 适配器
        viewPager.setAdapter(new MyViewPagerAdapter());

        //  设置 全局树 观察者 监听
        iv_red_point.getViewTreeObserver().addOnGlobalLayoutListener(new MyOnGlobalLayoutListener());

        //  设置 ViewPager 的滑动监听
        viewPager.setOnPageChangeListener(new MyOnPageChangeListener());

        //  给 "开始体验" 按钮添加 点击监听
        btn_start_main.setOnClickListener(new MyOnClickListener());
    }

    //  按钮点击监听实现接口类
    class MyOnClickListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {

            //  1. 把缓存文件的标志位改为 true 表示，已经进入过主界面
            CacheUtils.putBoolean(GuideActivity.this, SplachActivity.START_MAIN, true);

            //  2.  跳转到新页面
            Intent intent = new Intent(GuideActivity.this, MainActivity.class);
            startActivity(intent);

            //  3.  关闭 "向导" Activity
            finish();
        }
    }

    //  实现滑动监听接口
    class MyOnPageChangeListener implements ViewPager.OnPageChangeListener{

        /**
         * 当前页面会回调这个方法
         * @param position  当前页面的 位置
         * @param positionOffset  当前页面滑动百分比
         * @param positionOffsetPixels  当前页面滑动像素
         */
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            //  两点间移动的距离 = 屏幕滑动百分比 * 间距
//            int leftmargin = (int) (positionOffset * leftMax);

            //  两点间滑动距离对应的坐标 = 原来的起始位置 + 两点间移动的距离
            int leftmargin = (int) (position * leftMax + positionOffset * leftMax);

            //  params.leftMargin = 两点间滑动距离对应的坐标

            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) iv_red_point.getLayoutParams();
            params.leftMargin = leftmargin;
            iv_red_point.setLayoutParams(params);
        }


        /**
         * 当前页面被选中的时候调用
         * @param position  被选中页面的位置
         */
        @Override
        public void onPageSelected(int position) {

            //  在最后一个页面显示 "开始体验"  按钮
            if (position == imageViews.size()-1){
                //  最后一个页面
                btn_start_main.setVisibility(View.VISIBLE);
            }else{
                //  其他页面
                btn_start_main.setVisibility(View.GONE);
            }
        }


        /**
         * 当前页面滑动状态改变时调用这个方法
         * @param state
         */
        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }

    //  实现 全局树 监听接口
    class MyOnGlobalLayoutListener implements ViewTreeObserver.OnGlobalLayoutListener{

        //  这个方法不止一次调用，
        @Override
        public void onGlobalLayout() {
            //  但是这个方法只需要使用一次
            iv_red_point.getViewTreeObserver().removeGlobalOnLayoutListener(this);

            //  获取 第一个圆点距离左边距 与 第二个圆点距离左边距 之间的 差 之间间距
            leftMax = ll_point_group.getChildAt(1).getLeft() - ll_point_group.getChildAt(0).getLeft();
        }
    }


    //  继承 PagerAdapter 抽象类
    class MyViewPagerAdapter extends PagerAdapter{

        /**
         * 返回数据的总个数，是 ArrayList 容器中元素的个数，
         * @return
         */
        @Override
        public int getCount() {
            return imageViews.size();
        }


        /**
         * 第一个执行方法 ， 创建页面
         *
         * 与 ListView 的区别 需要 add 到容器中，再有就是有返回值
         *
         * 作用 类似 ListView 中的 getView 方法，但是这个方法 需要 add 容器中(ViewPager)
         * @param container  就是 ViewPager
         * @param position   要创建页面位置
         * @return  返回和创建当前页面有关系的值，也可以返回一个位置
         */
        @Override
        public Object instantiateItem(ViewGroup container, int position) {

            ImageView imageView = imageViews.get(position);
            container.addView(imageView);  //  将 imageView 添加到 ViewPager 容器中

//            return super.instantiateItem(container, position);
//            return position;  //  返回 position 也可以 但是一般都返回 imageView
            return imageView;
        }


        /**
         * ViewPager 一进入 默认创建 2 个视图 最多创建 3 个视图
         * 销毁页面
         * @param container  ViewPager
         * @param position   要销毁的页面位置
         * @param object     要销毁的页面
         */
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {

            //  这一行代码必须去掉，否则程序运行肯定崩溃
//            super.destroyItem(container, position, object);

            //  销毁的是在容器中的位置， 直接在容器中移除
            container.removeView((View) object);    //  因为 ViewPager 每次只能创建 2 个 视图，销毁的时候需要指定视图
        }


        /**
         * 第二个调用方法
         *
         * @param view  当前视图
         * @param object  上面 instantiateItem 返回的结果值
         * @return
         * 通常的做法是 把 instantiateItme 返回值和 view 进行比较，如果相同是同一个视图
         */
        @Override
        public boolean isViewFromObject(View view, Object object) {
//            return view == imageViews.get(Integer.parseInt((String)object));
            return view == object;
        }
    }
}
