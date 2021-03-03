package com.example.nghia.maplocation;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
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

public class SearchActivity extends AppCompatActivity {


    String urlSelectToSearch = "http://192.168.56.1:8080/androidwebservice/selecttosearch.php";
    String urlRequestFriend = "http://192.168.56.1:8080/androidwebservice/requestfrienduser.php";
    String urlSearch = "http://192.168.56.1:8080/androidwebservice/search.php";

    ListView lvSearch;
    ArrayList<User> arrayUser;
    SearchAdapter adapter;

    ImageView imgSearch;

    //AutoComplete đã chạy
    AutoCompleteTextView autoSearch;
    ArrayList<String> contactList;

    ImageView buttonReturn;
    String emailuser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

              // AutoComplete cho ô tìm kiếm đã chạy
        contactList = new ArrayList<String>();
        autoSearch = findViewById(R.id.autoSearch);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line,contactList);
        autoSearch.setAdapter(arrayAdapter);
        autoSearch.setThreshold(1);
        autoSearch.setDropDownHeight(400);

        imgSearch = findViewById(R.id.imgSearch);
        imgSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ReadJSONSelect(urlSearch);
            }
        });

        lvSearch = findViewById(R.id.lvSearch);
        arrayUser = new ArrayList<>();
        adapter = new SearchAdapter(this,R.layout.row_search_user,arrayUser);
        lvSearch.setAdapter(adapter);

        buttonReturn = findViewById(R.id.buttonReturn);
        buttonReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        Intent intent = getIntent();
        emailuser = intent.getStringExtra("emailuser");
//        Toast.makeText(this, "Search Friend of " + emailuser, Toast.LENGTH_SHORT).show();

        ReadJSONAutoComplete(urlSelectToSearch);
    }
    private void ReadJSONAutoComplete (String url){

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONArray jsonArray = new JSONArray(response);

//                            Toast.makeText(SearchActivity.this, jsonArray.toString(), Toast.LENGTH_SHORT).show();
                            Log.d("BBB","Loi ! \n"+jsonArray.toString());

                            arrayUser.clear();
                            for (int i = 0 ; i<= jsonArray.length(); i++) {
                                JSONObject object = jsonArray.getJSONObject(i);

                                //AutoComplete đã chạy
                                String givenName = object.getString("GivenName");
                                String name = object.getString("Name");
                                String email = object.getString("Email");
//
//                                // adding each child node to HashMap key => value
                                contactList.add(givenName +" "+ name);
                                contactList.add( email);

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
//                        Toast.makeText(MainActivity.this, response.toString(), Toast.LENGTH_SHORT).show();
//                        Log.d("AAA","Loi ! \n"+response.toString());

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(SearchActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String> params = new HashMap<>();

                params.put("emailUser",emailuser);

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

    private void ReadJSONSelect (String url){

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONArray jsonArray = new JSONArray(response);

//                            Toast.makeText(SearchActivity.this, jsonArray.toString(), Toast.LENGTH_SHORT).show();
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
//                        Toast.makeText(MainActivity.this, response.toString(), Toast.LENGTH_SHORT).show();
//                        Log.d("AAA","Loi ! \n"+response.toString());

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(SearchActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String> params = new HashMap<>();

                params.put("emailUser",emailuser);

                params.put("searchUser",autoSearch.getText().toString());

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

    public void ReadJSONRequestFriend (final String email){

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlRequestFriend,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.trim().equals("request success")){
                            Toast.makeText(SearchActivity.this, "Đã Gửi Yêu Cầu !", Toast.LENGTH_SHORT).show();

                        }else if (response.trim().equals("request error")){
                            Toast.makeText(SearchActivity.this, "Yêu Cầu Thất Bại !", Toast.LENGTH_SHORT).show();
                        }
                        ReadJSONSelect(urlSearch);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(SearchActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String> params = new HashMap<>();

                params.put("emailUser",emailuser);

                params.put("emailFriend",email);

                return params;
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
        super.onBackPressed();
    }
}
