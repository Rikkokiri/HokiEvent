package com.virginiatech.piraj.hokievent;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class InterestsActivity extends AppCompatActivity {

    private Button doneButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interests);


        // DONE -button
        doneButton = (Button) findViewById(R.id.doneSelecting);
        doneButton.setOnClickListener(doneSelectingListener);

    }


    private View.OnClickListener doneSelectingListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            //TODO Save which interests where selected

            //TODO Return to Create Account view
            Intent returnToCreateAccount = new Intent(view.getContext(), CreateAccountActivity.class);
            startActivity(returnToCreateAccount);

        }
    };
}
