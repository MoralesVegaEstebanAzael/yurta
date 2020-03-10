package com.itoaxaca.yurta.Interface;

import com.itoaxaca.yurta.pojos.DetallePedido;
import com.itoaxaca.yurta.pojos.Pedido;
import com.itoaxaca.yurta.response.AlmacenResponse;
import com.itoaxaca.yurta.response.CountNotificaciones;
import com.itoaxaca.yurta.response.DetPedidoResponse;
import com.itoaxaca.yurta.response.DetPedidosResponse;
import com.itoaxaca.yurta.response.Materiales;
import com.itoaxaca.yurta.response.NotificacionesResponse;
import com.itoaxaca.yurta.response.Obras;
import com.itoaxaca.yurta.pojos.Usuario;
import com.itoaxaca.yurta.response.PedidoResponse;
import com.itoaxaca.yurta.response.PedidosResponse;
import com.itoaxaca.yurta.response.TiposObraResponse;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiService {

    @Headers("Content-Type: application/json")
    @POST("login")
    Call<Usuario> getUser(@Body String body);
    //@GET("login")

    @GET("login")
    Call<Usuario> getLogin(@Query("email") String email,
                           @Query("password") String password,
                           @Query("fcm_token") String fcm_token);

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

    @POST("update_fcm_token")
    @FormUrlEncoded
    Call<Usuario> updateFCMtoken(@Field("api_token") String api_token,
                                    @Field("id") String id,
                                    @Field("fcm_token") String fcm_token);

    @GET("det_pedidos_obras")
    Call<DetPedidosResponse> getDetallesPedidos(@Query("api_token") String api_token, @Query("id") String id);
    //obtener las notificaciones
    @GET("notificaciones")
    Call<NotificacionesResponse> getNotificacones(@Query("api_token") String api_token,
                                                  @Query("id") String id);
    //numero de notificaciones
    @GET("count_notificaciones")
    Call<CountNotificaciones> getCountNotificaciones(@Query("api_token") String api_token,
                                                     @Query("id") String id);
    //marcar notificaciones como leidas
    @POST("notif_mark_as_read")
    @FormUrlEncoded
    Call<Usuario> markAsReadNotifications(@Field("api_token") String api_token,
                                          @Field("id") String id,
                                          @Field("ids_notif[]") List<String> ids_notif);


    @GET("materiales")
    Call<Materiales> getMateriales(@Query("api_token") String api_token);

    //observables GET
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
    //PETICIONES POST
    @POST("add_pedido")
    @FormUrlEncoded
    Call<PedidoResponse> savePedido(@Field("api_token") String api_token,
                        @Field("fecha_conf") String fecha_conf,
                        @Field("obra") String obra);

    @FormUrlEncoded
    @POST("add_det_pedido")
    Call<String> saveDetPedido(
            @Field("pedido") String pedido,
            @Field("cantidad[]") List<String> cantidad,
            @Field("material[]") List<String> material);


    @FormUrlEncoded
    @POST("add_pedido_detalles")
    Call<String> savePedidoDetails(
            @Field("api_token") String api_token,
            @Field("fecha_conf") String fecha_conf,
            @Field("obra") String obra,
            @Field("cantidad[]") List<String> cantidad,
            @Field("material[]") List<String> material);

    @FormUrlEncoded
    @POST("add_pedido_detalles")
    Call<PedidoResponse> savePedidoDetalles(
            @Field("api_token") String api_token,
            @Field("fecha_conf") String fecha_conf,
            @Field("obra") String obra,
            @Field("cantidad[]") List<String> cantidad,
            @Field("material[]") List<String> material);

}

   //@Field("items[]") List<Integer> items

/* @FormUrlEncoded
    @POST("add_det_pedido")
    Call<ResponseBody> saveDetPedido(
            @Field("detalles_pedido[]") List<DetPedidoResponse> detalles_pedido);
* */