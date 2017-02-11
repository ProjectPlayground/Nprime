package com.chornge.nprime;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
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

import com.chornge.nprime.users.User;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

import static android.app.Activity.RESULT_OK;

public class UserProfileFragment extends Fragment implements OnMapReadyCallback,
        View.OnClickListener, Runnable {

    public static final int RESULT_GALLERY = 0;
    private static final String KEY_NAME = "key_name";
    private static final int SELECT_PICTURE = 1;
    boolean isTabLoaded = false;
    ImageButton userProfilePhoto;
    FirebaseAuth firebaseAuth;
    TextView location_text;

    private User userObject;

    private DatePickerDialog datePickerDialog;
    private ImageButton calendar_event_button;

    //mandatory
    public UserProfileFragment() {

    }

//    public static UserProfileFragment newInstance(User user) {
//        UserProfileFragment fragment = new UserProfileFragment();
//        Bundle userBundle = new Bundle();
//        userBundle.putParcelable("userProfileData", user);
//        fragment.setArguments(userBundle);
//        return fragment;
//    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SupportMapFragment mapFragment = SupportMapFragment.newInstance();
        mapFragment.getMapAsync(this);
        FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.mapContainer, mapFragment).commit();
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
                    Toast toast = Toast.makeText(getContext(),
                            "Unable to show Location. Permission is Required", Toast.LENGTH_LONG);
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

        setRetainInstance(true);

        ((InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE))
                .hideSoftInputFromWindow(view.getWindowToken(), 0);

        firebaseAuth = FirebaseAuth.getInstance();

        userObject = new User();
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        String node = "dbroot/users" + '/' + firebaseAuth.getCurrentUser().getUid();
        DatabaseReference databaseReference = firebaseDatabase.getReference(node);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                userObject = (User) dataSnapshot.getValue();
                userObject.getFullName();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        TextView userProfileName = (TextView) view.findViewById(R.id.user_profile_name);
        //userProfileName.setText(userObject.getFullName());

        userProfileName.setTextColor(Color.BLACK);

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
        UiSettings mapSettings;

        if (ActivityCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(),
                android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        googleMap.setMyLocationEnabled(true);
        googleMap.getUiSettings().setMyLocationButtonEnabled(true);
        mapSettings = googleMap.getUiSettings();
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(13));
        mapSettings.setScrollGesturesEnabled(false);
        mapSettings.setZoomGesturesEnabled(false);
    }

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
            MapDialogFragment mapDialogFragment = new MapDialogFragment();

//            //FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
//            FragmentManager fragmentManager = getChildFragmentManager();
//            mapDialogFragment.show(fragmentManager, mapDialogFragment.getTag());
//
//            SupportMapFragment mapFragment = SupportMapFragment.newInstance();
//            mapFragment.getMapAsync(this);
//
//            FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
//            //fragmentTransaction.replace(R.id.map_dialog_fragment_container, mapFragment).commit();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
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

    @Override
    public void run() {
        int i = 0;
    }
}
