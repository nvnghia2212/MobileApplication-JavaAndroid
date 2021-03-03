package com.example.nghia.maplocation;

import android.app.Fragment;
import android.app.FragmentManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import java.util.List;
import java.util.Locale;

public class FragmentDescriotionMap extends Fragment{

    ImageView imgRemove,imgDirectionMap;
    AutoCompleteTextView autoSearch1,autoSearch2;
    LatLng origin, destination;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_description_map,container,false);

        imgRemove = view.findViewById(R.id.imgRemove);
        imgDirectionMap = view.findViewById(R.id.imgDirectionMap);
        autoSearch1 = view.findViewById(R.id.autoSearch1);
        autoSearch2 = view.findViewById(R.id.autoSearch2);

        imgRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Remove();
            }
        });

        imgDirectionMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (autoSearch1.getText().toString().equals("") || autoSearch2.getText().toString().equals("") ){
                    Toast.makeText(getActivity(), "Bạn phải nhập đầy đủ địa chỉ cần tìm", Toast.LENGTH_SHORT).show();
                }else {
                    String myLoca = autoSearch1.getText().toString();

                    Double Latitude,Longitude;
                    String regex = "[0-9]+.[0-9]+,[0-9]+.[0-9]+" ;
                    if (myLoca != regex) {
                        Geocoder geoCoder = new Geocoder(getActivity(), Locale.getDefault());
                        try {
                            List<Address> addresses = geoCoder.getFromLocationName(myLoca, 1);
                            if (addresses.size() > 0 && addresses != null) {
                                Address loca = addresses.get(0);
                                Latitude = loca.getLatitude();
                                Longitude = loca.getLongitude();
                                MainActivity.AddMarker(Latitude, Longitude,autoSearch1.getText().toString(),"Thông tin đã tìm !");
                                origin = new LatLng(Latitude,Longitude);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    else if (myLoca == regex){
                        String[] stlocation = myLoca.split(",");
                        for ( String st: stlocation) {
                            Latitude = Double.parseDouble(stlocation[0]);
                            Longitude = Double.parseDouble(stlocation[1]);
                            MainActivity.AddMarker(Latitude, Longitude,autoSearch1.getText().toString(),"Thông tin đã tìm !");
                            origin = new LatLng(Latitude,Longitude);
                        }
                    }


                    String frLoca = autoSearch2.getText().toString();
                    if (frLoca != regex) {
                        Geocoder geoCoder = new Geocoder(getActivity(), Locale.getDefault());
                        try {
                            List<Address> addresses = geoCoder.getFromLocationName(frLoca, 1);
                            if (addresses.size() > 0 && addresses != null) {
                                Address loca = addresses.get(0);
                                Latitude = loca.getLatitude();
                                Longitude = loca.getLongitude();
                                MainActivity.AddMarker(Latitude, Longitude,autoSearch2.getText().toString(),"Thông tin đã tìm !");
                                destination = new LatLng(Latitude,Longitude);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    else if (frLoca == regex){
                        String[] stlocation = frLoca.split(",");
                        for ( String st: stlocation) {
                            Latitude = Double.parseDouble(stlocation[0]);
                            Longitude = Double.parseDouble(stlocation[1]);
                            MainActivity.AddMarker(Latitude, Longitude,autoSearch2.getText().toString(),"Thông tin đã tìm !");
                            destination = new LatLng(Latitude,Longitude);
                        }
                    }

                    MainActivity.getDirection(origin,destination);
                    Remove();
                }
            }
        });
        return view;
    }

    private void Remove(){
        FragmentManager fragmentManager = getFragmentManager();
        android.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        FragmentDescriotionMap fragmentDescriptionMap = (FragmentDescriotionMap) getFragmentManager().findFragmentByTag("FragmentDescriptionMap");
        fragmentTransaction.remove(fragmentDescriptionMap);
        fragmentTransaction.commit();
    }

}
