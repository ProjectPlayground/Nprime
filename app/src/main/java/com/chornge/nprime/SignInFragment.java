package com.chornge.nprime;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.chornge.nprime.users.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignInFragment extends Fragment implements View.OnClickListener {

    private static int numberOfFailedLogins = 0;

    Button sign_in_button;
    TextView forgot_password;
    TextView user_login_text;
    EditText edit_text_email_login;
    EditText edit_text_password_login;
    TextInputLayout email_address_login;
    TextInputLayout password_login;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;

    public SignInFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sign_in, container, false);

        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(getActivity());
        Typeface robotoRegular = Typeface.createFromAsset(getActivity().getAssets(),
                "font/Roboto-Regular.ttf");
        Typeface robotoBlack = Typeface.createFromAsset(getActivity().getAssets(),
                "font/Roboto-Black.ttf");

        sign_in_button = (Button) view.findViewById(R.id.sign_in_button);
        sign_in_button.setTypeface(robotoBlack);
        user_login_text = (TextView) view.findViewById(R.id.user_login_text);
        user_login_text.setTypeface(robotoBlack);
        forgot_password = (TextView) view.findViewById(R.id.forgot_password);
        forgot_password.setTypeface(robotoRegular);

        email_address_login = (TextInputLayout) view.findViewById(R.id.email_address_login);
        email_address_login.setTypeface(robotoRegular);
        password_login = (TextInputLayout) view.findViewById(R.id.password_login);
        password_login.setTypeface(robotoRegular);

        edit_text_email_login = (EditText) view.findViewById(R.id.edit_text_email_login);
        edit_text_email_login.setTypeface(robotoRegular);
        edit_text_password_login = (EditText) view.findViewById(R.id.edit_text_password_login);
        edit_text_password_login.setTypeface(robotoRegular);

        final Animation translateFromTop = AnimationUtils.loadAnimation(
                getActivity().getApplicationContext(), R.anim.translate_from_top);
        final Animation translateFromBottom = AnimationUtils.loadAnimation(
                getActivity().getApplicationContext(), R.anim.translate_from_bottom);

        user_login_text.setAnimation(translateFromTop);
        email_address_login.setAnimation(translateFromTop);
        password_login.setAnimation(translateFromTop);
        sign_in_button.setAnimation(translateFromBottom);
        forgot_password.setAnimation(translateFromBottom);

        sign_in_button.setOnClickListener(this);
        forgot_password.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {
        if (view == sign_in_button) {
            signInUser();
        }

        if (view == forgot_password) {
            forgotPassword();
        }
    }

    private void signInUser() {
        final String sign_in_email = edit_text_email_login.getText().toString().trim();
        String sign_in_password = edit_text_password_login.getText().toString().trim();

        if (TextUtils.isEmpty(sign_in_email)) {
            Toast.makeText(getActivity().getApplicationContext(), "Invalid Email",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(sign_in_password)) {
            Toast.makeText(getActivity().getApplicationContext(), "Password must be 6 letters or more",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        progressDialog.setMessage("Logging In User...");
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(sign_in_email, sign_in_password)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        if (task.isSuccessful()) {
                            loginUser();
                            startActivity(new Intent(getActivity().getApplication(),
                                    UserLayoutActivity.class));
                        } else {
                            numberOfFailedLogins++;

                            if (numberOfFailedLogins % 4 == 0) {
                                Snackbar.make(getActivity().findViewById(R.id.user_login_container),
                                        "If you forgot your password, Click on Forgot Password...",
                                        Snackbar.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(getActivity().getApplicationContext(),
                                        "Incorrect Email or Password", Toast.LENGTH_SHORT).show();
                            }
                        }
                        progressDialog.dismiss();
                    }

                    private void loginUser() {
                        firebaseAuth.getCurrentUser().getUid();
                        User user = new User(firebaseAuth.getCurrentUser().getUid(), sign_in_email);
                        user.setUserName(firebaseAuth.getCurrentUser().getDisplayName());

                        //UserLayoutActivity.newInstance(user);
                        //UserProfileFragment.newInstance(user);
                    }
                });
    }

    private void forgotPassword() {
        String emailForReset = edit_text_email_login.getText().toString().trim();

        if (TextUtils.isEmpty(emailForReset)) {
            Toast.makeText(getActivity().getApplicationContext(),
                    "Check that email is not empty", Toast.LENGTH_SHORT).show();
            return;
        }

        firebaseAuth.sendPasswordResetEmail(emailForReset);
        Snackbar.make(getActivity().findViewById(R.id.user_login_container),
                "Password Reset Email Sent to " + emailForReset, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void onResume() {
        super.onResume();
        email_address_login.hasFocus();
        edit_text_email_login.setText("");
        edit_text_password_login.setText("");
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
