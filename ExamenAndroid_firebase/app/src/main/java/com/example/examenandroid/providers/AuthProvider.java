package com.example.examenandroid.providers;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AuthProvider {

    FirebaseAuth mAuth;

    private static AuthProvider INSTANCE = null;

    private AuthProvider() {
        mAuth = FirebaseAuth.getInstance();
    }

    public static AuthProvider getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new AuthProvider();
        }
        return INSTANCE;
    }

    public void logout() {
        mAuth.signOut();
    }

    public boolean islogin() {
        boolean e = false;
        if (mAuth.getCurrentUser() != null) {
            e = true;
        }
        return e;
    }

    public String getUid() {
        return mAuth.getUid();
    }

    public String getEmail() {
        return mAuth.getCurrentUser().getEmail();
    }

    public Task<AuthResult> signIn(String ph, String pw) {
        return mAuth.signInWithEmailAndPassword(ph, pw);
    }

    public Task<AuthResult> register(String ph, String pw) {
        return mAuth.createUserWithEmailAndPassword(ph, pw);
    }

    public FirebaseUser getUser() {
        return mAuth.getCurrentUser();
    }
}
