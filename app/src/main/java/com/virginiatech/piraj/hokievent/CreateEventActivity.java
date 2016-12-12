package com.virginiatech.piraj.hokievent;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.icu.text.TimeZoneFormat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import org.json.JSONException;
import org.json.JSONObject;

public class CreateEventActivity extends AppCompatActivity {

    private EditText eventNameField;

    private EditText eventDescriptionField;

    private EditText eventLocationField;

    private TextView tagsList;
    private TextView messageView;

    private Button enterTags;
    private Button next;
    private Button cancel;

    // --- Time and date ---
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

    private String tags;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);

        dateFormatter = new SimpleDateFormat("MMM d, yyyy", Locale.US); //"EEE, MMM d, ''yy"
        //dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        timeFormatter = new SimpleDateFormat("h:mm a", Locale.US);

        calendar = Calendar.getInstance();

        //Initialize views
        findViewsById();

        buildDatepicking();
        buildTimepicking();

        tags = "";
        
    }

    /**
     *
     */
    private void findViewsById(){

        eventNameField = (EditText) findViewById(R.id.createEventName);
        eventDescriptionField = (EditText) findViewById(R.id.createEventDescription);

        eventLocationField = (EditText) findViewById(R.id.createEventLocation);

        startDateField = (EditText) findViewById(R.id.startDate);
        endDateField = (EditText) findViewById(R.id.endDate);

        startTimeField = (EditText) findViewById(R.id.startTime);
        endTimeField = (EditText) findViewById(R.id.endTime);

        tagsList = (TextView) findViewById(R.id.tagsList);
        messageView = (TextView) findViewById(R.id.createAccountMessage);
        enterTags = (Button) findViewById(R.id.createEventTagsButton);
        next = (Button) findViewById(R.id.createEventNextButton);
        cancel = (Button) findViewById(R.id.createEventCancelButton);

        enterTags.setOnClickListener(selectTagsListener);
        cancel.setOnClickListener(cancelListener);
        next.setOnClickListener(nextListener);

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

            Calendar newTime = Calendar.getInstance();
            newTime.set(Calendar.HOUR_OF_DAY, hour);
            newTime.set(Calendar.MINUTE, minute);

            endTimeField.setText(timeFormatter.format(newTime.getTime()));
        }
    };

    /**
     * Listener for Select tags -button
     */
    private View.OnClickListener selectTagsListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            Intent tagsActivityListener = new Intent(view.getContext(), TagsActivity.class);
            tagsActivityListener.putExtra(TagsActivity.TAGS, tags);

            System.out.println("CreateAccount sends :" + tagsActivityListener.getStringExtra(TagsActivity.TAGS));
            startActivityForResult(tagsActivityListener, 0);

        }
    };


    /**
     * Listener for cancel button. Pressing the cancel button takes the user back to the home view
     */
    private View.OnClickListener cancelListener = new View.OnClickListener() {
        @Override
        public void onClick(View view){

            //If even one of the fields has text in it, show confirmation dialog before user exits
            if(!allFieldsEmpty()){
                confirmExit();
            }
            else {
                finish();
            }
        }
    };

    /**
     * Check if all fields are empty
     * @return True if all fields are empty and no tags have been entered. Else return false.
     */
    private boolean allFieldsEmpty(){

        if(eventNameField.getText().length() == 0
                && startTimeField.getText().length() == 0
                && startDateField.getText().length() == 0
                && endDateField.getText().length() == 0
                && endTimeField.getText().length() == 0
                && eventLocationField.getText().length() == 0
                && eventDescriptionField.getText().length() == 0
                && tags.length() == 0) {
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * Show confirmation dialog when user is about to leave the
     */
    private void confirmExit(){

        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Discard changes");
        alert.setMessage("Are sure you want to discard your changes?");

        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });

        alert.setNegativeButton("No", null);

        //Show the alert
        alert.show();
    }

    /**
     * If user has entered text to any of the fields (or has chosen tags), show confirmation
     * dialog before letting the user navigate away from the activity with the back button.
     */
    @Override
    public void onBackPressed() {
        if(!allFieldsEmpty()){
            confirmExit();
        }
        else {
            finish();
        }
    }

    /**
     * Listener for next -button
     */

    //TODO figure out how we're doing location.

    private View.OnClickListener nextListener = new View.OnClickListener(){
        @Override
        public void onClick(View view){

            if (eventNameField.getText().toString().equals(""))
            {
                messageView.setText("Please name your event");
                return;
            }
            if (eventDescriptionField.getText().toString().equals(""))
            {
                messageView.setText("Please provide a description");
                return;
            }
            if ( startDateField.getText().toString().equals("") )
            {
                messageView.setText("Please provide a start date");
                return;
            }
            if ( startTimeField.getText().toString().equals("") )
            {
                messageView.setText("Please provide a start time");
                return;
            }
            else if (tags.equals(""))
            {
                messageView.setText("Please tag your event");
                return;
            }

            String email = "no email";

            try {
                FileInputStream fin = openFileInput(User.USER_FILE);
                BufferedReader reader = new BufferedReader(new InputStreamReader(fin));

                email = reader.readLine();


                reader.close();

            } catch (IOException e) {
                e.printStackTrace();
            }

            HokiEvent newEvent = new HokiEvent(
                    eventNameField.getText().toString(),
                    eventDescriptionField.getText().toString(),
                    eventLocationField.getText().toString(),
                    startDateField.getText().toString(),
                    startTimeField.getText().toString(),
                    tags, email);

            if(endDateField.getText().length() > 0){
                newEvent.setEventEndDate(endDateField.getText().toString());
            }

            if(endTimeField.getText().length() > 0){
                newEvent.setEventEndTime(endTimeField.getText().toString());
            }

            System.out.println("CreateEventActivity: created new event: " + newEvent);

            Intent startConfirmEventActivity = new Intent(view.getContext(), ConfirmEventActivity.class);

            startConfirmEventActivity.putExtra(HokiEvent.EVENT, newEvent);

            startActivity(startConfirmEventActivity);

        }
    };

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 0 && resultCode == RESULT_OK && data != null) {
            tags = data.getStringExtra(TagsActivity.TAGS);
            System.out.println("CreateEventActivity received: " + tags);
            if (tags.equals(""))
            {
                tagsList.setText("No tags selected");
            }
            else
            {
                tagsList.setText(tags);
            }
        }
    }
}
