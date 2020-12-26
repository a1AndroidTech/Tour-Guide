package com.a1techandroid.tourguide;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
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
import com.a1techandroid.tourguide.Models.CarRentalModel;
import com.a1techandroid.tourguide.Models.HistotyModel;
import com.a1techandroid.tourguide.Models.NotificationModel;
import com.a1techandroid.tourguide.Models.PlaneModel;
import com.a1techandroid.tourguide.Models.UserModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;

import java.util.Calendar;
import java.util.Date;

public class CarRentalActivity extends AppCompatActivity {
    TextView departure, arrival, econmy, business, date, userName, userPhone;
    CardView bookNow;
    CarRentalModel carRentalModel;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mRefe;
    private DatabaseReference mRefe2;
    private DatabaseReference mRefe3;
    private ProgressDialog mProgressDialog;
    HistotyModel histotyModel;
    private int mYear, mMonth, mDay, mHour, mMinute;
    EditText numOfRooms, familyType;
    String Date;
    String DateTime;
    DatePickerDialog datePickerDialog;
    EditText Notification, Description;

    Calendar calendar_new;

    int day_new, month_new, year_new;
    Calendar calendar;
    UserModel userModel;

    BookingHotel booking;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.plane_ticket);
        mDatabase = FirebaseDatabase.getInstance();
        mRefe = mDatabase.getReference("car_booking");
        mRefe2 = mDatabase.getReference("History");
        mRefe3 = mDatabase.getReference("Notification");

        userModel = Prefrences.getUser(getApplicationContext());

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
        date=findViewById(R.id.date);
       userName = findViewById(R.id.userName);
       userPhone = findViewById(R.id.userPhone);
       userName.setText(Prefrences.getUser(getApplicationContext()).getName());
       userPhone.setText(Prefrences.getUser(getApplicationContext()).getPhone());

    }


    public void setUpValues(CarRentalModel model){
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
                    Toast.makeText(CarRentalActivity.this, "Please select date first", Toast.LENGTH_SHORT).show();
                }else {
                    mProgressDialog.setMessage("Make Booking");
                    mProgressDialog.show();
                    booking = new BookingHotel(carRentalModel.getEmail(),"Renting", carRentalModel.getArrival(), carRentalModel.getDestination(), date.getText().toString(), "pending", userName.getText().toString(), userPhone.getText().toString(), "", "");
                    mRefe.child(carRentalModel.getEmail()).child(mRefe.push().getKey())
                            .setValue(booking).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {

                                            histotyModel = new HistotyModel("Car/ Bus Ticket", carRentalModel.getArrival(), "Pending",date.getText().toString(), FirebaseAuth.getInstance().getCurrentUser().getEmail().replace(".", ""));
                                            mRefe2.child(mRefe2.push().getKey())
                                                    .setValue(histotyModel).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {

                                                    if (task.isSuccessful()) {
                                                        NotificationModel notificationModel = new NotificationModel(FirebaseAuth.getInstance().getCurrentUser().getUid(), "pending", "Car");
                                                        mRefe3.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(notificationModel);

                                                        Toast.makeText(CarRentalActivity.this, "Booking Request Submtted", Toast.LENGTH_SHORT).show();
                                                        Commons.testMessage(getApplicationContext(), Prefrences.getUser(getApplicationContext()).getName()+" Your Car Booking Request Submitted");
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
                }
            }
        });
    }

    public void dateTimeDialog() {

        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        datePickerDialog = new DatePickerDialog(CarRentalActivity.this,
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
