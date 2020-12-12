package com.a1techandroid.tourguide;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.a1techandroid.tourguide.Fragments.DealsFragment;
import com.a1techandroid.tourguide.Fragments.FragmentHotler;
import com.a1techandroid.tourguide.Fragments.FragmentTickter;
import com.a1techandroid.tourguide.Fragments.HistoryFragment;
import com.a1techandroid.tourguide.Fragments.HomeFragment;
import com.a1techandroid.tourguide.Fragments.SettingFragment;
import com.a1techandroid.tourguide.Models.UserModel;
import com.fxn.BubbleTabBar;
import com.fxn.OnBubbleClickListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth auth;
    BubbleTabBar bubbleTabBar;
    public static TextView title;
    SearchView searchView;
    private ProgressDialog mProgressDialog;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mRefe;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDatabase = FirebaseDatabase.getInstance();
        mRefe = mDatabase.getReference("Users");
        mProgressDialog = new ProgressDialog(this);
        auth = FirebaseAuth.getInstance();
        mProgressDialog = new ProgressDialog(MainActivity.this);
        initViews();
        setUpBubleTabBar();

        HomeFragment homeFragment= new HomeFragment();
        replaceFragment(homeFragment);
        title.setText("Home");

    }
    public void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.contentFrame, fragment);
        fragmentTransaction.addToBackStack(fragment.toString());
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        fragmentTransaction.commit();
    }

    public void initViews(){
        bubbleTabBar= findViewById(R.id.bubbleTabBar);
        title= findViewById(R.id.title);

    }
    public void setUpBubleTabBar(){

//        bubbleTabBar.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (v.getId() == R.id.home){
//
//                }else if (v.getId() == R.id.Discount){
//
//                }else if (v.getId() == R.id.History){
//
//                }else if (v.getId() == R.id.Discount){
//
//                }
//            }
//        });

        bubbleTabBar.addBubbleListener(new OnBubbleClickListener() {
            @Override
            public void onBubbleClick(int i) {
                if (i == 2131230942){
                    HomeFragment homeFragment= new HomeFragment();
                    replaceFragment(homeFragment);
                    title.setText("Home");
                }else if (i == 2131230726){
                    DealsFragment homeFragment= new DealsFragment();
                    replaceFragment(homeFragment);
                    title.setText("Deals");
                }else if (i == 2131230728){
                    HistoryFragment homeFragment= new HistoryFragment();
                    replaceFragment(homeFragment);
                    title.setText("History");
                }else if (i == 2131230739){
                    SettingFragment homeFragment= new SettingFragment();
                    replaceFragment(homeFragment);
                    title.setText("Setting");
                }
            }
        });

    }

    public void getUSerData(){
        mRefe.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserModel userModel = snapshot.getValue(UserModel.class);
                if (userModel.getUserType().equals("User")){
                    HomeFragment homeFragment= new HomeFragment();
                    replaceFragment(homeFragment);
                }else if (userModel.getUserType().equals("Hotel")){
                    FragmentHotler homeFragment= new FragmentHotler();
                    replaceFragment(homeFragment);
                }else if (userModel.getUserType().equals("Ticket Agency")){
                    FragmentTickter homeFragment= new FragmentTickter();
                    replaceFragment(homeFragment);
                }else if (userModel.getUserType().equals("Train")){
                    HomeFragment homeFragment= new HomeFragment();
                    replaceFragment(homeFragment);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

}