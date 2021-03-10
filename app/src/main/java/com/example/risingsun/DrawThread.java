package com.example.risingsun;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.Log;
import android.view.SurfaceHolder;

public class DrawThread extends Thread {

    private SurfaceHolder surfaceHolder;

    Paint p = new Paint();
    Path path = new Path();
    final RectF oval = new RectF();
    Paint backgroundPaint = new Paint();
    private volatile boolean running = true;//флаг для остановки потока

    public DrawThread(Context context, SurfaceHolder surfaceHolder) {
        this.surfaceHolder = surfaceHolder;
        p.setColor(Color.YELLOW);
        backgroundPaint.setColor(Color.BLACK);
        p.setStyle(Paint.Style.FILL);
    }

    public void requestStop() {
        running = false;
    }

    @Override
    public void run() {
        while (running) {
            Log.d("r","runrunrun");
            Canvas canvas = surfaceHolder.lockCanvas();
            if (canvas != null) {
                try {
                    Content.sun.x+=5;
                    canvas.drawColor(Color.BLACK);
                    if(Content.sun.open){
                        path.addCircle(Content.sun.y, Content.sun.x, Content.sun.r, Path.Direction.CCW);

                        oval.set(Content.sun.center_x - Content.sun.r, Content.sun.center_y - Content.sun.r, Content.sun.center_x + Content.sun.r,
                                Content.sun.center_y + Content.sun.r);
                        Content.sun.center_x = Content.sun.x-Content.sun.r;
                        canvas.drawArc(oval, 45, 270, true, p);
                    }
                    else{
                        Content.sun.center_x = Content.sun.x-Content.sun.r;
                        canvas.drawCircle(Content.sun.center_x, Content.sun.center_y, Content.sun.r, p);
                    }
                    Content.sun.col--;
                    if(Content.sun.col==0) {
                        Content.sun.open = !Content.sun.open;
                        Content.sun.col = 5;
                    }
                } finally {
                    surfaceHolder.unlockCanvasAndPost(canvas);
                }
            }
        }
    }
}