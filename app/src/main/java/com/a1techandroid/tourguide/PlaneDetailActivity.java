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

import com.a1techandroid.tourguide.Models.HistotyModel;
import com.a1techandroid.tourguide.Models.PlaneModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;

import java.util.Date;

public class PlaneDetailActivity extends AppCompatActivity {
    TextView departure, arrival, econmy, business;
    CardView bookNow;
    PlaneModel planeModel;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mRefe, mRefe2;
    private ProgressDialog mProgressDialog;
    HistotyModel histotyModel;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.plane_ticket);


        mDatabase = FirebaseDatabase.getInstance();
        mRefe = mDatabase.getReference("plane_ticket");
        mRefe2 = mDatabase.getReference("History");

        mProgressDialog = new ProgressDialog(this);
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
                mProgressDialog.setMessage("Make Booking");
                mProgressDialog.show();
                mRefe.child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                        .setValue(planeModel).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            String key = mRefe2.push().getKey();
                            histotyModel = new HistotyModel("Plane Tickets", planeModel.getArrival(),"Pending", new Date().toString(), FirebaseAuth.getInstance().getCurrentUser().getEmail().replace(".",""));
                            mRefe2.child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(histotyModel).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    if (task.isSuccessful()) {
                                        Toast.makeText(PlaneDetailActivity.this, "Booking Request Submtted", Toast.LENGTH_SHORT).show();
                                        finish();
                                        mProgressDialog.hide();
                                    } else {
                                        Toast.makeText(getApplicationContext(), "something went wrong", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                        } else {
                            Toast.makeText(PlaneDetailActivity.this, "something went wrong", Toast.LENGTH_SHORT).show();
                            mProgressDialog.hide();
                        }
                    }
                });
            }
        });
    }
}
