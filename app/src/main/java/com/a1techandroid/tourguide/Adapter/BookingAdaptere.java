package com.a1techandroid.tourguide.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.a1techandroid.tourguide.CustomClasses.Prefrences;
import com.a1techandroid.tourguide.Models.Booking;
import com.a1techandroid.tourguide.Models.BookingHotel;
import com.a1techandroid.tourguide.Models.HistotyModel;
import com.a1techandroid.tourguide.Models.NotificationModel;
import com.a1techandroid.tourguide.Models.UserModel;
import com.a1techandroid.tourguide.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class BookingAdaptere extends BaseAdapter {
    Context context;
    ArrayList<BookingHotel> list = new ArrayList<>();
    UserModel userModel;

    public BookingAdaptere(Context context, ArrayList<BookingHotel> list) {
        this.context = context;
        this.list = list;
        userModel = Prefrences.getUser(context);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    class ViewHolder{
        TextView name, status, date, type;
        ImageView accept, reject;
    }   

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null){
            LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.bookig_item, null);
            holder = new ViewHolder();
            holder.name = convertView.findViewById(R.id.name);
            holder.status = convertView.findViewById(R.id.status);
            holder.date = convertView.findViewById(R.id.date);
            holder.type = convertView.findViewById(R.id.bookedBy);
            holder.accept = convertView.findViewById(R.id.accept);
            holder.reject = convertView.findViewById(R.id.reject);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        final BookingHotel name = list.get(position);


        holder.name.setText(name.getName());
        holder.status.setText(name.getStatus());
        holder.date.setText(name.getDate());
        holder.type.setText(name.getUserName());

        if (name.getStatus().equals("Approved")){
           holder.accept.setVisibility(View.GONE);
           holder.reject.setVisibility(View.VISIBLE);
        }else {
            holder.accept.setVisibility(View.VISIBLE);
            holder.reject.setVisibility(View.VISIBLE);
        }

//        convertView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(v.getContext(), GiftActivity.class);
//                Gson gson = new Gson();
//                String myJson = gson.toJson(name);
//                intent.putExtra("model", myJson);
//                v.getContext().startActivity(intent);
//            }
//        });

        holder.accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
                Query applesQuery = null;
                Query query = ref.child("Notification");
                if (userModel.getUserType().equals("2")){
                    applesQuery = ref.child("hotel_booking").child(name.getEmail().replace(".",""));

                }else if (userModel.getUserType().equals("3")){
                    applesQuery = ref.child("ticket_booking").child(name.getEmail().replace(".",""));

                }else if (userModel.getUserType().equals("4")){
                    applesQuery = ref.child("car_booking").child(name.getEmail().replace(".",""));

                }

//                applesQuery.addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot snapshot) {
//                        BookingHotel model = snapshot.getValue(BookingHotel.class);
//                            String key = snapshot.getKey();
//                        HashMap<String, Object> map = new HashMap<>();
//                        map.put("status", "Approved");
//                        ref.child("hotel_booking").child(key).updateChildren(map);
//                        Toast.makeText(context, "Accepted", Toast.LENGTH_SHORT).show();
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError error) {
//
//                    }
//                });

                applesQuery.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                        BookingHotel model = snapshot.getValue(BookingHotel.class);

                        if (model.getEmail().equals(name.getEmail())) {
                            String key = snapshot.getKey();
                            HashMap<String, Object> map = new HashMap<>();
                            map.put("status", "Approved");
                            if (userModel.getUserType().equals("2")) {
                                ref.child("hotel_booking").child(model.getEmail()).child(key).updateChildren(map);
                            } else if (userModel.getUserType().equals("3")) {
                                ref.child("ticket_booking").child(model.getEmail()).child(key).updateChildren(map);
                        }else if (userModel.getUserType().equals("4")){
                                ref.child("car_booking").child(model.getEmail()).child(key).updateChildren(map);
                            }

                            DatabaseReference mRefe2 = FirebaseDatabase.getInstance().getReference("History");
                            HistotyModel histotyModel = new HistotyModel("Hotel Ticket", name.getName(), "Approved", name.getUserName(), name.getEmail().replace(".", ""), model.getUserName(), model.getUserPhone());

                            mRefe2.child(mRefe2.push().getKey()).setValue(histotyModel);
                            Toast.makeText(context, "Accepted", Toast.LENGTH_SHORT).show();
                            query.orderByChild("email").equalTo(name.getEmail()).addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    NotificationModel model1 = snapshot.getValue(NotificationModel.class);
                                    String key = snapshot.getKey();
                                    HashMap<String, Object> map = new HashMap<>();
                                    map.put("status", "Approved");
                                    ref.child(key).updateChildren(map);
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                        }
                    }

                    @Override
                    public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                    }

                    @Override
                    public void onChildRemoved(@NonNull DataSnapshot snapshot) {

                    }

                    @Override
                    public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
