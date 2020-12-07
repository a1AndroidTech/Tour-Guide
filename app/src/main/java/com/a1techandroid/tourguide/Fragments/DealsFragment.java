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
        listofItems.add(new Places(getActivity().getDrawable(R.drawable.s7), "Gilgit / Chitral", "GB Province, Pakistan"));
        listofItems.add(new Places(getActivity().getDrawable(R.drawable.s1), "Murree", "Tehsil Rawalpindi, Punjab"));
        listofItems.add(new Places(getActivity().getDrawable(R.drawable.s6), "Swat", "KPK Province, Pakistan"));
        listofItems.add(new Places(getActivity().getDrawable(R.drawable.s3), "Malam Jabba"
                , "KPK Province, Pakistan"));
        listofItems.add(new Places(getActivity().getDrawable(R.drawable.s8), "Faisal Mosque", "Islamabad Capital of Pakistan"));
        listofItems.add(new Places(getActivity().getDrawable(R.drawable.s10), "Lahore Fort  ", "Lahore City, Punjab, Pakistan"));
        listofItems.add(new Places(getActivity().getDrawable(R.drawable.s10), "Badshahi Mosque  ", "Lahore City, Punjab, Pakistan"));
        listofItems.add(new Places(getActivity().getDrawable(R.drawable.s10), "Sheesh Mahal  ", "Lahore City, Punjab, Pakistan"));
        listofItems.add(new Places(getActivity().getDrawable(R.drawable.s10), "Damn e Koh  ", "Islamabad , Pakistan"));
        listofItems.add(new Places(getActivity().getDrawable(R.drawable.s10), "Shalimar bagh  ", "Lahore City, Punjab, Pakistan"));
        listofItems.add(new Places(getActivity().getDrawable(R.drawable.s10), "Minar e Pakistan  ", "Lahore City, Punjab, Pakistan"));
        listofItems.add(new Places(getActivity().getDrawable(R.drawable.s10), "Pakistan Monument  ", "Islamabad, Pakistan"));
        listofItems.add(new Places(getActivity().getDrawable(R.drawable.s10), "Saif ul Maluk Lake  ", "Naran Kaghan , KPK"));
        listofItems.add(new Places(getActivity().getDrawable(R.drawable.s10), "Naran Kaghan  ", " KPK"));
//        listofItems.add(new Places(getActivity().getDrawable(R.drawable.s10), "Saif ul Maluk Lake  ", "Naran Kaghan , KPK"));
//        listofItems.add(new Places(getActivity().getDrawable(R.drawable.s10), "Saif ul Maluk Lake  ", "Naran Kaghan , KPK"));

        settingListAdapter = new PlacesAdapter(getActivity(), listofItems);
        listView.setAdapter(settingListAdapter);
        settingListAdapter.notifyDataSetChanged();
    }

}
