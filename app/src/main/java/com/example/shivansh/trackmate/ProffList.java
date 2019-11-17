package com.example.shivansh.trackmate;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ProffList {

    private FirebaseAuth auth;

    ProffList() {
    }
    ArrayList<Professor> getOnlineProfessors() {
        final ArrayList<Professor> onlineProfessors = new ArrayList<>();
        auth = FirebaseAuth.getInstance();
        final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("professor");
        Log.e("dadajee",auth.getCurrentUser().getEmail());

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                Log.e("check", String.valueOf(snapshot.getChildrenCount()));
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    Professor trans = postSnapshot.getValue(Professor.class);
                    onlineProfessors.add(trans);
                }
                Log.e("TotalOnline", String.valueOf(onlineProfessors.size()));

            }
            @Override
            public void onCancelled(DatabaseError firebaseError) {
                Log.e("egawedgiusajvdsazc",firebaseError.getMessage());
            }
        });
        return onlineProfessors;
    }
}
