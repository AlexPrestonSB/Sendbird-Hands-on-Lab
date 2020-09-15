# Hands on lab

[![platform](https://img.shields.io/badge/platform-Android-yellow.svg)](https://www.android.com)
[![API](https://img.shields.io/badge/API-21%2B-brightgreen.svg?style=plastic)](https://android-arsenal.com/api?level=21)
[![License](https://img.shields.io/badge/license-Apache%202-4EB1BA.svg?style=flat-square)](https://www.apache.org/licenses/LICENSE-2.0.html)

![](https://firebasestorage.googleapis.com/v0/b/sendbird-preston.appspot.com/o/dating.gif?alt=media&token=414105be-698a-49e6-906f-2690eda65eb5)

This guide is intended for you to have a reference for how to implement chat into a example dating app clone. The repo you have downloaded will have the UI components for chat already implemented baring the actual Chat UI. This guide gives you all the needed code in case you miss anything in the lab. Please note a lot of classes are already filled in (classes denoted by Custom), this is due to the fact we won’t have enough time to cover them in depth, but we still wanted you to have easy access to seeing more customization available with the Sendbird UIkit. 

### Getting Started

1. Clone the repo.
2. Check out the default branch
3. Open the project and wait for the Gradle to Sync
4. Run the project on an emulator or physical device.  

The default branch is the finished product. The branch labled 'starter-project' is the shell where we will be starting. 

### Profile Urls for user creation

- ##### [User1](https://firebasestorage.googleapis.com/v0/b/sendbird-preston.appspot.com/o/ZoeOrozco.png?alt=media&token=0e5283f9-8388-4d27-9d42-30af10c52f04)


- ##### [User2](https://firebasestorage.googleapis.com/v0/b/sendbird-preston.appspot.com/o/GinoFlores.png?alt=media&token=3e2dbfad-384c-4a98-9298-e9fca1367672)


- ##### [User3](https://firebasestorage.googleapis.com/v0/b/sendbird-preston.appspot.com/o/BrianFranks.png?alt=media&token=56a87651-c4b1-4ed4-9a7e-143bf09b4212)


- ##### [User4](https://firebasestorage.googleapis.com/v0/b/sendbird-preston.appspot.com/o/MelodyColes.png?alt=media&token=6f5742d2-8e96-4159-9603-ec91909cf3c0)


- ##### [User5](https://firebasestorage.googleapis.com/v0/b/sendbird-preston.appspot.com/o/Alicja%20Eastwood.png?alt=media&token=be2f2b8e-eed9-483c-aeb4-33236ba9c1b4)


- ##### [User6](https://firebasestorage.googleapis.com/v0/b/sendbird-preston.appspot.com/o/JakeLee.png?alt=media&token=36fe5902-b44f-4a91-935e-393417a9e924)


# Implementation


##### 1. App level Build.gradle
The following handles adding the Sendbird UIKit to your project. 

`implementation 'com.sendbird.sdk:uikit:1.1.3'`


##### 2. BaseApplication.java
The following handles initializing the UIKit SDK with your AppID. 

```sh
SendBirdUIKit.init(new SendBirdUIKitAdapter() {

   @Override

   public String getAppId() {

       return APP_ID;

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
```

##### 3. LoginActivity.java
The following handles connecting your user to Sendbird. 

```sh
String userId = etUserId.getText().toString();
// Remove all spaces from userID
userId = userId.replaceAll("\\s", "");

String userNickname = etNickname.getText().toString();
if (userId.isEmpty() || userNickname.isEmpty()) {
   return;
}

((BaseApplication)getApplication()).setUserId(userId);
((BaseApplication)getApplication()).setUserNickname(userNickname);

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
```

##### 4. MainActivity.java
The following code handles launching the UIKit.

```sh
...

fragList.add(getChatFragment()); //Add the Fragment to the ViewPagerAdapter

...

/**
* TODO SENDBIRD
* This method handles the creation of the ChannelListFragment with some custom options
* which will be set in the above code. 
* @return CustomChannelActivityFragment
*/
private Fragment getChatFragment() {
   GroupChannelListQuery query = GroupChannel.createMyGroupChannelListQuery();
   query.setIncludeEmpty(true);
   query.setOrder(GroupChannelListQuery.Order.CHRONOLOGICAL);

   ChannelListFragment.Builder builder = new ChannelListFragment.Builder()
           .setUseHeader(false)
           .setItemClickListener(((view, i, channel) -> showCustomChannelActivity(channel.getUrl())))
           .setGroupChannelListQuery(query);

   return builder.build();
}


/**
* TODO SENDBIRD
* @param channelUrl to launch Custom Channel Activity
*/
private void showCustomChannelActivity(String channelUrl) {
   Intent intent = CustomChannelActivity.newIntentFromCustomActivity(this, CustomChannelActivity.class, channelUrl);
   startActivity(intent);
}

```
##### 5. AccountFragment.java
The following handles setting your logged in user info on the AccountFragment. 
```sh
Glide.with(this).load(SendBird.getCurrentUser().getProfileUrl()).fitCenter().into(profileImage);
userName.setText(SendBird.getCurrentUser().getNickname());
userInfo.setText("About Me: Likes " + SendBird.getCurrentUser().getMetaData("Likes"));
```

##### 6. SwipeViewFragment.java
The following handles getting a list of users, creating cardviews for them, and adding them to the list. 

```sh
ApplicationUserListQuery query = SendBird.createApplicationUserListQuery();
query.setLimit(100); //Whatever you want
query.setMetaDataFilter("dating", Collections.singletonList("True"));
query.setMetaDataFilter("sex", Collections.singletonList("female"));

query.next((list, e) -> {
   if (e != null) {
       Log.e(SWIPE_FRAGMENT, e.getMessage());
       return;
   }

   for (User user : list) {
       if (!user.getUserId().equals(SendBird.getCurrentUser().getUserId())) {
           mSwipeView.addView(new DatingCard(mContext, user, mSwipeView));
       }
   }

});

...
//Setting Click Listener to handle the "Like" action
fabLike.setOnClickListener(v -> {
   animateFab(fabLike);

   //TODO SENDBIRD IMPL
   DatingCard user = (DatingCard) mSwipeView.getAllResolvers().get(0);
   User profile = user.getUser();
   createChannelWithMatch(profile);
   //END

   mSwipeView.doSwipe(true);
});


...

/**
* TODO SENDBIRD
* Creates a empty Distinct channel with the "Liked" User
* @param user
*/
private void createChannelWithMatch(User user) {
   GroupChannelParams params = new GroupChannelParams();
   params.setDistinct(true)
           .addUser(user);

   GroupChannel.createChannel(params, (groupChannel, e) -> {
       if (e != null) {
           Logger.e(e.getMessage());
           return;
       }
       Logger.d(groupChannel.getUrl() + ": Channel Created");

   });
}

```

<a name="credits"></a>
## Credits
The UI portion of this app was taken from https://github.com/gabriel-TheCode/TinderClone.

## License
Apache 2.0 license.
