package com.a1techandroid.tourguide.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.a1techandroid.tourguide.GiftActivity;
import com.a1techandroid.tourguide.Models.GiftModel;
import com.a1techandroid.tourguide.PlaneDetailActivity;
import com.a1techandroid.tourguide.R;
import com.google.gson.Gson;

import java.util.ArrayList;

public class GiftAdapter extends BaseAdapter {
    Context context;
    ArrayList<GiftModel> list = new ArrayList<>();

    public GiftAdapter(Context context, ArrayList<GiftModel> list) {
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
        ImageView img;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null){
            LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.gift_item, null);
            holder = new ViewHolder();
            holder.arrival = convertView.findViewById(R.id.arrival);
            holder.destination = convertView.findViewById(R.id.dep);
            holder.economy = convertView.findViewById(R.id.premium);
            holder.gold = convertView.findViewById(R.id.goldClass);
            holder.img = convertView.findViewById(R.id.img);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        final GiftModel name = list.get(position);


        holder.arrival.setText(name.getEco());
        holder.destination.setText(name.getArrival());
        holder.economy.setText(name.getArrival());

        holder.img.setImageDrawable(context.getResources().getDrawable(R.drawable.bell_icon));
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), GiftActivity.class);
                Gson gson = new Gson();
                String myJson = gson.toJson(name);
                intent.putExtra("model", myJson);
                v.getContext().startActivity(intent);
            }
        });


        return convertView;
    }
}



