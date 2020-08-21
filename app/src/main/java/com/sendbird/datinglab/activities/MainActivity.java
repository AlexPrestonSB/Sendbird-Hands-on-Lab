package com.sendbird.datinglab.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.sendbird.android.GroupChannel;
import com.sendbird.android.GroupChannelListQuery;
import com.sendbird.datinglab.R;
import com.sendbird.datinglab.adapters.ViewPagerAdapter;
import com.sendbird.datinglab.fragments.AccountFragment;
import com.sendbird.datinglab.fragments.ChatFragment;
import com.sendbird.datinglab.fragments.SwipeViewFragment;
import com.sendbird.uikit.fragments.ChannelFragment;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {


    private Context mContext;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext = this;

        BottomNavigationView bnv = findViewById(R.id.bottom_navigation);

        /**
         *  GroupChannelListQuery query = GroupChannel.createMyGroupChannelListQuery();
         *
         *         ChannelListFragment.Builder builder = new ChannelListFragment
         *                 .Builder()
         *                 .setUseHeader(false)
         *                 .setGroupChannelListQuery(query);
         *
         *         ChannelListFragment fragment = builder.build();
         */

        GroupChannelListQuery query = GroupChannel.createMyGroupChannelListQuery();

        com.sendbird.uikit.fragments.ChannelListFragment.Builder builder = new com.sendbird.uikit.fragments.ChannelListFragment.Builder()
                .setUseHeader(false)
                .setGroupChannelListQuery(query);

        com.sendbird.uikit.fragments.ChannelListFragment fragment = builder.build();

        ArrayList<Fragment> fragList = new ArrayList<>();
        fragList.add(new AccountFragment());
        fragList.add(new SwipeViewFragment());
        fragList.add(new ChatFragment());
        ViewPagerAdapter pagerAdapter = new ViewPagerAdapter(fragList, getSupportFragmentManager());
        viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(pagerAdapter);
        viewPager.setOffscreenPageLimit(3);
        bnv.setOnNavigationItemSelectedListener(this);

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.account:
                viewPager.setCurrentItem(0);
                break;
            case R.id.fire:
                viewPager.setCurrentItem(1);
                break;
            case R.id.chat:
                viewPager.setCurrentItem(2);
                break;
        }
        return true;
    }
}