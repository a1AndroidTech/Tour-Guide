package com.a1techandroid.tourguide.Fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.a1techandroid.tourguide.Extra.AttractionCollection;
import com.a1techandroid.tourguide.Extra.AttractionRepository;
import com.a1techandroid.tourguide.Extra.MasterAdapter;
import com.a1techandroid.tourguide.Adapter.SliderAdapter;
import com.a1techandroid.tourguide.MainActivity;
import com.a1techandroid.tourguide.R;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.List;

public class HomeFragment extends Fragment {
    SliderView sliderView;
    RecyclerView recyclerView;
    CardView Car_Rental, Airport, Tickets, GiftCard;
    ImageView plane, train, hoteling;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragmetn_home, container, false);
        initViews(view);
        setUpClicks();
        return view;
    }

    @Override
    public void onResume() {
        initSliderView();
        MainActivity.title.setText("Home");
        super.onResume();
    }

    public void initViews(View view){
        sliderView = view.findViewById(R.id.imageSlider);

        plane = view.findViewById(R.id.plane);
        train = view.findViewById(R.id.train);
        hoteling = view.findViewById(R.id.hotel);

        Car_Rental = view.findViewById(R.id.carRental);
        Airport = view.findViewById(R.id.airport);
        Tickets = view.findViewById(R.id.tickets);
        GiftCard = view.findViewById(R.id.giftCards);

        recyclerView = view.findViewById(R.id.main_recycler_view);

        // Set fixed size true and optimize recycler view performance
        // The data container has fixed number of attractions and not infinite list
        recyclerView.setHasFixedSize(true);
        AttractionRepository repository = AttractionRepository.getInstance(getActivity());
        List<AttractionCollection> collections = repository.getCollections();
        // Connect the RecyclerView widget to the vertical linear layout
        // (not reverse layout - hence false)
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),
                LinearLayoutManager.VERTICAL, false));

        // Attach adapter to the RecyclerView widget which is connected to a layout manager
        MasterAdapter collectionAdapter = new MasterAdapter(getActivity(), collections);
        recyclerView.setAdapter(collectionAdapter);
    }

    public void initSliderView(){

        SliderAdapter adapter = new SliderAdapter(getActivity());

        sliderView.setSliderAdapter(adapter);

        sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM); //set indicator animation by using IndicatorAnimationType. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        sliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
        sliderView.setIndicatorSelectedColor(Color.WHITE);
        sliderView.setIndicatorUnselectedColor(Color.GRAY);
        sliderView.setScrollTimeInSec(4); //set scroll delay in seconds :
        sliderView.startAutoCycle();
    }


    public void setUpClicks(){
        plane.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentAirPlane homeFragment= new FragmentAirPlane();
                replaceFragment(homeFragment);
                MainActivity.title.setText("Plane");
            }
        });

        train.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTrain homeFragment= new FragmentTrain();
                replaceFragment(homeFragment);
                MainActivity.title.setText("Train");
            }
        });

        hoteling.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentHoteling homeFragment= new FragmentHoteling();
                replaceFragment(homeFragment);
                MainActivity.title.setText("Hoteling");
            }
        });
        Car_Rental.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentCar homeFragment= new FragmentCar();
                replaceFragment(homeFragment);
                MainActivity.title.setText("Car Rental");
            }
        });
        Airport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmetnTicketing homeFragment= new FragmetnTicketing();
                replaceFragment(homeFragment);
                MainActivity.title.setText("Ticketing");
            }
        });
        Tickets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmetnTicketing homeFragment= new FragmetnTicketing();
                replaceFragment(homeFragment);
                MainActivity.title.setText("Tickets");
            }
        });
        GiftCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentGift homeFragment= new FragmentGift();
                replaceFragment(homeFragment);
                MainActivity.title.setText("Gift Cards");
            }
        });
    }

    public void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.contentFrame, fragment);
        fragmentTransaction.addToBackStack(fragment.toString());
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        fragmentTransaction.commit();
    }

}
