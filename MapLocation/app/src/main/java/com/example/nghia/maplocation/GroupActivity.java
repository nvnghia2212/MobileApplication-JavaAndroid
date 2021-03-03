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

public class GroupActivity extends AppCompatActivity {

    String urlSelectGroup = "http://192.168.56.1:8080/androidwebservice/selectgroupaddnew.php";

    String urlInsert = "http://192.168.56.1:8080/androidwebservice/insertgroupaddnew.php";

    String urlDeleteGroup = "http://192.168.56.1:8080/androidwebservice/deletegroupaddnew.php";

    String urlLeaveGroup = "http://192.168.56.1:8080/androidwebservice/deletepeoplegroup.php";

    String urlInsertMessGroup = "http://192.168.56.1:8080/androidwebservice/insertchatgroup.php";


    static String emailSend;

    ListView lvGroup;
    ArrayList<Group> arrayGroup;
    GroupAdapter adapter;

    ImageView buttonReturn;

    FragmentManager fragmentManager = getFragmentManager();
    android.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group);

        Intent intent = getIntent();
        emailSend = intent.getStringExtra("emailuser");

        lvGroup = findViewById(R.id.lvGroup);
        arrayGroup = new ArrayList<>();
        adapter = new GroupAdapter(this,R.layout.row_group,arrayGroup);
        lvGroup.setAdapter(adapter);

        buttonReturn = findViewById(R.id.buttonReturn);
        buttonReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        ReadJSONSelect(urlSelectGroup);
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

            FragmentAddGroup fragmentAddGroup = (FragmentAddGroup) getFragmentManager().findFragmentByTag("FragmentAddGroup");
            if (fragmentAddGroup != null)
            {
                fragmentTransaction.remove(fragmentAddGroup);
            }

            fragmentManager.popBackStack();
            fragmentTransaction.add(R.id.framConten,new FragmentAddGroup(),"FragmentAddGroup");
            fragmentTransaction.addToBackStack("");
            fragmentTransaction.commit();
        }
        return super.onOptionsItemSelected(item);
    }

    //Nút quay lại
    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount()>0)
        {
            getFragmentManager().popBackStack();
        }else {
            super.onBackPressed();
        }

    }

    public void ReadJSONInsert(final String nameGroup, final String dateTimeAdd){

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlInsert,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.trim().equals("insertgroupsuccess")){
                            Toast.makeText(GroupActivity.this, "Thêm Thành Công !", Toast.LENGTH_SHORT).show();
                        }else if (response.trim().equals("insertgrouperror")){
                            Toast.makeText(GroupActivity.this, "Thêm Thất Bại !", Toast.LENGTH_SHORT).show();
                        }
                        ReadJSONSelect(urlSelectGroup);

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

                params.put("emailUser",emailSend);
                params.put("nameGroup",nameGroup);
                params.put("dateTimeAdd",dateTimeAdd);

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

    private void ReadJSONSelect (String url){

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONArray jsonArray = new JSONArray(response);

//                            Toast.makeText(GroupActivity.this, jsonArray.toString(), Toast.LENGTH_SHORT).show();
                            Log.d("BBB","Loi ! \n"+jsonArray.toString());

                            arrayGroup.clear();
                            adapter.notifyDataSetChanged();
                            for (int i = 0 ; i<= jsonArray.length(); i++) {
                                JSONObject object = jsonArray.getJSONObject(i);

                                arrayGroup.add(new Group(
                                        object.getInt("ID"),
                                        object.getString("NameGroup"),
                                        object.getString("UserAdd"),
                                        object.getString("DateTimeAdd")
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
                Toast.makeText(GroupActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                Log.d("AAA","Loi ! \n"+error.toString());
            }
        }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String> params = new HashMap<>();

                params.put("emailUser",emailSend);
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

    public void ReadJSONDelete (final String nameGroup, final String userAdd, final String dateTimeAdd){

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlDeleteGroup,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.trim().equals("deletegroupsuccess")){
                            Toast.makeText(GroupActivity.this, "Đã Xóa !", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(GroupActivity.this, "Xóa Thất Bại !", Toast.LENGTH_SHORT).show();
                        }
                        ReadJSONSelect(urlSelectGroup);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(GroupActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String> params = new HashMap<>();

                params.put("emailUser",userAdd);
                params.put("nameGroup",nameGroup);
                params.put("dateTimeAdd",dateTimeAdd);

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

    public void ReadJSONLeave (final String userMember, final String nameGroup, final String userAdd, final String dateTimeAdd){

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlLeaveGroup,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.trim().equals("deletepeoplegroupsuccess")){
                            Toast.makeText(GroupActivity.this, "Đã Rời !", Toast.LENGTH_SHORT).show();
                        }else{
//                            Toast.makeText(GroupActivity.this, "deletepeoplegrouperror", Toast.LENGTH_SHORT).show();
                        }
                        ReadJSONSelect(urlSelectGroup);

//                        Toast.makeText(GroupActivity.this, response.toString(), Toast.LENGTH_SHORT).show();
                        Log.d("s","loi ! " + response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(GroupActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String> params = new HashMap<>();

                params.put("userAdd",userAdd);
                params.put("nameGroup",nameGroup);
                params.put("dateTimeAdd",dateTimeAdd);

                params.put("userMember",userMember);

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

    public void ReadJSONInsertMessGroup (final String userMember, final String nameGroup, final String userAdd, final String dateTimeAdd){

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
                Toast.makeText(GroupActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
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
                params.put("textMess",userMember+" "+"Đã Rời Nhóm !");
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

    public void IntentFriendAddGroup (final String nameGroup, final String userAdd, final String dateTimeAdd){

        Intent intent = new Intent(GroupActivity.this,FriendAddGroupActivity.class);

        intent.putExtra("emailSend",emailSend);
        intent.putExtra("nameGroup",nameGroup);
        intent.putExtra("userAdd",userAdd);
        intent.putExtra("dateTimeAdd",dateTimeAdd);
        startActivity(intent);

    }

    public void IntentGroupPeople (final String nameGroup, final String userAdd, final String dateTimeAdd){

        Intent intent = new Intent(GroupActivity.this,GroupPeopleActivity.class);

        intent.putExtra("emailSend",emailSend);
        intent.putExtra("nameGroup",nameGroup);
        intent.putExtra("userAdd",userAdd);
        intent.putExtra("dateTimeAdd",dateTimeAdd);
        startActivity(intent);

    }

    public void IntentChatGroup (final String nameGroup, final String userAdd, final String dateTimeAdd){

        Intent intent = new Intent(GroupActivity.this,ChatGroupActivity.class);

        intent.putExtra("emailSend",emailSend);
        intent.putExtra("nameGroup",nameGroup);
        intent.putExtra("userAdd",userAdd);
        intent.putExtra("dateTimeAdd",dateTimeAdd);
        startActivity(intent);

    }

}
