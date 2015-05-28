package com.thinkdo.util;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.Handler;
import android.widget.ImageView;

import com.thinkdo.activity.R;
import com.thinkdo.entity.GloVariable;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Administrator on 2015/5/28.
 */
public class KingpinCircleThread {
    private ExecutorService executorService;
    private float density;
    private Handler handler;
    private Bitmap src;

    public KingpinCircleThread(Handler handler) {
        this.handler = handler;
        this.executorService = Executors.newSingleThreadExecutor();
        this.density = GloVariable.context.getResources().getDisplayMetrics().density;
        src = BitmapFactory.decodeResource(GloVariable.context.getResources(), R.drawable.if_circle_1);

    }

    public void loadCirclePic(final ImageView image, final float percent) {
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                kingpinCircle(image, percent);
            }
        });
    }

    public void kingpinCircle(final ImageView image, float percent) {
        if (percent < -1) percent = -1f;
        if (percent > 1) percent = 1f;

        final Bitmap board = Bitmap.createBitmap((int) (830 * density), (int) (70 * density), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(board);
        canvas.drawBitmap(src, (391 + 380f * percent) * density, 11 * density, null);
        handler.post(new Runnable() {
            @Override
            public void run() {
                image.setImageBitmap(board);
            }
        });
    }

    public boolean isClose() {
        return executorService.isShutdown();
    }

    public void close() {
        if (!executorService.isShutdown()) executorService.shutdownNow();
    }
}
