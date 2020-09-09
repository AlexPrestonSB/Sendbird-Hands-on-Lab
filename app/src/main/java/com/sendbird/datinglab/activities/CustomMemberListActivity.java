package com.sendbird.datinglab.activities;


import androidx.annotation.NonNull;

import com.sendbird.datinglab.fragments.CustomMemberListFragment;
import com.sendbird.uikit.activities.MemberListActivity;
import com.sendbird.uikit.fragments.MemberListFragment;

public class CustomMemberListActivity extends MemberListActivity {

    @Override
    protected MemberListFragment createMemberListFragment(@NonNull String channelUrl) {
        return new MemberListFragment.Builder(channelUrl)
                .setCustomMemberListFragment(new CustomMemberListFragment())
                .setUseHeader(true)
                .setUseHeaderLeftButton(true)
                .setUseHeaderRightButton(false)
                .setHeaderLeftButtonListener(null)
                .setUserListAdapter(null)
                .setItemClickListener(null)
                .setItemLongClickListener(null)
                .build();
    }

}
