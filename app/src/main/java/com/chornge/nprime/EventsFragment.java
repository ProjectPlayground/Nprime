package com.chornge.nprime;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.hardware.Camera;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v13.app.FragmentCompat;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewSwitcher;

public class EventsFragment extends Fragment implements View.OnClickListener,
        FragmentCompat.OnRequestPermissionsResultCallback {

    private static final int REQUEST_CAMERA_PERMISSION = 1;
    private static final String FRAGMENT_DIALOG = "dialog";
    private static final int REQUEST_READ_PERMISSION = 100;
    boolean isTabLoaded = false;

    CameraManager cameraManager;

    ImageView clock_button;
    ImageButton plus;
    ImageButton cameraButton;
    ViewSwitcher viewSwitcher;
    TextView events_text_view;
    Typeface robotoBold;

    //mandatory
    public EventsFragment() {

    }

    public static EventsFragment newInstance() {
        return new EventsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_events, container, false);
        setRetainInstance(true);
        setUpViewObjects(view);

        plus.setOnClickListener(this);
        cameraButton.setOnClickListener(this);

        return view;
    }

    private void setUpViewObjects(View view) {
        robotoBold = Typeface.createFromAsset(getActivity().getAssets(), "font/Roboto-Bold.ttf");
        events_text_view = (TextView) view.findViewById(R.id.events_text_view);
        events_text_view.setTypeface(robotoBold);

        clock_button = (ImageView) view.findViewById(R.id.clockButton);
        plus = (ImageButton) view.findViewById(R.id.plusButton);
        cameraButton = (ImageButton) view.findViewById(R.id.pictureButton);

        viewSwitcher = (ViewSwitcher) view.findViewById(R.id.top_layer_layout);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if (isVisibleToUser && !isTabLoaded) {
            Log.e("Fourth Tab Fragment", "loaded");
            isTabLoaded = true;
        }
    }

    @Override
    public void onClick(View view) {
        if (view == cameraButton) {
            if (requestCameraPermission()) {
                int apiLevelOnPhoneRunningApp = Build.VERSION.SDK_INT;

                if (apiLevelOnPhoneRunningApp >= 21) {
                    //callSupportedAPI();
                    callTestAPI();
                }

                if (apiLevelOnPhoneRunningApp < 21) {
                    callDeprecatedAPI();
                }
            }
        }

        if (view == plus) {
            Intent intent = new Intent(getActivity(), CreateEventActivity.class);
            startActivity(intent);
        }
    }

    private Camera callTestAPI() {
        Camera mCamera = null;
        if (checkIfDeviceHasCamera(getContext())) {
            try {
                mCamera = Camera.open();
            } catch (Exception e) {
                //Camera not available.
            }
        }
        return mCamera;
    }

    private boolean requestCameraPermission() {
        boolean flag = false;
        if (ActivityCompat.checkSelfPermission(getContext(),
                android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{android.Manifest.permission.CAMERA}, REQUEST_READ_PERMISSION);
            flag = true;
        }

        return flag;
    }

    /**
     * Supported api for accessing camera. For devices running lollipop or a greater api build.
     */
    private void callSupportedAPI() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            cameraManager = (CameraManager) getContext().getSystemService(Context.CAMERA_SERVICE);
            String[] cameraListIDs;
            try {
                cameraListIDs = cameraManager.getCameraIdList();
                for (String cameraListID : cameraListIDs) {
                    Log.e("cameraid", cameraListID);
                }
            } catch (CameraAccessException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Deprecated api for accessing camera. For devices running a lower api build than lollipop.
     */
    private Camera callDeprecatedAPI() {
        Camera mCamera = null;
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            if (checkIfDeviceHasCamera(getContext())) {
                try {
                    mCamera = Camera.open();
                } catch (Exception e) {
                    //Camera not available.
                }
            }
        }

        return mCamera;
    }

    private boolean checkIfDeviceHasCamera(Context context) {
        return context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    /**
     * OnDestroy won't be called because of setRetainValue; onDetatch will be called instead.
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }
}