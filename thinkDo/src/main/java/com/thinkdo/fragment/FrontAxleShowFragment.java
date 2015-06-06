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

import com.thinkdo.activity.MainActivity;
import com.thinkdo.activity.R;
import com.thinkdo.entity.GloVariable;
import com.thinkdo.entity.ReferData;
import com.thinkdo.net.NetConnect;
import com.thinkdo.net.NetQuest;
import com.thinkdo.util.CommonUtil;
import com.thinkdo.util.DataCircleLoadThread;
import com.thinkdo.util.MyDialog;
import com.thinkdo.view.ButtonRaise;
import com.thinkdo.view.WindowRealTime;

public class FrontAxleShowFragment extends Fragment {
    private WindowRealTime leftFrontCamber, leftFrontToe, leftCaster,
            frontTotalToe, rightFrontToe, rightCaster, rightFrontCamber;
    private ButtonRaise raiseBtn;
    private FrontAxleCallback callback;

    private boolean carInUp = false, transFlag = false;

    private MyDialog myDialog;
    private NetConnect socketClient;
    private DataCircleLoadThread circleLoad;


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
            } else if (backCode != GloVariable.frontShowUrl && callback != null) {
                callback.frontAxleNext(backCode);
            } else {
                switch (statusCode) {
                    case 3:
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
                        break;

                    case 4:
                        //?exception
                        break;
                }
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
                    questCode = GloVariable.downCar;
                } else {
                    contentId = R.string.tip_raiseBtn_up;
                    resId = R.drawable.ib_arrow_up_press1;
                    questCode = GloVariable.upCar;
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
                                socketClient.send(questCode, 0, null);
                            }
                        })
                        .setPositiveButton(R.string.sure, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                raiseBtn.changeChecked();
                                ((MainActivity) getActivity()).setRaise(!checked);
                                socketClient.send(questCode, 2, null);
                            }
                        })
                        .create().show();
                socketClient.send(questCode, 1, null);
            }
        });

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
        socketClient = new NetConnect(handler, GloVariable.frontShowUrl);
    }

    @Override
    public void onPause() {
        super.onPause();
        transFlag = false;
        socketClient.close();
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