//                applesQuery.addListenerForSingleValueEvent(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(DataSnapshot dataSnapshot) {
//                        for (DataSnapshot appleSnapshot: dataSnapshot.getChildren()) {
//                            Toast.makeText(context, "Deleted", Toast.LENGTH_SHORT).show();
//                            BookingHotel model = appleSnapshot.getValue(BookingHotel.class);
//                            String key = appleSnapshot.getKey();
//                            HashMap<String, Object> map = new HashMap<>();
//                            map.put("status", "Approved");
//                            ref.child("hotel_booking").child(key).updateChildren(map);
//                        }
//                    }
//
//                    @Override
//                    public void onCancelled(DatabaseError databaseError) {
//                        Toast.makeText(context, "something went wrong", Toast.LENGTH_SHORT).show();
//                    }
//                });
            }
        });

        holder.reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
                Query applesQuery = null;
                if (userModel.getUserType().equals("2")){
                    applesQuery = ref.child("hotel_booking").child(name.getEmail().replace(".",""));

                }else if (userModel.getUserType().equals("3")){
                    applesQuery = ref.child("ticket_booking").child(name.getEmail().replace(".",""));

                }else if (userModel.getUserType().equals("4")){
                    applesQuery = ref.child("car_booking").child(name.getEmail().replace(".",""));

                }

                applesQuery.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                        BookingHotel model = snapshot.getValue(BookingHotel.class);

                        if (model.getEmail().equals(name.getEmail())) {
                            snapshot.getRef().removeValue();
                            list.add(model);
                            notifyDataSetChanged();
                            Toast.makeText(context, "Deleted", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                    }

                    @Override
                    public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                        BookingHotel model = snapshot.getValue(BookingHotel.class);
                        if (model.getEmail().equals(name.getEmail())) {
                            snapshot.getRef().removeValue();
                            list.remove(getItem(position));
                            notifyDataSetChanged();
                            Toast.makeText(context, "Deleted", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });


        return convertView;
    }

    public String getFormateddate(String format){
        Date date=null;
        String date2 = null;
        SimpleDateFormat formatter = new SimpleDateFormat("EEE MMM dd yyyy");
        String temp = "Thu Dec 17 15:37:43 GMT+05:30 2015";
        try {
            date = formatter.parse(format);
            date2 = date.toString();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return date2;
    }

//    public void updateHistory(){
//        FirebaseDatabase rootNode = FirebaseDatabase.getInstance();
//        DatabaseReference reference = reference=rootNode.getReference("History");
//        reference.orderByChild("email").equalTo(FirebaseAuth.getInstance().getCurrentUser().getEmail().replace(".","")).addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                for(DataSnapshot datas: dataSnapshot.getChildren()){
//                    HistotyModel uni_model=datas.getValue(HistotyModel.class);
////
//                }
//                if (list.size() == 0){
//                    noHistoryText.setVisibility(View.VISIBLE);
//                }
//                mProgressDialog.dismiss();
//            }
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//                Toast.makeText(getActivity(), "data not found", Toast.LENGTH_SHORT).show();
//                mProgressDialog.hide();
//            }
//        });
//
//    }
}




