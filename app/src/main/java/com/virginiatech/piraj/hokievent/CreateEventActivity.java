package com.virginiatech.piraj.hokievent;

import java.util.Calendar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
    private EditText startDateField;
    private EditText endDateField;
    private EditText startTimeField;
    private EditText endTimeField;
    private int year;
    private int month;
    private int day;

    //TODO Time?

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);

        eventNameField = (EditText) findViewById(R.id.eventName);

        eventDescriptionField = (EditText) findViewById(R.id.eventDescription);

        // --- Time & date ---
        startDateField = (EditText) findViewById(R.id.startDate);


        endDateField = (EditText) findViewById(R.id.endDate);

        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        showDate(year, month+1, day);

        startTimeField = (EditText) findViewById(R.id.startTime);
        startTimeField.setFocusable(false);
        startTimeField.setClickable(true);

        endTimeField = (EditText) findViewById(R.id.endTime);
        endTimeField.setFocusable(false);
        endTimeField.setClickable(true);

    }



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
