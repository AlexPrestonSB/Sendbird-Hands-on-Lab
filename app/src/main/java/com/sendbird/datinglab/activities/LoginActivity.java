package com.sendbird.datinglab.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.sendbird.android.SendBird;
import com.sendbird.datinglab.R;
import com.sendbird.datinglab.entities.MyUserInfo;
import com.sendbird.uikit.BuildConfig;
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


        findViewById(R.id.btSignIn).setOnClickListener(v -> {
            String userId = etUserId.getText().toString();
            // Remove all spaces from userID
            userId = userId.replaceAll("\\s", "");

            String userNickname = etNickname.getText().toString();
//            if (TextUtils.isEmpty(userId) || TextUtils.isEmpty(userNickname)) {
//                return;
//            }

            MyUserInfo userInfo = new MyUserInfo();
            userInfo.setUserInfo(userId, userNickname);

            WaitingDialog.show(this);
            SendBirdUIKit.connect((user, e) -> {
                if (e != null) {
                    Logger.e(e);
                    WaitingDialog.dismiss();
                    return;
                }
                WaitingDialog.dismiss();
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            });
        });
    }
}
