package com.virginiatech.piraj.hokievent;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class CreateAccountActivity extends AppCompatActivity {

    // --- Textfields ---
    private EditText firstNameField;
    private EditText middleNameField;
    private EditText lastNameField;

    private EditText phonenumberField;
    private EditText emailField;
    private EditText confirmEmailField;

    private EditText passwordField;
    private EditText confirmPasswordField;

    // --- Buttons ---
    private Button selectInterestsButton;
    private Button signUpButton;
    private Button cancelButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        //  -*-*-*- Textfields  -*-*-*-
        firstNameField = (EditText) findViewById(R.id.firstName);
        middleNameField = (EditText) findViewById(R.id.middleName);
        lastNameField = (EditText) findViewById(R.id.lastName);

        phonenumberField = (EditText) findViewById(R.id.phonenumber);
        emailField = (EditText) findViewById(R.id.email);
        confirmEmailField = (EditText) findViewById(R.id.confirmEmail);

        //TODO How to deal with password fields?
        passwordField = (EditText) findViewById(R.id.password);
        confirmPasswordField = (EditText) findViewById(R.id.confirmPassword);

        // -*-*-*- Buttons & listeners for buttons  -*-*-*-
        selectInterestsButton = (Button) findViewById(R.id.selectInterestsButton);
        selectInterestsButton.setOnClickListener(selectInterestListener);

        signUpButton = (Button) findViewById(R.id.signUpButton);
        signUpButton.setOnClickListener(signUpListener);

        cancelButton = (Button) findViewById(R.id.cancelButton);
        cancelButton.setOnClickListener(cancelListener);

        //TODO Add functionality to button listeners

    }

    /**
     * Listener for Select interests -button
     */
    private View.OnClickListener selectInterestListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            // TODO save data the user has entered so far

            // Open SelectInterestsActivity to allow user to select their interests
            //Intent selectInterests = new Intent(view.getContext(), InterestsActivity.class);
            //Intent selectInterests = new Intent(view.getContext(), EventDetailsActivity.class);
            //Intent selectInterests = new Intent(view.getContext(), CreateEventActivity.class);
            //Intent selectInterests = new Intent(view.getContext(), EventDetailsActivity.class);
            Intent selectInterests = new Intent(view.getContext(), HomeActivity.class);
            startActivity(selectInterests);
        }
    };

    /**
     * Listener for Sign up -button
     */
    private View.OnClickListener signUpListener = new View.OnClickListener(){
        @Override
        public void onClick(View view){

            //TODO Take the data user has provided and (try to) create a new account (verify email etc?)

        }
    };

    /**
     * Listener for cancel button. Pressing the cancel button takes the user back to the login/sign up view
     */
    private View.OnClickListener cancelListener = new View.OnClickListener() {
        @Override
        public void onClick(View view){
            //Go back to log in/sign up screen
            Intent returnToLogin = new Intent(view.getContext(), LoginActivity.class);
            startActivity(returnToLogin);
        }
    };

}
