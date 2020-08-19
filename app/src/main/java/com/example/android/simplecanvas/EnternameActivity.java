package com.example.android.simplecanvas;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class EnternameActivity extends AppCompatActivity {
    public static int X;
    public static int Y;
    int numberofplayers;
    EditText P1,P2,P3,P4;
    String sp1,sp2,sp3,sp4;
    Button startgame;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle extras= getIntent().getExtras();
        X= extras.getInt("intX");
        Y= extras.getInt("intY");
        numberofplayers=extras.getInt("noofplayers");
        setContentView(R.layout.enter_name);

        startgame= findViewById(R.id.start_game);
        P1 =  findViewById(R.id.editText1);
        P2 =  findViewById(R.id.editText2);
        P3 =  findViewById(R.id.editText3);
        P4 = findViewById(R.id.editText4);



        if(numberofplayers==2)
        {
            P3.setVisibility(View.INVISIBLE);
            P4.setVisibility(View.INVISIBLE);
            startgame.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    sp1= P1.getEditableText().toString();
                    sp2= P2.getEditableText().toString();


                    Intent intent = new Intent(EnternameActivity.this, gameActivity.class);
                    intent.putExtra("sp1",sp1);
                    intent.putExtra("sp2",sp2);
                    intent.putExtra("intX", X);
                    intent.putExtra("intY", Y);
                    intent.putExtra("numberofplayers", numberofplayers);
                    startActivity(intent);
                }
            });


        }
        if(numberofplayers==3)
        {
            P4.setVisibility(View.INVISIBLE);
            startgame.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    sp1= P1.getEditableText().toString();
                    sp2= P2.getEditableText().toString();
                    sp3= P3.getEditableText().toString();



                    Intent intent = new Intent(EnternameActivity.this, gameActivity.class);
                    intent.putExtra("sp1",sp1);
                    intent.putExtra("sp2",sp2);
                    intent.putExtra("sp3",sp3);
                    intent.putExtra("intX", X);
                    intent.putExtra("intY", Y);
                    intent.putExtra("numberofplayers", numberofplayers);
                    startActivity(intent);
                }
            });

        }
        if(numberofplayers==4)
        {

            startgame.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    sp1= P1.getEditableText().toString();
                    sp2= P2.getEditableText().toString();
                    sp3= P3.getEditableText().toString();
                    sp4= P4.getEditableText().toString();



                    Intent intent = new Intent(EnternameActivity.this, gameActivity.class);
                    intent.putExtra("sp1",sp1);
                    intent.putExtra("sp2",sp2);
                    intent.putExtra("sp3",sp3);
                    intent.putExtra("sp4",sp4);
                    intent.putExtra("intX", X);
                    intent.putExtra("intY", Y);
                    intent.putExtra("numberofplayers", numberofplayers);
                    startActivity(intent);
                }
            });

        }


    }

}
