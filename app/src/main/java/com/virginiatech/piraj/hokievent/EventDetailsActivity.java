package com.virginiatech.piraj.hokievent;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.IdRes;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnMenuTabClickListener;

public class EventDetailsActivity extends AppCompatActivity implements OnMapReadyCallback {

    //TODO eventID ?

    private TextView eventName;
    private TextView eventDate;
    private TextView eventTime;
    private TextView eventAddress;
    private TextView eventDescription;

    //TODO More elements...

    //Map
    private MapFragment mapFragment;
    private GoogleMap map;

    // --- Bottom bar ---
    BottomBar bottomBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details);

        buildBottomBar(this, savedInstanceState);

        eventName = (TextView) findViewById(R.id.eventName);
        eventDate = (TextView) findViewById(R.id.eventDate);
        eventTime = (TextView) findViewById(R.id.eventTime);
        eventAddress = (TextView) findViewById(R.id.eventAddress);
        eventDescription = (TextView) findViewById(R.id.eventDescription);

        //TODO Pull data from the "server" and write it into TextViews

        // --- Map ---
        mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        // Use getMapAsync() to set the callback on the fragment.
        mapFragment.getMapAsync(this);

    }

    /**
     * Use the onMapReady(GoogleMap) callback method to get a handle to the GoogleMap object.
     * The callback is triggered when the map is ready to be used.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;

        setLocationMarker();
    }

    /**
     *
     */
    private void setLocationMarker(){

        if(map != null){

            //TODO

        }
    }

    /**
     * Build navigation bar located on the bottom of the screen.
     *
     * @param activity
     * @param savedInstanceState
     */
    private void buildBottomBar(Activity activity, Bundle savedInstanceState){

        bottomBar = BottomBar.attach(activity, savedInstanceState,
                ContextCompat.getColor(this, R.color.colorPrimary), // Background Color
                ContextCompat.getColor(this, R.color.colorAccent), // Tab Item Color
                0.25f); // Tab Item Alpha
        bottomBar.setItems(R.menu.navigation_bar_items);


        bottomBar.setOnMenuTabClickListener(new OnMenuTabClickListener() {
            @Override
            public void onMenuTabSelected(@IdRes int menuItemId) {
                navigate(menuItemId);
            }

            @Override
            public void onMenuTabReSelected(@IdRes int menuItemId) {
                Log.i("NavBar","Menu item selected");
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
                Intent goHomeIntent = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(goHomeIntent);
                break;

            case R.id.action_create_event:
                Intent createEventIntent = new Intent(getApplicationContext(), CreateEventActivity.class);
                startActivity(createEventIntent);
                break;

            case R.id.action_search:
                Intent searchIntent = new Intent(getApplicationContext(), SearchActivity.class);
                startActivity(searchIntent);
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
        bottomBar.onSaveInstanceState(outState);
    }

}
