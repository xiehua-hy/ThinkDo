package com.thinkdo.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.thinkdo.activity.R;

public class PushCarFragment extends Fragment {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_pushcar, container, false);
        return rootView;
    }


//    /**
//     *
//     * */
//    class ArrowHandleCallback implements Handler.Callback {
//        private Bitmap down, up, src;
//        private float density;
//        private Handler handler;
//        private boolean isPlay = true;
//
//        public ArrowHandleCallback(Handler handler) {
//            this.handler = handler;
//            density = GloVariable.context.getResources().getDisplayMetrics().density;
//            down = BitmapFactory.decodeResource(getResources(), R.drawable.if_arrow_down);
//            up = BitmapFactory.decodeResource(getResources(), R.drawable.if_arrow_up);
//
//        }
//
//        @Override
//        public boolean handleMessage(Message msg) {
//            switch (msg.what) {
//                case -3:
//                    onPause();
//                    return true;
//                case -2:
//                    onPlay();
//                    return true;
//
//                case -1:
//                    //后面触底
//                    if (handler != null && isPlay) handler.sendEmptyMessage(-1);
//                    return true;
//
//                case 1:
//                    //向前触底
//                    if (handler != null && isPlay) handler.sendEmptyMessage(1);
//                    return true;
//
//                case 0:
//                    float percent = msg.arg1 / 1000f;
//                    float top, bottom;
//                    if (percent > 0) {
//                        top = 80 * density;
//                        bottom = (80f + 208f * percent) * density;
//                        src = down;
//                    } else {
//                        top = (288f - (1f + percent) * 208f) * density;
//                        bottom = 288 * density;
//                        src = up;
//                    }
//
//                    Bitmap board = Bitmap.createBitmap((int) (160 * density), (int) (374 * density), Bitmap.Config.ARGB_8888);
//                    Canvas canvas = new Canvas(board);
//                    Paint paint = new Paint();
//                    paint.setColor(Color.GRAY);
//                    canvas.drawBitmap(src, 0, 0, null);
//                    canvas.drawRect(43 * density, top, 115 * density, bottom, paint);
//                    if (handler != null && isPlay) {
//                        Message message = handler.obtainMessage();
//
//                    }
//                    return true;
//            }
//            return false;
//        }
//
//        private void onPause() {
//            isPlay = false;
//        }
//
//        private void onPlay() {
//            isPlay = true;
//        }
//    }
}
