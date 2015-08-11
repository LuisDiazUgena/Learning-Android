package com.luisdiaz.gpsspeedwarning;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;


/**
 * Created by luis-diaz on 11/08/15.
 */

public class ArcSpeed extends View{

    public float w,h;
    private int margin = 10;
    private Paint p;
    private RectF rectF;

    public ArcSpeed(Context context, AttributeSet attrs){
        super(context, attrs);

        DisplayMetrics metrics = new DisplayMetrics();
        ((WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getMetrics(metrics);
        float width = metrics.widthPixels;
        float height = metrics.heightPixels;

        w = width;
        h = height/2;
        margin = 10;
        p = new Paint();
        //x,y,width, height
        rectF = new RectF(margin, margin,w, w);

    }

    @Override
    protected void onDraw(Canvas canvas){

        p.setColor(Color.BLACK);
        //canvas.drawRect(margin, margin, w, 200, p);
        Paint p2 = new Paint();
        p.setColor(Color.YELLOW);
        canvas.drawText("150",w,w,p);
    }

    public void setSpeed(Canvas canvas, float speed){
        p.setColor(Color.RED);
        float wSpeed = (speed * w)/150;
        canvas.drawRect(margin,margin,wSpeed,200,p);
    }


}
