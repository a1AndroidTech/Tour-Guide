package com.a1techandroid.tourguide.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.a1techandroid.tourguide.Fragments.AboutAppfragment;
import com.a1techandroid.tourguide.LoginActivity;
import com.a1techandroid.tourguide.MainActivity;
import com.a1techandroid.tourguide.Models.Places;
import com.a1techandroid.tourguide.R;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class PlacesAdapter extends BaseAdapter {

    Context context;
    ArrayList<Places> list= new ArrayList<>();
    @Override
    public int getCount() {
        return list.size();
    }

    public PlacesAdapter(Context context, ArrayList<Places> list1){
        this.context = context;
        this.list = list1;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    class ViewHolder{
        TextView name, loc;
        ImageView marker;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null){
            LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.places_item, null);
            holder = new ViewHolder();
            holder.name = convertView.findViewById(R.id.name);
            holder.loc = convertView.findViewById(R.id.loc);
            holder.marker = convertView.findViewById(R.id.marker);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        final Places name = list.get(position);

        holder.name.setText(name.getName());
        holder.loc.setText(name.getLoc());

        holder.marker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri location = Uri.parse(String.valueOf(R.string.mq_mountain_whitewater));
                showMap(location);
            }
        });

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        return convertView;
    }

    public void showMap(Uri geoLocation) {
        // Initialize the map intent with an action and the geolocation parameter
        Intent mapIntent = new Intent(Intent.ACTION_VIEW);

        // Make the intent explicit by setting Google Maps package
        mapIntent.setPackage("com.google.android.apps.maps");

        // Attempt to start an activity that can handle the Intent w/o crashing the app
        if (mapIntent.resolveActivity(context.getPackageManager()) != null) {
            context.startActivity(mapIntent);
        }
    }
}
