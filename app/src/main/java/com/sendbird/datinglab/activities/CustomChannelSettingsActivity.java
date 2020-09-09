package com.sendbird.datinglab.activities;

import android.content.Intent;

import androidx.annotation.NonNull;

import com.sendbird.datinglab.R;
import com.sendbird.datinglab.fragments.CustomChannelSettingsFragment;
import com.sendbird.uikit.activities.ChannelSettingsActivity;
import com.sendbird.uikit.fragments.ChannelSettingsFragment;

public class CustomChannelSettingsActivity extends ChannelSettingsActivity {
    @Override
    protected ChannelSettingsFragment createChannelSettingsFragment(@NonNull String channelUrl) {
        return new ChannelSettingsFragment.Builder(channelUrl)
                .setCustomChannelSettingsFragment(new CustomChannelSettingsFragment())
                .setUseHeader(true)
                .setHeaderTitle(getString(R.string.sb_text_header_channel_settings))
                .setUseHeaderLeftButton(true)
                .setHeaderLeftButtonIconResId(R.drawable.icon_arrow_left)
                .setHeaderLeftButtonListener(null)
                .setMemberSettingClickListener(v -> showCustomMemberListActivity(channelUrl))
                .build();
    }

    private void showCustomMemberListActivity(String channelUrl) {
        Intent intent = CustomMemberListActivity.newIntentFromCustomActivity(CustomChannelSettingsActivity.this, CustomMemberListActivity.class, channelUrl);
        startActivity(intent);
    }
}
