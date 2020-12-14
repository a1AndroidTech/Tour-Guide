package com.a1techandroid.tourguide;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.a1techandroid.tourguide.CustomClasses.Commons;
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
    TextView departure, arrival, econmy, business, date;
    CardView bookNow;
    HotelModel carRentalModel;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mRefe;
    private DatabaseReference mRefe2;
    private ProgressDialog mProgressDialog;
    HistotyModel histotyModel;

    private int mYear, mMonth, mDay, mHour, mMinute;

    String Date;
    String DateTime;
    DatePickerDialog datePickerDialog;
    EditText Notification, Description;

    Calendar calendar_new;

    int day_new, month_new, year_new;
    Calendar calendar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.car_rental);
        mDatabase = FirebaseDatabase.getInstance();
        mRefe = mDatabase.getReference("hotel_booking");
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
        date=findViewById(R.id.date);
    }


    public void setUpValues(HotelModel model){
        departure.setText(model.getDestination());
        arrival.setText(model.getArrival());
        econmy.setText(model.getEco());
    }

    public void setUPClicks(){

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dateTimeDialog();
            }
        });

        bookNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mProgressDialog.setMessage("Make Booking");
                mProgressDialog.show();
                mRefe.child(FirebaseAuth.getInstance().getCurrentUser().getEmail().replace(".",""))
                        .setValue(carRentalModel).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            String key = mRefe2.push().getKey();
                            histotyModel = new HistotyModel("Hotel Ticket", carRentalModel.getArrival(),"Pending", date.getText().toString(), FirebaseAuth.getInstance().getCurrentUser().getEmail().replace(".",""));
                            mRefe2.child(mRefe2.push().getKey())
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

    public void dateTimeDialog() {

        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);


        datePickerDialog = new DatePickerDialog(HotelDetail.this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {

//                            txtDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                        Date = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;

                        String test = "" + datePickerDialog.getDatePicker().getYear() + "-" + (datePickerDialog.getDatePicker().getMonth() + 1);
                        day_new = datePickerDialog.getDatePicker().getDayOfMonth();
                        month_new = datePickerDialog.getDatePicker().getMonth();
                        year_new = datePickerDialog.getDatePicker().getYear();

                        calendar = Calendar.getInstance();
                        calendar.set(year_new, month_new, day_new);
                        Log.i("okkk1111", "" + calendar.getTime());

                        final Calendar c1 = Calendar.getInstance();
                        mHour = c1.get(Calendar.HOUR_OF_DAY);
                        mMinute = c1.get(Calendar.MINUTE);
                        date.setText(""+ Commons.SimpleGMTTimeFormat(calendar.getTime().toString()));
                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();


    }

}
