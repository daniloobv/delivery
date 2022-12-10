package com.catuses.footdeliveryapp.Adapter;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.catuses.footdeliveryapp.CreateCategoriaFragment;
import com.catuses.footdeliveryapp.Model.Categoria;
import com.catuses.footdeliveryapp.ProductosActivity;
import com.catuses.footdeliveryapp.R;
import com.catuses.footdeliveryapp.Utilidades.VariablesGlobales;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

public class CategoriaAdapter extends FirestoreRecyclerAdapter<Categoria, CategoriaAdapter.ViewHolder> {

    private FirebaseFirestore mFirestore = FirebaseFirestore.getInstance();
    Activity activity;
    FragmentManager fm;

    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public CategoriaAdapter(@NonNull FirestoreRecyclerOptions<Categoria> options, Activity activity, FragmentManager fm) {
        super(options);
        this.activity = activity;
        this.fm = fm;
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder viewHolder, int position, @NonNull Categoria categoria) {
        DocumentSnapshot documentSnapshot = getSnapshots().getSnapshot(viewHolder.getAdapterPosition());

        final String id = documentSnapshot.getId();
        final String categoria_id_ = documentSnapshot.getString("categoria_id");
        final String categoria_titulo_ = documentSnapshot.getString("categoria");
        final String categoria_photo_ = documentSnapshot.getString("photo");


        viewHolder.categoria_.setText(categoria.getCategoria());
        viewHolder.id_.setText(categoria.getCategoria_id());
        viewHolder.btnListaEmpresas.setText("VER TODO EN: " + categoria.getCategoria());

        if (categoria.getPhoto() == "" ) {

        }else{
            Picasso.get()
                    .load(categoria.getPhoto())
                    .resize(150, 150)
                    .into(viewHolder.img_cat);
        }


        viewHolder.btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateCategoriaFragment createCategoriaFragment = new CreateCategoriaFragment();
                Bundle bundle = new Bundle();
                bundle.putString("id_categoria",id);
                bundle.putString("categoria_id",categoria_id_);

                bundle.putString("categoria",categoria_titulo_);
                bundle.putString("photo",categoria_photo_);
                createCategoriaFragment.setArguments(bundle);
                createCategoriaFragment.show(fm,"abriendo fragmento");
            }
        });

        viewHolder.btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                //creamos un objeto detipo builder para un Alerta en pantalla
                builder.setMessage("DESEA ELIMINAR LA CATEGORIA: "+categoria_titulo_)
                        .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                deleteCategoria(id);
                            }
                        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //aqui el usuario se ha arrepentido de realizar el borrado y no se ejecutara ninguna accion
                            }
                        }).show();




            }
        });


        viewHolder.btnListaEmpresas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, ProductosActivity.class);
                intent.putExtra("id_firestores_categoria", id);

                intent.putExtra("categoria_id_", categoria.getCategoria_id());
                intent.putExtra("categoria_titulo_", categoria_titulo_);
                activity.startActivity(intent);
            }
        });
    }



    private void deleteCategoria(String id) {
        mFirestore.collection("categorias").document(id).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(activity, "Eliminado Correctamente", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(activity, "Error al eliminar", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_categorias,parent,false  );
        return new ViewHolder(v);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView categoria_;
        TextView id_;
        ImageView btnEliminar;
        ImageView btnEditar;

        Button btnListaEmpresas;

        ImageView img_cat;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            id_ = itemView.findViewById(R.id.txtIdView);
            categoria_ = itemView.findViewById(R.id.txtCategoriaView);

            btnEliminar = itemView.findViewById(R.id.btnEliminar);
            btnEditar = itemView.findViewById(R.id.btnEditar);
            btnListaEmpresas = itemView.findViewById(R.id.btnListaEmpresas);


            img_cat = itemView.findViewById(R.id.img_cat);

            if (VariablesGlobales.tipoUser.equals("cliente")){
                btnEditar.setVisibility(View.INVISIBLE);
                btnEliminar.setVisibility(View.INVISIBLE);
                id_.setVisibility(View.INVISIBLE);
            }

        }
    }




}
