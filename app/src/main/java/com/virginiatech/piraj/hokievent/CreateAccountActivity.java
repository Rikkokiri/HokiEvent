package com.virginiatech.piraj.hokievent;

import android.content.Intent;
import android.os.Parcel;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONObject;

public class CreateAccountActivity extends AppCompatActivity {

    // --- Textfields ---
    private EditText firstNameField;
    private EditText middleNameField;
    private EditText lastNameField;

    private EditText phoneNumberField;
    private EditText emailField;
    private EditText confirmEmailField;

    private EditText passwordField;
    private EditText confirmPasswordField;

    // --- TextViews ---
    private TextView messageView;
    private TextView interestsView;

    // --- Buttons ---
    private Button selectInterestsButton;
    private Button signUpButton;
    private Button cancelButton;

    // --- Strings ---
    private String interests; //used to store interests for user account creation

    static final String FIRST_NAME = "first name";
    static final String MIDDLE_NAME = "middle name";
    static final String LAST_NAME = "last name";
    static final String PHONE = "phone";
    static final String EMAIL = "email";
    static final String CONFIRM_EMAIL = "confirm email";
    static final String PASS = "pass";
    static final String CONFIRM_PASS = "confirm pass";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        //  -*-*-*- Textfields  -*-*-*-
        firstNameField = (EditText) findViewById(R.id.firstName);
        middleNameField = (EditText) findViewById(R.id.middleName);
        lastNameField = (EditText) findViewById(R.id.lastName);

        phoneNumberField = (EditText) findViewById(R.id.phonenumber);
        emailField = (EditText) findViewById(R.id.email);
        confirmEmailField = (EditText) findViewById(R.id.confirmEmail);

        passwordField = (EditText) findViewById(R.id.password);
        confirmPasswordField = (EditText) findViewById(R.id.confirmPassword);

        messageView = (TextView) findViewById(R.id.createAccountMessage);
        interestsView = (TextView) findViewById(R.id.interestsList);

        // -*-*-*- Buttons & listeners for buttons  -*-*-*-
        selectInterestsButton = (Button) findViewById(R.id.selectInterestsButton);
        selectInterestsButton.setOnClickListener(selectInterestListener);

        signUpButton = (Button) findViewById(R.id.signUpButton);
        signUpButton.setOnClickListener(signUpListener);

        cancelButton = (Button) findViewById(R.id.cancelButton);
        cancelButton.setOnClickListener(cancelListener);


        interests = "";

        interestsView.setText("Select interests");

        //TODO Add functionality to button listeners

    }

    /**
     * Listener for Select interests -button
     */
    private View.OnClickListener selectInterestListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            // Open SelectInterestsActivity to allow user to select their interests
            Intent interestActivityIntent = new Intent(view.getContext(), InterestsActivity.class);
            interestActivityIntent.putExtra(InterestsActivity.INTEREST, interests);

            System.out.println("CreateAccount sends :" + interestActivityIntent.getStringExtra(InterestsActivity.INTEREST));
            startActivityForResult(interestActivityIntent, 0);


        }
    };

    /**
     * Listener for Sign up -button
     */
    private View.OnClickListener signUpListener = new View.OnClickListener(){
        @Override
        public void onClick(View view){



            if (!emailField.getText().toString().equals(confirmEmailField.getText().toString()))
            {
                messageView.setText("Emails do not match");
                return;
            }
            if (!passwordField.getText().toString().equals(confirmPasswordField.getText().toString()))
            {
                messageView.setText("Passwords do not match");
                return;
            }

            User user = new User(
                    firstNameField.getText().toString(),
                    middleNameField.getText().toString(),
                    lastNameField.getText().toString(),
                    emailField.getText().toString(),
                    Integer.parseInt(phoneNumberField.getText().toString()),
                    interests,
                    passwordField.getText().toString()
            );

            //TODO Communicate with server and send it the new user entry
            new Communicator().addUser(user);

            Intent startHomeActivity = new Intent(view.getContext(), HomeActivity.class);

            startHomeActivity.putExtra(User.USER, user);

            startActivity(startHomeActivity);

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

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 0 && resultCode == RESULT_OK && data != null) {
            interests = data.getStringExtra(InterestsActivity.INTEREST);
            System.out.println("CreateAccountActivity received: " + interests);
            if (interests.equals(""))
            {
                interestsView.setText("Select interest");
            }
            else
            {
                interestsView.setText(interests);
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {

        outState.putString(FIRST_NAME, firstNameField.getText().toString());
        outState.putString(MIDDLE_NAME, middleNameField.getText().toString());
        outState.putString(LAST_NAME, lastNameField.getText().toString());
        outState.putString(EMAIL, emailField.getText().toString());
        outState.putString(PHONE, phoneNumberField.getText().toString());
        outState.putString(CONFIRM_EMAIL, confirmEmailField.getText().toString());
        outState.putString(PASS, passwordField.getText().toString());
        outState.putString(CONFIRM_PASS, confirmPasswordField.getText().toString());
        outState.putString(InterestsActivity.INTEREST, interests);

        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {

        firstNameField.setText(savedInstanceState.getString(FIRST_NAME));
        middleNameField.setText(savedInstanceState.getString(MIDDLE_NAME));
        lastNameField.setText(savedInstanceState.getString(LAST_NAME));
        emailField.setText(savedInstanceState.getString(EMAIL));
        confirmEmailField.setText(savedInstanceState.getString(CONFIRM_EMAIL));
        phoneNumberField.setText(savedInstanceState.getString(PHONE));
        passwordField.setText(savedInstanceState.getString(PASS));
        confirmPasswordField.setText(savedInstanceState.getString(CONFIRM_PASS));
        interests = savedInstanceState.getString(InterestsActivity.INTEREST);

        super.onRestoreInstanceState(savedInstanceState);

    }

}
