package com.itoaxaca.yurta;
import com.itoaxaca.yurta.Interface.ApiService;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class ApiAdapter {
    private static ApiService API_SERVICE;
    public static ApiService getApiService(){
        String baseUrl = " https://yurtapp.herokuapp.com/api/";
        if(API_SERVICE==null){
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            API_SERVICE = retrofit.create(ApiService.class);
        }
        return API_SERVICE;
    }
}