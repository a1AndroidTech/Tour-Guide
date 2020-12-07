package com.a1techandroid.tourguide.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.a1techandroid.tourguide.Models.Places;
import com.a1techandroid.tourguide.Models.PlaneModel;
import com.a1techandroid.tourguide.PlaneDetailActivity;
import com.a1techandroid.tourguide.R;
import com.google.gson.Gson;

import java.util.ArrayList;

public class PlaneAdapter extends BaseAdapter {

    Context context;
    ArrayList<PlaneModel> list = new ArrayList<>();

    public PlaneAdapter(Context context, ArrayList<PlaneModel> list) {
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
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null){
            LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.plane_item, null);
            holder = new ViewHolder();
            holder.arrival = convertView.findViewById(R.id.arrival);
            holder.destination = convertView.findViewById(R.id.dep);
            holder.economy = convertView.findViewById(R.id.premium);
            holder.gold = convertView.findViewById(R.id.goldClass);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        final PlaneModel name = list.get(position);
        holder.arrival.setText(name.getArrival());
        holder.destination.setText(name.getDestination());
        holder.economy.setText(name.getEco());
        holder.gold.setText(name.getGold());


        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), PlaneDetailActivity.class);
                Gson gson = new Gson();
                String myJson = gson.toJson(name);
                intent.putExtra("model", myJson);
                v.getContext().startActivity(intent);
            }
        });

        return convertView;
    }
}


