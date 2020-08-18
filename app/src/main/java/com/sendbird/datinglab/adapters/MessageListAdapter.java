package com.sendbird.datinglab.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.sendbird.android.GroupChannel;
import com.sendbird.datinglab.R;
import com.sendbird.datinglab.entities.MessageItem;
import com.sendbird.uikit.activities.adapter.ChannelListAdapter;
import com.sendbird.uikit.activities.viewholder.BaseViewHolder;

import java.util.List;

public class MessageListAdapter extends ChannelListAdapter {
    private Context context;
    private List<GroupChannel> messageList;

    class MyViewHolder extends BaseViewHolder<GroupChannel> {
        TextView name, message;
        ImageView thumbnail;
        RelativeLayout viewIndicator;

        MyViewHolder(@NonNull View view) {
            super(view);
            name = view.findViewById(R.id.text_name);
            message = view.findViewById(R.id.text_content);
            thumbnail = view.findViewById(R.id.thumbnail);
            viewIndicator = view.findViewById(R.id.layout_dot_indicator);
        }

        @Override
        public void bind(GroupChannel item) {
            name.setText(item.getName());
            message.setText(item.getLastMessage().getMessage());
            Glide.with(context).load(item.getCoverUrl()).into(thumbnail);
        }
    }


    public MessageListAdapter(Context context, List<GroupChannel> messageList) {
        this.context = context;
        this.messageList = messageList;
    }

    @Override
    public BaseViewHolder<GroupChannel> onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_message_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(BaseViewHolder<GroupChannel> holder, final int position) {
        //holder.bind(messageList.get(position));
        super.onBindViewHolder(holder,position);
    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }

}