package com.virginiatech.piraj.hokievent;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Pilvi Rajala (piraj) on 05/12/16.
 */

public class Communicator {

    private static final int GET = 0;
    private static final int POST = 1;

    //For posting user
    private static final String POST_EMAIL = "userEmail";
    private static final String FIRTS_NAME = "firstName";
    private static final String MIDDLE_NAME = "middleName";
    private static final String LAST_NAME = "lastName";
    private static final String PHONENUMBER = "phoneNumber";
    private static final String INTERESTS = "interests";


    private static final String URL = "http://71.62.121.1/index.php?"; //FIX THIS

    /**
     * Get user based on the email address
     * @param email
     */
    public void getUser(ResponseRetriever retriever, String email){

        //String url = URL + "email=" + email;
        String url = "http://71.62.121.1/get/user.php?email="+email;
        new ServerCall(retriever, GET).execute(url);

    }

    /**
     * Get event
     */
    public void getEvent(ResponseRetriever retriever){

        //String url =
        new ServerCall(retriever, GET).execute();
    }

    /**
     * TODO
     *
     * format of JSON needed for post:
     * {"userEmail":"bob@vt.edu","firstName":"Bob","lastName":"Jones","phoneNumber":"888-888-8888","interests":"Gaming, Food, Military"}
     */
    public void addUser(ResponseRetriever retriever, User user){

        String url = "71.62.121.1/post/user.php";

        JSONObject jsonObject = null;

        try {
            //Create JSONObject here
            jsonObject = new JSONObject();

            jsonObject.put(POST_EMAIL, user.getUserEmail());
            jsonObject.put(FIRTS_NAME, user.getFirstName());
            //jsonObject.put(MIDDLE_NAME, user.getMiddleName());
            jsonObject.put(LAST_NAME, user.getFirstName());
            jsonObject.put(PHONENUMBER, user.getPhoneNumber());
            jsonObject.put(INTERESTS, user.getInterests());

        } catch(JSONException exception){
            Log.i("ServerCall: " , exception.getMessage());
        }

        if(jsonObject != null) {
            new ServerCall(retriever, POST, jsonObject).execute(url);
        }
        else {
            //TODO Error!
            Log.i("Communicator", "POST ERROR");
        }

    }


}
