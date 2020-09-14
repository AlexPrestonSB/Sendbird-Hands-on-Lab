package com.sendbird.datinglab.activities;

import android.app.AlertDialog;
import android.content.Intent;

import androidx.annotation.NonNull;

import com.sendbird.datinglab.fragments.CustomChannelFragment;
import com.sendbird.datinglab.utils.StringSet;
import com.sendbird.uikit.activities.ChannelActivity;
import com.sendbird.uikit.fragments.ChannelFragment;

public class CustomChannelActivity extends ChannelActivity {
    private CustomChannelFragment customChannelFragment;

    @Override
    protected ChannelFragment createChannelFragment(@NonNull String channelUrl) {
        customChannelFragment = new CustomChannelFragment();
        return new ChannelFragment.Builder(channelUrl)
                .setCustomChannelFragment(customChannelFragment)
                .setUseHeader(true)
                .setUseHeaderLeftButton(true)
                .setUseHeaderRightButton(true)
                .setUseLastSeenAt(true)
                .setUseTypingIndicator(true)
                .setHeaderLeftButtonListener(null)
                .setHeaderRightButtonListener(v -> showCustomChannelSettingsActivity(channelUrl))
                .setItemClickListener(null)
                .setItemLongClickListener(null)
                .setInputLeftButtonListener(v -> showMessageTypeDialog())
                .setMessageListParams(null)
                .build();
    }

    private void showCustomChannelSettingsActivity(String channelUrl) {
        Intent intent = CustomChannelSettingsActivity.newIntentFromCustomActivity(CustomChannelActivity.this, CustomChannelSettingsActivity.class, channelUrl);
        startActivity(intent);
    }

    private void showMessageTypeDialog() {
        if (customChannelFragment == null) {
            return;
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Pick message type")
                .setMultiChoiceItems(new String[]{StringSet.highlight},
                        new boolean[]{customChannelFragment.isHighlightMode()},
                        (dialog, which, isChecked) -> {
                            customChannelFragment.setHighlightMode(isChecked);
                        })
                .create()
                .show();
    }
}
