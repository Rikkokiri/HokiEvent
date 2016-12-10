package com.virginiatech.piraj.hokievent;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ToggleButton;
import com.roughike.bottombar.BottomBar;

public class InterestsActivity extends AppCompatActivity {

    private BottomBar bottomBar;

    private Button doneButton;


    // --- Strings ---
    private String oldInterests;

    // --- ToggleButtons ---
    private ToggleButton sports;
    private ToggleButton academics;
    private ToggleButton food;
    private ToggleButton culture;
    private ToggleButton art;
    private ToggleButton nature;
    private ToggleButton filmTV;
    private ToggleButton music;
    private ToggleButton literature;
    private ToggleButton military;
    private ToggleButton family;
    private ToggleButton gaming;
    private ToggleButton travel;
    private ToggleButton animals;

    // --- Buttons ---
    private Button done;

    // --- CONSTANTS ---
    static final String INTEREST = "interest";


    public InterestsActivity() {
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interests);

        // DONE -button
        doneButton = (Button) findViewById(R.id.doneSelecting);
        doneButton.setOnClickListener(doneListener);
        
        Intent intent = getIntent();
        oldInterests = intent.getStringExtra(INTEREST);
        
        System.out.println("InterestsActivity OnCreate, oldInterests: " + oldInterests);
        
        sports = (ToggleButton) findViewById(R.id.sports);
        academics = (ToggleButton) findViewById(R.id.academics);
        food = (ToggleButton) findViewById(R.id.food);
        culture = (ToggleButton) findViewById(R.id.culture);
        art = (ToggleButton) findViewById(R.id.art);
        nature = (ToggleButton) findViewById(R.id.nature);
        filmTV = (ToggleButton) findViewById(R.id.filmTV);
        music = (ToggleButton) findViewById(R.id.music);
        literature = (ToggleButton) findViewById(R.id.literature);
        military = (ToggleButton) findViewById(R.id.military);
        family = (ToggleButton) findViewById(R.id.family);
        gaming = (ToggleButton) findViewById(R.id.gaming);
        travel = (ToggleButton) findViewById(R.id.travel);
        animals = (ToggleButton) findViewById(R.id.animals);
        
        setButtons(oldInterests);

    }

    /**
     * Listener for Done button
     */
    private View.OnClickListener doneListener = new View.OnClickListener(){
        @Override
        public void onClick(View view){

            Intent output = new Intent();
            output.putExtra(INTEREST, buildInterests());
            setResult(RESULT_OK, output);

            System.out.println("InterestsActivity returns: " + output.getStringExtra(INTEREST));

            finish();

        }
    };

    private void setButtons(String interests)
    {
        if (interests == null)
        {
            return;
        }

        String[] temp = interests.split(", ");

        for (int i = 0; i < temp.length; i++)
        {
            switch(temp[i].toLowerCase())
            {

                case "academics":
                    academics.setChecked(true);
                    break;
                case "animals":
                    animals.setChecked(true);
                    break;
                case "art":
                    art.setChecked(true);
                    break;
                case "culture":
                    culture.setChecked(true);
                    break;
                case "family":
                    family.setChecked(true);
                    break;
                case "film/tv":
                    filmTV.setChecked(true);
                    break;
                case "food":
                    food.setChecked(true);
                    break;
                case "gaming":
                    gaming.setChecked(true);
                    break;
                case "literature":
                    literature.setChecked(true);
                    break;
                case "military":
                    military.setChecked(true);
                    break;
                case "music":
                    music.setChecked(true);
                    break;
                case "nature":
                    nature.setChecked(true);
                    break;
                case "sports":
                    sports.setChecked(true);
                    break;
                case "travel":
                    travel.setChecked(true);
                    break;
                default:
                    break;
            }
        }
    }

    private String buildInterests()
    {
        String string = "";
        if (sports.isChecked())
        {
            string += getResources().getString(R.string.sports) + ", ";
        }
        if (academics.isChecked())
        {
            string += getResources().getString(R.string.academics)+ ", ";
        }
        if (food.isChecked())
        {
            string += getResources().getString(R.string.food) + ", ";
        }
        if (culture.isChecked())
        {
            string += getResources().getString(R.string.culture)+ ", ";
        }
        if (art.isChecked())
        {
            string += getResources().getString(R.string.art)+ ", ";
        }
        if (nature.isChecked())
        {
            string += getResources().getString(R.string.nature) + ", ";
        }
        if (filmTV.isChecked())
        {
            string += getResources().getString(R.string.filmTV )+ ", ";
        }
        if (music.isChecked())
        {
            string += getResources().getString(R.string.music)+ ", ";
        }
        if (literature.isChecked())
        {
            string += getResources().getString(R.string.literature)+ ", ";
        }
        if (military.isChecked())
        {
            string += getResources().getString(R.string.military)+ ", ";
        }
        if (family.isChecked())
        {
            string += getResources().getString(R.string.family)+ ", ";
        }
        if (gaming.isChecked())
        {
            string += getResources().getString(R.string.gaming)+ ", ";
        }
        if (travel.isChecked())
        {
            string += getResources().getString(R.string.travel)+ ", ";
        }
        if (animals.isChecked())
        {
            string += getResources().getString(R.string.animals) + ", ";
        }

        if (string.endsWith(", "))
        {
            string = string.substring(0, string.length() - 2);
        }

        System.out.println("InterestsActivity builds: " + string);

        return string;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString(INTEREST, buildInterests());
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        setButtons(savedInstanceState.getString(INTEREST));
        super.onRestoreInstanceState(savedInstanceState);

    }
}
