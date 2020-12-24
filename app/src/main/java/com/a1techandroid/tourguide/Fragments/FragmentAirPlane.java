package com.a1techandroid.tourguide.Fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.a1techandroid.tourguide.Adapter.HotelingAdapter;
import com.a1techandroid.tourguide.Adapter.PlaneAdapter;
import com.a1techandroid.tourguide.Models.HotelModel;
import com.a1techandroid.tourguide.Models.PlaneModel;
import com.a1techandroid.tourguide.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FragmentAirPlane extends Fragment {
    ListView listView;
    PlaneAdapter planeAdapter;
    ArrayList<PlaneModel> list = new ArrayList<>();
    PlaneModel planeModel;
    DatabaseReference reference;
    FirebaseDatabase rootNode;
    private ProgressDialog mProgressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_airplane, container, false);
        rootNode= FirebaseDatabase.getInstance();
        reference=rootNode.getReference("Planes");
        mProgressDialog= new ProgressDialog(getActivity());
        initViews(view);
//        initSEt();
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

    public void initSEt(){

        addUni();
    }

    public void addUni(){
        for (int i=0; i<list.size(); i++){
            planeModel = list.get(i);
            String key = reference.push().getKey();
            reference.child(key)
                    .setValue(planeModel).addOnCompleteListener(new OnCompleteListener<Void>() {
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

                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    PlaneModel uni_model = snapshot1.getValue(PlaneModel.class);
//                officers.setUid(snapshot.getKey());
                    list.add(uni_model);
                    planeAdapter = new PlaneAdapter(getActivity(), list);
                    listView.setAdapter(planeAdapter);
                    planeAdapter.notifyDataSetChanged();
                    mProgressDialog.hide();
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {


            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                PlaneModel officers=snapshot.getValue(PlaneModel.class);
//                officers.setUid(snapshot.getKey());
                list.remove(officers);
                planeAdapter = new PlaneAdapter(getActivity(), list);
                listView.setAdapter(planeAdapter);
                planeAdapter.notifyDataSetChanged();
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
