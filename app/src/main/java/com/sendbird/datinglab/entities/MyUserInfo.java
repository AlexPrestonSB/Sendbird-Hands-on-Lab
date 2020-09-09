package com.sendbird.datinglab.entities;

import com.sendbird.android.User;
import com.sendbird.uikit.interfaces.UserInfo;

/**
 * TODO SENDBIRD
 */
public class MyUserInfo implements UserInfo {

    User user;

  public MyUserInfo(User user) {
      this.user = user;
  }

    @Override
    public String getUserId() {
        return user.getUserId();
    }

    @Override
    public String getNickname() {
        return user.getNickname();
    }

    @Override
    public String getProfileUrl() {
        return user.getProfileUrl();
    }
}
