package com.a1techandroid.tourguide.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.a1techandroid.tourguide.Adapter.SettingListAdapter;
import com.a1techandroid.tourguide.R;

import java.util.ArrayList;

public class SettingFragment extends Fragment {

    ListView listView;
    SettingListAdapter settingListAdapter;
    ArrayList<String> listofItems;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.setting_fragment, container, false);
        initView(view);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    public void initView(View view){
        listView=view.findViewById(R.id.listView);
        listofItems= new ArrayList<>();
        listofItems.add("Update Profile");
        listofItems.add("Reset Password");
        listofItems.add("Rate this App");
        listofItems.add("About");
        listofItems.add("Share");
        listofItems.add("Logout");
        settingListAdapter = new SettingListAdapter(getActivity(), listofItems);
        listView.setAdapter(settingListAdapter);
        settingListAdapter.notifyDataSetChanged();
    }


}
