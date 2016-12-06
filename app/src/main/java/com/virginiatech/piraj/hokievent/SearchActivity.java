package com.virginiatech.piraj.hokievent;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

import org.json.JSONArray;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {

    //Navigation bar
    private BottomBar bottomBar;

    //Search inputs
    private EditText tagsInput;
    private EditText dateInput;
    private EditText timeInput;
    private EditText distanceInput;

    private Button searchButton;
    private boolean activityLaunched = false;

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
        dateInput = (EditText) findViewById(R.id.search_input_date);
        timeInput = (EditText) findViewById(R.id.search_input_time);
        distanceInput = (EditText) findViewById(R.id.search_input_distance);

    }

    /**
     * Listener for search button
     */
    private View.OnClickListener searchHandler = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            //TODO Perform the search by doing calls to the server
            APICaller api = new APICaller();
            JSONArray array;

            ArrayList<HokiEvent> events = new ArrayList<HokiEvent>();

            try{


                array = api.APIgetEventAll();


            } catch (Exception e)
            {
                System.out.print(e);
            }




            Intent startResultsActivity = new Intent(v.getContext(), SearchResultsActivity.class);
            startResultsActivity.putParcelableArrayListExtra(HokiEvent.EVENT, events);
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

        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
                navigate(tabId);
            }
        });
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
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // Necessary to restore the BottomBar's state, otherwise we would
        // lose the current tab on orientation change.
        //bottomBar.onSaveInstanceState(outState);
    }
}
