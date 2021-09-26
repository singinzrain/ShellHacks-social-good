package com.shell.hacks.ui.task;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.shell.hacks.R;
import com.shell.hacks.ui.help.TaskDetail;

import java.util.List;

public class RviewAdapter extends RecyclerView.Adapter<RviewHolder> {
    public List<TaskDetail> itemList;
    private ItemClickListener listener;

    public RviewAdapter(List<TaskDetail> itemList) {
        this.itemList = itemList;
    }

    public void setOnItemClickListener(ItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public RviewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_card, parent, false);
        return new RviewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(RviewHolder holder, int position) {
        TaskDetail currentItem = itemList.get(position);

        holder.itemName.setText(currentItem.getDescription());
        holder.itemDesc.setText(currentItem.getDescription());
        holder.duration.setText(currentItem.getStartTime() + "-" + currentItem.getEndTime());
        holder.distance.setText(currentItem.getLocation());
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public List<TaskDetail> getItemList() {
        return itemList;
    }

    public void setItemList(List<TaskDetail> itemList) {
        this.itemList = itemList;
    }

    public ItemClickListener getListener() {
        return listener;
    }

    public void setListener(ItemClickListener listener) {
        this.listener = listener;
    }
}
