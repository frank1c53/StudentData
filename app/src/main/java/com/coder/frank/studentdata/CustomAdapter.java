package com.coder.frank.studentdata;

import android.content.Context;
import android.media.Image;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

class CustomAdapter extends ArrayAdapter<String>{
    int img[];
    public CustomAdapter( Context context,  String[] Students,int img[]) {
        super(context,R.layout.custom_row,Students);
        this.img=img;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater lf= LayoutInflater.from(getContext());
        View CustomView=lf.inflate(R.layout.custom_row,parent,false);
        //get a reference
        String studentdata=getItem(position);
        TextView t1=(TextView) CustomView.findViewById(R.id.tv1);
        ImageView i1=(ImageView) CustomView.findViewById(R.id.iv1);
        t1.setText(studentdata);
        i1.setImageResource(img[position] );
        return CustomView;

    }
}
