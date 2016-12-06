package com.virginiatech.piraj.hokievent;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

/**
 *
 */

public class ServerCall extends AsyncTask<String, String, JSONObject> {

    private int byGetOrPost = 0;


    //flag 0 means get and 1 means post.(By default it is get.)
    public ServerCall(int flag) {
        byGetOrPost = flag;
    }

    @Override
    protected void onPreExecute(){
    }


    @Override
    protected JSONObject doInBackground(String... params) {

        // ------------ GET ------------
        if(byGetOrPost == 0){

            try{
                String link = (String) params[0];

                HttpClient client = new DefaultHttpClient();
                HttpGet request = new HttpGet();

                //Create URI from the link
                request.setURI(new URI(link));

                HttpResponse response = client.execute(request);
                BufferedReader in = new BufferedReader(new
                        InputStreamReader(response.getEntity().getContent()));

                StringBuffer sb = new StringBuffer("");
                String line="";

                while ((line = in.readLine()) != null) {
                    sb.append(line);
                    break;
                }

                in.close();
                //return sb.toString();

                //TODO
                return null;

            } catch(Exception e){
                //return new String("Exception: " + e.getMessage());
                Log.i("ServerCall:", "Exception: " + e.getMessage());
                return null;
            }

        }

        // ------------ POST ------------
        else{
            try{
                String username = (String) params[0];
                //String password = (String)arg0[1];

                String link="http://myphpmysqlweb.hostei.com/loginpost.php";

                //TODO
                String data  = URLEncoder.encode("username", "UTF-8") + "=" +
                        URLEncoder.encode(username, "UTF-8");

                URL url = new URL(link);
                URLConnection conn = url.openConnection();

                conn.setDoOutput(true);
                OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());

                wr.write( data );
                wr.flush();

                BufferedReader reader = new BufferedReader(new
                        InputStreamReader(conn.getInputStream()));

                StringBuilder sb = new StringBuilder();
                String line = null;

                // Read Server Response
                while((line = reader.readLine()) != null) {
                    sb.append(line);
                    break;
                }

                //return sb.toString();
                return null;

            } catch(Exception e){
                //return new String("Exception: " + e.getMessage());
                Log.i("ServerCall: ", "Exception " + e.getMessage());
                return null;
            }
        }
    }

    @Override
    protected void onPostExecute(JSONObject result){

        //TODO Do we need this for anything?

    }
}
