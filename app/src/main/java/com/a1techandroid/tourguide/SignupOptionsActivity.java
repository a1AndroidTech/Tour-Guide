package com.a1techandroid.tourguide;

import android.os.Bundle;
import android.widget.GridView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.a1techandroid.tourguide.Adapter.GridViewAdapter;
import com.a1techandroid.tourguide.Models.Places;

import java.util.ArrayList;

public class SignupOptionsActivity extends AppCompatActivity {
    GridView gridView;
    GridViewAdapter gridViewAdapter;
    ArrayList<Places> list;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_option_activity);
        gridView=findViewById(R.id.gridView);
        list = new ArrayList<>();
        list.add(new Places(getResources().getDrawable(R.drawable.travler), "User", ""));
        list.add(new Places(getResources().getDrawable(R.drawable.hotel), "Hotel", ""));
        list.add(new Places(getResources().getDrawable(R.drawable.plane_new2), "Ticket", ""));
        list.add(new Places(getResources().getDrawable(R.drawable.car_enw), "Rents", ""));
        gridViewAdapter = new GridViewAdapter(getApplicationContext(), list);
        gridView.setAdapter(gridViewAdapter);
        gridViewAdapter.notifyDataSetChanged();
    }
}
