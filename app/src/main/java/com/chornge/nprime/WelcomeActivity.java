package com.chornge.nprime;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        callSignUpSignInFragment();
    }

    public void callSignUpSignInFragment() {
        SignUpSignInFragment signUpSignInFragment = new SignUpSignInFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.welcome_layout_screen, signUpSignInFragment, signUpSignInFragment.getTag())
                .commit();
    }
}
