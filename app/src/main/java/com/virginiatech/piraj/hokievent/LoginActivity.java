package com.virginiatech.piraj.hokievent;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 *
 * @version 2016.11.24
 */

public class LoginActivity extends Activity {

    // --- Textfields & buttons ---
    private EditText emailField;
    private EditText passwordField;

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

        // -*-*-*- Buttons -*-*-*-
        loginButton = (Button) findViewById(R.id.loginButton);


        createAccountButton = (Button) findViewById(R.id.createAccountButton);
        createAccountButton.setOnClickListener(createAccountListener);

    }

    /**
     * Listener for login button
     */
    private View.OnClickListener loginHandler = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            //TODO

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
