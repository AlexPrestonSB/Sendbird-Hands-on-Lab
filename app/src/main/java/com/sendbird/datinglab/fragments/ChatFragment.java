package com.sendbird.datinglab.fragments;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sendbird.android.GroupChannel;
import com.sendbird.android.GroupChannelListQuery;
import com.sendbird.datinglab.R;
import com.sendbird.datinglab.activities.MainActivity;
import com.sendbird.datinglab.adapters.MessageListAdapter;
import com.sendbird.uikit.activities.ChannelActivity;
import com.sendbird.uikit.fragments.ChannelListFragment;


/**
 * A simple {@link Fragment} subclass.
 */
public class ChatFragment extends Fragment implements MessageListAdapter.onItemClickListener {


    View rootLayout;
    private static final String TAG = MainActivity.class.getSimpleName();
    private MessageListAdapter mAdapter;


    public ChatFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootLayout = inflater.inflate(R.layout.fragment_chat, container, false);


        mAdapter = new MessageListAdapter(getContext());

        GroupChannelListQuery query = GroupChannel.createMyGroupChannelListQuery();

        ChannelListFragment.Builder builder = new ChannelListFragment.Builder()
                .setChannelListAdapter(mAdapter)
                .setUseHeader(false)
                .setGroupChannelListQuery(query);

//        builder.setItemClickListener((view, position, data) -> {
//            view.getId()
//            Intent intent = ChannelActivity.newIntent(getContext(), data.getUrl());
//            startActivity(intent);
//        });

        ChannelListFragment fragment = builder.build();

        FragmentTransaction transaction = getChildFragmentManager()
                .beginTransaction();

        transaction
                .replace(R.id.chat_fragment, fragment)
                .commit();

        return rootLayout;
    }

    @Override
    public void onItemClick(int position, View v) {
        Intent intent = ChannelActivity.newIntent(getContext(), v.get);
        startActivity(intent);
    }

    @Override
    public void onItemLongClick(int position, View v) {

    }
}
