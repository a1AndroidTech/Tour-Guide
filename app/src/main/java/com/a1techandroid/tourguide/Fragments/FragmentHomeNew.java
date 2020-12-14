package com.a1techandroid.tourguide.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.a1techandroid.tourguide.Adapter.CardViewOptionsAdapter;
import com.a1techandroid.tourguide.Models.CardItemEntity;
import com.a1techandroid.tourguide.R;

import java.util.ArrayList;
import java.util.List;

public class FragmentHomeNew extends Fragment implements CardViewOptionsAdapter.OnItemClickListener{
    RecyclerView travel_options_recycle_view;
    FragmentManager fragmentManager;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fr_home_new, container, false);
        travel_options_recycle_view= view.findViewById(R.id.travel_options_recycle_view);
        fragmentManager = getActivity().getSupportFragmentManager();
        List<CardItemEntity> cardEntities = getTravelItems();

        CardViewOptionsAdapter cardViewOptionsAdapter = new CardViewOptionsAdapter(this, cardEntities);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        travel_options_recycle_view.setLayoutManager(mLayoutManager);
        travel_options_recycle_view.setItemAnimator(new DefaultItemAnimator());
        travel_options_recycle_view.setAdapter(cardViewOptionsAdapter);
        return view;

    }

    private List<CardItemEntity> getTravelItems() {
        List<CardItemEntity> cardEntities = new ArrayList<>();
        cardEntities.add(
                new CardItemEntity(
                        getResources().getDrawable(R.drawable.hotel_new2),
                       "Hotels"));
        cardEntities.add(
                new CardItemEntity(
                        getResources().getDrawable(R.drawable.plane_new2),
                        "Ticketing"));
        cardEntities.add(
                new CardItemEntity(
                        getResources().getDrawable(R.drawable.car_enw),
                        "Rents"));

        cardEntities.add(
                new CardItemEntity(
                        getResources().getDrawable(R.drawable.shop),
                        "Gifts"));

        cardEntities.add(
                new CardItemEntity(
                        getResources().getDrawable(R.drawable.culture2),
                        "Culture"));
        return cardEntities;
    }

    @Override
    public void onItemClick(int position) {
        Intent i;
        switch (position) {
            case 0:

                FragmentHoteling fragment;
                fragment = new FragmentHoteling();
                fragmentManager.beginTransaction().replace(R.id.inc, fragment).addToBackStack(fragment.getTag()).commit();

                break;
            case 1:
                FragmentAirPlane fragment1;
                fragment1 = new FragmentAirPlane();
                fragmentManager.beginTransaction().add(R.id.inc, fragment1).addToBackStack(fragment1.getTag()).commit();

                break;
            case 2:

                FragmentCar fragment2;
                fragment2 = new FragmentCar();
                fragmentManager.beginTransaction().add(R.id.inc, fragment2).addToBackStack(fragment2.getTag()).commit();
                break;
        }
    }

}
