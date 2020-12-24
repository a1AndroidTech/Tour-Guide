package com.a1techandroid.tourguide.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.a1techandroid.tourguide.GiftActivity;
import com.a1techandroid.tourguide.Models.GiftModel;
import com.a1techandroid.tourguide.Models.UserModel;
import com.a1techandroid.tourguide.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.util.ArrayList;

public class UserAdapter extends BaseAdapter {
    Context context;
    ArrayList<UserModel> list = new ArrayList<>();

    public UserAdapter(Context context, ArrayList<UserModel> list) {
        this.context = context;
        this.list = list;
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
        TextView userName, userType, userLocation;
        ImageView delete;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null){
            LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.user_item, null);
            holder = new ViewHolder();
            holder.userName = convertView.findViewById(R.id.userName);
            holder.userType = convertView.findViewById(R.id.type);
            holder.userLocation = convertView.findViewById(R.id.loc);
            holder.delete = convertView.findViewById(R.id.delete);

            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        final UserModel name = list.get(position);


        holder.userName.setText(name.getName());
        if (name.getUserType().equals("1")){
            holder.userType.setText("Traveler");

        }else if (name.getUserType().equals("2")){
            holder.userType.setText("Hotel");

        }else if (name.getUserType().equals("3")){
            holder.userType.setText("Ticket");

        }else if (name.getUserType().equals("4")){
            holder.userType.setText("Rent");

        }
        holder.userLocation.setText(name.getCity());

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
                Query applesQuery = ref.child("Users").orderByChild("email").equalTo(name.getEmail());

                applesQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot appleSnapshot: dataSnapshot.getChildren()) {
                            appleSnapshot.getRef().removeValue();

//                            ordersLists.remove(order);
                            Toast.makeText(context, "Deleted", Toast.LENGTH_SHORT).show();
//                            notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Toast.makeText(context, "something went wrong", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

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


        return convertView;
    }
}




