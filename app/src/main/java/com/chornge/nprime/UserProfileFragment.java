package com.chornge.nprime;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.drawable.Icon;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Calendar;

import static android.app.Activity.RESULT_OK;

public class UserProfileFragment extends Fragment implements OnMapReadyCallback, View.OnClickListener {

    public static final int RESULT_GALLERY = 0;
    private static final String KEY_NAME = "key_name";
    private static final int SELECT_PICTURE = 1;
    boolean isTabLoaded = false;
    ImageButton userProfilePhoto;
    FirebaseAuth firebaseAuth;
    TextView location_text;

    private DatePickerDialog datePickerDialog;
    private ImageButton calendar_event_button;

    //mandatory
    public UserProfileFragment() {

    }

    public static UserProfileFragment newInstance(String userFullName) {
        UserProfileFragment fragment = new UserProfileFragment();
        Bundle args = new Bundle();
        args.putString(KEY_NAME, userFullName);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            String fullNameFromSignUp = getArguments().getString(KEY_NAME);
        }

        setRetainInstance(true);
    }

    protected void requestPermission(String permissionType, int
            requestCode) {
        int permission = ContextCompat.checkSelfPermission(getContext(),
                permissionType);
        if (permission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{permissionType}, requestCode
            );
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case 101: {
                if (grantResults.length == 0 || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    Toast toast = Toast.makeText(getContext(), "Unable to show Location. Permission is Required", Toast.LENGTH_LONG);
                    toast.show();
                }
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_profile, container, false);

        ((InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE))
                .hideSoftInputFromWindow(view.getWindowToken(), 0);

        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();

        if (user != null) {
            //String userID = firebaseAuth.getCurrentUser().getUid();

            // Name, email address, and profile photo Url
            //String fullName = user.getDisplayName();

            TextView userProfileName = (TextView) view.findViewById(R.id.user_profile_name);
            //userProfileName.setText(FullNameFromSignUp);
        }

        location_text = (TextView) view.findViewById(R.id.location_text);
        location_text.setOnClickListener(this);

        userProfilePhoto = (ImageButton) view.findViewById(R.id.user_profile_photo);
        userProfilePhoto.setOnClickListener(this);

        calendar_event_button = (ImageButton) view.findViewById(R.id.calendar_event_button);
        calendar_event_button.setOnClickListener(this);

        Calendar calendar = Calendar.getInstance();
        datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                Calendar newCalendar = Calendar.getInstance();
                newCalendar.set(year, month, day);
            }

        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

        int LOCATION_REQUEST_CODE = 101;
        requestPermission(android.Manifest.permission.ACCESS_FINE_LOCATION, LOCATION_REQUEST_CODE);

        SupportMapFragment mapFragment = SupportMapFragment.newInstance();
        mapFragment.getMapAsync(this);
        FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.mapContainer, mapFragment).commit();

        return view;
    }

//    public void setTextView(String textName) {
//        TextView textView = (TextView) getView().findViewById(R.id.user_profile_name);
//        textView.setText(textName);
//    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if (isVisibleToUser && !isTabLoaded) {
            Log.e("First Tab Fragment", "loaded");
            isTabLoaded = true;
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        GoogleMap mMap = googleMap;
        UiSettings mapSettings;

        //if (mMap != null) {
        if (ActivityCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(),
                android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setMyLocationButtonEnabled(true);
        mapSettings = mMap.getUiSettings();
        mMap.animateCamera(CameraUpdateFactory.zoomTo(13));
        mapSettings.setScrollGesturesEnabled(false);
        mapSettings.setZoomGesturesEnabled(false);
    }

//    @Override
//    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//
//        if (savedInstanceState != null) {
//            //restore fragment's state
//        }
//    }
//
//    @Override
//    public void onSaveInstanceState(Bundle outState) {
//        super.onSaveInstanceState(outState);
//
//        //save fragment's state here
//    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                Uri selectedImageUri = data.getData();
                String selectedImagePath = getPath(selectedImageUri);
                userProfilePhoto.setImageIcon(Icon.createWithContentUri(selectedImageUri));
                userProfilePhoto.setImageURI(selectedImageUri);
            }
        }
    }

    public String getPath(Uri uri) {
//        // try to retrieve the image from the media store first
//        // this will only work for images selected from gallery

//        String[] projection = {MediaStore.Images.Media.DATA};
//        Cursor cursor = getContext().getContentResolver().query(uri, projection, null, null, null);
//        if (cursor != null) {
//            int column_index = cursor
//                    .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
//            cursor.moveToFirst();
////            cursor.close();
//            return cursor.getString(column_index);
//        }
//        // this is our fallback here
//        return uri.getPath();

        String res = null;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContext().getContentResolver().query(uri, proj, null, null, null);
        if (cursor.moveToFirst()) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            res = cursor.getString(column_index);
        }
        cursor.close();
        return res;
    }

    @Override
    public void onClick(View view) {
        if (view == calendar_event_button) {
            datePickerDialog.show();
        }

        if (view == userProfilePhoto) {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent,
                    "Select Picture"), SELECT_PICTURE);
        }

        if (view == location_text) {
//            MapDialogFragment mapDialogFragment = new MapDialogFragment();
//            //FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
//            FragmentManager fragmentManager = getChildFragmentManager();
//            mapDialogFragment.show(fragmentManager, mapDialogFragment.getTag());
//
//            SupportMapFragment mapFragment = SupportMapFragment.newInstance();
//            mapFragment.getMapAsync(this);
//
//            FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
//            fragmentTransaction.replace(R.id.mapContainer, mapFragment).commit();
//            //fragmentTransaction.replace(R.id.map_dialog_fragment_container, mapFragment).commit();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
//        if (mMap != null)
//            mMap.getCameraPosition();
    }

    @Override
    public void onStop() {
        super.onStop();
        firebaseAuth.signOut();
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
