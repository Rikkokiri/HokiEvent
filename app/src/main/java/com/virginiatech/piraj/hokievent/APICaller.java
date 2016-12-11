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

    public void APIlogin(JSONObject j) throws IOException, JSONException {
        new login().execute(j);
    }

     public void APIgetUser(String email, Context context) throws IOException, JSONException{
        mCallback = (TaskCompleted) context;
        new getUser().execute(email);
    }

    public void APIgetEventAll() throws IOException, JSONException {
        new getEventAll().execute();
    }

    public void APIpostUser(JSONObject jObject) throws IOException {
        new postUser().execute(jObject);
    }

    public void APIpostEvent(JSONObject jObject) throws IOException {
        new postEvent().execute(jObject);
    }

    public void APIpostJoinEvent(JSONObject jObject) throws IOException {
        new postJoinEvent().execute(jObject);
    }

    public void APIputUser(JSONObject jObject) throws IOException {
        new putUser().execute(jObject);
    }

    private class login extends AsyncTask<JSONObject, Void, String> {
        @Override
        protected String doInBackground(JSONObject...j) {
            OutputStream output = null;
            InputStream input = null;
            try {
                URL url = new URL(address + "index.php");
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
    }

    private class getUser extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... email) {
            InputStream input = null;
            try {
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
            //super.onPostExecute(s);
            response = s;
            super.onPostExecute(s);
            }
        }

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

    private class postEvent extends AsyncTask<JSONObject, Void, Void> {
        @Override
        protected Void doInBackground(JSONObject...j) {
            OutputStream output = null;
            try {
                URL url = new URL(address + "post/event.php");
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
    private class postJoinEvent extends AsyncTask<JSONObject, Void, Void> {
        @Override
        protected Void doInBackground(JSONObject...j) {
            OutputStream output = null;
            try {
                URL url = new URL(address + "post/eventjoin.php");
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

        private class putUser extends AsyncTask<JSONObject, Void, Void> {
            @Override
            protected Void doInBackground(JSONObject...j) {
                OutputStream output = null;
                try {
                    URL url = new URL(address + "put/user.php");
                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                    urlConnection.setDoOutput(true);
                    urlConnection.setConnectTimeout(15000);
                    urlConnection.setRequestMethod("PUT");
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
