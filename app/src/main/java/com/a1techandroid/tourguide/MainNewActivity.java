package com.a1techandroid.tourguide;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.ContextThemeWrapper;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.a1techandroid.tourguide.Fragments.FragmentHomeNew;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Objects;

public class MainNewActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private SharedPreferences mSharedPreferences;
    private int mPreviousMenuItemId;
    private String mToken;
    private Handler mHandler;


    Toolbar toolbar;
    DrawerLayout mDrawer;
    ActionBarDrawerToggle toggle;
    NavigationView navigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_new);

        toolbar = findViewById(R.id.toolbar);
        mDrawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);

        setSupportActionBar(toolbar);

        toggle = new ActionBarDrawerToggle(this, mDrawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        mDrawer.addDrawerListener(toggle);

        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);



//        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
//        mToken = mSharedPreferences.getString(USER_TOKEN, null);
//        mPreviousMenuItemId = R.id.nav_city; // This is default item
//        mHandler = new Handler(Looper.getMainLooper());

        // To show what's new in our application
//        WhatsNew whatsNew = WhatsNew.newInstance(
//                new WhatsNewItem(WHATS_NEW1_TITLE, WHATS_NEW1_TEXT));
//        whatsNew.setButtonBackground(ContextCompat.getColor(this, R.color.colorPrimaryDark));
//        whatsNew.setButtonTextColor(ContextCompat.getColor(this, R.color.white));
//        whatsNew.presentAutomatically(this);

        // To check for shared profile intents
        String action =  getIntent().getAction();
        if (Intent.ACTION_VIEW.equals(action)) {
//            showProfile(getIntent().getDataString());
        }

        //Initially city fragment
        FragmentHomeNew fragment;
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragment = new FragmentHomeNew();
        fragmentManager.beginTransaction().replace(R.id.inc, fragment).commit();

//        navigationView.getMenu().getItem(0).setChecked(true);

        // Get runtime permissions for Android M
        getRuntimePermissions();

//        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,
//                mDrawer,
//                toolbar,
//                R.string.navigation_drawer_open,
//                R.string.navigation_drawer_close);
//        mDrawer.addDrawerListener(toggle);
//        toggle.syncState();

//        String emailId = mSharedPreferences.getString("test@gmail.com", getString(R.string.app_name));
//        fillNavigationView(emailId, null);

//        getProfileInfo();
//        if (getIntent() != null && getIntent().getAction() != null) {
//            switch (getIntent().getAction()) {
//                case travelShortcut:
//                    fragment = TravelFragment.newInstance();
//                    fragmentManager.beginTransaction().replace(R.id.inc, fragment).commit();
//                    break;
//                case myTripsShortcut:
//                    fragment = MyTripsFragment.newInstance();
//                    fragmentManager.beginTransaction().replace(R.id.inc, fragment).commit();
//                    break;
//                case utilitiesShortcut:
//                    fragment = UtilitiesFragment.newInstance();
//                    fragmentManager.beginTransaction().replace(R.id.inc, fragment).commit();
//                    break;
//            }
//        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer != null && drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    // Change fragment on selecting naviagtion drawer item
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == mPreviousMenuItemId) {
            DrawerLayout drawer = findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
            return true;
        }

        int id = item.getItemId();
        Fragment fragment = null;
        FragmentManager fragmentManager = getSupportFragmentManager();

        switch (id) {
            case R.id.nav_travel:
//                fragment = TravelFragment.newInstance();
                break;

            case R.id.nav_mytrips:
//                fragment = MyTripsFragment.newInstance();
                break;

            case R.id.nav_city:
//                fragment = CityFragment.newInstance();
                break;

            case R.id.nav_utility:
//                fragment = UtilitiesFragment.newInstance();
                break;

            case R.id.nav_about_us:
//                fragment = AboutUsFragment.newInstance();
                break;

            case R.id.nav_signout: {

                //set AlertDialog before signout
                ContextThemeWrapper crt = new ContextThemeWrapper(this, R.style.AlertDialog);
                AlertDialog.Builder builder = new AlertDialog.Builder(crt);
                builder.setMessage(R.string.signout_message)
                        .setPositiveButton(R.string.positive_button,
                                (dialog, which) -> {
                                    mSharedPreferences
                                            .edit()
                                            .putString("", null)
                                            .apply();
                                    Intent i = new Intent(getApplicationContext(),LoginActivity.class);
                                    startActivity(i);
                                    finish();
                                })
                        .setNegativeButton(android.R.string.cancel,
                                (dialog, which) -> {

                                });
                builder.create().show();
                break;
            }

            case R.id.nav_myfriends :
//                fragment = MyFriendsFragment.newInstance();
                break;
            case R.id.nav_settings :
//                fragment = SettingsFragment.newInstance();
                break;
        }

        if (fragment != null) {
            fragmentManager.beginTransaction().replace(R.id.inc, fragment).commit();
        }

        mDrawer.closeDrawer(GravityCompat.START);
        mPreviousMenuItemId = item.getItemId();
        return true;
    }

    private void getRuntimePermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(MainNewActivity.this,
                    Manifest.permission.CAMERA)
                    != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.CAMERA,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.BLUETOOTH,
                        Manifest.permission.BLUETOOTH_ADMIN,
                        Manifest.permission.READ_CONTACTS,
                        Manifest.permission.WRITE_CONTACTS,
                        Manifest.permission.READ_PHONE_STATE,
                        Manifest.permission.WAKE_LOCK,
                        Manifest.permission.INTERNET,
                        Manifest.permission.ACCESS_NETWORK_STATE,
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.VIBRATE,
                }, 0);
            }
        }
    }

    public void onClickProfile(View view) {
//        Intent in = ProfileActivity.getStartIntent(MainActivity.this);
//        startActivity(in);
    }

    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        return intent;
    }

    private void fillNavigationView(String emailId, String imageURL) {

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // Get reference to the navigation view header and email textview
        View navigationHeader = navigationView.getHeaderView(0);
        TextView emailTextView = navigationHeader.findViewById(R.id.email);
        emailTextView.setText(emailId);

        ImageView imageView = navigationHeader.findViewById(R.id.image);
//        Picasso.with(MainActivity.this).load(imageURL).placeholder(R.drawable.icon_profile)
//                .error(R.drawable.icon_profile).into(imageView);
    }


