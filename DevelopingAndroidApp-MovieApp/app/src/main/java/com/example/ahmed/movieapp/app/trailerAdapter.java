package com.example.ahmed.movieapp.app;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Belal on 25-Sep-16.
 */
public class trailerAdapter extends ArrayAdapter {
    public Context mContext;
    public int LayoutResourceId;
    public ArrayList<objTrailer> tList=new ArrayList<objTrailer>();
    public trailerAdapter(Context context, int resource, ArrayList<objTrailer> trailList) {
        super(context, resource,trailList);
        this.mContext=context;
        this.LayoutResourceId=resource;
        this.tList=trailList;
    }

    @Override
    public int getCount() {
        return tList.size();
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
            holder.name=(TextView)rootView.findViewById(R.id.movTrailer);
            holder.play=(ImageView)rootView.findViewById(R.id.play);
            rootView.setTag(holder);
        }else holder=(Holder)rootView.getTag();
        holder.name.setText(tList.get(position).getName());
        holder.play.setImageResource(R.drawable.utube);
        return rootView;
    }
    class Holder
    {
        TextView name;
        ImageView play;
    }
}
