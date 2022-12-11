package com.example.examenandroid.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.examenandroid.R;
import com.example.examenandroid.providers.AuthProvider;

public class LoginActivity extends AppCompatActivity {

    Context mContext;
    AuthProvider mAuth;
    EditText mUser, mPass;
    Button mLogin;
    TextView mRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mContext = this;
        mAuth = AuthProvider.getInstance();
        if (mAuth.islogin()) {
            GotoMain();
        }

        mUser = findViewById(R.id.user);
        mPass = findViewById(R.id.pass);
        mLogin = findViewById(R.id.bt_login);
        mRegister = findViewById(R.id.bt_register);


        mLogin.setOnClickListener(v -> {

            mAuth.signIn(mUser.getText().toString(), mPass.getText().toString()).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    GotoMain();
                }
            }).addOnFailureListener(e -> {
                Toast.makeText(mContext, "Error:" + e.getMessage(), Toast.LENGTH_SHORT).show();
            });
        });
        mRegister.setOnClickListener(v -> Register());
    }

    private void GotoMain() {
        Intent i = new Intent(mContext, MainActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
    }

    private void Register() {
        Intent i = new Intent(mContext, RegistroActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
    }
}