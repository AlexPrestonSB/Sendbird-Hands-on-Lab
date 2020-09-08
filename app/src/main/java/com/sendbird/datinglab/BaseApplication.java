package com.sendbird.datinglab;

import android.app.Application;

import com.sendbird.android.ApplicationUserListQuery;
import com.sendbird.android.SendBird;
import com.sendbird.android.User;
import com.sendbird.datinglab.entities.MyUserInfo;
import com.sendbird.uikit.SendBirdUIKit;
import com.sendbird.uikit.adapter.SendBirdUIKitAdapter;
import com.sendbird.uikit.interfaces.CustomUserListQueryHandler;
import com.sendbird.uikit.interfaces.UserInfo;
import com.sendbird.uikit.interfaces.UserListResultHandler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BaseApplication extends Application {

    private String userId;
    private String userNickname;

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
                return new UserInfo() {
                    @Override
                    public String getUserId() {
                        return userId;
                    }

                    @Override
                    public String getNickname() {
                        return userNickname;
                    }

                    @Override
                    public String getProfileUrl() {
                        return "";
                    }
                };
            }
        }, this);
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setUserNickname(String userNickname) {
        this.userNickname = userNickname;
    }


    public static CustomUserListQueryHandler getCustomUserListQuery() {
        final ApplicationUserListQuery userListQuery = SendBird.createApplicationUserListQuery();
        //userListQuery.setMetaDataFilter("dating", Collections.singletonList("True"));
        return new CustomUserListQueryHandler() {
            @Override
            public void loadInitial(UserListResultHandler handler) {
                userListQuery.setLimit(5);
                userListQuery.next((list, e) -> {
                    if (e != null) {
                        return;
                    }

                    List<MyUserInfo> customUserList = new ArrayList<>();
                    for (User user : list) {
                        customUserList.add(new MyUserInfo(user));
                    }
                    handler.onResult(customUserList, null);
                });
            }

            @Override
            public void loadNext(UserListResultHandler userListResultHandler) {

            }

            @Override
            public boolean hasMore() {
                return false;
            }
        };
    }
}
