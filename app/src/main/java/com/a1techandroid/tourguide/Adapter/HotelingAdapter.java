package com.a1techandroid.tourguide.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import com.a1techandroid.tourguide.GiftActivity;
import com.a1techandroid.tourguide.HotelDetail;
import com.a1techandroid.tourguide.Models.HotelModel;
import com.a1techandroid.tourguide.R;
import com.google.gson.Gson;

import java.util.ArrayList;

public class HotelingAdapter extends BaseAdapter {
    Context context;
    ArrayList<HotelModel> list = new ArrayList<>();

    public HotelingAdapter(Context context, ArrayList<HotelModel> list) {
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
        TextView arrival, destination, economy, gold;
        RatingBar ratingBar;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null){
            LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.hotel_item, null);
            holder = new ViewHolder();
            holder.arrival = convertView.findViewById(R.id.arrival);
            holder.destination = convertView.findViewById(R.id.dep);
            holder.economy = convertView.findViewById(R.id.premium);
            holder.ratingBar = convertView.findViewById(R.id.rating);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        final HotelModel name = list.get(position);
        holder.arrival.setText(name.getDestination());
        holder.destination.setText(name.getArrival());
        holder.economy.setText(name.getEco());
        holder.ratingBar.setRating(name.getRating());

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), HotelDetail.class);
                Gson gson = new Gson();
                String myJson = gson.toJson(name);
                intent.putExtra("model", myJson);
                v.getContext().startActivity(intent);
            }
        });


        return convertView;
    }
}



