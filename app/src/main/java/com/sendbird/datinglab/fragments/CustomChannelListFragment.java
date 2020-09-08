package com.sendbird.datinglab.fragments;



import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.sendbird.android.GroupChannel;
import com.sendbird.uikit.fragments.ChannelListFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class CustomChannelListFragment extends ChannelListFragment {

    @Override
    protected void leaveChannel(@NonNull GroupChannel channel) {
        super.leaveChannel(channel);
    }

}
