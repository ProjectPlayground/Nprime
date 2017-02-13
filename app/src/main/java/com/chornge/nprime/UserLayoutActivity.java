package com.chornge.nprime;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.chornge.nprime.users.User;
import com.google.firebase.auth.FirebaseAuth;

public class UserLayoutActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private User userObject;

    public User getUserObject() {
        return userObject;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_view);

        firebaseAuth = FirebaseAuth.getInstance();

        Bundle getUserObjectInBundle = getIntent().getExtras();
        userObject = getUserObjectInBundle.getParcelable("userBundleKey");

        TabLayout mTabLayout;
        final ViewPager mViewPager;
        final PagerAdapter mPagerAdapter;

        mTabLayout = (TabLayout) findViewById(R.id.tab_layout);
        mViewPager = (ViewPager) findViewById(R.id.view_pager);

        mTabLayout.addTab(mTabLayout.newTab().setIcon(R.drawable.profile_icon_gold));
        mTabLayout.addTab(mTabLayout.newTab().setIcon(R.drawable.mail_gold));
        mTabLayout.addTab(mTabLayout.newTab().setIcon(R.drawable.search_gold));
        mTabLayout.addTab(mTabLayout.newTab().setIcon(R.drawable.event_gold));

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

    @Override
    public void onStop() {
        super.onStop();
        firebaseAuth.signOut();
    }
}