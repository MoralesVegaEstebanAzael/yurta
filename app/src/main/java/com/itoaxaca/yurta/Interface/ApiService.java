package com.itoaxaca.yurta.Interface;

import com.itoaxaca.yurta.response.Obras;
import com.itoaxaca.yurta.pojos.Usuario;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiService {

    @Headers("Content-Type: application/json")
    @POST("login")
    Call<Usuario> getUser(@Body String body);
    //@GET("login")

    
    @GET("login")
    Call<Usuario> getLogin(@Query("email") String email, @Query("password") String password);


    @GET("obras")
    Call<Obras> getObras(@Query("id") String id);
}
