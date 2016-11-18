package com.chornge.nprime;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class UserLayoutActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        if (savedInstanceState != null) {
//            getSupportFragmentManager().getFragment(savedInstanceState, "key");
//        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_view);

        firebaseAuth = FirebaseAuth.getInstance();

        TabLayout mTabLayout;
        final ViewPager mViewPager;
        final PagerAdapter mPagerAdapter;

        mTabLayout = (TabLayout) findViewById(R.id.tab_layout);
        mViewPager = (ViewPager) findViewById(R.id.view_pager);

        mTabLayout.addTab(mTabLayout.newTab().setIcon(R.drawable.profile_icon));
        mTabLayout.addTab(mTabLayout.newTab().setIcon(R.drawable.chat_icon));
        mTabLayout.addTab(mTabLayout.newTab().setIcon(R.drawable.search_icon));
        mTabLayout.addTab(mTabLayout.newTab().setIcon(R.drawable.event_icon));

        mPagerAdapter = new PagerAdapter(getSupportFragmentManager(), mTabLayout.getTabCount());
        mViewPager.setAdapter(mPagerAdapter);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));
        mTabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }

        });
    }

//    @Override
//    protected void onSaveInstanceState(Bundle outState) {
//        super.onSaveInstanceState(outState);
//
//        //save fragment's instance
//        getSupportFragmentManager().putFragment(outState, "key", fragment);
//    }

    @Override
    public void onStop() {
        super.onStop();
        firebaseAuth.signOut();
    }
}

//    private void setToolbar() {
//        toolbar = (Toolbar) findViewById(R.id.toolbar);
//        if (toolbar != null) {
//            setSupportActionBar(toolbar);
//        }
//    }
