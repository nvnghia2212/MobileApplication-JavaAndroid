package com.example.nghia.maplocation;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class FriendActivity extends AppCompatActivity {


    String urlSelectFriend = "http://192.168.56.1:8080/androidwebservice/selectfriend.php";

    String urlDeleteFriend = "http://192.168.56.1:8080/androidwebservice/deletefrienduser.php";

    ListView lvFriendUser;
    ArrayList<User> arrayUser;
    FriendUserAdapter adapter;


    ImageView buttonReturn;

    String emailuser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend);

        lvFriendUser = findViewById(R.id.lvFriendUser);
        arrayUser = new ArrayList<>();
        adapter = new FriendUserAdapter(this,R.layout.row_friend_user,arrayUser);
        lvFriendUser.setAdapter(adapter);

        buttonReturn = findViewById(R.id.buttonReturn);
        buttonReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        Intent intent = getIntent();
        emailuser = intent.getStringExtra("emailuser");
//        Toast.makeText(this, "Friend of " + emailuser, Toast.LENGTH_SHORT).show();

        ReadJSONSelect(urlSelectFriend);
    }

    //Select danh sách
    private void ReadJSONSelect (String url){

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONArray jsonArray = new JSONArray(response);

//                            Toast.makeText(FriendActivity.this, jsonArray.toString(), Toast.LENGTH_SHORT).show();
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
                Toast.makeText(FriendActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
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

    //Xóa
    public void ReadJSONDelete (final String email){

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlDeleteFriend,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.trim().equals("delete friends success")){
                            Toast.makeText(FriendActivity.this, "Đã Xóa !", Toast.LENGTH_SHORT).show();

                        }else{
                            Toast.makeText(FriendActivity.this, "Xóa Thất Bại !", Toast.LENGTH_SHORT).show();
                        }
                        ReadJSONSelect(urlSelectFriend);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(FriendActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
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

    public void IntentMess (String email){

        Intent intent = new Intent(FriendActivity.this,MessengerActivity.class);

        intent.putExtra("emailSend",emailuser);
        intent.putExtra("emailReceive",email);
        startActivity(intent);

    }
}
