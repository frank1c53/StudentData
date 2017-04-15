package com.coder.frank.studentdata;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;


import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class InsertActivity extends AppCompatActivity {
    EditText name,email,rollno,marks1,marks2,att1,att2;
    private static final String TAG = "msg";
    String n,e,r,m1,m2,at1,at2;
    int pr,st1,st2;
    float a1,a2,all,sall;
    private TextView mResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert);
    }
    public void submitData(View view)
    { Log.i(TAG,"inside submitdata button");
        mResult = (TextView) findViewById(R.id.result);
        name = (EditText) findViewById(R.id.editText1);
        email = (EditText) findViewById(R.id.editText2);
        rollno = (EditText) findViewById(R.id.editText3);
        marks1= (EditText) findViewById(R.id.editText4);
        marks2= (EditText) findViewById(R.id.editText5);
        att1 = (EditText) findViewById(R.id.editText6);
        att2 = (EditText) findViewById(R.id.editText7);

        n = name.getText().toString().trim();
        e = email.getText().toString().trim();
        r = rollno.getText().toString().trim();
        m1 = marks1.getText().toString().trim();
        m2 = marks2.getText().toString().trim();
        at1 = att1.getText().toString().trim();
        at2 = att2.getText().toString().trim();


        pr = Integer.parseInt(r);
        st1 = Integer.parseInt(m1);
        st2 = Integer.parseInt(m2);
        a1 = Float.parseFloat(at1);
        a2 = Float.parseFloat(at2);
        sall = (((st1+st2)/200.0f)*100.0f);
        all = (a1+a2)/2;
        Log.i(TAG,"b4 calling posttask");
        //make POST request
        //new PostDataTask().execute("http://192.168.43.233:1000/api/status");
        new PostDataTask().execute("http://192.168.0.101:1000/api/status");
        Log.i(TAG,"after calling posttask");

    }

    class PostDataTask extends AsyncTask<String, Void, String> {

        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            Log.i(TAG,"inside preexec");
            super.onPreExecute();

            progressDialog = new ProgressDialog(InsertActivity.this);
            progressDialog.setMessage("Inserting data...");
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            Log.i(TAG,"inside doInBackgroundc");
            try {
                return postData(params[0]);
            } catch (IOException ex) {
                return "Network error !";
            } catch (JSONException ex) {
                return "Data Invalid !";
            }
        }

        @Override
        protected void onPostExecute(String result) {
            Log.i(TAG,"inside nPostExecute");
            super.onPostExecute(result);

            mResult.setText(result);

            if (progressDialog != null) {
                progressDialog.dismiss();
            }
        }

        private String postData(String urlPath) throws IOException, JSONException {
            Log.i(TAG,"inside postData");
            StringBuilder result = new StringBuilder();
            BufferedWriter bufferedWriter = null;
            BufferedReader bufferedReader = null;

            try {
                //Create data to send to server
                JSONObject dataToSend = new JSONObject();
                dataToSend.put("name", n);
                dataToSend.put("email", e);
                dataToSend.put("rollno", pr);
                dataToSend.put("marks1", st1);
                dataToSend.put("marks2", st2);
                dataToSend.put("percentage", sall);
                dataToSend.put("attend1", a1);
                dataToSend.put("attend2", a2);
                dataToSend.put("overall", all);


                //Initialize and config request, then connect to server.
                URL url = new URL(urlPath);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setReadTimeout(10000 /* milliseconds */);
                urlConnection.setConnectTimeout(10000 /* milliseconds */);
                urlConnection.setRequestMethod("POST");
                urlConnection.setDoOutput(true);  //enable output (body data)
                urlConnection.setRequestProperty("Content-Type", "application/json");// set header
                urlConnection.connect();

                //Write data into server
                OutputStream outputStream = urlConnection.getOutputStream();
                bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream));
                bufferedWriter.write(dataToSend.toString());
                bufferedWriter.flush();

                //Read data response from server
                InputStream inputStream = urlConnection.getInputStream();
                bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    result.append(line).append("\n");
                }
            } finally {
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
                if (bufferedWriter != null) {
                    bufferedWriter.close();
                }
            }

            return result.toString();
        }
    }

}
