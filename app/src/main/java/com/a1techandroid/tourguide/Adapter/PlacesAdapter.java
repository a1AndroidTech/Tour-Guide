package com.a1techandroid.tourguide.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
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
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        final Places name = list.get(position);

        holder.name.setText(name.getName());
        holder.loc.setText(name.getLoc());


        return convertView;
    }
}
