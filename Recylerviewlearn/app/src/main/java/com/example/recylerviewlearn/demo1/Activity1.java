package com.example.recylerviewlearn.demo1;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTabHost;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;

import com.example.recylerviewlearn.R;
import com.example.recylerviewlearn.demo1.View.FirstFragment;
import com.example.recylerviewlearn.demo1.View.FragmentAdapter;
import com.example.recylerviewlearn.demo1.View.InterceptScrollView;
import com.example.recylerviewlearn.demo1.View.SecondFragment;
import com.example.recylerviewlearn.demo1.date.User;
import com.example.recylerviewlearn.demo1.date.UserDatabase;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import static com.example.recylerviewlearn.demo1.View.FirstFragment.MSG_DELETE_LAST;
import static com.example.recylerviewlearn.demo1.View.FirstFragment.MSG_RECEIVE;

/**
 * Demo1：RecyclerView的基本使用。
 * - 为RecyclerView添加OnItemClickListener接口。
 * - ItemDecoration的范例：DividerItemDecoration。
 * - 为RecyclerView实现Headerview和Footerview。
 */
public class Activity1 extends AppCompatActivity implements View.OnClickListener{

    private RecyclerView mRecyclerView;
    private ArrayList<User> mData;
    private NormalAdapter mAdapter;
    private DividerItemDecoration mDecoration;
    private RecyclerView.LayoutManager mLayoutManager;

    private static FirstFragment.MyHandler myHandler;

    private SwipeRefreshLayout mSwipeRefreshLayout;

    private RecyclerView mTRecyclerView;
    private ArrayList<User> mTData;
    private NormalAdapter mTAdapter;
    private DividerItemDecoration mTDecoration;
    private RecyclerView.LayoutManager mGridLayoutManager;

    private InterceptScrollView mScrollView;

    private TextView firstpage,secondpage;
    private ViewPager viewPager;
    private List<Fragment> mFragmentList = new ArrayList<>();
    private FragmentAdapter fragmentAdapter;
    private SecondFragment secondFragment;


    private FirstFragment firstFragment;

    private FragmentTabHost fragmentTabHost;

    public String test;

//    public final static int MSG_RELOAD = 6;

    private Button send_button;
    private EditText send_editText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_1);

//        myHandler = new MyHandler(this);
        initViews();
        initTab();
//        initTalk();
    }

    private void initTab() {
        fragmentTabHost = findViewById(R.id.main_tab);

    }

    private void initViews() {
        firstpage = (TextView) findViewById(R.id.first_page);
        secondpage = (TextView) findViewById(R.id.secend_page);
        firstpage.setOnClickListener((View.OnClickListener) this);
        secondpage.setOnClickListener((View.OnClickListener) this);

        viewPager = (ViewPager)findViewById(R.id.mainViewPager);
        firstFragment = new FirstFragment();
        secondFragment = new SecondFragment();

        mFragmentList.add(firstFragment);
        mFragmentList.add(secondFragment);
        viewPager.setOffscreenPageLimit(2);//ViewPager的缓存为2帧

        fragmentAdapter = new FragmentAdapter(this.getSupportFragmentManager(), mFragmentList);
        viewPager.setAdapter(fragmentAdapter);
        viewPager.setCurrentItem(0);
        firstpage.setTextColor(Color.parseColor("#66ccff"));

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                changeTextColor(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void changeTextColor(int position){
        if(position == 0){
            firstpage.setTextColor(Color.parseColor("#66ccff"));
            secondpage.setTextColor(Color.parseColor("#000000"));
        }else if (position == 1){
            firstpage.setTextColor(Color.parseColor("#000000"));
            secondpage.setTextColor(Color.parseColor("#66ccff"));
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.first_page:
                viewPager.setCurrentItem(0, true);
                 break;
            case R.id.secend_page:
                viewPager.setCurrentItem(1, true);
                break;
             }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_1, menu);
        return true;
    }


//    菜单栏点击选项按钮
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.item_add:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Activity1.this.getFirstFragment().addDrogen();
                    }
                }).start();
                break;
            case R.id.item_delete:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Activity1.this.getFirstFragment().deleteLast();
                    }
                }).start();

                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void setHandler(FirstFragment.MyHandler myHandler){
        this.myHandler = myHandler;
    }

    public SecondFragment getSecondFragment() {
        return secondFragment;
    }

    public void setSecondFragment(SecondFragment secondFragment) {
        this.secondFragment = secondFragment;
    }

    public FirstFragment getFirstFragment() {
        return firstFragment;
    }

    public void setFirstFragment(FirstFragment firstFragment) {
        this.firstFragment = firstFragment;
    }
}
