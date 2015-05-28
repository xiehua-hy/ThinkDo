package com.thinkdo.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.graphics.Color;
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

/**
 * Created by Administrator on 2015/5/6.
 */
public class TestResultFragment extends Fragment {
    private WindowRealTime leftFrontCamber, leftRearCamber, leftFrontToe, leftKPI, leftCaster, leftRearToe,
            frontTotalToe, rearTotalToe, thrustAngle, rightFrontToe, rightRearToe, rightKPI,
            rightCaster, rightFrontCamber, rightRearCamber;
    private ButtonRaise raiseBtn;
    private TestResultCallback callback;
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
            } else if (backCode != GloVariable.testDataUrl && callback != null) {
                callback.TestResultNext(backCode);
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

                        leftKPI.setResult(test.getLeftKpi());
                        rightKPI.setResult(test.getRightKpi());

                        rearTotalToe.setResult(test.getRearTotalToe());
                        leftRearToe.setResult(test.getLeftRearToe());
                        rightRearToe.setResult(test.getRightRearToe());

                        leftRearCamber.setResult(test.getLeftRearCamber());
                        rightRearCamber.setResult(test.getRightRearCamber());

                        thrustAngle.setResultText(test.getMaxThrust().getReal());

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

                        circleLoad.loadCirclePic(leftKPI.getLinearLayout(),
                                test.getLeftKpi().getPercent());
                        circleLoad.loadCirclePic(rightKPI.getLinearLayout(),
                                test.getRightKpi().getPercent());

                        circleLoad.loadCirclePic(rearTotalToe.getLinearLayout(),
                                test.getRearTotalToe().getPercent());
                        circleLoad.loadCirclePic(leftRearToe.getLinearLayout(),
                                test.getLeftRearToe().getPercent());
                        circleLoad.loadCirclePic(rightRearToe.getLinearLayout(),
                                test.getRightRearToe().getPercent());

                        circleLoad.loadCirclePic(leftRearCamber.getLinearLayout(),
                                test.getLeftRearCamber().getPercent());
                        circleLoad.loadCirclePic(rightRearCamber.getLinearLayout(),
                                test.getRightRearCamber().getPercent());

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
        View rootView = inflater.inflate(R.layout.fragment_test_result, container, false);
        init(rootView);
        return rootView;
    }

    private void init(View view) {
        frontTotalToe = (WindowRealTime) view.findViewById(R.id.window_frontTotalToe);
        leftFrontToe = (WindowRealTime) view.findViewById(R.id.window_leftFrontToe);
        rightFrontToe = (WindowRealTime) view.findViewById(R.id.window_rightFrontToe);

        leftFrontCamber = (WindowRealTime) view.findViewById(R.id.window_leftFrontCamber);
        leftRearCamber = (WindowRealTime) view.findViewById(R.id.window_leftRearCamber);

        leftKPI = (WindowRealTime) view.findViewById(R.id.window_leftKPI);
        rightKPI = (WindowRealTime) view.findViewById(R.id.window_rightKPI);

        leftCaster = (WindowRealTime) view.findViewById(R.id.window_leftCaster);
        rightCaster = (WindowRealTime) view.findViewById(R.id.window_rightCaster);

        rearTotalToe = (WindowRealTime) view.findViewById(R.id.window_rearTotalToe);
        leftRearToe = (WindowRealTime) view.findViewById(R.id.window_leftRearToe);
        rightRearToe = (WindowRealTime) view.findViewById(R.id.window_rightRearToe);

        rightFrontCamber = (WindowRealTime) view.findViewById(R.id.window_rightFrontCamber);
        rightRearCamber = (WindowRealTime) view.findViewById(R.id.window_rightRearCamber);

        thrustAngle = (WindowRealTime) view.findViewById(R.id.window_thrustAngle);
        thrustAngle.setResultColor(Color.BLUE);

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
                                new NetQuest(questCode).start();
                            }
                        })
                        .setPositiveButton(R.string.sure, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                raiseBtn.changeChecked();
                                ((MainActivity) getActivity()).setRaise(!checked);
                                new NetQuest(questCode, 2).start();
                            }
                        })
                        .create().show();

                new NetQuest(questCode, 1).start();
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

        leftFrontCamber.setAllValues(copy.getLeftFrontCamber(),false);
        rightFrontCamber.setAllValues(copy.getRightFrontCamber());

        leftCaster.setAllValues(copy.getLeftCaster());
        rightCaster.setAllValues(copy.getRightCaster());

        leftKPI.setAllValues(copy.getLeftKpi());
        rightKPI.setAllValues(copy.getRightKpi(), false);

        rearTotalToe.setAllValues(copy.getRearTotalToe());
        leftRearToe.setAllValues(copy.getLeftRearToe());
        rightRearToe.setAllValues(copy.getRightRearToe(), false);

        leftRearCamber.setAllValues(copy.getLeftRearCamber(),false);
        rightRearCamber.setAllValues(copy.getRightRearCamber());

        thrustAngle.setMiddleText(copy.getMaxThrust().getMid());
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
        socketClient = new NetConnect(getActivity(), handler, GloVariable.testDataUrl);
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
            callback = (TestResultCallback) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException("Activity must implement TestResultCallback.");
        }
    }

    public interface TestResultCallback {
        void TestResultNext(int position);
    }
}
