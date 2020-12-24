package com.a1techandroid.tourguide.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.a1techandroid.tourguide.Models.HistotyModel;
import com.a1techandroid.tourguide.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class HistoryAdapterNew extends BaseAdapter {
    Context context;
    ArrayList<HistotyModel> list = new ArrayList<>();

    public HistoryAdapterNew(Context context, ArrayList<HistotyModel> list) {
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
        TextView name, status, date, type, userName;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null){
            LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.history_cell_new, null);
            holder = new ViewHolder();
            holder.name = convertView.findViewById(R.id.name);
            holder.status = convertView.findViewById(R.id.status);
            holder.date = convertView.findViewById(R.id.date);
            holder.type = convertView.findViewById(R.id.type);
            holder.userName = convertView.findViewById(R.id.statusOrder);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        final HistotyModel name = list.get(position);


        holder.name.setText(name.getName());
        holder.status.setText(name.getStatus());
        holder.date.setText(name.getUserEmail());
        holder.type.setText(name.getType());
        holder.userName.setText(name.getUserName());
        return convertView;
    }

}





