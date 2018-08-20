package com.imastudio.crudmakananapp.network;

import com.imastudio.crudmakananapp.helper.MyConstant;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

// todo: 1 inisialisasi retrofit
public class InitRetrofit {

    // Ini untuk memanggil URL dasar dari endpoint file PHP
    private static Retrofit getRetrofit(){
        Retrofit r = new Retrofit.Builder().baseUrl(MyConstant.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return r;
    }

    // todo: 2 bikin instance retrofit verdasarkan interface
    public static RestApi getInstanceRetrofit(){
        //* Memanggil nama diatas, agar isi yang ada di BASE_URL Dapat terhubung dengan
        // parameter yang ada di dalam sini (Endpoint PHP, variabel2)
        return getRetrofit().create(RestApi.class);
    }
}
