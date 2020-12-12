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
import com.a1techandroid.tourguide.Models.TrainModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;

import java.util.Date;

public class TrainDetailActivity extends AppCompatActivity {
    TextView arrival,departure;
    TrainModel model;
    CardView bookNow;

    private FirebaseDatabase mDatabase;
    private DatabaseReference mRefe, mRefe2;
    private ProgressDialog mProgressDialog;
    HistotyModel histotyModel;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.train_activity);

        mDatabase = FirebaseDatabase.getInstance();
        mRefe = mDatabase.getReference("Train_Ticket");
        mRefe2 = mDatabase.getReference("History");

        mProgressDialog = new ProgressDialog(this);

        Gson gson = new Gson();
        model = gson.fromJson(getIntent().getStringExtra("model"), TrainModel.class);

        departure=findViewById(R.id.departure);
        arrival=findViewById(R.id.arrival);
        bookNow=findViewById(R.id.bookNow);

        departure.setText(model.getEndPoints());
        arrival.setText(model.getTrainName());

        bookNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mProgressDialog.setMessage("Make Booking");
                mProgressDialog.show();
                mRefe.child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                        .setValue(model).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            String key = mRefe2.push().getKey();
                            histotyModel = new HistotyModel("Plane Tickets", model.getTrainName(),"Pending", new Date().toString(), FirebaseAuth.getInstance().getCurrentUser().getEmail().replace(".",""));
                            mRefe2.child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(histotyModel).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    if (task.isSuccessful()) {
                                        Toast.makeText(TrainDetailActivity.this, "Booking Request Submitted", Toast.LENGTH_SHORT).show();
                                        finish();
                                        mProgressDialog.hide();
                                    } else {
                                        Toast.makeText(getApplicationContext(), "something went wrong", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                        } else {
                            Toast.makeText(TrainDetailActivity.this, "something went wrong", Toast.LENGTH_SHORT).show();
                            mProgressDialog.hide();
                        }
                    }
                });
            }
        });
    }
}
