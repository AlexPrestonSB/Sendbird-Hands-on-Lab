package com.sendbird.datinglab.fragments;


import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.mindorks.placeholderview.SwipeDecor;
import com.mindorks.placeholderview.SwipePlaceHolderView;
import com.sendbird.android.ApplicationUserListQuery;
import com.sendbird.android.BaseChannel;
import com.sendbird.android.GroupChannel;
import com.sendbird.android.GroupChannelParams;
import com.sendbird.android.SendBird;
import com.sendbird.android.SendBirdException;
import com.sendbird.android.User;
import com.sendbird.android.UserListQuery;
import com.sendbird.android.UserMessage;
import com.sendbird.datinglab.BaseApplication;
import com.sendbird.datinglab.R;
import com.sendbird.datinglab.utils.Utils;
import com.sendbird.datinglab.entities.Profile;
import com.sendbird.datinglab.entities.TinderCard;
import com.sendbird.uikit.SendBirdUIKit;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class SwipeViewFragment extends Fragment {


    private View rootLayout;
    private FloatingActionButton fabBack, fabLike, fabSkip, fabSuperLike, fabBoost;

    private SwipePlaceHolderView mSwipeView;
    private Context mContext;

    private List<User> users;

    public SwipeViewFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootLayout = inflater.inflate(R.layout.fragment_swipe_view, container, false);

        return rootLayout;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mSwipeView = view.findViewById(R.id.swipeView);
        fabLike = view.findViewById(R.id.fabLike);
        fabSkip = view.findViewById(R.id.fabSkip);


        mContext = getActivity();

        int bottomMargin = Utils.dpToPx(100);
        Point windowSize = Utils.getDisplaySize(getActivity().getWindowManager());
        mSwipeView.getBuilder()
                .setDisplayViewCount(3)
                .setSwipeDecor(new SwipeDecor()
                        .setViewWidth(windowSize.x)
                        .setViewHeight(windowSize.y - bottomMargin)
                        .setViewGravity(Gravity.TOP)
                        .setPaddingTop(20)
                        .setRelativeScale(0.01f)
                        .setSwipeInMsgLayoutId(R.layout.tinder_swipe_in_msg_view)
                        .setSwipeOutMsgLayoutId(R.layout.tinder_swipe_out_msg_view));


        /**
         * Sendbird
         */

        ApplicationUserListQuery query = SendBird.createApplicationUserListQuery();
        query.setLimit(100); //Whatever you want
        query.setMetaDataFilter("dating", Collections.singletonList("True"));

        query.next((list, e) -> {
            if (e != null) {
                return;
            }

            users = list;
            for (User user : list) {
                mSwipeView.addView(new TinderCard(mContext, user, mSwipeView));
            }

        });




        fabSkip.setOnClickListener(v -> {
            animateFab(fabSkip);
            mSwipeView.doSwipe(false);
        });

        fabLike.setOnClickListener(v -> {
            animateFab(fabLike);
            //Create new Channel
            TinderCard user = (TinderCard) mSwipeView.getAllResolvers().get(0);
            User profile = user.getUser();
            createChannelWithMatch(profile);

            mSwipeView.doSwipe(true);
        });

    }


    private void animateFab(final FloatingActionButton fab){
        fab.animate().scaleX(0.7f).setDuration(100).withEndAction(() -> fab.animate().scaleX(1f).scaleY(1f));
    }

    private void createChannelWithMatch(User user) {
        GroupChannelParams params = new GroupChannelParams();

        params.addUser(user);

        GroupChannel.createChannel(params, new GroupChannel.GroupChannelCreateHandler() {
            @Override
            public void onResult(GroupChannel groupChannel, SendBirdException e) {
                if (e != null) {
                    return;
                }
                Log.e("test", "test");
            }
        });
    }

}
