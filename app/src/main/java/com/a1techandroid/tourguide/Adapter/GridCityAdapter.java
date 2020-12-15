package com.a1techandroid.tourguide.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.a1techandroid.tourguide.Fragments.CityDetailFragment;
import com.a1techandroid.tourguide.GiftActivity;
import com.a1techandroid.tourguide.Models.CityModel;
import com.a1techandroid.tourguide.Models.GiftModel;
import com.a1techandroid.tourguide.R;
import com.google.gson.Gson;

import java.util.ArrayList;

public class GridCityAdapter extends BaseAdapter {
    Context context;
    ArrayList<CityModel> list = new ArrayList<>();

    public GridCityAdapter(Context context, ArrayList<CityModel> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
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
        TextView arrival;
        ImageView img;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null){
            LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.grid_city_item, null);
            holder = new ViewHolder();
            holder.arrival = convertView.findViewById(R.id.name);
            holder.img = convertView.findViewById(R.id.image);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        final CityModel name = list.get(position);


        holder.arrival.setText(name.getName());

        holder.img.setImageDrawable(name.getImg());
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new CityDetailFragment();
                Bundle bundle = new Bundle();
                bundle.putSerializable("myIDKey",name);
                fragment.setArguments(bundle);
                FragmentManager fm = ((AppCompatActivity)context).getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.add(R.id.inc, fragment).addToBackStack(fragment.getTag());
                ft.commit();
            }
        });


        return convertView;
    }
}




