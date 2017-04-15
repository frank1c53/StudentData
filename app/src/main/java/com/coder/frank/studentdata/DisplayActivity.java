package com.coder.frank.studentdata;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class DisplayActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    String[] cN;
    ArrayAdapter<String> adapter;
    public RecyclerView mRVFishPrice;
    public StudentAdapter mAdapter;
    TextView n,a,p,s;
    private TextView mResult;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);
        try {
            displayData();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        // new GetDataTask().execute("http://192.168.2.4:1001/api/status");
        //INITALIZE ADAPTER
/*
        adapter = new ArrayAdapter<String>(DisplayActivity.this,R.layout.activity_display,cN);

        setListAdapter(adapter);

        ListView list = getListView();
        list.setOnItemClickListener(DisplayActivity.this);*/



    }
    public void displayData() throws JSONException {
        n = (TextView) findViewById(R.id.test);
       /* a = (TextView) findViewById(R.id.author);
        p = (TextView) findViewById(R.id.price);
        s = (TextView) findViewById(R.id.stock);
        mResult = (TextView) findViewById(R.id.result);
*/
        //make GET request
        //new GetDataTask().execute("http://192.168.43.233:1000/api/status");
        new GetDataTask().execute("http://192.168.0.101:1000/api/status");
        // Spinner s = (Spinner) findViewById(R.id.books);
       /* ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, cN);
        s.setAdapter(adapter);*/
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        String itemval = adapterView.getItemAtPosition(i).toString();
        Toast.makeText(DisplayActivity.this,itemval,Toast.LENGTH_SHORT).show();
    }
/*
AsyncTask's generic types
The three types used by an asynchronous task are the following:

Params, the type of the parameters sent to the task upon execution.
Progress, the type of the progress units published during the background computation.
Result, the type of the result of the background computation.
Not all types are always used by an asynchronous task. To mark a type as unused, simply use the type Void:
*/

    class GetDataTask extends AsyncTask<String, Void, String>  {

        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {

            super.onPreExecute();

            progressDialog = new ProgressDialog(DisplayActivity.this);
            progressDialog.setMessage("Loading data...");
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {

            try {
                return getData(params[0]);
            } catch (IOException ex) {
                return "Network error !";
            }
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            List<Students> data=new ArrayList<>();
            //   Spinner s = (Spinner) findViewById(R.id.books);
            //set data response to textView
//            mResult.setText(result);
            //n.setText("onPostExe");
            //JSONObject obj = null;
            JSONArray obj ;
            try {
                obj = new JSONArray(result);
                //obj = new JSONObject(result);
                cN = new String[obj.length()];

                Students[] StudentData = new Students[obj.length()];
                for (int i = 0; i < obj.length(); i++) {
                    JSONObject jsonobj = obj.getJSONObject(i);
                    // Books bookData = new Books();
                    String N1;
                   StudentData[i] = new Students();
                    StudentData[i].id = jsonobj.getString("_id");
                    StudentData[i].name= jsonobj.getString("name");
                    StudentData[i].email = jsonobj.getString("email");
                    StudentData[i].rollno = Integer.parseInt(jsonobj.getString("rollno"));
                    StudentData[i].marks1 = Integer.parseInt(jsonobj.getString("marks1"));
                    StudentData[i].marks2 = Integer.parseInt(jsonobj.getString("marks2"));
                    StudentData[i].percent = Float.parseFloat(jsonobj.getString("percentage"));
                    StudentData[i].att1 = Float.parseFloat(jsonobj.getString("attend1"));
                    StudentData[i].att2 = Float.parseFloat(jsonobj.getString("attend2"));
                    StudentData[i].per = Float.parseFloat(jsonobj.getString("overall"));
                    data.add(StudentData[i]);
                    if (i == obj.length() - 1) {
                        n.setText("total row = " + obj.length());
                    }
                }
                // Setup and Handover data to recyclerview
                mRVFishPrice = (RecyclerView)findViewById(R.id.fishPriceList);
                mAdapter = new StudentAdapter(DisplayActivity.this, data);
                mRVFishPrice.setAdapter(mAdapter);
                mRVFishPrice.setLayoutManager(new LinearLayoutManager(DisplayActivity.this));
                //  mAdapter.notifyDataSetChanged();
             /*   ArrayAdapter<String> adapter = new ArrayAdapter<String>(DisplayActivity.this,
                        android.R.layout.linearLayout5, cN);
                s.setAdapter(adapter);*/
            } catch (JSONException e) {

                Toast.makeText(DisplayActivity.this,e.toString(),Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }

            //cancel progress dialog
            if (progressDialog != null) {
                progressDialog.dismiss();
            }
        }

        private String getData(String urlPath) throws IOException {
            //  n.setText("GET Data");
            StringBuilder result = new StringBuilder();
            BufferedReader bufferedReader =null;

            try {
                //Initialize and config request, then connect to server
                URL url = new URL(urlPath);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setReadTimeout(10000 /* milliseconds */);
                urlConnection.setConnectTimeout(10000 /* milliseconds */);
                urlConnection.setRequestMethod("GET");
                urlConnection.setRequestProperty("Content-Type", "application/json");// set header
                urlConnection.connect();

                //Read data response from server
                InputStream inputStream = urlConnection.getInputStream();
                bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String line;
                while ((line = bufferedReader.readLine()) != null) {

                    result.append(line).append("\n");

                }



            } /*catch (JSONException e) {
                e.printStackTrace();
            } */finally {
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
            }

            return result.toString();
        }


    }

}
