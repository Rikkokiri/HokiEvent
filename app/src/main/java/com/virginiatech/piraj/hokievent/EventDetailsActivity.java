package com.virginiatech.piraj.hokievent;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class EventDetailsActivity extends AppCompatActivity {

    //TODO eventID ?

    private TextView eventName;
    private TextView eventDate;
    private TextView eventTime;
    private TextView eventAddress;
    private TextView eventDescription;

    //TODO More elements...

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details);

        eventName = (TextView) findViewById(R.id.eventName);
        eventDate = (TextView) findViewById(R.id.eventDate);
        eventTime = (TextView) findViewById(R.id.eventTime);
        eventAddress = (TextView) findViewById(R.id.eventAddress);
        eventDescription = (TextView) findViewById(R.id.eventDescription);

        //TODO Pull data from the "server" and write it into TextViews

    }
}
