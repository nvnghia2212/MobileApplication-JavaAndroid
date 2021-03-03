package com.example.nghia.maplocation.map;

import com.example.nghia.maplocation.map.direction.DirectionRoot;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ServiceAPI {
    @GET("directions/json?&key=AIzaSyCeBgOb0qxSn_HimrAMoSDDpVk2EbVQk8M")
    Call<DirectionRoot>getDirection(@Query("origin") String origin,
                                    @Query("destination") String destination);
}
