package com.sendbird.datinglab.fragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.sendbird.android.GroupChannel;
import com.sendbird.android.GroupChannelListQuery;
import com.sendbird.datinglab.R;
import com.sendbird.datinglab.activities.MainActivity;
import com.sendbird.uikit.fragments.ChannelListFragment;
import com.sendbird.uikit.widgets.ChannelSettingsView;


/**
 * A simple {@link Fragment} subclass.
 */
public class ChatFragment extends Fragment {


    View rootLayout;
    private static final String TAG = MainActivity.class.getSimpleName();
    private RelativeLayout chatLayout;


    public ChatFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootLayout = inflater.inflate(R.layout.fragment_chat, container, false);

        GroupChannelListQuery query = GroupChannel.createMyGroupChannelListQuery();

        ChannelListFragment.Builder builder = new ChannelListFragment
                .Builder()
                .setUseHeader(false)
                .setGroupChannelListQuery(query);

        ChannelListFragment fragment = builder.build();

        FragmentTransaction transaction = getChildFragmentManager()
                .beginTransaction();

        transaction
                .replace(R.id.chat_fragment, fragment)
                .commit();


        return rootLayout;
    }

}
