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
import com.a1techandroid.tourguide.Models.Places;
import com.a1techandroid.tourguide.R;
import com.a1techandroid.tourguide.SignUpNewActivity;
import com.google.gson.Gson;

import java.util.ArrayList;

public class GridViewAdapter extends BaseAdapter {
    Context context;
    ArrayList<Places> list = new ArrayList<>();

    public GridViewAdapter(Context context, ArrayList<Places> list) {
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
        TextView name;
        ImageView img;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null){
            LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.item_signup_option, null);
            holder = new ViewHolder();
            holder.name = convertView.findViewById(R.id.name);
            holder.img = convertView.findViewById(R.id.img);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        final Places name = list.get(position);


        holder.name.setText(name.getName());
        holder.img.setImageDrawable(name.getDrawable());
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, SignUpNewActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                if (position == 0){
                   intent.putExtra("userType", 1);
                    v.getContext().startActivity(intent);
                }else if (position == 1){
                    intent.putExtra("userType", 2);
                    v.getContext().startActivity(intent);
                }else if (position == 2){
                    intent.putExtra("userType", 3);
                    v.getContext().startActivity(intent);
                }else if (position ==3){
                    intent.putExtra("userType", 4);
                    v.getContext().startActivity(intent);
                }

            }
        });


        return convertView;
    }
}




