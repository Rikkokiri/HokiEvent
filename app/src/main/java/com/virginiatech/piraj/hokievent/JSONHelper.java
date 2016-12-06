package com.virginiatech.piraj.hokievent;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Pilvi Rajala (piraj) on 06/12/16.
 */

public class JSONHelper {

    //JSON keys for user
    private static final String FIRTS_NAME_JSON = "firstName";
    private static final String MIDDLE_NAME_JSON = "middleName";
    private static final String LAST_NAME_JSON = "lastName";
    private static final String EMAIL_JSON = "userEmail";
    private static final String PHONE_NUMBER_JSON = "phoneNumber";
    private static final String INTERESTS_JSON = "interests";
    private static final String PASSWORD_JSON = "password";



    public JSONHelper(){
        //Do nothing
    }

    /**
     * Create JSON Object based on a User object
     *
     * @param user User object that the JSON Object is going to be created from
     * @return The created JSON Object
     */
    public static JSONObject createUserJSON(User user){

        JSONObject json = new JSONObject();

        try {
            json.put(FIRTS_NAME_JSON, user.getFirstName());
            json.put(MIDDLE_NAME_JSON, user.getMiddleName());
            json.put(LAST_NAME_JSON, user.getMiddleName());
            json.put(EMAIL_JSON, user.getUserEmail());
            json.put(PHONE_NUMBER_JSON, user.getPhoneNumber());
            json.put(INTERESTS_JSON, user.getInterests());
            json.put(PASSWORD_JSON, user.getPassword());

        } catch (JSONException exception){
            //TODO Handle exception
            return null;
        }

        return json;
    }

}
