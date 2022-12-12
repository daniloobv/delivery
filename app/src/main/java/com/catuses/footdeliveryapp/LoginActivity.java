package com.catuses.footdeliveryapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import androidx.appcompat.app.AppCompatActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.catuses.footdeliveryapp.Utilidades.VariablesGlobales;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import android.os.Bundle;

public class LoginActivity extends AppCompatActivity {

    //GoogleSignInClient mSignInClient;
    Context mContext;
    //FirebaseAuth firebaseAuth;
    //FirebaseAuth mAuth;

    ProgressDialog progressBar;
    Button signInButton;
    EditText usuarioText, passText;

    FirebaseAuth mAuthx;
    FirebaseFirestore mFirestorex;
    DatabaseReference db_ref;
    TextView mRegister;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        mAuthx = FirebaseAuth.getInstance();
        mFirestorex = FirebaseFirestore.getInstance();
        db_ref = FirebaseDatabase.getInstance().getReference("user");
        //Firebase Authentication


        //mAuth = FirebaseAuth.getInstance();
        mContext = this;






       // if (mAuth.getCurrentUser() != null) {
         //   GotoMain();
        //}


        //Progress bar
        progressBar = new ProgressDialog(this);
        progressBar.setTitle("ESPERE...");
        progressBar.setMessage("CARGANDO....");
        progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);


        mRegister = findViewById(R.id.bt_register);
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

                    login(NombreUsuario, PassUsuario);

                    //mAuth.signInWithEmailAndPassword(NombreUsuario, PassUsuario).addOnCompleteListener(task -> {
                      //  if (task.isSuccessful()) {
                        //    GotoMain();
                        //}

                  //  }).addOnFailureListener(e -> {
                    //    Toast.makeText(mContext, "Error:" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    //});



                }

            }
        });

        mRegister.setOnClickListener(v -> Register());


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



    private void revisar_rol(){



        mFirestorex.collection("user").document(mAuthx.getCurrentUser().getUid()).get().
                addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()) {

                            Log.d("TAG", "el documento si existe");
                            VariablesGlobales.id_firebase = mAuthx.getCurrentUser().getUid();
                            VariablesGlobales.id_user = documentSnapshot.get("usuario_id").toString();
                            VariablesGlobales.tipoUser = documentSnapshot.get("tipoUser").toString();
                            VariablesGlobales.userName = documentSnapshot.get("name").toString();

                            String tipoUserx = "";

                            tipoUserx = documentSnapshot.getString("tipoUser");
                            Log.d("TAG", "tipo usuario revisar rol: " + tipoUserx + " documento en revisio: "+ mAuthx.getCurrentUser().getUid() );

                            if(tipoUserx.equals("administrador")){
                                finish();
                                startActivity(new Intent(LoginActivity.this, CategoriasActivity.class));
                                Toast.makeText(LoginActivity.this, "Bienvenido", Toast.LENGTH_SHORT).show();
                            }
                            if(tipoUserx.equals("empresa")){
                                finish();
                                startActivity(new Intent(LoginActivity.this, CategoriasActivity.class));
                                Toast.makeText(LoginActivity.this, "Bienvenido", Toast.LENGTH_SHORT).show();
                            }
                            if(tipoUserx.equals("cliente")){
                                finish();
                                startActivity(new Intent(LoginActivity.this, CategoriasActivity.class));
                                Toast.makeText(LoginActivity.this, "Bienvenido", Toast.LENGTH_SHORT).show();

                            }




                        }else{
                            Log.d("TAG", "el documento NO existe");
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("TAG", "fallo durante la consulta");
                    }
                });


    }


    private void login(String emailUser, String passUser) {
        mAuthx.signInWithEmailAndPassword(emailUser, passUser).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    // cargarVariables2();

                    //startActivity(new Intent(MainActivity.this, CategoriasActivity.class));

                    revisar_rol();



                } else {
                    Toast.makeText(LoginActivity.this, "Error", Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(LoginActivity.this, "Error al iniciar Sesion", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = mAuthx.getCurrentUser();
        if(user != null){
            // cargarVariables2();
            //startActivity(new Intent(MainActivity.this, CategoriasActivity.class));
            // startActivity(new Intent(MainActivity.this, MenuInicioActivity.class));
            revisar_rol();

        }
    }

    private void Register() {
        Intent i = new Intent(mContext, RegistroActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
    }

}