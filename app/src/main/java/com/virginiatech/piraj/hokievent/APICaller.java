package com.virginiatech.piraj.hokievent;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.android.gms.tasks.Task;

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
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.concurrent.ExecutionException;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.net.ssl.HttpsURLConnection;

/**
 *
 */
public class APICaller  {

    final private String address = "http://71.62.121.1/";
    public String response = "";
    public boolean done = false;
    private Context mContext;
    private TaskCompleted mCallback;

    public APICaller(Context c) {
        mContext = c;
        mCallback = (TaskCompleted) mContext;
    }

    public void APIlogin(JSONObject jObject) {
        new apiConnection("index.php", "POST", true, true).execute(jObject);
    }

    public void APIgetEventAll() {
        new apiConnection("get/eventall.php", "POST", true, false).execute();
    }

    public void APIpostUser(JSONObject jObject) {
        new apiConnection("post/user.php", "POST", true, true).execute(jObject);
    }

    public void APIpostEvent(JSONObject jObject) {
        new apiConnection("post/event.php", "POST", false, true).execute(jObject);
    }

    public void APIpostJoinEvent(JSONObject jObject) {
        new apiConnection("post/eventjoin.php", "POST", false, true).execute(jObject);
    }

    public void APIpostLeaveEvent(JSONObject jObject) {
        new apiConnection("delete/eventleave.php", "DELETE", false, true).execute(jObject);
    }

    public void APIpostSaveEvent(JSONObject jObject) {
        new apiConnection("post/eventsave.php", "POST", false, true).execute(jObject);
    }

    public void APIpostUnsaveEvent(JSONObject jObject) {
        new apiConnection("post/eventunsave.php", "POST", false, true).execute(jObject);
    }
    public void APIputUser(JSONObject jObject) {
        new apiConnection("put/user.php", "PUT", false, true).execute(jObject);
    }

    public void APIputEvent(JSONObject jObject) {
        new apiConnection("put/event.php", "PUT", false, true).execute(jObject);
    }

    private class apiConnection extends AsyncTask<JSONObject, Void, String> {

        String s;
        String t;
        boolean r;
        boolean send;

        private apiConnection(String url, String type, boolean returnedOutput, boolean sendInput) {
            s = url;
            t = type;
            r = returnedOutput;
            send = sendInput;
        }

        @Override
        protected String doInBackground(JSONObject...j) {
            OutputStream output = null;
            InputStream input = null;
            try {
                URL url = new URL(address + s);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setDoOutput(send);
                urlConnection.setConnectTimeout(15000);
                urlConnection.setRequestMethod(t);
                if (send) {
                    urlConnection.setRequestProperty("Content-Type", "application/json");
                } else {
                    urlConnection.setChunkedStreamingMode(0);
                }
                urlConnection.connect();

                output = urlConnection.getOutputStream();
                if (send) {
                    output.write(j[0].toString().getBytes());
                    output.flush();
                }

                System.out.println("RESPONSE CODE: " + urlConnection.getResponseCode());

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
                return out.toString();
            } catch (Exception e) {
                System.out.println("ERROR: " + e);
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            //System.out.println(s);
            if (r) { mCallback.onTaskComplete(s); }
        }
    }

/*
    private class getEventAll extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... avoid) {
            InputStream input = null;
            try {
                URL url = new URL(address + "get/eventall.php");
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
                return out.toString();
            } catch (Exception e) {
                System.out.println("ERROR: " + e);
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            mCallback.onTaskComplete(s);
        }
    }*/
}
