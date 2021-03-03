package com.example.nghia.maplocation;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MemberDatingActivity extends AppCompatActivity {

    String urlSelectMemberDating    = "http://192.168.56.1:8080/androidwebservice/selectmemberdating.php";

    ListView lvMemberDating;
    ArrayList<User> arrayUser;
    MemberDatingAdapter adapter;

    ImageView buttonReturn;

    String nameAddress,dateAddress,dateTimeAdd,userAdd,emailUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_dating);

        Intent intent = getIntent();
        nameAddress = intent.getStringExtra("nameAddress");
        dateAddress = intent.getStringExtra("dateAddress");
        dateTimeAdd = intent.getStringExtra("dateTimeAdd");
        userAdd     = intent.getStringExtra("userAdd");


        lvMemberDating = findViewById(R.id.lvMemberDating);
        arrayUser = new ArrayList<>();
        adapter = new MemberDatingAdapter(this,R.layout.row_member_dating,arrayUser);
        lvMemberDating.setAdapter(adapter);

        buttonReturn = findViewById(R.id.buttonReturn);
        buttonReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        ReadJSONSelectMemberDating(urlSelectMemberDating);
    }

    private void ReadJSONSelectMemberDating (String url){

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONArray jsonArray = new JSONArray(response);

                            Log.d("BBB","Loi ! \n"+jsonArray.toString());

                            arrayUser.clear();
                            adapter.notifyDataSetChanged();
                            for (int i = 0 ; i<= jsonArray.length(); i++) {
                                JSONObject object = jsonArray.getJSONObject(i);

                                arrayUser.add(new User(
                                        object.getInt("ID"),
                                        object.getString("Email"),
                                        object.getString("GivenName"),
                                        object.getString("Name"),
                                        object.getDouble("Latitude"),
                                        object.getDouble("Longitude"),
                                        object.getInt("OnlOff")
                                ));
                                adapter.notifyDataSetChanged();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        Log.d("AAA","Loi ! \n"+response.toString());

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MemberDatingActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String> params = new HashMap<>();

                params.put("nameAddress",nameAddress);
                params.put("dateAddress",dateAddress);
                params.put("dateTimeAdd",dateTimeAdd);
                params.put("userAdd",userAdd);

                return params;
            }

            // Cho phép StringRequest nhận JsonArray
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {

                Map<String,String> headers = new HashMap<>();
                headers.put("Accept","application/json");

                return headers;
            }
        };
        stringRequest.setRetryPolicy(
                new DefaultRetryPolicy(
                        0,
                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        requestQueue.add(stringRequest);

    }

    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount()>0)
        {
            getFragmentManager().popBackStack();
        }else {
            super.onBackPressed();
        }

    }
}
