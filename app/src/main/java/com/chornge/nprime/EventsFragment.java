package com.chornge.nprime;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v13.app.FragmentCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewSwitcher;

public class EventsFragment extends Fragment implements View.OnClickListener, FragmentCompat.OnRequestPermissionsResultCallback {

    private static final int REQUEST_CAMERA_PERMISSION = 1;
    private static final String FRAGMENT_DIALOG = "dialog";
    boolean isTabLoaded = false;
    ImageView clock_button;
    ImageButton plus;
    ImageButton cameraButton;
    ViewSwitcher viewSwitcher;
    TextView events_text_view;

    //mandatory
    public EventsFragment() {

    }

    public static EventsFragment newInstance() {
        return new EventsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_events, container, false);

        Typeface robotoBold = Typeface.createFromAsset(getActivity().getAssets(), "font/Roboto-Bold.ttf");

        events_text_view = (TextView) view.findViewById(R.id.events_text_view);
        events_text_view.setTypeface(robotoBold);

        clock_button = (ImageView) view.findViewById(R.id.clockButton);
        plus = (ImageButton) view.findViewById(R.id.plusButton);
        cameraButton = (ImageButton) view.findViewById(R.id.pictureButton);
        viewSwitcher = (ViewSwitcher) view.findViewById(R.id.top_layer_layout);

        plus.setOnClickListener(this);
        cameraButton.setOnClickListener(this);

        return view;
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

    @Override
    public void onClick(View view) {
        if (view == cameraButton) {
            //request permission
        }

        if (view == plus) {
//            Intent intent = new Intent(Intent.ACTION_INSERT_OR_EDIT);
//            intent.setData(CalendarContract.Events.CONTENT_URI);
//            //intent.setType("vnd.android.cursor.item/event");
//            startActivity(intent);
            Intent intent = new Intent(getActivity(), CreateEventActivity.class);
            startActivity(intent);

            //Snackbar.make(view, "Event Added", Snackbar.LENGTH_SHORT).show();
//            Fragment createEventFragment = new CreateEventFragment();
//            FragmentTransaction ft = getFragmentManager().beginTransaction();
//            ft.replace(R.id.containerLayout, createEventFragment);
//            ft.addToBackStack(null);
//            ft.commit();
        }
    }
}