package com.example.shivansh.trackmate;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignUp extends AppCompatActivity {

    private FirebaseAuth auth;
    ProgressBar progressBar = null;
    Button reg= null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        auth = FirebaseAuth.getInstance();

        reg = findViewById(R.id.signup_register);
        final EditText id = findViewById(R.id.signup_id);
        final EditText pass = findViewById(R.id.signup_password);
        progressBar = findViewById(R.id.signup_progress);
        final EditText name = findViewById(R.id.signup_name);
        final EditText subject = findViewById(R.id.roll_no);
        final TextView already = findViewById(R.id.already);

        final int[] userType = new int[1];
        userType[0] = -1;
        already.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignUp.this, SignInActivity.class));
                finish();
            }
        });
        RadioGroup rg = findViewById(R.id.RGroup);
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId)
            {
                switch(checkedId)
                {
                    case R.id.radio_proff:
                        userType[0] = 0;
                        subject.setVisibility(View.GONE);
                        break;
                    case R.id.radio_student:
                        subject.setVisibility(View.VISIBLE);
                        userType[0] = 1;
                        break;
                }
            }
        });

        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String email = id.getText().toString().trim();
                final String password = pass.getText().toString().trim();
                final String Roll = subject.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Enter User ID!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }
                progressBar.setVisibility(View.VISIBLE);
                reg.setVisibility(View.GONE);
                //create user
                auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(SignUp.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                Toast.makeText(SignUp.this, "New User Created Successfully !", Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.GONE);
                                reg.setVisibility(View.VISIBLE);
                                if (!task.isSuccessful()) {
                                    Toast.makeText(SignUp.this, "Authentication failed." + task.getException(),
                                            Toast.LENGTH_SHORT).show();
                                } else {
                                    final String t = auth.getCurrentUser().getUid();
                                    Log.e("log test",t);
                                    SharedPreferences.Editor editor = getSharedPreferences("name", MODE_PRIVATE).edit();
                                    editor.putInt("type", userType[0]);
                                    editor.apply();
                                    if (userType[0] == 0) {
                                        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("professor");
                                        String uname = name.getText().toString();
                                        Professor p = new Professor(uname,email,"Offline","Nothing to show",false,userType[0]);
                                        mDatabase.child(t).setValue(p);
                                        startActivity(new Intent(SignUp.this, MainActivity.class));
                                        finish();
                                    } else if (userType[0] == 1) {
                                        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("student");
                                        String uname = name.getText().toString();
                                        Student s = new Student(uname,email,Roll,userType[0]);
                                        mDatabase.child(t).setValue(s);
                                        startActivity(new Intent(SignUp.this, MainActivity.class));
                                        finish();
                                    }
                                }
                            }
                        });
            }
        });
    }
    @Override
    protected void onResume() {
        super.onResume();
        progressBar.setVisibility(View.GONE);
        reg.setVisibility(View.VISIBLE);
    }
}