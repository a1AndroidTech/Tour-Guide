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

        list.add(new CityModel("Lahore", getResources().getDrawable(R.drawable.lahore), "Lahore is the capital of the Pakistani province of Punjab and is the country's 2nd largest city after Karachi, as well as the 26th largest city in the world."));
        list.add(new CityModel("Karachi", getResources().getDrawable(R.drawable.karachi), "Karachi is the capital of the Pakistani province of Sindh. It is the largest city in Pakistan and the twelfth largest city in the world. Ranked as a beta-global city, the city is Pakistan's premier industrial and financial center"));
        list.add(new CityModel("Multan", getResources().getDrawable(R.drawable.multan), "Multan is a city and capital of Multan Division located in Punjab, Pakistan. Located on the bank of the Chenab River, Multan is Pakistan's 7th largest city and is the major cultural and economic centre of southern Punjab"));
        list.add(new CityModel("Attock", getResources().getDrawable(R.drawable.attock), "Attock is a place of great historic significance. Emperor Akbar the Great, the grandson of Babar, recognized the strategic importance of this area in 1581 and built the famous Attock Fort Complex"));
        list.add(new CityModel("Islamabad", getResources().getDrawable(R.drawable.islamabad), "The city was built in 1960 to replace Karachi as the Pakistani capital, which it has been since 1963. Due to Islamabad's proximity to Rawalpindi, they are considered sister cities"));
        list.add(new CityModel("Peshawar", getResources().getDrawable(R.drawable.pesh), "Peshawar was founded on the Gandhara plains in the broad Valley of Peshawar. The city likely first existed as a small village in the 5th century BCE, within the cultural sphere of eastern ancient Persia."));
        list.add(new CityModel("Queta", getResources().getDrawable(R.drawable.queta), "The first detailed account of Quetta is from the 11th century when it was captured by Sultan Mahmud Ghaznavi during one of his invasions of South Asia. In 1543, the Mughal "));

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
