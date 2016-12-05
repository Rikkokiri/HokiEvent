package com.virginiatech.piraj.hokievent;

import android.content.Intent;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        //Find componenets by id
        findById();



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
}
