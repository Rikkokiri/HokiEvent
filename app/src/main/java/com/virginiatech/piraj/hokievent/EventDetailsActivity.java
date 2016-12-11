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
import com.roughike.bottombar.OnTabReselectListener;
import com.roughike.bottombar.OnTabSelectListener;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class EventDetailsActivity extends AppCompatActivity implements OnMapReadyCallback, TaskCompleted{

    private TextView eventName;
    private TextView eventTime;
    private TextView eventAddress;
    private TextView eventDescription;
    private TextView eventTags;

    private Button leftButton;
    private Button rightButton;

    private boolean owned;
    private boolean saved;
    private boolean joined;

    //Map
    private MapFragment mapFragment;
    private GoogleMap map;

    private HokiEvent event = null;
    private String email;

    // --- Bottom bar ---
    BottomBar bottomBar;
    private boolean activityLaunched = false;

    public static final String JOIN = "join";
    public static final String LEAVE = "leave";
    public static final String SAVE = "save";
    public static final String UNSAVE = "unsave";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details);

        buildBottomBar(this, savedInstanceState);

        //Find view components
        findById();

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        owned = false;
        saved = false;
        joined = false;

        try {

            FileInputStream fin = openFileInput(User.USER_FILE);
            BufferedReader reader = new BufferedReader(new InputStreamReader(fin));

            email = reader.readLine();
            System.out.println("email: " + email);

            reader.close();

        } catch (IOException e)
        {
            e.printStackTrace();
        }

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

    /**
     * Find layout components by ID
     */
    private void findById(){

        eventName = (TextView) findViewById(R.id.eventName);
        eventTime = (TextView) findViewById(R.id.eventTime);
        eventAddress = (TextView) findViewById(R.id.eventAddress);
        eventDescription = (TextView) findViewById(R.id.eventDescription);
        eventTags = (TextView) findViewById(R.id.tags);

        leftButton = (Button) findViewById(R.id.leftButton);
        rightButton = (Button) findViewById(R.id.rightButton);

    }

    private void getRelationships()
    {
        try {

            FileInputStream fin = openFileInput(User.USER_FILE);
            BufferedReader reader = new BufferedReader(new InputStreamReader(fin));

            email = reader.readLine();
            System.out.println("email: " + email);

            if (event.getOwnerEmail().equals(email)) {
                owned = true;
            }

            //TODO Get rest of the relationships?
            //TODO: Saved
            //TODO: Joined

            reader.close();

        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }



    private void showEventInfo(){

        //Event title
        eventName.setText(event.getEventName());

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
        else
        {
            date = event.getEventStartDate() + "\n" + event.getEventStartTime();
            if(event.getEventEndTime() != null){
                //Has end time
                 date += " to " + event.getEventEndTime();
            }

        }

        eventTime.setText(date);

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
                leftButton.setOnClickListener(unsaveListener);
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

    
    public void sendData(String mode)
    {
        //Send server new user entry
        JSONObject json = new JSONHelper().eventMembership(event.getEventName(), email);

        System.out.println(json);
        if(json != null) {
            APICaller api = new APICaller(this);
            try {
                switch (mode)
                {
                    case JOIN:
                        api.APIpostJoinEvent(json);
                        break;
                    case LEAVE:
                        api.APIpostLeaveEvent(json);
                        break;
                    case SAVE:
                        api.APIpostSaveEvent(json);
                        break;
                    case UNSAVE:
                        api.APIpostUnsaveEvent(json);
                        break;
                }
            } catch (Exception e) {
                System.out.println(e);
            }
        }
        else {
            //TODO Handle the case where the JSON couldn't be created ???
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

    /**
     * Listener for Edit event -button
     */
    private View.OnClickListener editListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            Intent editEventIntent = new Intent(v.getContext(), EditEventActivity.class);

            //Pass the event to the EditEventActivity
            editEventIntent.putExtra(HokiEvent.EVENT, event);

            startActivityForResult(editEventIntent, 0);

        }
    };

    /**
     * Listener for Save event -button
     */
    private View.OnClickListener saveListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            sendData(SAVE);
            saved = true;
            setUpButtons();
        }
    };


    /**
     * Listener for Unsave event -button
     */
    private View.OnClickListener unsaveListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            sendData(UNSAVE);
            saved = false;
            setUpButtons();
        }
    };

    /**
     * Listener for Join event -button
     */
    private View.OnClickListener joinListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            sendData(JOIN);
            joined = true;
            setUpButtons();
        }
    };

    /**
     * Listener for Leave event -button
     */
    private View.OnClickListener leaveListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            sendData(LEAVE);
            joined = false;
            setUpButtons();
        }
    };

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 0 && resultCode == RESULT_OK && data != null) {
            event = data.getParcelableExtra(HokiEvent.EVENT);
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

        bottomBar.setOnTabReselectListener(new OnTabReselectListener() {
            @Override
            public void onTabReSelected(@IdRes int tabId) {
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
                System.out.println("HEADING HOME!");

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

    @Override
    public void onTaskComplete(String result) {

    }
}
