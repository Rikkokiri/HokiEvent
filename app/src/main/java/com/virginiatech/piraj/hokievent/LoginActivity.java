package com.virginiatech.piraj.hokievent;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 *
 * @version 2016.11.24
 */

public class LoginActivity extends Activity {

    // --- Textfields & buttons ---
    private EditText emailField;
    private EditText passwordField;

    private TextView loginFailed;

    private Button loginButton;
    private Button createAccountButton;

    //TODO Forgot password


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // -*-*-*- Textfields -*-*-*-
        emailField = (EditText) findViewById(R.id.email);
        passwordField = (EditText) findViewById(R.id.password);

        loginFailed = (TextView) findViewById(R.id.loginFailed);

        // -*-*-*- Buttons -*-*-*-
        loginButton = (Button) findViewById(R.id.loginButton);
        loginButton.setOnClickListener(loginHandler);


        createAccountButton = (Button) findViewById(R.id.createAccountButton);
        createAccountButton.setOnClickListener(createAccountListener);

    }

    /**
     * Listener for login button
     */
    private View.OnClickListener loginHandler = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            User user;

            //TODO Communicate with server to look for user from given login credentials

            if (false) //TODO Temporarily skip login and just move to home activity
            {
                loginFailed.setText("Invalid email/password");
                return;
            }

            //Create intent for starting create account activity
            Intent startHomeIntent = new Intent(view.getContext(), HomeActivity.class);

            //Start the CreateAccountActivity
            startActivity(startHomeIntent);

        }
    };

    /**
     * Listener for create account button
     */
    private View.OnClickListener createAccountListener = new  View.OnClickListener() {
        @Override
        public void onClick(View view) {

            //Create intent for starting create account activity
            Intent startCreateAccountIntent = new Intent(view.getContext(), CreateAccountActivity.class);

            //Start the CreateAccountActivity
            startActivity(startCreateAccountIntent);
        }
    };

}
