package com.virginiatech.piraj.hokievent;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.IdRes;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabReselectListener;
import com.roughike.bottombar.OnTabSelectListener;

import org.json.JSONObject;
import org.w3c.dom.Text;

/**
 *
 * @version 2016.12.XX
 */
public class ProfileActivity extends AppCompatActivity implements ResponseRetriever {

    private BottomBar bottomBar;

    private TextView fullNameField;
    private TextView phoneNumberField;
    private TextView emailField;
    private TextView interestsField;

    private User user = null;

    private boolean activityLaunched = false;

    private Button editProfileButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        //Build bottom navigation
        buildBottomBar(this);

        // --- TextViews for displaying the user information ---
        fullNameField = (TextView) findViewById(R.id.profileFullName);
        phoneNumberField = (TextView) findViewById(R.id.profilePhonenumber);
        emailField = (TextView) findViewById(R.id.profileEmail);
        interestsField = (TextView) findViewById(R.id.profileInterestList);


        // --- Edit profile button ---
        editProfileButton = (Button) findViewById(R.id.editProfileButton);
        editProfileButton.setOnClickListener(editProfileListener);

        //TODO Pull the user's data from the server?
        //new Communicator().getUser(this, "kyz@vt.edu");

        //TODO Populate text views with data pulled from the server
        if(user != null) {
            displayUserInfo(user);
        }

        //TODO Populate pull the interests from the server and show them

    }

    /**
     * Fill in the TextViews showing the user's name, phone number and email
     */
    private void displayUserInfo(User user){
        //Show name
        fullNameField.setText(user.getFirstName() + " " + user.getMiddleName() + " " + user.getLastName());
        //Show phone number
        phoneNumberField.setText(user.getPhoneNumber());
        //Show email
        emailField.setText(user.getUserEmail());
        interestsField.setText(user.getInterests());
    }

    /**
     * Listener for "Edit profile" -button. Pressing the button takes user to Edit Profile view.
     */
    private View.OnClickListener editProfileListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent editProfileIntent = new Intent(view.getContext(), EditProfileActivity.class);

            //TODO: Should we pass the user to the new activity or request user data from there?
            editProfileIntent.putExtra(User.USER, user);
            startActivity(editProfileIntent);
        }
    };

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
                //Do nothing
                break;
        }
    }

    @Override
    public void getResponse(JSONObject jsonObject) {

        if(jsonObject != null){
            interestsField.setText(jsonObject.toString());
        } else {
            interestsField.setText("JSON NULL");
        }
    }

}
