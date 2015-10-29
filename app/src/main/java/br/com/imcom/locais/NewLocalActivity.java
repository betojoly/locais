package br.com.imcom.locais;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.NameValuePair;
import cz.msebera.android.httpclient.message.BasicNameValuePair;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.util.Log;

/**
 * Created by BETO on 27/10/2015.
 */
public class NewLocalActivity extends Activity {
    // Progress Dialog
    private ProgressDialog pDialog;

    // Creating JSON Parser object
    JSONParser jsonParser = new JSONParser();
    EditText inputName;
    EditText inputAddr;
    EditText inputDesc;

    // url to create new local
    private static String url_create_local = "http://joly.imontanha.com//android_connect/create_local.php";

    // create LOG_TAG


    // JSON Node names
    private static final String TAG_SUCCESS = "success";

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.add_local);

        // Edit Text
        inputName = (EditText) findViewById(R.id.inputName);
        inputAddr = (EditText) findViewById(R.id.inputAddr);
        inputDesc = (EditText) findViewById(R.id.inputDesc);

        // Create button
        Button btnCreateLocal = (Button) findViewById(R.id.btnCreateLocal);

        // button click event
        btnCreateLocal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // creating new local in background thread
                new CreateNewLocal().execute();
            }
        });

    }

    /**
     * Background Async Task to Create new Local
     * */
    class CreateNewLocal extends AsyncTask<String, String, String> {
        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(NewLocalActivity.this);
            pDialog.setMessage("Criando Local...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        /**
         * Creating Local
         * */
        protected String doInBackground(String... args){
            String name = inputName.getText().toString();
            String addr = inputAddr.getText().toString();
            String description = inputDesc.getText().toString();

            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("name", name));
            params.add(new BasicNameValuePair("addr", addr));
            params.add(new BasicNameValuePair("description", description));

            // getting JSON Object
            // Note that create local url accepts POST method
            JSONObject json = jsonParser.makeHttpRequest(url_create_local, "POST", params);

            // check log cat for response
            Log.d("Create Response", json.toString());

            // check for success tag
            try {
                int success = json.getInt(TAG_SUCCESS);

                if(success == 1){
                    // successfully created local
                    Intent i = new Intent(getApplication(), AllLocalActivity.class);
                    startActivity(i);

                    // closing this screen
                    finish();
                } else {
                    // failed to create product
                }
            } catch (JSONException e){
                e.printStackTrace();
            }

            return null;

        }

        /**
         * After completing background task Dismiss the progress dialog
         * **/
        @Override
        protected void onPostExecute(String file_url) {
            super.onPostExecute(file_url);
            // dismiss the dialog once done
            pDialog.dismiss();
        }
    }
}
