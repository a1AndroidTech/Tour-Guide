package com.a1techandroid.tourguide.Extra;


import android.content.Context;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import org.w3c.dom.Attr;

public class AttractionActivity extends AppCompatActivity {
    /* Class Constants */
    private static final String EXTRA_ATTRACTION = "com.example.android.tourfc.attraction";

    public static Intent newIntent(Context packageContext, Attraction attraction) {
        Intent intent = new Intent(packageContext, AttractionActivity.class);
        intent.putExtra(EXTRA_ATTRACTION, attraction);

        return intent;
    }



//    @Override
//    protected Fragment createFragment(Context context) {
//        Attraction attraction = getIntent().getParcelableExtra(EXTRA_ATTRACTION);
//        return AttractionFragment.newInstance(attraction);
//    }
}
