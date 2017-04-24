package com.example.gy.myqq;

import android.animation.LayoutTransition;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import java.util.ArrayList;

/**
 * Created by gy on 2017/1/16.
 */

public class QQMainActivity extends Activity {
    /* 新建控件变量 */
    private ViewPager tabPager;
    private ImageView tab1, tab2, tab3, tab4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置不需要标题栏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.qqmain);

        //启动Activity时不自动弹出软键盘
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        /* 兴建控件变量，通过控件id赋值 */
        tabPager = (ViewPager)findViewById(R.id.tabpager);
        tab1 = (ImageView)findViewById(R.id.img_chat);
        tab2 = (ImageView)findViewById(R.id.img_content);
        tab3 = (ImageView)findViewById(R.id.img_zone);
        tab4 = (ImageView)findViewById(R.id.img_setting);

        tab1.setOnClickListener(new MyOnClickListener(0));
        tab2.setOnClickListener(new MyOnClickListener(1));
        tab3.setOnClickListener(new MyOnClickListener(2));
        tab4.setOnClickListener(new MyOnClickListener(3));

        /* 新建要分页显示的View， 并将view装入到views中 */
        LayoutInflater li = LayoutInflater.from(this);
        View view1 = li.inflate(R.layout.main_tab_chat, null);
        View view2 = li.inflate(R.layout.main_tab_contact, null);
        View view3 = li.inflate(R.layout.main_tab_zone, null);
        View view4 = li.inflate(R.layout.main_tab_setting, null);

        final ArrayList<View> views = new ArrayList<>();
        views.add(view1);
        views.add(view2);
        views.add(view3);
        views.add(view4);

        /* viewpager的数据适配器， 完成用户滑动 */
        PagerAdapter adapter = new PagerAdapter() {
            @Override
            public int getCount() {
                return views.size();
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                ((ViewPager)container).removeView(views.get(position));
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                ((ViewPager)container).addView(views.get(position));
                return views.get(position);
            }
        };

        tabPager.setAdapter(adapter);

    }

    public class MyOnClickListener implements View.OnClickListener{
        private int index = 0;

        public MyOnClickListener(int index){
            this.index = index;
        }
        @Override
        public void onClick(View v) {
            tabPager.setCurrentItem(index);
        }
    }


    /**
     * 完成main_tab_chat子界面中监听事件对应的方法
     * @param v
     */
    public void startchat(View v){
        Intent intent = new Intent(QQMainActivity.this, ChatActivity.class);
        startActivity(intent);
    }

}
