package com.virginiatech.piraj.hokievent;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.roughike.bottombar.BottomBar;

public class SearchActivity extends AppCompatActivity {

    private BottomBar bottomBar;

    private Button searchButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        //Build bottom navigation
        //buildBottomBar(this, savedInstanceState);

        //Search button
        searchButton = (Button) findViewById(R.id.searchButton);
        searchButton.setOnClickListener(searchHandler);

    }


    /**
     * Listener for search button
     */
    private View.OnClickListener searchHandler = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            //TODO Perform the search by doing calls to the server

        }
    };


    /**
     * Build navigation bar located on the bottom of the screen.
     *
     * @param activity
     * @param savedInstanceState
     */
    private void buildBottomBar(Activity activity, Bundle savedInstanceState){

    }

    /**
     *
     * @param menuID
     */
    private void navigate(int menuID){

        switch (menuID){
            case R.id.action_home:
                Intent goHomeIntent = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(goHomeIntent);
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
