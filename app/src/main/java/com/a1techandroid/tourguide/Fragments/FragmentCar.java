package com.a1techandroid.tourguide.Fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowId;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.a1techandroid.tourguide.Adapter.CarRentalAdapter;
import com.a1techandroid.tourguide.Adapter.GiftAdapter;
import com.a1techandroid.tourguide.Models.CarRentalModel;
import com.a1techandroid.tourguide.Models.GiftModel;
import com.a1techandroid.tourguide.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class FragmentCar extends Fragment {
    ListView listView;
    CarRentalAdapter carRentalAdapter;
    ArrayList<CarRentalModel> list= new ArrayList<>();
    DatabaseReference reference;
    FirebaseDatabase rootNode;
    CarRentalModel hotelModel;
    private ProgressDialog mProgressDialog;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_airplane, container, false);
        rootNode=FirebaseDatabase.getInstance();
        reference=rootNode.getReference("Car_Rental");
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

    public void initV(){
        list.add(new CarRentalModel("Islamabad","ABBOTTABAD","1410/410","N/A"));
        list.add(new CarRentalModel("Islamabad","AHMED PUR EAST","1200/1200","N/A"));
        list.add(new CarRentalModel("Islamabad","BAHAWALPUR","1000/1000","N/A"));
        list.add(new CarRentalModel("Islamabad","BARIKOT","1480/1480","N/A"));
        list.add(new CarRentalModel("Islamabad","BATKHELA","1430/1430","N/A"));
        list.add(new CarRentalModel("Islamabad","BHAKKAR","1000/1000","N/A"));
        list.add(new CarRentalModel("Islamabad","CHOWK BAHADURPUR","1570/1570","N/A"));
        list.add(new CarRentalModel("Islamabad","DASKA","500/500","N/A"));
        list.add(new CarRentalModel("Islamabad","DERA GHAZI KHAN","1250/1250","N/A"));
        list.add(new CarRentalModel("Islamabad","DERA ISMAIL KHAN","1100","N/A"));
        list.add(new CarRentalModel("Islamabad","DHARKI","N/A","N/A"));
        list.add(new CarRentalModel("Islamabad","FAISALABAD","530/530","N/A"));
        list.add(new CarRentalModel("Islamabad","GUJRANWALA","410","N/A"));
        list.add(new CarRentalModel("Islamabad","GUJRAT","380","N/A"));
        list.add(new CarRentalModel("Islamabad","HARIPUR","1410/1410\t","N/A"));
        list.add(new CarRentalModel("Islamabad","HYDERABAD","3380/3380","N/A"));
        list.add(new CarRentalModel("Islamabad","JAMPUR","1500/1500","N/A"));
        list.add(new CarRentalModel("Islamabad","AMSHORO","3030/N/A","N/A"));
        list.add(new CarRentalModel("Islamabad","JHANG","670/N/A","N/A"));
        list.add(new CarRentalModel("Islamabad","JHELUM","690/N/A","N/A"));
        list.add(new CarRentalModel("Islamabad","JAUHARABAD\t","840/N/A","N/A"));
        list.add(new CarRentalModel("Islamabad","KARACHI","3420/4800","N/A"));
        list.add(new CarRentalModel("Islamabad","KASHMORE","2200/N/A","N/A"));
        list.add(new CarRentalModel("Islamabad","KHAN PUR","1510/1510","N/A"));
        list.add(new CarRentalModel("Islamabad","Lodhran","910/910","N/A"));
        list.add(new CarRentalModel("Islamabad","MARDAN","1210/1160","N/A"));
        list.add(new CarRentalModel("Islamabad","MIANWALI","1000N/A","N/A"));
        list.add(new CarRentalModel("Islamabad","MUZAFARGARH","1050/1100","N/A"));
        list.add(new CarRentalModel("Islamabad","OKARA CITY","390/390","N/A"));


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
                CarRentalModel uni_model=snapshot.getValue(CarRentalModel.class);
//                officers.setUid(snapshot.getKey());
                list.add(uni_model);
                carRentalAdapter = new CarRentalAdapter(getActivity(), list);
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
                list.remove(officers);
                carRentalAdapter = new CarRentalAdapter(getActivity(), list);
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
}
