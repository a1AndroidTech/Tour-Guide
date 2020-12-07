package com.a1techandroid.tourguide.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.a1techandroid.tourguide.Adapter.PlaneAdapter;
import com.a1techandroid.tourguide.Models.PlaneModel;
import com.a1techandroid.tourguide.R;

import java.util.ArrayList;

public class FragmentAirPlane extends Fragment {
    ListView listView;
    PlaneAdapter planeAdapter;
    ArrayList<PlaneModel> list = new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_airplane, container, false);
        initViews(view);
        initSEt();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    public void initViews(View view){
        listView=view.findViewById(R.id.listView);
    }

    public void initSEt(){
        list.add(new PlaneModel("Islamabad","Karachi","11,806","22,300"));
        list.add(new PlaneModel("Islamabad","Peshawar","10,400","20,100"));
        list.add(new PlaneModel("Islamabad","Queta","15,000","25,300"));
        list.add(new PlaneModel("Islamabad","Sialkot","10,806","N/A"));
        list.add(new PlaneModel("Islamabad","Gilgit","15,806","28,300"));
        list.add(new PlaneModel("Islamabad","Multan","9,806","16,300"));
        list.add(new PlaneModel("Islamabad","Gawadar","10,806","16,300"));
        list.add(new PlaneModel("Islamabad","Lahore","7,806","13,300"));
        list.add(new PlaneModel("Lahore","Karachi","11,806","22,300"));
        list.add(new PlaneModel("Lahore","Peshawar","15,806","20,300"));
        list.add(new PlaneModel("Lahore","Queta","10,806","22,300"));
        list.add(new PlaneModel("Lahore","Gilgit","13,806","22,300"));
        list.add(new PlaneModel("Lahore","Multan","5,806","13,300"));

        planeAdapter = new PlaneAdapter(getActivity(), list);
        listView.setAdapter(planeAdapter);
        planeAdapter.notifyDataSetChanged();
    }
}
