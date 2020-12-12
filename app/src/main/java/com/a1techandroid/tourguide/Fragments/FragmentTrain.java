package com.a1techandroid.tourguide.Fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.a1techandroid.tourguide.Adapter.HotelingAdapter;
import com.a1techandroid.tourguide.Adapter.TrainAdapter;
import com.a1techandroid.tourguide.Models.HotelModel;
import com.a1techandroid.tourguide.Models.TrainModel;
import com.a1techandroid.tourguide.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class FragmentTrain extends Fragment {
ListView listView;
    TrainModel trainModel;
    ArrayList<TrainModel> list= new ArrayList<>();
    DatabaseReference reference;
    FirebaseDatabase rootNode;
    HotelModel hotelModel;
    private ProgressDialog mProgressDialog;
    TrainAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_airplane, container, false);
        rootNode=FirebaseDatabase.getInstance();
        reference=rootNode.getReference("Train");
        mProgressDialog= new ProgressDialog(getActivity());
        initViews(view);
//        addUni();
        readValueFromFireBase();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    public void initViews(View view){
        listView=view.findViewById(R.id.listView);
    }

    public void addUni(){
        list.add(new TrainModel("Akbar Express","Quetta – Lahore Junction "));
        list.add(new TrainModel("Allama Iqbal Express","Karachi City – Sialkot Junction"));
        list.add(new TrainModel("Attock Passenger","Mari Indus – Attock City Junction"));
        list.add(new TrainModel("Awam Express","Karachi City – Peshawar Cantonment"));
        list.add(new TrainModel("Abaseen Express","Multan Cantonment - Quetta"));
        list.add(new TrainModel("Amruka Mixed","Samasata Junction - Amruka"));
        list.add(new TrainModel("Babu Passenger","Lahore Junction – Wazirabad Junction"));
        list.add(new TrainModel("Badin Express","Hyderabad Junction – Badin"));
        list.add(new TrainModel("Bahauddin Zakaria Express","Karachi City – Multan Cantonment"));
        list.add(new TrainModel("Balochistan Express","Quetta -- Karachi City"));
        list.add(new TrainModel("Jaffar Express","Peshawar – Quetta"));
        list.add(new TrainModel("Badar Express","Lahore Junction – Faisalabad"));
        list.add(new TrainModel("Jinnah Express","Karachi Cantonment -- Rawalpindi"));
        list.add(new TrainModel("Lasani Express","Lahore Junction–Sialkot Junction"));
        list.add(new TrainModel("Lala Musa Express","Lala Musa Junction - Sargodha Junction"));
        for (int i=0; i<list.size(); i++){
            trainModel = list.get(i);
            String key = reference.push().getKey();
            reference.child(key)
                    .setValue(trainModel).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {

                    if (task.isSuccessful()) {
                        Toast.makeText(getActivity(), "submitted successfully", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getActivity(), "something went wrong", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }


    }

    public void readValueFromFireBase(){
        mProgressDialog.setMessage("Fetching Data");
        mProgressDialog.show();
        reference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                TrainModel uni_model=snapshot.getValue(TrainModel.class);
//                officers.setUid(snapshot.getKey());
                list.add(uni_model);
                adapter= new TrainAdapter(getActivity(), list);
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
                adapter= new TrainAdapter(getActivity(), list);
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
