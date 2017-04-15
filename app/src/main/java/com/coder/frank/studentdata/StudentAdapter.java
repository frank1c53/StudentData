package com.coder.frank.studentdata;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

/**
 * Created by Vivek Gandhi on 3/26/2017.
 */

public class StudentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private LayoutInflater inflater;
    List<Students> data= Collections.emptyList();
    Students current;
    int currentPos=0;
    public StudentAdapter(){}
    // create constructor to innitilize context and data sent from MainActivity
    public StudentAdapter(Context context, List<Students> data){
        this.context=context;
        inflater= LayoutInflater.from(context);
        this.data=data;
    }

    // Inflate the layout when viewholder created
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=inflater.inflate(R.layout.containers_student, parent,false);
        MyHolder holder=new MyHolder(view);
        return holder;
    }

    // Bind data
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        // Get current position of item in recyclerview to bind data and assign values from list
        MyHolder myHolder= (MyHolder) holder;
        Students current=data.get(position);
        myHolder.id.setText("ID : " + current.id);
        myHolder.idedit.setText("ID : " + current.id);
        myHolder.name.setText("Name: " + current.name);
        myHolder.email.setText("Email: " + current.email);
        myHolder.rollno.setText("Rollno: " + current.rollno);
        myHolder.marks1.setText("Marks: " + current.marks1);
        myHolder.marks1.setTextColor(ContextCompat.getColor(context, R.color.colorAccent));
        myHolder.marks2.setText("Marks: " + current.marks2);
        myHolder.marks2.setTextColor(ContextCompat.getColor(context, R.color.colorAccent));
        myHolder.percentage.setText("Percentage:" + current.percent+"%");
        myHolder.percentage.setTextColor(ContextCompat.getColor(context, R.color.percentAccent));
        myHolder.att1.setText("Attendance 1:" + current.att1);
        myHolder.att1.setTextColor(ContextCompat.getColor(context, R.color.colorAccent));
        myHolder.att2.setText("Attendance 2:" + current.att2);
        myHolder.att2.setTextColor(ContextCompat.getColor(context, R.color.colorAccent));
        myHolder.all.setText("Overall:" + current.per+"%");
        myHolder.all.setTextColor(ContextCompat.getColor(context, R.color.percentAccent));


        // load image into imageview using glide
       /* Glide.with(context).load("http://192.168.1.7/test/images/" + current.fishImage)
                .placeholder(R.drawable.ic_img_error)
                .error(R.drawable.ic_img_error)
                .into(myHolder.ivFish);*/

    }

    // return total item from List
    @Override
    public int getItemCount() {
        return data.size();
    }


    class MyHolder extends RecyclerView.ViewHolder{
        EditText idedit;
        TextView name;
        ImageView ivFish;
        TextView email;
        TextView rollno;
        TextView marks1;
        TextView marks2;
        TextView percentage;
        TextView att1;
        TextView att2;
        TextView all;

        TextView id;
        // create constructor to get widget reference
        public MyHolder(View itemView) {
            super(itemView);
            id = (TextView) itemView.findViewById(R.id.id);
            idedit = (EditText) itemView.findViewById(R.id.idedit);
            name= (TextView) itemView.findViewById(R.id.sname);
            // ivFish= (ImageView) itemView.findViewById(R.id.ivFish);
            email = (TextView) itemView.findViewById(R.id.email);
           rollno = (TextView) itemView.findViewById(R.id.rollno);
            marks1 = (TextView) itemView.findViewById(R.id.marks1);
            marks2 = (TextView) itemView.findViewById(R.id.marks2);
            percentage =(TextView) itemView.findViewById(R.id.percent);
            att1 = (TextView) itemView.findViewById(R.id.att1);
            att2 = (TextView) itemView.findViewById(R.id.att2);
            all = (TextView) itemView.findViewById(R.id.all);

        }

    }


}
