package com.sendbird.datinglab.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.sendbird.android.User;
import com.sendbird.datinglab.BaseApplication;
import com.sendbird.datinglab.R;
import com.sendbird.datinglab.entities.MyUserInfo;
import com.sendbird.uikit.SendBirdUIKit;
import com.sendbird.uikit.log.Logger;
import com.sendbird.uikit.widgets.WaitingDialog;

public class LoginActivity extends AppCompatActivity {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        EditText etUserId = findViewById(R.id.etUserId);
        EditText etNickname = findViewById(R.id.etNickname);

        etUserId.setSelectAllOnFocus(true);
        etNickname.setSelectAllOnFocus(true);

        /**
         * TODO SENDBIRD
         */
        findViewById(R.id.btSignIn).setOnClickListener(v -> {
       
        });
    }
}
