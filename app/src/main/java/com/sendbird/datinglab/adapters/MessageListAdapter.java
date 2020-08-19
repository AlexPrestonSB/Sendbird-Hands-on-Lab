package com.sendbird.datinglab.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.sendbird.android.GroupChannel;
import com.sendbird.datinglab.R;
import com.sendbird.uikit.activities.adapter.ChannelListAdapter;
import com.sendbird.uikit.activities.viewholder.BaseViewHolder;

public class MessageListAdapter extends ChannelListAdapter {
    private Context context;
    private static onItemClickListener listener;

    public interface onItemClickListener {
        void onItemClick(GroupChannel channel);
        void onItemLongClick(GroupChannel channel);
    }

    class MyViewHolder extends BaseViewHolder<GroupChannel> implements View.OnClickListener, View.OnLongClickListener {
        TextView name, message;
        ImageView thumbnail;

        MyViewHolder(@NonNull View view) {
            super(view);
            name = view.findViewById(R.id.text_name);
            message = view.findViewById(R.id.text_content);
            thumbnail = view.findViewById(R.id.thumbnail);
            view.setOnLongClickListener(listener.onItemClick(););
            view.setOnClickListener(this);
        }

        @Override
        public void bind(GroupChannel item, onItemClickListener listener) {
            name.setText(item.getName());
            message.setText(item.getLastMessage().getMessage());
            Glide.with(context).load(item.getCoverUrl()).into(thumbnail);

        }


        @Override
        public void onClick(View view) {
            listener.onItemClick(v);
        }

        @Override
        public boolean onLongClick(View view) {
            listener.onItemLongClick(getAdapterPosition(), view);
            return false;
        }
    }


    public MessageListAdapter(Context context) {
        this.context = context;
    }

    @Override
    public BaseViewHolder<GroupChannel> onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_message_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(BaseViewHolder<GroupChannel> holder, final int position) {
        super.onBindViewHolder(holder, position);
    }

}