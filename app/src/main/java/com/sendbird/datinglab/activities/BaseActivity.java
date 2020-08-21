package com.sendbird.datinglab.activities;

import android.app.Application;
import com.sendbird.datinglab.entities.MyUserInfo;
import com.sendbird.uikit.SendBirdUIKit;
import com.sendbird.uikit.adapter.SendBirdUIKitAdapter;
import com.sendbird.uikit.interfaces.UserInfo;

public class BaseActivity extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        /**
         * TODO SENDBIRD CODE FOR UIKIT HERE
         */
        SendBirdUIKit.init(new SendBirdUIKitAdapter() {
            @Override
            public String getAppId() {
                return "B6DCC89A-9D92-4012-B354-CC41A1BAC5DF";
            }

            @Override
            public String getAccessToken() {
                return "null";
            }

            @Override
            public UserInfo getUserInfo() {
                return new MyUserInfo();
            }
        }, this);


    }
}
