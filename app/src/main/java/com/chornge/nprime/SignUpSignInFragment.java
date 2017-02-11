package com.chornge.nprime;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class SignUpSignInFragment extends Fragment implements View.OnClickListener {

    FirebaseAuth firebaseAuth;
    TextView app_name_text_view_n;
    TextView app_name_text_view_prime;
    private Button bSignIn;
    private Button bSignUp;

    public SignUpSignInFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sign_up_sign_in, container, false);

        firebaseAuth = FirebaseAuth.getInstance();
        Typeface robotoLight = Typeface.createFromAsset(getActivity().getAssets(), "font/Roboto-Light.ttf");
        Typeface robotoLightItalic = Typeface.createFromAsset(getActivity().getAssets(), "font/Roboto-LightItalic.ttf");
        //Typeface robotoBold = Typeface.createFromAsset(this.getAssets(), "font/Roboto-Bold.ttf");

        RelativeLayout relativeLayout = (RelativeLayout) view.findViewById(R.id.sign_up_sign_in_container);
        final Animation translateAnimation = AnimationUtils.loadAnimation(getActivity().getApplicationContext(), R.anim.translate_welcome_screen);
        relativeLayout.startAnimation(translateAnimation);

        bSignUp = (Button) view.findViewById(R.id.bSignUp);
        bSignUp.setTypeface(robotoLight);
        bSignIn = (Button) view.findViewById(R.id.bSignIn);
        bSignIn.setTypeface(robotoLight);

        app_name_text_view_prime = (TextView) view.findViewById(R.id.text_view_prime);
        app_name_text_view_prime.setTypeface(robotoLightItalic);
        app_name_text_view_prime.setTextColor(0xFFE9AD07);

        bSignUp.setOnClickListener(this);
        bSignIn.setOnClickListener(this);

        return view;
    }

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
        if (view == bSignUp) {
            callSignUpFragment();
        }

        if (view == bSignIn) {
            callSignInFragment();
        }
    }

    private void callSignUpFragment() {
        SignUpFragment signUpFragment = new SignUpFragment();
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.welcome_layout_screen, signUpFragment, signUpFragment.getTag())
                .commit();
    }

    private void callSignInFragment() {
        SignInFragment signInFragment = new SignInFragment();
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.welcome_layout_screen, signInFragment, signInFragment.getTag())
                .commit();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }
}
