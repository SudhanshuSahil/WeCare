package com.example.demoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText name = findViewById(R.id.name);
        final Sharedpref sharedpref = new Sharedpref(MainActivity.this);

        sharedpref.setSetup(false);
//        if(sharedpref.getCheckedIn() == 1){
//            Intent myIntent = new Intent(MainActivity.this, Main2Activity.class);
//            MainActivity.this.startActivity(myIntent);
//        }
//        else if(sharedpref.getCheckedIn() == 2){
//            Intent myIntent = new Intent(MainActivity.this, Main3Activity.class);
//            MainActivity.this.startActivity(myIntent);
//        }

        Button submit = findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String n = name.getText().toString();
                sharedpref.setCheckedIn(1);

                if(!n.equals("")){
                    sharedpref.setName(n);
                    Intent myIntent = new Intent(MainActivity.this, Main2Activity.class);
                    MainActivity.this.startActivity(myIntent);
                }
                else {
                    Toast.makeText(getApplicationContext(), "Enter Name ", Toast.LENGTH_SHORT).show();
                }

            }
        });

        Button submit2 = findViewById(R.id.submit2);
        submit2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String n = name.getText().toString();
                sharedpref.setCheckedIn(2);

                if(!n.equals("")){
                    sharedpref.setName(n);
                    Intent myIntent = new Intent(MainActivity.this, Main3Activity.class);
                    MainActivity.this.startActivity(myIntent);
                }
                else {
                    Toast.makeText(getApplicationContext(), "Enter Name ", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


}
