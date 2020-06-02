package com.example.hrecyclerviewdemo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hrecyclerviewdemo.MainActivity;
import com.example.hrecyclerviewdemo.R;
import com.example.hrecyclerviewdemo.bean.ClassBean;
import com.example.hrecyclerviewdemo.utils.FitPopupUtil;

import java.util.List;

public class ContentAdapter extends RecyclerView.Adapter<ContentAdapter.ContentViewHolder> {
    private final LayoutInflater mLayoutInflater;
    private Context mContext;
    private List<ClassBean> mDataList;

    public ContentAdapter(Context context) {
        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
    }

    public void setData(List<ClassBean> dataList) {
        mDataList = dataList;
    }

    @NonNull
    @Override
    public ContentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View View = mLayoutInflater.inflate(R.layout.item_content_layout, parent, false);
        ContentViewHolder contentViewHolder = new ContentViewHolder(View);
        return contentViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ContentViewHolder holder, int position) {
        if (mDataList == null || mDataList.size() == 0) {
            return;
        }
        holder.itemView.setOnClickListener(view -> initPopup(view));
        holder.classTypeNameView.setText(mDataList.get(position).getName());
        holder.stuNameView.setText(mDataList.get(position).getStudentName());
    }

    @Override
    public int getItemCount() {
        if (mDataList == null) {
            return 0;
        }
        return mDataList.size();
    }


    class ContentViewHolder extends RecyclerView.ViewHolder {
        TextView stuNameView;
        TextView classTypeNameView;

        public ContentViewHolder(@NonNull View itemView) {
            super(itemView);
            classTypeNameView = itemView.findViewById(R.id.class_type_name_tv);
            stuNameView = itemView.findViewById(R.id.stu_name_tv);
        }
    }

    private void initPopup(View anchorView) {
        FitPopupUtil fitPopupUtil = new FitPopupUtil((MainActivity) mContext);
        fitPopupUtil.setOnClickListener(reason -> Toast.makeText(mContext, reason, Toast.LENGTH_SHORT).show());
        fitPopupUtil.showPopup(anchorView);
    }


}
