package com.a1techandroid.tourguide;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.a1techandroid.tourguide.Models.GiftModel;
import com.a1techandroid.tourguide.Models.HistotyModel;
import com.a1techandroid.tourguide.Models.HotelModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;

import java.util.Calendar;
import java.util.Date;

public class HotelDetail extends AppCompatActivity {
    TextView departure, arrival, econmy, business;
    CardView bookNow;
    HotelModel carRentalModel;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mRefe;
    private DatabaseReference mRefe2;
    private ProgressDialog mProgressDialog;
    HistotyModel histotyModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.car_rental);
        mDatabase = FirebaseDatabase.getInstance();
        mRefe = mDatabase.getReference("hoteling");
        mRefe2 = mDatabase.getReference("History");
        mProgressDialog = new ProgressDialog(this);


        Gson gson = new Gson();
        carRentalModel = gson.fromJson(getIntent().getStringExtra("model"), HotelModel.class);
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


    public void setUpValues(HotelModel model){
        departure.setText(model.getDestination());
        arrival.setText(model.getArrival());
        econmy.setText(model.getEco());
    }

    public void setUPClicks(){

        bookNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mProgressDialog.setMessage("Make Booking");
                mProgressDialog.show();
                mRefe.child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                        .setValue(carRentalModel).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            String key = mRefe2.push().getKey();
                            histotyModel = new HistotyModel("Hotel Ticket", carRentalModel.getArrival(),"Pending", new Date().toString(), FirebaseAuth.getInstance().getCurrentUser().getEmail().replace(".",""));
                            mRefe2.child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(histotyModel).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    if (task.isSuccessful()) {
                                        Toast.makeText(HotelDetail.this, "Booking Request Submtted", Toast.LENGTH_SHORT).show();
                                        finish();
                                        mProgressDialog.hide();                                    } else {
                                        Toast.makeText(getApplicationContext(), "something went wrong", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });



                        } else {
                            Toast.makeText(HotelDetail.this, "something went wrong", Toast.LENGTH_SHORT).show();
                            mProgressDialog.hide();
                        }
                    }
                });

            }
        });
    }
}
