package com.chornge.nprime;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class CreateEventActivity extends AppCompatActivity implements View.OnClickListener {

    private FloatingActionButton fab;
    private DatePickerDialog datePickerDialogFrom;
    private DatePickerDialog datePickerDialogTo;

    private TimePickerDialog timePickerDialogStart;
    private TimePickerDialog timePickerDialogEnd;
    private SimpleDateFormat dateFormatter;
    private SimpleDateFormat timeFormatter;

    private EditText editTextFromDate;
    private EditText editTextToDate;

    private EditText editTextStartTime;
    private EditText editTextEndTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);

        Calendar newCal = Calendar.getInstance();
        Calendar eventCalendar = Calendar.getInstance();
        dateFormatter = new SimpleDateFormat("EEE, MMM d, yyyy", Locale.US);
        timeFormatter = new SimpleDateFormat("h:mm a", Locale.US);

        fab = (FloatingActionButton) findViewById(R.id.create_event_fab);
        editTextFromDate = (EditText) findViewById(R.id.edit_text_from_date);
        editTextToDate = (EditText) findViewById(R.id.edit_text_to_date);
        editTextStartTime = (EditText) findViewById(R.id.edit_text_start_time);
        editTextEndTime = (EditText) findViewById(R.id.edit_text_end_time);

        editTextFromDate.setInputType(InputType.TYPE_NULL);
        //editTextFromDate.setText(dateFormatter.format(newCal.getTime()));
        editTextToDate.setInputType(InputType.TYPE_NULL);
        //editTextToDate.setText(dateFormatter.format(newCal.getTime()));

        editTextStartTime.setInputType(InputType.TYPE_NULL);
        //editTextStartTime.setText(timeFormatter.format(newCal.getTime()));
        editTextEndTime.setInputType(InputType.TYPE_NULL);
        //editTextEndTime.setText(timeFormatter.format(newCal.getTime()));

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

        fab.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        if (view == fab) {
//            Calendar calendar = Calendar.getInstance();
            Snackbar.make(view, "Event Created", Snackbar.LENGTH_SHORT).show();
        }
    }
}
