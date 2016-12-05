package com.virginiatech.piraj.hokievent;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 *
 * @version 2016.12.05
 */
public class EditProfileActivity extends AppCompatActivity {

    private EditText firstNameField;
    private EditText middleNameField;
    private EditText lastNameField;
    private EditText phonenumberField;

    private Button editInterestsButton;
    private Button saveChangesButton;

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
        Intent userIntent = getIntent();
        Bundle bundle = userIntent.getExtras();
        User user = (User) bundle.get("User");

        if(user != null){
            showData(user);
        }

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

        // --- Buttons ---
        editInterestsButton = (Button) findViewById(R.id.editInterestsButton);
        saveChangesButton = (Button) findViewById(R.id.saveChangesButton);

        editInterestsButton.setOnClickListener(editInterestsListener);
    }

    /**
     * Show the "old" user information in the text fields
     * @param user
     */
    private void showData(User user){

        firstNameField.setText(user.getFirstName());
        middleNameField.setText(user.getMiddleName());
        lastNameField.setText(user.getLastName());
        phonenumberField.setText(user.getPhoneNumber());

    }

    private View.OnClickListener editInterestsListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            //TODO Save textfield values

            //TODO Pass interests activity the user's interestsss

            //Open interests view for editing interests
            Intent startInterestsActivity = new Intent(view.getContext(), InterestsActivity.class);
            startActivity(startInterestsActivity);

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

        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {

        firstNameField.setText(savedInstanceState.getString(FIRST_NAME));
        middleNameField.setText(savedInstanceState.getString(MIDDLE_NAME));
        lastNameField.setText(savedInstanceState.getString(LAST_NAME));
        phonenumberField.setText(savedInstanceState.getString(PHONE));

        super.onRestoreInstanceState(savedInstanceState);

    }

    

}
