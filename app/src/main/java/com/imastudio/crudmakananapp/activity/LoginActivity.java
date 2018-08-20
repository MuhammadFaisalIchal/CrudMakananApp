package com.imastudio.crudmakananapp.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;

import com.imastudio.crudmakananapp.R;
import com.imastudio.crudmakananapp.databinding.ActivityLoginBinding;

public class LoginActivity extends com.training.crudmakananlanjutan.helper.MyFuction implements View.OnClickListener {

    ActivityLoginBinding loginBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loginBinding = DataBindingUtil.setContentView(this,R.layout.activity_login);

        //* Untuk deklarasi tombol yang di get dari file XML
        loginBinding.regBtnLogin.setOnClickListener(this);
    }

    //* Jika ingin lebih dari banyak tombol, gunakan Switch-Case
    @Override
    public void onClick(View view) {

    }
}
