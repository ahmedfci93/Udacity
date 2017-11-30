package com.example.ahmed.movieapp.app;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Belal on 26-Sep-16.
 */
public class reviewAdapter extends ArrayAdapter {

    public Context mContext;
    public int LayoutResourceId;
    public ArrayList<objReview> rList=new ArrayList<objReview>();

    public reviewAdapter(Context context, int resource, ArrayList<objReview> reviewList) {
        super(context, resource, reviewList);
        this.mContext=context;
        this.LayoutResourceId=resource;
        this.rList=reviewList;
    }

    @Override
    public int getCount() {
        return rList.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        View rootView=convertView;
        Holder holder=null;
        if(rootView==null)
        {
            LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
            rootView= inflater.inflate(LayoutResourceId, parent, false);
            holder=new Holder();
            holder.author=(TextView)rootView.findViewById(R.id.author);
            holder.content=(TextView)rootView.findViewById(R.id.content);
            rootView.setTag(holder);
        }else holder=(Holder)rootView.getTag();
        holder.author.setText(rList.get(position).author);
        holder.content.setText(rList.get(position).content);
        return rootView;
    }

    class Holder
    {
        TextView author;
        TextView content;
    }
}
