package com.virginiatech.piraj.hokievent;

import android.util.Log;
import android.util.StringBuilderPrinter;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Pilvi Rajala (piraj) on 05/12/16.
 */

public class ParsingHelper {

    //For parsing user data
    private static String USER_ID = "userID";
    private static String FIRST_NAME = "firstName";
    private static String LAST_NAME = "lastName";
    private static String EMAIL = "email";
    private static String PHONENUMBER = "phoneNumber";
    private static String INTERESTS = "interests";

    //For parsing event data


    public User parseUser(JSONObject jsonObject){

        User user = null;

        try {
            String firstName = jsonObject.getString(FIRST_NAME);

            //TODO middleName
            String middleName = "Middle name";

            String lastName = jsonObject.getString(LAST_NAME);

            String email = jsonObject.getString(EMAIL);
            String phoneNumber = jsonObject.getString(PHONENUMBER);
            String[] interests = jsonObject.getString(INTERESTS).split(",");

            user = new User(firstName, middleName, lastName, email, phoneNumber)

        } catch (JSONException exception){
            Log.i("ParsingHelper: ", exception.getMessage());
            return null;
        }

        return user;
    }

    public HokiEvent parseEvent(JSONObject jsonObject){

        //String eventName = jsonObject.getString("firstName");
        //String eventDesc
        //String eventLoc
        //String eventStartTime =
        //String enventStartDate =

        //TODO eventEndTime
        //TODO eventEndDate

        //String tags =

        //return new HokieEvent

        return null;
    }

}

    /*

    JSONObject obj = new JSONObject(" .... ");
    String pageName = obj.getJSONObject("pageInfo").getString("pageName");

    JSONArray arr = obj.getJSONArray("posts");
for (int i = 0; i < arr.length(); i++)
        {
        String post_id = arr.getJSONObject(i).getString("post_id");
        ......
        }
            */