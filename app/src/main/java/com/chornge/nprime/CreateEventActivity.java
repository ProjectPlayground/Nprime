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
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);

        progressDialog = new ProgressDialog(this);

        Calendar newCal = Calendar.getInstance();
        Calendar eventCalendar = Calendar.getInstance();
        dateFormatter = new SimpleDateFormat("EEE, MMM d, yyyy", Locale.US);
        timeFormatter = new SimpleDateFormat("h:mm a", Locale.US);

        eventTypeSwitch = (Switch) findViewById(R.id.event_switch);
        floatingActionButton = (FloatingActionButton) findViewById(R.id.create_event_fab);
        create_event_input_layout = (TextInputLayout) findViewById(R.id.create_event_input_layout);
        edit_text_create_event_name = (EditText) findViewById(R.id.edit_text_create_event_name);
        editTextFromDate = (EditText) findViewById(R.id.edit_text_from_date);
        editTextToDate = (EditText) findViewById(R.id.edit_text_to_date);
        editTextStartTime = (EditText) findViewById(R.id.edit_text_start_time);
        editTextEndTime = (EditText) findViewById(R.id.edit_text_end_time);
        add_an_image_text = (TextView) findViewById(R.id.add_an_image_text);
        create_event_image = (ImageButton) findViewById(R.id.create_event_image);

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
                if (hasWindowFocus())
                    datePickerDialogFrom.show();
                view.clearFocus();
            }
        });

        editTextToDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (hasWindowFocus())
                    datePickerDialogTo.show();
                view.clearFocus();
            }
        });

        editTextStartTime.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (hasWindowFocus())
                    timePickerDialogStart.show();
                view.clearFocus();
            }
        });

        editTextEndTime.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (hasWindowFocus())
                    timePickerDialogEnd.show();
                view.clearFocus();
            }
        });

        datePickerDialogFrom = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                Calendar newCalFrom = Calendar.getInstance();
                newCalFrom.set(year, month, day);
                editTextFromDate.setText(dateFormatter.format(newCalFrom.getTime()));
            }

        }, newCal.get(Calendar.YEAR), newCal.get(Calendar.MONTH), newCal.get(Calendar.DAY_OF_MONTH));

        datePickerDialogTo = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                Calendar newCalTo = Calendar.getInstance();
                newCalTo.set(year, month, day);
                editTextToDate.setText(dateFormatter.format(newCalTo.getTime()));
            }

        }, newCal.get(Calendar.YEAR), newCal.get(Calendar.MONTH), newCal.get(Calendar.DAY_OF_MONTH));

        timePickerDialogStart = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hour, int minute) {
                Calendar newTimeStart = Calendar.getInstance();
                newTimeStart.set(Calendar.HOUR_OF_DAY, hour);
                newTimeStart.set(Calendar.MINUTE, minute);
                editTextStartTime.setText(timeFormatter.format(newTimeStart.getTime()));
            }

        }, newCal.get(Calendar.HOUR_OF_DAY), newCal.get(Calendar.MINUTE), true);

        timePickerDialogEnd = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hour, int minute) {
                Calendar newTimeStart = Calendar.getInstance();
                newTimeStart.set(Calendar.HOUR_OF_DAY, hour);
                newTimeStart.set(Calendar.MINUTE, minute);
                editTextEndTime.setText(timeFormatter.format(newTimeStart.getTime()));
            }

        }, newCal.get(Calendar.HOUR_OF_DAY), newCal.get(Calendar.MINUTE), true);

        setOnClickListeners();

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
            //initializeEvent();
            progressDialog.setMessage("Creating " + edit_text_create_event_name.getText().toString() + "...");
            progressDialog.show();
            Event event = new Event(FirebaseAuth.getInstance().getCurrentUser().getUid());
            event.setEventName(edit_text_create_event_name.getText().toString());
            if (eventTypeSwitch.isChecked()) {
                event.setEventType("Public");
            } else {
                event.setEventType("Private");
            }

            event.setEventDescription("Event Description");

            String nodeForAllEvents = "events";
            FirebaseDatabase eventDb = FirebaseDatabase.getInstance();
            DatabaseReference eventReference = eventDb.getReference(nodeForAllEvents);
            progressDialog.setMessage("Creating " + edit_text_create_event_name.getText().toString() + "...");
            progressDialog.show();
            String eventKey = eventReference.push().getKey();
            event.setEventID(eventKey);
            eventReference.setValue(event.getEventID());

//            String nodeForCurrentUser = "user";
//            User user = new User();
//            FirebaseDatabase userDb = FirebaseDatabase.getInstance();
//            DatabaseReference userReference = userDb.getReference(
//                    nodeForCurrentUser + '/' + FirebaseAuth.getInstance().getCurrentUser().getUid());
//            user.attachEventToUser(event.getEventID());

            //userReference.
            //reference.child(nodeForAllEvents).setValue(event);

            /**
             * Wait slightly ~ 3 secs before returning user to previous activity
             */

            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    progressDialog.dismiss();
                    finish();
                }
            }, 3072);
        }
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
