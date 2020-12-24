package com.a1techandroid.tourguide.Fragments;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowId;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.a1techandroid.tourguide.Adapter.CarRentalAdapter;
import com.a1techandroid.tourguide.Adapter.GiftAdapter;
import com.a1techandroid.tourguide.CustomClasses.Prefrences;
import com.a1techandroid.tourguide.Models.CarRentalModel;
import com.a1techandroid.tourguide.Models.GiftModel;
import com.a1techandroid.tourguide.Models.HotelModel;
import com.a1techandroid.tourguide.Models.UserModel;
import com.a1techandroid.tourguide.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FragmentCar extends Fragment {
    ListView listView;
    CarRentalAdapter carRentalAdapter;
    ArrayList<CarRentalModel> list= new ArrayList<>();
    DatabaseReference reference;
    DatabaseReference reference1;
    FirebaseDatabase rootNode;
    CarRentalModel hotelModel;
    private ProgressDialog mProgressDialog;
    UserModel userModel;
    Dialog dialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_airplane, container, false);
        rootNode=FirebaseDatabase.getInstance();
        reference=rootNode.getReference("Car_Rental");
        reference1 = rootNode.getReference("Users");
        mProgressDialog= new ProgressDialog(getActivity());
        initViews(view);
        userModel = Prefrences.getUser(getActivity());

//        if (userModel.getProfileStatus().equals("pending")){
//            resetPasswordDialog();
//        }else {
//            readValueFromFireBase();
//        }
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

