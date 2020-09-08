package com.sendbird.datinglab.activities;

import android.content.Intent;

import androidx.annotation.NonNull;

import com.sendbird.datinglab.R;
import com.sendbird.datinglab.fragments.CustomMemberListFragment;
import com.sendbird.uikit.activities.MemberListActivity;
import com.sendbird.uikit.fragments.MemberListFragment;

public class CustomMemberListActivity extends MemberListActivity {

    @Override
    protected MemberListFragment createMemberListFragment(@NonNull String channelUrl) {
        return new MemberListFragment.Builder(channelUrl)
                .setCustomMemberListFragment(new CustomMemberListFragment())
                .setHeaderTitle("TEST")
                .setUseHeaderRightButton(false)
                .build();
    }

    private void showCustomInviteChannelActivity(String channelUrl) {
//        Intent intent = CustomInviteChannelActivity.newIntentFromCustomActivity(CustomMemberListActivity.this, CustomInviteChannelActivity.class, channelUrl);
//        startActivity(intent);
    }
}
