package com.example.examenandroid.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.examenandroid.R;
import com.example.examenandroid.providers.AuthProvider;

public class MainActivity extends AppCompatActivity {
    Context mContext;
    AuthProvider mAuth;
    Button mSalir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;
        mAuth = AuthProvider.getInstance();

        mSalir = findViewById(R.id.salir);

        mSalir.setOnClickListener(v -> {
            mAuth.logout();
            GotoLogin();
        });

        if (!mAuth.islogin()) {
            GotoLogin();
        }
    }

    void GotoLogin() {
        Intent i = new Intent(mContext, LoginActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);

    }
}