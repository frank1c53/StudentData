package com.coder.frank.studentdata;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class UpdateActivity extends AppCompatActivity {
    EditText name,email,rollno,marks1,marks2,att1,att2,uid;
    private static final String TAG = "msg";
    String n,e,r,m1,m2,at1,at2,u;
    int pr,st1,st2;
    float sall,a1,a2,all;
    private TextView mResult;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
    }

    public void submitData(View view)
    { Log.i(TAG,"inside submitdata button");
        mResult = (TextView) findViewById(R.id.result);
        name = (EditText) findViewById(R.id.editText1);
        email = (EditText) findViewById(R.id.editText2);
        rollno = (EditText) findViewById(R.id.editText3);
        marks1= (EditText) findViewById(R.id.editText4);
        marks2= (EditText) findViewById(R.id.editText5);
        att1= (EditText) findViewById(R.id.editText6);
       att2= (EditText) findViewById(R.id.editText7);

        uid = (EditText) findViewById(R.id.updateid);

        u = uid.getText().toString();
        n = name.getText().toString().trim();
        e = email.getText().toString().trim();
        r = rollno.getText().toString().trim();
        m1 = marks1.getText().toString().trim();
        m2 =marks2.getText().toString().trim();
        at1=att1.getText().toString().trim();
        at2=att2.getText().toString().trim();



        pr = Integer.parseInt(r);
        st1 = Integer.parseInt(m1);
        st2 = Integer.parseInt(m2);
        sall = ((st1+st2)/200)*100;
        Log.i(TAG,"b4 calling posttask");
        //String ex = "http://192.168.43.233:1000/api/status/"+u;
        String ex = "http://192.168.0.101:1000/api/status/"+u;
        //make PUT request1
        new PutDataTask().execute(ex);

    }

    class PutDataTask extends AsyncTask<String, Void, String> {

        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = new ProgressDialog(UpdateActivity.this);
            progressDialog.setMessage("Updating data...");
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                return putData(params[0]);
            } catch (IOException ex) {
                return "Network error !";
            } catch (JSONException ex) {
                return "Data invalid !";
            }

        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            mResult.setText(result);

            if (progressDialog != null) {
                progressDialog.dismiss();
            }
        }

        private String putData(String urlPath) throws IOException, JSONException {

            BufferedWriter bufferedWriter = null;
            String result = null;

            try {


                //Create data to send to server
                JSONObject dataToSend = new JSONObject();
                dataToSend.put("name", n);
                dataToSend.put("email", e);
                dataToSend.put("rollno", pr);
                dataToSend.put("marks1", st1);
                dataToSend.put("marks2", st1);
                dataToSend.put("Percentage", sall);
                dataToSend.put("attend1", a1);
                dataToSend.put("attend2", a2);
                dataToSend.put("Overall", all);


                //Initialize and config request, then connect to server
                URL url = new URL(urlPath);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setReadTimeout(10000 /* milliseconds */);
                urlConnection.setConnectTimeout(10000 /* milliseconds */);
                urlConnection.setRequestMethod("PUT");
                urlConnection.setDoOutput(true);  //enable output (body data)
                urlConnection.setRequestProperty("Content-Type", "application/json");// set header
                urlConnection.connect();

                //Write data into server
                OutputStream outputStream = urlConnection.getOutputStream();
                bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream));
                bufferedWriter.write(dataToSend.toString());
                bufferedWriter.flush();

                //Check update successful or not
                if (urlConnection.getResponseCode() == 200) {
                    return "Update successfully !";
                } else {
                    return "Update failed !";
                }
            } finally {
                if(bufferedWriter != null) {
                    bufferedWriter.close();
                }
            }
        }
    }

}
