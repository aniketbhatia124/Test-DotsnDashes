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

public class GridView extends View {

    Paint dot;
    Paint line;
    Paint box;
    int rows= gameActivity.Y;
    int columns=gameActivity.X;
    float[][] xcoord = new float[rows+1][columns+1];
    float[][] ycoord = new float[rows+1][columns+1];
    float xnear1;
    float ynear1;
    float xnear2;
    float ynear2;
    Bitmap myBitmap;
    Canvas canvas;
    float[] xdotselected= new float[(rows>=columns)?4*(rows+1)*(rows):4*(columns+1)*(columns)];
    float[] ydotselected= new float[(rows>=columns)?4*(rows+1)*(rows):4*(columns+1)*(columns)];
    int k=0;
    float columnwidth;
    float rowwidth;

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
        line.setStrokeWidth(5);
        box= new Paint();
        box.setColor(resources.getColor(R.color.colorDot));
        box.setStyle(Paint.Style.FILL);


    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        myBitmap= Bitmap.createBitmap(w,h,Bitmap.Config.ARGB_8888);
        canvas=new Canvas(myBitmap);
        rowwidth= (float)h / (columns + 1);
        columnwidth= (float)w/(rows + 1);

    }

    @RequiresApi(api = Build.VERSION_CODES.M)



 // Grid of Dots
    @Override
    protected void onDraw(Canvas canvas)
    {
        final int height= getMeasuredHeight();
        final int width = getMeasuredWidth();
          canvas.drawBitmap(myBitmap,0,0,null);
       // if((Math.sqrt(((xnear1-xnear2)*(xnear1-xnear2))+((ynear1-ynear2)*(ynear1-ynear2)))== width /((rows + 1))||(Math.sqrt(((xnear1-xnear2)*(xnear1-xnear2))+((ynear1-ynear2)*(ynear1-ynear2)))==(height/(columns + 1)))))
//     if(Math.abs(xnear1-xnear2)==(float) (width /(rows + 1))||Math.abs(ynear1-ynear2)==(float)(height/(columns + 1)))
//         if(xnear1==xnear2||ynear1==ynear2)
//               canvas.drawLine(xnear1,ynear1,xnear2,ynear2,line);
        for(int i=1; i<= rows; i++)
        {
            for(int j=1; j<=columns; j++) {
                canvas.drawCircle((float)i * columnwidth, (float) j * rowwidth, 7, dot);
                xcoord[i][j]= (float) i * columnwidth;
                ycoord[i][j]= (float) j * rowwidth;
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
                        xnear1 = xcoord[i][j];
                        ynear1 = ycoord[i][j];


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
                        xnear2 = xcoord[i][j];
                        ynear2 = ycoord[i][j];

                    }

                }
            }
            for(int a=0;a<k;a=a+2) {

                if((xdotselected[a]==xnear1&&ydotselected[a]==ynear1&&xdotselected[a+1]==xnear2&&ydotselected[a+1]==ynear2)||(xdotselected[a+1]==xnear1&&ydotselected[a+1]==ynear1&&xdotselected[a]==xnear2&&ydotselected[a]==ynear2))
                {
                 flag1=1;
                }
            }
            if(flag1==0) {
                xdotselected[k] = xnear1;
                ydotselected[k] = ynear1;
                k++;
                xdotselected[k] = xnear2;
                ydotselected[k] = ynear2;
                k++;
            }
            checkboxformed(xnear1,xnear2,ynear1,ynear2);

            if((Math.abs(xnear1-xnear2)==columnwidth)||(Math.abs(ynear1-ynear2)==rowwidth))
               if((xnear1==xnear2)||(ynear1==ynear2))
            canvas.drawLine(xnear1,ynear1,xnear2,ynear2,line);
            invalidate();




        }

        return true;
    }
    void checkboxformed(float xnear1,float xnear2,float ynear1,float ynear2)
    {
        int flag1=0,flag2=0,flag3=0,flag4=0;
        int lflag1 =0, lflag2=0,lflag3=0;
        int[] points= new int[4];
        if(xnear1==xnear2)
        {
//          for(int a=1; a<=k;a++)
//          {
//              if(xdotselected[a]==xnear1&&ydotselected[a]==ynear1)
//                  for(int b=1; b<=k;b++)
//                      if(xdotselected[b]==xnear2&&ydotselected[b]==ynear2)
//                          for(int c=1;c<=k;c++)
//                              if((xdotselected[c]==xnear1+rowwidth && ydotselected[c]==ynear1)||(xdotselected[c]==xnear1-rowwidth && ydotselected[c]==ynear1))
            for(int a=0; a<=k;a++)
            {
                if(xdotselected[a]==xnear1&&ydotselected[a]==ynear1) {
                    flag1 = 1;
                    points[0]=a;
                }
                if(xdotselected[a]==xnear2&&ydotselected[a]==ynear2){
                    flag2=1;
                    points[1]=a;
                }
                if((xdotselected[a]==xnear1+columnwidth && ydotselected[a]==ynear1)||(xdotselected[a]==xnear1-columnwidth && ydotselected[a]==ynear1)) {
                    if(a%2==0) {
                        if (((xdotselected[a + 1] == xdotselected[a] ) && (ydotselected[a + 1]+rowwidth == ydotselected[a])) || ((xdotselected[a + 1]== xdotselected[a] ) && (ydotselected[a + 1] -rowwidth == ydotselected[a] ))) {
                            flag3 = 1;
                            points[2] = a;
                        }
                    }
                    else {
                            if (((xdotselected[a - 1]  == xdotselected[a]) && (ydotselected[a - 1]+rowwidth == ydotselected[a])) || ((xdotselected[a - 1] == xdotselected[a] ) && (ydotselected[a - 1]-rowwidth == ydotselected[a] ))) {
                                flag3 = 1;
                                points[2] = a;
                            }
                        }
                }

                if((xdotselected[a]==xnear2+columnwidth && ydotselected[a]==ynear2)||(xdotselected[a]==xnear2-columnwidth && ydotselected[a]==ynear2)){
                    if(a%2==0) {
                        if (((xdotselected[a + 1] == xdotselected[a]) && (ydotselected[a + 1] +rowwidth== ydotselected[a])) || ((xdotselected[a + 1] == xdotselected[a]) && (ydotselected[a + 1] -rowwidth == ydotselected[a] ))) {
                            flag4 = 1;
                            points[3] = a;
                        }
                    }
                    else {
                            if (((xdotselected[a - 1] == xdotselected[a] ) && (ydotselected[a - 1] +rowwidth== ydotselected[a] )) || ((xdotselected[a - 1] == xdotselected[a] ) && (ydotselected[a - 1] -rowwidth == ydotselected[a] ))) {
                                flag4 = 1;
                                points[3] = a;
                            }
                        }

                }
            }

            if(flag1==1&&flag2==1&&flag3==1&&flag4==1)

                for(int b=0; b<k;b=b+2) {
                    if ((xdotselected[b] ==xdotselected[points[2]] ) && (ydotselected[points[2]] == ydotselected[b])&&(xdotselected[b+1]==xdotselected[points[3]])&&(ydotselected[b+1]==ydotselected[points[3]]) )
                        lflag1 = 1;
                    else if (( xdotselected[b+1]==xdotselected[points[2]] ) && (ydotselected[points[2]] == ydotselected[b+1])&&(xdotselected[b]==xdotselected[points[3]])&&(ydotselected[b]==ydotselected[points[3]]) )
                        lflag1 = 1;

                    if((xnear1 == xdotselected[b]) && (ynear1== ydotselected[b])&&(xdotselected[b+1]==xdotselected[points[2]])&&(ydotselected[b+1]==ydotselected[points[2]]))
                        lflag2=1;
                    else if((xnear1 == xdotselected[b+1]) && (ynear1== ydotselected[b+1])&&(xdotselected[b]==xdotselected[points[2]])&&(ydotselected[b]==ydotselected[points[2]]))
                        lflag2=1;

                    if((xnear2 == xdotselected[b]) && (ynear2== ydotselected[b])&&(xdotselected[b+1]==xdotselected[points[3]])&&(ydotselected[b+1]==ydotselected[points[3]]))
                        lflag3=1;
                    else if((xnear2 == xdotselected[b+1]) && (ynear2== ydotselected[b+1])&&(xdotselected[b]==xdotselected[points[3]])&&(ydotselected[b]==ydotselected[points[3]]))
                        lflag3=1;
                }



              if(flag1==1&&flag2==1&&flag3==1&&flag4==1&&lflag1==1&&lflag2==1&&lflag3==1){
                  if(ynear1<ynear2)
                        if(xdotselected[points[2]]<xnear1)
                            canvas.drawRect(xdotselected[points[2]]+3,ynear1+3, xnear2-2 ,ynear2-2,box);
                        else
                            canvas.drawRect(xnear1+3,ynear1+3,xdotselected[points[3]]-2 ,ynear2-2,box);

                else
                    if(xdotselected[points[3]]<xnear2)
                        canvas.drawRect(xdotselected[points[3]]+3,ynear2+3, xnear1-2 ,ynear1-2,box);
                    else
                        canvas.drawRect(xnear2+3,ynear2+3,xdotselected[points[2]]-2 ,ynear1-2,box);
            }

        }

        else if(ynear1==ynear2)
        {
            for(int a=0; a<=k;a++)
            {
                if(xdotselected[a]==xnear1&&ydotselected[a]==ynear1) {
                    flag1 = 1;
                    points[0]=a;
                }
                if(xdotselected[a]==xnear2&&ydotselected[a]==ynear2){
                    flag2=1;
                    points[1]=a;
                }
                if((xdotselected[a]==xnear1 && ydotselected[a]==ynear1+rowwidth)||(xdotselected[a]==xnear1 && ydotselected[a]==ynear1-rowwidth)) {
                    if(a%2==0) {
                        if (((xdotselected[a + 1]+ columnwidth == xdotselected[a]) && (ydotselected[a + 1] == ydotselected[a] )) || ((xdotselected[a + 1]-columnwidth == xdotselected[a] ) && (ydotselected[a + 1]  == ydotselected[a]))) {
                            flag3 = 1;
                            points[2] = a;
                        }
                    }
                    else {
                            if (((xdotselected[a - 1] + columnwidth== xdotselected[a]) && (ydotselected[a - 1] == ydotselected[a])) || ((xdotselected[a - 1]-columnwidth  == xdotselected[a]) && (ydotselected[a - 1]== ydotselected[a] ))) {
                                flag3 = 1;
                                points[2] = a;
                            }
                        }
                }

                if((xdotselected[a]==xnear2 && ydotselected[a]==ynear2+rowwidth)||(xdotselected[a]==xnear2 && ydotselected[a]==ynear2-rowwidth)){
                    if(a%2==0) {
                        if (((xdotselected[a + 1] + columnwidth== xdotselected[a]) && (ydotselected[a + 1]  == ydotselected[a])) || ((xdotselected[a + 1]-columnwidth== xdotselected[a] ) && (ydotselected[a + 1] == ydotselected[a] ))) {
                            flag4 = 1;
                            points[3] = a;
                        }
                    }
                    else {
                            if (((xdotselected[a - 1]+ columnwidth== xdotselected[a] ) && (ydotselected[a - 1] == ydotselected[a])) || (xdotselected[a - 1]-columnwidth == xdotselected[a]) && (ydotselected[a - 1] == ydotselected[a] )) {
                                flag4 = 1;
                                points[3] = a;
                            }
                        }
                }

            }

            if(flag1==1&&flag2==1&&flag3==1&&flag4==1)

            for(int b=0; b<k;b=b+2) {
                if ((xdotselected[points[2]] == xdotselected[b]) && (ydotselected[points[2]] == ydotselected[b])&&(xdotselected[b+1]==xdotselected[points[3]])&&(ydotselected[b+1]==ydotselected[points[3]]) )
                    lflag1 = 1;
                else if ((xdotselected[points[2]] == xdotselected[b+1]) && (ydotselected[points[2]] == ydotselected[b+1])&&(xdotselected[b]==xdotselected[points[3]])&&(ydotselected[b]==ydotselected[points[3]]) )
                    lflag1 = 1;

                if((xnear1 == xdotselected[b]) && (ynear1== ydotselected[b])&&(xdotselected[b+1]==xdotselected[points[2]])&&(ydotselected[b+1]==ydotselected[points[2]]))
                    lflag2=1;
                else if((xnear1 == xdotselected[b+1]) && (ynear1== ydotselected[b+1])&&(xdotselected[b]==xdotselected[points[2]])&&(ydotselected[b]==ydotselected[points[2]]))
                    lflag2=1;

                if((xnear2 == xdotselected[b]) && (ynear2== ydotselected[b])&&(xdotselected[b+1]==xdotselected[points[3]])&&(ydotselected[b+1]==ydotselected[points[3]]))
                    lflag3=1;
                else if((xnear2 == xdotselected[b+1]) && (ynear2== ydotselected[b+1])&&(xdotselected[b]==xdotselected[points[3]])&&(ydotselected[b]==ydotselected[points[3]]))
                    lflag3=1;
            }




            if(flag1==1&&flag2==1&&flag3==1&&flag4==1&&lflag1==1&&lflag2==1&&lflag3==1)
            {
                if(xnear1<xnear2)
                    if(ydotselected[points[2]]<ynear1)
                        canvas.drawRect(xnear1+3,ydotselected[points[2]]+3, xnear2-2 ,ynear2-2,box);
                    else
                        canvas.drawRect(xnear1+3,ynear1+3, xnear2-2,ydotselected[points[3]]-2,box);

                else
                    if(ydotselected[points[3]]<ynear2)
                    canvas.drawRect(xnear2+3,ydotselected[points[3]]+3, xnear1-2 ,ynear1-2,box);
                    else
                    canvas.drawRect(xnear2+3,ynear2+3, xnear1-2 ,ydotselected[points[2]]-2,box);
            }

        }

    }
 }


