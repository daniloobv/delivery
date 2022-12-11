package com.catuses.footdeliveryapp.Adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.telephony.PhoneNumberUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.catuses.footdeliveryapp.CreateEmpresaFragment;
import com.catuses.footdeliveryapp.Model.Empresa;
import com.catuses.footdeliveryapp.R;
import com.catuses.footdeliveryapp.Utilidades.VariablesGlobales;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class EmpresaAdapter extends FirestoreRecyclerAdapter<Empresa, EmpresaAdapter.ViewHolder> {

    private FirebaseFirestore mFirestore = FirebaseFirestore.getInstance();
    Activity activity;
    FragmentManager fm;

    Integer imageResource;
    Drawable res;


    public EmpresaAdapter(@NonNull FirestoreRecyclerOptions<Empresa> options, Activity activity, FragmentManager fm) {
        super(options);
        this.activity = activity;
        this.fm = fm;
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder viewHolder, int i, @NonNull Empresa empresas) {
        DocumentSnapshot documentSnapshot = getSnapshots().getSnapshot(viewHolder.getAdapterPosition());

        final String id = documentSnapshot.getId();
        final String departamento_id_ = String.valueOf(documentSnapshot.get("departamento_id"));
        final String empresa_id_ = documentSnapshot.getString("empresa_id");
        final String categoria_id_ = documentSnapshot.getString("categoria_id");
        final String telefono_ = documentSnapshot.getString("telefono");
        final String pais_id_ = documentSnapshot.getString("pais_id");
        final String empresa_titulo_ = documentSnapshot.getString("nombre_comercial");


        String uri1 = "@drawable/ic_baseline_star_border_24_color";  //
        String uri2 = "@drawable/ic_baseline_star_24";  //
        String uri3 = "@drawable/ic_baseline_star_half_24";  //


        viewHolder.nombre_comercial.setText(empresas.getNombre_comercial());


        mFirestore.collection("categorias")
                .whereEqualTo("categoria_id",empresas.getCategoria_id())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {

                            for (QueryDocumentSnapshot document : task.getResult()) {

                                //Log.d("PUNTUACION_", "puntuacion" + String.valueOf(document.get("puntuacion") ));
                                viewHolder.categoria.setText( String.valueOf(document.get("categoria").toString()));

                            }
                        } else {

                        }



                    }
                });





        viewHolder.pais.setText(empresas.getPais_id());
        viewHolder.departamento.setText(empresas.getDepartamento_id());

        imageResource = activity.getResources().getIdentifier(uri1, null, activity.getPackageName());

        res = activity.getDrawable(imageResource);

        viewHolder.bV1.setImageDrawable(res);
