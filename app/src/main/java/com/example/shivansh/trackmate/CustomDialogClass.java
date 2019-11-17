package com.example.shivansh.trackmate;

import android.app.Activity;
import android.app.Dialog;
import android.nfc.TagLostException;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class CustomDialogClass extends Dialog implements
        android.view.View.OnClickListener {

    public Activity c;
    public Dialog d;
    public String Pname;

    public CustomDialogClass(Activity a, String name) {
        super(a);
        // TODO Auto-generated constructor stub
        this.c = a;
        this.Pname=name;
    }

    private FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.custom_dialog);
        final TextView name = findViewById(R.id.dia_name);
        final TextView email = findViewById(R.id.dia_mail);
        final TextView status = findViewById(R.id.dia_status);
        final TextView location = findViewById(R.id.dia_location);
        final TextView time = findViewById(R.id.dia_time);

        auth = FirebaseAuth.getInstance();


        final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("professor");
        Log.e("dadajee",auth.getCurrentUser().getEmail());
        mDatabase.orderByChild("name").equalTo(Pname).addValueEventListener(new ValueEventListener() {
            final boolean[] isDone = {false};
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if(!isDone[0]) {
                    Log.e("check", String.valueOf(snapshot.getChildrenCount()));
                    for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                        Professor trans = postSnapshot.getValue(Professor.class);
                        boolean avail = trans.getOnline();
                        name.setText("Name : "+trans.getName());
                        email.setText("E-Mail : "+trans.getEmail());
                        if (avail) {
                            status.setText("Current Status : Online");
                        } else {
                            status.setText("Current Status : Offline");
                        }
                        location.setText("Current Location : "+trans.getLocation());
                        time.setText("Last Location Update on : "+trans.getTime());
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError firebaseError) {
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.okay_button:
                c.finish();
                break;
            default:
                break;
        }
        dismiss();
    }
}