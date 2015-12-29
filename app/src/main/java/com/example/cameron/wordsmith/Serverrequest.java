package com.example.cameron.wordsmith;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.logging.Logger;
import java.util.logging.Level;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.Buffer;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

import android.content.ContentValues;
import android.os.AsyncTask;
import android.util.Log;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by cameron on 12/26/15.
 */

public class Serverrequest {


    public JSONObject getJSON(String u, String username, String password)
    throws IOException, JSONException {


        JSONObject creds = new JSONObject();
        creds.put("username", username);
        creds.put("password", password);

        URL url = new URL(u);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setReadTimeout(10000);
        conn.setConnectTimeout(15000);
        conn.setRequestMethod("POST");
        conn.setDoInput(true);
        conn.setDoOutput(true);
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestProperty("Accept", "application/json");

        OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream());
        writer.write(creds.toString());
        writer.flush();

        StringBuilder builder = new StringBuilder();
        int httpRes = conn.getResponseCode();
        if (httpRes == HttpsURLConnection.HTTP_OK) {
            BufferedReader br = new BufferedReader(new InputStreamReader(
                    conn.getInputStream(), "UTF-8"
            ));
            String line = null;
            while ((line = br.readLine()) != null) {
                builder.append(line + "\n");
            }
            br.close();

        } else {
            System.out.println(conn.getResponseMessage());
        }
        System.out.println(builder);
        return new JSONObject(builder.toString());
    }


    }
