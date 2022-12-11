package com.example.examenandroid.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.examenandroid.R;
import com.example.examenandroid.models.User;
import com.example.examenandroid.providers.AuthProvider;
import com.example.examenandroid.providers.UsersProvider;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class RegistroActivity extends AppCompatActivity {

    Context mContext;
    AuthProvider mAuth;
    UsersProvider mUsersProvider;
    String[] COMUNAS = new String[]{"SELECCIONE:", "ARICA", "CAMARONES", "PUTRE", "GENERAL LAGOS", "IQUIQUE", "ALTO HOSPICIO", "POZO ALMONTE", "CAMIÑA", "COLCHANE", "HUARA", "PICA", "ANTOFAGASTA", "MEJILLONES", "SIERRA GORDA", "TALTAL", "CALAMA", "OLLAGÜE", "SAN PEDRO DE ATACAMA", "TOCOPILLA", "MARÍA ELENA", "COPIAPÓ", "CALDERA", "TIERRA AMARILLA", "CHAÑARAL", "DIEGO DE ALMAGRO", "VALLENAR", "ALTO DEL CARMEN", "FREIRINA", "HUASCO", "LA SERENA", "COQUIMBO", "ANDACOLLO", "LA HIGUERA", "PAIGUANO", "VICUÑA", "ILLAPEL", "CANELA", "LOS VILOS", "SALAMANCA", "OVALLE", "COMBARBALÁ", "MONTE PATRIA", "PUNITAQUI", "RÍO HURTADO", "VALPARAÍSO", "CASABLANCA", "CONCÓN", "JUAN FERNÁNDEZ", "PUCHUNCAVÍ", "QUINTERO", "VIÑA DEL MAR", "ISLA DE PASCUA", "LOS ANDES", "CALLE LARGA", "RINCONADA", "SAN ESTEBAN", "LA LIGUA", "CABILDO", "PAPUDO", "PETORCA", "ZAPALLAR", "QUILLOTA", "CALERA", "HIJUELAS", "LA CRUZ", "NOGALES", "SAN ANTONIO", "ALGARROBO", "CARTAGENA", "EL QUISCO", "EL TABO", "SANTO DOMINGO", "SAN FELIPE", "CATEMU", "LLAILLAY", "PANQUEHUE", "PUTAENDO", "SANTA MARÍA", "LIMACHE", "QUILPUÉ", "VILLA ALEMANA", "OLMUÉ", "RANCAGUA", "CODEGUA", "COINCO", "COLTAUCO", "DOÑIHUE", "GRANEROS", "LAS CABRAS", "MACHALÍ", "MALLOA", "MOSTAZAL", "OLIVAR", "PEUMO", "PICHIDEGUA", "QUINTA DE TILCOCO", "RENGO", "REQUÍNOA", "SAN VICENTE", "PICHILEMU", "LA ESTRELLA", "LITUECHE", "MARCHIHUE", "NAVIDAD", "PAREDONES", "SAN FERNANDO", "CHÉPICA", "CHIMBARONGO", "LOLOL", "NANCAGUA", "PALMILLA", "PERALILLO", "PLACILLA", "PUMANQUE", "SANTA CRUZ", "TALCA", "CONSTITUCIÓN", "CUREPTO", "EMPEDRADO", "MAULE", "PELARCO", "PENCAHUE", "RÍO CLARO", "SAN CLEMENTE", "SAN RAFAEL", "CAUQUENES", "CHANCO", "PELLUHUE", "CURICÓ", "HUALAÑÉ", "LICANTÉN", "MOLINA", "RAUCO", "ROMERAL", "SAGRADA FAMILIA", "TENO", "VICHUQUÉN", "LINARES", "COLBÚN", "LONGAVÍ", "PARRAL", "RETIRO", "SAN JAVIER", "VILLA ALEGRE", "YERBAS BUENAS", "CONCEPCIÓN", "CORONEL", "CHIGUAYANTE", "FLORIDA", "HUALQUI", "LOTA", "PENCO", "SAN PEDRO DE LA PAZ", "SANTA JUANA", "TALCAHUANO", "TOMÉ", "HUALPÉN", "LEBU", "ARAUCO", "CAÑETE", "CONTULMO", "CURANILAHUE", "LOS ALAMOS", "TIRÚA", "LOS ANGELES", "ANTUCO", "CABRERO", "LAJA", "MULCHÉN", "NACIMIENTO", "NEGRETE", "QUILACO", "QUILLECO", "SAN ROSENDO", "SANTA BÁRBARA", "TUCAPEL", "YUMBEL", "ALTO BIOBÍO", "CHILLÁN", "BULNES", "COBQUECURA", "COELEMU", "COIHUECO", "CHILLÁN VIEJO", "EL CARMEN", "NINHUE", "ÑIQUÉN", "PEMUCO", "PINTO", "PORTEZUELO", "QUILLÓN", "QUIRIHUE", "RÁNQUIL", "SAN CARLOS", "SAN FABIÁN", "SAN IGNACIO", "SAN NICOLÁS", "TREGUACO", "YUNGAY", "TEMUCO", "CARAHUE", "CUNCO", "CURARREHUE", "FREIRE", "GALVARINO", "GORBEA", "LAUTARO", "LONCOCHE", "MELIPEUCO", "NUEVA IMPERIAL", "PADRE LAS CASAS", "PERQUENCO", "PITRUFQUÉN", "PUCÓN", "SAAVEDRA", "TEODORO SCHMIDT", "TOLTÉN", "VILCÚN", "VILLARRICA", "CHOLCHOL", "ANGOL", "COLLIPULLI", "CURACAUTÍN", "ERCILLA", "LONQUIMAY", "LOS SAUCES", "LUMACO", "PURÉN", "RENAICO", "TRAIGUÉN", "VICTORIA", "VALDIVIA", "CORRAL", "LANCO", "LOS LAGOS", "MÁFIL", "MARIQUINA", "PAILLACO", "PANGUIPULLI", "LA UNIÓN", "FUTRONO", "LAGO RANCO", "RÍO BUENO", "PUERTO MONTT", "CALBUCO", "COCHAMÓ", "FRESIA", "FRUTILLAR", "LOS MUERMOS", "LLANQUIHUE", "MAULLÍN", "PUERTO VARAS", "CASTRO", "ANCUD", "CHONCHI", "CURACO DE VÉLEZ", "DALCAHUE", "PUQUELDÓN", "QUEILÉN", "QUELLÓN", "QUEMCHI", "QUINCHAO", "OSORNO", "PUERTO OCTAY", "PURRANQUE", "PUYEHUE", "RÍO NEGRO", "SAN JUAN DE LA COSTA", "SAN PABLO", "CHAITÉN", "FUTALEUFÚ", "HUALAIHUÉ", "PALENA", "COIHAIQUE", "LAGO VERDE", "AISÉN", "CISNES", "GUAITECAS", "COCHRANE", "OHIGGINS", "TORTEL", "CHILE CHICO", "RÍO IBÁÑEZ", "PUNTA ARENAS", "LAGUNA BLANCA", "RÍO VERDE", "SAN GREGORIO", "CABO DE HORNOS", "ANTÁRTICA", "PORVENIR", "PRIMAVERA", "TIMAUKEL", "NATALES", "TORRES DEL PAINE", "SANTIAGO", "CERRILLOS", "CERRO NAVIA", "CONCHALÍ", "EL BOSQUE", "ESTACIÓN CENTRAL", "HUECHURABA", "INDEPENDENCIA", "LA CISTERNA", "LA FLORIDA", "LA GRANJA", "LA PINTANA", "LA REINA", "LAS CONDES", "LO BARNECHEA", "LO ESPEJO", "LO PRADO", "MACUL", "MAIPÚ", "ÑUÑOA", "PEDRO AGUIRRE CERDA", "PEÑALOLÉN", "PROVIDENCIA", "PUDAHUEL", "QUILICURA", "QUINTA NORMAL", "RECOLETA", "RENCA", "SAN JOAQUÍN", "SAN MIGUEL", "SAN RAMÓN", "VITACURA", "PUENTE ALTO", "PIRQUE", "SAN JOSÉ DE MAIPO", "COLINA", "LAMPA", "TILTIL", "SAN BERNARDO", "BUIN", "CALERA DE TANGO", "PAINE", "MELIPILLA", "ALHUÉ", "CURACAVÍ", "MARÍA PINTO", "SAN PEDRO", "TALAGANTE", "EL MONTE", "ISLA DE MAIPO", "PADRE HURTADO", "PEÑAFLOR"};
    EditText mNombre, mApellido, mDireccion, mTelefono, mCorreo, mPassword;
    Spinner mComuna;
    Button mRegister;
    TextView mVolver;


    FirebaseFirestore mFirestorex;
    FirebaseAuth mAuthx;
    private static Integer secuencia;
    private RadioButton RUsuario;
    private RadioButton REmpresa;

    ArrayAdapter<String> mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        mContext = this;
        mAuth = AuthProvider.getInstance();
        mUsersProvider = UsersProvider.getInstance();



        secuencia = 0 ;

        mAuthx = FirebaseAuth.getInstance();
        mFirestorex = FirebaseFirestore.getInstance();

        mFirestorex.collection("user")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                secuencia = secuencia + 1;
                                Log.d("TAG", document.getId() + " => " + document.getData() + " total: " + String.valueOf(secuencia));

                            }
                            Log.d("secuencia: ", " numero consecutivo " + String.valueOf(secuencia+1) );
                        } else {
                            Log.d("TAG", "Error getting documents: ", task.getException());
                        }
                    }
                });

        mNombre = findViewById(R.id.nombre);
        mApellido = findViewById(R.id.apellidos);
        mDireccion = findViewById(R.id.direccion);
        mTelefono = findViewById(R.id.telefono);
        mCorreo = findViewById(R.id.correo);
        mPassword = findViewById(R.id.password);
        mComuna = findViewById(R.id.comuna);
        mRegister = findViewById(R.id.bt_register);
        mVolver = findViewById(R.id.bt_volver);

        RUsuario = (RadioButton) findViewById(R.id.RbtnUsuario);
        REmpresa = (RadioButton) findViewById(R.id.RbtnEmpresa);

        mAdapter = new ArrayAdapter<>(mContext, android.R.layout.simple_spinner_item, COMUNAS);
        mAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mComuna.setAdapter(mAdapter);


        mRegister.setOnClickListener(v -> {
            RegisterUser();
        });
    }

    private void RegisterUser() {
        String nombre = mNombre.getText().toString();
        String apellido = mApellido.getText().toString();
        String direccion = mDireccion.getText().toString();
        String telefono = mTelefono.getText().toString();
        String correo = mCorreo.getText().toString();
        String password = mPassword.getText().toString();

        String tipoUser = "";

        if (RUsuario.isChecked()==true) {
            tipoUser = "cliente";
        }else{
            //tipoUser = "";
        }

        if (REmpresa.isChecked()==true) {
            tipoUser = "empresa";
        }else{
            //tipoUser = "";
        }




        String finalTipoUser = tipoUser;
        mAuth.register(correo, password).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                User u = new User();
                u.setApellidos(apellido);
                u.setCorreo(correo);
                u.setDireccion(direccion);
                u.setTelefono(telefono);
                u.setId_comuna(mComuna.getSelectedItemPosition());
                u.setNombre_cliente(nombre);
                u.setId_cliente(mAuth.getUid());
                u.setTipouser(finalTipoUser);
                CreateUser(u);
                registrarUsuario(nombre+" "+apellido,correo,password,finalTipoUser);
            }
        }).addOnFailureListener(e -> {
            Toast.makeText(mContext, "Error:" + e.getMessage(), Toast.LENGTH_SHORT).show();
       });




    }

    private void CreateUser(User u) {
        mUsersProvider.CreateUser(u).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(mContext, "Cuenta creada Exitosamente!", Toast.LENGTH_SHORT).show();

                //GotoMain();
            }
        }).addOnFailureListener(e -> {
            Toast.makeText(mContext, "Error:" + e.getMessage(), Toast.LENGTH_SHORT).show();
        });

    }


    private void GotoMain() {
        Intent i = new Intent(mContext, MainActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
    }



    private void registrarUsuario(String nombreUsuario, String EmailUsuario, String passwordUsuario, String tipoUser){
        mAuthx.createUserWithEmailAndPassword(EmailUsuario,passwordUsuario).addOnCompleteListener(
                new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        String id = mAuthx.getCurrentUser().getUid();

                        Map<String, Object> map = new HashMap<>();
                        map.put("id", id);
                        map.put("name", nombreUsuario);
                        map.put("email", EmailUsuario);
                        map.put("password", passwordUsuario);
                        map.put("tipoUser", tipoUser);
                        map.put("usuario_id", secuencia+1);

                        mFirestorex.collection("user").document(id).set(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                mAuthx.signOut();
                                finish();
                                startActivity(new Intent(RegistroActivity.this, LoginActivity.class));
                                Toast.makeText(RegistroActivity.this, "Usuario Registrado con Exito!", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(RegistroActivity.this, "Error al Guardar", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
               // Toast.makeText(RegistroActivity.this, "Error al Registrar", Toast.LENGTH_SHORT).show();
            }
        });
    }
}