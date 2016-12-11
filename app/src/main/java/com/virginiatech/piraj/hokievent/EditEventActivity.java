package com.virginiatech.piraj.hokievent;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class EditEventActivity extends AppCompatActivity implements TaskCompleted {

    private EditText eventNameField;

    private EditText eventDescriptionField;

    private EditText eventLocationField;

    private TextView tagsList;
    private TextView messageView;

    private Button enterTags;
    private Button done;
    private Button cancel;

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

    private String tags;

    private HokiEvent event;

    private static String NAME = "name";
    private static String DESC= "desc";
    private static String LOC = "loc";
    private static String START_DATE = "start date";
    private static String START_TIME = "start time";
    private static String END_DATE = "end date";
    private static String END_TIME = "end time";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_event);

        dateFormatter = new SimpleDateFormat("MMM d, yyyy", Locale.US); //"EEE, MMM d, ''yy"
        //dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        timeFormatter = new SimpleDateFormat("h:mm a", Locale.US);

        calendar = Calendar.getInstance();

        //Initialize views
        findViewsById();

        buildDatepicking();
        buildTimepicking();

        tags = "";

        //Get the event information passed from the previous activity
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            event = extras.getParcelable(HokiEvent.EVENT);
        }

        //Show the event information
        if(event != null) {
            showInfo();
        }
        
    }

    /**
     * Display the information regarding the event to be edited
     */
    private void showInfo(){

        //Event name
        eventNameField.setText(event.getEventName());

        // --- Time and date (start and end) ---

        startDateField.setText(event.getEventStartDate());

        if(event.getEventEndDate() != null){
            endDateField.setText(event.getEventEndDate());
        }

        startTimeField.setText(event.getEventStartTime());

        if(event.getEventEndTime() != null){
            endTimeField.setText(event.getEventEndTime());
        }

        //Address
        if(event.getEventLoc() != null){
            eventLocationField.setText(event.getEventLoc());
        }

        //Description
        eventDescriptionField.setText(event.getEventDesc());

        //Tags
        tags = event.getInterests();
        tagsList.setText(tags);

    }

    /**
     *
     */
    private void findViewsById(){

        eventNameField = (EditText) findViewById(R.id.editEventName);
        eventDescriptionField = (EditText) findViewById(R.id.editEventDescription);

        eventLocationField = (EditText) findViewById(R.id.editEventLocation);

        startDateField = (EditText) findViewById(R.id.startDate);
        endDateField = (EditText) findViewById(R.id.endDate);

        startTimeField = (EditText) findViewById(R.id.startTime);
        endTimeField = (EditText) findViewById(R.id.endTime);

        tagsList = (TextView) findViewById(R.id.tagsList);
        messageView = (TextView) findViewById(R.id.editEventMessage);
        enterTags = (Button) findViewById(R.id.editEventTagsButton);
        done = (Button) findViewById(R.id.editEventDoneButton);
        cancel = (Button) findViewById(R.id.editEventCancelButton);

        enterTags.setOnClickListener(selectTagsListener);
        cancel.setOnClickListener(cancelListener);
        done.setOnClickListener(doneListener);

    }

    /**
     *
     */
    private void buildDatepicking(){

        startDateField.setFocusable(false);
        startDateField.setClickable(true);

        endDateField.setFocusable(false);
        endDateField.setClickable(true);

        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        // --- Set the start date field ---
        try {
            calendar.setTime(dateFormatter.parse(startDateField.getText().toString()));
            year = calendar.get(Calendar.YEAR);
            month = calendar.get(Calendar.MONTH);
            day = calendar.get(Calendar.DAY_OF_MONTH);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        startDatePicker = new DatePickerDialog(this, startDateListener, year, month, day);

        // --- Set the end date field ---
        if(endDateField.getText() != null){
            try {
                calendar.setTime(dateFormatter.parse(endDateField.getText().toString()));
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                day = calendar.get(Calendar.DAY_OF_MONTH);

            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

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

        // --- Set the start time field ---
        try {
            calendar.setTime(timeFormatter.parse(startTimeField.getText().toString()));
            hour = calendar.get(Calendar.HOUR_OF_DAY);
            minute = calendar.get(Calendar.MINUTE);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        startTimePicker = new TimePickerDialog(this, startTimeListener, hour, minute, DateFormat.is24HourFormat(this));

        // --- Set the start time field ---
        if(endTimeField.getText() != null){
            try {
                calendar.setTime(timeFormatter.parse(endTimeField.getText().toString()));
                hour = calendar.get(Calendar.HOUR_OF_DAY);
                minute = calendar.get(Calendar.MINUTE);

            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

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
            if(changesMade()){
                confirmExit();
            } else {
                finish();
            }
        }
    };

    /**
     * Listener for next -button
     */

    //TODO figure out how we're doing location.

    private View.OnClickListener doneListener = new View.OnClickListener(){
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



            } catch (IOException e)
            {
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

            sendData(newEvent);

            Intent output = new Intent();
            output.putExtra(HokiEvent.EVENT, newEvent);
            setResult(RESULT_OK, output);

            System.out.println("InterestsActivity returns: " + output.getParcelableExtra(HokiEvent.EVENT));

            finish();

        }
    };

    /**
     * Send data to the server.
     *
     * @param newEvent
     */
    public void sendData(HokiEvent newEvent)
    {
        //Send server new user entry
        JSONObject json = new JSONHelper().createEventJSON(newEvent);

        System.out.println(json);
        if(json != null) {
            APICaller api = new APICaller(this);
            try {
                api.APIputEvent(json);
            } catch (Exception e) {
                System.out.println(e);
            }
        }
        else {
            //TODO Handle the case where the JSON couldn't be created ???
        }
    }

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

    /**
     * When the back button is pressed, if the user has made changes in input fields, show confirmation dialog.
     * If user hasn't made any changes, return to previous activity normally.
     */
    @Override
    public void onBackPressed() {
        if(changesMade()){
            confirmExit();
        }
        else {
            finish();
        }
    }

    /**
     * Show confirmation dialog when user is about to leave the
     */
    public void confirmExit(){

        new AlertDialog.Builder(this)
                .setTitle("Discard changes")
                .setMessage("Are sure you want to discard your changes?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }

                })
                .setNegativeButton("No", null)
                .show();
    }

    /**
     * Checks whether the user made any changes to event information.
     *
     * @return True if the user didn't make any changes. Else false.
     */
    public boolean changesMade(){

        if(event.getEventName().equals(eventNameField.getText().toString())
                && event.getEventStartDate().equals(startDateField.getText().toString())
                && event.getEventStartTime().equals(startTimeField.getText().toString())
                && event.getEventEndDate().equals(endDateField.getText().toString())
                && event.getEventEndTime().equals(endTimeField.getText().toString())
                && event.getEventDesc().equals(eventDescriptionField.getText().toString())
                && event.getEventLoc().equals(eventLocationField.getText().toString())
                && event.getInterests().equals(tagsList.getText().toString())
                ){
            return false;
        }
        else {
            return true;
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {

        outState.putString(NAME, eventNameField.getText().toString());
        outState.putString(DESC, eventDescriptionField.getText().toString());
        outState.putString(LOC, eventLocationField.getText().toString());
        outState.putString(START_DATE, startDateField.getText().toString());
        outState.putString(START_TIME, startTimeField.getText().toString());
        outState.putString(END_DATE, endDateField.getText().toString());
        outState.putString(END_TIME, endTimeField.getText().toString());
        outState.putParcelable(HokiEvent.EVENT, event);

        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {

        eventDescriptionField.setText(savedInstanceState.getString(NAME));
        eventDescriptionField.setText(savedInstanceState.getString(DESC));
        eventLocationField.setText(savedInstanceState.getString(LOC));
        startDateField.setText(savedInstanceState.getString(START_DATE));
        startTimeField.setText(savedInstanceState.getString(START_TIME));
        endDateField.setText(savedInstanceState.getString(END_DATE));
        endTimeField.setText(savedInstanceState.getString(END_TIME));
        event = savedInstanceState.getParcelable(HokiEvent.EVENT);

        showInfo();
        super.onRestoreInstanceState(savedInstanceState);

    }
    @Override
    public void onTaskComplete(String result) {

    }
}
