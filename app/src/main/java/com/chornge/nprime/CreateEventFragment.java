package com.chornge.nprime;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;

import com.chornge.nprime.events.Event;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class CreateEventFragment extends Fragment implements View.OnClickListener {

    private static final int SELECT_PICTURE = 1;
    private static final String ARG_PARAM1 = "param1";
    private FloatingActionButton floatingActionButton;
    private DatePickerDialog datePickerDialogFrom;
    private DatePickerDialog datePickerDialogTo;
    private TimePickerDialog timePickerDialogStart;
    private TimePickerDialog timePickerDialogEnd;
    private SimpleDateFormat dateFormatter;
    private SimpleDateFormat timeFormatter;
    private TextInputLayout create_event_input_layout; // TODO: 2/9/2017 Add Animation to TextInputLayout
    private Switch eventTypeSwitch;
    private TextView add_an_image_text;
    private EditText edit_text_create_event_name;
    private EditText editTextFromDate;
    private EditText editTextToDate;
    private EditText editTextStartTime;
    private EditText editTextEndTime;
    private ImageButton create_event_image;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;

    public CreateEventFragment() {
        // Required empty public constructor
    }

    public static CreateEventFragment newInstance(String param1) {
        CreateEventFragment fragment = new CreateEventFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            //;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_create_event, container, false);

        progressDialog = new ProgressDialog(getActivity());
        firebaseAuth = FirebaseAuth.getInstance();
        //firebaseUser = firebaseAuth.getCurrentUser().getUid();

        Calendar newCal = Calendar.getInstance();
        Calendar eventCalendar = Calendar.getInstance();
        dateFormatter = new SimpleDateFormat("EEE, MMM d, yyyy", Locale.US);
        timeFormatter = new SimpleDateFormat("h:mm a", Locale.US);

        eventTypeSwitch = (Switch) view.findViewById(R.id.event_switch);
        floatingActionButton = (FloatingActionButton) view.findViewById(R.id.create_event_fab);
        create_event_input_layout = (TextInputLayout) view.findViewById(R.id.create_event_input_layout);
        edit_text_create_event_name = (EditText) view.findViewById(R.id.edit_text_create_event_name);
        editTextFromDate = (EditText) view.findViewById(R.id.edit_text_start_date);
        editTextToDate = (EditText) view.findViewById(R.id.edit_text_end_date);
        editTextStartTime = (EditText) view.findViewById(R.id.edit_text_start_time);
        editTextEndTime = (EditText) view.findViewById(R.id.edit_text_end_time);
        add_an_image_text = (TextView) view.findViewById(R.id.add_an_image_text);
        create_event_image = (ImageButton) view.findViewById(R.id.create_event_image);

        editTextFromDate.setInputType(InputType.TYPE_CLASS_DATETIME);
        //editTextFromDate.setText(dateFormatter.format(newCal.getTime().getDate()));
        editTextToDate.setInputType(InputType.TYPE_CLASS_DATETIME);
        //editTextToDate.setText(dateFormatter.format(newCal.getTime()));

        editTextStartTime.setInputType(InputType.TYPE_CLASS_DATETIME);
        //editTextStartTime.setText(timeFormatter.format(newCal.getTime()));
        editTextEndTime.setInputType(InputType.TYPE_CLASS_DATETIME);
        //editTextEndTime.setText(timeFormatter.format(newCal.getTime()));

        floatingActionButton.hasFocus();

        editTextFromDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (getView().hasWindowFocus())
                    datePickerDialogFrom.show();
                view.clearFocus();
            }
        });

        editTextToDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (getView().hasWindowFocus())
                    datePickerDialogTo.show();
                view.clearFocus();
            }
        });

        editTextStartTime.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (getView().hasWindowFocus())
                    timePickerDialogStart.show();
                view.clearFocus();
            }
        });

        editTextEndTime.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (getView().hasWindowFocus())
                    timePickerDialogEnd.show();
                view.clearFocus();
            }
        });

        datePickerDialogFrom = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                Calendar newCalFrom = Calendar.getInstance();
                newCalFrom.set(year, month, day);
                editTextFromDate.setText(dateFormatter.format(newCalFrom.getTime()));
            }

        }, newCal.get(Calendar.YEAR), newCal.get(Calendar.MONTH), newCal.get(Calendar.DAY_OF_MONTH));

        datePickerDialogTo = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                Calendar newCalTo = Calendar.getInstance();
                newCalTo.set(year, month, day);
                editTextToDate.setText(dateFormatter.format(newCalTo.getTime()));
            }

        }, newCal.get(Calendar.YEAR), newCal.get(Calendar.MONTH), newCal.get(Calendar.DAY_OF_MONTH));

        timePickerDialogStart = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hour, int minute) {
                Calendar newTimeStart = Calendar.getInstance();
                newTimeStart.set(Calendar.HOUR_OF_DAY, hour);
                newTimeStart.set(Calendar.MINUTE, minute);
                editTextStartTime.setText(timeFormatter.format(newTimeStart.getTime()));
            }

        }, newCal.get(Calendar.HOUR_OF_DAY), newCal.get(Calendar.MINUTE), true);

        timePickerDialogEnd = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hour, int minute) {
                Calendar newTimeStart = Calendar.getInstance();
                newTimeStart.set(Calendar.HOUR_OF_DAY, hour);
                newTimeStart.set(Calendar.MINUTE, minute);
                editTextEndTime.setText(timeFormatter.format(newTimeStart.getTime()));
            }

        }, newCal.get(Calendar.HOUR_OF_DAY), newCal.get(Calendar.MINUTE), true);

        setOnClickListeners();

        return view;
    }

    private void setOnClickListeners() {
        create_event_image.setOnClickListener(this);
        floatingActionButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == create_event_image) {
            Intent intent = new Intent().setType("image/*").setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_PICTURE);
            add_an_image_text.setVisibility(View.INVISIBLE);
        }

        if (view == floatingActionButton) {
            saveEventToDatabase(initializeEvent());

            /**
             * Wait slightly ~ 2 secs before returning user to previous screen
             */

            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    progressDialog.dismiss();
                    //getActivity().getFragmentManager().beginTransaction().remove(this).commit();
                    getActivity().getSupportFragmentManager().popBackStack();
                }
            }, 2048);
        }
    }

    private Event initializeEvent() {
        progressDialog.setMessage("Creating " + edit_text_create_event_name.getText().toString() + "...");
        progressDialog.show();
        Event event = new Event(firebaseAuth.getCurrentUser().getUid());
        event.setEventName(edit_text_create_event_name.getText().toString());
        if (eventTypeSwitch.isChecked()) {
            event.setEventType("Public");
        } else {
            event.setEventType("Private");
        }

        event.setEventDescription("Event Description");

        return event;
    }

    private void saveEventToDatabase(Event event) {

        String nodeForAllEvents = "dbroot/events";
        FirebaseDatabase eventDb = FirebaseDatabase.getInstance();
        DatabaseReference eventReference = eventDb.getReference(nodeForAllEvents);
        progressDialog.setMessage("Creating " + edit_text_create_event_name.getText().toString() + "...");
        progressDialog.show();
        String eventKey = eventReference.push().getKey();
        event.setEventID(eventKey);
        eventReference.setValue(eventKey);
        eventReference.child(nodeForAllEvents + '/' + eventKey).setValue(event);
        addEventToCreatorEventList(event, firebaseAuth.getCurrentUser().getUid());
    }

    private void addEventToCreatorEventList(Event event, String cID) {
        String eventID = event.getEventID();
        String nodeForThisUser = "dbroot/users" + '/' + cID;
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference(nodeForThisUser
                + '/' + "userEvents");
        databaseReference.setValue(eventID);
    }

    @Override
    public void onResume() {
        super.onResume();
        edit_text_create_event_name.clearFocus();
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