package com.virginiatech.piraj.hokievent;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class CreateAccountActivity extends AppCompatActivity {

    // --- Textfields ---
    private EditText firstName;
    private EditText middleName;
    private EditText lastName;

    private EditText phonenumber;
    private EditText email;
    private EditText confirmEmail;

    private EditText password;
    private EditText confirmPassword;

    // --- Buttons ---
    private Button selectInterestsButton;
    private Button signUpButton;
    private Button cancelButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        //  -*-*-*- Textfields  -*-*-*-
        firstName = (EditText) findViewById(R.id.firstName);
        middleName = (EditText) findViewById(R.id.middleName);
        lastName = (EditText) findViewById(R.id.lastName);

        phonenumber = (EditText) findViewById(R.id.phonenumber);
        email = (EditText) findViewById(R.id.email);
        confirmEmail = (EditText) findViewById(R.id.confirmEmail);

        //TODO How to deal with password fields?
        password = (EditText) findViewById(R.id.password);
        confirmPassword = (EditText) findViewById(R.id.confirmPassword);

        // -*-*-*- Buttons & listeners for buttons  -*-*-*-
        selectInterestsButton = (Button) findViewById(R.id.selectInterestsButton);
        selectInterestsButton.setOnClickListener(selectInterestListener);

        signUpButton = (Button) findViewById(R.id.selectInterestsButton);
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

            //TODO Open SelectInterestsActivity to allow user to select their interests, save data the user has entered so far

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
     * Listener for cancel button
     */
    private View.OnClickListener cancelListener = new View.OnClickListener() {
        @Override
        public void onClick(View view){

            //TODO Go back to log in/sign up screen

        }
    };

}
