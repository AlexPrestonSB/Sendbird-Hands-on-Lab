# Hands on lab

[![platform](https://img.shields.io/badge/platform-Android-yellow.svg)](https://www.android.com)
[![API](https://img.shields.io/badge/API-21%2B-brightgreen.svg?style=plastic)](https://android-arsenal.com/api?level=21)
[![License](https://img.shields.io/badge/license-Apache%202-4EB1BA.svg?style=flat-square)](https://www.apache.org/licenses/LICENSE-2.0.html)

![](https://firebasestorage.googleapis.com/v0/b/sendbird-preston.appspot.com/o/dating.gif?alt=media&token=414105be-698a-49e6-906f-2690eda65eb5)

This guide is intended for you to have a reference for how to implement chat into a example dating app clone. The repo you have downloaded will have the UI components for chat already implemented baring the actual Chat UI. This guide gives you all the needed code in case you miss anything in the lab. Please note a lot of classes are already filled in (classes denoted by Custom), this is due to the fact we won’t have enough time to cover them in depth, but we still wanted you to have easy access to seeing more customization available with the Sendbird UIkit. 

### Getting Started

1. Clone the repo.
2. Check out the default branch.
3. Open the project and wait for the Gradle to Sync.
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

## Push Notifications

This section is not covered in this lab and only shows the code required to make pushes work, not the prerequsite set up. To get started on the Push Notification prerequsite setup we recommend checking out our [documentation](https://docs.sendbird.com/android/push_notifications#2_push_notifications_for_android), and following the guide on our docs to set up pushes in either [FCM](https://docs.sendbird.com/android/push_notifications#3_push_notifications_for_fcm) or [HMS](https://docs.sendbird.com/android/push_notifications#3_push_notifications_for_hms).

The following is taken from our [Sendbird UIKit sample app](https://github.com/sendbird/SendBird-Android/tree/master/uikit), and covers the main portions of implementation for implementing Push Notifications. 

Create a PushUtils.java class

```sh
 public static void registerPushHandler(SendBirdPushHandler handler) {
        SendBirdPushHelper.registerPushHandler(handler);
    }

  public static void unregisterPushHandler(SendBirdPushHelper.OnPushRequestCompleteListener listener) {
        SendBirdPushHelper.unregisterPushHandler(listener);
    }
```
Create a MyFireBaseMessagingService.java class(This will be different for HMS). This class handles all the set up required to implement Push Notifications. It handles the events for when new tokens are issues, handles receiving pushes, and shows how to handle Push Notifications it terms of displaying them with Notification Manager.  

```sh

public class MyFirebaseMessagingService extends SendBirdPushHandler {

    private static final String TAG = "MyFirebaseMsgService";
    private static final AtomicReference<String> pushToken = new AtomicReference<>();

    public interface ITokenResult {
        void onPushTokenReceived(String pushToken, SendBirdException e);
    }

    @Override
    protected boolean isUniquePushToken() {
        return false;
    }

    @Override
    public void onNewToken(String token) {
        Log.i(TAG, "onNewToken(" + token + ")");
        pushToken.set(token);
    }

    /**
     * Called when message is received.
     *
     * @param remoteMessage Object representing the message received from Firebase Cloud Messaging.
     */
    @Override
    public void onMessageReceived(Context context, RemoteMessage remoteMessage) {
        Logger.d("From: " + remoteMessage.getFrom());
        if (remoteMessage.getData().size() > 0) {
            Logger.d( "Message data payload: " + remoteMessage.getData());
        }

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Logger.d( "Message Notification Body: " + remoteMessage.getNotification().getBody());
        }

        try {
            if (remoteMessage.getData().containsKey(StringSet.sendbird)) {
                String jsonStr = remoteMessage.getData().get(StringSet.sendbird);
                JSONObject sendBird = new JSONObject(jsonStr);
                JSONObject channel = sendBird.getJSONObject(StringSet.channel);
                String channelUrl = channel.getString(StringSet.channel_url);

                SendBird.markAsDelivered(channelUrl);
                sendNotification(context, sendBird);

            }
        } catch (JSONException e) {
            Logger.e(e);
        }
    }

    /**
     * Create and show a simple notification containing the received FCM message.
     *
     * @param sendBird JSONObject payload from FCM
     */
    public static void sendNotification(@NonNull Context context, @NonNull JSONObject sendBird) throws JSONException {
        String message = sendBird.getString(StringSet.message);
        JSONObject channel = sendBird.getJSONObject(StringSet.channel);
        String channelUrl = channel.getString(StringSet.channel_url);

        JSONObject sender = sendBird.getJSONObject(StringSet.sender);
        String senderName = sender.getString(StringSet.name);

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        final String CHANNEL_ID = StringSet.CHANNEL_ID;
        if (Build.VERSION.SDK_INT >= 26) {  // Build.VERSION_CODES.O
            NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID, StringSet.CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(mChannel);
        }

        Intent intent = ChannelListActivity.newRedirectToChannelIntent(context, channelUrl);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0 /* Request code */, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.icon_push_lollipop)
                .setColor(ContextCompat.getColor(context, R.color.primary_300))  // small icon background color
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.drawable.icon_push_oreo))
                .setContentTitle(senderName)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setPriority(Notification.PRIORITY_MAX)
                .setDefaults(Notification.DEFAULT_ALL)
                .setContentIntent(pendingIntent);
        notificationBuilder.setContentText(message);

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }


    public static void getPushToken(ITokenResult listener) {
        String token = pushToken.get();
        if (!TextUtils.isEmpty(token)) {
            listener.onPushTokenReceived(token, null);
            return;
        }

        SendBirdPushHelper.getPushToken((token1, e) -> {
            Log.d(TAG, "FCM token : " + token1);
            if (listener != null) {
                listener.onPushTokenReceived(token1, e);
            }

            if (e == null) {
                pushToken.set(token1);
            }
        });
    }
}

```

In your sample app's BaseApplication.java after the init call.
```sh
PushUtils.registerPushHandler(new MyFirebaseMessagingService());
```
In your LoginActivity.java after the connect call returns successfully before launching the to the next activity. 

```sh
PushUtils.registerPushHandler(new MyFirebaseMessagingService());
```

## Trouble Shooting push notifications

Attached is a [FAQ on Push Notifications](https://view.highspot.com/viewer/5ef55777811717173b465ee0), what they are, how to implement them, and more importantly what to do to help trouble shoot issues with pushes. 

<a name="credits"></a>
## Credits
The UI portion of this app was taken from https://github.com/gabriel-TheCode/TinderClone.

## License
Apache 2.0 license.
