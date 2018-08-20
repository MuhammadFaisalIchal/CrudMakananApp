package com.imastudio.crudmakananapp.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.imastudio.crudmakananapp.R;
import com.imastudio.crudmakananapp.databinding.ActivityRegisterBinding;
import com.imastudio.crudmakananapp.model.ResponseRegister;
import com.imastudio.crudmakananapp.network.InitRetrofit;
import com.imastudio.crudmakananapp.network.RestApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends com.training.crudmakananlanjutan.helper.MyFuction implements View.OnClickListener {

    ActivityRegisterBinding registerBinding;
    //* Jika pakai Kotlin, maka 1 ArrayList bisa banyak tipe data
    String[] jenkel = {"Laki - laki", "Perempuan"};
    private String strjenkel;
    private String strLevel;
    private String strNama;
    private String strAlamat;
    private String strNoHp;
    private String strUserName;
    private String strPassword;
    private String strPasswordConfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        registerBinding = DataBindingUtil.setContentView(this, R.layout.activity_register);

        setJenkel();
        setHakAkses();

        // Aksi Klik pada radioButton dan button
        registerBinding.regAdmin.setOnClickListener(this);
        registerBinding.regUserbiasa.setOnClickListener(this);
        registerBinding.btnregister.setOnClickListener(this);
    }

    private void setHakAkses() {
        //* isChecked() digunakan untuk mengeset nilai default
        if (registerBinding.regAdmin.isChecked()) {
            strLevel = "admin";
        } else {
            strLevel = "user biasa";
        }
    }

    //* Apabila RegisterActivity tidak kebaca maka klik File > Invalidate Caches/Restart
    private void setJenkel() {
        ArrayAdapter adapter = new ArrayAdapter(RegisterActivity.this, R.layout.support_simple_spinner_dropdown_item,jenkel);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Set data ke view (spinner)
        registerBinding.spinjenkel.setAdapter(adapter);
        registerBinding.spinjenkel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            //* Kenapa On item Selected? karena Ini adalah tipe item dropdown yang di select.
            // kalau on item click untuk satu hal aja
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                strjenkel = jenkel[i];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    @Override
    public void onClick(View view) {
        //* .getId() digunakan untuk mendapatkan id dari file XML
        switch (view.getId()) {
            case R.id.regAdmin:
                strLevel = "admin";
                break;

            case R.id.regUserbiasa:
                strLevel = "user biasa";
                break;

            case R.id.btnregister:
                strNama = registerBinding.edtnama.getText().toString();
                strAlamat = registerBinding.edtalamat.getText().toString();
                strNoHp = registerBinding.edtnotelp.getText().toString();
                strUserName = registerBinding.edtusername.getText().toString();
                strPassword = registerBinding.edtpassword.getText().toString();
                strPasswordConfirm = registerBinding.edtpasswordconfirm.getText().toString();
                if (TextUtils.isEmpty(strNama)) {
                    registerBinding.edtnama.setError(getString(R.string.namaWarning));
                    registerBinding.edtnama.requestFocus();
                    myanimation(registerBinding.edtnama);
                } else if (TextUtils.isEmpty(strAlamat)) {
                    registerBinding.edtalamat.requestFocus();
                    registerBinding.edtalamat.setError(getString(R.string.alamatWarning));
                    myanimation(registerBinding.edtalamat);
                } else if (TextUtils.isEmpty(strNoHp)) {
                    registerBinding.edtnotelp.requestFocus();
                    myanimation(registerBinding.edtnotelp);
                    registerBinding.edtnotelp.setError(getString(R.string.noHpWarning));
                } else if (TextUtils.isEmpty(strUserName)) {
                    registerBinding.edtusername.requestFocus();
                    myanimation(registerBinding.edtusername);
                    registerBinding.edtusername.setError(getString(R.string.usernameWarning));
                } else if (TextUtils.isEmpty(strPassword)) {
                    registerBinding.edtpassword.requestFocus();
                    myanimation(registerBinding.edtpassword);
                    registerBinding.edtpassword.setError(getString(R.string.passwordWarning));
                    //* Ini untuk menghitung jumlah character, apabila char kurang dari 6, maka
                } else if (strPasswordConfirm.length() < 6) {
                    myanimation(registerBinding.edtpassword);
                    registerBinding.edtpassword.setError(getString(R.string.strPasswordConfirmWarning));
                } else if (TextUtils.isEmpty(strPasswordConfirm)) {
                    registerBinding.edtpasswordconfirm.requestFocus();
                    myanimation(registerBinding.edtpasswordconfirm);
                    registerBinding.edtpasswordconfirm.setError("password confirm tidak boleh kosong");
                    //* Ini untuk menyamakan jumlah character, apabila char tidak sama dengan confirm, maka
                } else if (!strPassword.equals(strPasswordConfirm)) {
                    registerBinding.edtpasswordconfirm.requestFocus();
                    myanimation(registerBinding.edtpasswordconfirm);
                    registerBinding.edtpasswordconfirm.setError("password tidak sama");
                } else {
                    registeruser();
                }
                break;
        }
    }

    //* Ini berasal dari /network/RestApi.interface
    private void registeruser() {
        // todo: 4 panggil interface dan instance retrofit
        // todo: 5 request ke endpoint yang ada di webservice sesuai dengan urutan parameter (Nama Form)
        // panggil Interface
        RestApi api = InitRetrofit.getInstanceRetrofit();
        Call<ResponseRegister> registerCall = api.registerUser(
                // Ini harus sama dengan urutan di dalam public interface RestApi (registeruser.php) dan juga di From XML
                strNama,
                strAlamat,
                strNoHp,
                strjenkel,
                strUserName,
                strLevel,
                strPassword
        );
        // todo: 6 Ini adalah untuk menangkap response dari webserver(PHP - Setelah Android meminta request) untuk menampilkan data json
        registerCall.enqueue(new Callback<ResponseRegister>() {
            // todo: 6.1 Jika berhasil, maka akan menampilkan ini
            // Jika response dari webservice berhasil
            @Override
            public void onResponse(Call<ResponseRegister> call, Response<ResponseRegister> response) {
                if (response.isSuccessful()) {
                    String result = response.body().getResult();
                    String msg = response.body().getMsg();
                    if (result.equals("1")) {
                        myIntent(LoginActivity.class);
                        //* Untuk mengclear kan activity di dalam memory
                        finish();
                        myToast(msg);
                    } else {
                        myToast(msg);
                    }
                } else {
                    myToast("Gagal mengambil data JSON");
                }
            }

            // todo: 6.2 Jika gagal, maka akan menampilkan ini
            // Jika response dari webservice gagal
            @Override
            public void onFailure(Call<ResponseRegister> call, Throwable t) {
                myToast("gagal koneksi" + t.getMessage());
            }
        });
    }
}
