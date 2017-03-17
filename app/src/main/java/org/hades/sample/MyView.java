package org.hades.sample;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by Hades on 17/3/17.
 */

public class MyView extends SurfaceView implements SurfaceHolder.Callback {

    MainActivity activity;
    MyViewDrawThread mvdt; //绘画线程
    Paint paint;
    float dx;
    float dy;
    float dz;
    float x;
    float y;
    float rx;
    float ry;
    float juli2;
    float juli;

    public MyView(Context context) {
        super(context);
        activity = (MainActivity) context;
        getHolder().addCallback(this);
        paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setTextSize(80);
        paint.setAntiAlias(true);
        mvdt = new MyViewDrawThread(this);
    }



    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        canvas.drawBitmap(activity.shang, 105, 0, paint); // 画上面的管子
        canvas.drawBitmap(activity.yuan, 400, 150, paint); // 画中间的圆形
        canvas.drawBitmap(activity.zuo, 0, 0, paint); // 画左边的管子

        x = dx*34;          //对x轴上的位移赋值
        x=dx*34;
        if(x>200)x=200;
        if(x<-200)x=-200;
        canvas.drawBitmap(activity.qiuzuo, 10, 300+x,paint);
        y=dy*34;
        if(y>550)y=550;
        if(y<-550)y=-550;
        canvas.drawBitmap(activity.qiushang, 610 + y, 3, paint); // 画上边的水滴

        juli = (float) Math.sqrt((dx * 34) * (dx * 43) + (dy * 34) * (dy * 34)); // 求到原点的距离
        juli2 = juli / 170;
        if (juli2 <= 1) { //如果小于1 直接赋值
            rx = (dx * 34) / 170;
            ry = (dy * 34) / 170;
        } else {
            if (dy > 0) {
                rx = (float) Math.sqrt(2 * dy * dy / (dx * dx + dy * dy));
            } else {
                rx = -(float) Math.sqrt(2 * dy * dy / (dx * dx + dy * dy));
            }
            ry = dx / dy * rx;
        }
        canvas.drawBitmap(activity.qiuzhong, 630+rx*110, 380+ry*110,paint);

        Log.e("TEST", "onDraw");
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        //启动线程
       mvdt.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {

    }
}
