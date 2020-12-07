package com.a1techandroid.tourguide;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.a1techandroid.tourguide.Fragments.DealsFragment;
import com.a1techandroid.tourguide.Fragments.HistoryFragment;
import com.a1techandroid.tourguide.Fragments.HomeFragment;
import com.a1techandroid.tourguide.Fragments.SettingFragment;
import com.fxn.BubbleTabBar;
import com.fxn.OnBubbleClickListener;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth auth;
    BubbleTabBar bubbleTabBar;
    public static TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        auth = FirebaseAuth.getInstance();
        initViews();
        setUpBubleTabBar();
        HomeFragment homeFragment= new HomeFragment();
        replaceFragment(homeFragment);
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

        bubbleTabBar.addBubbleListener(new OnBubbleClickListener() {
            @Override
            public void onBubbleClick(int i) {
                if (i == 2131230935){
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
                }else if (i == 2131230738){
                    SettingFragment homeFragment= new SettingFragment();
                    replaceFragment(homeFragment);
                    title.setText("Setting");
                }
            }
        });

    }

}