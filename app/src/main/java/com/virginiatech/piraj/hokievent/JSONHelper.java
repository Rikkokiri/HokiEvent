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

    // --- JSON Keys for event ---
    private static final String EVENT_OWNER_EMAIL = "eventOwner";
    private static final String EVENT_NAME_JSON = "eventName";
    private static final String EVENT_STARTDATE_JSON = "eventStartDate";
    private static final String EVENT_ENDDATE_JSON = "eventEndDate";
    private static final String EVENT_STARTTIME_JSON = "eventStartTime";
    private static final String EVENT_ENDTIME_JSON = "eventEndTime";
    private static final String EVENT_DESC_JSON = "eventDescription";
    private static final String EVENT_LOC_JSON = "eventLocation";


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
            json.put(EVENT_OWNER_EMAIL, "");
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

    public static JSONObject createEventJSON(HokiEvent event){

        JSONObject json = new JSONObject();

        try {
            json.put()
            json.put(EVENT_NAME_JSON, event.getEventName());

            json.put(EVENT_STARTDATE_JSON, event.getEventStartDate());
            if(event.getEventEndDate() != null){
                json.put(EVENT_ENDDATE_JSON, event.getEventEndDate());
            }

            json.put(EVENT_STARTTIME_JSON, event.getEventStartTime());
            if(event.getEventEndTime() != null){
                json.put(EVENT_ENDTIME_JSON, event.getEventEndTime());
            }

            json.put(EVENT_LOC_JSON, event.getEventLoc());
            json.put(EVENT_DESC_JSON, event.getEventDesc());


        } catch (JSONException exception){
            //TODO Handle exception
            return null;
        }

        return json;
    }

}
