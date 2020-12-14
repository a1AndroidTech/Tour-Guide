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

import com.a1techandroid.tourguide.Adapter.HistoryAdapter;
import com.a1techandroid.tourguide.Adapter.HotelingAdapter;
import com.a1techandroid.tourguide.Models.HistotyModel;
import com.a1techandroid.tourguide.Models.HotelModel;
import com.a1techandroid.tourguide.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class HistoryFragment extends Fragment {
    ListView listView;
    HistoryAdapter adapter;
    ArrayList<HistotyModel> list=new ArrayList<>();
    DatabaseReference reference;
    FirebaseDatabase rootNode;
    HistotyModel hotelModel;
    private ProgressDialog mProgressDialog;

    public static HistoryFragment newInstance() {
        HistoryFragment fragment = new HistoryFragment();
        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.history_fragment, container, false);
        rootNode=FirebaseDatabase.getInstance();
        reference=rootNode.getReference("History");
        mProgressDialog= new ProgressDialog(getActivity());
        initViews(view);
        readValueFromFireBase();
        return view;
    }

    private void initViews(View view) {
        listView=view.findViewById(R.id.listView);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    public void readValueFromFireBase(){
        mProgressDialog.setMessage("Fetching Data");
        mProgressDialog.show();

        reference.orderByChild("email").equalTo(FirebaseAuth.getInstance().getCurrentUser().getEmail().replace(".","")).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot datas: dataSnapshot.getChildren()){
                    HistotyModel uni_model=datas.getValue(HistotyModel.class);
//                officers.setUid(snapshot.getKey());
                list.add(uni_model);
                adapter= new HistoryAdapter(getActivity(), list);
                listView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                mProgressDialog.hide();
                }
                mProgressDialog.dismiss();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getActivity(), "data not found", Toast.LENGTH_SHORT).show();
                mProgressDialog.hide();
            }
        });
//        reference.orderByChild("email").addChildEventListener(new ChildEventListener() {
//            @Override
//            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
//                HistotyModel uni_model=snapshot.getValue(HistotyModel.class);
////                officers.setUid(snapshot.getKey());
//                list.add(uni_model);
//                adapter= new HistoryAdapter(getActivity(), list);
//                listView.setAdapter(adapter);
//                adapter.notifyDataSetChanged();
//                mProgressDialog.hide();
//            }
//
//            @Override
//            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
//
//
//            }
//
//            @Override
//            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
//                HistotyModel officers=snapshot.getValue(HistotyModel.class);
////                officers.setUid(snapshot.getKey());
//                list.remove(officers);
//                adapter= new HistoryAdapter(getActivity(), list);
//                listView.setAdapter(adapter);
//                adapter.notifyDataSetChanged();
//                mProgressDialog.hide();
//
//            }
//
//            @Override
//            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });

    }

}
