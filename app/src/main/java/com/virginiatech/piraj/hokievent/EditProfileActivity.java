package com.virginiatech.piraj.hokievent;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import org.json.JSONObject;

/**
 *
 * @version 2016.12.05
 */
public class EditProfileActivity extends AppCompatActivity {

    private EditText firstNameField;
    private EditText middleNameField;
    private EditText lastNameField;
    private EditText phonenumberField;
    private TextView interestsList;
    private TextView messageView;

    private Button editInterestsButton;
    private Button saveChangesButton;

    private String interests;

    private User user;

    // --- Strings values ---
    private static String FIRST_NAME = "First name";
    private static String MIDDLE_NAME = "Middle name";
    private static String LAST_NAME = "Last name";
    private static String PHONE = "Phone number";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        //Find componenets by id
        findById();

        //TODO Get user data from either previous activity or from the server?

        readData();
        showData();


    }


    /**
     * Find components by id
     */
    private void findById(){

        // --- Textfields ---
        firstNameField = (EditText) findViewById(R.id.firstNameField);
        middleNameField = (EditText) findViewById(R.id.middleNameField);
        lastNameField = (EditText) findViewById(R.id.lastNameField);
        phonenumberField = (EditText) findViewById(R.id.phonenumberField);

        interestsList = (TextView) findViewById(R.id.phonenumberField);
        messageView = (TextView) findViewById(R.id.editProfileButton);

        // --- Buttons ---
        editInterestsButton = (Button) findViewById(R.id.selectInterestsButton);
        saveChangesButton = (Button) findViewById(R.id.saveChangesButton);

        editInterestsButton.setOnClickListener(editInterestsListener);
        saveChangesButton.setOnClickListener(saveChangesListener);
    }

    private void readData()
    {

        try {


            FileInputStream fin = openFileInput(User.USER_FILE);
            BufferedReader reader = new BufferedReader(new InputStreamReader(fin));

            String id = reader.readLine();
            String first = reader.readLine();
            String middle = reader.readLine();
            String last = reader.readLine();
            String email = reader.readLine();
            String phone = reader.readLine();
            interests = reader.readLine();
            String password = reader.readLine();

            reader.close();

            user = new User(first, middle, last, email, phone, interests, password);

            user.setUserID(id);

        } catch (IOException e)
        {
            e.printStackTrace();
        }

    }

    /**
     * Show the "old" user information in the text field
     */
    private void showData(){

        firstNameField.setText(user.getFirstName());
        middleNameField.setText(user.getMiddleName());
        lastNameField.setText(user.getLastName());
        phonenumberField.setText(user.getPhoneNumber());
        interestsList.setText(user.getInterests());


    }

    private View.OnClickListener editInterestsListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            // Open SelectInterestsActivity to allow user to select their interests
            Intent interestActivityIntent = new Intent(view.getContext(), InterestsActivity.class);
            interestActivityIntent.putExtra(InterestsActivity.INTEREST, interests);

            System.out.println("EditProfile sends :" + interestActivityIntent.getStringExtra(InterestsActivity.INTEREST));
            startActivityForResult(interestActivityIntent, 0);

        }
    };

    private View.OnClickListener saveChangesListener = new View.OnClickListener(){
        @Override
        public void onClick(View view){



            //TODO Update new user data on server

            try {

                File dir = getFilesDir();
                File file = new File(dir, User.USER_FILE);
                file.delete();

                FileOutputStream fos = openFileOutput(User.USER_FILE, Context.MODE_PRIVATE);

                OutputStreamWriter writer = new OutputStreamWriter(fos);

                writer.write(user.getUserID() + "\n");
                writer.write(user.getFirstName() + "\n");
                writer.write(user.getMiddleName() + "\n");
                writer.write(user.getLastName() + "\n");
                writer.write(user.getUserEmail() + "\n");
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



            } catch (IOException e)
            {
                e.printStackTrace();
            }


        }
    };


    @Override
    public void onBackPressed() {

        //TODO Only ask for confirmation is user changed values in any fields
        confirmExit();
    }

    /**
     * Show confirmation dialog when user is about to leave the
     */
    public void confirmExit(){
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Closing Activity")
                .setMessage("Are you sure you want to close this activity?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }

                })
                .setNegativeButton("No", null)
                .show();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {

        outState.putString(FIRST_NAME, firstNameField.getText().toString());
        outState.putString(MIDDLE_NAME, middleNameField.getText().toString());
        outState.putString(LAST_NAME, lastNameField.getText().toString());
        outState.putString(PHONE, phonenumberField.getText().toString());
        outState.putString(InterestsActivity.INTEREST, interests);

        readData();
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {

        firstNameField.setText(savedInstanceState.getString(FIRST_NAME));
        middleNameField.setText(savedInstanceState.getString(MIDDLE_NAME));
        lastNameField.setText(savedInstanceState.getString(LAST_NAME));
        phonenumberField.setText(savedInstanceState.getString(PHONE));
        interests = (savedInstanceState.getString(InterestsActivity.INTEREST));
        readData();

        super.onRestoreInstanceState(savedInstanceState);

    }

}