//    @Override
//    protected void onResume() {
//        super.onResume();
//        fillNavigationView(mSharedPreferences.getString(USER_NAME, getString(R.string.app_name)),
//                mSharedPreferences.getString(USER_IMAGE, null));
//        invalidateOptionsMenu();
//    }

//    void showProfile(String data) {
//        Uri uri = Uri.parse(data);
//        String userId = uri.getQueryParameter(SHARE_PROFILE_USER_ID_QUERY);
//        String myId = mSharedPreferences.getString(USER_ID, null);
//        Log.v("user id", userId + " " + myId);
//        if (userId != null) {
//            int id = Integer.parseInt(userId);
//            if (!userId.equals(myId)) {
//                Intent intent = FriendsProfileActivity.getStartIntent(MainActivity.this, id);
//                startActivity(intent);
//            } else {
//                Intent intent = ProfileActivity.getStartIntent(MainActivity.this);
//                startActivity(intent);
//            }
//        }
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.notification_menu, menu);
//        updateNotificationsCount(menu);
        return true;
    }

//    public void updateNotificationsCount(Menu menu) {
//        String uri;
//        uri = API_LINK_V2 + "number-of-unread-notifications";
//        Log.v("EXECUTING", uri);
//
//        //Set up client
//        OkHttpClient client = new OkHttpClient();
//        //Execute request
//        Request request = new Request.Builder()
//                .header("Authorization", "Token " + mToken)
//                .url(uri)
//                .build();
//        //Setup callback
//        client.newCall(request).enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//                Log.e("Request Failed", "Message : " + e.getMessage());
//            }
//
//            @Override
//            public void onResponse(Call call, final Response response) throws IOException {
//                final String res = Objects.requireNonNull(response.body()).string();
//
//                mHandler.post(() -> {
//                    try {
//                        JSONObject object = new JSONObject(res);
//                        int mNotificationCount = object.getInt("number_of_unread_notifications");
//                        if (mNotificationCount > 0) {
//                            BadgeCounter.update(MainActivity.this,
//                                    menu.findItem(R.id.action_notification),
//                                    R.drawable.ic_notifications_white,
//                                    BadgeCounter.BadgeColor.RED,
//                                    mNotificationCount);
//                        }
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                        Log.e("ERROR : ", "Message : " + e.getMessage());
//                    }
//                });
//            }
//        });
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_notification :
//                Intent intent = NotificationsActivity.getStartIntent(MainActivity.this);
//                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

