package com.virginiatech.piraj.hokievent;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.IdRes;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.roughike.bottombar.BottomBar;

import org.w3c.dom.Text;

/**
 *
 * @version 2016.12.XX
 */
public class ProfileActivity extends AppCompatActivity {

    private BottomBar bottomBar;

    private TextView fullNameField;
    private TextView phoneNumberField;
    private TextView emailField;
    private TextView interestsField;

    private User user = null;

    private Button editProfileButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        //Build bottom navigation
        //buildBottomBar(this, savedInstanceState);

        // --- TextViews for displaying the user information ---
        fullNameField = (TextView) findViewById(R.id.profileFullName);
        phoneNumberField = (TextView) findViewById(R.id.profilePhonenumber);
        emailField = (TextView) findViewById(R.id.profileEmail);
        interestsField = (TextView) findViewById(R.id.profileInterestList);


        // --- Edit profile button ---
        editProfileButton = (Button) findViewById(R.id.editProfileButton);
        editProfileButton.setOnClickListener(editProfileListener);

        //TODO Pull the user's data from the server?

        //TODO Populate text views with data pulled from the server
        //displayUserInfo(user);

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
     * @param savedInstanceState
     */
    private void buildBottomBar(Activity activity, Bundle savedInstanceState){

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

}
