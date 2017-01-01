package com.chornge.nprime;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.Icon;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TimePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class CreateEventActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int SELECT_PICTURE = 1;
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
    private ImageButton create_event_image;

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
        create_event_image = (ImageButton) findViewById(R.id.create_event_image);

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

        setOnClickListeners();

    }

    private void setOnClickListeners() {
        create_event_image.setOnClickListener(this);
        fab.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == fab) {
//            Calendar calendar = Calendar.getInstance();
            Snackbar.make(view, "Event Created", Snackbar.LENGTH_SHORT).show();
        }

        if (view == create_event_image) {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent,
                    "Select Picture"), SELECT_PICTURE);
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
        Cursor cursor = getContentResolver().query(uri, proj, null, null, null);
        if (cursor.moveToFirst()) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            res = cursor.getString(column_index);
        }
        cursor.close();
        return res;
    }
}
