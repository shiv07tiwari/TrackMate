package com.example.shivansh.trackmate;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView mRecyclerView;
    final ArrayList<Requests> myDataset = new ArrayList<>();
    private RecyclerView.Adapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        auth = FirebaseAuth.getInstance();
        super.onCreate(savedInstanceState);
        ProffList p = new ProffList();
        SharedPreferences prefs = getSharedPreferences("name", MODE_PRIVATE);
        Integer type = prefs.getInt("type", -1);
        Log.e("type", String.valueOf(type));
        if(type==0) {
            Toast.makeText(MainActivity.this,"Prof",Toast.LENGTH_LONG).show();
        } else if(type==1) {
            Toast.makeText(MainActivity.this,"Stu",Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(MainActivity.this,"Error",Toast.LENGTH_LONG).show();
        }
        setContentView(R.layout.activity_main);
        mRecyclerView = findViewById(R.id.request_list);
        final ProgressBar progress = findViewById(R.id.progress_bar);
        FloatingActionButton fab = findViewById(R.id.new_request);
        final Button openMap = findViewById(R.id.open_map);
        openMap.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, MapOpen.class));
            }
        });

        if (type == 0) {
            openMap.setVisibility(View.INVISIBLE);
            mRecyclerView.setHasFixedSize(true);
            mRecyclerView.setVisibility(View.GONE);
            mLayoutManager = new LinearLayoutManager(this);
            mRecyclerView.setLayoutManager(mLayoutManager);
            fab.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    startActivity(new Intent(MainActivity.this, ProffData.class));
                }
            });

            final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("request");
            Log.e("dada",auth.getCurrentUser().getEmail());
            mDatabase.orderByChild("professor").equalTo(auth.getCurrentUser().getEmail()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    myDataset.clear();
                    progress.setVisibility(View.VISIBLE);
                    Log.e("size", String.valueOf(snapshot.getChildrenCount()));
                    for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                        Requests trans = postSnapshot.getValue(Requests.class);
                        myDataset.add(trans);
                    }
                    Collections.reverse(myDataset);
                    if (myDataset.size()==0) {
                        progress.setVisibility(View.GONE);
                        mRecyclerView.setVisibility(View.GONE);
                        openMap.setVisibility(View.VISIBLE);
                    } else {
                        progress.setVisibility(View.GONE);
                        mRecyclerView.setVisibility(View.VISIBLE);
                        openMap.setVisibility(View.VISIBLE);
                    }
                    mAdapter = new ListAdapter(myDataset,getApplicationContext());
                    mRecyclerView.setAdapter(mAdapter);
                }
                @Override
                public void onCancelled(DatabaseError firebaseError) {
                    /*
                     * You may print the error message.
                     **/
                }
            });
        } else if (type == 1) {
            mRecyclerView.setVisibility(View.GONE);
            openMap.setVisibility(View.INVISIBLE);
            fab.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    startActivity(new Intent(MainActivity.this, NewRequestActivity.class));
                }
            });
            mRecyclerView.setHasFixedSize(true);
            mRecyclerView.setVisibility(View.GONE);
            mLayoutManager = new LinearLayoutManager(this);
            mRecyclerView.setLayoutManager(mLayoutManager);

            final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("request");
            Log.e("dada",auth.getCurrentUser().getEmail());
            mDatabase.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    myDataset.clear();
                    progress.setVisibility(View.VISIBLE);
                    Log.e("size", String.valueOf(snapshot.getChildrenCount()));
                    for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                        Requests trans = postSnapshot.getValue(Requests.class);
                        myDataset.add(trans);
                    }
                    Collections.reverse(myDataset);
                    if (myDataset.size()==0) {
                        progress.setVisibility(View.GONE);
                        mRecyclerView.setVisibility(View.GONE);
                        openMap.setVisibility(View.VISIBLE);
                    } else {
                        progress.setVisibility(View.GONE);
                        mRecyclerView.setVisibility(View.VISIBLE);
                        openMap.setVisibility(View.VISIBLE);
                    }
                    mAdapter = new ListAdapter2(myDataset,getApplicationContext());
                    mRecyclerView.setAdapter(mAdapter);
                }
                @Override
                public void onCancelled(DatabaseError firebaseError) {
                    /*
                     * You may print the error message.
                     **/
                }
            });
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_logout) {
            final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setMessage("Are you sure you want to Logout ?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            auth.signOut();
                            startActivity(new Intent(MainActivity.this, SignInActivity.class));
                            finish();
                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                        }
                    });
            builder.create().show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
