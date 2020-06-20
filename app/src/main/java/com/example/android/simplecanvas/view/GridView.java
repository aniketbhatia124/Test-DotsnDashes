package com.example.android.simplecanvas.view;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;

import com.example.android.simplecanvas.R;
import com.example.android.simplecanvas.gameActivity;

import java.text.DecimalFormat;

public class GridView extends View {

    Paint dot;
    Paint line;
    Paint box;
    int rows= gameActivity.Y;
    int columns=gameActivity.X;
    double[][] xcoord = new double[rows+1][columns+1];
    double[][] ycoord = new double[rows+1][columns+1];
    int xnear1;
    int ynear1;
    int xnear2;
    int ynear2;
    Bitmap myBitmap;
    Canvas canvas;
    double[] xdotselected= new double[(rows>=columns)?4*(rows+1)*(rows):4*(columns+1)*(columns)];
    double[] ydotselected= new double[(rows>=columns)?4*(rows+1)*(rows):4*(columns+1)*(columns)];
    int k=0;
    double columnwidth;
    double rowwidth;

    public GridView(Context context) {
        super(context);
        init(null);


    }

    public GridView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public GridView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public GridView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs);
    }

    private void  init(@Nullable AttributeSet set){


                Resources resources =getResources();          //    java.lang.NullPointerException: Attempt to invoke virtual method 'long android.graphics.Paint.getNativeInstance()' on a null object reference
        dot= new Paint();                                  //    at android.graphics.Canvas.drawCircle(Canvas.java:1169)-- Paint object values must be defined in init() for all constructors
        dot.setFlags(Paint.ANTI_ALIAS_FLAG);
        dot.setStyle(Paint.Style.FILL);
        dot.setColor(resources.getColor(R.color.colorDot));
        line= new Paint();
        line.setColor(Color.BLACK);
        line.setStrokeWidth(7);
        box= new Paint();
        box.setColor(resources.getColor(R.color.colorDot));
        box.setStyle(Paint.Style.FILL);


    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        myBitmap= Bitmap.createBitmap(w,h,Bitmap.Config.ARGB_8888);
        canvas=new Canvas(myBitmap);
        rowwidth= (double) h / (columns+1);
        columnwidth= (double) w/(rows+1);

    }

    @RequiresApi(api = Build.VERSION_CODES.M)



 // Grid of Dots
    @Override
    protected void onDraw(Canvas canvas)
    {

          canvas.drawBitmap(myBitmap,0,0,null);
       // if((Math.sqrt(((xnear1-xnear2)*(xnear1-xnear2))+((ynear1-ynear2)*(ynear1-ynear2)))== width /((rows + 1))||(Math.sqrt(((xnear1-xnear2)*(xnear1-xnear2))+((ynear1-ynear2)*(ynear1-ynear2)))==(height/(columns + 1)))))
//     if(Math.abs(xnear1-xnear2)==(float) (width /(rows + 1))||Math.abs(ynear1-ynear2)==(float)(height/(columns + 1)))
//         if(xnear1==xnear2||ynear1==ynear2)
//               canvas.drawLine(xnear1,ynear1,xnear2,ynear2,line);
        for(int i=1; i<= rows; i++)
        {
            for(int j=1; j<=columns; j++) {
                canvas.drawCircle(i * (float)columnwidth,  j * (float)rowwidth, 7, dot);
                xcoord[i][j]= (double) i * columnwidth;
                ycoord[i][j]= (double) j * rowwidth;
            }

        }
    }

    @Override
    public boolean performClick() {
        return super.performClick();
    }



    //Drawing line
    public boolean onTouchEvent(MotionEvent event)
    {
        int flag1=0,flag2=0;
        super.onTouchEvent(event);
        performClick();
        final int height= getMeasuredHeight();
        final int width = getMeasuredWidth();
        if(event.getAction()==MotionEvent.ACTION_DOWN)
        {
            for(int i=1; i<= rows; i++)
            {
                for(int j=1; j<=columns; j++) {

                    if((Math.abs(xcoord[i][j]-event.getX())<  columnwidth/2  &&(Math.abs(ycoord[i][j]-event.getY()))< rowwidth/2))
                    {
                        xnear1 = i;
                        ynear1 = j;


                    }

                }
            }
        }
        if(event.getAction()==MotionEvent.ACTION_UP)
        {
            for(int i=1; i<= rows; i++)
            {
                for(int j=1; j<=columns; j++) {

                    if((Math.abs(xcoord[i][j]-event.getX())<  columnwidth/2 &&(Math.abs(ycoord[i][j]-event.getY()))< rowwidth/2)) {
                        xnear2 = i;
                        ynear2 = j;

                    }

                }
            }
            for(int a=0;a<k;a=a+2) {

                if((xdotselected[a]==xnear1&&ydotselected[a]==ynear1&&xdotselected[a+1]==xnear2&&ydotselected[a+1]==ynear2)||(xdotselected[a+1]==xnear1&&ydotselected[a+1]==ynear1&&xdotselected[a]==xnear2&&ydotselected[a]==ynear2))
                {
                 flag1=1;
                 break;
                }
            }
            if(flag1==0) {
                xdotselected[k] = xnear1;
                ydotselected[k] = ynear1;
                k++;
                xdotselected[k] = xnear2;
                ydotselected[k] = ynear2;
                k++;
                checkboxformed(xnear1,xnear2,ynear1,ynear2);


                if(((Math.abs(xcoord[xnear1][ynear1]-xcoord[xnear2][ynear2]))<=columnwidth+0.00005&&(Math.abs(xcoord[xnear1][ynear1]-xcoord[xnear2][ynear2]))>=columnwidth-0.00005)||((Math.abs(ycoord[xnear1][ynear1]-ycoord[xnear2][ynear2]))<=rowwidth+0.00005&&(Math.abs(ycoord[xnear1][ynear1]-ycoord[xnear2][ynear2]))>=rowwidth-0.00005)) {
                    if ((xnear1 == xnear2) || (ynear1 == ynear2)) {
                        canvas.drawLine((float)xcoord[xnear1][ynear1],(float) ycoord[xnear1][ynear1], (float)xcoord[xnear2][ynear2],(float)ycoord[xnear2][ynear2], line);
                    }
                }
                invalidate();
            }


        }

        return true;
    }
    void checkboxformed(int xnear1,int xnear2,int ynear1,int ynear2)
    {
        int flag1=0;
        int flag2=0;
        int flag3=0;
        int flag4=0;
        int flag5=0;
        int flag6=0;
        if(xnear1==xnear2)
        {

            for(int i=0;i<k;i+=2)
            {
                //Top left And  Bottom left edge
                if(((xdotselected[i]==xnear1)&&(ydotselected[i]==ynear1)&&(xdotselected[i+1]==xnear1-1)&&(ydotselected[i+1]==ynear1))||((xdotselected[i+1]==xnear1)&&(ydotselected[i+1]==ynear1)&&(xdotselected[i]==xnear1-1)&&(ydotselected[i]==ynear1))){
                    flag1=1;
                }
                if(((xdotselected[i]==xnear2)&&(ydotselected[i]==ynear2)&&(xdotselected[i+1]==xnear2-1)&&(ydotselected[i+1]==ynear2))||((xdotselected[i+1]==xnear2)&&(ydotselected[i+1]==ynear2)&&(xdotselected[i]==xnear2-1)&&(ydotselected[i]==ynear2))){
                    flag2=1;
                }
                //Left edge
                if(((xdotselected[i]==xnear1-1)&&(ydotselected[i]==ynear1)&&(xdotselected[i+1]==xnear2-1)&&(ydotselected[i+1]==ynear2))||((xdotselected[i+1]==xnear1-1)&&(ydotselected[i+1]==ynear1)&&(xdotselected[i]==xnear2-1)&&(ydotselected[i]==ynear2))){
                    flag3=1;
                }

                //Top right And Bottom right edge
                if(((xdotselected[i]==xnear1)&&(ydotselected[i]==ynear1)&&(xdotselected[i+1]==xnear1+1)&&(ydotselected[i+1]==ynear1))||((xdotselected[i+1]==xnear1)&&(ydotselected[i+1]==ynear1)&&(xdotselected[i]==xnear1+1)&&(ydotselected[i]==ynear1))){
                    flag4=1;
                }
                if(((xdotselected[i]==xnear2)&&(ydotselected[i]==ynear2)&&(xdotselected[i+1]==xnear2+1)&&(ydotselected[i+1]==ynear2))||((xdotselected[i+1]==xnear2)&&(ydotselected[i+1]==ynear2)&&(xdotselected[i]==xnear2+1)&&(ydotselected[i]==ynear2))){
                    flag5=1;
                }

                //Right edge
                if(((xdotselected[i]==xnear1+1)&&(ydotselected[i]==ynear1)&&(xdotselected[i+1]==xnear2+1)&&(ydotselected[i+1]==ynear2))||((xdotselected[i+1]==xnear1+1)&&(ydotselected[i+1]==ynear1)&&(xdotselected[i]==xnear2+1)&&(ydotselected[i]==ynear2))) {
                    flag6 = 1;
                }
            }

            //Left box
            if((flag1==1)&&(flag2==1)&&(flag3==1)){
                if(ynear1<ynear2) {
                    canvas.drawRect((float) xcoord[xnear1 - 1][ynear1], (float) ycoord[xnear1 - 1][ynear1] , (float) xcoord[xnear2][ynear2] , (float) ycoord[xnear2][ynear2], box);
                }
                else {
                    canvas.drawRect((float) xcoord[xnear2 - 1][ynear2], (float) ycoord[xnear2 - 1][ynear2] , (float) xcoord[xnear1][ynear1] , (float) ycoord[xnear1][ynear1] , box);
                }
                canvas.drawLine((float)xcoord[xnear1][ynear1],(float) ycoord[xnear1][ynear1], (float)xcoord[xnear1-1][ynear1],(float)ycoord[xnear1-1][ynear1], line);
                canvas.drawLine((float)xcoord[xnear2][ynear2],(float) ycoord[xnear2][ynear2], (float)xcoord[xnear2-1][ynear2],(float)ycoord[xnear2-1][ynear2], line);
                canvas.drawLine((float)xcoord[xnear1-1][ynear1],(float) ycoord[xnear1-1][ynear1], (float)xcoord[xnear2-1][ynear2],(float)ycoord[xnear2-1][ynear2], line);

            }

            //Right box
            if((flag4==1)&&(flag5==1)&&(flag6==1)){
                if(ynear1<ynear2){
                    canvas.drawRect((float) xcoord[xnear1][ynear1], (float) ycoord[xnear1][ynear1], (float) xcoord[xnear2+1][ynear2] , (float) ycoord[xnear2+1][ynear2], box);
                }
                else {
                    canvas.drawRect((float) xcoord[xnear2][ynear2], (float) ycoord[xnear2][ynear2], (float) xcoord[xnear1 + 1][ynear1] , (float) ycoord[xnear1 + 1][ynear1] , box);
                }
                canvas.drawLine((float)xcoord[xnear1][ynear1],(float) ycoord[xnear1][ynear1], (float)xcoord[xnear1+1][ynear1+1],(float)ycoord[xnear1+1][ynear1], line);
                canvas.drawLine((float)xcoord[xnear2][ynear2],(float) ycoord[xnear2][ynear2], (float)xcoord[xnear2+1][ynear2],(float)ycoord[xnear2+1][ynear2], line);
                canvas.drawLine((float)xcoord[xnear1+1][ynear1],(float) ycoord[xnear1+1][ynear1], (float)xcoord[xnear2+1][ynear2],(float)ycoord[xnear2+1][ynear2], line);
            }





        }

        else if(ynear1==ynear2)
        {


            for(int i=0;i<k;i+=2) {
                //Top left And  Top Right edge
                if (((xdotselected[i]==xnear1)&&(ydotselected[i]==ynear1)&&(xdotselected[i+1]==xnear1)&&(ydotselected[i+1]==ynear1-1))||((xdotselected[i+1]==xnear1)&&(ydotselected[i+1]==ynear1)&&(xdotselected[i]==xnear1)&&(ydotselected[i]==ynear1-1))){
                    flag1=1;
                }
                if (((xdotselected[i]==xnear2)&&(ydotselected[i]==ynear2)&&(xdotselected[i+1]==xnear2)&&(ydotselected[i+1]==ynear2-1))||((xdotselected[i+1]==xnear2)&&(ydotselected[i+1]==ynear2)&&(xdotselected[i]==xnear2)&&(ydotselected[i]==ynear2-1))){
                    flag2=1;
                }

                //Topmost edge
                if (((xdotselected[i] == xnear1) && (ydotselected[i] == ynear1-1) && (xdotselected[i + 1] == xnear2) && (ydotselected[i + 1] == ynear2-1)) || ((xdotselected[i + 1] == xnear1) && (ydotselected[i + 1] == ynear1 - 1) && (xdotselected[i] == xnear2) && (ydotselected[i] == ynear2 - 1))) {
                    flag3=1;
                }

                //Bottom left and Bottom right edge
                if(((xdotselected[i]==xnear1)&&(ydotselected[i]==ynear1)&&(xdotselected[i+1]==xnear1)&&(ydotselected[i+1]==ynear1+1))||((xdotselected[i+1]==xnear1)&&(ydotselected[i+1]==ynear1)&&(xdotselected[i]==xnear1)&&(ydotselected[i]==ynear1+1))){
                    flag4=1;
                }
                if(((xdotselected[i]==xnear2)&&(ydotselected[i]==ynear2)&&(xdotselected[i+1]==xnear2)&&(ydotselected[i+1]==ynear2+1))||((xdotselected[i+1]==xnear2)&&(ydotselected[i+1]==ynear2)&&(xdotselected[i]==xnear2)&&(ydotselected[i]==ynear2+1))){
                    flag5=1;
                }

                //Bottommost edge
                if(((xdotselected[i]==xnear1)&&(ydotselected[i]==ynear1+1)&&(xdotselected[i+1]==xnear2)&&(ydotselected[i+1]==ynear2+1))||((xdotselected[i+1]==xnear1)&&(ydotselected[i+1]==ynear1+1)&&(xdotselected[i]==xnear2)&&(ydotselected[i]==ynear2+1))){
                    flag6=1;
                }

            }
            //Top box
            if((flag1==1)&&(flag2==1)&&(flag3==1)){
                if(xnear1<xnear2){
                    canvas.drawRect((float) xcoord[xnear1][ynear1-1], (float) ycoord[xnear1][ynear1-1], (float) xcoord[xnear2][ynear2] , (float) ycoord[xnear2][ynear2], box);
                }
                else{
                    canvas.drawRect((float) xcoord[xnear2][ynear2-1], (float) ycoord[xnear2][ynear2-1], (float) xcoord[xnear1][ynear1] , (float) ycoord[xnear1][ynear1], box);
                }
                canvas.drawLine((float)xcoord[xnear1][ynear1],(float) ycoord[xnear1][ynear1], (float)xcoord[xnear1][ynear1-1],(float)ycoord[xnear1][ynear1-1], line);
                canvas.drawLine((float)xcoord[xnear2][ynear2],(float) ycoord[xnear2][ynear2], (float)xcoord[xnear2][ynear2-1],(float)ycoord[xnear2][ynear2-1], line);
                canvas.drawLine((float)xcoord[xnear1][ynear1-1],(float) ycoord[xnear1][ynear1-1], (float)xcoord[xnear2][ynear2-1],(float)ycoord[xnear2][ynear2-1], line);

            }
            //Bottom box
            if((flag4==1)&&(flag5==1)&&(flag6==1)){
                if(xnear1<xnear2){
                    canvas.drawRect((float) xcoord[xnear1][ynear1], (float) ycoord[xnear1][ynear1], (float) xcoord[xnear2][ynear2+1] , (float) ycoord[xnear2][ynear2+1], box);
                }
                else {
                    canvas.drawRect((float) xcoord[xnear2][ynear2], (float) ycoord[xnear2][ynear2], (float) xcoord[xnear1][ynear1 + 1], (float) ycoord[xnear1][ynear1 + 1], box);
                }
                canvas.drawLine((float)xcoord[xnear1][ynear1],(float) ycoord[xnear1][ynear1], (float)xcoord[xnear1][ynear1+1],(float)ycoord[xnear1][ynear1+1], line);
                canvas.drawLine((float)xcoord[xnear2][ynear2],(float) ycoord[xnear2][ynear2], (float)xcoord[xnear2][ynear2+1],(float)ycoord[xnear2][ynear2+1], line);
                canvas.drawLine((float)xcoord[xnear1][ynear1+1],(float) ycoord[xnear1][ynear1+1], (float)xcoord[xnear2][ynear2+1],(float)ycoord[xnear2][ynear2+1], line);
            }


        }

    }
 }


