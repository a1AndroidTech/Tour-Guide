package com.a1techandroid.tourguide.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.a1techandroid.tourguide.Adapter.HotelingAdapter;
import com.a1techandroid.tourguide.Models.HotelModel;
import com.a1techandroid.tourguide.R;

import java.util.ArrayList;

public class FragmentHoteling extends Fragment {
    ListView listView;
    HotelingAdapter adapter;
    ArrayList<HotelModel> list=new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_airplane, container, false);
        initViews(view);
        initV();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }
    public void initViews(View view){
        listView=view.findViewById(R.id.listView);
    }
public void initV(){
        list.add(new HotelModel("Hotel One Mall Road Murree"," Jawa Building, GPO Chowk The Mall, 41750 Murree", "5 Star", 4));
        list.add(new HotelModel("Hotel One Bhurban 3 stars","Hotel in Murree", "3 star", 3));
        list.add(new HotelModel("The Smart Hotel 3 stars","Murree", "3 Star", 3));
        list.add(new HotelModel("HOTEL METROPOLE MURREE","Murree", "", 0));
        list.add(new HotelModel("OASIS MURREE","Murree", "Murree", 0));
        list.add(new HotelModel("ARCADIAN BLUE PINES RES...PEARL CONTINENTAL HOTEL","Murree", "", 0));
        list.add(new HotelModel("PEARL CONTINENTAL HOTEL","Murree", "Business Class", 4));
        list.add(new HotelModel("HOTEL JAWA INTERNATIONA","Shangrila", "Business Class", 5));
        list.add(new HotelModel("HOTEL MOVE N PICK MURREE","Murree", "Business Class", 5));
        list.add(new HotelModel("ROYAL INN HOTEL PATRIATA","Patriata Murree", "Business Class", 5));
        list.add(new HotelModel("HOTEL ONE MURREE","Murree", "Business Class", 5));
        list.add(new HotelModel("HOTEL FARAN MURREE","Murree", "Business Class", 5));
        list.add(new HotelModel("FIRHILL IMPERIAL LODGES","Swaat", "Business Class", 5));
        list.add(new HotelModel("HOTEL FARAN MURREE","Murree", "Business Class", 5));
        list.add(new HotelModel("BRIGHTLANDS HOTEL ","Swaat", "Business Class", 5));
        list.add(new HotelModel("BEST VIEW HOTEL","Swaaat", "Business Class", 5));


        adapter= new HotelingAdapter(getActivity(), list);
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
}
}
