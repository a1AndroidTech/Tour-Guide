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
import com.a1techandroid.tourguide.Models.HistotyModel;
import com.a1techandroid.tourguide.Models.NotificationModel;
import com.a1techandroid.tourguide.Models.PlaneModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;

import java.util.Calendar;
import java.util.Date;

public class PlaneDetailActivity extends AppCompatActivity {
    TextView departure, arrival, econmy, business, date, userName, userPhone;
    CardView bookNow;
    PlaneModel planeModel;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mRefe, mRefe2, mRefe3;
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
    BookingHotel booking;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.plane_ticket);


        mDatabase = FirebaseDatabase.getInstance();
        mRefe = mDatabase.getReference("ticket_booking");
        mRefe2 = mDatabase.getReference("History");
        mRefe3 = mDatabase.getReference("Notification");


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
        date=findViewById(R.id.date);
        userName=findViewById(R.id.userName);
        userName.setText(Prefrences.getUser(getApplicationContext()).getName());
        userPhone=findViewById(R.id.userPhone);
        userPhone.setText(Prefrences.getUser(getApplicationContext()).getPhone());
    }

    public void setUpValues(PlaneModel model){
        departure.setText(model.getDestination());
        arrival.setText(model.getArrival());
        econmy.setText(model.getEco());
        business.setText(model.getGold());
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
                    Toast.makeText(PlaneDetailActivity.this, "Please select date first", Toast.LENGTH_SHORT).show();
                }else {
                mProgressDialog.setMessage("Make Booking");
                mProgressDialog.show();
                booking = new BookingHotel(planeModel.getEmail(), "Ticketing",planeModel.getArrival(), planeModel.getDestination(), date.getText().toString(), "pending", Prefrences.getUser(getApplicationContext()).getName(), Prefrences.getUser(getApplicationContext()).getPhone(), "", "");
                mRefe.child(planeModel.getEmail()).child(mRefe.push().getKey())
                        .setValue(booking).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            String key = mRefe2.push().getKey();
                            histotyModel = new HistotyModel("Plane Tickets", planeModel.getArrival(),"Pending", date.getText().toString(), FirebaseAuth.getInstance().getCurrentUser().getEmail().replace(".",""));
                            mRefe2.child(mRefe2.push().getKey())
                                    .setValue(histotyModel).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    if (task.isSuccessful()) {
                                        NotificationModel notificationModel = new NotificationModel(FirebaseAuth.getInstance().getCurrentUser().getUid(), "pending", "Plane", planeModel.getEmail() );
                                        mRefe3.child(mRefe3.push().getKey()).setValue(notificationModel);

                                        Toast.makeText(PlaneDetailActivity.this, "Booking Request Submtted", Toast.LENGTH_SHORT).show();
                                        Commons.testMessage(getApplicationContext(), Prefrences.getUser(getApplicationContext()).getName() + " Your Ticket Booking Request Submitted");

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
            }}
        });
    }

    public void dateTimeDialog() {

        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        datePickerDialog = new DatePickerDialog(PlaneDetailActivity.this,
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
