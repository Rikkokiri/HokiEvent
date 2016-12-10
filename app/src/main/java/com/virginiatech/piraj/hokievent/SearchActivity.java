package com.virginiatech.piraj.hokievent;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;


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

    private EditText startDateField;
    private EditText endDateField;
    private DatePickerDialog startDatePicker;
    private DatePickerDialog endDatePicker;

    private EditText startTimeField;
    private EditText endTimeField;
    private TimePickerDialog startTimePicker;
    private TimePickerDialog endTimePicker;

    private Button searchButton;
    private boolean activityLaunched = false;

    // --- Strings ---
    private String tags; //Used to store tags that are searched
    private static final String DATE = "date";
    private static final String TIME = "time";
    private static final String DISTANCE = "distance";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        //Build bottom navigation
        buildBottomBar(this, savedInstanceState);

        //Find components by Id
        findById();

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

        tagsInput.setFocusable(false);
        tagsInput.setClickable(true);
        tagsInput.setOnClickListener(selectTagsListener);

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

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState){

        tags = savedInstanceState.getString(InterestsActivity.INTEREST);
        dateInput.setText(savedInstanceState.getString(DATE));
        timeInput.setText(savedInstanceState.getString(TIME));
        distanceInput.setText(savedInstanceState.getString(DISTANCE));

        super.onRestoreInstanceState(savedInstanceState);

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {

        outState.putString(DATE, dateInput.getText().toString());
        outState.putString(TIME, timeInput.getText().toString());
        outState.putString(DISTANCE, distanceInput.getText().toString());
        outState.putString(InterestsActivity.INTEREST, tags);

        super.onSaveInstanceState(outState);
    }
}