//        viewHolder.bV2.setImageDrawable(res);
        //      viewHolder.bV3.setImageDrawable(res);
        //    viewHolder.bV4.setImageDrawable(res);
        //  viewHolder.bV5.setImageDrawable(res);


        mFirestore.collection("ranking")
                .whereEqualTo("empresa_id",empresa_id_)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            //txtTotalR.setText( String.valueOf( total));
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                //total = document.getString("puntuacion");
                                Log.d("PUNTUACION_", "puntuacion" + String.valueOf(document.get("puntuacion") ));

                                if ( String.valueOf(document.get("puntuacion")).equals("1")){
                                    Log.d("PUNTUACION_", "puntuacion 1");
                                    imageResource =  activity.getResources().getIdentifier(uri2, null, activity.getPackageName());
                                    res = activity.getDrawable(imageResource);
                                    viewHolder.bV1.setImageDrawable(res);
                                }

                                if ( String.valueOf(document.get("puntuacion")).equals("2")){
                                    Log.d("PUNTUACION_", "puntuacion 2");
                                    imageResource =  activity.getResources().getIdentifier(uri2, null, activity.getPackageName());
                                    res = activity.getDrawable(imageResource);
                                    viewHolder.bV1.setImageDrawable(res);
                                    //viewHolder.bV2.setImageDrawable(res);
                                }

                                if ( String.valueOf(document.get("puntuacion")).equals("3")){
                                    Log.d("PUNTUACION_", "puntuacion 3");
                                    imageResource =  activity.getResources().getIdentifier(uri2, null, activity.getPackageName());
                                    res = activity.getDrawable(imageResource);
                                    viewHolder.bV1.setImageDrawable(res);
                                    //viewHolder.bV2.setImageDrawable(res);
                                    //viewHolder.bV3.setImageDrawable(res);
                                }

                                if ( String.valueOf(document.get("puntuacion")).equals("4")){
                                    Log.d("PUNTUACION_", "puntuacion 4");
                                    imageResource =  activity.getResources().getIdentifier(uri2, null, activity.getPackageName());
                                    res = activity.getDrawable(imageResource);
                                    viewHolder.bV1.setImageDrawable(res);
                                    //viewHolder.bV2.setImageDrawable(res);
                                    //viewHolder.bV3.setImageDrawable(res);
                                    //viewHolder.bV4.setImageDrawable(res);
                                }

                                if ( String.valueOf(document.get("puntuacion")).equals("5")){
                                    Log.d("PUNTUACION_", "puntuacion 5");
                                    imageResource =  activity.getResources().getIdentifier(uri2, null, activity.getPackageName());
                                    res = activity.getDrawable(imageResource);
                                    viewHolder.bV1.setImageDrawable(res);
                                    //viewHolder.bV2.setImageDrawable(res);
                                    //viewHolder.bV3.setImageDrawable(res);
                                    //viewHolder.bV4.setImageDrawable(res);
                                    //viewHolder.bV5.setImageDrawable(res);
                                }

                                //txtTotalR.setText( String.valueOf( total));
                            }
                        } else {
                            Log.d("respuesta", "no hay registros: ", task.getException());
                        }

                        Log.d("PUNTUACION_", "puntuacion 0");

                    }
                });


        viewHolder.btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateEmpresaFragment createEmpresaFragment = new CreateEmpresaFragment();

                Bundle bundle = new Bundle();
                bundle.putString("id_categoria",id);
                bundle.putString("id_categoria_2",categoria_id_);
                bundle.putString("id_empresa",empresa_id_);
                bundle.putString("telefono",telefono_);
                bundle.putString("pais_id",pais_id_);
                bundle.putString("departamento_id",departamento_id_);
                bundle.putString("nombre_comercial",empresa_titulo_);
                createEmpresaFragment.setArguments(bundle);

                createEmpresaFragment.show(fm,"abriendo fragmento");
            }
        });

        viewHolder.btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                //creamos un objeto detipo builder para un Alerta en pantalla
                builder.setMessage("DESEA ELIMINAR LA CATEGORIA: "+empresa_titulo_)
                        .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                deleteEmpresa(id);
                            }
                        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //aqui el usuario se ha arrepentido de realizar el borrado y no se ejecutara ninguna accion
                            }
                        }).show();


            }
        });

        viewHolder.btnListaServicios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Intent intent = new Intent(activity, ServiciosActivity.class);
                //intent.putExtra("id_firestores_empresa", id);
                //intent.putExtra("empresa_id_", empresas.getEmpresa_id());
                //intent.putExtra("empresa_titulo_", empresas.getNombre_comercial());
                //activity.startActivity(intent);


            }
        });






        viewHolder.contactar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //ContactarFragment contactarFragment = new ContactarFragment();


                //Bundle bundle = new Bundle();
                //bundle.putString("empresa_id",empresas.getEmpresa_id());
                //rankingFragment.setArguments(bundle);


                //contactarFragment.show( ((FragmentActivity)v.getContext()).getSupportFragmentManager(),"Navegar a fragment");

                //AbrirWhatsApp("89065522");
                AbrirWhatsApp(empresas.getTelefono());

            }
        });




    }


    private void deleteEmpresa(String id) {
        mFirestore.collection("empresas").document(id).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
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
    public EmpresaAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_empresas,parent,false);
        return new ViewHolder(v);
    }


    private void AbrirWhatsApp(String telefono)
    {
        Intent _intencion = new Intent("android.intent.action.MAIN");
        _intencion.setComponent(new ComponentName("com.whatsapp","com.whatsapp.Conversation"));
        _intencion.putExtra("jid", PhoneNumberUtils.stripSeparators("57" + telefono)+"@s.whatsapp.net");
        activity.startActivity(_intencion);
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        Button btnRankear;
        TextView nombre_comercial, categoria, pais, departamento;

        ImageView bV1, bV2, bV3, bV4,bV5, contactar;

        ImageView btnEliminar;
        ImageView btnEditar;
        Button btnListaServicios;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nombre_comercial = itemView.findViewById(R.id.nombre_comercial);
            categoria = itemView.findViewById(R.id.categoria);
            pais = itemView.findViewById(R.id.pais);
            departamento = itemView.findViewById(R.id.departamento);
            btnEliminar = itemView.findViewById(R.id.btnEliminarE);
            btnEditar = itemView.findViewById(R.id.btnEditarE);
            btnListaServicios = itemView.findViewById(R.id.btnListaServiciosE);
            btnRankear = itemView.findViewById(R.id.btnRakear);

            bV1 = itemView.findViewById(R.id.btnValor1);
            bV1 = itemView.findViewById(R.id.btnValor2);
            bV1 = itemView.findViewById(R.id.btnValor3);
            bV1 = itemView.findViewById(R.id.btnValor4);
            bV1 = itemView.findViewById(R.id.btnValor5);

            contactar = itemView.findViewById(R.id.contactarEmpresa);


            if (VariablesGlobales.tipoUser.equals("empresa_")) {

                btnRankear.setVisibility(View.INVISIBLE);

            }


            if (VariablesGlobales.tipoUser.equals("cliente_")){
                btnEditar.setVisibility(View.INVISIBLE);
                btnEliminar.setVisibility(View.INVISIBLE);
                categoria.setVisibility(View.INVISIBLE);
            }

        }
    }
}

