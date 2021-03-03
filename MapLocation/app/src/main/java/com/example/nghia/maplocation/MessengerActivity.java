package com.example.nghia.maplocation;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class MessengerActivity extends AppCompatActivity implements View.OnClickListener {

    TextView txtName;
    EditText editMess;
    ImageView imgSend,imgRemove;

    ListView lvMess;
    ArrayList<SendMessenger> arrayMess;
    MessengerAdapter adapter;

    static String emailSend;
    static String emailReceived;
    Calendar calendar = Calendar.getInstance();
    SimpleDateFormat simpleDateFormatDay = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat simpleDateFormatTime= new SimpleDateFormat("HH:mm");
    String date,time;

    private Handler mHandler = new Handler();
    String urlSelectMess = "http://192.168.56.1:8080/androidwebservice/selectmess.php";
    String urlInsertMess = "http://192.168.56.1:8080/androidwebservice/insertmess.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messenger);

        Intent intent = getIntent();
        emailSend = intent.getStringExtra("emailSend");
        emailReceived = intent.getStringExtra("emailReceive");

        txtName = findViewById(R.id.txtName);
        txtName.setText(emailReceived);
        txtName.setTextColor(Color.BLUE);

        editMess = findViewById(R.id.editMess);

        lvMess = findViewById(R.id.lvMess);
        arrayMess = new ArrayList<>();
        adapter = new MessengerAdapter(this,R.layout.row_sendmess_user,arrayMess);
        lvMess.setAdapter(adapter);

        imgSend = findViewById(R.id.imgSend);
        imgSend.setOnClickListener(this);

        imgRemove = findViewById(R.id.imgRemove);
        imgRemove.setOnClickListener(this);

//        ReadJSONSelect(urlSelectMess);
        autoUp();
    }

    //    ON Click
    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.imgRemove:
                onBackPressed();
                break;

            case R.id.imgSend:
                calendar = Calendar.getInstance();
                date = simpleDateFormatDay.format(calendar.getTime());
                time = simpleDateFormatTime.format(calendar.getTime());
//                Toast.makeText(this,
//                        emailSend + "  ,  " + emailReceived +" , "+ editMess.getText() +" , "+ date +" , "+time,
//                        Toast.LENGTH_SHORT).show();

                if (editMess.getText().toString().equals("")){
                    Toast.makeText(this, "Bạn nên nhập gì đó !", Toast.LENGTH_SHORT).show();
                }else {
                    ReadJSONInsertMess(urlInsertMess);
                }
                break;
        }
    }

    private void autoUp (){
        mHandler.removeCallbacks(autoUpload);
        mHandler.postDelayed(autoUpload, 1000);
    }

    private Runnable autoUpload = new Runnable() {
        public void run() {
            ReadJSONSelect(urlSelectMess);
//            Toast.makeText(MessengerActivity.this, "abcxyz", Toast.LENGTH_SHORT).show();
            mHandler.postDelayed(autoUpload, 4000);
        }
    };

    private void ReadJSONSelect (String url){

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONArray jsonArray = new JSONArray(response);

//                            Toast.makeText(MessengerActivity.this, jsonArray.toString(), Toast.LENGTH_SHORT).show();
                            Log.d("BBB","Loi ! \n"+jsonArray.toString());

                            arrayMess.clear();
                            adapter.notifyDataSetChanged();
                            for (int i = 0 ; i<= jsonArray.length(); i++) {
                                JSONObject object = jsonArray.getJSONObject(i);


                                arrayMess.add(new SendMessenger(
                                        object.getInt("ID"),
                                        object.getString("EmailSend"),
                                        object.getString("TextMess"),
                                        object.getString("EmailReceived"),
                                        object.getString("DateMess"),
                                        object.getString("TimeMess")
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
                Toast.makeText(MessengerActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String> params = new HashMap<>();

                params.put("emailSend",emailSend);
                params.put("emailReceived",emailReceived);

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

    public void ReadJSONInsertMess (String url){

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.trim().equals("insertsuccess")){
//                            Toast.makeText(MessengerActivity.this, "insert success", Toast.LENGTH_SHORT).show();
                            editMess.setText("");
                        }else if (response.trim().equals("inserterror")){
                            Toast.makeText(MessengerActivity.this, "Gửi không thành công !", Toast.LENGTH_SHORT).show();
                        }
                        ReadJSONInsertMess(urlSelectMess);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MessengerActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String> params = new HashMap<>();

                params.put("emailSend",emailSend);
                params.put("textMess",editMess.getText().toString());
                params.put("emailReceived",emailReceived);
                params.put("dateMess", date);
                params.put("timeMess",time);

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

    @Override
    protected void onDestroy() {
        mHandler.removeCallbacks(autoUpload);
        super.onDestroy();
    }

}
