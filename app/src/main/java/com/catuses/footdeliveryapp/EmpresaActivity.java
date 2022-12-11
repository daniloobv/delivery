package com.catuses.footdeliveryapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
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

import com.catuses.footdeliveryapp.Adapter.EmpresaAdapter;
import com.catuses.footdeliveryapp.Model.Empresa;
import com.catuses.footdeliveryapp.Utilidades.VariablesGlobales;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class EmpresaActivity extends AppCompatActivity {

    FloatingActionButton btnAddEmpresa;
    EmpresaAdapter mAdapter;
    RecyclerView mRecycler;
    FirebaseFirestore mFirestore;
    FirebaseAuth mAuth;
    TextView txtDato, txtTituloGaleria;

    private String id_firestores_categoria, empresa_id;
    private String categoria_id_;
    private String categoria_titulo_;
    Integer totalE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empresa);

        totalE=0;
        id_firestores_categoria = getIntent().getStringExtra("id_firestores_categoria");
        categoria_id_ = getIntent().getStringExtra("categoria_id_");
        empresa_id = getIntent().getStringExtra("categoria_id_");
        categoria_titulo_ = getIntent().getStringExtra("categoria_titulo_");



        txtTituloGaleria.setText(categoria_titulo_);

        mFirestore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        btnAddEmpresa = findViewById(R.id.btnAddEmpresa);

        setTitle(categoria_titulo_);
        txtDato.setText(categoria_id_);
        // idCategoria();

        if (VariablesGlobales.tipoUser.equals("administrador")) {
            btnAddEmpresa.setVisibility(View.INVISIBLE);

        }

        if (VariablesGlobales.tipoUser.equals("empresa")) {

            setTitle("Bienvenido: "+ VariablesGlobales.userName + " [empresario]");

        }





        if (VariablesGlobales.tipoUser.equals("cliente")){

            btnAddEmpresa.setVisibility(View.INVISIBLE);

        }else{
            btnAddEmpresa.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CreateEmpresaFragment createEmpresaFragment = new CreateEmpresaFragment();

                    Bundle bundle = new Bundle();
                    bundle.putString("id_categoria_n",categoria_id_);
                    bundle.putString("id_empresa",categoria_id_);
                    createEmpresaFragment.setArguments(bundle);

                    createEmpresaFragment.show(getSupportFragmentManager(),"Navegar a fragment");

                }
            });
        }




        Log.d("TAG", "variable global tipo usuario var2: " + VariablesGlobales.tipoUser);

        setUpRecyclerView();


    }




    @SuppressLint("NotifyDataSetChanged")
    private void setUpRecyclerView() {

        mRecycler = findViewById(R.id.recyclerViewSingle);
        mRecycler.setLayoutManager(new LinearLayoutManager(this));
//      Query query = mFirestore.collection("pet").whereEqualTo("id_user", mAuth.getCurrentUser().getUid());
        Query query = mFirestore.collection("empresas").whereEqualTo("categoria_id",categoria_id_);
        if( VariablesGlobales.tipoUser.equals("empresa")){
            query = mFirestore.collection("empresas").whereEqualTo("usuario_id", VariablesGlobales.id_user);
        }
        FirestoreRecyclerOptions<Empresa> firestoreRecyclerOptions =
                new FirestoreRecyclerOptions.Builder<Empresa>().setQuery(query, Empresa.class).build();
        mAdapter = new EmpresaAdapter(firestoreRecyclerOptions, this, getSupportFragmentManager());
        mAdapter.notifyDataSetChanged();
        mRecycler.setItemAnimator(null);
        mRecycler.setAdapter(mAdapter);

        if (VariablesGlobales.tipoUser.equals("empresa")) {
            mFirestore.collection("empresas")
                    .whereEqualTo("usuario_id", VariablesGlobales.id_user)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    totalE =  1;
                                }
                            } else {
                                Log.d("TAG", "Error getting documents: ", task.getException());
                            }
                            Log.d("TAG", "total empresa este usuario: "+ String.valueOf(totalE));
                            if (totalE == 0){
                               // avisar();
                            }
                            if (totalE == 1){
                                btnAddEmpresa.setVisibility(View.INVISIBLE);
                            }
                        }
                    });
        }


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



    private void idCategoria(){


        mFirestore.collection("categorias").document(id_firestores_categoria).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if (value.exists()) {

                    txtDato.setText(value.getString("categoria_id"));
                    Log.d("TAG", "variable categoria tipo : " + value.getString("categoria_id"));


                }else{

                }
            }
        });

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


                AlertDialog.Builder builder = new AlertDialog.Builder(EmpresaActivity.this);
                //creamos un objeto detipo builder para un Alerta en pantalla
                builder.setMessage("DESEA CERRAR SU SESION?")
                        .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                cerrarSesion();
                            }
                        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //aqui el usuario se ha arrepentido de realizar el borrado y no se ejecutara ninguna accion
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
        startActivity(new Intent(EmpresaActivity.this, LoginActivity.class));

    }





}
