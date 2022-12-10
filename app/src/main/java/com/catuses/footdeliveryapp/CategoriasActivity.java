package com.catuses.footdeliveryapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.catuses.footdeliveryapp.Adapter.CategoriaAdapter;
import com.catuses.footdeliveryapp.Model.Categoria;
import com.catuses.footdeliveryapp.Utilidades.VariablesGlobales;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;

public class CategoriasActivity extends AppCompatActivity {

    FloatingActionButton btnAddCategoria;
    RecyclerView mRecycler;
    CategoriaAdapter mAdapter;
    FirebaseFirestore mFirestore;
    FirebaseAuth mAuth;
    String tipoUser_;
    String idUser_;
    TextView tituloGaleria;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_categorias);
        tipoUser_ = "";
        idUser_ = "";
        mFirestore = FirebaseFirestore .getInstance();
        mAuth = FirebaseAuth.getInstance();
        mRecycler = findViewById(R.id.recyclerViewCategorias);
        mRecycler.setLayoutManager(new LinearLayoutManager(this));

        Query query = mFirestore.collection("categorias");
        FirestoreRecyclerOptions<Categoria> firestoreRecyclerOptions = new FirestoreRecyclerOptions.Builder<Categoria>().setQuery(query,Categoria.class).build();

        mAdapter = new CategoriaAdapter(firestoreRecyclerOptions, this, getSupportFragmentManager());
        mAdapter.notifyDataSetChanged();
        mRecycler.setItemAnimator(null);
        mRecycler.setAdapter(mAdapter);
        btnAddCategoria = findViewById(R.id.btnAddCategoria);

        tituloGaleria = findViewById(R.id.txtTituloGaleria);
        cargarVariables3();

        if (VariablesGlobales.tipoUser.equals("cliente")){

            btnAddCategoria.setVisibility(View.INVISIBLE);

        }else{
            btnAddCategoria.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CreateCategoriaFragment fm = new CreateCategoriaFragment();
                    fm.show(getSupportFragmentManager(), "Navegar a fragmento Crear Categoria");
                }
            });
        }






    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_principal, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){

            case R.id.item1:

                AlertDialog.Builder builder = new AlertDialog.Builder(CategoriasActivity.this);
                builder.setMessage("DESEA CERRAR SU SESION?")
                        .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                cerrarSesion();
                            }
                        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        }).show();
                return  true;


            default:
                return super.onOptionsItemSelected(item);

        }

    }


    private void cerrarSesion(){

        mAuth.signOut();
        finish();
        startActivity(new Intent(CategoriasActivity.this, LoginActivity.class));


    }


    @Override
    protected void onStart() {
        super.onStart();
        mAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mAdapter.stopListening();
    }




    private void cargarVariables3(){
        Log.d("TAG", "cargarVariables3");
        mFirestore.collection("user").document(mAuth.getCurrentUser().getUid()).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if (value.exists()) {
                    VariablesGlobales.id_firebase = mAuth.getCurrentUser().getUid();
                    VariablesGlobales.id_user = value.get("usuario_id").toString();
                    VariablesGlobales.tipoUser = value.getString("tipoUser");
                    String titulo = value.get("name").toString() + "\n [" + VariablesGlobales.tipoUser+"]";
                    //tituloGaleria.setText("Usuario: " + value.get("name").toString() + "\n tipo usuario: " + VariablesGlobales.tipoUser );
                    setTitle(titulo);
                    Log.d("TAG", "variable accesible "+ VariablesGlobales.tipoUser);
                }else{
                    Log.d("TAG", "el documento NO existe");
                }
            }
        });
    }


}