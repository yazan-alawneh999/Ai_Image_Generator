package com.example.aiimagegenerator.util;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FirebaseUtil {
    private static final String USERS_REF = "users";

    public static FirebaseAuth getAuth()
    {
        return FirebaseAuth.getInstance();
    }
    public static DatabaseReference getUserRef(){
        return FirebaseDatabase.getInstance()
                .getReference(USERS_REF);
    }
    public static DatabaseReference getCurrentUserRef(){
        return getUserRef().child(getAuth().getCurrentUser().getUid());

    }
}
