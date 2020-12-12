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

import com.a1techandroid.tourguide.Models.CarRentalModel;
import com.a1techandroid.tourguide.Models.HistotyModel;
import com.a1techandroid.tourguide.Models.PlaneModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;

import java.util.Date;

public class CarRentalActivity extends AppCompatActivity {
    TextView departure, arrival, econmy, business;
    CardView bookNow;
    CarRentalModel carRentalModel;
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
        mRefe = mDatabase.getReference("Car_Rental");
        mRefe2 = mDatabase.getReference("History");

        mProgressDialog = new ProgressDialog(CarRentalActivity.this);
        Gson gson = new Gson();
        carRentalModel = gson.fromJson(getIntent().getStringExtra("model"), CarRentalModel.class);
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


    public void setUpValues(CarRentalModel model){
        departure.setText(model.getDestination());
        arrival.setText(model.getArrival());
        econmy.setText(model.getEco());
        business.setText(model.getGold());
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
                            mRefe.child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(carRentalModel).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        String key = mRefe2.push().getKey();
                                        histotyModel = new HistotyModel("Car/ Bus Ticket", carRentalModel.getArrival(),"Pending", new Date().toString(), FirebaseAuth.getInstance().getCurrentUser().getEmail().replace(".",""));
                                        mRefe2.child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                                .setValue(histotyModel).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {

                                                if (task.isSuccessful()) {
                                                    Toast.makeText(CarRentalActivity.this, "Booking Request Submtted", Toast.LENGTH_SHORT).show();
                                                    finish();
                                                    mProgressDialog.hide();
                                                } else {
                                                    Toast.makeText(getApplicationContext(), "something went wrong", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });



                                    } else {
                                        Toast.makeText(CarRentalActivity.this, "something went wrong", Toast.LENGTH_SHORT).show();
                                        mProgressDialog.hide();
                                    }
                                }
                            });


                        } else {
                            Toast.makeText(CarRentalActivity.this, "something went wrong", Toast.LENGTH_SHORT).show();
                            mProgressDialog.hide();
                        }
                    }
                });
            }
        });
    }


}
