package com.sendbird.datinglab.fragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sendbird.android.GroupChannel;
import com.sendbird.android.GroupChannelListQuery;
import com.sendbird.datinglab.R;
import com.sendbird.datinglab.activities.MainActivity;
import com.sendbird.datinglab.adapters.LikeAdapter;
import com.sendbird.datinglab.adapters.MessageListAdapter;
import com.sendbird.datinglab.entities.Like;
import com.sendbird.datinglab.entities.MessageItem;
import com.sendbird.uikit.SendBirdUIKit;
import com.sendbird.uikit.fragments.ChannelListFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChatFragment extends Fragment {


    View rootLayout;
    private static final String TAG = MainActivity.class.getSimpleName();
    private List<GroupChannel> messageList;
    private MessageListAdapter mAdapter;


    public ChatFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootLayout = inflater.inflate(R.layout.fragment_chat, container, false);



        RecyclerView recyclerView = rootLayout.findViewById(R.id.recycler_view_messages);
        messageList = new ArrayList<>();
        mAdapter = new MessageListAdapter(getContext(), messageList);



        GroupChannelListQuery query = GroupChannel.createMyGroupChannelListQuery();

        ChannelListFragment fragment = new ChannelListFragment.Builder().setChannelListAdapter(mAdapter).setGroupChannelListQuery(query).build();


        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        //recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(mAdapter);





        return rootLayout;
    }


}
