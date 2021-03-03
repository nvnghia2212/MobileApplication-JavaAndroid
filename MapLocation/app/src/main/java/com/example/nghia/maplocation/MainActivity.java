package com.example.nghia.maplocation;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.nghia.maplocation.map.ServiceAPI;
import com.example.nghia.maplocation.map.direction.DirectionRoot;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.maps.android.PolyUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends Activity implements
        OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        GoogleMap.OnMarkerClickListener,
        View.OnClickListener{

    String urlInsertUpdate       = "http://192.168.2.19:8080/androidwebservice/updateorinsertuser.php";
    String urlSelectFriend       = "http://192.168.56.1:8080/androidwebservice/selectfriend.php";
    static String urlSelectMemberDating = "http://192.168.56.1:8080/androidwebservice/selectmemberdating.php";
    String urlDating             = "http://192.168.56.1:8080/androidwebservice/selectdating.php";

//GG Sign-in
    public static GoogleSignInClient mGoogleSignInClient;
    int RC_SIGN_IN = 277;
    ImageView imgLogout;
    ImageView imgMove;
    ImageView imgStop;
    ImageView imgTimeDating;
    ImageView imgDirect;
    static ImageView imgFriendMarker;
    static ImageView imgNotFriendMarker;
    String email,displayNameUser,givenNameUser,familyNameUser;
    TextView txtName;

    private static GoogleMap googleMap;
    private GoogleApiClient apiClient;
    private LocationRequest locationRequest;

    private Location currentLocation;

// Vẽ tuyến đường
    private static List<LatLng> decodedPath;
    private static String polylines;

    private static Retrofit retrofit;

    private static final int GPS_PERMISSION_REQUEST_CODE = 1;

//Floating Action Menu
    FloatingActionMenu floatingFriendMenu,floatingMapMenu;
    FloatingActionButton floatingSearchFriend,floatingRequestFriend,floatingFriend,floatingSearchMap,floatingDirection,floatingChatGroup;

    FragmentManager fragmentManager = getFragmentManager();
    android.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

//auto update
    private Handler mHandler = new Handler();
//intent dating
    String nameAddress,dateAddress,dateTimeAdd,userAdd;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initRetrofit();

        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

// GG Sign-in
        //yêu cầu ng dùng cung cấp thông tin cơ bản
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        //tạo một đối tượng GoogleSignInClient
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        //tạo nút signin và gán sự kiện setOnclick
        SignInButton signInButton = findViewById(R.id.sign_in_button);
        signInButton.setSize(SignInButton.SIZE_STANDARD);
        findViewById(R.id.sign_in_button).setOnClickListener(this);

        //nút logout
        imgLogout = findViewById(R.id.imgLogout);
        findViewById(R.id.imgLogout).setOnClickListener(this);

        txtName = findViewById(R.id.txtName);

        imgMove = findViewById(R.id.imgMove);
        findViewById(R.id.imgMove).setOnClickListener(this);

        imgStop = findViewById(R.id.imgStop);
        findViewById(R.id.imgStop).setOnClickListener(this);

        imgFriendMarker = findViewById(R.id.imgFriendMarker);
        findViewById(R.id.imgFriendMarker).setOnClickListener(this);

        imgNotFriendMarker = findViewById(R.id.imgNotFriendMarker);
        findViewById(R.id.imgNotFriendMarker).setOnClickListener(this);

        imgTimeDating = findViewById(R.id.imgTimeDating);
        findViewById(R.id.imgTimeDating).setOnClickListener(this);

        imgDirect = findViewById(R.id.imgDirect);

//Floating Action Menu
        floatingFriendMenu = findViewById(R.id.floatingFriendMenu);
        floatingMapMenu = findViewById(R.id.floatingMapMenu);

        floatingSearchFriend = findViewById(R.id.floatingSearchFriend);
        findViewById(R.id.floatingSearchFriend).setOnClickListener(this);

        floatingRequestFriend = findViewById(R.id.floatingRequestFriend);
        findViewById(R.id.floatingRequestFriend).setOnClickListener(this);

        floatingFriend = findViewById(R.id.floatingFriend);
        findViewById(R.id.floatingFriend).setOnClickListener(this);

        floatingSearchMap = findViewById(R.id.floatingSearchMap);
        findViewById(R.id.floatingSearchMap).setOnClickListener(this);

        floatingDirection = findViewById(R.id.floatingDirection);
        findViewById(R.id.floatingDirection).setOnClickListener(this);

        floatingChatGroup = findViewById(R.id.floatingChatGroup);
        findViewById(R.id.floatingChatGroup).setOnClickListener(this);


    }
    private void initRetrofit(){
        retrofit = new Retrofit.Builder()
                .baseUrl("https://maps.googleapis.com/maps/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    private void buildApiClient(){
        apiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        apiClient.connect();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case GPS_PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults [0] == PackageManager.PERMISSION_GRANTED){
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) ==
                            PackageManager.PERMISSION_GRANTED){
                        if (apiClient == null){
                            buildApiClient();
                        }
                        googleMap.setMyLocationEnabled(true);
                    }else {
//                        Toast.makeText(getApplicationContext(),"Error",Toast.LENGTH_SHORT).show();
                    }
                }
                break;
        }
    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {
        this.googleMap = googleMap;
        this.googleMap.setOnMarkerClickListener(this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED ) {
            buildApiClient();
            this.googleMap.setMyLocationEnabled(true);
        }else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                    requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, GPS_PERMISSION_REQUEST_CODE);
            }
        }
    }

    private void clickMyLocation(){
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            final FusedLocationProviderClient mF = LocationServices.getFusedLocationProviderClient(this);
            mF.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    if (location != null) {
                        currentLocation = location;
                        LatLng myLocation = new LatLng(currentLocation.getLatitude(),currentLocation.getLongitude());
                        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myLocation, 17));
                    }
                }
            });
        }
    }

