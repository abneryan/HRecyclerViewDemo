package com.example.hrecyclerviewdemo;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.ViewTreeObserver;

import com.example.hrecyclerviewdemo.adapter.ContentAdapter;
import com.example.hrecyclerviewdemo.adapter.TitleAdapter;
import com.example.hrecyclerviewdemo.bean.ClassBean;
import com.example.hrecyclerviewdemo.bean.TimeBean;
import com.example.hrecyclerviewdemo.bean.ViewBean;
import com.example.hrecyclerviewdemo.widget.NoScrollRecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements TitleAdapter.ScollToCallBack {

    private static final String TAG = "MainActivity";
    private NestedScrollView mScrollView;
    private NoScrollRecyclerView mTitleRV;
    private RecyclerView mContentRV;
    private List<ClassBean> classList = new ArrayList<>();
    private List<TimeBean> timeList = new ArrayList<>();
    private ContentAdapter mContentAdapter;
    private TitleAdapter mTitleAdapter;
    private boolean isInitView;
    private int mScrollViewHeight;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock_layout);
        initview();
        initData();
    }

    private void initData() {

        for (int i = 0; i < 12; i++) {//12天对应的课程
            for (int j = 0; j < 30; j++) {
                ClassBean classBean = new ClassBean();
                classBean.setName("正式课");
                classBean.setStudentName("张三" + i);
                classList.add(classBean);
            }
        }

        for (int j = 0; j < 30; j++) {//每天对应时间段
            TimeBean timeBean = new TimeBean();
            timeBean.setTime(j + 1 + ":00");
            timeList.add(timeBean);
        }


    }


    private void initview() {
        mScrollView = findViewById(R.id.timetable_sv);
        mScrollView.getViewTreeObserver().addOnPreDrawListener(
                new ViewTreeObserver.OnPreDrawListener() {

                    @Override
                    public boolean onPreDraw() {
                        if (!isInitView) {
                            Log.d(TAG, "Height:" + mScrollView.getHeight() + "--Width:" + mScrollView.getWidth());
                            mScrollViewHeight = mScrollView.getHeight();
                            mScrollView.getViewTreeObserver().removeOnPreDrawListener(this);
                            isInitView = true;
                        }
                        return true;
                    }
                });
        mTitleRV = findViewById(R.id.timetable_title_rv);
        mContentRV = findViewById(R.id.timetable_content_rv);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mTitleRV.setLayoutManager(linearLayoutManager);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 30, LinearLayoutManager.HORIZONTAL, false);
        mContentRV.setLayoutManager(gridLayoutManager);

        mContentAdapter = new ContentAdapter(this);
        mContentRV.setAdapter(mContentAdapter);
        mContentAdapter.setData(classList);

        mTitleAdapter = new TitleAdapter(getBaseContext());
        mTitleAdapter.setScollToCallBack(this);
        mTitleRV.setAdapter(mTitleAdapter);
        mTitleAdapter.setData(timeList);


    }

    @Override
    public void scollTo(final ViewBean bean) {
        mScrollView.post(()->{
            int hight = bean.getHight();
            Log.d(TAG, "scollTo()---hight:" + hight + "--mScrollViewHeight:" + mScrollViewHeight);
            if (hight >= mScrollViewHeight) {
                mScrollView.scrollTo(0, hight);
            }
        });
    }
}
