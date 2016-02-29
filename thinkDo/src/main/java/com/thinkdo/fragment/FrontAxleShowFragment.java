package com.thinkdo.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.thinkdo.activity.MainActivity;
import com.thinkdo.activity.R;
import com.thinkdo.application.MainApplication;
import com.thinkdo.entity.ReferData;
import com.thinkdo.net.NetConnect;
import com.thinkdo.net.NetSingleConnect;
import com.thinkdo.net.SocketClient;
import com.thinkdo.util.CommonUtil;
import com.thinkdo.util.DataCircleLoadThread;
import com.thinkdo.util.MyDialog;
import com.thinkdo.view.ButtonRaise;
import com.thinkdo.view.WindowRealTime;

public class FrontAxleShowFragment extends Fragment {
    private WindowRealTime frontTotalToe, leftFrontToe, rightFrontToe, leftFrontCamber, rightFrontCamber,
            leftCaster, rightCaster, leftKpi, rightKpi;
    private ButtonRaise raiseBtn;
    private FrontAxleCallback callback;
    private boolean carInUp = false, transFlag = false;
    private MyDialog myDialog;
    private NetConnect socketClient;
    private NetSingleConnect loginConnect;
    private DataCircleLoadThread circleLoad;


    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            if (!transFlag) return true;
            String reply = msg.getData().getString(MainApplication.head);
            if (reply == null) return true;
            int backCode = CommonUtil.getQuestCode(reply);
            int statusCode = CommonUtil.getStatusCode(reply);


            if (backCode == MainApplication.frontShowUrl) {
                if (!raiseBtn.isBtnEnable()) raiseBtn.setBtnEnable(true);

                if (MainActivity.referData == null)
                    MainActivity.referData = new ReferData();
                MainActivity.referData.addRealData(reply);

                ReferData test = MainActivity.referData.copy();
                test.unitConvert();

                if (MainActivity.referData.isFlush()) {
                    loadData(test);
                    MainActivity.referData.setFlush(false);
                }

                if (carInUp != test.isRaiseStatus()) {
                    carInUp = !carInUp;
                    raiseBtn.setChecked(carInUp);
                    ((MainActivity) getActivity()).setRaise(carInUp);
                }

                frontTotalToe.setResult(test.getFrontTotalToe());
                leftFrontToe.setResult(test.getLeftFrontToe());
                rightFrontToe.setResult(test.getRightFrontToe());

                leftFrontCamber.setResult(test.getLeftFrontCamber());
                rightFrontCamber.setResult(test.getRightFrontCamber());

                leftCaster.setResult(test.getLeftCaster());
                rightCaster.setResult(test.getRightCaster());

                leftKpi.setResult(test.getLeftKpi());
                rightKpi.setResult(test.getRightKpi());

                circleLoad.loadCirclePic(frontTotalToe.getLinearLayout(),
                        test.getFrontTotalToe().getPercent());
                circleLoad.loadCirclePic(leftFrontToe.getLinearLayout(),
                        test.getLeftFrontToe().getPercent());
                circleLoad.loadCirclePic(rightFrontToe.getLinearLayout(),
                        test.getRightFrontToe().getPercent());

                circleLoad.loadCirclePic(leftFrontCamber.getLinearLayout(),
                        test.getLeftFrontCamber().getPercent());
                circleLoad.loadCirclePic(rightFrontCamber.getLinearLayout(),
                        test.getRightFrontCamber().getPercent());

                circleLoad.loadCirclePic(leftCaster.getLinearLayout(),
                        test.getLeftCaster().getPercent());
                circleLoad.loadCirclePic(rightCaster.getLinearLayout(),
                        test.getRightCaster().getPercent());

                circleLoad.loadCirclePic(leftKpi.getLinearLayout(),
                        test.getLeftKpi().getPercent());
                circleLoad.loadCirclePic(rightKpi.getLinearLayout(),
                        test.getRightKpi().getPercent());

            } else if (backCode == MainApplication.errorUrl) {
                if (statusCode == MainApplication.erroDiss) {
                    myDialog.dismiss();
                } else {
                    myDialog.show(CommonUtil.getErrorString(statusCode, reply));
                }
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
                callback.frontAxleNext(backCode);
            }