//    Lấy vị trí
    private void getLocation (){
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            final FusedLocationProviderClient mF = LocationServices.getFusedLocationProviderClient(this);
            mF.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            if (location != null) {
                                currentLocation = location;

                                LatLng myLocation = new LatLng(currentLocation.getLatitude(),currentLocation.getLongitude());
                                if (txtName.getText().toString().equals("")){
                                    SignIn();
                                }else {
                                    ReadJSONInsertUpdate(1);
                                }

                            }
                        }
                    });
        }
    }

// Vẽ tuyến đường
    public static void getDirection(LatLng origin, LatLng destination){
        //Chưa test xóa đc vì chưa đăng kí đc api <==================================== Chưa Test Đc
//        if (polylines != null){
//            decodedPath.remove(polylines);
//        }

        ServiceAPI serviceAPI = retrofit.create(ServiceAPI.class);
        String originAddress = String.valueOf(origin.latitude) + "," + String.valueOf(origin.longitude);
        String destinationAddress = String.valueOf(destination.latitude) + "," + String.valueOf(destination.longitude);

        Call<DirectionRoot> call = serviceAPI.getDirection(originAddress,destinationAddress);
        call.enqueue(new Callback<DirectionRoot>() {
            @Override
            public void onResponse(Call<DirectionRoot> call, Response<DirectionRoot> response) {
                DirectionRoot directionRoot = response.body();
//                String polylines = directionRoot.getRoutes().get(0).getOverview_polyline().getPoints();
                polylines = directionRoot.getRoutes().get(0).getOverview_polyline().getPoints();

//                List<LatLng> decodedPath = PolyUtil.decode(polylines);
                decodedPath = PolyUtil.decode(polylines);
                googleMap.addPolyline(new PolylineOptions().addAll(decodedPath));
                decodedPath.remove(polylines);
            }

            @Override
            public void onFailure(Call<DirectionRoot> call, Throwable t) {
            }
        });
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        locationRequest = new LocationRequest();
        locationRequest.setInterval(1000);
        locationRequest.setFastestInterval(1000);
        locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
//        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        getLocation();
        clickMyLocation();
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    // Click Marker
    @Override
    public boolean onMarkerClick(final Marker marker) {

        imgDirect.setVisibility(View.GONE);
        imgDirect.setVisibility(View.VISIBLE);
        imgDirect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LatLng currLatLng = new LatLng(currentLocation.getLatitude(),currentLocation.getLongitude());
                getDirection(currLatLng, marker.getPosition());
                imgDirect.setVisibility(View.GONE);
            }
        });

        return false;
    }

    //Add Maker
    public static void AddMarker(Double latitude, Double longitude ,String title, String snippet) {

        googleMap.clear();
        imgFriendMarker.setVisibility(View.VISIBLE);
        imgNotFriendMarker.setVisibility(View.GONE);

        LatLng Location = new LatLng(latitude,longitude);
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(Location, 17));
        googleMap.addMarker(new MarkerOptions()
                .title(title)
                .snippet(snippet)
                .position(Location)).showInfoWindow();
    }

