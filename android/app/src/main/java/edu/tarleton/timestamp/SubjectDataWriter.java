package edu.tarleton.timestamp;

import android.app.Application;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

import javax.net.ssl.HttpsURLConnection;

public class SubjectDataWriter extends AsyncTask<Void, Void, Boolean> {

    String urlString = "http://www.yoursite.com/";

    private Context context;
    private SubjectData data;
    private final String TAG = "post json example";
    private final DatabaseWriter dbWriter;

    public SubjectDataWriter(Context context, SubjectData data) {
        this.context = context;
        this.data = data;
        dbWriter = new DatabaseWriter(context);
    }

    @Override
    protected void onPreExecute() {
        Log.e(TAG, "1 - RequestVoteTask is about to start...");
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        boolean status = false;

        String response = "";
        Log.e(TAG, "2 - pre Request to response...");

        try {
            response = performPostCall(urlString, this.data);
            Log.e(TAG, "3 - give Response...");
            Log.e(TAG, "4 " + response.toString());
        } catch (Exception e) {
            // displayLoding(false);

            Log.e(TAG, "Error ...");
        }
        Log.e(TAG, "5 - after Response...");

        if (!response.equalsIgnoreCase("")) {
            try {
                Log.e(TAG, "6 - response !empty...");
                //
                JSONObject jRoot = new JSONObject(response);
                JSONObject d = jRoot.getJSONObject("d");

                int ResultType = d.getInt("ResultType");
                Log.e("ResultType", ResultType + "");

                if (ResultType == 1) {
                    status = true;
                }

            } catch (JSONException e) {
                // displayLoding(false);
                // e.printStackTrace();
                Log.e(TAG, "Error " + e.getMessage());
            }
        } else {
            Log.e(TAG, "6 - response is empty...");
            status = false;
        }

        return status;
    }

    @Override
    protected void onPostExecute(Boolean result) {}

    public String performPostCall(String requestURL, SubjectData data)
    {
        URL url;
        String response = "";
        try {
            url = new URL(requestURL);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(3000);
            conn.setConnectTimeout(3000);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);

            conn.setRequestProperty("Content-Type", "application/json");

            Log.e(TAG, "11 - url : " + requestURL);

            JSONObject root = new JSONObject();
            root.put("name", data.name);
            root.put("tag_id", data.tagId);
            root.put("description", data.description);
            root.put("end_time", data.endTime.toString());
            root.put("start_time", data.startTime.toString());

            Log.e(TAG, "12 - root : " + root.toString());

            String str = root.toString();
            byte[] outputBytes = str.getBytes("UTF-8");
            OutputStream os = conn.getOutputStream();
            os.write(outputBytes);

            int responseCode = conn.getResponseCode();

            Log.e(TAG, "13 - responseCode : " + responseCode);

            if (responseCode == HttpsURLConnection.HTTP_OK) {
                Log.e(TAG, "14 - HTTP_OK");

                String line;
                BufferedReader br = new BufferedReader(new InputStreamReader(
                        conn.getInputStream()));
                while ((line = br.readLine()) != null) {
                    response += line;
                }
            } else {
                // write to sqlite database
                HashMap<String, String> map = new HashMap<>();
                map.put("id", ""+data.tagId);

                map.put("start", ""+data.startTime);
                dbWriter.write(map, DatabaseWriter.MODE.INSERT);
                Log.e(TAG, "14 - False - HTTP_OK");
                response = "";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return response;
    }
}