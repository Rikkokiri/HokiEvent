package com.virginiatech.piraj.hokievent;

import org.json.JSONObject;

/**
 * Created by Pilvi Rajala (piraj) on 05/12/16.
 */

public class Communicator {

    private static final int GET = 0;
    private static final int POST = 1;

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
     */
    public void addUser(){

    }


}
