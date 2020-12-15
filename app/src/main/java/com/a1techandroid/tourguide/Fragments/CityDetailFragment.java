package com.a1techandroid.tourguide.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.a1techandroid.tourguide.Adapter.GridCityAdapter;
import com.a1techandroid.tourguide.Models.CityModel;
import com.a1techandroid.tourguide.R;

import java.util.ArrayList;

public class CityDetailFragment extends Fragment {
    ImageView image;
    TextView name, detail;
    CityModel cityModel;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.city_detail, container, false);
//        initViews(view);
        cityModel= (CityModel) getArguments().getSerializable("myIDKey");
       image = view.findViewById(R.id.image);
       name = view.findViewById(R.id.cityName);
       detail = view.findViewById(R.id.detail);

       image.setImageDrawable(cityModel.getImg());

       name.setText(cityModel.getName());
       detail.setText(cityModel.getDetail());
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}

