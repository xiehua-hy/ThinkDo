package com.thinkdo.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.widget.ImageView;

import com.thinkdo.activity.R;
import com.thinkdo.application.MainApplication;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Administrator on 2015/5/26.
 */
public class PushCarArrowThread {
    private ExecutorService executorService;
    private float density;
    private Handler handler;
    private boolean isPlayPlan = true;
    private Bitmap down, up;

    public PushCarArrowThread(Handler handler) {
        this.handler = handler;
        executorService = Executors.newSingleThreadExecutor();
        density = MainApplication.context.getResources().getDisplayMetrics().density;
        down = BitmapFactory.decodeResource(MainApplication.context.getResources(), R.drawable.if_arrow_down);
        up = BitmapFactory.decodeResource(MainApplication.context.getResources(), R.drawable.if_arrow_up);
    }

    public void onPause() {
        isPlayPlan = false;
    }

    public void onPlay() {
        isPlayPlan = true;
    }

    public void loadCirclePic(final ImageView image,final float percent){

        executorService.submit(new Runnable() {
            @Override
            public void run() {
                if(!isPlayPlan)return;
                pushCarArrow(image, percent);
            }
        });
    }

    protected void pushCarArrow(final ImageView image, float percent) {
        float top, bottom;
        Bitmap src;
        if (percent < -1) {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    if (isPlayPlan) image.setImageResource(R.drawable.if_arrow_down_warn);
                }
            });
            return;
        }
        if (percent > 1) {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    if (isPlayPlan) image.setImageResource(R.drawable.if_arrow_up_warn);
                }
            });
            return;
        }

        if (percent > 0) {
            top = 80 * density;
            bottom = (80f + 208f * percent) * density;
            src = down;
        } else {
            top = (288f - (1f + percent) * 208f) * density;
            bottom = 288 * density;
            src = up;
        }

        final Bitmap board = Bitmap.createBitmap((int) (160 * density), (int) (374 * density), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(board);
        Paint paint = new Paint();
        paint.setColor(Color.GRAY);
        canvas.drawBitmap(src, 0, 0, null);
        canvas.drawRect(43 * density, top, 115 * density, bottom, paint);

        handler.post(new Runnable() {
            @Override
            public void run() {
                if (isPlayPlan) image.setImageBitmap(board);

            }
        });
    }

    public boolean isClose() {
        return executorService.isShutdown();
    }

    public void close() {
        if (!isClose()) executorService.shutdown();
    }
}
