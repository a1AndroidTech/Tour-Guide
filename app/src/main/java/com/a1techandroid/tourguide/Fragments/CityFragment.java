package com.a1techandroid.tourguide.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.a1techandroid.tourguide.Adapter.GridCityAdapter;
import com.a1techandroid.tourguide.Models.CityModel;
import com.a1techandroid.tourguide.R;

import java.util.ArrayList;

public class CityFragment extends Fragment {
    GridView listView;
    GridCityAdapter gridCityAdapter;
    ArrayList<CityModel> list = new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.city_fragment, container, false);
//        initViews(view);
        listView = view.findViewById(R.id.listView);

        list.add(new CityModel("Lahore", getResources().getDrawable(R.drawable.lahore), getResources().getString(R.string.lahore)));
        list.add(new CityModel("Karachi", getResources().getDrawable(R.drawable.karachi), getResources().getString(R.string.karachi)));
        list.add(new CityModel("Multan", getResources().getDrawable(R.drawable.multan), getResources().getString(R.string.multan)));
        list.add(new CityModel("Attock", getResources().getDrawable(R.drawable.attock), getResources().getString(R.string.attock)));
        list.add(new CityModel("Islamabad", getResources().getDrawable(R.drawable.islamabad), getResources().getString(R.string.islamabad)));
        list.add(new CityModel("Peshawar", getResources().getDrawable(R.drawable.pesh), getResources().getString(R.string.peshawar)));
        list.add(new CityModel("Queta", getResources().getDrawable(R.drawable.queta), getResources().getString(R.string.queta)));
        list.add(new CityModel("Gilgit", getResources().getDrawable(R.drawable.gilgit), getResources().getString(R.string.gilgit)));
        list.add(new CityModel("Abbotabad", getResources().getDrawable(R.drawable.abtabad), getResources().getString(R.string.abbotabad)));
        list.add(new CityModel("Swaat", getResources().getDrawable(R.drawable.swat), getResources().getString(R.string.swaat)));
        list.add(new CityModel("Sialkot", getResources().getDrawable(R.drawable.sialkot), getResources().getString(R.string.sialkot)));

        gridCityAdapter = new GridCityAdapter(getActivity(), list);
        listView.setAdapter(gridCityAdapter);
        gridCityAdapter.notifyDataSetChanged();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}
