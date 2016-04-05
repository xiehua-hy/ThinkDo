package com.thinkdo.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.thinkdo.activity.MainActivityLorry;
import com.thinkdo.activity.R;
import com.thinkdo.application.MainApplication;
import com.thinkdo.entity.LorryDataItem;
import com.thinkdo.entity.LorryReferData;
import com.thinkdo.net.NetConnect;
import com.thinkdo.net.NetSingleConnect;
import com.thinkdo.net.SocketClient;
import com.thinkdo.util.CommonUtil;
import com.thinkdo.util.DataCircleLoadThread;
import com.thinkdo.util.MyDialog;
import com.thinkdo.view.WindowRealTime;

/**
 * Created by Administrator on 2015/9/8.
 */
public class FrontAxleLorryFragment extends Fragment implements View.OnClickListener {
    private WindowRealTime firstLeftCaster, firstRightCaster, firstLeftKpi, firstRightKpi,
            secondLeftCaster, secondRightCaster, secondLeftKpi, secondRightKpi,
            leftFrontToe, rightFrontToe, frontTotalToe, leftFrontCamber, rightFrontCamber,
            secondLeftToe, secondRightToe, secondTotalToe, secondLeftCamber, secondRightCamber;


    private FrontAxleLorryCallback callback;
    private NetConnect socketClient;
    private NetSingleConnect loginConnect;
    private DataCircleLoadThread circleLoad;
    private MyDialog myDialog;
    private boolean transFlag = false;

    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            if (!transFlag) return true;
            String reply = msg.getData().getString(MainApplication.head);
            if (reply == null) return true;

            int backCode = CommonUtil.getQuestCode(reply);
            int statusCode = CommonUtil.getStatusCode(reply);


            if (backCode == MainApplication.frontShowUrl) {
                if (MainActivityLorry.lorryReferData == null)
                    MainActivityLorry.lorryReferData = new LorryReferData();
                MainActivityLorry.lorryReferData.addRealData(reply);

                LorryReferData test = (LorryReferData) MainActivityLorry.lorryReferData.copy();
                test.unitConvert();

                if (MainActivityLorry.lorryReferData.isFlush()) {
                    loadData(test);
                    MainActivityLorry.lorryReferData.setFlush(false);
                }


                frontTotalToe.setResult(test.getFrontTotalToe());
                leftFrontToe.setResult(test.getLeftFrontToe());
                rightFrontToe.setResult(test.getRightFrontToe());

                leftFrontCamber.setResult(test.getLeftFrontCamber());
                rightFrontCamber.setResult(test.getRightFrontCamber());

                secondTotalToe.setResult(test.getSecondTotalToe());
                secondLeftToe.setResult(test.getSecondLeftToe());
                secondRightToe.setResult(test.getSecondRightToe());

                secondLeftCamber.setResult(test.getSecondLeftCamber());
                secondRightCamber.setResult(test.getSecondRightCamber());

                firstLeftCaster.setResult(test.getLeftCaster());
                firstRightCaster.setResult(test.getRightCaster());

                secondLeftCaster.setResult(test.getSecondLeftCaster());
                secondRightCaster.setResult(test.getSecondRightCaster());

                firstLeftKpi.setResult(test.getLeftKpi());
                firstRightKpi.setResult(test.getRightKpi());

                secondLeftKpi.setResult(test.getSecondLeftKpi());
                secondRightKpi.setResult(test.getSecondRightKpi());

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

                circleLoad.loadCirclePic(secondTotalToe.getLinearLayout(),
                        test.getSecondTotalToe().getPercent());
                circleLoad.loadCirclePic(secondLeftToe.getLinearLayout(),
                        test.getSecondLeftToe().getPercent());
                circleLoad.loadCirclePic(secondRightToe.getLinearLayout(),
                        test.getSecondRightToe().getPercent());

                circleLoad.loadCirclePic(secondLeftCamber.getLinearLayout(),
                        test.getSecondLeftCamber().getPercent());
                circleLoad.loadCirclePic(secondRightCamber.getLinearLayout(),
                        test.getSecondRightCamber().getPercent());

                circleLoad.loadCirclePic(firstLeftCaster.getLinearLayout(),
                        test.getLeftCaster().getPercent());
                circleLoad.loadCirclePic(firstRightCaster.getLinearLayout(),
                        test.getRightCaster().getPercent());

                circleLoad.loadCirclePic(secondLeftCaster.getLinearLayout(),
                        test.getSecondLeftCaster().getPercent());
                circleLoad.loadCirclePic(secondRightCaster.getLinearLayout(),
                        test.getSecondRightCaster().getPercent());

                circleLoad.loadCirclePic(firstLeftKpi.getLinearLayout(),
                        test.getLeftKpi().getPercent());
                circleLoad.loadCirclePic(firstRightKpi.getLinearLayout(),
                        test.getRightKpi().getPercent());

                circleLoad.loadCirclePic(secondLeftKpi.getLinearLayout(),
                        test.getSecondLeftKpi().getPercent());
                circleLoad.loadCirclePic(secondRightKpi.getLinearLayout(),
                        test.getSecondRightKpi().getPercent());


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
                callback.frontAxleLorryNext(backCode);
            }

