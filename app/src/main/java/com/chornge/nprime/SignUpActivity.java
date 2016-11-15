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
import com.google.firebase.auth.FirebaseUser;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {

    Button sign_up_button;
    TextView already_a_user;
    TextView user_registration_text;
    EditText edit_text_full_name_sign_up;
    EditText edit_text_email_sign_up;
    EditText edit_text_password_sign_up;
    TextInputLayout full_name_sign_up;
    TextInputLayout email_address_sign_up;
    TextInputLayout password_sign_up;

    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_sign_up);

        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);
        Typeface robotoRegular = Typeface.createFromAsset(this.getAssets(), "font/Roboto-Regular.ttf");
        Typeface robotoBlack = Typeface.createFromAsset(this.getAssets(), "font/Roboto-Black.ttf");

        if (firebaseAuth.getCurrentUser() != null) {
            finish();
            startActivity(new Intent(this, TabLayoutActivity.class));
        }

        sign_up_button = (Button) findViewById(R.id.sign_up_button);
        sign_up_button.setTypeface(robotoBlack);
        user_registration_text = (TextView) findViewById(R.id.user_registration_text);
        user_registration_text.setTypeface(robotoBlack);
        already_a_user = (TextView) findViewById(R.id.already_a_user);
        already_a_user.setTypeface(robotoRegular);

        full_name_sign_up = (TextInputLayout) findViewById(R.id.full_name_sign_up);
        full_name_sign_up.setTypeface(robotoRegular);
        email_address_sign_up = (TextInputLayout) findViewById(R.id.email_address_sign_up);
        email_address_sign_up.setTypeface(robotoRegular);
        password_sign_up = (TextInputLayout) findViewById(R.id.password_sign_up);
        password_sign_up.setTypeface(robotoRegular);

        edit_text_full_name_sign_up = (EditText) findViewById(R.id.edit_text_full_name_sign_up);
        edit_text_full_name_sign_up.setTypeface(robotoRegular);
        edit_text_email_sign_up = (EditText) findViewById(R.id.edit_text_email_sign_up);
        edit_text_email_sign_up.setTypeface(robotoRegular);
        edit_text_password_sign_up = (EditText) findViewById(R.id.edit_text_password_sign_up);
        edit_text_password_sign_up.setTypeface(robotoRegular);

        final Animation translateFromTop = AnimationUtils.loadAnimation(this, R.anim.translate_from_top);
        final Animation translateFromBottom = AnimationUtils.loadAnimation(this, R.anim.translate_from_bottom);

        user_registration_text.setAnimation(translateFromTop);
        full_name_sign_up.setAnimation(translateFromTop);
        email_address_sign_up.setAnimation(translateFromTop);
        password_sign_up.setAnimation(translateFromTop);
        sign_up_button.setAnimation(translateFromBottom);
        already_a_user.setAnimation(translateFromBottom);

        already_a_user.setOnClickListener(this);
        sign_up_button.setOnClickListener(this);
    }

    public void registerUser() {
        String fullName = edit_text_full_name_sign_up.getText().toString();
        String email = edit_text_email_sign_up.getText().toString().trim();
        String password = edit_text_password_sign_up.getText().toString().trim();

        if (TextUtils.isEmpty(fullName)) {
            Snackbar.make(this.findViewById(R.id.user_registration_container), "Please Enter Name", Snackbar.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(email)) {
            Snackbar.make(this.findViewById(R.id.user_registration_container), "Please Enter Email", Snackbar.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            Snackbar.make(this.findViewById(R.id.user_registration_container), "Please Enter Password", Snackbar.LENGTH_SHORT).show();
            return;
        }

        progressDialog.setMessage("Registering User...");
        progressDialog.show();

        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        if (task.isSuccessful()) {
                            finish();
                            callTabLayoutActivity();
                        } else {
                            Toast.makeText(getApplicationContext(), "Registration Failed", Toast.LENGTH_SHORT).show();
                        }
                        progressDialog.dismiss();
                    }
                });
    }

    private void callTabLayoutActivity() {
        FirebaseUser user = firebaseAuth.getCurrentUser();
        String fullName = user.getDisplayName();
        //String fullName = edit_text_full_name_sign_up.getText().toString();
        Tab1Fragment fragment = Tab1Fragment.newInstance(fullName);
//        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
//        .setDisplayName(fullName)
//        .build();
//
//        user.updateProfile(profileUpdates);
        startActivity(new Intent(getApplication(), TabLayoutActivity.class));
    }


    @Override
    public void onClick(View view) {
        if (view == sign_up_button) {
            registerUser();
        }

        if (view == already_a_user) {
            startActivity(new Intent(this, SignInActivity.class));
        }
    }
}
