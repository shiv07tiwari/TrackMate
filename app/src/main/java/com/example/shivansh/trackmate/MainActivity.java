package com.example.shivansh.trackmate;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        auth = FirebaseAuth.getInstance();
        super.onCreate(savedInstanceState);
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
