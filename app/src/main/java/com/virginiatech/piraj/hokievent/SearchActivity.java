package com.virginiatech.piraj.hokievent;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;


public class SearchActivity extends AppCompatActivity {

    //Navigation bar
    private BottomBar bottomBar;

    //Search inputs
    private EditText tagsInput;
    private EditText distanceInput;

    // --- Time and date ---
    private Calendar calendar;
    private SimpleDateFormat dateFormatter;
    private SimpleDateFormat timeFormatter;

    private EditText startDateInput;
    private EditText endDateInput;
    private DatePickerDialog startDatePicker;
    private DatePickerDialog endDatePicker;

    private EditText startTimeInput;
    private EditText endTimeInput;
    private TimePickerDialog startTimePicker;
    private TimePickerDialog endTimePicker;

    private Button searchButton;
    private boolean activityLaunched = false;

    // --- Strings ---
    private String tags; //Used to store tags that are searched
    private static final String START_DATE = "start date";
    private static final String END_DATE = "end date";
    private static final String START_TIME = "start time";
    private static final String END_TIME = "end time";
    private static final String DISTANCE = "distance";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        //Build bottom navigation
        buildBottomBar(this, savedInstanceState);

        dateFormatter = new SimpleDateFormat("MMM d, yyyy", Locale.US); //"EEE, MMM d, ''yy"
        //dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        timeFormatter = new SimpleDateFormat("h:mm a", Locale.US);

        //Find components by Id
        findById();

        buildDatepicking();
        buildTimepicking();

