package com.example.john.beijingnews.fragment;

import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.john.beijingnews.R;
import com.example.john.beijingnews.activity.MainActivity;
import com.example.john.beijingnews.base.BaseFragment;
import com.example.john.beijingnews.domain.NewsCenterPargerBean;
import com.example.john.beijingnews.pager.NewsCenterPager;
import com.example.john.beijingnews.utils.DensityUtil;


import java.util.List;

/**
 *  Created by john on 2017/8/9.
 *  作用 : 左侧菜单的 Fragment
 */

public class LeftmenuFragment extends BaseFragment {

    private List<NewsCenterPargerBean.DataBean> data;

    private ListView listView;

    private MyLeftmenuFragmentAdapter adapter;

    //  记录当前点击的 是 LeftmenuFragment 中 listView 的 那一项的位置
    private int  prePosition;

    @Override
    public View initView() {

        //  菜单栏的 显示 承载 方式 是 ListView
        listView = new ListView(context);

        //  设置 距离 上下左右 边距
        listView.setPadding(0, DensityUtil.dip2px(context, 40), 0, 0);

        //  设置分割线 高度为 0
        listView.setDividerHeight(0);

        //  listView  在 2.3 或 低版本中 点击 listView 会变灰，把它设置成透明
        listView.setCacheColorHint(Color.TRANSPARENT);

        //  listView 的 item 选中某一项后 底色为黄色 设置为透明
        listView.setSelector(android.R.color.transparent);

        //  设置点击 leftmentFragment 的 listView 中的某一个 item 时，让"字体 图标" 变成红色
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                //  1. 记录上一次点击的位置
                prePosition = position;
                //  刷新 listView
                adapter.notifyDataSetChanged();  //  会调用  getCount() -->  getView()

                // 2. 关闭菜单栏
                MainActivity mainActivity = (MainActivity) context;
                mainActivity.getSlidingMenu().toggle(); // 作用  如果是"开" 那么点击就是 "关" 否则相反

                // 3.  切换到对应的详情页面：新闻页面、专题页面、图组页面、互动页面
                switchPager(position, mainActivity);


            }
        });
        return listView;
    }



    //  设置 左侧菜单栏 需要显示的数据
    public void setData(List<NewsCenterPargerBean.DataBean> data) {
        
        this.data= data;
        for (int i = 0; i < data.size(); i++){
            data.get(i).getTitle();
        }

        adapter = new MyLeftmenuFragmentAdapter();

        //  设置 listView 适配器
        listView.setAdapter(adapter);
    }

    class MyLeftmenuFragmentAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public View getView(int position, View view, ViewGroup viewGroup) {

            TextView textView = (TextView) view.inflate(context, R.layout.item_leftmenu, null);
            textView.setText(data.get(position).getTitle());
            //  让其点击的 textView 变成红色显示，其他变为白色显示
            textView.setEnabled(prePosition == position);
            return textView;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }
    }

    /**
     *  根据位置不同切换不同的详情页面
     * @param position
     * @param mainActivity
     */
    private void switchPager(int position, MainActivity mainActivity) {
        ContentFragment contentFragment = mainActivity.getContentFragment();
        NewsCenterPager newsCenterPager = contentFragment.getNewsCenterPager();

        //  通过位置切换详情页面
        newsCenterPager.switchPager(position);
    }
}
