package com.example.android.simplecanvas;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.android.simplecanvas.view.GridView;

public class gameActivity extends AppCompatActivity {
   public static int X;
   public static int Y;
   public static String Player1,Player2,Player3,Player4;
   public static int numberofplayers;
    public static TextView namep1,namep2,namep3,namep4;
   public static TextView Turncolour, scorep1,scorep2,scorep3,scorep4;
    public static int[] p;
    public static int plno,intscorep1,intscorep2,intscorep3,intscorep4;
    public static Dialog winnerdialog;
    TextView closescreen;
     public static MediaPlayer clicksound;
    public static MediaPlayer boxformed;
    public static MediaPlayer winner;
    public static Animation blink;
    public static Button undo;



    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle extras= getIntent().getExtras();
        X= extras.getInt("intX");
        Y= extras.getInt("intY");
        numberofplayers= extras.getInt("numberofplayers");
        setContentView(R.layout.game_screen);
        final GridView gridView= findViewById(R.id.gridofdots);
        Resources resources =getResources();
        namep1= findViewById(R.id.namep1);
        namep2= findViewById(R.id.namep2);
        namep3= findViewById(R.id.namep3);
        namep4= findViewById(R.id.namep4);
        scorep1=findViewById(R.id.scorep1);
        scorep2=findViewById(R.id.scorep2);
        scorep3=findViewById(R.id.scorep3);
        scorep4=findViewById(R.id.scorep4);
        Turncolour= findViewById(R.id.Turncolour);
        winnerdialog=new Dialog(this);
        closescreen= findViewById(R.id.close);
        clicksound = MediaPlayer.create(this, R.raw.clicksound);
        boxformed = MediaPlayer.create(this, R.raw.boxformed);
        winner = MediaPlayer.create(this, R.raw.winner);
        blink = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.blinkinganimation);
        undo=findViewById(R.id.undo);



        p= new int[numberofplayers];
        p[0]=1;
        for(int i=1;i<numberofplayers;i++)
        {
            p[i]=0;
        }
        plno=0;
        intscorep1=0;
        intscorep2=0;
        intscorep3=0;
        intscorep4=0;


        if(numberofplayers==2)
        {
           Player1=extras.getString("sp1");
           Player2=extras.getString("sp2");
           namep3.setVisibility(View.INVISIBLE);
           scorep3.setVisibility(View.INVISIBLE);
           namep4.setVisibility(View.INVISIBLE);
           scorep4.setVisibility(View.INVISIBLE);
           namep1.setText(Player1);
           namep2.setText(Player2);
            Turncolour.setBackgroundColor(resources.getColor(R.color.Blue));
            gridView.line.setColor(resources.getColor(R.color.Blue));

        }
        else if (numberofplayers == 3) {
            Player1=extras.getString("sp1");
            Player2=extras.getString("sp2");
            Player3=extras.getString("sp3");
            namep4.setVisibility(View.INVISIBLE);
            scorep4.setVisibility(View.INVISIBLE);
            namep1.setText(Player1);
            namep2.setText(Player2);
            namep3.setText(Player3);
            Turncolour.setBackgroundColor(resources.getColor(R.color.Blue));
            gridView.line.setColor(resources.getColor(R.color.Blue));


        }
        else if(numberofplayers==4){
            Player1=extras.getString("sp1");
            Player2=extras.getString("sp2");
            Player3=extras.getString("sp3");
            Player4=extras.getString("sp4");
            namep1.setText(Player1);
            namep2.setText(Player2);
            namep3.setText(Player3);
            namep4.setText(Player4);
            Turncolour.setBackgroundColor(resources.getColor(R.color.Blue));
            gridView.line.setColor(resources.getColor(R.color.Blue));

        }

        undo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gridView.undo();
            }
        });


        closescreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(gameActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });


    }

}

