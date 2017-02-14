package com.chornge.nprime;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
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
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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
    private User userSignInObject;

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

        setUpViewObjects(view);
        setTypeFaces();
        setUpAnimations();

        sign_in_button.setOnClickListener(this);
        forgot_password.setOnClickListener(this);

        return view;
    }

    private void setUpViewObjects(View view) {
        sign_in_button = (Button) view.findViewById(R.id.sign_in_button);
        user_login_text = (TextView) view.findViewById(R.id.user_login_text);
        forgot_password = (TextView) view.findViewById(R.id.forgot_password);
        email_address_login = (TextInputLayout) view.findViewById(R.id.email_address_login);
        password_login = (TextInputLayout) view.findViewById(R.id.password_login);
        edit_text_email_login = (EditText) view.findViewById(R.id.edit_text_email_login);
        edit_text_password_login = (EditText) view.findViewById(R.id.edit_text_password_login);
    }

    private void setTypeFaces() {
        sign_in_button.setTypeface(useRobotoBlackFontStyle());
        user_login_text.setTypeface(useRobotoBlackFontStyle());
        forgot_password.setTypeface(useRobotoRegularFontStyle());
        email_address_login.setTypeface(useRobotoRegularFontStyle());
        password_login.setTypeface(useRobotoRegularFontStyle());
        edit_text_email_login.setTypeface(useRobotoRegularFontStyle());
        edit_text_password_login.setTypeface(useRobotoRegularFontStyle());
    }

    private Typeface useRobotoBlackFontStyle() {
        return Typeface.createFromAsset(getActivity().getAssets(),
                "font/Roboto-Black.ttf");
    }

    private Typeface useRobotoRegularFontStyle() {
        return Typeface.createFromAsset(getActivity().getAssets(),
                "font/Roboto-Regular.ttf");
    }

    private void setUpAnimations() {
        final Animation translateFromTop = AnimationUtils.loadAnimation(
                getActivity().getApplicationContext(), R.anim.translate_from_top);
        final Animation translateFromBottom = AnimationUtils.loadAnimation(
                getActivity().getApplicationContext(), R.anim.translate_from_bottom);

        user_login_text.setAnimation(translateFromTop);
        email_address_login.setAnimation(translateFromTop);
        password_login.setAnimation(translateFromTop);
        sign_in_button.setAnimation(translateFromBottom);
        forgot_password.setAnimation(translateFromBottom);
    }

    @Override
    public void onClick(View view) {
        if (view == sign_in_button) {
            getUserDetails();
        }

        if (view == forgot_password) {
            forgotPassword();
        }
    }

    private void getUserDetails() {
        final String sign_in_email = edit_text_email_login.getText().toString().trim();
        String sign_in_password = edit_text_password_login.getText().toString().trim();
        checkUserEmail(sign_in_email);
        checkUserPassword(sign_in_password);

        progressDialog.setMessage("Logging In User...");
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(sign_in_email, sign_in_password)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        if (task.isSuccessful()) {
                            String nodeForUser = "users";
                            FirebaseDatabase database = FirebaseDatabase.getInstance();
                            //noinspection ConstantConditions - To prevent NullPointerExcp on .getUiD()
                            DatabaseReference reference = database.getReference(nodeForUser);
                            reference.addChildEventListener(new ChildEventListener() {
                                @Override
                                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                    //noinspection ConstantConditions - To prevent NullPointer on .getUid()
                                    if (dataSnapshot.getKey().equals(firebaseAuth.getCurrentUser().getUid()))
                                        userSignInObject = dataSnapshot.getValue(User.class);
                                }

                                @Override
                                public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                                }

                                @Override
                                public void onChildRemoved(DataSnapshot dataSnapshot) {

                                }

                                @Override
                                public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {
                                    Log.e("SignInFragmentTAG", "Error, could not retrieve data");
                                }
                            });

                            /**
                             * Wait slightly ~ 2 secs before sending user to next screen
                             */

                            final Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    progressDialog.dismiss();
                                    Intent intent = new Intent(getActivity(), UserLayoutActivity.class);
                                    intent.putExtra("userBundleKey", userSignInObject);
                                    startActivity(intent);
                                }
                            }, 2048);

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
                });
    }

    private void checkUserEmail(String sign_in_email) {
        if (TextUtils.isEmpty(sign_in_email)) {
            Toast.makeText(getActivity().getApplicationContext(),
                    "Invalid Email", Toast.LENGTH_SHORT).show();
            return;
        }
    }

    private void checkUserPassword(String sign_in_password) {
        if (TextUtils.isEmpty(sign_in_password)) {
            Toast.makeText(getActivity().getApplicationContext(),
                    "Invalid Password", Toast.LENGTH_SHORT).show();
            return;
        }

        if (sign_in_password.length() < 6) {
            Toast.makeText(getActivity().getApplicationContext(),
                    "Password must be at least 6 characters", Toast.LENGTH_SHORT).show();
            return;
        }
    }

    private void forgotPassword() {
        String emailForReset = edit_text_email_login.getText().toString().trim();
        checkUserEmail(emailForReset);
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
