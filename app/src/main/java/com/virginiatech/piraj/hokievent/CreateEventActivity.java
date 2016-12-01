package com.virginiatech.piraj.hokievent;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import android.app.DatePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

public class CreateEventActivity extends AppCompatActivity {

    private EditText eventNameField;

    private EditText eventDescriptionField;

    // --- Time and date ---
    private DatePicker datePicker;
    private TimePicker timePicker;
    private Calendar calendar;

    private EditText startDateField;
    private EditText endDateField;
    private DatePickerDialog startDatePicker;
    private DatePickerDialog endDatePicker;

    private EditText startTimeField;
    private EditText endTimeField;
    private int year;
    private int month;
    private int day;

    private SimpleDateFormat dateFormatter;

    //TODO Time?

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);

        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);

        //Initialize views
        findViewsById();

        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        showDate(year, month+1, day);

        // --- Time & date ---
        startTimeField.setFocusable(false);
        startTimeField.setClickable(true);

        endTimeField.setFocusable(false);
        endTimeField.setClickable(true);

        startDateField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startDatePicker.show();
            }
        });

        endTimeField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                endDatePicker.show();
            }
        });

        // --- DatePickers ---
        startDatePicker = new DatePickerDialog(this, StartDateListener, year, month, day);
        endDatePicker = new DatePickerDialog(this, EndDateListener, year, month, day);



    }

    private void findViewsById(){

        eventNameField = (EditText) findViewById(R.id.eventName);
        eventDescriptionField = (EditText) findViewById(R.id.eventDescription);

        startDateField = (EditText) findViewById(R.id.startDate);
        endDateField = (EditText) findViewById(R.id.endDate);

        startTimeField = (EditText) findViewById(R.id.startTime);
        endTimeField = (EditText) findViewById(R.id.endTime);
    }

    /**
     * Listener for DatePickerDialog used to pick start date
     */
    private DatePickerDialog.OnDateSetListener StartDateListener = new DatePickerDialog.OnDateSetListener(){

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            Calendar newDate = Calendar.getInstance();
            newDate.set(year, monthOfYear, dayOfMonth);
            startDateField.setText(dateFormatter.format(newDate.getTime()));
        }
    };

    /**
     *
     */
    private DatePickerDialog.OnDateSetListener EndDateListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            Calendar newDate = Calendar.getInstance();
            newDate.set(year, monthOfYear, dayOfMonth);
            endTimeField.setText(dateFormatter.format(newDate.getTime()));
        }
    };



    /**
     * TODO ...
     *
     * @param year
     * @param month
     * @param day
     */
    private void showDate(int year, int month, int day) {
        /* TODO
        eventDateField.setText(new StringBuilder().append(day).append("/")
                .append(month).append("/").append(year));
        */
    }
}
