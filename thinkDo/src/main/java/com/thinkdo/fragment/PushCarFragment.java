package com.thinkdo.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.thinkdo.activity.R;
import com.thinkdo.entity.GloVariable;
import com.thinkdo.net.NetConnect;
import com.thinkdo.util.CommonUtil;
import com.thinkdo.util.MyDialog;
import com.thinkdo.util.PushCarArrowThread;

public class PushCarFragment extends Fragment {
    private PushCarCallback callback;
    private NetConnect socketClient;
    private PushCarArrowThread arrowThread;
    private MyDialog myDialog;
    private boolean transFlag = false;
    private int direFlag = 0;
    private ImageView iv;
    private TextView tv;

    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            if (!transFlag) return true;
            String reply = msg.getData().getString(GloVariable.head);
            if (reply == null) return true;

            int backCode = CommonUtil.getQuestCode(reply);
            int statusCode = CommonUtil.getStatusCode(reply);
            if (backCode == GloVariable.errorUrl) {
                if (statusCode == GloVariable.erroDiss) {
                    myDialog.dismiss();
                } else {
                    myDialog.show(CommonUtil.getErrorString(statusCode, reply));
                }
            } else if (backCode != GloVariable.pushcarUrl && callback != null) {
                callback.pushCarNext(backCode);
            } else {
                switch (statusCode) {
                    case 0:
                        tv.setText(R.string.pushCar_tip_init);
                        break;
                    case 1:
                        tv.setText(R.string.pushCar_tip_testing);
                        break;
                    case 2:
                        tv.setText(R.string.pushCar_tip_backward);
                        if (direFlag != 1) {
                            iv.setImageResource(R.drawable.if_arrow_down);
                            direFlag = 1;
                        }
                        arrowThread.onPlay();
                        break;
                    case 3:
                        tv.setText(R.string.pushCar_tip_forward);
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
                        if (callback != null) callback.pushCarNext(GloVariable.testDataUrl);
                        break;
                    case 9:
                        Float data = getPushcarData(reply);
                        if (data != null && data != 0) {
                            arrowThread.loadCirclePic(iv, data);
                        }
                        break;
                    case 10:
                        arrowThread.onPause();
                        tv.setText(R.string.pushCar_tip_stop);
                        iv.setImageResource(R.drawable.if_stop);
                        break;
                }
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
        socketClient = new NetConnect(handler, GloVariable.pushcarUrl);
    }

    @Override
    public void onPause() {
        super.onPause();
        socketClient.close();
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
