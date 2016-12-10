package com.virginiatech.piraj.hokievent;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.google.android.gms.tasks.TaskCompletionSource;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;

public class SearchResultsActivity extends AppCompatActivity {

    ArrayList<HokiEvent> eventList;

    private BottomBar bottomBar;
    private static String CURRENT_TAB = "current_tab";

    private RecyclerView recyclerView;
    private EventAdapter eAdapter;
    private TextView message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);

        message = (TextView) findViewById(R.id.resultsMessage);

        Intent intent = getIntent();
        eventList = intent.getParcelableArrayListExtra(HokiEvent.EVENT);
/*
        APICaller api = new APICaller();
        JSONArray array;
*/
        ArrayList<HokiEvent> events = new ArrayList<HokiEvent>();


        //TEST DATA
        eventList = new ArrayList<HokiEvent>();

        HokiEvent e0 = new HokiEvent("Epic all-nighter", "The most epic all-nighter of all time", "139 Clover Valley Circle, Blacksburg, VA 24060, USA", "December 6th 2016", "Forever", "Academics", "kyz@vt.edu");
        HokiEvent e1 = new HokiEvent("Fashion Show", "People dress up in ridiculous outfits", "paris", "December 10", "10 AM", "Culture, Sports", "fake@email.com");
        HokiEvent e2 = new HokiEvent("Christian's Birthday", "I turn 28.  DESPAIR", "139 Clover Valley Circle, Blacksburg, VA 24060, USA", "December 11th 2016", "12:00 AM", "no one","k4b0odls@vt.edu");
        HokiEvent e3 = new HokiEvent("Christmas", "The most wonderful time of the year", "North Pole", "December 25 2016", "12:00 AM", "Family, Food", "k4b0odls@vt.edu");
        HokiEvent e4 = new HokiEvent("New Year", "Go to hell, 2016", "New York City", "January 1, 217", "12:00 AM", "Culture, Family, Food", "fake@email.com");


        eventList.add(e0);
        eventList.add(e1);
        eventList.add(e2);
        eventList.add(e3);
        eventList.add(e4);


        recyclerView = (RecyclerView) findViewById(R.id.cardList);
        recyclerView.setHasFixedSize(true);
        eAdapter = new EventAdapter(eventList);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);
        recyclerView.setAdapter(eAdapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        //TEST


        if (eventList.size() == 0)
        {
            message.setText("No events found");
        }
        else
        {
            message.setText("");
        }


    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {

        outState.putInt(CURRENT_TAB, R.id.action_home);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {


        super.onRestoreInstanceState(savedInstanceState);
    }


}
