package br.com.imcom.locais;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.NameValuePair;
import cz.msebera.android.httpclient.message.BasicNameValuePair;

/**
 * Created by BETO on 05/11/2015.
 */
public class DetailsLocalActivity extends Activity {

    TextView txtName;
    TextView txtAddr;
    TextView txtDesc;
    TextView txtCreatedAt;
    Button btnMap;

    String pid;

    // Progress Dialog
    private ProgressDialog pDialog;

    // JSON parser class
    JSONParser jsonParser = new JSONParser();

    // single product url
    private static final String url_local_details = "http://joly.imontanha.com//android_connect/get_local_details.php";

    // JSON Node names
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_LOCAL = "local";
    private static final String TAG_PID = "pid";
    private static final String TAG_NAME = "name";
    private static final String TAG_ADDR = "addr";
    private static final String TAG_DESCRIPTION = "description";

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.detail_local);

        // save button
        btnMap = (Button) findViewById(R.id.btnMap);

        // getting product details from intent
        Intent i = getIntent();

        // getting product id (pid) from intent
        pid = i.getStringExtra(TAG_PID);

        // Getting complete product details in background thread
        new GetLocalDetails().execute();

        // save button click event
        btnMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Abrir Mapa com os dados de coordenadas
            }
        });
    }

    /**
     * Background Async Task to Get complete product details
     * */
    class GetLocalDetails extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(DetailsLocalActivity.this);
            pDialog.setMessage("Loading local details. Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        /**
         * Getting product details in background thread
         * */
        protected String doInBackground(String... params) {

            // updating UI from Background Thread
            runOnUiThread(new Runnable() {
                public void run() {
                    // Check for success tag
                    int success;
                    try {
                        // Building Parameters
                        List<NameValuePair> params = new ArrayList<NameValuePair>();
                        params.add(new BasicNameValuePair("pid", pid));

                        // getting product details by making HTTP request
                        // Note that product details url will use GET request
                        JSONObject json = jsonParser.makeHttpRequest(
                                url_local_details, "GET", params);

                        // check your log for json response
                        Log.d("Single Local Details", json.toString());

                        // json success tag
                        success = json.getInt(TAG_SUCCESS);
                        if (success == 1) {
                            // successfully received local details
                            JSONArray localObj = json
                                    .getJSONArray(TAG_LOCAL); // JSON Array

                            // get first product object from JSON Array
                            JSONObject local = localObj.getJSONObject(0);

                            // local with this pid found
                            // Edit Text
                            txtName = (TextView) findViewById(R.id.inputName);
                            txtAddr = (TextView) findViewById(R.id.inputAddr);
                            txtDesc = (TextView) findViewById(R.id.inputDesc);

                            // display local data in EditText
                            txtName.setText(local.getString(TAG_NAME));
                            txtAddr.setText(local.getString(TAG_ADDR));
                            txtDesc.setText(local.getString(TAG_DESCRIPTION));

                        }else{
                            // local with pid not found
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });

            return null;
        }

        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(String file_url) {
            // dismiss the dialog once got all details
            pDialog.dismiss();
        }
    }
}
