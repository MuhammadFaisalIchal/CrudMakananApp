package com.imastudio.crudmakananapp.activity;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.imastudio.crudmakananapp.R;
import com.imastudio.crudmakananapp.databinding.ActivityMakananBinding;
import com.imastudio.crudmakananapp.databinding.ContentMakananBinding;
import com.imastudio.crudmakananapp.databinding.TambahmakananBinding;
import com.imastudio.crudmakananapp.helper.MyConstant;
import com.imastudio.crudmakananapp.model.DataKategoriItem;
import com.imastudio.crudmakananapp.model.ResponseKategoriMakanan;
import com.imastudio.crudmakananapp.network.InitRetrofit;
import com.imastudio.crudmakananapp.network.RestApi;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MakananActivity extends com.training.crudmakananlanjutan.helper.MyFuction {

    ActivityMakananBinding activityMakananBinding;
    ContentMakananBinding makananBinding;
    TambahmakananBinding binding;
    private Dialog dialog;
    private Bitmap bitmap;
    private List<DataKategoriItem> listkategorimakanan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        makananBinding = DataBindingUtil.setContentView(this,R.layout.content_makanan);
        activityMakananBinding = DataBindingUtil.setContentView(this,R.layout.activity_makanan);
        setSupportActionBar(activityMakananBinding.toolbar);
        //* Permission untuk akses gallery
        permissionGallery();
        makananBinding.listmakanan.setLayoutManager(new LinearLayoutManager(c));
        activityMakananBinding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog = new Dialog(MakananActivity.this);
                dialog.setTitle("Data Makanan");

                binding = DataBindingUtil.inflate(LayoutInflater.from(MakananActivity.this)
                        ,R.layout.tambahmakanan,null,false);
                dialog.setContentView(binding.getRoot());
                dialog.setCancelable(true);
                //* Gakbisa di gagalin pencet luar
                dialog.setCanceledOnTouchOutside(false);
                dialog.show();
                //* Untuk mendapatkan file yang sudah ada di database(di dalam spinner / otomatis)
                getKategoriMakanan(binding.spincarikategori);
                binding.btnuploadmakanan.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        showFileChooser();
                    }
                });
            }
        });
        // Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        // setSupportActionBar(toolbar);

        //* Recycle view bagus untuk menyimpan data daripada listview(lemot), dan pilih tampilan sesuai kebutuhan tampilan(grid, linear, sttaggered)

        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
    }

    private void getKategoriMakanan(Spinner spincarikategori) {
        //* Panggil untuk mengambil data yang ada di database melalui Rest api(webservice)
        // Postman api dlu file getkategorimakanan
        RestApi api = InitRetrofit.getInstanceRetrofit();
        Call<ResponseKategoriMakanan> kategoriMakananCall = api.getkategorimakanan();
        kategoriMakananCall.enqueue(new Callback<ResponseKategoriMakanan>() {
            @Override
            public void onResponse(Call<ResponseKategoriMakanan> call, Response<ResponseKategoriMakanan> response) {
                if (response.isSuccessful()){
                    listkategorimakanan = response.body().getDataKategori();
                    //* Melakukan looping untuk mengambil nama kategori dan idnya(karna banyak)
                    //* ditampung disini, dan di passing Melalui Array Adapter dibawah
                    String [] namakategori = new String[listkategorimakanan.size()];
                    String [] idkategori = new String[listkategorimakanan.size()];
                    for (int i=0; i<listkategorimakanan.size();i++){
                        namakategori[i] = listkategorimakanan.size().getNamaKategori();
                        namakategori[i] = listkategorimakanan.size().getIdKategori();
                    }
                    ArrayAdapter = new ArrayAdapter(MakananActivity.this, android.R.layout.simple_spinner_dropdown_item,namakategori)

                }

            }

            @Override
            public void onFailure(Call<ResponseKategoriMakanan> call, Throwable t) {

            }
        });
    }

    private void permissionGallery() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
            return;

        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            //If the user has denied the permission previously your code will come to this block
            //Here you can explain why you need this permission
            //Explain here why you need this permission
        }
        //And finally ask for the permission
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MyConstant.STORAGE_PERMISSION_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MyConstant.REQ_FILE_CHOOSE && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            filepath = data.getData();
            try {

                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filepath);
                imgpreview.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();

            }
        }
    }

    private void showFileChooser() {
        Intent intentgalery = new Intent(Intent.ACTION_PICK);
        intentgalery.setType("image/*");
        intentgalery.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intentgalery, "select Pictures"), reqFileChoose);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode)
    }

    @Override
    public void onBackPressed() {
        //* Memanggil method yang sudah dibuat di MyFunction.java
        keluar();
    }
}
