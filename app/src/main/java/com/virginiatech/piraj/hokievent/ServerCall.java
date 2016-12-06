package com.virginiatech.piraj.hokievent;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Iterator;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import javax.net.ssl.HttpsURLConnection;

/**
 *
 */
public class ServerCall extends AsyncTask<String, String, JSONObject> {

    private static final String GET_URL = "http://71.62.121.1/index.php?";
    private static final String POST_URL = "";
    private static final int GET = 0;
    private static final int POST = 1;

    private int byGetOrPost = 0;
    private ResponseRetriever responseRetriever;

    private JSONObject jsonObject;


    //Flag 0 means get and 1 means post.(By default it is get.)

    /**
     * For GET
     *
     * @param responseRetriever
     * @param flag
     */
    public ServerCall(ResponseRetriever responseRetriever, int flag) {
        this.responseRetriever = responseRetriever;
        byGetOrPost = flag;
    }

    /**
     * For POST
     *
     * @param flag
     * @param jsonObject
     */
    public ServerCall(int flag, JSONObject jsonObject){
        this.byGetOrPost = flag;
        this.jsonObject = jsonObject;
    }

    @Override
    protected void onPreExecute(){
        super.onPreExecute();
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

                //Return JSONObject
                return new JSONObject(sb.toString());


            } catch(Exception e){
                //return new String("Exception: " + e.getMessage());
                Log.i("ServerCall:", "Exception: " + e.getMessage());
                return null;
            }

        }

        // ------------ POST ------------
        else{
            try {

                String link = (String) params[0];

                URL url = new URL(link); // here is your URL path

                JSONObject postDataParams = new JSONObject();
                postDataParams.put("name", "abc");
                postDataParams.put("email", "abc@gmail.com");
                Log.e("params",postDataParams.toString());

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(15000 /* milliseconds */);
                conn.setConnectTimeout(15000 /* milliseconds */);
                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                conn.setDoOutput(true);

                OutputStream os = conn.getOutputStream();

                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));

                writer.write(getPostDataString(jsonObject));

                writer.flush();
                writer.close();
                os.close();

                int responseCode=conn.getResponseCode();

                if (responseCode == HttpsURLConnection.HTTP_OK) {

                    BufferedReader in=new BufferedReader(
                            new InputStreamReader(
                                    conn.getInputStream()));
                    StringBuffer sb = new StringBuffer("");
                    String line="";

                    while((line = in.readLine()) != null) {

                        sb.append(line);
                        break;
                    }

                    in.close();
                    Log.i("ServerCall: ", sb.toString());
                    //return sb.toString();
                    return null;

                }
                else {
                    //return new String("false : "+responseCode);
                    Log.i("ServerCall: ", "False: " + responseCode);
                    return null;
                }
            }
            catch(Exception e){
                //return new String("Exception: " + e.getMessage());
                Log.i("ServerCall: ", "Exception: " + e.getMessage());
                return null;
            }

        }
    }


    @Override
    protected void onPostExecute(JSONObject result){

        if(byGetOrPost == GET) {
            responseRetriever.getResponse(result);
        }

        if(byGetOrPost == POST){
            //TODO What to do on post?
            Log.i("ServerCall", "POST SUCCESFUL"); //TODO Remove?
        }
    }


    /**
     * Encode the url string of JSONObject
     * https://www.studytutorial.in/android-httpurlconnection-post-and-get-request-tutorial
     *
     * @param params
     * @return
     * @throws Exception
     */
    public String getPostDataString(JSONObject params) throws Exception {

        StringBuilder result = new StringBuilder();
        boolean first = true;

        Iterator<String> itr = params.keys();

        while(itr.hasNext()){
            String key= itr.next();
            Object value = params.get(key);

            if (first) {
                first = false;
            }
            else {
                result.append("&");
            }

            result.append(URLEncoder.encode(key, "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(value.toString(), "UTF-8"));
        }

        return result.toString();
    }


}
