package com.example.pcaragones.eyetrack20;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

/**
 * Created by pcaragones on 7/7/17.
 */

class HttpHandler  extends AsyncTask<String,Void,String> {
    private static final String BASE_URL = "http://192.168.1.10:8000";

    @Override
    protected String doInBackground(String... params) {
        URL url;
        HttpURLConnection urlConnection = null;
        String response = null;
        try {
            String address = BASE_URL + params[0];
            Log.d("sendPost", address);

            url = new URL(address);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod(params[1]);
            urlConnection.setDoInput(true);
            if(params[1] == "POST") urlConnection.setDoOutput(true);

            Uri.Builder builder = new Uri.Builder();
            if(params[1] == "POST"){
                for(int i = 2; i < params.length; i+=2){
                    builder.appendQueryParameter(params[i], params[i+1]);
                }
                String query = builder.build().getEncodedQuery();

                OutputStream os = urlConnection.getOutputStream();

                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));

                writer.write(query);
                writer.flush();
                writer.close();
                os.close();
            }
            urlConnection.connect();

            // read the response
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            response = convertStreamToString(in);
            Log.d("response", response);
            return (response);
        } catch (MalformedURLException e) {
            Log.d("Error","MalformedURLException: " + e.getMessage());
        } catch (ProtocolException e) {
            Log.d("Error","ProtocolException: " + e.getMessage());
        } catch (IOException e) {
            Log.d("Error","IOException: " + e.getMessage());
        } catch (Exception e) {
            Log.e("Error","Exception: " + e);
        }  finally {
            if(urlConnection !=null)  urlConnection.disconnect();
        }
        return ("incorrect");
    }

    @Override
    protected void onPostExecute(String message) {
        //process message
    }

    private String convertStreamToString(InputStream in) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        StringBuilder sb = new StringBuilder();

        String line;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line).append('\n');
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }
}