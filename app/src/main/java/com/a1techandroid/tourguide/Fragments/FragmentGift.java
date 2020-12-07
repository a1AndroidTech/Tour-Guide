package com.a1techandroid.tourguide.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.a1techandroid.tourguide.Adapter.GiftAdapter;
import com.a1techandroid.tourguide.Models.GiftModel;
import com.a1techandroid.tourguide.Models.HotelModel;
import com.a1techandroid.tourguide.R;

import java.util.ArrayList;

public class FragmentGift extends Fragment {
ListView listView;
    GiftAdapter giftAdapter;

    ArrayList<GiftModel> list = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_airplane, container, false);
        initViews(view);
        setUp();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }
    public void initViews(View view){
        listView=view.findViewById(R.id.listView);
    }

    public void setUp(){
        list.add( new GiftModel("Hotel One Mall Road Murree","Jawa Building, GPO Chowk The Mall","30% Off",getResources().getDrawable(R.drawable.bell_icon)));
        list.add( new GiftModel("HOTEL FARAN MURREE","Murree","15% off",getResources().getDrawable(R.drawable.bell_icon)));
        list.add( new GiftModel("ROYAL INN HOTEL PATRIATA","Swaat","20% off",getResources().getDrawable(R.drawable.bell_icon)));
        list.add( new GiftModel("HOTEL MOVE N PICK MURREE","MURREE","25% Off",getResources().getDrawable(R.drawable.bell_icon)));
        list.add( new GiftModel("PEARL CONTINENTAL HOTEL","MURREE","10% Off",getResources().getDrawable(R.drawable.bell_icon)));
        list.add( new GiftModel("HOTEL METROPOLE MURREE","MURREE","12% Off",getResources().getDrawable(R.drawable.bell_icon)));
        list.add( new GiftModel("HOTEL MOVE","Shangrila","5% Off",getResources().getDrawable(R.drawable.bell_icon)));
        list.add( new GiftModel("OASIS MURREE","MURREE","10% off",getResources().getDrawable(R.drawable.bell_icon)));
        list.add( new GiftModel("The Smart Hotel 3 stars","MURREE","30% Off",getResources().getDrawable(R.drawable.bell_icon)));
        list.add( new GiftModel("otel One Bhurban 3 stars","MURREE","20% Off",getResources().getDrawable(R.drawable.bell_icon)));

        giftAdapter= new GiftAdapter(getActivity(), list);
        listView.setAdapter(giftAdapter);
        giftAdapter.notifyDataSetChanged();
          }
}
