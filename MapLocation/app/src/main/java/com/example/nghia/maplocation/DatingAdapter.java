package com.example.nghia.maplocation;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class DatingAdapter extends BaseAdapter {

    private DatingActivity context;
    private int layout;
    private List<Dating> groupList;

    public DatingAdapter(DatingActivity context, int layout, List<Dating> groupList) {
        this.context = context;
        this.layout = layout;
        this.groupList = groupList;
    }

    @Override
    public int getCount() {
        return groupList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    private class ViewHolder {
        String nameAddress,dateAddress,dateTimeAdd,userAdd;
        TextView txtNameDating, txtDateAddress;
        ImageView imgLocation, imgMember, imgJoin, imgUnJoin;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null){
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView             = inflater.inflate(layout,null);

            holder.txtNameDating                    = convertView.findViewById(R.id.txtNameDating);
            holder.txtDateAddress                   = convertView.findViewById(R.id.txtDateAddress);
            holder.imgLocation                      = convertView.findViewById(R.id.imgLocation);
            holder.imgMember                        = convertView.findViewById(R.id.imgMember);
            holder.imgJoin                          = convertView.findViewById(R.id.imgJoin);
            holder.imgUnJoin                        = convertView.findViewById(R.id.imgUnJoin);

            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        final Dating dating = groupList.get(position);

        holder.txtNameDating.setText(dating.getNameAddress());
        holder.txtDateAddress.setText(dating.getDateAddress());

        holder.imgLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConvertAddrToCoor(dating.getNameAddress(),dating.getDateAddress());

                MainActivity.ReadJSONSelectMemberDating(context,
                        dating.getNameAddress(),
                        dating.getDateAddress(),
                        dating.getDateTimeAdd(),
                        dating.getUserAdd());

                context.onBackPressed();
            }
        });
        holder.imgMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.IntentMemberDating(dating.getNameAddress(),dating.getDateAddress(),dating.getDateTimeAdd(),dating.getUserAdd());
            }
        });
        holder.imgJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.ReadJSONInsertMemberDating(dating.getNameAddress(),dating.getDateAddress(),dating.getDateTimeAdd(),dating.getUserAdd());
            }
        });
        holder.imgUnJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.ReadJSONDeleteMemberDating(dating.getNameAddress(),dating.getDateAddress(),dating.getDateTimeAdd(),dating.getUserAdd());
            }
        });

        holder.nameAddress  = dating.getNameAddress();
        holder.dateAddress  = dating.getDateAddress();
        holder.dateTimeAdd  = dating.getDateTimeAdd();
        holder.userAdd      = dating.getUserAdd();

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, context.urlSelectTestEmpty,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.trim().equals("1")){
                            holder.imgLocation.setVisibility(View.VISIBLE);
                            holder.imgMember.setVisibility(View.VISIBLE);
                            holder.imgJoin.setVisibility(View.GONE);
                            holder.imgUnJoin.setVisibility(View.VISIBLE);
                        }else if (response.trim().equals("0")){
                            holder.imgLocation.setVisibility(View.GONE);
                            holder.imgMember.setVisibility(View.GONE);
                            holder.imgJoin.setVisibility(View.VISIBLE);
                            holder.imgUnJoin.setVisibility(View.GONE);
                        }
                    }
                }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("AAA","Loi ! \n"+error.toString());
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();

                params.put("nameAddress",holder.nameAddress);
                params.put("dateAddress",holder.dateAddress);
                params.put("dateTimeAdd",holder.dateTimeAdd);
                params.put("userAdd",holder.userAdd);

                params.put("emailUser",context.emailUser);

                return params;
            }
        };
        stringRequest.setRetryPolicy(
                new DefaultRetryPolicy(
                        0,
                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(stringRequest);

        return convertView;
    }
    private void ConvertAddrToCoor(String NameAddress, String DateAddress){
        Double Latitude,Longitude;
        String regex = "[0-9]+.[0-9]+,[0-9]+.[0-9]+" ;
        if (NameAddress != regex) {
            Geocoder geoCoder = new Geocoder(context, Locale.getDefault());
            try {
                List<Address> addresses = geoCoder.getFromLocationName(NameAddress, 1);
                if (addresses.size() > 0 && addresses != null) {
                    Address loca = addresses.get(0);
                    Latitude = loca.getLatitude();
                    Longitude = loca.getLongitude();
                    MainActivity.AddMarker(Latitude, Longitude,NameAddress,"Ngày Hẹn : "+ DateAddress);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else if (NameAddress == regex){
            String[] stlocation = NameAddress.split(",");
            for ( String st: stlocation) {
                Latitude = Double.parseDouble(stlocation[0]);
                Longitude = Double.parseDouble(stlocation[1]);
                MainActivity.AddMarker(Latitude, Longitude,NameAddress,"Ngày Hẹn : "+ DateAddress);
            }
        }

    }
}
