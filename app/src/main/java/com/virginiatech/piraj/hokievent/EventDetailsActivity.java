package com.virginiatech.piraj.hokievent;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.support.annotation.IdRes;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import com.roughike.bottombar.OnTabSelectListener;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
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

    private boolean owned;
    private boolean saved;
    private boolean joined;

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

        leftButton = (Button) findViewById(R.id.leftButton);
        rightButton = (Button) findViewById(R.id.rightButton);

        buildBottomBar(this, savedInstanceState);

        //Find view components
        findById();

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        owned = false;
        saved = false;
        joined = false;

        if (bundle != null)
        {
            event = bundle.getParcelable(HokiEvent.EVENT);
        }

        if(event != null){
            getRelationships();
            showEventInfo();
        }

        setUpButtons();

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

        //leftButton = (Button) findViewById(R.id.leftButton);
        //rightButton = (Button) findViewById(R.id.rightButton);

    }

    private void getRelationships()
    {
        try {

            FileInputStream fin = openFileInput(User.USER_FILE);
            BufferedReader reader = new BufferedReader(new InputStreamReader(fin));

            String email = reader.readLine();
            System.out.println("email: " + email);
            if (event.getOwnerEmail().equals(email))
            {
                owned = true;
            }

            reader.close();

        } catch (IOException e)
        {
            e.printStackTrace();
        }
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

    private void setUpButtons()
    {
        if (owned)
        {
            leftButton.setText("Cancel Event");
            leftButton.setOnClickListener(cancelListener);
            rightButton.setText("Edit Event");
            rightButton.setOnClickListener(editListener);
        }
        else
        {
            if (saved)
            {
                leftButton.setText("Un-Save Event");
                leftButton.setOnClickListener(unSaveListener);
            }
            else
            {
                leftButton.setText("Save Event");
                leftButton.setOnClickListener(saveListener);
            }

            if (joined)
            {
                rightButton.setText("Leave Event");
                rightButton.setOnClickListener(leaveListener);
            }
            else
            {
                rightButton.setText("Join Event");
                rightButton.setOnClickListener(joinListener);
            }

        }
    }

    private View.OnClickListener cancelListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            new AlertDialog.Builder(v.getContext())
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle("Canceling Event")
                    .setMessage("Are you sure you want to cancel this event?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            //TODO cancel teh goddamn event

                            finish();
                        }

                    })
                    .setNegativeButton("No", null)
                    .show();
        }
    };

    private View.OnClickListener editListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            Intent editEventIntent = new Intent(v.getContext(), EditEventActivity.class);

            startActivityForResult(editEventIntent, 0);

        }
    };

    private View.OnClickListener saveListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            //TODO tell server to add this to user's list of saved events;
            saved = true;
            setUpButtons();
        }
    };

    private View.OnClickListener unSaveListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            //TODO tell server to add this to user's list of saved events;
            saved = false;
            setUpButtons();
        }
    };

    private View.OnClickListener joinListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            //TODO tell server to add this to user's list of saved events;
            joined = true;
            setUpButtons();
        }
    };

    private View.OnClickListener leaveListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            //TODO tell server to add this to user's list of saved events;
            joined = false;
            setUpButtons();
        }
    };

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 0 && resultCode == RESULT_OK && data != null) {
            event = data.getParcelableExtra(HokiEvent.EVENT);
            System.out.println("CreateAccountActivity received: " + event);
        }

        showEventInfo();
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
