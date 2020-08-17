package com.sendbird.datinglab.entities;

import com.sendbird.uikit.interfaces.UserInfo;

public class MyUserInfo implements UserInfo {

    private String userId;
    private String nickname;


    public void setUserInfo(String userId, String nickname) {
        this.userId = userId;
        this.nickname = nickname;
    }

    @Override
    public String getUserId() {
        return userId;
    }

    @Override
    public String getNickname() {
        return nickname;
    }

    @Override
    public String getProfileUrl() {
        return "null";
    }
}
