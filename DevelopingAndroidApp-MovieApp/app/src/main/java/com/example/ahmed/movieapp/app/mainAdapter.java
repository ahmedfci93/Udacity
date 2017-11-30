package com.example.ahmed.movieapp.app;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Belal on 19-Sep-16.
 */
public class mainAdapter extends ArrayAdapter {

    private Context mContext;
    private ArrayList<String> mList=new ArrayList<String>();
    private int layoutResourceId;

    public mainAdapter(Context context, int resource, ArrayList<String> movList) {
        super(context, resource, movList);
        this.mContext=context;
        this.mList=movList;
        this.layoutResourceId=resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder hold=null;
        View rootView=convertView;
        if(rootView==null)
        {
            LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
            rootView= inflater.inflate(layoutResourceId, parent, false);
            hold=new Holder();
            hold.imageView=(ImageView)rootView.findViewById(R.id.imgMovie);
            rootView.setTag(hold);
        }else hold= (Holder) rootView.getTag();
        String url=mList.get(position);
        if(mList.get(position)==null)
        {
            hold.imageView.setImageResource(R.drawable.image);
        }
        else {
            Picasso.with(mContext).load(url).into(hold.imageView);
        }
        return rootView;
    }
    class Holder
    {
        ImageView imageView;
    }

}
