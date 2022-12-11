package com.example.examenandroid.providers;

import com.example.examenandroid.models.User;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class UsersProvider {
    DatabaseReference mDB;

    private static UsersProvider INSTANCE = null;

    private UsersProvider() {
        mDB = FirebaseDatabase.getInstance().getReference().child(User.USER);
    }

    public static UsersProvider getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new UsersProvider();
        }
        return INSTANCE;
    }

    public Task<Void> CreateUser(User mUa) {
        Map<String, Object> map = new HashMap<>();
        map.put(User.UID, mUa.getId_cliente());
        map.put(User.NOMBRE, mUa.getNombre_cliente());
        map.put(User.APELLIDOS, mUa.getApellidos());
        map.put(User.COMUNA, mUa.getId_comuna());
        map.put(User.CORREO, mUa.getCorreo());
        map.put(User.DIRECCION, mUa.getDireccion());
        map.put(User.TELEFONO, mUa.getTelefono());
        return mDB.child(mUa.getId_cliente()).setValue(map);
    }

    public DatabaseReference GetUser(String uid) {
        return mDB.child(uid);
    }
/*
    public Task<Void> UpdateUser(User mUa) {
        Map<String, Object> map = new HashMap<>();
        map.put(User.PNAME, mUa.getPn());
        map.put(User.SNAME, mUa.getSn());
        map.put(User.PSURNAME, mUa.getPa());
        map.put(User.SSURNAME, mUa.getSa());
        map.put(User.DATE, mUa.getFn());
        map.put(User.PHONE, mUa.getPh());
        map.put(User.EMAIL, mUa.getEm());
        map.put(User.UID, mUa.getUid());
        return mDB.child(User.USER).child(mUa.getUid()).updateChildren(map);
    }
    */
}
