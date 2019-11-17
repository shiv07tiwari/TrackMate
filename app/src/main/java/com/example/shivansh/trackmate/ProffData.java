package com.example.shivansh.trackmate;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;

public class ProffData extends AppCompatActivity {

    private RadioGroup updateLoc,LocOnOff;
    private EditText nextAvail;
    private String location;
    private FirebaseAuth auth;
    private boolean avail = true;
    private Button update;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proff_data);
        final TextView cur_location = findViewById(R.id.current_status);
        final TextView status = findViewById(R.id.current_loction);

        auth = FirebaseAuth.getInstance();
        final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("professor");
        Log.e("dada",auth.getCurrentUser().getEmail());
        mDatabase.orderByChild("email").equalTo(auth.getCurrentUser().getEmail()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    Professor trans = postSnapshot.getValue(Professor.class);
                    if (trans.getOnline()) {
                        status.setText("Current Status : Online");
                    } else {
                        status.setText("Current Status : Offline");
                    }
                    cur_location.setText("Current Location : "+trans.getLocation());
                }
            }
            @Override
            public void onCancelled(DatabaseError firebaseError) {

            }
        });

        updateLoc = findViewById(R.id.updateLoc);
        LocOnOff = findViewById(R.id.locOnOff);
        nextAvail = findViewById(R.id.nextAvail);
        update = findViewById(R.id.update);

        checkAvail();

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final boolean[] isDone = {false};
                checkAvail();
                //Toast.makeText(ProffData.this,location + " " + avail + " " + nextAvail.getText().toString() , Toast.LENGTH_LONG).show();
                final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("professor");
                Log.e("dadajee",auth.getCurrentUser().getEmail());
                mDatabase.orderByChild("email").equalTo(auth.getCurrentUser().getEmail()).addValueEventListener(new ValueEventListener() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        if(!isDone[0]) {
                            Log.e("check", String.valueOf(snapshot.getChildrenCount()));
                            for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                                Professor trans = postSnapshot.getValue(Professor.class);
                                if (avail) {
                                    postSnapshot.getRef().child("location").setValue(location);
                                } else {
                                    postSnapshot.getRef().child("location").setValue("Not Available");
                                }
                                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
                                LocalDateTime now = LocalDateTime.now();
                                String[] data = dtf.format(now).split(" ");
                                postSnapshot.getRef().child("online").setValue(avail);
                                postSnapshot.getRef().child("nextAvailable").setValue(nextAvail.getText().toString());
                                postSnapshot.getRef().child("time").setValue(data[1]);
                                isDone[0] = true;
                                finish();
                            }
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError firebaseError) {
                    }
                });
            }
        });
    }

    public void checkAvail(){

        updateLoc.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case R.id.Admin_Building:
                        location = "Admin Building";
                        break;

                    case R.id.CC_1:
                        location = "CC-1";
                        break;

                    case R.id.CC_2:
                        location = " CC-2";
                        break;

                    case R.id.Main_Audi:
                        location = "Main Auditorium";
                        break;

                    case R.id.CC_3:
                        location = "CC-3";
                        break;
                }
            }
        });
        LocOnOff.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case R.id.online:
                        avail = true;
                        updateLoc.setVisibility(View.VISIBLE);
                        break;

                    case R.id.offline :
                        avail = false;
                        location = "Not Available";
                        updateLoc.setVisibility(View.GONE);
                        break;
                }
            }
        });
    }
}
