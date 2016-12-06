package com.virginiatech.piraj.hokievent;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.IdRes;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    ArrayList<HokiEvent> eventList;

    private BottomBar bottomBar;
    private static String CURRENT_TAB = "current_tab";

    private RecyclerView recyclerView;
    private EventAdapter eAdapter;
    private TextView message;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        message = (TextView) findViewById(R.id.homeMessage);


        //TEST DATA
        eventList = new ArrayList<HokiEvent>();

        HokiEvent e0 = new HokiEvent("Epic all-nighter", "The most epic all-nighter of all time", "139 Clover Valley Circle, Blacksburg, VA 24060, USA", "December 6th 2016", "Forever", "Academics");
        HokiEvent e1 = new HokiEvent("Christian's Birthday", "I turn 28.  DESPAIR", "139 Clover Valley Circle, Blacksburg, VA 24060, USA", "December 11th 2016", "12:00 AM", "no one");
        eventList.add(e0);
        eventList.add(e1);



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
            message.setText("You have no events!");
        }
        else
        {
            message.setText("");
        }


        //Build the bottom navigation
        buildBottomBar(this);

    }

    /**
     * Build navigation bar located on the bottom of the screen.
     *
     * @param activity
     */
    private void buildBottomBar(Activity activity){

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
                //Do nothing
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

        outState.putInt(CURRENT_TAB, R.id.action_home);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {

        //TODO This doesn't work :-/
        bottomBar.selectTabWithId(savedInstanceState.getInt(CURRENT_TAB));
        super.onRestoreInstanceState(savedInstanceState);
    }

}