//    ON Click
    @Override
    public void onClick(View v) {

        Intent intent = null;

        switch (v.getId()) {

            case R.id.imgTimeDating:
                intent = new Intent(MainActivity.this, DatingActivity.class);
                intent.putExtra("emailuser", email);
                startActivity(intent);
                break;

            case R.id.imgFriendMarker:
                ReadJSONSelectAllFriend(urlSelectFriend);
                imgFriendMarker.setVisibility(View.GONE);
                imgNotFriendMarker.setVisibility(View.VISIBLE);
                imgDirect.setVisibility(View.GONE);
                break;

            case R.id.imgNotFriendMarker:
                googleMap.clear();
                imgFriendMarker.setVisibility(View.VISIBLE);
                imgNotFriendMarker.setVisibility(View.GONE);
                imgDirect.setVisibility(View.GONE);
                break;

            case R.id.imgMove:
                AlertDialog.Builder dialogMove = new AlertDialog.Builder(this);
                dialogMove.setMessage("Bạn muốn bật chế độ di chuyển");
                dialogMove.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        autoClickMyLocation();
                        imgMove.setVisibility(View.GONE);
                        imgStop.setVisibility(View.VISIBLE);
                        imgDirect.setVisibility(View.GONE);
                    }
                });
                dialogMove.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                dialogMove.show();
                break;

            case R.id.imgStop:
                AlertDialog.Builder dialogStop = new AlertDialog.Builder(this);
                dialogStop.setMessage("Bạn muốn ngừng chế độ di chuyển");
                dialogStop.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mHandler.removeCallbacks(autoZoom);
                        imgMove.setVisibility(View.VISIBLE);
                        imgStop.setVisibility(View.GONE);
                        imgDirect.setVisibility(View.GONE);

                    }
                });
                dialogStop.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                dialogStop.show();

                break;

            case R.id.imgLogout:
                mHandler.removeCallbacks(autoUpload);
                mHandler.removeCallbacks(autoZoom);
                SignOut();
                SignIn();
                break;

            case R.id.floatingSearchFriend:
//                Toast.makeText(MainActivity.this,email,Toast.LENGTH_SHORT).show();

                intent = new Intent(MainActivity.this, SearchActivity.class);
                intent.putExtra("emailuser", email);
                startActivity(intent);

                break;

            case R.id.floatingRequestFriend:
//                Toast.makeText(MainActivity.this,email,Toast.LENGTH_SHORT).show();

                intent = new Intent(MainActivity.this, RequestFriendActivity.class);
                intent.putExtra("emailuser", email);
                startActivity(intent);

                break;

            case R.id.floatingFriend:
//                Toast.makeText(MainActivity.this,email,Toast.LENGTH_SHORT).show();

                intent = new Intent(MainActivity.this, FriendActivity.class);
                intent.putExtra("emailuser", email);
                startActivity(intent);

                break;

            case R.id.floatingChatGroup:
