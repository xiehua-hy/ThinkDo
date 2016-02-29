package com.thinkdo.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.thinkdo.activity.R;
import com.thinkdo.application.MainApplication;
import com.thinkdo.net.NetConnect;
import com.thinkdo.net.NetSingleConnect;
import com.thinkdo.net.SocketClient;
import com.thinkdo.util.CommonUtil;
import com.thinkdo.util.KingpinCircleThread;
import com.thinkdo.util.MyDialog;

public class KingpinFragment extends Fragment {
    private KinPingCallback callback;
    private int wheelStatus = 0;// 0��״̬��1��ת,2��ת
    private NetConnect socketClient;
    private MyDialog myDialog;
    private boolean transFlag = false;
    private KingpinCircleThread kingpinCircleThread;
    private NetSingleConnect loginConnect;
    private TextView tv;
    private ImageView iv_wheel, iv_circle;

    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            if (!transFlag) return true;
            String reply = msg.getData().getString(MainApplication.head);
            if (reply == null) return true;
            int backCode = CommonUtil.getQuestCode(reply);
            int statusCode = CommonUtil.getStatusCode(reply);

            if (backCode == MainApplication.loginUrl) {
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

            } else if (backCode == MainApplication.errorUrl) {
                if (statusCode == MainApplication.erroDiss) {
                    myDialog.dismiss();
                } else {
                    myDialog.show(CommonUtil.getErrorString(statusCode, reply));
                }
            } else if (backCode != MainApplication.kingpinUrl && callback != null) {
                callback.kinPingNext(backCode);
            } else {
                switch (statusCode) {
                    case 2:
                        wheelStatus = 2;
                        iv_wheel.setImageResource(R.drawable.if_steering_wheel_right);
                        break;

                    case 3:
                        wheelStatus = 1;
                        iv_wheel.setImageResource(R.drawable.if_steering_wheel_left);
                        break;

                    case 4:
                        if (wheelStatus == 1)
                            iv_wheel.setImageResource(R.drawable.if_steering_wheel_right);
                        else if (wheelStatus == 2)
                            iv_wheel.setImageResource(R.drawable.if_steering_wheel_left);
                        else iv_wheel.setImageResource(R.drawable.if_steering_wheel);
                        break;

                    case 5:
                        iv_wheel.setImageResource(R.drawable.if_steering_wheel);
                        iv_circle.setImageResource(R.drawable.ib_kingpin_center);
                        break;

                    case 6:
                        if (callback != null) callback.kinPingNext(MainApplication.testDataUrl);
                        break;

                    case 7:
                        Float[] data = getKingpinData(reply);

                        if (data[0] != null && data[2] != null) {
                            tv.setText(String.format("%s°", CommonUtil.format(data[1], 2)));
                            kingpinCircleThread.loadCirclePic(iv_circle, data[0]);

                            float direct = data[2];
                            if (direct == 1 && wheelStatus != 1) {
                                wheelStatus = 1;
                                iv_wheel.setImageResource(R.drawable.if_steering_wheel_left);
                            } else if (direct == 2 && wheelStatus != 2) {
                                wheelStatus = 2;
                                iv_wheel.setImageResource(R.drawable.if_steering_wheel_right);
                            }
                        }
                        break;

                }
            }

            return true;
        }
    });

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_kingpin, container, false);
        initView(rootView);
        return rootView;
    }

    private void initView(View view) {
        Button btn = (Button) view.findViewById(R.id.btn_next);
        tv = (TextView) view.findViewById(R.id.tv_result);
        iv_wheel = (ImageView) view.findViewById(R.id.imageView_wheel);
        iv_circle = (ImageView) view.findViewById(R.id.imageView_circle);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (callback != null)
                    callback.kinPingNext(MainApplication.frontShowUrl);
            }
        });
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        kingpinCircleThread = new KingpinCircleThread(handler);
        myDialog = new MyDialog(getActivity());
    }

    @Override
    public void onResume() {
        super.onResume();
        transFlag = true;
        startConnect();
    }

    @Override
    public void onPause() {
        super.onPause();
        transFlag = false;
        myDialog.dismiss();
        if (loginConnect != null) loginConnect.close();
        if (socketClient != null) socketClient.close();
    }

    public void startConnect() {
        if (MainApplication.availableDay > 0) {
            socketClient = new NetConnect(handler, MainApplication.kingpinUrl);
        } else if (!MainApplication.loginFlag) {
            loginConnect = new NetSingleConnect(handler, MainApplication.loginUrl);
        } else {
            Toast.makeText(MainApplication.context, R.string.tip_recharge, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        kingpinCircleThread.close();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            callback = (KinPingCallback) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException("Activity must implement KinPingCallback");
        }

    }

    public interface KinPingCallback {
        void kinPingNext(int position);
    }

    private Float[] getKingpinData(String msg) {
        Float[] data = new Float[3];
        if (msg.contains("&&")) {
            String[] arr = msg.split("&&");
            String[] array = arr[1].split("\\|");
            try {
                if (array.length == 3) {
                    data[0] = Float.valueOf(array[0]);
                    data[1] = Float.valueOf(array[1]);
                    data[2] = Float.valueOf(array[2]);
                } else {
                    data[0] = null;
                    data[1] = null;
                    data[2] = null;
                }
            } catch (NumberFormatException e) {
                data[0] = null;
                data[1] = null;
                data[2] = null;
            }
        }
        return data;
    }
}
