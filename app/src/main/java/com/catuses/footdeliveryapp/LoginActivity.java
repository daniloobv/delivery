package com.catuses.footdeliveryapp;

import androidx.appcompat.app.AppCompatActivity;

import androidx.appcompat.app.AppCompatActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;

import android.os.Bundle;

public class LoginActivity extends AppCompatActivity {

    //GoogleSignInClient mSignInClient;
    Context mContext;
    //FirebaseAuth firebaseAuth;
    FirebaseAuth mAuth;
    ProgressDialog progressBar;
    Button signInButton;
    EditText usuarioText, passText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Firebase Authentication
        mAuth = FirebaseAuth.getInstance();
        mContext = this;



        if (mAuth.getCurrentUser() != null) {
            GotoMain();
        }


        //Progress bar
        progressBar = new ProgressDialog(this);
        progressBar.setTitle("ESPERE...");
        progressBar.setMessage("CARGANDO....");
        progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);



        usuarioText = (EditText) findViewById(R.id.txtuser);
        passText = (EditText) findViewById(R.id.pass);
        signInButton = (Button) findViewById(R.id.bt_login);









        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //OBTENER DATOS CTE
                String NombreUsuario = usuarioText.getText().toString();
                String PassUsuario = passText.getText().toString();


                //VACIO O NO ELSE.
                if (NombreUsuario.isEmpty() || PassUsuario.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "RELLENE LOS CAMPOS", Toast.LENGTH_SHORT).show();
                } else {

                    mAuth.signInWithEmailAndPassword(NombreUsuario, PassUsuario).addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            GotoMain();
                        }
                    }).addOnFailureListener(e -> {
                        Toast.makeText(mContext, "Error:" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    });



                }

            }
        });


    }



    private void GotoMain() {
        Intent i = new Intent(mContext, CategoriasActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
    }

    //private void Register() {
    //  Intent i = new Intent(mContext, RegistroActivity.class);
    //  i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
    //  startActivity(i);
    //}
}