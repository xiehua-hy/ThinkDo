package com.thinkdo.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.thinkdo.activity.R;
import com.thinkdo.application.MainApplication;
import com.thinkdo.net.NetConnect;
import com.thinkdo.net.NetSingleConnect;
import com.thinkdo.net.SocketClient;
import com.thinkdo.util.CommonUtil;
import com.thinkdo.util.MyDialog;
import com.thinkdo.util.PushCarArrowThread;

public class PushCarFragment extends Fragment {
    private PushCarCallback callback;
    private NetConnect socketClient;
    private NetSingleConnect loginConnect;
    private PushCarArrowThread arrowThread;
    private MyDialog myDialog;
    private boolean transFlag = false;
    private int direFlag = 0;
    private ImageView iv;
    private TextView tv;
    private ToneGenerator toneGenerator = new ToneGenerator(AudioManager.STREAM_SYSTEM,
            ToneGenerator.MAX_VOLUME);

    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            if (!transFlag) return true;
            String reply = msg.getData().getString(MainApplication.head);
            if (reply == null) return true;

            int backCode = CommonUtil.getQuestCode(reply);
            int statusCode = CommonUtil.getStatusCode(reply);

            if (backCode == MainApplication.pushcarUrl) {
                if (myDialog.isShow()) myDialog.dismiss();

                switch (statusCode) {
                    case 0:
                        tv.setText(R.string.pushCar_tip_init);
                        break;
                    case 1:
                        tv.setText(R.string.pushCar_tip_testing);
                        break;
                    case 2:
                        //向后推车
                        tv.setText(R.string.pushCar_tip_backward);
                        toneGenerator.startTone(ToneGenerator.TONE_CDMA_DIAL_TONE_LITE, 500);
                        if (direFlag != 1) {
                            iv.setImageResource(R.drawable.if_arrow_down);
                            direFlag = 1;
                        }
                        arrowThread.onPlay();
                        break;
                    case 3:
                        //向前推车
                        tv.setText(R.string.pushCar_tip_forward);
                        toneGenerator.startTone(ToneGenerator.TONE_CDMA_INTERCEPT, 500);
                        if (direFlag != -1) {
                            iv.setImageResource(R.drawable.if_arrow_up);
                            direFlag = -1;
                        }
                        arrowThread.onPlay();
                        break;
                    case 4:
                        arrowThread.onPause();
                        tv.setText(R.string.pushCar_tip_stop);
                        iv.setImageResource(R.drawable.if_stop);
                        break;
                    case 5:
                        tv.setText(R.string.pushCar_tip_data_handle);
                        iv.setImageResource(R.drawable.if_stop);
                        break;
                    case 6:
                        if (callback != null) callback.pushCarNext(MainApplication.testDataUrl);
                        break;
                    case 9:
                        Float data = getPushcarData(reply);
                        if (data != null && data != 0) {

                            if (data > 1 || data < -1) {
                                tv.setText("");
                                direFlag = 0;
                            } else if (data > 0 && direFlag != 1) {
                                tv.setText(R.string.pushCar_tip_backward);
                                direFlag = 1;
                            } else if (data < 0 && direFlag != -1) {
                                tv.setText(R.string.pushCar_tip_forward);
                                direFlag = -1;
                            }
                            arrowThread.loadCirclePic(iv, data);
                        }
                        if (data == 0) tv.setText("");
                        break;
                    case 10:
                        arrowThread.onPause();
                        tv.setText(R.string.pushCar_tip_stop);
                        iv.setImageResource(R.drawable.if_stop);

                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                for (int i = 0; i < 3; i++) {
                                    toneGenerator.startTone(ToneGenerator.TONE_CDMA_DIAL_TONE_LITE, 500);
                                    try {
                                        Thread.sleep(1000);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        }).start();
                        break;
                }
            } else if (backCode == MainApplication.errorUrl && statusCode != MainApplication.erroDiss) {
                myDialog.show(CommonUtil.getErrorString(statusCode, reply));
            } else if (backCode == MainApplication.loginUrl) {
                String[] data = SocketClient.parseData(reply);
                if (data != null && data.length == 2) {
                    int i;
                    try {
                        i = Integer.parseInt(data[1]);
                    } catch (NumberFormatException e) {
                        i = 0;
                    }
                    MainApplication.device = data[0];
                    MainApplication.availableDay = i;
                    MainApplication.loginFlag = true;
                    startConnect();
                }
            } else if (callback != null) {
                callback.pushCarNext(backCode);
            }
            return true;
        }
    });

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_pushcar, container, false);
        tv = (TextView) rootView.findViewById(R.id.tv_notice);
        iv = (ImageView) rootView.findViewById(R.id.imageView);
        return rootView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myDialog = new MyDialog(getActivity());
        arrowThread = new PushCarArrowThread(handler);
    }


    @Override
    public void onResume() {
        super.onResume();
        transFlag = true;
        startConnect();
    }

    public void startConnect() {
        if (MainApplication.availableDay > 0) {
            socketClient = new NetConnect(handler, MainApplication.pushcarUrl);
        } else if (!MainApplication.loginFlag) {
            loginConnect = new NetSingleConnect(handler, MainApplication.loginUrl);
        } else {
            Toast.makeText(MainApplication.context, R.string.tip_recharge, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (loginConnect != null) loginConnect.close();
        if (socketClient != null) socketClient.close();
        transFlag = false;
        myDialog.dismiss();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        arrowThread.close();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            callback = (PushCarCallback) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException("Activity must implement PushCarCallback.");
        }
    }

    public interface PushCarCallback {
        void pushCarNext(int position);
    }

    private Float getPushcarData(String msg) {
        if (msg.contains("&&")) {
            String[] arr = msg.split("&&");
            String[] array = arr[1].split("\\|");
            try {
                return Float.parseFloat(array[0]);
            } catch (NumberFormatException e) {
                return null;
            }
        }
        return null;
    }

}
