package org.hades.sample;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

/**
 * Created by Hades on 17/3/17.
 */
public class MyViewDrawThread extends Thread{

    boolean flag = true;
    boolean pauseFlag;
    MyView mv;
    SurfaceHolder surfaceHolder;

    public MyViewDrawThread(MyView mv) {
        this.mv = mv;
        surfaceHolder = mv.getHolder();
    }

    @Override
    public void run() {
        Canvas c;
        while (flag) {
            c = null;
            if (!pauseFlag) {
                try {
                    c = this.surfaceHolder.lockCanvas();
                    synchronized (surfaceHolder) {
                        mv.draw(c);
                    }
                }finally {
                    if (c != null) {
                        this.surfaceHolder.unlockCanvasAndPost(c);
                    }
                    try {
                        Thread.sleep(50);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
