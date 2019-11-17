package com.example.shivansh.trackmate;

import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Collections;

public class Utility {

    private FirebaseAuth auth;
    Utility() {
        auth = FirebaseAuth.getInstance();
    }

    public String getProfessorEmaail(final String name) {
        final String[] PEmail = new String[1];
        final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("professor");
        mDatabase.orderByChild("name").equalTo(name).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if(snapshot.getChildrenCount() !=0) {
                    PEmail[0] =name;
                }
            }
            @Override
            public void onCancelled(DatabaseError firebaseError) {
            }
        });
        return PEmail[0];
    }

}
