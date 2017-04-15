package com.coder.frank.studentdata;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class DeleteActivity extends AppCompatActivity {

    TextView mResult;
    EditText delid;
    String did;
    String del = "58d78c09cfa70620e0138ef3";
    // String ex = "http://192.168.1.15:1000/api/status/"+del;
    //ex.concate(del);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete);
        mResult = (TextView) findViewById(R.id.textView);
        delid = (EditText) findViewById(R.id.delid);
        did = delid.getText().toString();
        //String ex = "http://192.168.1.15:1000/api/status/"+del;
        //make DELETE request
        // new DeleteDataTask().execute(ex);
    }
    public void delete(View v)
    {
       // String ex = "http://192.168.43.233:1000/api/status/"+did;
        //make DELETE request
        String ex = "http://192.168.0.101:1000/api/status/"+did;
        new DeleteDataTask().execute(ex);

    }

    class DeleteDataTask extends AsyncTask<String, Void, String> {

        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = new ProgressDialog(DeleteActivity.this);
            progressDialog.setMessage("Deleting data...");
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                return deleteData(params[0]);
            } catch (IOException ex) {
                return "Network error !";
            }
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            mResult.setText(result);

            if(progressDialog != null) {
                progressDialog.dismiss();
            }
        }

        private String deleteData(String urlPath) throws IOException {

            String result =  null;

            //Initialize and config request, then connect to server.
            URL url = new URL(urlPath);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(10000 /* milliseconds */);
            urlConnection.setRequestMethod("DELETE");
            urlConnection.setRequestProperty("Content-Type", "application/json");// set header
            urlConnection.connect();

            //Check update successful or not
            if (urlConnection.getResponseCode() == 204) {
                result = "Delete successfully !";
            } else {
                result = "Delete failed !";
            }
            return result;
        }
    }


}
