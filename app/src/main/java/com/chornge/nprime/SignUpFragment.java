package com.chornge.nprime;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpFragment extends Fragment implements View.OnClickListener {

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

    public SignUpFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sign_up, container, false);

        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(getActivity());

        setUpViewObjects(view);
        setUpAnimations();

        already_a_user.setOnClickListener(this);
        sign_up_button.setOnClickListener(this);

        return view;
    }

    private void setUpViewObjects(View view) {
        user_registration_text = (TextView) view.findViewById(R.id.user_registration_text);
        user_registration_text.setTypeface(useRobotoBlackFontStyle());
        sign_up_button = (Button) view.findViewById(R.id.sign_up_button);
        sign_up_button.setTypeface(useRobotoBlackFontStyle());

        already_a_user = (TextView) view.findViewById(R.id.already_a_user);
        already_a_user.setTypeface(useRobotoRegularFontStyle());
        full_name_sign_up = (TextInputLayout) view.findViewById(R.id.full_name_sign_up);
        full_name_sign_up.setTypeface(useRobotoRegularFontStyle());
        email_address_sign_up = (TextInputLayout) view.findViewById(R.id.email_address_sign_up);
        email_address_sign_up.setTypeface(useRobotoRegularFontStyle());
        password_sign_up = (TextInputLayout) view.findViewById(R.id.password_sign_up);
        password_sign_up.setTypeface(useRobotoRegularFontStyle());

        edit_text_full_name_sign_up = (EditText) view.findViewById(R.id.edit_text_full_name_sign_up);
        edit_text_full_name_sign_up.setTypeface(useRobotoRegularFontStyle());
        edit_text_email_sign_up = (EditText) view.findViewById(R.id.edit_text_email_sign_up);
        edit_text_email_sign_up.setTypeface(useRobotoRegularFontStyle());
        edit_text_password_sign_up = (EditText) view.findViewById(R.id.edit_text_password_sign_up);
        edit_text_password_sign_up.setTypeface(useRobotoRegularFontStyle());
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

        user_registration_text.setAnimation(translateFromTop);
        full_name_sign_up.setAnimation(translateFromTop);
        email_address_sign_up.setAnimation(translateFromTop);
        password_sign_up.setAnimation(translateFromTop);
        sign_up_button.setAnimation(translateFromBottom);
        already_a_user.setAnimation(translateFromBottom);
    }

    @Override
    public void onClick(View view) {
        if (view == already_a_user) {
            switchToSignInScreen();
        }

        if (view == sign_up_button) {
            getUserDetails();
        }
    }

    private void switchToSignInScreen() {
        SignInFragment signInFragment = new SignInFragment();
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.welcome_layout_screen, signInFragment, signInFragment.getTag())
                .commit();
    }

    public void getUserDetails() {
        final String sign_up_fullname = edit_text_full_name_sign_up.getText().toString();
        final String sign_up_email = edit_text_email_sign_up.getText().toString().trim();
        final String sign_up_password = edit_text_password_sign_up.getText().toString().trim();

        checkUserFullName(sign_up_fullname);
        checkUserEmail(sign_up_email);
        checkUserPassword(sign_up_password);
        registerUser(sign_up_fullname, sign_up_email, sign_up_password);
    }

    private void checkUserFullName(String sign_up_fullname) {
        if (TextUtils.isEmpty(sign_up_fullname)) {
            Toast.makeText(getActivity().getApplicationContext(),
                    "Invalid Name", Toast.LENGTH_SHORT).show();
            return;
        }
    }

    private void checkUserEmail(String sign_up_email) {
        if (TextUtils.isEmpty(sign_up_email)) {
            Toast.makeText(getActivity().getApplicationContext(),
                    "Invalid Email", Toast.LENGTH_SHORT).show();
            return;
        }
    }

    private void checkUserPassword(String sign_up_password) {
        if (TextUtils.isEmpty(sign_up_password)) {
            Toast.makeText(getActivity().getApplicationContext(),
                    "Invalid Password", Toast.LENGTH_SHORT).show();
            return;
        }

        if (sign_up_password.length() < 6) {
            Toast.makeText(getActivity().getApplicationContext(),
                    "Password must be at least 6 characters", Toast.LENGTH_SHORT).show();
            return;
        }
    }

    private void registerUser(final String sign_up_fullname, final String sign_up_email,
                              final String sign_up_password) {
        progressDialog.setMessage("Registering New User...");
        progressDialog.show();

        firebaseAuth.createUserWithEmailAndPassword(sign_up_email, sign_up_password)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        if (task.isSuccessful()) {
                            signInUser(sign_up_fullname, sign_up_email, sign_up_password);
                        } else {
                            Toast.makeText(getActivity().getApplicationContext(),
                                    "Registration Failed", Toast.LENGTH_SHORT).show();
                        }
                    }


                });
    }

    private void signInUser(final String sign_up_fullname, final String sign_up_email,
                            String sign_up_password) {
        progressDialog.setMessage("Logging in New User...");
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(sign_up_email, sign_up_password)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        if (task.isSuccessful()) {
                            setUpUser(sign_up_fullname, sign_up_email);
                        } else {
                            Toast.makeText(getActivity().getApplicationContext(),
                                    "Login Failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void setUpUser(String sign_up_fullname, String sign_up_email) {
        User user = new User(sign_up_fullname, sign_up_email);
        saveUserToDatabase(user);
        passUserData(user);
    }

    private void saveUserToDatabase(User user) {
        //noinspection ConstantConditions - To prevent NullPointer on .getUiD()
        String userKey = firebaseAuth.getCurrentUser().getUid();
        user.setUserID(userKey);

        String nodeForAllUsers = "users";
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference(nodeForAllUsers + '/' + userKey);
        reference.setValue(user);
    }

    private void passUserData(User user) {
        Intent intent = new Intent(getActivity(), UserLayoutActivity.class);
        intent.putExtra("userBundleKey", user);
        startActivity(intent);
    }

    @Override
    public void onResume() {
        super.onResume();
        edit_text_full_name_sign_up.setText("");
        edit_text_email_sign_up.setText("");
        edit_text_password_sign_up.setText("");
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
