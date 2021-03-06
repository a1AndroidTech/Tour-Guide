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
import com.a1techandroid.tourguide.Models.GiftModel;
import com.a1techandroid.tourguide.Models.HistotyModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;

import java.util.Date;

public class GiftActivity extends AppCompatActivity {
    TextView departure, arrival, econmy, business;
    CardView bookNow;
    GiftModel carRentalModel;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mRefe;
    private DatabaseReference mRefe2;
    private ProgressDialog mProgressDialog;
    HistotyModel histotyModel;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gift_activity);
        mDatabase = FirebaseDatabase.getInstance();
        mRefe = mDatabase.getReference("Gift_Book");
        mRefe = mDatabase.getReference("History");
        mProgressDialog = new ProgressDialog(this);
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
        econmy.setText(model.getEco());
        arrival.setText(model.getArrival());
        business.setText("Soon");
    }

    public void setUPClicks(){
        bookNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mProgressDialog.setMessage("Getting Discount`   ");
                mProgressDialog.show();
                mRefe.child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                        .setValue(carRentalModel).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            String key = mRefe2.push().getKey();
                            histotyModel = new HistotyModel("Gifts/ Disount", carRentalModel.getArrival(),"Pending", new Date().toString(), FirebaseAuth.getInstance().getCurrentUser().getEmail().replace(".",""));
                            mRefe2.child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(histotyModel).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    if (task.isSuccessful()) {
                                        Toast.makeText(GiftActivity.this, "Booking Request Submtted", Toast.LENGTH_SHORT).show();
                                        finish();
                                        mProgressDialog.hide();
                                    } else {
                                        Toast.makeText(getApplicationContext(), "something went wrong", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                        } else {
                            Toast.makeText(GiftActivity.this, "something went wrong", Toast.LENGTH_SHORT).show();
                            mProgressDialog.hide();
                        }
                    }
                });
            }
        });
    }
}
