package com.a1techandroid.tourguide.Fragments;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.a1techandroid.tourguide.Adapter.BookingAdaptere;
import com.a1techandroid.tourguide.CustomClasses.Prefrences;
import com.a1techandroid.tourguide.Models.Booking;
import com.a1techandroid.tourguide.Models.HotelModel;
import com.a1techandroid.tourguide.Models.PlaneModel;
import com.a1techandroid.tourguide.Models.UserModel;
import com.a1techandroid.tourguide.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FragmetnTicketing extends Fragment {
    ListView listView;
    BookingAdaptere adapter;
    ArrayList<Booking> list = new ArrayList<>();
    DatabaseReference reference;
    DatabaseReference reference1;
    DatabaseReference reference2;
    FirebaseDatabase rootNode;
    HotelModel hotelModel;
    private ProgressDialog mProgressDialog;
    UserModel userModel;
    Dialog dialog;
    TextView centerTExt;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_airplane, container, false);
        rootNode = FirebaseDatabase.getInstance();
        reference = rootNode.getReference("Car_Rental");
        reference1 = rootNode.getReference("Users");
        reference2 = rootNode.getReference("car_booking");
        mProgressDialog = new ProgressDialog(getActivity());
        userModel = Prefrences.getUser(getActivity());

        if (userModel.getProfileStatus().equals("pending")) {
            resetPasswordDialog();
        } else {
            readValueFromFireBase();
        }
        initViews(view);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    public void initViews(View view) {
        listView = view.findViewById(R.id.listView);
        centerTExt = view.findViewById(R.id.centerTExt);

    }

    public void resetPasswordDialog() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.ticket_dialog);

        final EditText editText = (EditText) dialog.findViewById(R.id.name);
        final EditText editText2 = (EditText) dialog.findViewById(R.id.loc);
        final EditText editText3 = (EditText) dialog.findViewById(R.id.star);
        final EditText editText4 = (EditText) dialog.findViewById(R.id.price);
        Button btnSave = (Button) dialog.findViewById(R.id.update);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AuthCredential credential = EmailAuthProvider
                        .getCredential(editText.getText().toString(), editText2.getText().toString());
                String key = reference.push().getKey();
                PlaneModel hotelModel1 = new PlaneModel(editText.getText().toString(), editText2.getText().toString(), editText3.getText().toString(), editText4.getText().toString(), FirebaseAuth.getInstance().getCurrentUser().getEmail().replace(".", ""));
                reference.child(FirebaseAuth.getInstance().getCurrentUser().getEmail().replace(".", ""))
                        .setValue(hotelModel1).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if (task.isSuccessful()) {
                            updateProfileStatus();
                        } else {
                            Toast.makeText(getActivity(), "something went wrong", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
        final Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.CENTER);
        dialog.show();
    }




    public void initV() {
        readValueFromFireBase();

//        addUni();

    }

//    public void addUni(){
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
//    }

    public void readValueFromFireBase() {
        mProgressDialog.setMessage("Fetching Data");
        mProgressDialog.show();

        reference2.child(FirebaseAuth.getInstance().getCurrentUser().getEmail().replace(".", "")).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Booking uni_model = snapshot.getValue(Booking.class);
//                officers.setUid(snapshot.getKey());
                if (uni_model != null) {
                    list.add(uni_model);
                    adapter = new BookingAdaptere(getActivity(), list);
                    listView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    mProgressDialog.hide();
                } else {
                    centerTExt.setVisibility(View.VISIBLE);
                    centerTExt.setText("Not Any Booking Yet!");
                    mProgressDialog.hide();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
//        reference2.addChildEventListener(new ChildEventListener() {
//            @Override
//            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
//                HotelModel uni_model=snapshot.getValue(HotelModel.class);
////                officers.setUid(snapshot.getKey());
//                list.add(uni_model);
//                adapter= new HotelingAdapter(getActivity(), list);
//                listView.setAdapter(adapter);
//                adapter.notifyDataSetChanged();
//                mProgressDialog.hide();
//
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
//                HotelModel officers=snapshot.getValue(HotelModel.class);
////                officers.setUid(snapshot.getKey());
//                list.remove(officers);
//                adapter= new HotelingAdapter(getActivity(), list);
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

    public void updateProfileStatus() {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        Query applesQuery = reference1.child(FirebaseAuth.getInstance().getCurrentUser().getEmail().replace(".", ""));
        applesQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserModel model = snapshot.getValue(UserModel.class);
                Map<String, Object> postValues = new HashMap<String, Object>();
                postValues.put("profileStatus", "Approved");
                model.setProfileStatus("Approved");
                reference1.child(model.getEmail().replace(".", "")).updateChildren(postValues);
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