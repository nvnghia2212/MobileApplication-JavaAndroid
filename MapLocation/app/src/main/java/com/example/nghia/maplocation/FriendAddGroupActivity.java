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

public class FriendAddGroupActivity extends AppCompatActivity {

    String urlSelectFriendAddGroup  = "http://192.168.56.1:8080/androidwebservice/selectfriendaddgroup.php";
    String urlInsert                = "http://192.168.56.1:8080/androidwebservice/insertpeoplegroup.php";

    String urlInsertMessGroup = "http://192.168.56.1:8080/androidwebservice/insertchatgroup.php";

    ListView lvFriendAddGroup;
    ArrayList<User> arrayUser;
    FriendAddGroupAdapter adapter;
    String emailSend,nameGroup,userAdd,dateTimeAdd;
    ImageView buttonReturn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_add_group);

        Intent intent = getIntent();
        emailSend = intent.getStringExtra("emailSend");

        nameGroup = intent.getStringExtra("nameGroup");
        userAdd = intent.getStringExtra("userAdd");
        dateTimeAdd = intent.getStringExtra("dateTimeAdd");

        lvFriendAddGroup = findViewById(R.id.lvFriendAddGroup);
        arrayUser = new ArrayList<>();
        adapter = new FriendAddGroupAdapter(this,R.layout.row_friend_add_group,arrayUser);
        lvFriendAddGroup.setAdapter(adapter);

        buttonReturn = findViewById(R.id.buttonReturn);
        buttonReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        ReadJSONSelect(urlSelectFriendAddGroup);
    }

    private void ReadJSONSelect (String url){

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONArray jsonArray = new JSONArray(response);

//                            Toast.makeText(FriendAddGroupActivity.this, jsonArray.toString(), Toast.LENGTH_SHORT).show();
//                            Log.d("BBB","Loi ! \n"+jsonArray.toString());

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
//                        Toast.makeText(FriendAddGroupActivity.this, response.toString(), Toast.LENGTH_SHORT).show();
                        Log.d("AAA","Loi ! \n"+response.toString());

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(FriendAddGroupActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String> params = new HashMap<>();

                params.put("emailUser",emailSend);

                params.put("nameGroup",nameGroup);
                params.put("userAdd",userAdd);
                params.put("dateTimeAdd",dateTimeAdd);

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

    public void ReadJSONInsert(final String userMember){

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlInsert,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.trim().equals("insertgrouppeoplesuccess")){
                            Toast.makeText(FriendAddGroupActivity.this, "Đã Thêm !", Toast.LENGTH_SHORT).show();
                        }else if (response.trim().equals("insertgrouppeopleerror")){
                            Toast.makeText(FriendAddGroupActivity.this, "Thêm Thất Bại !", Toast.LENGTH_SHORT).show();
                        }
                        ReadJSONSelect(urlSelectFriendAddGroup);

//                        Toast.makeText(MainActivity.this, response.toString(), Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                Toast.makeText(GroupActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                Log.d("AAA","Loi ! \n"+error.toString());
            }
        }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String> params = new HashMap<>();

                params.put("nameGroup",nameGroup);
                params.put("emailUser",userAdd);
                params.put("dateTimeAdd",dateTimeAdd);

                params.put("member",userMember);


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

    public void ReadJSONInsertMessGroup (final String userMember){

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlInsertMessGroup,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.trim().equals("insertchatgroupsuccess")){
//                            Toast.makeText(MessengerActivity.this, "insert success", Toast.LENGTH_SHORT).show();
//                            editMess.setText("");
                        }else if (response.trim().equals("insertchatgrouperror")){
//                            Toast.makeText(GroupPeopleActivity.this, "Gửi không thành công !", Toast.LENGTH_SHORT).show();
                        }
//                        ReadJSONSelectMessGroup(urlSelectMessGroup);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(FriendAddGroupActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String> params = new HashMap<>();

                params.put("nameGroup",nameGroup);
                params.put("userAdd",userAdd);
                params.put("dateTimeAdd",dateTimeAdd);

                params.put("emailSend",emailSend);
                params.put("textMess","Đã Thêm"+" "+userMember+" "+"Vào Nhóm !");
                params.put("dateMess","");
                params.put("timeMess","");

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

    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount()>0)
        {
            getFragmentManager().popBackStack();
        }else {
            super.onBackPressed();
        }

    }
}