//                Toast.makeText(MainActivity.this,email,Toast.LENGTH_SHORT).show();

                intent = new Intent(MainActivity.this, GroupActivity.class);
                intent.putExtra("emailuser", email);
                startActivity(intent);

                break;

            case R.id.floatingSearchMap:

                imgDirect.setVisibility(View.GONE);
                fragmentTransaction = fragmentManager.beginTransaction();

                FragmentDescriotionMap fragmentDescriptionMap = (FragmentDescriotionMap) getFragmentManager().findFragmentByTag("FragmentDescriptionMap");
                if (fragmentDescriptionMap != null)
                {
                    fragmentTransaction.remove(fragmentDescriptionMap);
                }

                fragmentManager.popBackStack();
                fragmentTransaction.add(R.id.framConten,new FragmentSearchMap(),"FragmentSearchMap");
                fragmentTransaction.addToBackStack("");
                fragmentTransaction.commit();

                break;
            case R.id.floatingDirection:

                imgDirect.setVisibility(View.GONE);
                fragmentTransaction = fragmentManager.beginTransaction();

                FragmentSearchMap fragmentSearchMap = (FragmentSearchMap) getFragmentManager().findFragmentByTag("FragmentSearchMap");
                if (fragmentSearchMap != null)
                {
                    fragmentTransaction.remove(fragmentSearchMap);
                }

                fragmentManager.popBackStack();
                fragmentTransaction.add(R.id.framConten,new FragmentDescriotionMap(),"FragmentDescriptionMap");
                fragmentTransaction.addToBackStack("");
                fragmentTransaction.commit();

                break;
        }

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            email = account.getEmail();
            familyNameUser = account.getFamilyName();
            givenNameUser = account.getGivenName();
            displayNameUser = account.getDisplayName();

            autoUp();

            txtName.setText(displayNameUser);
            txtName.setVisibility(View.VISIBLE);
            findViewById(R.id.sign_in_button).setVisibility(View.GONE);
            imgLogout.setVisibility(View.VISIBLE);

            AlertDialog.Builder dialogStop = new AlertDialog.Builder(this);
            dialogStop.setMessage("Nhắc Nhở :\nBạn nên kiểm tra danh sách những điểm hẹn thường xuyên để không quên thời gian !");
            dialogStop.setPositiveButton("Đồng Ý", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            dialogStop.show();

        } catch (ApiException e) {

        }
    }

        //Funtion GG signIn
    private void SignIn() {

        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

        //Funtion GG signOut
    private void SignOut() {

        MainActivity.mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

//                        Toast.makeText(MainActivity.this,email,Toast.LENGTH_SHORT).show();

                        ReadJSONInsertUpdate(0);

                        findViewById(R.id.sign_in_button).setVisibility(View.VISIBLE);
                        imgLogout.setVisibility(View.GONE);
                        txtName.setVisibility(View.GONE);

                    }
                });
    }

    //    Upload Đăng Nhập
    private void ReadJSONInsertUpdate (final int x){

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlInsertUpdate,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.trim().equals("updatesuccess")){
//                            Toast.makeText(MainActivity.this, "updatesuccess", Toast.LENGTH_SHORT).show();
                        }else if (response.trim().equals("updateerror")){
//                            Toast.makeText(MainActivity.this, "updateerror", Toast.LENGTH_SHORT).show();
                        }else if (response.trim().equals("addsuccess")){
//                            Toast.makeText(MainActivity.this, "addsuccess", Toast.LENGTH_SHORT).show();
                        }else if (response.trim().equals("adderror")) {
//                            Toast.makeText(MainActivity.this, "adderror", Toast.LENGTH_SHORT).show();
                        }

