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
import com.thinkdo.util.CommonUtil;
import com.thinkdo.util.DataCircleLoadThread;
import com.thinkdo.util.MyDialog;
import com.thinkdo.view.ButtonRaise;
import com.thinkdo.view.WindowRealTime;

/**
 * Created by Administrator on 2015/4/30.
 */
public class RearAxleShowFragment extends Fragment {
    private RearAxleCallback callback;
    private WindowRealTime rearTotalToe, leftRearCamber, leftRearToe, rightRearToe, rightRearCamber;
    private ButtonRaise raiseBtn;

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
            } else if (backCode != GloVariable.rearShowUrl && callback != null) {
                callback.rearAxleNext(backCode);
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

                        leftRearCamber.setResult(test.getLeftRearCamber());
                        rightRearCamber.setResult(test.getRightRearCamber());

                        rearTotalToe.setResult(test.getRearTotalToe());
                        leftRearToe.setResult(test.getLeftRearToe());
                        rightRearToe.setResult(test.getRightRearToe());

                        circleLoad.loadCirclePic(leftRearCamber.getLinearLayout(),
                                test.getLeftRearCamber().getPercent());
                        circleLoad.loadCirclePic(rightRearCamber.getLinearLayout(),
                                test.getRightRearCamber().getPercent());

                        circleLoad.loadCirclePic(rearTotalToe.getLinearLayout(),
                                test.getRearTotalToe().getPercent());
                        circleLoad.loadCirclePic(leftRearToe.getLinearLayout(),
                                test.getLeftRearToe().getPercent());
                        circleLoad.loadCirclePic(rightRearToe.getLinearLayout(),
                                test.getRightRearToe().getPercent());
                        break;

                    case 4:
                        //�exception
                        break;
                }
            }

            return true;
        }
    });

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_rearaxle_show, container, false);
        init(rootView);
        return rootView;
    }

    private void init(View view) {
        rearTotalToe = (WindowRealTime) view.findViewById(R.id.window_rearTotalToe);
        leftRearToe = (WindowRealTime) view.findViewById(R.id.window_leftRearToe);
        rightRearToe = (WindowRealTime) view.findViewById(R.id.window_rightRearToe);
        leftRearCamber = (WindowRealTime) view.findViewById(R.id.window_leftRearCamber);
        rightRearCamber = (WindowRealTime) view.findViewById(R.id.window_rightRearCamber);

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
        rearTotalToe.setAllValues(copy.getRearTotalToe());
        leftRearToe.setAllValues(copy.getLeftRearToe());
        rightRearToe.setAllValues(copy.getRightRearToe(), false);

        leftRearCamber.setAllValues(copy.getLeftRearCamber(), false);
        rightRearCamber.setAllValues(copy.getRightRearCamber());
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
        socketClient = new NetConnect(handler, GloVariable.rearShowUrl);
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
            callback = (RearAxleCallback) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException("Activity must implement RearAxleCallback.");
        }
    }

    public void setRaiseBtnStatus(boolean raiseBtnStatus) {
        this.carInUp = raiseBtnStatus;
    }

    public interface RearAxleCallback {
        void rearAxleNext(int position);
    }

}