            return true;
        }
    });

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_frontaxle_show_lorry, container, false);
        init(rootView);
        return rootView;
    }

    private void init(View view) {
        leftFrontToe = (WindowRealTime) view.findViewById(R.id.window_firstLeftToe);
        rightFrontToe = (WindowRealTime) view.findViewById(R.id.window_firstRightToe);
        frontTotalToe = (WindowRealTime) view.findViewById(R.id.window_firstTotalToe);

        leftFrontCamber = (WindowRealTime) view.findViewById(R.id.window_firstLeftCamber);
        rightFrontCamber = (WindowRealTime) view.findViewById(R.id.window_firstRightCamber);

        secondLeftToe = (WindowRealTime) view.findViewById(R.id.window_secondLeftToe);
        secondRightToe = (WindowRealTime) view.findViewById(R.id.window_secondRightToe);
        secondTotalToe = (WindowRealTime) view.findViewById(R.id.window_secondTotalToe);

        secondLeftCamber = (WindowRealTime) view.findViewById(R.id.window_secondLeftCamber);
        secondRightCamber = (WindowRealTime) view.findViewById(R.id.window_secondRightCamber);

        firstLeftCaster = (WindowRealTime) view.findViewById(R.id.window_firstLeftCaster);
        firstRightCaster = (WindowRealTime) view.findViewById(R.id.window_firstRightCaster);
        firstLeftKpi = (WindowRealTime) view.findViewById(R.id.window_firstLeftKpi);
        firstRightKpi = (WindowRealTime) view.findViewById(R.id.window_firstRightKpi);

        secondLeftCaster = (WindowRealTime) view.findViewById(R.id.window_secondLeftCaster);
        secondRightCaster = (WindowRealTime) view.findViewById(R.id.window_secondRightCaster);
        secondLeftKpi = (WindowRealTime) view.findViewById(R.id.window_secondLeftKpi);
        secondRightKpi = (WindowRealTime) view.findViewById(R.id.window_secondRightKpi);


        if (MainActivityLorry.lorryReferData != null) {
            LorryReferData copy = (LorryReferData) MainActivityLorry.lorryReferData.copy();
            copy.unitConvert();
            loadData(copy);
        }

        leftFrontToe.setOnClickListener(this);
        rightFrontToe.setOnClickListener(this);
        frontTotalToe.setOnClickListener(this);

        leftFrontCamber.setOnClickListener(this);
        rightFrontCamber.setOnClickListener(this);

        secondLeftToe.setOnClickListener(this);
        secondRightToe.setOnClickListener(this);
        secondTotalToe.setOnClickListener(this);

        secondLeftCamber.setOnClickListener(this);
        secondRightCamber.setOnClickListener(this);

        firstLeftCaster.setOnClickListener(this);
        firstRightCaster.setOnClickListener(this);
        firstLeftKpi.setOnClickListener(this);
        firstRightKpi.setOnClickListener(this);

        secondLeftCaster.setOnClickListener(this);
        secondRightCaster.setOnClickListener(this);
        secondLeftKpi.setOnClickListener(this);
        secondRightKpi.setOnClickListener(this);
    }

    private void loadData(LorryReferData copy) {
        frontTotalToe.setAllValues(copy.getFrontTotalToe());
        leftFrontToe.setAllValues(copy.getLeftFrontToe());
        rightFrontToe.setAllValues(copy.getRightFrontToe(), false);

        leftFrontCamber.setAllValues(copy.getLeftFrontCamber(), false);
        rightFrontCamber.setAllValues(copy.getRightFrontCamber());

        secondTotalToe.setAllValues(copy.getSecondTotalToe());
        secondLeftToe.setAllValues(copy.getSecondLeftToe());
        secondRightToe.setAllValues(copy.getSecondRightToe(), false);

        secondLeftCamber.setAllValues(copy.getSecondLeftCamber(), false);
        secondRightCamber.setAllValues(copy.getSecondRightCamber());

        firstLeftCaster.setAllValues(copy.getLeftCaster());
        firstRightCaster.setAllValues(copy.getRightCaster());

        secondLeftCaster.setAllValues(copy.getLeftCaster());
        secondRightCaster.setAllValues(copy.getRightCaster());


        firstLeftKpi.setAllValues(copy.getLeftKpi());
        firstRightKpi.setAllValues(copy.getRightKpi());

        secondLeftKpi.setAllValues(copy.getLeftKpi());
        secondRightKpi.setAllValues(copy.getRightKpi());
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

    @Override
    public void onClick(final View v) {
        String[] array = getResources().getStringArray(R.array.axle_short);

        final Spinner spinner = new Spinner(getActivity());
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_selectable_list_item, array);
        spinner.setAdapter(adapter);

        new AlertDialog.Builder(getActivity())
                .setTitle(R.string.tip_data_save_as)
                .setView(spinner)
                .setNegativeButton(R.string.cancel, null)
                .setPositiveButton(R.string.sure, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        saveData(v, spinner.getSelectedItemPosition());
                        toast(R.string.tip_data_saved);
                    }
                })
                .create().show();
    }


    public void saveData(View v, int index) {
        if (MainActivityLorry.lorryReferData == null) {
            Toast.makeText(getActivity(), R.string.tip_data_cannot_null, Toast.LENGTH_SHORT).show();
            return;
        }

        if (MainActivityLorry.printData == null) MainActivityLorry.printData = new LorryDataItem();
        switch (v.getId()) {
            case R.id.window_firstTotalToe:
            case R.id.window_firstLeftToe:
            case R.id.window_firstRightToe:
            case R.id.window_firstLeftCamber:
            case R.id.window_firstRightCamber:
            case R.id.window_firstLeftCaster:
            case R.id.window_firstRightCaster:
            case R.id.window_firstLeftKpi:
            case R.id.window_firstRightKpi:
                MainActivityLorry.printData.setTotalToe(index, MainActivityLorry.lorryReferData.getFrontTotalToe());
                MainActivityLorry.printData.setLeftToe(index, MainActivityLorry.lorryReferData.getLeftFrontToe());
                MainActivityLorry.printData.setRightToe(index, MainActivityLorry.lorryReferData.getRightFrontToe());
                MainActivityLorry.printData.setLeftCamber(index, MainActivityLorry.lorryReferData.getLeftFrontCamber());
                MainActivityLorry.printData.setRightCamber(index, MainActivityLorry.lorryReferData.getRightFrontCamber());

                MainActivityLorry.printData.setLeftCaster(index, MainActivityLorry.lorryReferData.getLeftCaster());
                MainActivityLorry.printData.setRightCaster(index, MainActivityLorry.lorryReferData.getRightCaster());
                MainActivityLorry.printData.setLeftKpi(index, MainActivityLorry.lorryReferData.getLeftKpi());
                MainActivityLorry.printData.setRightKpi(index, MainActivityLorry.lorryReferData.getRightKpi());
                break;
            case R.id.window_secondTotalToe:
            case R.id.window_secondLeftToe:
            case R.id.window_secondRightToe:
            case R.id.window_secondLeftCamber:
            case R.id.window_secondRightCamber:
            case R.id.window_secondLeftCaster:
            case R.id.window_secondRightCaster:
            case R.id.window_secondLeftKpi:
            case R.id.window_secondRightKpi:
                MainActivityLorry.printData.setTotalToe(index, MainActivityLorry.lorryReferData.getSecondTotalToe());
                MainActivityLorry.printData.setLeftToe(index, MainActivityLorry.lorryReferData.getSecondLeftToe());
                MainActivityLorry.printData.setRightToe(index, MainActivityLorry.lorryReferData.getSecondRightToe());
                MainActivityLorry.printData.setLeftCamber(index, MainActivityLorry.lorryReferData.getSecondLeftCamber());
                MainActivityLorry.printData.setRightCamber(index, MainActivityLorry.lorryReferData.getSecondRightCamber());

                MainActivityLorry.printData.setLeftCaster(index, MainActivityLorry.lorryReferData.getSecondLeftCaster());
                MainActivityLorry.printData.setRightCaster(index, MainActivityLorry.lorryReferData.getSecondRightCaster());
                MainActivityLorry.printData.setLeftKpi(index, MainActivityLorry.lorryReferData.getSecondLeftKpi());
                MainActivityLorry.printData.setRightKpi(index, MainActivityLorry.lorryReferData.getSecondRightKpi());
                break;

        }
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
            callback = (FrontAxleLorryCallback) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException("Activity must implement FrontAxleLorryCallback.");
        }
    }

    public interface FrontAxleLorryCallback {
        void frontAxleLorryNext(int position);
    }

    public void toast(int id) {
        Toast toast = Toast.makeText(getActivity(), id, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.BOTTOM, 0, 0);
        toast.show();
    }
}