//                        Toast.makeText(MainActivity.this, response.toString(), Toast.LENGTH_SHORT).show();
                    }
                }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                Toast.makeText(MainActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                Log.d("AAA","Loi ! \n"+error.toString());
            }
        }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String> params = new HashMap<>();

                params.put("emailUser",email);
                params.put("givennameUser",familyNameUser);
                params.put("nameUser",givenNameUser);
                params.put("latitudeUser", String.valueOf(currentLocation.getLatitude()));
                params.put("longitudeUser",String.valueOf(currentLocation.getLongitude()));
                params.put("onloffUser", String.valueOf(x));

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

    private void autoUp (){
        mHandler.removeCallbacks(autoUpload);
        mHandler.postDelayed(autoUpload, 1000);
    }

    private Runnable autoUpload = new Runnable() {
        public void run() {

            getLocation();
            mHandler.postDelayed(autoUpload, 2000);
        }
    };

    private void autoClickMyLocation (){
        mHandler.removeCallbacks(autoZoom);
        mHandler.postDelayed(autoZoom, 1000);
    }

    private Runnable autoZoom = new Runnable() {
        public void run() {

            clickMyLocation();
            mHandler.postDelayed(autoZoom, 2000);
        }
    };

    private void ReadJSONSelectAllFriend (String url){

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONArray jsonArray = new JSONArray(response);

//                            Toast.makeText(FriendActivity.this, jsonArray.toString(), Toast.LENGTH_SHORT).show();
                            Log.d("BBB","Loi ! \n"+jsonArray.toString());

                            for (int i = 0 ; i<= jsonArray.length(); i++) {
                                JSONObject object = jsonArray.getJSONObject(i);

                                        int idJson              = object.getInt("ID");
                                        String emailJson        = object.getString("Email");
                                        String giveNameJson     = object.getString("GivenName");
                                        String nameJson         = object.getString("Name");
                                        Double latitudeJson     = object.getDouble("Latitude");
                                        Double longitudeNJson   = object.getDouble("Longitude");
                                        int onlOffJson          = object.getInt("OnlOff");

                                        if (onlOffJson == 1){

//                                            AddMarker(latitudeJson,longitudeNJson,giveNameJson +" "+nameJson,emailJson);
                                            LatLng Location = new LatLng(latitudeJson,longitudeNJson);
                                            googleMap.addMarker(new MarkerOptions()
                                                    .title(giveNameJson +" "+nameJson)
                                                    .snippet(emailJson)
                                                    .position(Location)
                                                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET)))
                                                    .showInfoWindow();
                                        }
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
//                        Toast.makeText(MainActivity.this, response.toString(), Toast.LENGTH_SHORT).show();
//                        Log.d("AAA","Loi ! \n"+response.toString());

                    }
                }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String> params = new HashMap<>();

                params.put("emailUser",email);

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

    public static void ReadJSONSelectMemberDating(final Context context,final String nameAddress, final String dateAddress, final String dateTimeAdd, final String userAdd){

        RequestQueue requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlSelectMemberDating,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONArray jsonArray = new JSONArray(response);

                            Log.d("BBB","Loi ! \n"+jsonArray.toString());

                            for (int i = 0 ; i<= jsonArray.length(); i++) {
                                JSONObject object = jsonArray.getJSONObject(i);

                                int idJson              = object.getInt("ID");
                                String emailJson        = object.getString("Email");
                                String giveNameJson     = object.getString("GivenName");
                                String nameJson         = object.getString("Name");
                                Double latitudeJson     = object.getDouble("Latitude");
                                Double longitudeNJson   = object.getDouble("Longitude");
                                int onlOffJson          = object.getInt("OnlOff");

                                if (onlOffJson == 1){

                                    LatLng Location = new LatLng(latitudeJson,longitudeNJson);
                                    googleMap.addMarker(new MarkerOptions()
                                            .title(giveNameJson +" "+nameJson)
                                            .snippet(emailJson)
                                            .position(Location)
                                            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)));
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show();
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

    @Override
    protected void onDestroy() {
        ReadJSONInsertUpdate(0);
        mHandler.removeCallbacks(autoUpload);
        mHandler.removeCallbacks(autoZoom);
        super.onDestroy();
    }
}
