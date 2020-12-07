package com.a1techandroid.tourguide;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.a1techandroid.tourguide.Models.PlaneModel;
import com.google.gson.Gson;

public class PlaneDetailActivity extends AppCompatActivity {
    TextView departure, arrival, econmy, business;
    CardView bookNow;
    PlaneModel planeModel;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.plane_ticket);
        Gson gson = new Gson();
        planeModel = gson.fromJson(getIntent().getStringExtra("model"), PlaneModel.class);
        initView();
        setUPClicks();
        setUpValues(planeModel);
    }

    public void initView(){
        departure=findViewById(R.id.departure);
        arrival=findViewById(R.id.arrival);
        econmy=findViewById(R.id.econmy);
        business=findViewById(R.id.business);
        bookNow=findViewById(R.id.bookNow);
    }

    public void setUpValues(PlaneModel model){
        departure.setText(model.getDestination());
        arrival.setText(model.getArrival());
        econmy.setText(model.getEco());
        business.setText(model.getGold());
    }

    public void setUPClicks(){
        bookNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
