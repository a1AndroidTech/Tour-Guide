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

import com.a1techandroid.tourguide.Adapter.GiftAdapter;
import com.a1techandroid.tourguide.Adapter.TrainAdapter;
import com.a1techandroid.tourguide.Models.GiftModel;
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

public class FragmentGift extends Fragment {
ListView listView;
    GiftAdapter giftAdapter;

    ArrayList<GiftModel> list = new ArrayList<>();

    DatabaseReference reference;
    FirebaseDatabase rootNode;
    GiftModel hotelModel;
    private ProgressDialog mProgressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_airplane, container, false);
        rootNode=FirebaseDatabase.getInstance();
        reference=rootNode.getReference("Gifts");
        mProgressDialog= new ProgressDialog(getActivity());
        initViews(view);
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

    public void setUp(){
        list.add( new GiftModel("Hotel One Mall Road Murree","Jawa Building, GPO Chowk The Mall","30% Off"));
        list.add( new GiftModel("HOTEL FARAN MURREE","Murree","15% off"));
        list.add( new GiftModel("ROYAL INN HOTEL PATRIATA","Swaat","20% off"));
        list.add( new GiftModel("HOTEL MOVE N PICK MURREE","MURREE","25% Off"));
        list.add( new GiftModel("PEARL CONTINENTAL HOTEL","MURREE","10% Off"));
        list.add( new GiftModel("HOTEL METROPOLE MURREE","MURREE","12% Off"));
        list.add( new GiftModel("HOTEL MOVE","Shangrila","5% Off"));
        list.add( new GiftModel("OASIS MURREE","MURREE","10% off"));
        list.add( new GiftModel("The Smart Hotel 3 stars","MURREE","30% Off"));
        list.add( new GiftModel("otel One Bhurban 3 stars","MURREE","20% Off"));

        for (int i=0; i<list.size(); i++){
            hotelModel = list.get(i);
            String key = reference.push().getKey();
            reference.child(key)
                    .setValue(hotelModel).addOnCompleteListener(new OnCompleteListener<Void>() {
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
                GiftModel uni_model=snapshot.getValue(GiftModel.class);
//                officers.setUid(snapshot.getKey());
                list.add(uni_model);
                giftAdapter= new GiftAdapter(getActivity(), list);
                listView.setAdapter(giftAdapter);
                giftAdapter.notifyDataSetChanged();
                mProgressDialog.hide();

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {


            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                GiftModel officers=snapshot.getValue(GiftModel.class);
//                officers.setUid(snapshot.getKey());
                list.remove(officers);
                giftAdapter= new GiftAdapter(getActivity(), list);
                listView.setAdapter(giftAdapter);
                giftAdapter.notifyDataSetChanged();
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
