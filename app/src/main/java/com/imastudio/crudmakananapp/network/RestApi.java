package com.imastudio.crudmakananapp.network;

import com.imastudio.crudmakananapp.model.ResponseKategoriMakanan;
import com.imastudio.crudmakananapp.model.ResponseRegister;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface RestApi {

    // todo: 3 panggil endpoint(file lokal php) untuk register user
    // @FormUrlEncoded digunakan karna berbentuk POST. Kalau Get, tidak perlu
    @FormUrlEncoded
    // Ini adalah nama file lokal dari htdocs yang diakses
    // jika banyak file, maka tambahkan lagi
    @POST("registeruser.php")
    // Ini adalah response sesuai model dan sesuaikan parameter dengan nama/variable/function yang ada di webserfice(PHP)
    Call<ResponseRegister> registerUser(
            @Field("vsnama") String nama,
            @Field("vsalamat") String alamat,
            @Field("vsnotelp") String noHp,
            @Field("vsjenkel") String jenKel,
            @Field("vsusername") String username,
            @Field("vslevel") String level,
            @Field("vspassword") String password
    );

    @FormUrlEncoded
    // Ini adalah nama file lokal dari htdocs yang diakses
    // jika banyak file, maka tambahkan lagi
    @POST("loginuser.php")
        // Ini adalah response sesuai model dan sesuaikan parameter dengan nama/variable/function yang ada di webserfice(PHP)
    Call<ResponseRegister> registerUser(
            @Field("edtusername") String nama,
            @Field("edtpassword") String password,
            @Field("vslevel") String level
    );

    @GET("kategorimakanan.php")
    Call<ResponseKategoriMakanan> getkategorimakanan();
}
