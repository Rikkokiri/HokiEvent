package com.virginiatech.piraj.hokievent;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.IdRes;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnMenuTabClickListener;

/**
 *
 * @version 2016.12.XX
 */
public class ProfileActivity extends AppCompatActivity {

    private BottomBar bottomBar;

    private Button editProfileButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        //Build bottom navigation
        //buildBottomBar(this, savedInstanceState);

        editProfileButton = (Button) findViewById(R.id.editProfileButton);
        editProfileButton.setOnClickListener(editProfileListener);
        
    }

    /**
     * Listener for "Edit profile" -button. Pressing the button takes user to Edit Profile view.
     */
    private View.OnClickListener editProfileListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent editProfileIntent = new Intent(view.getContext(), EditProfileActivity.class);
            startActivity(editProfileIntent);
        }
    };

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
                //Do nothing
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
                //Do nothing
                break;
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // Necessary to restore the BottomBar's state, otherwise we would
        // lose the current tab on orientation change.
        //bottomBar.onSaveInstanceState(outState);
    }
}
