package com.thinkdo.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.widget.LinearLayout;

import com.thinkdo.activity.R;
import com.thinkdo.application.MainApplication;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Administrator on 2015/5/26.
 */
public class DataCircleLoadThread {
    private ExecutorService executorService;
    private float density;
    private Handler handler;

    public DataCircleLoadThread(Handler handler, int num) {
        this.handler = handler;
        this.executorService = Executors.newFixedThreadPool(num);
        this.density = MainApplication.context.getResources().getDisplayMetrics().density;

    }

    public void loadCirclePic(final LinearLayout linear, final float percent) {
        if (percent > -1) {

            executorService.submit(new Runnable() {
                @Override
                public void run() {
                    Bitmap original = BitmapFactory.decodeResource(MainApplication.context.getResources(), R.drawable.ib_data);
                    Bitmap circle = BitmapFactory.decodeResource(MainApplication.context.getResources(), R.drawable.ib_circle);
                    Bitmap bg = Bitmap.createBitmap(original.getWidth(), original.getHeight(), Bitmap.Config.ARGB_8888);
                    Canvas canvas = new Canvas(bg);
                    canvas.drawBitmap(original, 0, 0, null);
                    canvas.drawBitmap(circle, (115 * percent + 25) * density, 48 * density, null);
                    final Drawable drawable = new BitmapDrawable(MainApplication.context.getResources(), bg);
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            linear.setBackgroundDrawable(drawable);

                        }
                    });
                }
            });
        }
    }

    public boolean isClose() {
        return executorService.isShutdown();
    }

    public void close() {
        if (!isClose()) {
            executorService.shutdownNow();
        }
    }

}
