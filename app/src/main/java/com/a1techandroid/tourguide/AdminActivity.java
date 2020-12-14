package com.a1techandroid.tourguide;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.a1techandroid.tourguide.Adapter.CarRentalAdapter;
import com.a1techandroid.tourguide.Adapter.HistoryAdapter;
import com.a1techandroid.tourguide.Adapter.HotelingAdapter;
import com.a1techandroid.tourguide.Adapter.UserAdapter;
import com.a1techandroid.tourguide.Models.CarRentalModel;
import com.a1techandroid.tourguide.Models.HistotyModel;
import com.a1techandroid.tourguide.Models.HotelModel;
import com.a1techandroid.tourguide.Models.UserModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class AdminActivity extends AppCompatActivity {
    ListView listView;
    TextView logout;
    Button Users, Hotels, Tickts, History;

    DatabaseReference reference;
    DatabaseReference reference1;
    DatabaseReference reference2;
    DatabaseReference reference3;
    FirebaseDatabase rootNode;
    UserAdapter userAdapter;
    HistoryAdapter historyAdapter;
    ArrayList<UserModel> listofItems;
    ArrayList<HistotyModel> listofItems1;
    ArrayList<UserModel> listofItems2;
    private ProgressDialog mProgressDialog;
    HotelingAdapter adapter;
    ArrayList<HotelModel> list=new ArrayList<>();
    CarRentalAdapter carRentalAdapter;
    ArrayList<CarRentalModel> listCar= new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_activity);
        rootNode=FirebaseDatabase.getInstance();
        reference=rootNode.getReference("Users");
        reference1=rootNode.getReference("History");
        reference2=rootNode.getReference("Hotels");
        reference3=rootNode.getReference("Car_Rental");
        mProgressDialog = new ProgressDialog(AdminActivity.this);
        listView=findViewById(R.id.listView);
        logout=findViewById(R.id.logout);
        Users=findViewById(R.id.users);
        Hotels=findViewById(R.id.Hotels);
        Tickts=findViewById(R.id.Tickets);
        History=findViewById(R.id.history);
        readValueFromFireBase();


        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(AdminActivity.this, LoginActivity.class));
            }
        });

        Users.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                readValueFromFireBase();
            }
        });
        History.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                readValueFromFireBase1();
            }
        });

        Hotels.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                readValueFromFireBase5();
            }
        });

        Tickts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                readValueFromFireBase2();
            }
        });
    }

    public void readValueFromFireBase(){
        mProgressDialog.setMessage("Getting");
        mProgressDialog.show();
        listofItems = new ArrayList<>();
        reference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                UserModel uni_model=snapshot.getValue(UserModel.class);
//                officers.setUid(snapshot.getKey());
                listofItems.add(uni_model);
                userAdapter = new UserAdapter(getApplicationContext(), listofItems);
                listView.setAdapter(userAdapter);
                userAdapter.notifyDataSetChanged();
                mProgressDialog.hide();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {


            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                UserModel officers=snapshot.getValue(UserModel.class);
//                officers.setUid(snapshot.getKey());
                listofItems.remove(officers);
                userAdapter = new UserAdapter(getApplicationContext(), listofItems);
                listView.setAdapter(userAdapter);
                userAdapter.notifyDataSetChanged();
                mProgressDialog.hide();
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


    public void readValueFromFireBase1(){
        mProgressDialog.setMessage("Getting");
        mProgressDialog.show();
        listofItems1 = new ArrayList<>();
        reference1.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                HistotyModel uni_model=snapshot.getValue(HistotyModel.class);
//                officers.setUid(snapshot.getKey());
                listofItems1.add(uni_model);
                historyAdapter = new HistoryAdapter(getApplicationContext(), listofItems1);
                listView.setAdapter(historyAdapter);
                historyAdapter.notifyDataSetChanged();
                mProgressDialog.hide();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {


            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                HistotyModel uni_model=snapshot.getValue(HistotyModel.class);
//                officers.setUid(snapshot.getKey());
                listofItems1.add(uni_model);
                historyAdapter = new HistoryAdapter(getApplicationContext(), listofItems1);
                listView.setAdapter(historyAdapter);
                historyAdapter.notifyDataSetChanged();
                mProgressDialog.hide();
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public void readValueFromFireBase2(){
        mProgressDialog.setMessage("Getting");
        mProgressDialog.show();
        listofItems1 = new ArrayList<>();
        reference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                CarRentalModel uni_model=snapshot.getValue(CarRentalModel.class);
//                officers.setUid(snapshot.getKey());
                listCar.add(uni_model);
                carRentalAdapter = new CarRentalAdapter(getApplicationContext(), listCar);
                listView.setAdapter(carRentalAdapter);
                carRentalAdapter.notifyDataSetChanged();
                mProgressDialog.hide();

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {


            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                CarRentalModel officers=snapshot.getValue(CarRentalModel.class);
//                officers.setUid(snapshot.getKey());
                listCar.remove(officers);
                carRentalAdapter = new CarRentalAdapter(getApplicationContext(), listCar);
                listView.setAdapter(carRentalAdapter);
                carRentalAdapter.notifyDataSetChanged();
                mProgressDialog.hide();

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


    public void readValueFromFireBase3(){
        mProgressDialog.setMessage("Getting");
        mProgressDialog.show();
        listofItems1 = new ArrayList<>();
        reference1.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                HistotyModel uni_model=snapshot.getValue(HistotyModel.class);
//                officers.setUid(snapshot.getKey());
                listofItems1.add(uni_model);
                historyAdapter = new HistoryAdapter(getApplicationContext(), listofItems1);
                listView.setAdapter(historyAdapter);
                historyAdapter.notifyDataSetChanged();
                mProgressDialog.hide();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {


            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                HistotyModel uni_model=snapshot.getValue(HistotyModel.class);
//                officers.setUid(snapshot.getKey());
                listofItems1.add(uni_model);
                historyAdapter = new HistoryAdapter(getApplicationContext(), listofItems1);
                listView.setAdapter(historyAdapter);
                historyAdapter.notifyDataSetChanged();
                mProgressDialog.hide();
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public void readValueFromFireBase5(){
        mProgressDialog.setMessage("Fetching Data");
        mProgressDialog.show();
        reference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                HotelModel uni_model=snapshot.getValue(HotelModel.class);
//                officers.setUid(snapshot.getKey());
                list.add(uni_model);
                adapter= new HotelingAdapter(getApplicationContext(), list);
                listView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                mProgressDialog.hide();

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {


            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                HotelModel officers=snapshot.getValue(HotelModel.class);
//                officers.setUid(snapshot.getKey());
                list.remove(officers);
                adapter= new HotelingAdapter(getApplicationContext(), list);
                listView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                mProgressDialog.hide();

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }



}
