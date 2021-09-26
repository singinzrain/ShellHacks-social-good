package com.shell.hacks.ui.task;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.shell.hacks.R;

public class RviewHolder extends RecyclerView.ViewHolder {
    public TextView itemName;
    public TextView itemDesc;
    public TextView distance;
    public TextView duration;
    public Button button;

    public RviewHolder(View itemView, final ItemClickListener listener) {
        super(itemView);
        itemName = itemView.findViewById(R.id.task_name);
        itemDesc = itemView.findViewById(R.id.task_desc);
        distance = itemView.findViewById(R.id.distance);
        duration = itemView.findViewById(R.id.duration);
        button = itemView.findViewById(R.id.accept_button);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    int position = getLayoutPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(position);
                    }
                }
            }
        });
    }
}