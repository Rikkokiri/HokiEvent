package com.virginiatech.piraj.hokievent;

import android.app.Activity;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.roughike.bottombar.BottomBar;

import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.IOException;
import java.util.List;

/**
 * Activity that displays the user the event details they just entered in CreateEventActivity.
 * User can review the details, publish the event or return to CreateEventActivity to edit the details.
 */
public class ConfirmEventActivity extends AppCompatActivity implements OnMapReadyCallback, TaskCompleted {

    private TextView eventName;
    private TextView eventTime;
    private TextView eventAddress;
    private TextView eventDescription;
    private TextView eventTags;

    private Button back;
    private Button finish;

    //Map
    private MapFragment mapFragment;
    private GoogleMap map;

    // --- HokiEvent ---

    private HokiEvent event;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_event);

        findById();

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            event = extras.getParcelable(HokiEvent.EVENT);
        }

        // --- Map ---
        mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        // Use getMapAsync() to set the callback on the fragment.
        mapFragment.getMapAsync(this);

        showEventInfo();
    }

    private void showEventInfo() {

        // --- Event title ---
        eventName.setText(event.getEventName());

        // --- Event date & time ---

        String date;

        if(event.getEventEndDate() != null && event.getEventEndDate().equals(event.getEventStartDate())){
            //Multiday event
            date = "Starts " + event.getEventStartDate() + " at " + event.getEventStartTime() + "\n";
            if(event.getEventEndTime() != null){
                date += "Ends "+ event.getEventEndDate() + " at " + event.getEventEndTime();
            } else {
                date += "Ends " + event.getEventEndDate();
            }
        }

        else {
            date = event.getEventStartDate() + "\n" + event.getEventStartTime();
            if(event.getEventEndTime() != null){
                //Has end time
                date += " to " + event.getEventEndTime();
            }
        }

        eventTime.setText(date);

        // --- Event address ---
        eventAddress.setText(event.getEventLoc());

        // --- Event description ---
        eventDescription.setText(event.getEventDesc());

        eventTags.setText(event.getInterests());

    }

    /**
     * Find layout components by id
     */
    private void findById(){

        eventName = (TextView) findViewById(R.id.confirmEventName);
        eventTime = (TextView) findViewById(R.id.confirmEventTime);
        eventAddress = (TextView) findViewById(R.id.confirmEventAddress);
        eventDescription = (TextView) findViewById(R.id.confirmEventDescription);
        eventTags = (TextView) findViewById(R.id.confirmEventTags);

        back = (Button) findViewById(R.id.backToCreateEvent);
        back.setOnClickListener(backListener);

        finish = (Button) findViewById(R.id.finishEventCreation);
        finish.setOnClickListener(finishListener);

    }


    /**
     * Listener for Sign up -button
     */
    private View.OnClickListener finishListener = new View.OnClickListener(){
        @Override
        public void onClick(View view){

            //Send server new event entry
            sendData();

            Intent startHomeActivity = new Intent(view.getContext(), HomeActivity.class);
            startActivity(startHomeActivity);

        }
    };

    /**
     * Listener for cancel button. Pressing the cancel button takes the user back to the login/sign up view
     */
    private View.OnClickListener backListener = new View.OnClickListener() {
        @Override
        public void onClick(View view){
            //Go back to create event screen
            finish();
        }
    };

    public void sendData() {
        JSONObject eventJSON = new JSONHelper().createEventJSON(event);

        System.out.println(eventJSON);

        if(eventJSON != null) {

            APICaller api = new APICaller(this);

            try {
                api.APIpostEvent(eventJSON);

            } catch (Exception e) {
                System.out.println(e);
            }
        }
        else {
            //TODO Handle the case where the JSON couldn't be created ???
            throw new IllegalArgumentException("Event JSON couldn't be created!");
        }
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


    // - o - o - o - o - o - TASK COMPLEMETED  - o - o - o - o - o -

    @Override
    public void onTaskComplete(String result) {

        //TODO

    }
}
