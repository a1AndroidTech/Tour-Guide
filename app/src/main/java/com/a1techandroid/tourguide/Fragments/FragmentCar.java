package com.a1techandroid.tourguide.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowId;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.a1techandroid.tourguide.Adapter.CarRentalAdapter;
import com.a1techandroid.tourguide.Models.CarRentalModel;
import com.a1techandroid.tourguide.R;

import java.util.ArrayList;

public class FragmentCar extends Fragment {
    ListView listView;
    CarRentalAdapter carRentalAdapter;
    ArrayList<CarRentalModel> list= new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_airplane, container, false);
        initViews(view);
        initV();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }
    public void initViews(View view){
        listView=view.findViewById(R.id.listView);
    }

    public void initV(){
        list.add(new CarRentalModel("Islamabad","ABBOTTABAD","1410/410","N/A"));
        list.add(new CarRentalModel("Islamabad","AHMED PUR EAST","1200/1200","N/A"));
        list.add(new CarRentalModel("Islamabad","BAHAWALPUR","1000/1000","N/A"));
        list.add(new CarRentalModel("Islamabad","BARIKOT","1480/1480","N/A"));
        list.add(new CarRentalModel("Islamabad","BATKHELA","1430/1430","N/A"));
        list.add(new CarRentalModel("Islamabad","BHAKKAR","1000/1000","N/A"));
        list.add(new CarRentalModel("Islamabad","CHOWK BAHADURPUR","1570/1570","N/A"));
        list.add(new CarRentalModel("Islamabad","DASKA","500/500","N/A"));
        list.add(new CarRentalModel("Islamabad","DERA GHAZI KHAN","1250/1250","N/A"));
        list.add(new CarRentalModel("Islamabad","DERA ISMAIL KHAN","1100","N/A"));
        list.add(new CarRentalModel("Islamabad","DHARKI","N/A","N/A"));
        list.add(new CarRentalModel("Islamabad","FAISALABAD","530/530","N/A"));
        list.add(new CarRentalModel("Islamabad","GUJRANWALA","410","N/A"));
        list.add(new CarRentalModel("Islamabad","GUJRAT","380","N/A"));
        list.add(new CarRentalModel("Islamabad","HARIPUR","1410/1410\t","N/A"));
        list.add(new CarRentalModel("Islamabad","HYDERABAD","3380/3380","N/A"));
        list.add(new CarRentalModel("Islamabad","JAMPUR","1500/1500","N/A"));
        list.add(new CarRentalModel("Islamabad","AMSHORO","3030/N/A","N/A"));
        list.add(new CarRentalModel("Islamabad","JHANG","670/N/A","N/A"));
        list.add(new CarRentalModel("Islamabad","JHELUM","690/N/A","N/A"));
        list.add(new CarRentalModel("Islamabad","JAUHARABAD\t","840/N/A","N/A"));
        list.add(new CarRentalModel("Islamabad","KARACHI","3420/4800","N/A"));
        list.add(new CarRentalModel("Islamabad","KASHMORE","2200/N/A","N/A"));
        list.add(new CarRentalModel("Islamabad","KHAN PUR","1510/1510","N/A"));
        list.add(new CarRentalModel("Islamabad","Lodhran","910/910","N/A"));
        list.add(new CarRentalModel("Islamabad","MARDAN","1210/1160","N/A"));
        list.add(new CarRentalModel("Islamabad","MIANWALI","1000N/A","N/A"));
        list.add(new CarRentalModel("Islamabad","MUZAFARGARH","1050/1100","N/A"));
        list.add(new CarRentalModel("Islamabad","OKARA CITY","390/390","N/A"));

        carRentalAdapter = new CarRentalAdapter(getActivity(), list);
        listView.setAdapter(carRentalAdapter);
        carRentalAdapter.notifyDataSetChanged();
    }

}
