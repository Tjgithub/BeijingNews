package com.example.john.beijingnews;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.RelativeLayout;

import com.example.john.beijingnews.activity.GuideActivity;
import com.example.john.beijingnews.activity.MainActivity;
import com.example.john.beijingnews.utils.CacheUtils;

public class SplachActivity extends Activity {

    public static final String START_MAIN = "start_main";
    private RelativeLayout rl_splahs_root;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splach);

        // 获取 RelativeLayout 对象实例
        rl_splahs_root = (RelativeLayout) findViewById(R.id.rl_splach_root);

        //  进入 app 图片 透明度 拉伸 旋转
        AlphaAnimation alpAnimation = new AlphaAnimation(0, 1);
//        alpAnimation.setDuration(500);  //  设置动画播放时间
        alpAnimation.setFillAfter(true);  //  设置动画播放结束后是否保持播放状态

        //  拉伸     x轴开始， x轴结束  y轴开始， y轴结束  Animation.RELATIVE_TO_SELF  0.5f  代表旋转 坐标点
        ScaleAnimation scaleAnimation = new ScaleAnimation(0, 1, 0, 1, ScaleAnimation.RELATIVE_TO_SELF, 0.5f, ScaleAnimation.RELATIVE_TO_SELF, 0.5f);
//        scaleAnimation.setDuration(500);
        scaleAnimation.setFillAfter(true);

        //  旋转    开始角度   结束角度    旋转坐标点
        RotateAnimation rotAnimation = new RotateAnimation(0, 360, RotateAnimation.RELATIVE_TO_SELF, 0.5f, RotateAnimation.RELATIVE_TO_SELF, 0.5f);
//        rotAnimation.setDuration(500);
        rotAnimation.setFillAfter(true);

        //  设置播放容器  把三种播放 形式 添加到  AnimationSet 容器中，实现三种形式的调用
        AnimationSet aniSet = new AnimationSet(false);  //  默认为 false
        aniSet.addAnimation(alpAnimation);
        aniSet.addAnimation(scaleAnimation);
        aniSet.addAnimation(rotAnimation);   //  添加三个动画没有先后顺序， 便于同时播放动画
        aniSet.setDuration(2000);   //  会代替上面 三个对象设置的 动画播放延迟

        //  播放动画 是 View 类中的方法 RelativeLayout 也是子类的方法，让 RelativeLayout 包含的图片进行旋转拉伸等动画
        rl_splahs_root.startAnimation(aniSet);

        //  设置动画 播放监听
        aniSet.setAnimationListener(new MyAnimationListener());
    }

    class MyAnimationListener implements Animation.AnimationListener{

        /**
         * 动画开始播放 调用
         * @param animation
         */
        @Override
        public void onAnimationStart(Animation animation) {

        }

        /**
         * 动画结束播放 调用
         * @param animation
         */
        @Override
        public void onAnimationEnd(Animation animation) {
            //  判断是否进入过主页面
            boolean isStartMain = CacheUtils.getBoolean(SplachActivity.this, START_MAIN);  //  创建一个工具类， 记录是否是第一次启动程序
            Intent intent = null;
            if (isStartMain){
                //  进入主界面
                intent = new Intent(SplachActivity.this, MainActivity.class);
            }else{
                //  进入 向导界面
                intent = new Intent(SplachActivity.this, GuideActivity.class);
            }
            startActivity(intent);

            //  关闭 当前 Activity
            finish();
        }

        /**
         * 动画重复开始播放 调用
         * @param animation
         */
        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    }
}
