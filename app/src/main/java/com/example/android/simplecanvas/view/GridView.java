package com.example.android.simplecanvas.view;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;

import com.example.android.simplecanvas.R;
import com.example.android.simplecanvas.gameActivity;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import static android.graphics.Bitmap.createBitmap;
import static com.example.android.simplecanvas.gameActivity.Player1;
import static com.example.android.simplecanvas.gameActivity.Player2;
import static com.example.android.simplecanvas.gameActivity.Player3;
import static com.example.android.simplecanvas.gameActivity.Player4;
import static com.example.android.simplecanvas.gameActivity.Turncolour;

import static com.example.android.simplecanvas.gameActivity.blink;
import static com.example.android.simplecanvas.gameActivity.intscorep1;
import static com.example.android.simplecanvas.gameActivity.intscorep2;
import static com.example.android.simplecanvas.gameActivity.intscorep3;
import static com.example.android.simplecanvas.gameActivity.intscorep4;
import static com.example.android.simplecanvas.gameActivity.namep1;
import static com.example.android.simplecanvas.gameActivity.namep2;
import static com.example.android.simplecanvas.gameActivity.namep3;
import static com.example.android.simplecanvas.gameActivity.namep4;
import static com.example.android.simplecanvas.gameActivity.numberofplayers;
import static com.example.android.simplecanvas.gameActivity.p;
import static com.example.android.simplecanvas.gameActivity.plno;
import static com.example.android.simplecanvas.gameActivity.scorep1;
import static com.example.android.simplecanvas.gameActivity.scorep2;
import static com.example.android.simplecanvas.gameActivity.scorep3;
import static com.example.android.simplecanvas.gameActivity.scorep4;
import static com.example.android.simplecanvas.gameActivity.undo;
import static com.example.android.simplecanvas.gameActivity.winnerdialog;

public class GridView extends View {

    Paint dot;
    public Paint line;
    public Paint box;
    int rows= gameActivity.Y;
    int columns=gameActivity.X;
    double[][] xcoord = new double[rows+1][columns+1];
    double[][] ycoord = new double[rows+1][columns+1];
    int xnear1;
    int ynear1;
    int xnear2;
    int ynear2;
    Bitmap myBitmap;
    Bitmap backupBitmap;
    Canvas canvas;
    Canvas backupBitmapCanvas;

