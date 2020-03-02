package com.itoaxaca.yurta.Interface;

import com.itoaxaca.yurta.response.AlmacenResponse;
import com.itoaxaca.yurta.response.DetPedidoResponse;
import com.itoaxaca.yurta.response.DetPedidosResponse;
import com.itoaxaca.yurta.response.Materiales;
import com.itoaxaca.yurta.response.Obras;
import com.itoaxaca.yurta.pojos.Usuario;
import com.itoaxaca.yurta.response.PedidoResponse;
import com.itoaxaca.yurta.response.PedidosResponse;
import com.itoaxaca.yurta.response.TiposObraResponse;

import io.reactivex.Observable;
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

    /**MIDDLEWERE**/

    /*@GET("obras")
    Call<Obras> getObras(@Query("api_token") String api_token, @Query("id") String id);
    @GET("tipos_obra")
    Call<TiposObraResponse> getTiposObra(@Query("api_token") String api_token);
    @GET("almacen_obras")
    Call<AlmacenResponse> getAlmacen(@Query("api_token") String api_token,@Query("id") String id);
    @GET("pedidos_obras")
    Call<PedidosResponse> getPedidos(@Query("api_token") String api_token, @Query("id") String id);
    @GET("det_pedidos_obras")
    Call<DetPedidosResponse> getDetPedidos(@Query("api_token") String api_token, @Query("id") String id);*/

    @GET("materiales")
    Call<Materiales> getMateriales(@Query("api_token") String api_token);

    //observables
    @GET("almacen_obras")
    Observable<AlmacenResponse> getAlmacen(@Query("api_token") String api_token,@Query("id") String id);
    @GET("obras")
    Observable<Obras> getObras(@Query("api_token") String api_token, @Query("id") String id);
    @GET("tipos_obra")
    Observable<TiposObraResponse> getTiposObras(@Query("api_token") String api_token);
    @GET("pedidos_obras")
    Observable<PedidosResponse> getPedidos(@Query("api_token") String api_token,@Query("id") String id);
    @GET("det_pedidos_obras")
    Observable<DetPedidosResponse> getDetPedidos(@Query("api_token") String api_token,@Query("id") String id);


   }
