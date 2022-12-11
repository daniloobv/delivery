package com.catuses.footdeliveryapp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.catuses.footdeliveryapp.Utilidades.VariablesGlobales;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CreateEmpresaFragment extends DialogFragment {

    String empresa_photo, id_categoria_x,id_empresa,id_departamento, id_pais,nombre_comercial, empresa_id, telefono, user_id, categoria_descip, id_categoria_2;
    Button btnInsertEmpresa;
    EditText et_Nombre_Comercial_f, et_Categoria_f, et_Pais_f, et_Departamento_f, et_EmpresaId, et_Telefono, et_User_id;
    TextView fragmentTituloE;
    Integer total, contador;
    Spinner spinner_categorias;

    ImageView img_Empresa;
    ImageView btnEliminarFotoCategoria;
    ImageView btnBuscarFotoCategoria;

    private ArrayList<String> languageList;

    private FirebaseFirestore mFirestore;
    FirebaseAuth mAuth;

    List<String> list;

    StorageReference storageReference = FirebaseStorage.getInstance().getReference();
    String storage_path = "empresas/*";

    private static final int COD_SEL_STORAGE = 200;
    private static final int COD_SEL_IMAGE = 300;

    private Uri image_url;
    String photo = "photo";
    String idd;
    ProgressDialog progressDialog;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        progressDialog = new ProgressDialog(getContext());

        if (getArguments() != null) {
            id_categoria_x = getArguments().getString("id_categoria");
            id_categoria_2 = getArguments().getString("id_categoria_2");
            id_empresa = getArguments().getString("id_empresa");
            empresa_id = getArguments().getString("id_empresa");
            id_departamento = getArguments().getString("departamento_id");
            id_pais = getArguments().getString("pais_id");
            nombre_comercial = getArguments().getString("nombre_comercial");
            telefono = getArguments().getString("telefono");
            user_id = String.valueOf(VariablesGlobales.id_user);
            empresa_photo = getArguments().getString("photo");
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_create_empresa, container, false);
        mFirestore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        btnInsertEmpresa = v.findViewById(R.id.btnInsertEmpresa);
        et_Nombre_Comercial_f = v.findViewById(R.id.et_Nombre_Comercial_f);
        et_Categoria_f = v.findViewById(R.id.et_Categoria_f);
        et_Pais_f = v.findViewById(R.id.et_Pais_f);
        et_Departamento_f = v.findViewById(R.id.et_Departamento_f);
        fragmentTituloE = v.findViewById(R.id.fragmentTituloE);
        et_User_id =v.findViewById(R.id.et_User_id);
        et_Telefono = v.findViewById(R.id.et_Telefono_f);
        et_EmpresaId = v.findViewById(R.id.et_EmpresaId);

        img_Empresa = v.findViewById(R.id.img_Empresa);
        btnEliminarFotoCategoria = v.findViewById(R.id.btnEliminarFotoEmpresa);
        btnBuscarFotoCategoria = v.findViewById(R.id.btnBuscarFotoEmpresa);

        categoria_descip="";
        contador=0;
        list = new ArrayList<String>();

        list.add("CATEGORIA");

        mFirestore.collection("categorias")
                .orderBy("categoria")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                list.add(document.getString("categoria"));

                                contador++;
                                if(document.getString("categoria_id").equals(id_categoria_2)){
                                    spinner_categorias.setSelection(contador);
                                }

                            }
                        } else {
                            Log.d("TAG", "Error getting documents: ", task.getException());
                        }
                    }
                });

        spinner_categorias = v.findViewById(R.id.spinner_categorias);

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinner_categorias.setAdapter(dataAdapter);


        total = 0;

        mFirestore.collection("empresas")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                total = total + 1;
                                Log.d("TAG", document.getId() + " => " + document.getData() + " total: "+ total);
                                et_EmpresaId.setText( String.valueOf( total+1));
                            }
                        } else {
                            Log.d("TAG", "Error getting documents: ", task.getException());
                        }
                    }
                });



        btnBuscarFotoCategoria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadPhoto();
            }
        });

        btnEliminarFotoCategoria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deletePhoto();
            }
        });



        et_Categoria_f.setText(id_categoria_2);
        et_User_id.setText(user_id);
        et_Nombre_Comercial_f.setText(nombre_comercial);
        et_Pais_f.setText(id_pais);
        et_Departamento_f.setText(id_departamento);
        et_Telefono.setText(telefono);

        //et_Categoria_f.setVisibility(View.INVISIBLE);
        //et_User_id.setVisibility(View.INVISIBLE);
        //et_EmpresaId.setVisibility(View.INVISIBLE);





        spinner_categorias.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String categoria = (String) spinner_categorias.getSelectedItem();


                et_Departamento_f.setText(id_departamento);

                mFirestore.collection("categorias")
                        .whereEqualTo("categoria",categoria)
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        et_Categoria_f.setText(document.get("categoria_id").toString());
                                    }
                                }
                                else {

                                }


                            }
                        });





                //AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                //builder.setMessage(categoria)
                //      .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                //          @Override
                //          public void onClick(DialogInterface dialogInterface, int i) {
                //                          }
                //      }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                //          @Override
                //          public void onClick(DialogInterface dialogInterface, int i) {
                //          }
                //      }).show();


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // No seleccionaron nada
            }
        });




        if (id_categoria_x == null ||  id_categoria_x == "" || id_empresa == null ||  id_empresa == "" ) {
            //id_categoria_x = getArguments().getString("id_categoria");

            btnInsertEmpresa.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String nombre_comercial = et_Nombre_Comercial_f.getText().toString().trim();
                    String categoria = et_Categoria_f.getText().toString().trim();
                    String pais = et_Pais_f.getText().toString().trim();
                    String departamento = et_Departamento_f.getText().toString().trim();
                    String empresaIDnex = et_EmpresaId.getText().toString().trim();
                    String telefonox = et_Telefono.getText().toString().trim();
                    String usuarioIdx = et_User_id.getText().toString().trim();


                    if (nombre_comercial.isEmpty() && categoria.isEmpty() && pais.isEmpty() && departamento.isEmpty()){

                        Toast.makeText(getContext(), "ingresar todos los datos", Toast.LENGTH_SHORT );

                    }else{
                        postNuevaEmpresa(nombre_comercial,categoria, pais, departamento, empresaIDnex,telefonox,usuarioIdx, image_url);
                    }
                }
            });

        }else{
            //getCategoria(id_categoria_x);
            fragmentTituloE.setText("ACTUALIZAR EMPRESA");
            btnInsertEmpresa.setText("Actualizar");
            btnInsertEmpresa.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String nombreE = et_Nombre_Comercial_f.getText().toString().trim();
                    String paisE = et_Pais_f.getText().toString().trim();
                    String departamentoE = et_Departamento_f.getText().toString().trim();
                    String telefonoE = et_Telefono.getText().toString().trim();
                    String categoria = et_Categoria_f.getText().toString().trim();

                    if (nombreE.isEmpty() && paisE.isEmpty() && departamentoE.isEmpty()){
                        Toast.makeText(getContext(), "Ingresar los datos, estos no pueden estar vacios",Toast.LENGTH_SHORT).show();
                    }else{
                        updateEmpresa(nombreE, paisE,departamentoE,telefonoE,categoria);
                    }
                }
            });

        }


        return v;
    }


    private void uploadPhoto() {

        Intent i = new Intent(Intent.ACTION_PICK);
        i.setType("image/*");
        startActivityForResult(i, COD_SEL_IMAGE);
    }

    private void deletePhoto(){
        FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
        StorageReference storageReference = firebaseStorage.getReferenceFromUrl(empresa_photo);
        storageReference.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.e("Picture"," # " + empresa_photo + "deleted");

                Map<String, Object> map = new HashMap<>();
                map.put("photo", "");

                mFirestore.collection("empresas").document(id_empresa).update(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(getContext(), "Foto Exitosamente",Toast.LENGTH_SHORT).show();
                        // getDialog().dismiss();
                        img_Empresa.setImageDrawable(getResources().getDrawable(R.drawable.motodelivery));
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getContext(), "Error al actualizar datos",Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });
    }


    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        Log.d("image_url", "resquestCode - RESULT_OK: "+requestCode+" "+ Activity.RESULT_OK);
        if(resultCode ==  Activity.RESULT_OK){
            if(requestCode == COD_SEL_IMAGE) {
                image_url = data.getData();
                Picasso.get()
                        .load(image_url)
                        .resize(150, 150)
                        .into(img_Empresa);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }




    private void updateEmpresa(String nombre_comercial, String pais , String departamento, String telefono, String categoria) {
        Map<String, Object> map = new HashMap<>();

        map.put("nombre_comercial", nombre_comercial);
        map.put("pais_id", pais);
        map.put("categoria_id",categoria);
        map.put("departamento_id", departamento);
        map.put("telefono", telefono);

        mFirestore.collection("empresas").document(id_categoria_x).update(map).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(getContext(), "Actualizacion Exitosamente",Toast.LENGTH_SHORT).show();
                getDialog().dismiss();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(), "Error al actualizar datos",Toast.LENGTH_SHORT).show();
            }
        });
    }




    private void postNuevaEmpresa(String nombre_comercial, String categoria, String pais, String departamento, String empresa_id_n, String telefono, String userIdx, Uri image_url) {

        if (image_url == null) {

            Map<String, Object> map = new HashMap<>();
            map.put("nombre_comercial",nombre_comercial);
            map.put("categoria_id",categoria);
            map.put("pais_id",pais);
            map.put("departamento_id",departamento);
            map.put("empresa_id",  empresa_id_n );
            map.put("telefono",  telefono );
            map.put("usuario_id",  userIdx );
            map.put("photo", "");

            mFirestore.collection("empresas").add(map).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                @Override
                public void onSuccess(DocumentReference documentReference) {
                    Toast.makeText(getContext(), "Registrado exitosamente", Toast.LENGTH_SHORT );
                    getDialog().dismiss();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getContext(), "Error al ingresar", Toast.LENGTH_SHORT );
                }
            });

        }else{
            progressDialog.setMessage("Subiendo foto");
            progressDialog.show();

            String rute_storage_photo = storage_path + "" + photo + "" + mAuth.getUid() + "" + empresa_id_n;
            StorageReference reference = storageReference.child(rute_storage_photo);
            reference.putFile(image_url).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                    while (!uriTask.isSuccessful()) ;
                    if (uriTask.isSuccessful()) {
                        uriTask.addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                String download_uri = uri.toString();

                                HashMap<String, Object> map = new HashMap<>();

                                map.put("nombre_comercial",nombre_comercial);
                                map.put("categoria_id",categoria);
                                map.put("pais_id",pais);
                                map.put("departamento_id",departamento);
                                map.put("empresa_id",  empresa_id_n );
                                map.put("telefono",  telefono );
                                map.put("usuario_id",  userIdx );
                                map.put("photo", download_uri);


                                mFirestore.collection("empresas").add(map).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                    @Override
                                    public void onSuccess(DocumentReference documentReference) {
                                        progressDialog.dismiss();
                                        Toast.makeText(getContext(), "Registrado exitosamente", Toast.LENGTH_SHORT);
                                        getDialog().dismiss();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(getContext(), "Error al ingresar", Toast.LENGTH_SHORT);
                                    }
                                });


                            }
                        });
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getContext(), "Error al cargar foto", Toast.LENGTH_SHORT).show();
                }
            });


        }


    }




}





