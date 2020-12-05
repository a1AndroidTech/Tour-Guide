package com.a1techandroid.tourguide.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.a1techandroid.tourguide.Adapter.PlacesAdapter;
import com.a1techandroid.tourguide.Adapter.SettingListAdapter;
import com.a1techandroid.tourguide.Models.Places;
import com.a1techandroid.tourguide.R;

import java.util.ArrayList;

public class DealsFragment extends Fragment {
    ListView listView;
    PlacesAdapter settingListAdapter;
    ArrayList<Places> listofItems;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.deals_fragment, container, false);
        initView(view);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    public void initView(View view){
        listView=view.findViewById(R.id.listView);
        listofItems= new ArrayList<>();
        listofItems.add(new Places(getActivity().getDrawable(R.drawable.s7), "Notre Dame Cathedral", "6 Parvis Notre-Dame - Pl. Jean-Paul II, 75004"));
        listofItems.add(new Places(getActivity().getDrawable(R.drawable.s1), "Louvre Museum", "Rue de Rivoli, 75001"));
        listofItems.add(new Places(getActivity().getDrawable(R.drawable.s6), "Champs Elysées/Arc of Triumph", "Place Charles de Gaulle, 75008"));
        listofItems.add(new Places(getActivity().getDrawable(R.drawable.s3), "Cruise on the Seine ", "75001 Paris, France"));
        listofItems.add(new Places(getActivity().getDrawable(R.drawable.s8), "Montmartre", "75018 Paris, France"));
        listofItems.add(new Places(getActivity().getDrawable(R.drawable.s10), "Palace of Versailles", "Place d\\'Armes, 78000 Versailles"));

        settingListAdapter = new PlacesAdapter(getActivity(), listofItems);
        listView.setAdapter(settingListAdapter);
        settingListAdapter.notifyDataSetChanged();
    }

}
