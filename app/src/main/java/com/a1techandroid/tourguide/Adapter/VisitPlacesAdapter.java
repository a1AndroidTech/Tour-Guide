package com.a1techandroid.tourguide.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.a1techandroid.tourguide.Models.UserModel;
import com.a1techandroid.tourguide.Models.VisitPlaces;
import com.a1techandroid.tourguide.R;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class VisitPlacesAdapter extends BaseAdapter {
    Context context;
    ArrayList<VisitPlaces> list = new ArrayList<>();

    public VisitPlacesAdapter(Context context, ArrayList<VisitPlaces> list) {
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
        TextView detail;
        ImageView image;
        Button button;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null){
            LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.visit_place, null);
            holder = new ViewHolder();
            holder.detail = convertView.findViewById(R.id.description);
            holder.image = convertView.findViewById(R.id.placeimage);
            holder.button = convertView.findViewById(R.id.add);

            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        final VisitPlaces name = list.get(position);

        holder.button.setVisibility(View.GONE);
        holder.detail.setText(name.getDetail());
        holder.detail.setEnabled(false);
        if (name.getImageUrl() != "") {
            Glide.with(context)
                    .load(name.getImageUrl())
                    .centerCrop()
                    .into(holder.image);
        }
        return convertView;
    }
}





