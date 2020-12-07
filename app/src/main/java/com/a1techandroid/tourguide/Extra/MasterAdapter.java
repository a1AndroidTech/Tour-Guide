package com.a1techandroid.tourguide.Extra;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.a1techandroid.tourguide.R;

import java.util.List;

public class MasterAdapter
        extends RecyclerView.Adapter<MasterAdapter.SectionViewHolder> {
    
    private List<AttractionCollection> mData;
    private Context mContext;

    public MasterAdapter(Context context, List<AttractionCollection> data) {
        this.mData = data;
        this.mContext = context;
    }

    @NonNull
    @Override
    public SectionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.attraction_collection, parent, false);
        return new SectionViewHolder(view);
    }


    @SuppressLint("WrongConstant")
    @Override
    public void onBindViewHolder(@NonNull SectionViewHolder holder, final int position) {
        final AttractionCollection currentCollection = mData.get(position);
        final int sectionHeader = mData.get(position).getHeaderTitle();

        final List<Attraction> attractions = currentCollection.getAttractions();

        SingleAttractionDataAdapter singleAttractionAdapter = new SingleAttractionDataAdapter(
                mContext, attractions);

        holder.sectionTitle.setText(sectionHeader);

        switch (position) {
            case 0:
                int topActivitiesColor =
                        ContextCompat.getColor(mContext, R.color.gray_color);
                holder.sectionTitle.setTextColor(topActivitiesColor);
                holder.showAllClickable.setTextColor(topActivitiesColor);
                break;

            case 1:
                int topRestaurantsColor =
                        ContextCompat.getColor(mContext, R.color.gray_color);
                holder.sectionTitle.setTextColor(topRestaurantsColor);
                holder.showAllClickable.setTextColor(topRestaurantsColor);
                break;

            case 2:
                int topBreweriesColor =
                        ContextCompat.getColor(mContext, R.color.gray_color);
                holder.sectionTitle.setTextColor(topBreweriesColor);
                holder.showAllClickable.setTextColor(topBreweriesColor);
                break;

            case 3:
                int topBarsColor =
                        ContextCompat.getColor(mContext, R.color.gray_color);
                holder.sectionTitle.setTextColor(topBarsColor);
                holder.showAllClickable.setTextColor(topBarsColor);
                break;
            default:
                break;
        }

        // Set fixed size true and optimize recycler view performance
        // The data container has fixed number of attractions and is not streaming from a web server
        holder.sectionRecyclerView.setHasFixedSize(true);

        // Set layout for the attractions collection data using a layout manager
        holder.sectionRecyclerView.setLayoutManager(new LinearLayoutManager(mContext,
                LinearLayout.HORIZONTAL, false));

        // Connect attraction collection recycler view widget to corresponding data adapter
        holder.sectionRecyclerView.setAdapter(singleAttractionAdapter);
    }


    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    static class SectionViewHolder extends RecyclerView.ViewHolder {

        TextView sectionTitle;
        RecyclerView sectionRecyclerView;
        Button showAllClickable;
        final Context context;


        SectionViewHolder(View itemView) {
            super(itemView);
            context = itemView.getContext();

            sectionTitle = itemView.findViewById(R.id.section_title_tv);
            sectionRecyclerView = itemView.findViewById(R.id.section_collection_rv);
            showAllClickable = itemView.findViewById(R.id.show_all_button);

            // Setup listener to receive "show all" navigation requests by the user
            showAllClickable.setOnClickListener(v -> {
                // Declare intent to navigate to the correct activity requested by the user
                int sectionResId = Utils.resolveStringToResId(context, sectionTitle.getText());
                if (sectionResId == 0) {
                    throw new IllegalArgumentException("Unexpected section header value " +
                            "received from ShowAll click listener");
                }
                Intent passToActivity = AttractionListActivity
                        .newIntent(context, Utils.resolveStringToResId(context, sectionTitle.getText()));
                context.startActivity(passToActivity);
            });
        }
    }
}
