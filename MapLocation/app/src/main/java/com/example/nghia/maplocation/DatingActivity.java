package com.example.nghia.maplocation;

import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
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

public class DatingActivity extends AppCompatActivity {

    String urlSelectTestEmpty       = "http://192.168.56.1:8080/androidwebservice/selecttestempty.php";
    String urlSelectNewDating       = "http://192.168.56.1:8080/androidwebservice/selectnewdating.php";
    String urlInsetNewDating        = "http://192.168.56.1:8080/androidwebservice/insertnewdating.php";
    String urlInsetMemberDating     = "http://192.168.56.1:8080/androidwebservice/insertmemberdating.php";
    String urlDeleteMemberDating    = "http://192.168.56.1:8080/androidwebservice/deletememberdating.php";

    String emailUser;

    ListView lvDating;
    ArrayList<Dating> arrayDating;
    DatingAdapter adapter;

    ImageView buttonReturn;

    FragmentManager fragmentManager = getFragmentManager();
    android.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dating);

        Intent intent = getIntent();
        emailUser = intent.getStringExtra("emailuser");
//        emailUser = "thanhthien@gmail.com";
//        emailUser = "yennhi@gmail.com";

        lvDating = findViewById(R.id.lvDating);
        arrayDating = new ArrayList<>();
        adapter = new DatingAdapter(this,R.layout.row_dating,arrayDating);
        lvDating.setAdapter(adapter);

        buttonReturn = findViewById(R.id.buttonReturn);
        buttonReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        ReadJSONSelectNewDating(urlSelectNewDating);
    }

    public void ReadJSONDeleteMemberDating(final String nameAddress, final String dateAddress, final String dateTimeAdd, final String userAdd){

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlDeleteMemberDating,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.trim().equals("deletememberdatingsuccess")){
                            Toast.makeText(DatingActivity.this, "Hủy Hẹn Thành Công !", Toast.LENGTH_SHORT).show();
                        }else if (response.trim().equals("deletememberdatingerror")){
                            Toast.makeText(DatingActivity.this, "Hủy Hẹn Thất Bại !", Toast.LENGTH_SHORT).show();
                        }
                        ReadJSONSelectNewDating(urlSelectNewDating);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("AAA","Loi ! \n"+error.toString());
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
                params.put("emailUser",emailUser);

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

    public void ReadJSONInsertMemberDating(final String nameAddress, final String dateAddress, final String dateTimeAdd, final String userAdd){

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlInsetMemberDating,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.trim().equals("insertmemberdatingsuccess")){
                            Toast.makeText(DatingActivity.this, "Tham Gia Thành Công !", Toast.LENGTH_SHORT).show();
                        }else if (response.trim().equals("insertmemberdatingerror")){
                            Toast.makeText(DatingActivity.this, "Chưa Được Tham Gia !", Toast.LENGTH_SHORT).show();
                        }
                        ReadJSONSelectNewDating(urlSelectNewDating);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("AAA","Loi ! \n"+error.toString());
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
                params.put("emailUser",emailUser);

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

    public void ReadJSONInsertDating(final String nameAddress, final String dateAddress, final String dateTimeAdd){

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlInsetNewDating,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.trim().equals("insertdatingsuccess")){
                            Toast.makeText(DatingActivity.this, "Tạo Lịch Hẹn Thành Công !", Toast.LENGTH_SHORT).show();
                        }else if (response.trim().equals("insertdatingerror")){
                            Toast.makeText(DatingActivity.this, "Chưa Tạo Được Lịch Hẹn !", Toast.LENGTH_SHORT).show();
                        }
                        ReadJSONSelectNewDating(urlSelectNewDating);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("AAA","Loi ! \n"+error.toString());
            }
        }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String> params = new HashMap<>();


                params.put("nameAddress",nameAddress);
                params.put("dateAddress",dateAddress);
                params.put("dateTimeAdd",dateTimeAdd);
                params.put("emailUser",emailUser);

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

    private void ReadJSONSelectNewDating (String url){

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONArray jsonArray = new JSONArray(response);

                            Log.d("BBB","Loi ! \n"+jsonArray.toString());

                            arrayDating.clear();
                            adapter.notifyDataSetChanged();
                            for (int i = 0 ; i<= jsonArray.length(); i++) {
                                JSONObject object = jsonArray.getJSONObject(i);

                                arrayDating.add(new Dating(
                                        object.getInt("ID"),
                                        object.getString("NameAddress"),
                                        object.getString("DateAddress"),
                                        object.getString("DateTimeAdd"),
                                        object.getString("UserAdd")
                                ));
                                adapter.notifyDataSetChanged();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(DatingActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                Log.d("AAA","Loi ! \n"+error.toString());
            }
        }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String> params = new HashMap<>();

                params.put("emailUser",emailUser);
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_group,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menuAddGroup){

            fragmentTransaction = fragmentManager.beginTransaction();

            FragmentDating fragmentDating = (FragmentDating) getFragmentManager().findFragmentByTag("FragmentDating");
            if (fragmentDating != null)
            {
                fragmentTransaction.remove(fragmentDating);
            }
            fragmentManager.popBackStack();
            fragmentTransaction.add(R.id.framConten,new FragmentDating(),"FragmentDating");
            fragmentTransaction.addToBackStack("");
            fragmentTransaction.commit();
        }
        return super.onOptionsItemSelected(item);
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

    public void IntentMemberDating (final String nameAddress, final String dateAddress, final String dateTimeAdd, final String userAdd){

        Intent intent = new Intent(DatingActivity.this,MemberDatingActivity.class);

        intent.putExtra("nameAddress",nameAddress);
        intent.putExtra("dateAddress",dateAddress);
        intent.putExtra("dateTimeAdd",dateTimeAdd);
        intent.putExtra("userAdd",userAdd);

        startActivity(intent);

    }

//    public void IntentMapActivity (final String nameAddress, final String dateAddress, final String dateTimeAdd, final String userAdd){
//
//        Intent intent = new Intent(DatingActivity.this,MainActivity.class);
//
//        intent.putExtra("nameAddress",nameAddress);
//        intent.putExtra("dateAddress",dateAddress);
//        intent.putExtra("dateTimeAdd",dateTimeAdd);
//        intent.putExtra("userAdd",userAdd);
//
//        startActivity(intent);
//
//    }
}