        //Search button
        searchButton.setOnClickListener(searchHandler);

    }


    /**
     * Find view components by id
     */
    private void findById(){

        searchButton = (Button) findViewById(R.id.searchButton);
        tagsInput = (EditText) findViewById(R.id.search_tags_input);
        distanceInput = (EditText) findViewById(R.id.search_input_distance);

        startDateInput = (EditText) findViewById(R.id.startDate);
        endDateInput = (EditText) findViewById(R.id.endDate);

        startTimeInput = (EditText) findViewById(R.id.startTime);
        endTimeInput = (EditText) findViewById(R.id.endTime);

        tagsInput.setFocusable(false);
        tagsInput.setClickable(true);
        tagsInput.setOnClickListener(selectTagsListener);
    }


    /**
     * Build DatePickers for start and end date
     */
    private void buildDatepicking(){

        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        startDateInput.setFocusable(false);
        startDateInput.setClickable(true);

        endDateInput.setFocusable(false);
        endDateInput.setClickable(true);

        // --- DatePickers ---
        startDatePicker = new DatePickerDialog(this, startDateListener, year, month, day);
        endDatePicker = new DatePickerDialog(this, endDateListener, year, month, day);

        startDateInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startDatePicker.show();
            }
        });

        endDateInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                endDatePicker.show();
            }
        });

    }

    /**
     * Build TimePickers for start and end time
     */
    private void buildTimepicking(){

        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        startTimeInput.setFocusable(false);
        startTimeInput.setClickable(true);

        endTimeInput.setFocusable(false);
        endTimeInput.setClickable(true);

        startTimePicker = new TimePickerDialog(this, startTimeListener, hour, minute, DateFormat.is24HourFormat(this));
        endTimePicker = new TimePickerDialog(this, endTimeListener, hour, minute, DateFormat.is24HourFormat(this));

        startTimeInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startTimePicker.show();
            }
        });

        endTimeInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                endTimePicker.show();
            }
        });

    }

    /**
     * Listener for tagsInput, open view for selecting tags
     */
    private View.OnClickListener selectTagsListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            Intent selectTagsIntent = new Intent(view.getContext(), InterestsActivity.class);
            selectTagsIntent.putExtra(InterestsActivity.INTEREST, tags);

            startActivityForResult(selectTagsIntent, 0);

        }
    };

    /**
     * Get the results from selecting tags
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    protected void onActivityResult(int requestCode, int resultCode, Intent data){

        if (requestCode == 0 && resultCode == RESULT_OK && data != null) {
            tags = data.getStringExtra(InterestsActivity.INTEREST);

            if (tags.equals("")) {
                tagsInput.setText("");
            }
            else {
                tagsInput.setText(tags);
            }
        }
    }

    /**
     * Listener for search button
     */
    private View.OnClickListener searchHandler = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            //TODO Perform the search by doing calls to the server





            //System.out.println("events outsdie " + events.toString());


            Intent startResultsActivity = new Intent(v.getContext(), SearchResultsActivity.class);
            //startResultsActivity.putParcelableArrayListExtra(HokiEvent.EVENT, events);
            startActivity(startResultsActivity);

        }
    };



    /**
     * Listener for DatePickerDialog used to pick start date of the event
     */
    private DatePickerDialog.OnDateSetListener startDateListener = new DatePickerDialog.OnDateSetListener(){

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            Calendar newDate = Calendar.getInstance();
            newDate.set(year, monthOfYear, dayOfMonth);
            startDateInput.setText(dateFormatter.format(newDate.getTime()));
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
            endDateInput.setText(dateFormatter.format(newDate.getTime()));
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

            startTimeInput.setText(timeFormatter.format(newTime.getTime()));
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

            endTimeInput.setText(timeFormatter.format(newTime.getTime()));
        }
    };

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState){

        startDateInput.setText(savedInstanceState.getString(START_DATE));
        endDateInput.setText(savedInstanceState.getString(END_DATE));

        startTimeInput.setText(savedInstanceState.getString(START_TIME));
        endTimeInput.setText(savedInstanceState.getString(END_TIME));

        distanceInput.setText(savedInstanceState.getString(DISTANCE));
        tags = savedInstanceState.getString(InterestsActivity.INTEREST);

        super.onRestoreInstanceState(savedInstanceState);

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {

        outState.putString(START_DATE, startDateInput.getText().toString());
        outState.putString(END_DATE, endDateInput.getText().toString());

        outState.putString(START_TIME, startTimeInput.getText().toString());
        outState.putString(END_TIME, endTimeInput.getText().toString());

        outState.putString(DISTANCE, distanceInput.getText().toString());
        outState.putString(InterestsActivity.INTEREST, tags);

        super.onSaveInstanceState(outState);
    }

    // - o - o - o - o - o - o - o - o - o - o - o - o - o - o - o - o - o - o - o - o
    // - o - o - o - o - o - o - o - o NAVIGATION BAR  - o - o - o - o - o - o - o - o
    // - o - o - o - o - o - o - o - o - o - o - o - o - o - o - o - o - o - o - o - o

    /**
     * Build navigation bar located on the bottom of the screen.
     *
     * @param activity
     * @param savedInstanceState
     */
    private void buildBottomBar(Activity activity, Bundle savedInstanceState){

        bottomBar = (BottomBar) findViewById(R.id.bottomBar);
/*
        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
                navigate(tabId);
            }
        };*/
    }

    /**
     *
     * @param menuID
     */
    private void navigate(int menuID){

        switch (menuID){
            case R.id.action_home:
                //This boolean check is here to stop the app from throwing the user back to home view from profile view
                if(activityLaunched) {
                    Intent goHomeIntent = new Intent(getApplicationContext(), HomeActivity.class);
                    startActivity(goHomeIntent);
                } else {
                    activityLaunched = true;
                }
                break;

            case R.id.action_create_event:
                Intent createEventIntent = new Intent(getApplicationContext(), CreateEventActivity.class);
                startActivity(createEventIntent);
                break;

            case R.id.action_search:
                //Do nothing
                break;

            case R.id.action_profile:
                Intent profileIntent = new Intent(getApplicationContext(), ProfileActivity.class);
                startActivity(profileIntent);
                break;
        }
    }

}
