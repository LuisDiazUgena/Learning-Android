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

    public ArcSpeed(Context context, AttributeSet attrs){
        super(context, attrs);

        DisplayMetrics metrics = new DisplayMetrics();
        ((WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getMetrics(metrics);
        float width = metrics.widthPixels;
        float height = metrics.heightPixels;

        w = width;
        h = height/2;
    }

    @Override
    protected void onDraw(Canvas canvas){
        Paint p = new Paint();
        //x,y,ancho, alto
        RectF rectF = new RectF(0, 0,w, w);
        p.setColor(Color.BLACK);
        canvas.drawArc (rectF, 0, 200, true, p);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldW,int oldH){
    }



}
