package org.hse.baseproject;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public final class ItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final static int TYPE_ITEM= 0;
    private final static int TYPE_HEADER = 1;
    private List<ScheduleItem> dataList = new ArrayList<>();
    private OnItemClick onItemClick;
    public ItemAdapter (OnItemClick onItemClick) { this.onItemClick = onItemClick; }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder (ViewGroup parent, int viewType) {
        Log.e("","onCreateViewHolder");
        Context context =parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        if (viewType == TYPE_ITEM) {
            View contactView = inflater.inflate(R.layout.activity_shedule_item, parent,  false);
            return new ViewHolder (contactView, context, onItemClick);
        } else if (viewType == TYPE_HEADER) {
           View contactView = inflater.inflate(R.layout.activity_shedule_item_header, parent,  false);
            return new ViewHolderHeader (contactView, context, onItemClick);
        }
        throw new IllegalArgumentException("Invalid view type");
    }


    public int getItemViewType(int position) {
        ScheduleItem data = dataList.get(position);
        if (data instanceof ScheduleItemHeader) { return TYPE_HEADER;}
        return TYPE_ITEM;

    }
    @Override
    public void onBindViewHolder (RecyclerView.ViewHolder viewHolder, int position) {
        Log.e("","onBindViewHolder"+position);
        ScheduleItem data =dataList.get(position);
        if (viewHolder instanceof ViewHolder) {Log.e("","instanceof ViewHolder");
            ((ViewHolder) viewHolder).bind(data);
        }else if (viewHolder instanceof ViewHolderHeader) {
            ((ViewHolderHeader) viewHolder).bind((ScheduleItemHeader) data);
        }

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setDataList(List<ScheduleItem> list) {
        dataList=list;
        notifyDataSetChanged();
        Log.e("","SetDataList"+ (list.size()) );
    }
}
