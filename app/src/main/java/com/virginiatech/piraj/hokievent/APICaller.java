package com.virginiatech.piraj.hokievent;

import android.app.Activity;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Iterator;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import javax.net.ssl.HttpsURLConnection;

/**
 *
 */
public class APICaller  {

    final private String address = "http://71.62.121.1/";
    private String response;

    public JSONObject APIgetUser(String email) throws IOException, JSONException {
        new getUser().execute(email);
        //System.out.println(response);
        return new JSONObject(response);
    }

    public void APIpostUser(JSONObject jObject) throws IOException {
        new postUser().execute(jObject);

    }

    private class getUser extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... email) {
            InputStream input = null;
            try {
                //System.out.println(address+ "get/user.php?email=" + email[0]);
                URL url = new URL(address + "get/user.php?email=" + email[0]);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

                urlConnection.setConnectTimeout(15000);
                urlConnection.setRequestMethod("GET");
                urlConnection.setChunkedStreamingMode(0);
                urlConnection.connect();

                input = urlConnection.getInputStream();
                final int bufferSize = 1024;
                final char[] buffer = new char[bufferSize];
                final StringBuilder out = new StringBuilder();
                Reader in = new InputStreamReader(input, "UTF-8");
                for (; ; ) {
                    int rsz = in.read(buffer, 0, buffer.length);
                    if (rsz < 0)
                        break;
                    out.append(buffer, 0, rsz);
                }
                urlConnection.disconnect();
                //System.out.println(out.toString());
                return out.toString();
                //System.out.println(response);
            } catch (Exception e) {
                System.out.println("ERROR: " + e);
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            //System.out.println(s);
            if (s != null)
                Log.v("sina", s); // !! DataFromWebService is null because AsyncTask hasn't done its task
            else
                Log.v("sing", "null");
            }
        }

    private class postUser extends AsyncTask<JSONObject, Void, Void> {
        @Override
        protected Void doInBackground(JSONObject...j) {
            OutputStream output = null;
            try {
                URL url = new URL(address + "post/user.php");
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setDoOutput(true);
                urlConnection.setConnectTimeout(15000);
                urlConnection.setRequestMethod("POST");
                urlConnection.setRequestProperty("Content-Type", "application/json");
                urlConnection.connect();

                output = urlConnection.getOutputStream();
                output.write(j[0].toString().getBytes());
                output.flush();

                System.out.println(urlConnection.getResponseCode());
                BufferedReader br = new BufferedReader(new InputStreamReader(
                        (urlConnection.getInputStream())));

                String output1;
                System.out.println("Output from Server .... \n");
                while ((output1 = br.readLine()) != null) {
                    System.out.println(output);
                }


                urlConnection.disconnect();
            } catch (Exception e) {
                System.out.println("ERROR: " + e);
            }
            return null;
        }
        }
    }