    int[] xdotselected= new int[(((rows-1)*columns)+((columns-1)*rows))*2];
    int[] ydotselected= new int[(((rows-1)*columns)+((columns-1)*rows))*2];
    public int k=0;
    double columnwidth;
    double rowwidth;
    Path path;
    ArrayList<Drawaction> actionslist = new ArrayList<>();
    int l=0,bl=0,br=0,bt=0,bb=0;


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
        dot.setColor(Color.WHITE);
        line= new Paint();
        line.setColor(resources.getColor(R.color.Red));
        line.setStrokeWidth(7);
        box= new Paint();
        box.setColor(resources.getColor(R.color.Red));
        box.setStyle(Paint.Style.FILL);
        path = new Path();



    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        myBitmap= createBitmap(w,h,Bitmap.Config.ARGB_8888);
        backupBitmap= createBitmap(w,h,Bitmap.Config.ARGB_8888);
        canvas=new Canvas(myBitmap);
        backupBitmapCanvas= new Canvas(backupBitmap);
        rowwidth= (double) h / (columns+1);
        columnwidth= (double) w/(rows+1);

    }

    @RequiresApi(api = Build.VERSION_CODES.M)



 // Grid of Dots
    @Override
    protected void onDraw(Canvas canvas)
    {
//
//        for(Drawaction actionToDraw : actionslist ){
//            canvas.drawPath(actionToDraw.path,actionToDraw.paint);
//        }
          canvas.drawBitmap(myBitmap,0,0,null);
        for(int i=1; i<= rows; i++)
        {
            for(int j=1; j<=columns; j++) {
                canvas.drawCircle(i * (float)columnwidth,  j * (float)rowwidth, 9, dot);
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

        int flag1=0;
        super.onTouchEvent(event);
        performClick();
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
                        undo.setEnabled(true);

                    }

                }
            }


                if(((Math.abs(xcoord[xnear1][ynear1]-xcoord[xnear2][ynear2]))<=columnwidth+0.00005&&(Math.abs(xcoord[xnear1][ynear1]-xcoord[xnear2][ynear2]))>=columnwidth-0.00005)||((Math.abs(ycoord[xnear1][ynear1]-ycoord[xnear2][ynear2]))<=rowwidth+0.00005&&(Math.abs(ycoord[xnear1][ynear1]-ycoord[xnear2][ynear2]))>=rowwidth-0.00005)) {
                    if ((xnear1 == xnear2) || (ynear1 == ynear2)) {
                        for(int a=0;a<k;a=a+2) {

                            if((xdotselected[a]==xnear1&&ydotselected[a]==ynear1&&xdotselected[a+1]==xnear2&&ydotselected[a+1]==ynear2)||(xdotselected[a+1]==xnear1&&ydotselected[a+1]==ynear1&&xdotselected[a]==xnear2&&ydotselected[a]==ynear2))
                            {
                                flag1=1;
                                break;
                            }
                        }
                        if(xnear1==xnear2&&ynear1==ynear2)
                        {
                            flag1=1;
                        }
                        if(flag1==0) {
                            xdotselected[k] = xnear1;
                            ydotselected[k] = ynear1;
                            k++;
                            xdotselected[k] = xnear2;
                            ydotselected[k] = ynear2;
                            k++;

                            boxundo();
                            if(l==2) {
                                if(plno==0){
                                    plno=gameActivity.numberofplayers-1;
                                    p[gameActivity.numberofplayers-1]=1;
                                    p[0]=0;
                                }
                                else{
                                    p[plno]=0;
                                    plno--;
                                    p[plno]=1;
                                }
                                linecolour();
                                backupBitmapCanvas.drawLine((float) xcoord[xdotselected[k - 4]][ydotselected[k - 4]], (float) ycoord[xdotselected[k - 4]][ydotselected[k - 4]], (float) xcoord[xdotselected[k - 3]][ydotselected[k - 3]], (float) ycoord[xdotselected[k - 3]][ydotselected[k - 3]], line);
                                if(plno==gameActivity.numberofplayers-1){
                                    plno=0;
                                    p[gameActivity.numberofplayers-1]=0;
                                    p[0]=1;
                                }
                                else{
                                    p[plno]=0;
                                    plno++;
                                    p[plno]=1;
                                }
                                linecolour();
                            }
                            else {
                                l ++;
                            }

                            boxcolour();
                            checkboxformed(xnear1,xnear2,ynear1,ynear2);


                            canvas.drawLine((float)xcoord[xnear1][ynear1],(float) ycoord[xnear1][ynear1], (float)xcoord[xnear2][ynear2],(float)ycoord[xnear2][ynear2], line);
                            gameActivity.clicksound.start();
                        if(plno==gameActivity.numberofplayers-1){
                            plno=0;
                            p[gameActivity.numberofplayers-1]=0;
                            p[0]=1;
                        }
                        else{
                            p[plno]=0;
                            plno++;
                            p[plno]=1;
                        }
                        linecolour();
                    }
                }
                invalidate();

            }
            if(k==((((rows-1)*columns)+((columns-1)*rows))*2))
            {
                showpopup();
            }

        }

        return true;
    }



    public void undo(){
        myBitmap=createBitmap(backupBitmap);
//        myBitmap= backupBitmap.copy(Bitmap.Config.ARGB_8888,true);
        canvas= new Canvas(myBitmap);
        k--;
        xdotselected[k] = 0;
        ydotselected[k] = 0;
        k--;
        xdotselected[k] = 0;
        ydotselected[k] = 0;
        if(plno==0){
            plno=gameActivity.numberofplayers-1;
            p[gameActivity.numberofplayers-1]=1;
            p[0]=0;
        }
        else{
            p[plno]=0;
            plno--;
            p[plno]=1;
        }
        linecolour();
        invalidate();
        undo.setEnabled(false);
    }

    public void boxundo(){

        if(bl==1) {

            if(ydotselected[k - 4]<ydotselected[k - 3]) {
                backupBitmapCanvas.drawRect((float) xcoord[ xdotselected[k - 4]- 1][ydotselected[k - 4]], (float) ycoord[xdotselected[k - 4] - 1][ydotselected[k - 4]], (float) xcoord[xdotselected[k - 3]][ydotselected[k - 3]] , (float) ycoord[xdotselected[k - 3]][ydotselected[k - 3]], box);
            }
            else {
                backupBitmapCanvas.drawRect((float) xcoord[ xdotselected[k - 4]- 1][ydotselected[k - 4]], (float) ycoord[xdotselected[k - 4] - 1][ydotselected[k - 4]], (float) xcoord[xdotselected[k - 3]][ydotselected[k - 3]] , (float) ycoord[xdotselected[k - 3]][ydotselected[k - 3]], box);

            }
            backupBitmapCanvas.drawLine((float)xcoord[xdotselected[k - 4]][ydotselected[k - 4]],(float) ycoord[xdotselected[k - 4]][ydotselected[k - 4]], (float)xcoord[xdotselected[k - 4]-1][ydotselected[k - 4]],(float)ycoord[xdotselected[k - 4]-1][ydotselected[k - 4]], line);
            backupBitmapCanvas.drawLine((float)xcoord[xdotselected[k - 3]][ydotselected[k - 3]],(float) ycoord[xdotselected[k - 3]][ydotselected[k - 3]], (float)xcoord[xdotselected[k - 3]-1][ydotselected[k - 3]],(float)ycoord[xdotselected[k - 3]-1][ydotselected[k - 3]], line);
            backupBitmapCanvas.drawLine((float)xcoord[xdotselected[k - 4]-1][ydotselected[k - 4]],(float) ycoord[xdotselected[k - 4]-1][ydotselected[k - 4]], (float)xcoord[xdotselected[k - 3]-1][ydotselected[k - 3]],(float)ycoord[xdotselected[k - 3]-1][ydotselected[k - 3]], line);
            bl=0;
        }
        else if(br==1){

        }
        else if(bb==1){

        }
        else if(bt==1){

        }
    }
    public void showpopup(){

        int samescore=0;
        Button closepopup;
        TextView winnername;
        winnerdialog.setContentView(R.layout.winnerpopup);
        closepopup = winnerdialog.findViewById(R.id.closepopup);
        winnername = winnerdialog.findViewById(R.id.winnername);
        Resources resources =getResources();
        gameActivity.winner.start();


        if (numberofplayers==2) {
            if (intscorep1 == intscorep2) {
                winnername.setTextColor(Color.WHITE);
                winnername.setText("No One");
                closepopup.setBackgroundColor(Color.WHITE);
                samescore = 1;
            }
        }
        else if (numberofplayers==3) {
            if (intscorep1 == intscorep2 && intscorep2 == intscorep3) {
                winnername.setTextColor(Color.WHITE);
                winnername.setText("No One");
                closepopup.setBackgroundColor(Color.WHITE);
                samescore = 1;
            }
        }
        else if (numberofplayers==4) {
            if (intscorep1 == intscorep2 && intscorep2 == intscorep3 && intscorep3 == intscorep4) {
                winnername.setTextColor(Color.WHITE);
                winnername.setText("No One");
                closepopup.setBackgroundColor(Color.WHITE);
                samescore = 1;
            }
        }
        if(samescore==0){
            if(intscorep1>intscorep2)
            {
                if(intscorep3>intscorep4)
                {
                    if(intscorep1>intscorep3){
                        winnername.setTextColor(resources.getColor(R.color.Blue));
                        winnername.setText(Player1);
                        closepopup.setBackgroundColor(resources.getColor(R.color.Blue));
                    }
                    else{
                        winnername.setTextColor(resources.getColor(R.color.Red));
                        winnername.setText(Player3);
                        closepopup.setBackgroundColor(resources.getColor(R.color.Red));
                    }
                }
                else{
                    if(intscorep1>intscorep4){
                        winnername.setTextColor(resources.getColor(R.color.Blue));
                        winnername.setText(Player1);
                        closepopup.setBackgroundColor(resources.getColor(R.color.Blue));

                    }
                    else{
                        winnername.setTextColor(resources.getColor(R.color.Lightblue));
                        winnername.setText(Player4);
                        closepopup.setBackgroundColor(resources.getColor(R.color.Lightblue));
                    }
                }
            }
            else{
                if(intscorep3>intscorep4)
                {
                    if(intscorep2>intscorep3){
                        winnername.setTextColor(resources.getColor(R.color.Purple));
                        winnername.setText(Player2);
                        closepopup.setBackgroundColor(resources.getColor(R.color.Purple));
                    }
                    else{
                        winnername.setTextColor(resources.getColor(R.color.Red));
                        winnername.setText(Player3);
                        closepopup.setBackgroundColor(resources.getColor(R.color.Red));
                    }
                }
                else{
                    if(intscorep2>intscorep4){
                        winnername.setTextColor(resources.getColor(R.color.Purple));
                        winnername.setText(Player2);
                        closepopup.setBackgroundColor(resources.getColor(R.color.Purple));
                    }
                    else{
                        winnername.setTextColor(resources.getColor(R.color.Lightblue));
                        winnername.setText(Player4);
                        closepopup.setBackgroundColor(resources.getColor(R.color.Lightblue));
                    }
                }
            }
        }

        winnerdialog.show();
        closepopup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                winnerdialog.dismiss();
            }
        });
        winnerdialog.setCanceledOnTouchOutside(false);
        winnerdialog.setCancelable(false);

    }

    public void linecolour(){
        Resources resources =getResources();

        if(p[0]==1)
        {
            Turncolour.setBackgroundColor(resources.getColor(R.color.Blue));
            line.setColor(resources.getColor(R.color.Blue));
        }
        else if(p[1]==1)
        {
            Turncolour.setBackgroundColor(resources.getColor(R.color.Purple));
            line.setColor(resources.getColor(R.color.Purple));
        }
        else if(p[2]==1)
        {
            Turncolour.setBackgroundColor(resources.getColor(R.color.Red));
            line.setColor(resources.getColor(R.color.Red));
        }
        else if(p[3]==1)
        {
            Turncolour.setBackgroundColor(resources.getColor(R.color.Lightblue));
            line.setColor(resources.getColor(R.color.Lightblue));
        }

    }

    void boxcolour()
    {
        Resources resources =getResources();
        if(p[0]==1)
        {
            box.setColor(resources.getColor(R.color.Blue));
        }
        else if(p[1]==1)
        {
            box.setColor(resources.getColor(R.color.Purple));
        }
        else if(p[2]==1)
        {
            box.setColor(resources.getColor(R.color.Red));
        }
        else if(p[3]==1)
        {
            box.setColor(resources.getColor(R.color.Lightblue));
        }

    }

    void points(){
        if(p[0]==1)
        {
            intscorep1++;
           scorep1.setText(""+intscorep1);
            namep1.startAnimation(blink);
            scorep1.startAnimation(blink);
        }
        else if(p[1]==1)
        {
            intscorep2++;
            scorep2.setText(""+intscorep2);
            namep2.startAnimation(blink);
            scorep2.startAnimation(blink);
        }
        else if(p[2]==1)
        {
            intscorep3++;
            scorep3.setText(""+intscorep3);
            namep3.startAnimation(blink);
            scorep3.startAnimation(blink);
        }
        else if(p[3]==1)
        {
            intscorep4++;
            scorep4.setText(""+intscorep4);
            namep4.startAnimation(blink);
            scorep4.startAnimation(blink);
        }
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
                    points();
                    bl=1;
                }
                else {
                    canvas.drawRect((float) xcoord[xnear2 - 1][ynear2], (float) ycoord[xnear2 - 1][ynear2] , (float) xcoord[xnear1][ynear1] , (float) ycoord[xnear1][ynear1] , box);
                    points();
                    bl=1;
                }
                canvas.drawLine((float)xcoord[xnear1][ynear1],(float) ycoord[xnear1][ynear1], (float)xcoord[xnear1-1][ynear1],(float)ycoord[xnear1-1][ynear1], line);
                canvas.drawLine((float)xcoord[xnear2][ynear2],(float) ycoord[xnear2][ynear2], (float)xcoord[xnear2-1][ynear2],(float)ycoord[xnear2-1][ynear2], line);
                canvas.drawLine((float)xcoord[xnear1-1][ynear1],(float) ycoord[xnear1-1][ynear1], (float)xcoord[xnear2-1][ynear2],(float)ycoord[xnear2-1][ynear2], line);
                if(plno==0){
                    plno=gameActivity.numberofplayers-1;
                    p[gameActivity.numberofplayers-1]=1;
                    p[0]=0;
                }
                else{
                    p[plno]=0;
                    plno--;
                    p[plno]=1;
                }
                gameActivity.boxformed.start();
            }

            //Right box
            if((flag4==1)&&(flag5==1)&&(flag6==1)){
                if(ynear1<ynear2){
                    canvas.drawRect((float) xcoord[xnear1][ynear1], (float) ycoord[xnear1][ynear1], (float) xcoord[xnear2+1][ynear2] , (float) ycoord[xnear2+1][ynear2], box);
                    points();
                    br=1;
                }
                else {
                    canvas.drawRect((float) xcoord[xnear2][ynear2], (float) ycoord[xnear2][ynear2], (float) xcoord[xnear1 + 1][ynear1] , (float) ycoord[xnear1 + 1][ynear1] , box);
                    points();
                    br=1;
                }
                canvas.drawLine((float)xcoord[xnear1][ynear1],(float) ycoord[xnear1][ynear1], (float)xcoord[xnear1+1][ynear1],(float)ycoord[xnear1+1][ynear1], line);
                canvas.drawLine((float)xcoord[xnear2][ynear2],(float) ycoord[xnear2][ynear2], (float)xcoord[xnear2+1][ynear2],(float)ycoord[xnear2+1][ynear2], line);
                canvas.drawLine((float)xcoord[xnear1+1][ynear1],(float) ycoord[xnear1+1][ynear1], (float)xcoord[xnear2+1][ynear2],(float)ycoord[xnear2+1][ynear2], line);
                if(plno==0){
                    plno=gameActivity.numberofplayers-1;
                    p[gameActivity.numberofplayers-1]=1;
                    p[0]=0;
                }
                else{
                    p[plno]=0;
                    plno--;
                    p[plno]=1;
                }
                gameActivity.boxformed.start();
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
                    points();
                    bt=1;
                }
                else{
                    canvas.drawRect((float) xcoord[xnear2][ynear2-1], (float) ycoord[xnear2][ynear2-1], (float) xcoord[xnear1][ynear1] , (float) ycoord[xnear1][ynear1], box);
                    points();
                    bt=1;
                }
                canvas.drawLine((float)xcoord[xnear1][ynear1],(float) ycoord[xnear1][ynear1], (float)xcoord[xnear1][ynear1-1],(float)ycoord[xnear1][ynear1-1], line);
                canvas.drawLine((float)xcoord[xnear2][ynear2],(float) ycoord[xnear2][ynear2], (float)xcoord[xnear2][ynear2-1],(float)ycoord[xnear2][ynear2-1], line);
                canvas.drawLine((float)xcoord[xnear1][ynear1-1],(float) ycoord[xnear1][ynear1-1], (float)xcoord[xnear2][ynear2-1],(float)ycoord[xnear2][ynear2-1], line);
                if(plno==0){
                    plno=gameActivity.numberofplayers-1;
                    p[gameActivity.numberofplayers-1]=1;
                    p[0]=0;
                }
                else{
                    p[plno]=0;
                    plno--;
                    p[plno]=1;
                }
                gameActivity.boxformed.start();
            }
            //Bottom box
            if((flag4==1)&&(flag5==1)&&(flag6==1)){
                if(xnear1<xnear2){
                    canvas.drawRect((float) xcoord[xnear1][ynear1], (float) ycoord[xnear1][ynear1], (float) xcoord[xnear2][ynear2+1] , (float) ycoord[xnear2][ynear2+1], box);
                    points();
                    bb=1;
                }
                else {
                    canvas.drawRect((float) xcoord[xnear2][ynear2], (float) ycoord[xnear2][ynear2], (float) xcoord[xnear1][ynear1 + 1], (float) ycoord[xnear1][ynear1 + 1], box);
                    points();
                    bb=1;
                }
                canvas.drawLine((float)xcoord[xnear1][ynear1],(float) ycoord[xnear1][ynear1], (float)xcoord[xnear1][ynear1+1],(float)ycoord[xnear1][ynear1+1], line);
                canvas.drawLine((float)xcoord[xnear2][ynear2],(float) ycoord[xnear2][ynear2], (float)xcoord[xnear2][ynear2+1],(float)ycoord[xnear2][ynear2+1], line);
                canvas.drawLine((float)xcoord[xnear1][ynear1+1],(float) ycoord[xnear1][ynear1+1], (float)xcoord[xnear2][ynear2+1],(float)ycoord[xnear2][ynear2+1], line);
                if(plno==0){
                    plno=gameActivity.numberofplayers-1;
                    p[gameActivity.numberofplayers-1]=1;
                    p[0]=0;
                }
                else{
                    p[plno]=0;
                    plno--;
                    p[plno]=1;
                }
                gameActivity.boxformed.start();
            }


        }

    }
 }


