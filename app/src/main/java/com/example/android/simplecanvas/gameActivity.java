package com.example.android.simplecanvas;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Build;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.android.simplecanvas.view.GridView;

public class gameActivity extends AppCompatActivity {
   public static int X;
   public static int Y;
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle extras= getIntent().getExtras();
        X= extras.getInt("intX");
        Y= extras.getInt("intY");
        setContentView(R.layout.game_screen);



    }

}

