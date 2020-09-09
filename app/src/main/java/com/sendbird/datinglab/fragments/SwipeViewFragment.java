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
import com.sendbird.android.GroupChannel;
import com.sendbird.android.GroupChannelParams;
import com.sendbird.android.SendBird;
import com.sendbird.android.SendBirdException;
import com.sendbird.android.User;
import com.sendbird.datinglab.R;
import com.sendbird.datinglab.utils.Utils;
import com.sendbird.datinglab.entities.DatingCard;
import com.sendbird.uikit.log.Logger;

import java.util.Collections;

/**
 * A simple {@link Fragment} subclass.
 */
public class SwipeViewFragment extends Fragment {


    final private String SWIPE_FRAGMENT = "SWIPE_FRAGMENT";
    private View rootLayout;
    private FloatingActionButton fabLike, fabSkip;

    private SwipePlaceHolderView mSwipeView;
    private Context mContext;

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
         * TODO SENDBIRD
         */

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

        //END

        fabSkip.setOnClickListener(v -> {
            animateFab(fabSkip);
            mSwipeView.doSwipe(false);
        });

        fabLike.setOnClickListener(v -> {
            animateFab(fabLike);

            //TODO SENDBIRD IMPL
            DatingCard user = (DatingCard) mSwipeView.getAllResolvers().get(0);
            User profile = user.getUser();
            createChannelWithMatch(profile);
            //END

            mSwipeView.doSwipe(true);
        });

    }


    private void animateFab(final FloatingActionButton fab) {
        fab.animate().scaleX(0.7f).setDuration(100).withEndAction(() -> fab.animate().scaleX(1f).scaleY(1f));
    }

    /**
     * TODO SENDBIRD
     *
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

}
