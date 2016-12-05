package com.virginiatech.piraj.hokievent;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.icu.text.TimeZoneFormat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

public class CreateEventActivity extends AppCompatActivity {

    private EditText eventNameField;

    private EditText eventDescriptionField;

    // --- Time and date ---
    private DatePicker datePicker;
    private TimePicker timePicker;
    private Calendar calendar;
    private SimpleDateFormat dateFormatter;
    private SimpleDateFormat timeFormatter;

    private EditText startDateField;
    private EditText endDateField;
    private DatePickerDialog startDatePicker;
    private DatePickerDialog endDatePicker;

    private EditText startTimeField;
    private EditText endTimeField;
    private TimePickerDialog startTimePicker;
    private TimePickerDialog endTimePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);
        
        dateFormatter = new SimpleDateFormat("MMM d, ''yy", Locale.US); //"EEE, MMM d, ''yy"
        //dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        timeFormatter = new SimpleDateFormat("h:mm a", Locale.US);

        calendar = Calendar.getInstance();

        //Initialize views
        findViewsById();

        buildDatepicking();
        buildTimepicking();
        
    }

    /**
     *
     */
    private void findViewsById(){

        eventNameField = (EditText) findViewById(R.id.eventName);
        eventDescriptionField = (EditText) findViewById(R.id.eventDescription);

        startDateField = (EditText) findViewById(R.id.startDate);
        endDateField = (EditText) findViewById(R.id.endDate);

        startTimeField = (EditText) findViewById(R.id.startTime);
        endTimeField = (EditText) findViewById(R.id.endTime);
    }

    /**
     *
     */
    private void buildDatepicking(){

        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        startDateField.setFocusable(false);
        startDateField.setClickable(true);

        endDateField.setFocusable(false);
        endDateField.setClickable(true);

        // --- DatePickers ---
        startDatePicker = new DatePickerDialog(this, startDateListener, year, month, day);
        endDatePicker = new DatePickerDialog(this, endDateListener, year, month, day);

        startDateField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startDatePicker.show();
            }
        });

        endDateField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                endDatePicker.show();
            }
        });

    }

    private void buildTimepicking(){

        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        startTimeField.setFocusable(false);
        startTimeField.setClickable(true);

        endTimeField.setFocusable(false);
        endTimeField.setClickable(true);

        startTimePicker = new TimePickerDialog(this, startTimeListener, hour, minute, DateFormat.is24HourFormat(this));
        endTimePicker = new TimePickerDialog(this, endTimeListener, hour, minute, DateFormat.is24HourFormat(this));

        startTimeField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startTimePicker.show();
            }
        });

        endTimeField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                endTimePicker.show();
            }
        });

    }

    /**
     * Listener for DatePickerDialog used to pick start date of the event
     */
    private DatePickerDialog.OnDateSetListener startDateListener = new DatePickerDialog.OnDateSetListener(){

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            Calendar newDate = Calendar.getInstance();
            newDate.set(year, monthOfYear, dayOfMonth);
            startDateField.setText(dateFormatter.format(newDate.getTime()));
        }
    };

    /**
     * Listener for DatePickerDialog used to pick end date of the event
     */
    private DatePickerDialog.OnDateSetListener endDateListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            Calendar newDate = Calendar.getInstance();
            newDate.set(year, monthOfYear, dayOfMonth);
            endDateField.setText(dateFormatter.format(newDate.getTime()));
        }
    };

    /**
     * Listener for TimePickerDialog used to pick the start time of the event
     */
    private TimePickerDialog.OnTimeSetListener startTimeListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker timePicker, int hour, int minute) {

            //TODO
            Calendar newTime = Calendar.getInstance();
            newTime.set(Calendar.HOUR_OF_DAY, hour);
            newTime.set(Calendar.MINUTE, minute);

            startTimeField.setText(timeFormatter.format(newTime.getTime()));
        }
    };

    /**
     * Listener for TimePickerDialog used to pick the end time of the event
     */
    private TimePickerDialog.OnTimeSetListener endTimeListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker timePicker, int hour, int minute) {

            //TODO
            Calendar newTime = Calendar.getInstance();
            newTime.set(Calendar.HOUR_OF_DAY, hour);
            newTime.set(Calendar.MINUTE, minute);

            endTimeField.setText(timeFormatter.format(newTime.getTime()));
        }
    };

}
