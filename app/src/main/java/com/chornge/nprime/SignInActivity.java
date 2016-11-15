package com.chornge.nprime;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignInActivity extends AppCompatActivity implements View.OnClickListener {

    Button sign_in_button;
    TextView forgot_password;
    TextView user_login_text;
    EditText edit_text_email_login;
    EditText edit_text_password_login;
    TextInputLayout email_address_login;
    TextInputLayout password_login;

    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);
        Typeface robotoRegular = Typeface.createFromAsset(this.getAssets(), "font/Roboto-Regular.ttf");
        Typeface robotoBlack = Typeface.createFromAsset(this.getAssets(), "font/Roboto-Black.ttf");

        if (firebaseAuth.getCurrentUser() != null) {
            finish();
            callTabLayoutActivity();
        }

        sign_in_button = (Button) findViewById(R.id.sign_in_button);
        sign_in_button.setTypeface(robotoBlack);
        user_login_text = (TextView) findViewById(R.id.user_login_text);
        user_login_text.setTypeface(robotoBlack);
        forgot_password = (TextView) findViewById(R.id.forgot_password);
        forgot_password.setTypeface(robotoRegular);

        email_address_login = (TextInputLayout) findViewById(R.id.email_address_login);
        email_address_login.setTypeface(robotoRegular);
        password_login = (TextInputLayout) findViewById(R.id.password_login);
        password_login.setTypeface(robotoRegular);

        edit_text_email_login = (EditText) findViewById(R.id.edit_text_email_login);
        edit_text_email_login.setTypeface(robotoRegular);
        edit_text_password_login = (EditText) findViewById(R.id.edit_text_password_login);
        edit_text_password_login.setTypeface(robotoRegular);

        final Animation translateFromTop = AnimationUtils.loadAnimation(this, R.anim.translate_from_top);
        final Animation translateFromBottom = AnimationUtils.loadAnimation(this, R.anim.translate_from_bottom);

        user_login_text.setAnimation(translateFromTop);
        email_address_login.setAnimation(translateFromTop);
        password_login.setAnimation(translateFromTop);
        sign_in_button.setAnimation(translateFromBottom);
        forgot_password.setAnimation(translateFromBottom);

        sign_in_button.setOnClickListener(this);
        forgot_password.setOnClickListener(this);
    }

    public void signInUser() {
        String email = edit_text_email_login.getText().toString().trim();
        String password = edit_text_password_login.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            Snackbar.make(this.findViewById(R.id.user_login_container), "Please Enter Email", Snackbar.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            Snackbar.make(this.findViewById(R.id.user_login_container), "Please Enter Password", Snackbar.LENGTH_SHORT).show();
            return;
        }

        progressDialog.setMessage("Logging In User...");
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        if (task.isSuccessful()) {
                            finish();
                            callTabLayoutActivity();
                        } else {
                            Toast.makeText(getApplicationContext(), "Incorrect Email or Password", Toast.LENGTH_SHORT).show();
                        }
                        progressDialog.dismiss();
                    }
                });
    }

    private void callTabLayoutActivity() {
        startActivity(new Intent(this, TabLayoutActivity.class));
    }

    @Override
    public void onClick(View view) {
        if (view == sign_in_button) {
            signInUser();
        }

        if (view == forgot_password) {
            finish();
            Snackbar.make(this.findViewById(R.id.user_login_container), "Password Reset Email Sent", Snackbar.LENGTH_SHORT).show();
        }
    }
}