            return true;
        }
    });

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_frontaxle_show, container, false);
        init(rootView);
        return rootView;
    }

    private void init(View view) {
        frontTotalToe = (WindowRealTime) view.findViewById(R.id.window_frontTotalToe);
        leftFrontToe = (WindowRealTime) view.findViewById(R.id.window_leftFrontToe);
        rightFrontToe = (WindowRealTime) view.findViewById(R.id.window_rightFrontToe);
        leftFrontCamber = (WindowRealTime) view.findViewById(R.id.window_leftFrontCamber);
        rightFrontCamber = (WindowRealTime) view.findViewById(R.id.window_rightFrontCamber);
        leftCaster = (WindowRealTime) view.findViewById(R.id.window_leftCaster);
        rightCaster = (WindowRealTime) view.findViewById(R.id.window_rightCaster);
        leftKpi = (WindowRealTime) view.findViewById(R.id.window_leftKPI);
        rightKpi = (WindowRealTime) view.findViewById(R.id.window_rightKPI);


        raiseBtn = (ButtonRaise) view.findViewById(R.id.btn_raise);
        raiseBtn.setChecked(carInUp);
        raiseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final boolean checked = raiseBtn.isChecked();
                int resId, contentId;
                final int questCode;
                if (checked) {
                    contentId = R.string.tip_raiseBtn_down;
                    resId = R.drawable.ib_arrow_down_press1;
                    questCode = MainApplication.downCar;
                } else {
                    contentId = R.string.tip_raiseBtn_up;
                    resId = R.drawable.ib_arrow_up_press1;
                    questCode = MainApplication.upCar;
                }

                ImageView iv = new ImageView(getActivity());
                iv.setImageResource(resId);

                new AlertDialog.Builder(getActivity(),
                        AlertDialog.THEME_HOLO_LIGHT)
                        .setIconAttribute(android.R.attr.alertDialogIcon)
                        .setTitle(contentId)
                        .setView(iv)
                        .setCancelable(false)
                        .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (socketClient != null) socketClient.send(questCode, 0, null);
                            }
                        })
                        .setPositiveButton(R.string.sure, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                raiseBtn.changeChecked();
                                ((MainActivity) getActivity()).setRaise(!checked);
                                if (socketClient != null) socketClient.send(questCode, 2, null);
                            }
                        })
                        .create().show();
                if (socketClient != null) socketClient.send(questCode, 1, null);
            }
        });

        if (!raiseBtn.isChecked()) raiseBtn.setBtnEnable(false);

        if (MainActivity.referData != null) {
            ReferData copy = MainActivity.referData.copy();
            copy.unitConvert();
            loadData(copy);
        }
    }

    private void loadData(ReferData copy) {
        frontTotalToe.setAllValues(copy.getFrontTotalToe());
        leftFrontToe.setAllValues(copy.getLeftFrontToe());
        rightFrontToe.setAllValues(copy.getRightFrontToe(), false);

        leftFrontCamber.setAllValues(copy.getLeftFrontCamber(), false);
        rightFrontCamber.setAllValues(copy.getRightFrontCamber());

        leftCaster.setAllValues(copy.getLeftCaster());
        rightCaster.setAllValues(copy.getRightCaster());

        leftKpi.setAllValues(copy.getLeftKpi());
        rightKpi.setAllValues(copy.getRightKpi());
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myDialog = new MyDialog(getActivity());
        circleLoad = new DataCircleLoadThread(handler, 2);
    }

    @Override
    public void onResume() {
        super.onResume();
        transFlag = true;
        startConnect();
    }

    public void startConnect() {

        if (MainApplication.availableDay > 0) {
            socketClient = new NetConnect(handler, MainApplication.frontShowUrl);
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
    public void onDestroy() {
        super.onDestroy();
        circleLoad.close();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            callback = (FrontAxleCallback) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException("Activity must implement FrontAxleCallback.");
        }
    }

    public interface FrontAxleCallback {
        void frontAxleNext(int position);
    }


}
