package com.virginiatech.piraj.hokievent;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

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
    private static final String EVENT_TAGS_JSON = "eventTags";


    public JSONHelper(){
        //Do nothing
    }

    /**
     * Create JSON Object based on a User object
     *
     * @param user User object that the JSONObject is going to be created from
     * @return The created JSON Object
     */
    public static JSONObject createUserJSON(User user){

        JSONObject json = new JSONObject();

        try {
            json.put(FIRTS_NAME_JSON, user.getFirstName());
            json.put(MIDDLE_NAME_JSON, user.getMiddleName());
            json.put(LAST_NAME_JSON, user.getLastName());
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

    /**
     * Parse a User object from a JSONObject
     *
     * @param userJSON JSONObject that contains the information that will be stored in the User object that the method creates
     * @return User object created from the information stored in the parameter userJSON
     */
    public static User getUser(JSONObject userJSON){

        User newUser = null;

        if(userJSON != null){

            try {
                String firstName = userJSON.getString(FIRTS_NAME_JSON);
                String middleName = userJSON.getString(MIDDLE_NAME_JSON);
                String lastName = userJSON.getString(LAST_NAME_JSON);
                String email = userJSON.getString(EMAIL_JSON);
                String phone = userJSON.getString(PHONE_NUMBER_JSON);
                String interests = userJSON.getString(INTERESTS_JSON);

                newUser = new User(firstName, middleName, lastName, email, phone, interests);

            } catch (JSONException e) {
                e.printStackTrace();
                return null;
            }
        }

        return newUser;

    }

    /**
     * Put the login information to JSONObject
     *
     * @param email
     * @param password
     * @return
     */
    public static JSONObject login(String email, String password){

        JSONObject json = new JSONObject();

        try {
            json.put(EMAIL_JSON, email.trim());
            json.put(PASSWORD_JSON, password);

        } catch (JSONException exception){
            //TODO Handle exception
            exception.printStackTrace();
        }

        return json;
    }

    /**
     * Creates a JSONObject that can be used to save, join and leave events
     *
     * @param email User email
     * @param eventName Event that the user is saving, joining or leaving events
     * @return
     */
    public static JSONObject eventMembership(String email, String eventName){

        JSONObject json = new JSONObject();

        try {
            json.put(EMAIL_JSON, email);
            json.put(EVENT_NAME_JSON, eventName);

        } catch (JSONException exception){
            exception.printStackTrace();
        }

        return json;
    }

    /**
     * Put the information from a HokiEvent object to a JSONObject
     *
     * @param event
     * @return
     */
    public static JSONObject createEventJSON(HokiEvent event){

        JSONObject json = new JSONObject();

        try {
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
            json.put(EVENT_TAGS_JSON, event.getInterests());

            //Owner email
            json.put(EVENT_OWNER_EMAIL, event.getOwnerEmail());

            System.out.println("EVENT JSON: " + json);


        } catch (JSONException exception){
            //TODO Handle exception
            return null;
        }

        return json;
    }

    /**
     * Parse a list of HokiEvent objects from a JSONArray
     *
     * @param jsonArray
     * @return
     */
    public static ArrayList<HokiEvent> getEvents(JSONArray jsonArray){

        ArrayList<HokiEvent> events = new ArrayList<HokiEvent>();

        if (jsonArray != null) {

            for (int i = 0; i < jsonArray.length(); i++) {

                try {
                    HokiEvent newEvent = getEvent(jsonArray.getJSONObject(i));

                    //If an event was succesfully parsed from a JSONObject, add it to the eventlist
                    if(newEvent != null){
                        events.add(newEvent);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        return events;
    }

    /**
     * Parse a HokiEvent from a JSONObject
     *
     * @param eventJSON
     * @return
     */
    public static HokiEvent getEvent(JSONObject eventJSON){

        try {

            String eventName = eventJSON.getString(EVENT_NAME_JSON);
            String eventDesc = eventJSON.getString(EVENT_DESC_JSON);
            String eventLoc = eventJSON.getString(EVENT_LOC_JSON);
            String eventStartDate = eventJSON.getString(EVENT_STARTDATE_JSON);
            String eventStartTime = eventJSON.getString(EVENT_ENDTIME_JSON);
            String eventTags = eventJSON.getString(EVENT_TAGS_JSON);
            String ownerEmail = eventJSON.getString(EVENT_OWNER_EMAIL);

            HokiEvent newEvent = new HokiEvent(eventName, eventDesc, eventLoc, eventStartDate, eventStartTime, eventTags, ownerEmail);
            newEvent.setOwnerEmail(ownerEmail);

            if(eventJSON.getString(EVENT_ENDDATE_JSON) != null){
                newEvent.setEventEndDate(eventJSON.getString(EVENT_ENDDATE_JSON));
            }

            if(eventJSON.getString(EVENT_ENDTIME_JSON) != null){
                newEvent.setEventEndTime(eventJSON.getString(EVENT_ENDTIME_JSON));
            }

            return newEvent;

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

}
