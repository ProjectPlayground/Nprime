package com.chornge.nprime;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class WelcomeActivity extends AppCompatActivity implements View.OnClickListener {

    FirebaseAuth firebaseAuth;
    TextView app_name_text_view_n;
    TextView app_name_text_view_prime;
    private Button bSignIn;
    private Button bSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        firebaseAuth = FirebaseAuth.getInstance();
        Typeface robotoLight = Typeface.createFromAsset(this.getAssets(), "font/Roboto-Light.ttf");
        Typeface robotoLightItalic = Typeface.createFromAsset(this.getAssets(), "font/Roboto-LightItalic.ttf");
        //Typeface robotoBold = Typeface.createFromAsset(this.getAssets(), "font/Roboto-Bold.ttf");

        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.welcome_layout_screen);
        final Animation translateAnimation = AnimationUtils.loadAnimation(getBaseContext(), R.anim.translate_welcome_screen);

        relativeLayout.startAnimation(translateAnimation);

        bSignIn = (Button) findViewById(R.id.bSignIn);
        bSignIn.setTypeface(robotoLight);
        bSignUp = (Button) findViewById(R.id.bSignUp);
        bSignUp.setTypeface(robotoLight);
        app_name_text_view_prime = (TextView) findViewById(R.id.app_name_text_view_prime);
        app_name_text_view_prime.setTypeface(robotoLightItalic);
        app_name_text_view_prime.setTextColor(0xFFE9AD07);

        bSignIn.setOnClickListener(this);
        bSignUp.setOnClickListener(this);
    }

    private void signInIntent() {
        Intent intent = new Intent(this, SignInActivity.class);
        startActivity(intent);
    }

    private void signUpIntent() {
        Intent intent = new Intent(this, SignUpActivity.class);
        startActivity(intent);
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.welcome_layout_menu, menu);
//        return super.onCreateOptionsMenu(menu);
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            //case R.id.homepage_help_menu:
            //user chooses the 'help' item

            //perform desired action
            //return true;

            default:
                //user's action not recognized, invoke superclass to handle it
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onClick(View view) {
        if (view == bSignIn) {
            signInIntent();
        }

        if (view == bSignUp) {
            signUpIntent();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        firebaseAuth.signOut();
    }

}
