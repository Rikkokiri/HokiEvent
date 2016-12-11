package com.virginiatech.piraj.hokievent;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

/**
 *
 * @version 2016.11.24
 */

public class LoginActivity extends Activity implements TaskCompleted {

    // --- Textfields & buttons ---
    private EditText emailField;
    private EditText passwordField;

    private TextView loginFailed;

    private Button loginButton;
    private Button createAccountButton;

    User user;

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

    private void sendLoginRequest()
    {
        user = null;

        APICaller api = new APICaller(this);
        try {
            api.APIlogin(JSONHelper.login(emailField.getText().toString(), passwordField.getText().toString()));
            //The result of this call is received in onTaskComplete
        }
        catch (JSONException e)
        {
            System.out.println(e.toString());
        }
        catch (IOException e)
        {
            System.out.println(e.toString());
        }
    }
    /**
     * Listener for login button
     */
    private View.OnClickListener loginHandler = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            sendLoginRequest();

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


    @Override
    public void onTaskComplete(String result) {

        if (result == null)
        {
            System.out.println("LoginActivity: LOGIN FAILED");
            loginFailed.setText("Invalid email/password");
            return;
        }

        try {
            JSONObject json = new JSONObject(result);
            user = JSONHelper.getUser(json);
        }
        catch (JSONException e) {
            e.printStackTrace();
        }

        System.out.println("Login successful?: " + user);


        try {

            File dir = getFilesDir();
            File file = new File(dir, User.USER_FILE);
            //file.delete();

            //FileOutputStream fos = openFileOutput(User.USER_FILE, Context.MODE_PRIVATE);
            FileOutputStream fos = openFileOutput(User.USER_FILE, Context.MODE_PRIVATE);

            OutputStreamWriter writer = new OutputStreamWriter(fos);

            writer.write(user.getUserEmail() + "\n");
            writer.write(user.getUserID() + "\n");
            writer.write(user.getFirstName() + "\n");
            writer.write(user.getMiddleName() + "\n");
            writer.write(user.getLastName() + "\n");
            writer.write(user.getPhoneNumber() + "\n");
            writer.write(user.getInterests() + "\n");
            writer.write(user.getPassword());

            writer.flush();
            writer.close();

            FileInputStream fin = openFileInput(User.USER_FILE);
            BufferedReader reader = new BufferedReader(new InputStreamReader(fin));

            String line = reader.readLine();

            while (line != null)
            {
                System.out.println(line);
                line = reader.readLine();
            }

            reader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        //Create intent for starting create account activity
        Intent startHomeIntent = new Intent(this, HomeActivity.class);

        //Start the CreateAccountActivity
        startActivity(startHomeIntent);
    }
}