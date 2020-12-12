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
import com.a1techandroid.tourguide.Models.HistotyModel;
import com.a1techandroid.tourguide.R;
import com.google.gson.Gson;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class HistoryAdapter extends BaseAdapter {
    Context context;
    ArrayList<HistotyModel> list = new ArrayList<>();

    public HistoryAdapter(Context context, ArrayList<HistotyModel> list) {
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
        TextView name, status, date, type;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null){
            LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.history_item, null);
            holder = new ViewHolder();
            holder.name = convertView.findViewById(R.id.name);
            holder.status = convertView.findViewById(R.id.status);
            holder.date = convertView.findViewById(R.id.date);
            holder.type = convertView.findViewById(R.id.type);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        final HistotyModel name = list.get(position);


        holder.name.setText(name.getName());
        holder.status.setText(name.getStatus());
        holder.date.setText(getFormateddate(name.getDate()));
        holder.type.setText(name.getType());

//        convertView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(v.getContext(), GiftActivity.class);
//                Gson gson = new Gson();
//                String myJson = gson.toJson(name);
//                intent.putExtra("model", myJson);
//                v.getContext().startActivity(intent);
//            }
//        });


        return convertView;
    }

    public String getFormateddate(String format){
        Date date=null;
        String date2 = null;
        SimpleDateFormat formatter = new SimpleDateFormat("EEE MMM dd yyyy");
        String temp = "Thu Dec 17 15:37:43 GMT+05:30 2015";
        try {
            date = formatter.parse(format);
            date2 = date.toString();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return date2;
    }
}




