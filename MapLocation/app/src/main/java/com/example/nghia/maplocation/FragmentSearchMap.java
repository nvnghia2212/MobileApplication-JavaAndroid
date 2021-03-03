package com.example.nghia.maplocation;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.List;
import java.util.Locale;

public class FragmentSearchMap extends Fragment{

    ImageView imgRemove,imgSearhMap;
    AutoCompleteTextView autoSearch;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_search_map,container,false);

        imgRemove = view.findViewById(R.id.imgRemove);
        imgSearhMap = view.findViewById(R.id.imgSearhMap);
        autoSearch = view.findViewById(R.id.autoSearch);

        imgRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Remove();
            }
        });

        imgSearhMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (autoSearch.getText().toString().equals("")){
                    Toast.makeText(getActivity(), "Bạn phải nhập đầy đủ địa chỉ cần tìm", Toast.LENGTH_SHORT).show();
                }else {
                    String myLoca = autoSearch.getText().toString();
                    ConvertAddrToCoor(myLoca);
                }
            }
        });
        return view;
    }

    private void Remove(){
        FragmentManager fragmentManager = getFragmentManager();
        android.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        FragmentSearchMap fragmentSearchMap = (FragmentSearchMap) getFragmentManager().findFragmentByTag("FragmentSearchMap");
        fragmentTransaction.remove(fragmentSearchMap);
        fragmentTransaction.commit();
    }

    //function convert addresses to coordinates
    private void ConvertAddrToCoor(String x){
        Double Latitude,Longitude;
        String regex = "[0-9]+.[0-9]+,[0-9]+.[0-9]+" ;
        if (x != regex) {
            Geocoder geoCoder = new Geocoder(getActivity(), Locale.getDefault());
            try {
                List<Address> addresses = geoCoder.getFromLocationName(x, 1);
                if (addresses.size() > 0 && addresses != null) {
                    Address loca = addresses.get(0);
                    Latitude = loca.getLatitude();
                    Longitude = loca.getLongitude();
                    MainActivity.AddMarker(Latitude, Longitude,autoSearch.getText().toString(),"Thông tin đã tìm !");
                    Remove();
                }
            } catch (Exception e) {
//                autoSearch.setText(String.valueOf("Không tìm thấy địa chỉ"));
                e.printStackTrace();
            }
        }
        else if (x == regex){
            String[] stlocation = x.split(",");
            for ( String st: stlocation) {
                Latitude = Double.parseDouble(stlocation[0]);
                Longitude = Double.parseDouble(stlocation[1]);
                MainActivity.AddMarker(Latitude, Longitude,autoSearch.getText().toString(),"Thông tin đã tìm !");
                Remove();
            }
        }
    }

}
