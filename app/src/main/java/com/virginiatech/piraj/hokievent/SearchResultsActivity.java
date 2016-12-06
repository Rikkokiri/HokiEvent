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

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

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


        //TEST DATA
        eventList = new ArrayList<HokiEvent>();

        HokiEvent e0 = new HokiEvent("Epic all-nighter", "The most epic all-nighter of all time", "139 Clover Valley Circle, Blacksburg, VA 24060, USA", "December 6th 2016", "Forever", "No tags");
        HokiEvent e1 = new HokiEvent("name", "desc", "beijing", "startDate", "startTime", "interests");
        HokiEvent e2 = new HokiEvent("name2", "desc2", "moscow", "startDat2e", "startTime2", "interests2");
        HokiEvent e3 = new HokiEvent("name3", "des3c", "london", "startDate3", "startTim3e", "interests3");
        HokiEvent e4 = new HokiEvent("name4", "des4c", "paris", "startDat4e", "startTim4e", "interests4");

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
