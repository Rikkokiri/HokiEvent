package com.virginiatech.piraj.hokievent;

import android.app.Activity;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

import java.io.IOException;
import java.util.List;

public class EventDetailsActivity extends AppCompatActivity implements OnMapReadyCallback {

    //TODO eventID ?

    private TextView eventName;
    private TextView eventDate;
    private TextView eventTime;
    private TextView eventAddress;
    private TextView eventDescription;
    private TextView eventTags;

    private Button leftButton;
    private Button rightButton;

    //TODO More elements...

    //Map
    private MapFragment mapFragment;
    private GoogleMap map;

    private HokiEvent event = null;

    // --- Bottom bar ---
    BottomBar bottomBar;
    private boolean activityLaunched = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details);

        buildBottomBar(this, savedInstanceState);

        //Find view components
        findById();

        //TODO Pull data from the "server" and write it into TextViews
        //this.event = ...

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        if (bundle != null)
        {
            event = bundle.getParcelable(HokiEvent.EVENT);
        }

        if(event != null){
            showEventInfo();
        }

        // --- Map ---
        mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);

        // Use getMapAsync() to set the callback on the fragment.
        mapFragment.getMapAsync(this);

    }


    private void findById(){
        eventName = (TextView) findViewById(R.id.eventName);
        eventDate = (TextView) findViewById(R.id.eventDate);
        eventTime = (TextView) findViewById(R.id.eventTime);
        eventAddress = (TextView) findViewById(R.id.eventAddress);
        eventDescription = (TextView) findViewById(R.id.eventDescription);
        eventTags = (TextView) findViewById(R.id.tags);

        leftButton = (Button) findViewById(R.id.leftButton);
        rightButton = (Button) findViewById(R.id.rightButton);

    }

    private void showEventInfo(){

        //Event title
        eventName.setText(event.getEventName());

        if(event.getEventEndDate() != null){
            //Multiday event
            eventDate.setText("From " + event.getEventStartDate() + " to " + event.getEventEndDate());
        } else {
            eventDate.setText(event.getEventStartDate());
        }

        if(event.getEventEndTime() != null){
            //Has end time
            eventTime.setText("From " + event.getEventStartTime() + " to " + event.getEventEndTime());
        } else {
            eventTime.setText(event.getEventStartTime());
        }

        //Event address
        eventAddress.setText(event.getEventLoc());

        //Event description
        eventDescription.setText(event.getEventDesc());

        eventTags.setText(event.getInterests());

    }

    /**
     * Use the onMapReady(GoogleMap) callback method to get a handle to the GoogleMap object.
     * The callback is triggered when the map is ready to be used.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;


        if(event == null){

            //TODO Handle the situation where we don't have address

        } else {
            Geocoder geocoder = new Geocoder(this);
            List<Address> addresses = null;

            try {
                addresses = geocoder.getFromLocationName(event.getEventLoc(), 1);

            } catch (IOException e) {
                e.printStackTrace();
            }

            if (addresses.size() > 0) {
                double latitude = addresses.get(0).getLatitude();
                double longitude = addresses.get(0).getLongitude();

                map.addMarker(new MarkerOptions()
                        .position(new LatLng(latitude, longitude))
                        .title("Event location"));

                map.moveCamera( CameraUpdateFactory.newLatLngZoom(new LatLng(latitude,longitude) , 14.0f) );
            }

        }
    }


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

        outState.putParcelable(HokiEvent.EVENT, event);

        super.onSaveInstanceState(outState);

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {

        event = savedInstanceState.getParcelable(HokiEvent.EVENT);

        showEventInfo();
        super.onRestoreInstanceState(savedInstanceState);
    }

}
