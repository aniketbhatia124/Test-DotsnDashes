package com.example.android.simplecanvas.view;

import android.graphics.Paint;
import android.graphics.Path;

public class Drawaction{
    public Path path;
    public Paint paint;


    public Drawaction(Path path, Paint paint){
        this.path=path;
        this.paint=paint;
    }
}
