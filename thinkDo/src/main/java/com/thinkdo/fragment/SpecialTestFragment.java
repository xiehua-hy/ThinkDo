package com.thinkdo.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.thinkdo.activity.R;
import com.thinkdo.application.MainApplication;
import com.thinkdo.entity.SpecialTestData;
import com.thinkdo.net.NetConnect;
import com.thinkdo.net.NetSingleConnect;
import com.thinkdo.net.SocketClient;
import com.thinkdo.util.CommonUtil;
import com.thinkdo.util.MyDialog;
import com.thinkdo.view.WindowSpecial;

/**
 * Created by Administrator on 2015/5/7.
 */
public class SpecialTestFragment extends Fragment {
    private WindowSpecial leftWheelbase, wheelbase_diff, rearWheel, frontWheel, axleOffset, wheel_diff, rightWheelbase;
    private boolean transFlag = false;
    private MyDialog myDialog;
    private NetConnect socketClient;
    private NetSingleConnect loginConnect;
    private SpecialTestCallback callback;
    private SpecialTestData data;
    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            if (!transFlag) return true;
            String reply = msg.getData().getString(MainApplication.head);
            if (reply == null) return true;
            int backCode = CommonUtil.getQuestCode(reply);
            int statusCode = CommonUtil.getStatusCode(reply);


            if (backCode == MainApplication.specialTestUrl) {
                if (myDialog.isShow()) myDialog.dismiss();

                if (data == null) data = new SpecialTestData();
                data.addRealData(reply);
                data.unitConvert();
                leftWheelbase.setTestText(data.getLeftWheelbase());
                wheelbase_diff.setTestText(data.getWheelbase_diff());
                rearWheel.setTestText(data.getRearWheel());
                frontWheel.setTestText(data.getFrontWheel());
                axleOffset.setTestText(data.getAxleOffset());
                wheel_diff.setTestText(data.getWheel_diff());
                rightWheelbase.setTestText(data.getRightWheelbase());
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

            } else if (backCode == MainApplication.errorUrl && statusCode != MainApplication.erroDiss) {
                myDialog.show(CommonUtil.getErrorString(statusCode, reply));
            } else if (backCode != MainApplication.specialTestUrl && callback != null) {
                callback.specialTest(backCode);
            }
            return true;
        }
    });


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_special_test, container, false);
        init(rootView);
        return rootView;
    }

    private void init(View view) {
        leftWheelbase = (WindowSpecial) view.findViewById(R.id.leftWheelbase);
        wheelbase_diff = (WindowSpecial) view.findViewById(R.id.wheelbaseDiff);
        rearWheel = (WindowSpecial) view.findViewById(R.id.rearWheel);
        frontWheel = (WindowSpecial) view.findViewById(R.id.frontWheel);
        axleOffset = (WindowSpecial) view.findViewById(R.id.axleOffset);
        wheel_diff = (WindowSpecial) view.findViewById(R.id.wheelDiff);
        rightWheelbase = (WindowSpecial) view.findViewById(R.id.rightWheelbase);

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myDialog = new MyDialog(getActivity());
    }

    @Override
    public void onResume() {
        super.onResume();
        transFlag = true;
        startConnect();
    }

    public void startConnect() {
        if (MainApplication.availableDay > 0) {
            socketClient = new NetConnect(handler, MainApplication.specialTestUrl);
        } else if (!MainApplication.loginFlag) {
            loginConnect = new NetSingleConnect(handler, MainApplication.loginUrl);
        } else {
            Toast.makeText(MainApplication.context, R.string.tip_recharge, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        transFlag = false;
        if (loginConnect != null) loginConnect.close();
        if (socketClient != null) socketClient.close();
        myDialog.dismiss();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            callback = (SpecialTestCallback) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException("Activity must implement SpecialTestCallback.");
        }
    }

    public interface SpecialTestCallback {
        void specialTest(int position);
    }
}
