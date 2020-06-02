package com.example.hrecyclerviewdemo.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hrecyclerviewdemo.R;
import com.example.hrecyclerviewdemo.bean.TimeBean;
import com.example.hrecyclerviewdemo.bean.ViewBean;

import java.util.List;

public class TitleAdapter extends RecyclerView.Adapter<TitleAdapter.TimeTitleViewHolder> {
    private final static String TAG = "TitleAdapter";
    private final Context mContext;
    private final LayoutInflater mLayoutInflater;
    private List<TimeBean> mDataList;
    private ScollToCallBack mCallBack;
    private boolean isInitView;
    private int mViewHeight;
    private int mScoll2Position;

    public interface ScollToCallBack {
        /**
         * 设置课表列表滚动的位置
         * @param bean
         */
        void scollTo(ViewBean bean);
    }

    public TitleAdapter(Context context) {
        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
    }

    public void setScollToCallBack(ScollToCallBack callBack) {
        mCallBack = callBack;
    }

    public void setData(List<TimeBean> dataList) {
        mDataList = dataList;
    }

    @NonNull
    @Override
    public TimeTitleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View view = mLayoutInflater.inflate(R.layout.item_time_layout, parent, false);
        view.getViewTreeObserver().addOnPreDrawListener(
                new ViewTreeObserver.OnPreDrawListener() {

                    @Override
                    public boolean onPreDraw() {
                        if (!isInitView) {
                            Log.d(TAG, "Height:" + view.getHeight() + "--Width:" + view.getWidth());
                            mViewHeight = view.getHeight();
                            ViewBean viewBean = new ViewBean();
                            mViewHeight = (mScoll2Position) * mViewHeight;
                            viewBean.setHight(mViewHeight);
                            mCallBack.scollTo(viewBean);
                            view.getViewTreeObserver().removeOnPreDrawListener(this);
                            isInitView = true;
                        }
                        return true;
                    }
                });
        TimeTitleViewHolder timeTitleViewHolder = new TimeTitleViewHolder(view);
        return timeTitleViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull TimeTitleViewHolder holder, int position) {
        if (mDataList == null || mDataList.size() == 0) {
            return;
        }
        TimeBean timeBean = mDataList.get(position);
        if (timeBean.getTime().equals("18:00")) {
            mScoll2Position = position;
        }
        holder.timeTitleView.setText(timeBean.getTime());
    }

    @Override
    public int getItemCount() {
        if (mDataList == null || mDataList.size() == 0) {
            return 0;
        }
        return mDataList.size();
    }

    public class TimeTitleViewHolder extends RecyclerView.ViewHolder {
        TextView timeTitleView;

        public TimeTitleViewHolder(@NonNull View itemView) {
            super(itemView);
            timeTitleView = itemView.findViewById(R.id.class_timetv);
        }
    }

}
