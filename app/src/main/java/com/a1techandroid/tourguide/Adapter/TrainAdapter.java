package com.a1techandroid.tourguide.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.a1techandroid.tourguide.HotelDetail;
import com.a1techandroid.tourguide.Models.TrainModel;
import com.a1techandroid.tourguide.R;
import com.a1techandroid.tourguide.TrainDetailActivity;
import com.google.gson.Gson;

import java.util.ArrayList;

public class TrainAdapter extends BaseAdapter {
    Context context;
    ArrayList<TrainModel> list = new ArrayList<>();

    public TrainAdapter(Context context, ArrayList<TrainModel> list) {
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
        TextView name, loc;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null){
            LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.train_item, null);
            holder = new ViewHolder();
            holder.name = convertView.findViewById(R.id.trainName);
            holder.loc = convertView.findViewById(R.id.destination);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        final TrainModel name = list.get(position);

        holder.name.setText(name.getTrainName());
        holder.loc.setText(name.getEndPoints());

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), TrainDetailActivity.class);
                Gson gson = new Gson();
                String myJson = gson.toJson(name);
                intent.putExtra("model", myJson);
                v.getContext().startActivity(intent);
            }
        });

        return convertView;
    }
}



