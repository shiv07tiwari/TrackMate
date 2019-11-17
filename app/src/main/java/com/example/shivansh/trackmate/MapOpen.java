package com.example.shivansh.trackmate;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;

public class MapOpen extends AppCompatActivity {

    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COARSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;
    private boolean mLocationPermissionGranted = false;
    private GoogleMap mMap;
    double latitide = 25.4303;
    double longitude = 81.7706;
    private static final float DEFAULT_ZOOM = 18f;
    private EditText searchProff;
    private ImageButton searchBtn;
    private ArrayList<Professor> professors;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_open);
        final Button more = findViewById(R.id.more_button);
        more.setVisibility(View.GONE);

        final ArrayList<LocationClass> locationArrayList = new ArrayList<>();
        locationArrayList.add(new LocationClass("Admin Building",new LatLng(latitide,longitude),"Camera1"));
        locationArrayList.add(new LocationClass("CC-1",new LatLng(25.4315,81.7701),"Camera2"));
        locationArrayList.add(new LocationClass("CC-3",new LatLng(25.4321,81.7703),"Camera3"));
        locationArrayList.add(new LocationClass("CC-2",new LatLng(25.4301,81.7722),"Camera4"));
        locationArrayList.add(new LocationClass("Main Auditorium",new LatLng(25.4310,81.7693),"Camera5"));
        locationArrayList.add(new LocationClass("Main Ground",new LatLng(25.4291,81.7730),"Camera6"));



        searchProff = findViewById(R.id.searchProfess);
        searchBtn = findViewById(R.id.searchBtn);
        final ArrayList<LatLng> latLngArrayList = new ArrayList<>();
        latLngArrayList.add(new LatLng(25.4312,81.7709));
        getLocationPermission();

        searchProff.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                more.setVisibility(View.GONE);

            }
        });

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final LatLng loc = new LatLng(latitide,longitude);
//                if (searchProff.getText().toString().compareTo("Shivansh") == 0){
//                    loc = latLngArrayList.get(0);
//                    more.setVisibility(View.VISIBLE);
//                }

                final ArrayList<Professor> onlineProfessors = new ArrayList<>();
                FirebaseAuth auth = FirebaseAuth.getInstance();
                final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("professor");
                Log.e("dadajee",auth.getCurrentUser().getEmail());

                mDatabase.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        if (professors != null)
                            professors.clear();
                        Log.e("check", String.valueOf(snapshot.getChildrenCount()));
                        for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                            Professor trans = postSnapshot.getValue(Professor.class);
                            onlineProfessors.add(trans);
                        }
                        Log.e("TotalOnline", String.valueOf(onlineProfessors.size()));
                        professors = onlineProfessors;
                        doSOmething(locationArrayList,more,loc);
                    }
                    @Override
                    public void onCancelled(DatabaseError firebaseError) {
                        Log.e("egawedgiusajvdsazc",firebaseError.getMessage());
                    }
                });
            }
        });
        more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CustomDialogClass cdd=new CustomDialogClass(MapOpen.this,searchProff.getText().toString());
                cdd.show();
            }
        });
    }

    void doSOmething(ArrayList<LocationClass> locationArrayList, Button more, LatLng loc) {
        int flag = 0;
        Log.e("professors : ", String.valueOf(professors.size()));
        Log.e("locations : ", String.valueOf(locationArrayList.size()));
        for(int i = 0 ; i < professors.size() ; i++){
            Log.e("Professor Details : ",professors.get(i).getName()+" "+professors.get(i).getOnline());
            Log.e("log",searchProff.getText().toString());
            if(professors.get(i).getName().equals(searchProff.getText().toString())){
                if(!professors.get(i).getOnline()) {
                    Log.e("flag1",flag+"");
                    flag =2;
                    Log.e("Setting flag to 2","Yes");
                }
                more.setVisibility(View.VISIBLE);

                for(int j = 0 ; j < locationArrayList.size() ; j++){
                    Log.e("log","Teration12");
                    String a = professors.get(i).getLocation().trim();
                    String b = locationArrayList.get(j).getName().trim();


                    Log.e("checksum",a+" "+b);
                    Log.e("log",a.length()+" "+b.length());

                    Log.e("log", String.valueOf(a.compareTo(b)));
                    if(a.equals(b)){
                        Log.e("Log","match");
                        if(professors.get(i).getOnline()) {
                            loc = locationArrayList.get(j).getLatLng();
                        }
                        Log.e("jhgsdgisd","jgsiusgiugifs");
                        flag =1;
                        break;
                    }
                }
                if(flag == 1){
                    break;
                }
            }
        }

        if(flag != 1){
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MapOpen.this);
            if(flag==2) {
                alertDialogBuilder.setMessage("Professor is Offline !!");
            } else if (flag==0) {
                alertDialogBuilder.setMessage("Professor Not Found !!");
            }
            alertDialogBuilder.setNegativeButton("Exit", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.cancel();
                }
            });
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();

        }
//                if (searchProff.getText().toString().compareTo("r") == 0){
//                    loc = latLngArrayList.get(0);
//
//                }
        if(flag==1) {
            moveCamera(loc, DEFAULT_ZOOM, searchProff.getText().toString());
            mMap.setMyLocationEnabled(true);
        }
    }

    private void getDeviceLocation() {

        LatLng location = new LatLng(latitide, longitude);
        moveCamera(location, DEFAULT_ZOOM,"IIIT Allahabad");


    }

    private void moveCamera(LatLng latLng, float zoom, String title) {
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));

        if(!title.equals("IIIT Allahabad")) {
            MarkerOptions options = new MarkerOptions()
                    .position(latLng)
                    .title(title);
            mMap.addMarker(options);
        }

        hideSoftKeyboard();
    }

    private void initMap() {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                mMap = googleMap;
                Toast.makeText(MapOpen.this, "Map is Ready", Toast.LENGTH_LONG).show();
                if (mLocationPermissionGranted) {
                    getDeviceLocation();
                    if (ActivityCompat.checkSelfPermission(MapOpen.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(MapOpen.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return;
                    }
                    mMap.setMyLocationEnabled(true);
                }
            }
        });
    }

    private void getLocationPermission(){
        String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION};

        if(ContextCompat.checkSelfPermission(this.getApplicationContext(),FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            if(ContextCompat.checkSelfPermission(this.getApplicationContext(),COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                mLocationPermissionGranted = true;
                initMap();
            }else{
                ActivityCompat.requestPermissions(this,permissions,LOCATION_PERMISSION_REQUEST_CODE);
            }
        }
        else{
            ActivityCompat.requestPermissions(this,permissions,LOCATION_PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        mLocationPermissionGranted = false;
        switch (requestCode){
            case LOCATION_PERMISSION_REQUEST_CODE : {
                if(grantResults.length > 0){
                    for(int i = 0 ; i < grantResults.length ; i++){
                        if(grantResults[i] != PackageManager.PERMISSION_GRANTED){
                            mLocationPermissionGranted = false;
                            return;
                        }
                    }
                    mLocationPermissionGranted = true;
                    //initialize our map
                    initMap();
                }
            }
        }
    }

    private void hideSoftKeyboard(){
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }
}
