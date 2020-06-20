package com.example.android.simplecanvas;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    Spinner dd_no_of_plrs;
    Button startgame;
    EditText edX;
    EditText edY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dd_no_of_plrs = (Spinner) findViewById(R.id.dd_no_of_plrs);
        String[] no_of_plrs = new String[]{" ","2", "3", "4"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, no_of_plrs);
        dd_no_of_plrs.setAdapter(adapter);
        startgame = findViewById(R.id.startgame);
        edX = (EditText) findViewById(R.id.X);
        edY = (EditText) findViewById(R.id.Y);

        dd_no_of_plrs.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                switch (position) {

                    case 0: Toast.makeText(MainActivity.this, "Select Number of Players",Toast.LENGTH_SHORT).show();
                                break;
                    case 1: entername(2);
                                break;
                    case 2:entername(3);
                                break;
                    case 3: entername(4);
                                break;

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    void  SetActivity()
         {
        startgame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



            }
        });

    }
    void entername(final int no_of_players){
        startgame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String X= edX.getEditableText().toString();
                String Y= edY.getEditableText().toString();
                int intX;
                int intY;
                try{
                    intX =Integer.parseInt(X);
                    intY =Integer.parseInt(Y);
                }

                catch (NumberFormatException ex){
                    Toast.makeText(MainActivity.this, "Enter a size of grid",Toast.LENGTH_SHORT).show();
                    return;
                }

                Intent intent = new Intent(MainActivity.this, EnternameActivity.class);
                intent.putExtra("intX", intX);
                intent.putExtra("intY", intY);
                intent.putExtra("noofplayers",no_of_players);
                startActivity(intent);



            }
        });
    }

}





