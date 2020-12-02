package com.a1techandroid.tourguide.Adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.a1techandroid.tourguide.R;
import com.bumptech.glide.Glide;
import com.smarteist.autoimageslider.SliderViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class SliderAdapter extends
        SliderViewAdapter<SliderAdapter.SliderAdapterVH> {

    private Context context;
    private List<Drawable> mSliderItems = new ArrayList<>();

    public SliderAdapter(Context context) {
        this.context = context;
        addItem(context.getResources().getDrawable(R.drawable.s1));
        addItem(context.getResources().getDrawable(R.drawable.s10));
        addItem(context.getResources().getDrawable(R.drawable.s9));
        addItem(context.getResources().getDrawable(R.drawable.s2));
        addItem(context.getResources().getDrawable(R.drawable.s5));
        addItem(context.getResources().getDrawable(R.drawable.s8));
        addItem(context.getResources().getDrawable(R.drawable.s3));
        addItem(context.getResources().getDrawable(R.drawable.s4));
        addItem(context.getResources().getDrawable(R.drawable.s6));
        addItem(context.getResources().getDrawable(R.drawable.s7));
    }

    public void renewItems(List<Drawable> sliderItems) {
        this.mSliderItems = sliderItems;
        notifyDataSetChanged();
    }

    public void deleteItem(int position) {
        this.mSliderItems.remove(position);
        notifyDataSetChanged();
    }

    public void addItem(Drawable sliderItem) {
        this.mSliderItems.add(sliderItem);
        notifyDataSetChanged();
    }

    @Override
    public SliderAdapterVH onCreateViewHolder(ViewGroup parent) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_slider_item, null);
        return new SliderAdapterVH(inflate);
    }

    @Override
    public void onBindViewHolder(SliderAdapterVH viewHolder, final int position) {

        Drawable sliderItem = mSliderItems.get(position);


        Glide.with(viewHolder.itemView)
                .load(sliderItem)
                .fitCenter()
                .into(viewHolder.imageViewBackground);


    }

    @Override
    public int getCount() {
        //slider view count could be dynamic size
        return 10    ;
    }

    class SliderAdapterVH extends SliderViewAdapter.ViewHolder {
        View itemView;
        ImageView imageViewBackground;

        public SliderAdapterVH(View itemView) {
            super(itemView);
            imageViewBackground = itemView.findViewById(R.id.img);
            this.itemView = itemView;
        }
    }

}