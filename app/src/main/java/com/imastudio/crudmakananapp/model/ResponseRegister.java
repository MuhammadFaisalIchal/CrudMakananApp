package com.imastudio.crudmakananapp.model;

// import javax.annotation.Generated;

import com.google.gson.annotations.SerializedName;

// @Generated("com.robohorse.robopojogenerator")
// Dapat menggunakan jsonpojo (google.com)
/*
Ini adalah hasil response dari Postman API
dapat menghilangkan @Serialized dengan implement RetroFit*/
/*
Kecepatan data dari rendah ke tinggi:
AsyncTask
Volley
RetroFit
 */
public class ResponseRegister {

    // Ini adalah variable yang didapat dari File PHP dan hasil Response Postman API
    @SerializedName("result")
    private String result;

    @SerializedName("msg")
    private String msg;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    //* Jika ingin mendapatkan response baru dari file baru lagi,
    // maka harus ditambah ulang menggunakan robopjpgenerator
}