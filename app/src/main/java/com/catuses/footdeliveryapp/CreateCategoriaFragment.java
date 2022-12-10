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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

public class CreateCategoriaFragment extends DialogFragment {

    String id_categoria_x, categoria_photo,categoria_idx;
    Button btnInsertCategoria;
    EditText categoria, categoria_id;
    TextView fragmentTituloC;
    ImageView img_Categoria;
    ImageView btnEliminarFotoCategoria;
    ImageView btnBuscarFotoCategoria;

    StorageReference storageReference = FirebaseStorage.getInstance().getReference();
    String storage_path = "categorias/*";

    private static final int COD_SEL_STORAGE = 200;
    private static final int COD_SEL_IMAGE = 300;

    private Uri image_url;
    String photo = "photo";
    String idd;
    ProgressDialog progressDialog;
    Integer total;


    private FirebaseFirestore mFirestore;
    FirebaseAuth mAuth;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        progressDialog = new ProgressDialog(getContext());

        if (getArguments() != null) {
            id_categoria_x = getArguments().getString("id_categoria");
            categoria_idx = getArguments().getString("categoria_id");
            categoria_photo = getArguments().getString("photo");
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_create_categoria, container, false);
        mFirestore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        categoria = v.findViewById(R.id.txtNombreCategoria);
        categoria_id = v.findViewById(R.id.txtCategoriaId);
        fragmentTituloC = v.findViewById(R.id.fragmentTituloC);
        btnInsertCategoria = v.findViewById(R.id.btnInsertCategoria);
        img_Categoria = v.findViewById(R.id.img_Categoria);
        btnEliminarFotoCategoria = v.findViewById(R.id.btnEliminarFotoCategoria);
        btnBuscarFotoCategoria = v.findViewById(R.id.btnBuscarFotoCategoria);

        total = 0;

        mFirestore.collection("categorias")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                total = total + 1;
                                Log.d("TAG", document.getId() + " => " + document.getData() + " total: "+ total);
                                categoria_id.setText( String.valueOf( total+1));
                            }
                        } else {
                            Log.d("TAG", "Error getting documents: ", task.getException());
                        }
                    }
                });


        categoria_id.setVisibility(View.INVISIBLE);


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



        if (id_categoria_x == null ||  id_categoria_x == "") {
            //id_categoria_x = getArguments().getString("id_categoria");

            btnInsertCategoria.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String categoria_ = categoria.getText().toString().trim();
                    String categoria_id_ = categoria_id.getText().toString().trim();


                    if (categoria_.isEmpty() && categoria_id_.isEmpty()){
                        Toast.makeText(getContext(), "Ingresar los datos, estos no pueden estar vacios",Toast.LENGTH_SHORT).show();
                    }else{
                        postNuevaCategoria(categoria_, categoria_id_, image_url);
                    }
                }
            });

        }else{
            getCategoria(id_categoria_x);

            if (categoria_photo == "" ) {

            }else {
                Picasso.get()
                        .load(categoria_photo)
                        .resize(150, 150)
                        .into(img_Categoria);
            }

            fragmentTituloC.setText("ACTUALIZAR REGISTRO");
            btnInsertCategoria.setText("Actualizar");
            btnInsertCategoria.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String categoria_ = categoria.getText().toString().trim();
                    String categoria_id_x = id_categoria_x;

                    if (categoria_.isEmpty() && categoria_id_x.isEmpty()){
                        Toast.makeText(getContext(), "Ingresar los datos, estos no pueden estar vacios",Toast.LENGTH_SHORT).show();
                    }else{
                        updateCategoria(categoria_, categoria_id_x);
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
        StorageReference storageReference = firebaseStorage.getReferenceFromUrl(categoria_photo);
        storageReference.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.e("Picture"," # " + categoria_photo + "deleted");

                Map<String, Object> map = new HashMap<>();
                map.put("photo", "");

                mFirestore.collection("categorias").document(id_categoria_x).update(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(getContext(), "Foto Exitosamente",Toast.LENGTH_SHORT).show();
                        // getDialog().dismiss();
                        img_Categoria.setImageDrawable(getResources().getDrawable(R.drawable.motodelivery));
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
                        .into(img_Categoria);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }




    private void updateCategoria(String categoria_, String categoria_id_) {

        if (image_url == null) {

            Map<String, Object> map = new HashMap<>();
            map.put("categoria", categoria_);
            //map.put("photo", categoria_);

            mFirestore.collection("categorias").document(categoria_id_).update(map).addOnSuccessListener(new OnSuccessListener<Void>() {
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
        }else{
            progressDialog.setMessage("Actualizando categoria");
            progressDialog.show();
            String rute_storage_photo = storage_path + "" + photo + "" + mAuth.getUid() +""+ id_categoria_x;
            StorageReference reference = storageReference.child(rute_storage_photo);
            reference.putFile(image_url).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                    while (!uriTask.isSuccessful());
                    if (uriTask.isSuccessful()){
                        uriTask.addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                String download_uri = uri.toString();
                                HashMap<String, Object> map = new HashMap<>();
                                map.put("categoria", categoria_);
                                map.put("photo", download_uri);
                                mFirestore.collection("categorias").document(id_categoria_x).update(map);
                                Toast.makeText(getActivity(), "Categoria actualizada", Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                                getDialog().dismiss();
                            }
                        });
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getActivity(), "Error al cargar foto", Toast.LENGTH_SHORT).show();
                }
            });


        }

    }




    private void postNuevaCategoria(String descripcion, String id, Uri image_url){

        if (image_url == null) {

            Map<String, Object> map = new HashMap<>();
            map.put("categoria", descripcion);
            map.put("categoria_id", id);
            map.put("photo", "");

            mFirestore.collection("categorias").add(map).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                @Override
                public void onSuccess(DocumentReference documentReference) {
                    Toast.makeText(getContext(), "Categoria Creada Exitosamente", Toast.LENGTH_SHORT).show();
                    getDialog().dismiss();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getContext(), "Error al insertar datos", Toast.LENGTH_SHORT).show();
                }
            });

        }else{

            progressDialog.setMessage("Subiendo foto");
            progressDialog.show();


            String rute_storage_photo = storage_path + "" + photo + "" + mAuth.getUid() + "" + id;
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


                                map.put("categoria", descripcion);
                                map.put("categoria_id", id);
                                map.put("photo", download_uri);


                                mFirestore.collection("categorias").add(map).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
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

    private void getCategoria(String id){
        mFirestore.collection("categorias").document(id).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                String categoriaCatgeroria = documentSnapshot.getString("categoria");
                categoria.setText(categoriaCatgeroria);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(), "Error al obtener los datos",Toast.LENGTH_SHORT).show();
            }
        });
    }

}