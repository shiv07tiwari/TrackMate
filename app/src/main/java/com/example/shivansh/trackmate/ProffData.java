package com.example.shivansh.trackmate;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class ProffData extends AppCompatActivity {

    private RadioGroup updateLoc,LocOnOff;
    private EditText nextAvail;
    private String location;
    private boolean avail = true;
    private Button update;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proff_data);

        updateLoc = (RadioGroup)findViewById(R.id.updateLoc);
        LocOnOff = (RadioGroup)findViewById(R.id.locOnOff);
        nextAvail = (EditText)findViewById(R.id.nextAvail);
        update = (Button)findViewById(R.id.update);

        checkAvail();

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAvail();
                Toast.makeText(ProffData.this,location + " " + avail + " " + nextAvail.getText().toString() , Toast.LENGTH_LONG).show();
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
                        nextAvail.setVisibility(View.GONE);
                        break;

                    case R.id.offline :
                        avail = false;
                        nextAvail.setVisibility(View.VISIBLE);
                        break;
                }
            }
        });
    }
}
