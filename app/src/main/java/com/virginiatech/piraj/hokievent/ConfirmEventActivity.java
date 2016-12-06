package com.virginiatech.piraj.hokievent;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.roughike.bottombar.BottomBar;

public class ConfirmEventActivity extends AppCompatActivity implements OnMapReadyCallback {

    //TODO eventID ?

    private TextView eventName;
    private TextView eventDateTime;
    private TextView eventAddress;
    private TextView eventDescription;

    private Button back;
    private Button finish;

    //TODO More elements...

    //Map
    private MapFragment mapFragment;
    private GoogleMap map;



    // --- HokiEvent ---

    private HokiEvent hokiEvent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_event);

        //buildBottomBar(this, savedInstanceState);

        eventName = (TextView) findViewById(R.id.confirmEventName);
        eventDateTime = (TextView) findViewById(R.id.confirmEventDateTime);
        eventAddress = (TextView) findViewById(R.id.confirmEventAddress);
        eventDescription = (TextView) findViewById(R.id.confirmEventDescription);

        back = (Button) findViewById(R.id.backToCreateEvent);
        back.setOnClickListener(backListener);

        finish = (Button) findViewById(R.id.finishEventCreation);
        finish.setOnClickListener(finishListener);

        //TODO Pull data from the "server" and write it into TextViews

        Bundle extras = getIntent().getExtras();
        if (extras != null)
        {
            hokiEvent = extras.getParcelable(HokiEvent.EVENT);
        }


        // --- Map ---
        mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        // Use getMapAsync() to set the callback on the fragment.
        mapFragment.getMapAsync(this);



    }

    /**
     * Listener for Sign up -button
     */
    private View.OnClickListener finishListener = new View.OnClickListener(){
        @Override
        public void onClick(View view){


            //TODO Communicate with server and send it the new event entry

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


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

    }

}
