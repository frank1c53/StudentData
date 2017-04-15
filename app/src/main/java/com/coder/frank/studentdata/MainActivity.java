package com.coder.frank.studentdata;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String Students[]={"Display Student Data","Add Student Data","Modify Student Data","Delete Student Data"};
        int[] img ={
                R.drawable.display,
                R.drawable.add,
                R.drawable.modify,
                R.drawable.delete};
        ListAdapter la1=new CustomAdapter(this,Students,img);
        ListView l1= (ListView) findViewById(R.id.l1);
        l1.setAdapter(la1);
        l1.setOnItemClickListener(
                new AdapterView.OnItemClickListener(){

                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String pos=String.valueOf(parent.getItemAtPosition(position));
                        Toast.makeText(MainActivity.this,pos,Toast.LENGTH_LONG).show();
                        if(position==0)
                        {
                            Intent i=new Intent(view.getContext(),DisplayActivity.class);
                            startActivity(i);
                        }
                        if(position==1)
                        {
                           Intent i=new Intent(view.getContext(),InsertActivity.class);
                            startActivity(i);
                        }
                        if(position==2)
                        {
                            Intent i=new Intent(view.getContext(),UpdateActivity.class);
                            startActivity(i);
                        }
                        if(position==3)
                        {
                            Intent i=new Intent(view.getContext(),DeleteActivity.class);
                            startActivity(i);
                        }
                    }
                }
        );
    }


}