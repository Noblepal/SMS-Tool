package com.intelligence.smscounter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MessagesAdapter extends RecyclerView.Adapter<MessagesAdapter.ViewHolder> {

    List<SMSData> list;
    Context context;

    public MessagesAdapter(List<SMSData> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public MessagesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.row_layout2, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MessagesAdapter.ViewHolder holder, int position) {
        SMSData s = list.get(position);
        holder.tv_sender.setText(s.getSenderName());
        holder.tv_date.setText(s.getDate());
        holder.tv_message.setText(s.getMessage());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_sender, tv_date, tv_message;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_sender = itemView.findViewById(R.id.tv_sender);
            tv_date = itemView.findViewById(R.id.tv_date);
            tv_message = itemView.findViewById(R.id.tv_message);
        }
    }
}
