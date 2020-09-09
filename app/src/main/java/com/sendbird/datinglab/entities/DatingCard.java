package com.sendbird.datinglab.entities;

import android.content.Context;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mindorks.placeholderview.SwipePlaceHolderView;
import com.mindorks.placeholderview.annotations.Layout;
import com.mindorks.placeholderview.annotations.Resolve;
import com.mindorks.placeholderview.annotations.View;
import com.mindorks.placeholderview.annotations.swipe.SwipeCancelState;
import com.mindorks.placeholderview.annotations.swipe.SwipeIn;
import com.mindorks.placeholderview.annotations.swipe.SwipeInState;
import com.mindorks.placeholderview.annotations.swipe.SwipeOut;
import com.mindorks.placeholderview.annotations.swipe.SwipeOutState;
import com.sendbird.android.User;
import com.sendbird.datinglab.R;

@Layout(R.layout.adapter_dating_card)
public class DatingCard {

    @View(R.id.profileImageView)
    private ImageView profileImageView;

    @View(R.id.nameAgeTxt)
    private TextView nameAgeTxt;

    @View(R.id.locationNameTxt)
    private TextView locationNameTxt;

    private User mUser;
    private Context mContext;
    private SwipePlaceHolderView mSwipeView;

    //TODO SENDBIRD
    public DatingCard(Context context, User user, SwipePlaceHolderView swipeView) {
        mContext = context;
         mUser = user;
        mSwipeView = swipeView;
    }

    public User getUser() {
        return mUser;
    }

    //END

    @Resolve
    private void onResolved(){
        Glide.with(mContext).load(mUser.getProfileUrl()).into(profileImageView);
        nameAgeTxt.setText(mUser.getNickname() + ", 25");
        locationNameTxt.setText("San Mateo");
    }

    @SwipeOut
    private void onSwipedOut(){
        Log.d("EVENT", "onSwipedOut");
        mSwipeView.addView(this);
    }

    @SwipeCancelState
    private void onSwipeCancelState(){
        Log.d("EVENT", "onSwipeCancelState");
    }

    @SwipeIn
    private void onSwipeIn(){
        Log.d("EVENT", "onSwipedIn");
    }

    @SwipeInState
    private void onSwipeInState(){
        Log.d("EVENT", "onSwipeInState");
    }

    @SwipeOutState
    private void onSwipeOutState(){
        Log.d("EVENT", "onSwipeOutState");
    }
}