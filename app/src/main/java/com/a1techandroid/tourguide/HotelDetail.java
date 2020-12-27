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
import com.a1techandroid.tourguide.CustomClasses.Prefrences;
import com.a1techandroid.tourguide.Models.Booking;
import com.a1techandroid.tourguide.Models.BookingHotel;
import com.a1techandroid.tourguide.Models.GiftModel;
import com.a1techandroid.tourguide.Models.HistotyModel;
import com.a1techandroid.tourguide.Models.HotelModel;
import com.a1techandroid.tourguide.Models.NotificationModel;
import com.a1techandroid.tourguide.Models.UserModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;

import java.util.Calendar;
import java.util.Date;

public class HotelDetail extends AppCompatActivity {
    TextView departure, arrival, econmy, business, date, userName, userPhone;
    CardView bookNow;
    HotelModel carRentalModel;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mRefe;
    private DatabaseReference mRefe2;
    private DatabaseReference mRefe3;
    private ProgressDialog mProgressDialog;
    HistotyModel histotyModel;
    BookingHotel booking;
    UserModel userModel;
    EditText numbOfRooms, familyType;

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
        setContentView(R.layout.hotel_booking);
        mDatabase = FirebaseDatabase.getInstance();
        mRefe = mDatabase.getReference("hotel_booking");
        mRefe2 = mDatabase.getReference("History");
        mRefe3 = mDatabase.getReference("Notification");
        mProgressDialog = new ProgressDialog(this);
        userModel= Prefrences.getUser(HotelDetail.this);



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
        userName=findViewById(R.id.userName);
        userPhone=findViewById(R.id.userPhone);
        numbOfRooms=findViewById(R.id.numOfRooms);
        familyType=findViewById(R.id.familyType);
    }


    public void setUpValues(HotelModel model){
        departure.setText(model.getDestination());
        arrival.setText(model.getArrival());
        econmy.setText(model.getEco());
        userName.setText(userModel.getName());
        userPhone.setText(userModel.getPhone());
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

                if (date.getText().equals("Please select date")){
                    Toast.makeText(HotelDetail.this, "Please select date first", Toast.LENGTH_SHORT).show();
                }else {
                    mProgressDialog.setMessage("Make Booking");
                    mProgressDialog.show();
                    booking = new BookingHotel(carRentalModel.getEmail(),"Hoteling", carRentalModel.getArrival(), carRentalModel.getDestination(), date.getText().toString(), "Pending", userModel.getName(), userModel.getPhone(), numbOfRooms.getText().toString(), familyType.getText().toString());
                    mRefe.child(carRentalModel.getEmail()).child(mRefe.push().getKey())
                            .setValue(booking).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                String key = mRefe2.push().getKey();
                                histotyModel = new HistotyModel("Hotel Ticket", carRentalModel.getArrival(), "Pending", date.getText().toString(), FirebaseAuth.getInstance().getCurrentUser().getEmail().replace(".", ""));
                                mRefe2.child(mRefe2.push().getKey())
                                        .setValue(histotyModel).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {

                                        if (task.isSuccessful()) {
                                            NotificationModel notificationModel = new NotificationModel(FirebaseAuth.getInstance().getCurrentUser().getUid(), "pending", "Hotel", carRentalModel.getEmail());
                                            mRefe3.child(mRefe3.push().getKey()).setValue(notificationModel);
                                            Toast.makeText(HotelDetail.this, "Booking Request Submitted", Toast.LENGTH_SHORT).show();
                                            Commons.testMessage(getApplicationContext(), Prefrences.getUser(getApplicationContext()).getName()+" Your Hotel Booking Request Submitted");

                                            finish();
                                            mProgressDialog.hide();
                                        } else {
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
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        datePickerDialog.show();


    }

}
