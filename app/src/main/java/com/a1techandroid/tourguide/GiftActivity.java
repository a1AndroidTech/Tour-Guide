package com.a1techandroid.tourguide;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.a1techandroid.tourguide.Models.CarRentalModel;
import com.a1techandroid.tourguide.Models.GiftModel;
import com.google.gson.Gson;

public class GiftActivity extends AppCompatActivity {
    TextView departure, arrival, econmy, business;
    CardView bookNow;
    GiftModel carRentalModel;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.car_rental);
        Gson gson = new Gson();
        carRentalModel = gson.fromJson(getIntent().getStringExtra("model"), GiftModel.class);
        initView();
        setUPClicks();
        setUpValues(carRentalModel);
    }

    public void initView(){
        departure=findViewById(R.id.departure);
        arrival=findViewById(R.id.arrival);
        econmy=findViewById(R.id.econmy);
        business=findViewById(R.id.business);
        bookNow=findViewById(R.id.bookNow);
    }


    public void setUpValues(GiftModel model){
        departure.setText(model.getDestination());
        econmy.setText(model.getArrival());
        arrival.setText(model.getEco());
    }

    public void setUPClicks(){
        bookNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