//    public void initV(){
//        list.add(new CarRentalModel("Islamabad","ABBOTTABAD","1410/410","N/A"));
//        list.add(new CarRentalModel("Islamabad","AHMED PUR EAST","1200/1200","N/A"));
//        list.add(new CarRentalModel("Islamabad","BAHAWALPUR","1000/1000","N/A"));
//        list.add(new CarRentalModel("Islamabad","BARIKOT","1480/1480","N/A"));
//        list.add(new CarRentalModel("Islamabad","BATKHELA","1430/1430","N/A"));
//        list.add(new CarRentalModel("Islamabad","BHAKKAR","1000/1000","N/A"));
//        list.add(new CarRentalModel("Islamabad","CHOWK BAHADURPUR","1570/1570","N/A"));
//        list.add(new CarRentalModel("Islamabad","DASKA","500/500","N/A"));
//        list.add(new CarRentalModel("Islamabad","DERA GHAZI KHAN","1250/1250","N/A"));
//        list.add(new CarRentalModel("Islamabad","DERA ISMAIL KHAN","1100","N/A"));
//        list.add(new CarRentalModel("Islamabad","DHARKI","N/A","N/A"));
//        list.add(new CarRentalModel("Islamabad","FAISALABAD","530/530","N/A"));
//        list.add(new CarRentalModel("Islamabad","GUJRANWALA","410","N/A"));
//        list.add(new CarRentalModel("Islamabad","GUJRAT","380","N/A"));
//        list.add(new CarRentalModel("Islamabad","HARIPUR","1410/1410\t","N/A"));
//        list.add(new CarRentalModel("Islamabad","HYDERABAD","3380/3380","N/A"));
//        list.add(new CarRentalModel("Islamabad","JAMPUR","1500/1500","N/A"));
//        list.add(new CarRentalModel("Islamabad","AMSHORO","3030/N/A","N/A"));
//        list.add(new CarRentalModel("Islamabad","JHANG","670/N/A","N/A"));
//        list.add(new CarRentalModel("Islamabad","JHELUM","690/N/A","N/A"));
//        list.add(new CarRentalModel("Islamabad","JAUHARABAD\t","840/N/A","N/A"));
//        list.add(new CarRentalModel("Islamabad","KARACHI","3420/4800","N/A"));
//        list.add(new CarRentalModel("Islamabad","KASHMORE","2200/N/A","N/A"));
//        list.add(new CarRentalModel("Islamabad","KHAN PUR","1510/1510","N/A"));
//        list.add(new CarRentalModel("Islamabad","Lodhran","910/910","N/A"));
//        list.add(new CarRentalModel("Islamabad","MARDAN","1210/1160","N/A"));
//        list.add(new CarRentalModel("Islamabad","MIANWALI","1000N/A","N/A"));
//        list.add(new CarRentalModel("Islamabad","MUZAFARGARH","1050/1100","N/A"));
//        list.add(new CarRentalModel("Islamabad","OKARA CITY","390/390","N/A"));
//
//
//        for (int i=0; i<list.size(); i++){
//            hotelModel = list.get(i);
//            String key = reference.push().getKey();
//            reference.child(key)
//                    .setValue(hotelModel).addOnCompleteListener(new OnCompleteListener<Void>() {
//                @Override
//                public void onComplete(@NonNull Task<Void> task) {
//
//                    if (task.isSuccessful()) {
//                        Toast.makeText(getActivity(), "submitted successfully", Toast.LENGTH_SHORT).show();
//                    } else {
//                        Toast.makeText(getActivity(), "something went wrong", Toast.LENGTH_SHORT).show();
//                    }
//                }
//            });
//        }
//
//
//
//
//    }


    public void readValueFromFireBase(){
        mProgressDialog.setMessage("Fetching Data");
        mProgressDialog.show();
        reference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                for (DataSnapshot snapshot1: snapshot.getChildren()) {
                    CarRentalModel uni_model = snapshot1.getValue(CarRentalModel.class);
//                officers.setUid(snapshot.getKey());
                    list.add(uni_model);
                    carRentalAdapter = new CarRentalAdapter(getActivity(), list);
                    listView.setAdapter(carRentalAdapter);
                    carRentalAdapter.notifyDataSetChanged();
                    mProgressDialog.hide();
                }

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

    public void resetPasswordDialog(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.ticket_dialog);

        final EditText editText = (EditText) dialog.findViewById(R.id.name);
        final EditText editText2 = (EditText) dialog.findViewById(R.id.loc);
        final EditText editText3 = (EditText) dialog.findViewById(R.id.star);
        final EditText editText4 = (EditText) dialog.findViewById(R.id.price);
        Button btnSave  = (Button) dialog.findViewById(R.id.update);

//        btnSave.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                AuthCredential credential = EmailAuthProvider
//                        .getCredential(editText.getText().toString(), editText2.getText().toString());
//                String key = reference.push().getKey();
//                HotelModel hotelModel1 = new HotelModel(editText.getText().toString(), editText2.getText().toString(), editText3.getText().toString(), 0);
//                reference.child(key)
//                        .setValue(hotelModel1).addOnCompleteListener(new OnCompleteListener<Void>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Void> task) {
//
//                        if (task.isSuccessful()) {
//                            updateProfileStatus();
//                        } else {
//                            Toast.makeText(getActivity(), "something went wrong", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                });
//            }
//        });
        final Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.CENTER);
        dialog.show();
    }


    public void updateProfileStatus(){
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        Query applesQuery = reference1.child(FirebaseAuth.getInstance().getCurrentUser().getEmail().replace(".",""));
        applesQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserModel model = snapshot.getValue(UserModel.class);
                Map<String, Object> postValues = new HashMap<String,Object>();
                postValues.put("profileStatus", "Approved");
                model.setProfileStatus("Approved");
                reference1.child(model.getEmail().replace(".","")).updateChildren(postValues);
                Prefrences.saveUSer(model, getActivity());
                Toast.makeText(getActivity(), "updated", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
                readValueFromFireBase();
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
//        applesQuery.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                for (DataSnapshot appleSnapshot: dataSnapshot.getChildren()) {
//                    Map<String, Object> postValues = new HashMap<String,Object>();
//                    String key = appleSnapshot.getRef().getKey();
//                    postValues.put("profileStatus", "Approved");
//                    UserModel model = appleSnapshot.getValue(UserModel.class);
//                    model.setProfileStatus("Approved");
//                    reference1.child(key).updateChildren(postValues);
//                    Prefrences.saveUSer(model, getActivity());
//                    Toast.makeText(getActivity(), "updated", Toast.LENGTH_SHORT).show();
//                    dialog.dismiss();
//                }
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//                Toast.makeText(getActivity(), "something went wrong", Toast.LENGTH_SHORT).show();
//            }
//        });
    }

}
