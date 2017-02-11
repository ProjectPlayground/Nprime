package com.chornge.nprime;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.Icon;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.View;
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

public class CreateEventActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int SELECT_PICTURE = 1;
    private FloatingActionButton floatingActionButton;

    private Calendar newCal;
    private Calendar newCalTo;
    private Calendar newCalFrom;
    private Calendar newTimeEnd;
    private Calendar newTimeStart;

    private DatePickerDialog startDatePickerDialog;
    private DatePickerDialog endDatePickerDialog;
    private TimePickerDialog startTimePickerDialog;
    private TimePickerDialog endTimePickerDialog;

    private SimpleDateFormat dateFormatter;
    private SimpleDateFormat timeFormatter;
    private TextInputLayout create_event_input_layout; //TODO: 2/9/2017 Add Animation to InputLayout
    private Switch eventTypeSwitch;
    private TextView add_an_image_text;

    private EditText edit_text_create_event_name;
    private EditText editTextStartDate;
    private EditText editTextEndDate;
    private EditText editTextStartTime;
    private EditText editTextEndTime;

    private ImageButton create_event_image;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;
    private Event event;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);

        progressDialog = new ProgressDialog(this);
        firebaseAuth = FirebaseAuth.getInstance();

        newCal = Calendar.getInstance();
        event = new Event();
        Calendar eventCalendar = Calendar.getInstance();
        dateFormatter = new SimpleDateFormat("EEE, MMM d, yyyy", Locale.US);
        timeFormatter = new SimpleDateFormat("h:mm a", Locale.US);

        eventTypeSwitch = (Switch) findViewById(R.id.event_switch);
        floatingActionButton = (FloatingActionButton) findViewById(R.id.create_event_fab);
        create_event_input_layout = (TextInputLayout) findViewById(R.id.create_event_input_layout);
        edit_text_create_event_name = (EditText) findViewById(R.id.edit_text_create_event_name);
        editTextStartDate = (EditText) findViewById(R.id.edit_text_start_date);
        editTextEndDate = (EditText) findViewById(R.id.edit_text_end_date);
        editTextStartTime = (EditText) findViewById(R.id.edit_text_start_time);
        editTextEndTime = (EditText) findViewById(R.id.edit_text_end_time);
        add_an_image_text = (TextView) findViewById(R.id.add_an_image_text);
        create_event_image = (ImageButton) findViewById(R.id.create_event_image);

        editTextStartDate.setInputType(InputType.TYPE_CLASS_DATETIME);
        editTextEndDate.setInputType(InputType.TYPE_CLASS_DATETIME);
        editTextStartTime.setInputType(InputType.TYPE_CLASS_DATETIME);
        editTextEndTime.setInputType(InputType.TYPE_CLASS_DATETIME);

        setOnClickListeners();

        setOnFocusListenersOnDateInputs();
        setOnFocusListenersOnTimeInputs();

        setStartDateInputDialog();
        setStartTimeInputDialog();

        setEndDateInputDialog();
        setEndTimeInputDialog();

    }

    private void setOnClickListeners() {
        create_event_image.setOnClickListener(this);
        floatingActionButton.setOnClickListener(this);
    }

    /**
     * OnFocusListeners check to see when the user has put a view in focus (clicking on date input
     * or time input container).
     * When this happens, the date picker input dialog is shown, and it will only show when the
     * view is in focus.
     */

    private void setOnFocusListenersOnDateInputs() {
        editTextStartDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (hasWindowFocus())
                    startDatePickerDialog.show();
                view.clearFocus();
            }
        });

        editTextEndDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (hasWindowFocus())
                    endDatePickerDialog.show();
                view.clearFocus();
            }
        });
    }

    private void setOnFocusListenersOnTimeInputs() {
        editTextStartTime.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (hasWindowFocus())
                    startTimePickerDialog.show();
                view.clearFocus();
            }
        });

        editTextEndTime.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (hasWindowFocus())
                    endTimePickerDialog.show();
                view.clearFocus();
            }
        });
    }

    private void setStartDateInputDialog() {
        startDatePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        newCalFrom = Calendar.getInstance();
                        newCalFrom.set(year, month, day);
                        editTextStartDate.setText(dateFormatter.format(newCalFrom.getTime()));
                    }

                }, newCal.get(Calendar.YEAR), newCal.get(Calendar.MONTH), newCal.get(Calendar.DAY_OF_MONTH));
    }

    private void setStartTimeInputDialog() {
        startTimePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hour, int minute) {
                newTimeStart = Calendar.getInstance();
                newTimeStart.set(Calendar.HOUR_OF_DAY, hour);
                newTimeStart.set(Calendar.MINUTE, minute);
                editTextStartTime.setText(timeFormatter.format(newTimeStart.getTime()));
                event.setEventStartTime(editTextStartTime.getText().toString());
            }

        }, newCal.get(Calendar.HOUR_OF_DAY), newCal.get(Calendar.MINUTE), true);
    }

    private void setEndDateInputDialog() {
        endDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                newCalTo = Calendar.getInstance();
                newCalTo.set(year, month, day);
                editTextEndDate.setText(dateFormatter.format(newCalTo.getTime()));
                event.setEventEndDate(editTextEndDate.getText().toString());
            }

        }, newCal.get(Calendar.YEAR), newCal.get(Calendar.MONTH), newCal.get(Calendar.DAY_OF_MONTH));
    }

    private void setEndTimeInputDialog() {
        endTimePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hour, int minute) {
                newTimeEnd = Calendar.getInstance();
                newTimeEnd.set(Calendar.HOUR_OF_DAY, hour);
                newTimeEnd.set(Calendar.MINUTE, minute);
                editTextEndTime.setText(timeFormatter.format(newTimeEnd.getTime()));
                event.setEventEndTime(editTextEndTime.getText().toString());
            }

        }, newCal.get(Calendar.HOUR_OF_DAY), newCal.get(Calendar.MINUTE), true);
    }

    @Override
    public void onClick(View view) {
        if (view == create_event_image) {
            Intent intent = new Intent().setType("image/*").setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_PICTURE);
            add_an_image_text.setVisibility(View.INVISIBLE);
        }

        if (view == floatingActionButton) {
            addEventToDatabase(initializeEvent());

            /**
             * Wait slightly ~ 2 secs before returning user to previous screen
             */

            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    progressDialog.dismiss();
                    finish();
                }
            }, 2048);
        }
    }

    private Event initializeEvent() {
        progressDialog.setMessage("Creating " + edit_text_create_event_name.getText()
                .toString() + "...");
        progressDialog.show();
        event.setEventCreatorID(firebaseAuth.getCurrentUser().getUid());
        event.setEventName(edit_text_create_event_name.getText().toString());
        if (eventTypeSwitch.isChecked()) {
            event.setEventType("Public");
        } else {
            event.setEventType("Private");
        }

        event.setEventDescription("Event Description");

        return event;
    }

    private void addEventToDatabase(Event event) {
        String nodeForAllEvents = "dbroot/events";
        FirebaseDatabase eventDatabase = FirebaseDatabase.getInstance();
        DatabaseReference eventReference = eventDatabase.getReference(nodeForAllEvents);
        progressDialog.setMessage("Creating " + edit_text_create_event_name.getText()
                .toString() + "...");
        progressDialog.show();
        String eventKey = eventReference.push().getKey();
        event.setEventID(eventKey);
        eventReference.setValue(eventKey);
        eventReference.child(nodeForAllEvents).child(eventKey).setValue(event);
        addEventToCreatorEventList(eventKey);
    }

    //private void addEventToPhoneCalendar(Event event) { }

    private void addEventToCreatorEventList(String eventID) {
        //User user = new User();

        String creatorID = firebaseAuth.getCurrentUser().getUid();
        String nodeForCreator = "dbroot/users";
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference(nodeForCreator);
        databaseReference.child(nodeForCreator).child(creatorID).child("userEvents").setValue(eventID);

        //user = DataSnapshot.getValue(User.class);
        //databaseReference.orderByChild(String s);
        //TODO use DataSnapshot to acquire actual event object
        //user.attachEventToUser(eventID);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                Uri selectedImageUri = data.getData();
                String selectedImagePath = getPath(selectedImageUri);
                create_event_image.setImageIcon(Icon.createWithContentUri(selectedImageUri));
                create_event_image.setImageURI(selectedImageUri);
            }
        }
    }

    public String getPath(Uri uri) {
        String res = null;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(uri, proj, null, null, null);
        if (cursor.moveToFirst()) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            res = cursor.getString(column_index);
        }
        cursor.close();
        return res;
    }
}
