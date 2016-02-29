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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.thinkdo.activity.MainActivityLorry;
import com.thinkdo.activity.R;
import com.thinkdo.application.MainApplication;
import com.thinkdo.entity.LorryDataItem;
import com.thinkdo.entity.LorryReferData;
import com.thinkdo.net.NetConnect;
import com.thinkdo.net.NetQuest;
import com.thinkdo.net.NetSingleConnect;
import com.thinkdo.net.SocketClient;
import com.thinkdo.util.CommonUtil;
import com.thinkdo.util.DataCircleLoadThread;
import com.thinkdo.util.MyDialog;
import com.thinkdo.view.WindowRealTime;

/**
 * Created by Administrator on 2015/9/9.
 */
public class TestResultLorryFragment extends Fragment implements View.OnClickListener {
    private WindowRealTime leftFrontToe, rightFrontToe, frontTotalToe, leftFrontCamber, rightFrontCamber,
            secondLeftToe, secondRightToe, secondTotalToe, secondLeftCamber, secondRightCamber,
            leftRearToe, rightRearToe, rearTotalToe, leftRearCamber, rightRearCamber,
            fourthLeftToe, fourthRightToe, fourthTotalToe, fourthLeftCamber, fourthRightCamber;

    private MyDialog myDialog;
    private NetConnect socketClient;
    private NetSingleConnect loginConnect;
    private DataCircleLoadThread circleLoad;
    private boolean transFlag = false;
    private TestResultLorryCallback callback;
    private int referAxle = 0;
    private RadioButton radioBtn1, radioBtn2, radioBtn3, radioBtn4;

    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            if (!transFlag) return true;
            String reply = msg.getData().getString(MainApplication.head);
            if (reply == null) return true;
            int backCode = CommonUtil.getQuestCode(reply);
            int statusCode = CommonUtil.getStatusCode(reply);

            if (backCode == MainApplication.testDataUrl) {

                if (MainActivityLorry.lorryReferData == null)
                    MainActivityLorry.lorryReferData = new LorryReferData();

                MainActivityLorry.lorryReferData.addRealData(reply);

                LorryReferData test = (LorryReferData) MainActivityLorry.lorryReferData.copy();
                test.unitConvert();

                if (MainActivityLorry.lorryReferData.isFlush()) {
                    loadData(test);
                    MainActivityLorry.lorryReferData.setFlush(false);
                }

                if (referAxle != test.getReferAxle()) {
                    setRadioBtnCheck(test.getReferAxle());
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

                rearTotalToe.setResult(test.getRearTotalToe());
                leftRearToe.setResult(test.getLeftRearToe());
                rightRearToe.setResult(test.getRightRearToe());

                leftRearCamber.setResult(test.getLeftRearCamber());
                rightRearCamber.setResult(test.getRightRearCamber());

                fourthTotalToe.setResult(test.getFourthTotalToe());
                fourthLeftToe.setResult(test.getFourthLeftToe());
                fourthRightToe.setResult(test.getFourthRightToe());

                fourthLeftCamber.setResult(test.getFourthLeftCamber());
                fourthRightCamber.setResult(test.getFourthRightCamber());

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

                circleLoad.loadCirclePic(fourthTotalToe.getLinearLayout(),
                        test.getFourthTotalToe().getPercent());
                circleLoad.loadCirclePic(fourthLeftToe.getLinearLayout(),
                        test.getFourthLeftToe().getPercent());
                circleLoad.loadCirclePic(fourthRightToe.getLinearLayout(),
                        test.getFourthRightToe().getPercent());


                circleLoad.loadCirclePic(fourthLeftCamber.getLinearLayout(),
                        test.getFourthLeftCamber().getPercent());
                circleLoad.loadCirclePic(fourthRightCamber.getLinearLayout(),
                        test.getFourthRightCamber().getPercent());

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
                callback.testResultLorryNext(backCode);
            }

            return true;
        }
    });

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_test_result_lorry, container, false);
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


        leftRearToe = (WindowRealTime) view.findViewById(R.id.window_thirdLeftToe);
        rightRearToe = (WindowRealTime) view.findViewById(R.id.window_thirdRightToe);
        rearTotalToe = (WindowRealTime) view.findViewById(R.id.window_thirdTotalToe);

        leftRearCamber = (WindowRealTime) view.findViewById(R.id.window_thirdLeftCamber);
        rightRearCamber = (WindowRealTime) view.findViewById(R.id.window_thirdRightCamber);


        fourthLeftToe = (WindowRealTime) view.findViewById(R.id.window_fourthLeftToe);
        fourthRightToe = (WindowRealTime) view.findViewById(R.id.window_fourthRightToe);
        fourthTotalToe = (WindowRealTime) view.findViewById(R.id.window_fourthTotalToe);

        fourthLeftCamber = (WindowRealTime) view.findViewById(R.id.window_fourthLeftCamber);
        fourthRightCamber = (WindowRealTime) view.findViewById(R.id.window_fourthRightCamber);


        radioBtn1 = (RadioButton) view.findViewById(R.id.radioButton1);
        radioBtn2 = (RadioButton) view.findViewById(R.id.radioButton2);
        radioBtn3 = (RadioButton) view.findViewById(R.id.radioButton3);
        radioBtn4 = (RadioButton) view.findViewById(R.id.radioButton4);

        RadioCheckedChangeListener radioCheckedChangeListener = new RadioCheckedChangeListener();
        radioBtn1.setOnCheckedChangeListener(radioCheckedChangeListener);
        radioBtn2.setOnCheckedChangeListener(radioCheckedChangeListener);
        radioBtn3.setOnCheckedChangeListener(radioCheckedChangeListener);
        radioBtn4.setOnCheckedChangeListener(radioCheckedChangeListener);

        if (MainActivityLorry.lorryReferData != null) {
            LorryReferData copy = (LorryReferData) MainActivityLorry.lorryReferData.copy();
            copy.unitConvert();
            loadData(copy);
        }

        frontTotalToe.setOnClickListener(this);
        leftFrontToe.setOnClickListener(this);
        rightFrontToe.setOnClickListener(this);

        leftFrontCamber.setOnClickListener(this);
        rightFrontCamber.setOnClickListener(this);

        secondTotalToe.setOnClickListener(this);
        secondLeftToe.setOnClickListener(this);
        secondRightToe.setOnClickListener(this);

        secondLeftCamber.setOnClickListener(this);
        secondRightCamber.setOnClickListener(this);

        rearTotalToe.setOnClickListener(this);
        leftRearToe.setOnClickListener(this);
        rightRearToe.setOnClickListener(this);

        leftRearCamber.setOnClickListener(this);
        rightRearCamber.setOnClickListener(this);

        fourthTotalToe.setOnClickListener(this);
        fourthLeftToe.setOnClickListener(this);
        fourthRightToe.setOnClickListener(this);

        fourthLeftCamber.setOnClickListener(this);
        fourthRightCamber.setOnClickListener(this);
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

        rearTotalToe.setAllValues(copy.getRearTotalToe());
        leftRearToe.setAllValues(copy.getLeftRearToe());
        rightRearToe.setAllValues(copy.getRightRearToe(), false);

        leftRearCamber.setAllValues(copy.getLeftRearCamber(), false);
        rightRearCamber.setAllValues(copy.getRightRearCamber());


        fourthTotalToe.setAllValues(copy.getFourthTotalToe());
        fourthLeftToe.setAllValues(copy.getFourthLeftToe());
        fourthRightToe.setAllValues(copy.getFourthRightToe(), false);

        fourthLeftCamber.setAllValues(copy.getFourthLeftCamber(), false);
        fourthRightCamber.setAllValues(copy.getFourthRightCamber());
    }

    class RadioCheckedChangeListener implements CompoundButton.OnCheckedChangeListener {
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (isChecked) setRadioBtnCheck(buttonView, true);
        }
    }

    public void setRadioBtnCheck(Button radioBtn, boolean quest) {
        radioBtn1.setChecked(false);
        radioBtn2.setChecked(false);
        radioBtn3.setChecked(false);
        radioBtn4.setChecked(false);
        ((RadioButton) radioBtn).setChecked(true);

        switch (radioBtn.getId()) {
            case R.id.radioButton1:
                referAxle = 1;
                break;
            case R.id.radioButton2:
                referAxle = 2;
                break;
            case R.id.radioButton3:
                referAxle = 3;
                break;
            case R.id.radioButton4:
                referAxle = 4;
        }

        if (quest) {
            int index;
            try {
                index = Integer.parseInt(radioBtn.getText().toString());
            } catch (NumberFormatException e) {
                index = 0;
            }
            new NetQuest(MainApplication.setAxle, index);
        }
    }

    public void setRadioBtnCheck(int index) {
        switch (index) {
            case 1:
                setRadioBtnCheck(radioBtn1, false);
                break;
            case 2:
                setRadioBtnCheck(radioBtn2, false);
                break;
            case 3:
                setRadioBtnCheck(radioBtn3, false);
                break;
            case 4:
                setRadioBtnCheck(radioBtn4, false);
                break;
        }
    }


    @Override
    public void onClick(final View v) {
        String[] array = getResources().getStringArray(R.array.axle);

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

    private void saveData(View v, int index) {
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
                MainActivityLorry.printData.setTotalToe(index, MainActivityLorry.lorryReferData.getFrontTotalToe());
                MainActivityLorry.printData.setLeftToe(index, MainActivityLorry.lorryReferData.getLeftFrontToe());
                MainActivityLorry.printData.setRightToe(index, MainActivityLorry.lorryReferData.getRightFrontToe());
                MainActivityLorry.printData.setLeftCamber(index, MainActivityLorry.lorryReferData.getLeftFrontCamber());
                MainActivityLorry.printData.setRightCamber(index, MainActivityLorry.lorryReferData.getRightFrontCamber());
                break;
            case R.id.window_secondTotalToe:
            case R.id.window_secondLeftToe:
            case R.id.window_secondRightToe:
            case R.id.window_secondLeftCamber:
            case R.id.window_secondRightCamber:
                MainActivityLorry.printData.setTotalToe(index, MainActivityLorry.lorryReferData.getSecondTotalToe());
                MainActivityLorry.printData.setLeftToe(index, MainActivityLorry.lorryReferData.getSecondLeftToe());
                MainActivityLorry.printData.setRightToe(index, MainActivityLorry.lorryReferData.getSecondRightToe());
                MainActivityLorry.printData.setLeftCamber(index, MainActivityLorry.lorryReferData.getSecondLeftCamber());
                MainActivityLorry.printData.setRightCamber(index, MainActivityLorry.lorryReferData.getSecondRightCamber());
                break;
            case R.id.window_thirdTotalToe:
            case R.id.window_thirdLeftToe:
            case R.id.window_thirdRightToe:
            case R.id.window_thirdLeftCamber:
            case R.id.window_thirdRightCamber:
                MainActivityLorry.printData.setTotalToe(index, MainActivityLorry.lorryReferData.getRearTotalToe());
                MainActivityLorry.printData.setLeftToe(index, MainActivityLorry.lorryReferData.getLeftRearToe());
                MainActivityLorry.printData.setRightToe(index, MainActivityLorry.lorryReferData.getRightRearToe());
                MainActivityLorry.printData.setLeftCamber(index, MainActivityLorry.lorryReferData.getLeftRearCamber());
                MainActivityLorry.printData.setRightCamber(index, MainActivityLorry.lorryReferData.getRightRearCamber());
                break;
            case R.id.window_fourthTotalToe:
            case R.id.window_fourthLeftToe:
            case R.id.window_fourthRightToe:
            case R.id.window_fourthLeftCamber:
            case R.id.window_fourthRightCamber:
                MainActivityLorry.printData.setTotalToe(index, MainActivityLorry.lorryReferData.getFourthTotalToe());
                MainActivityLorry.printData.setLeftToe(index, MainActivityLorry.lorryReferData.getFourthLeftToe());
                MainActivityLorry.printData.setRightToe(index, MainActivityLorry.lorryReferData.getFourthRightToe());
                MainActivityLorry.printData.setLeftCamber(index, MainActivityLorry.lorryReferData.getFourthLeftCamber());
                MainActivityLorry.printData.setRightCamber(index, MainActivityLorry.lorryReferData.getFourthRightCamber());
                break;
        }
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myDialog = new MyDialog(getActivity());
        circleLoad = new DataCircleLoadThread(handler, 3);
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
        if (socketClient != null) socketClient.close();
        if (loginConnect != null) loginConnect.close();
        myDialog.dismiss();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        circleLoad.close();
    }

    public void startConnect() {
        if (MainApplication.availableDay > 0) {
            socketClient = new NetConnect(handler, MainApplication.testDataUrl);
        } else if (!MainApplication.loginFlag) {
            loginConnect = new NetSingleConnect(handler, MainApplication.loginUrl);
        } else {
            Toast.makeText(MainApplication.context, R.string.tip_recharge, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            callback = (TestResultLorryCallback) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException("Activity must implement TestResultLorryCallback.");
        }
    }

    public interface TestResultLorryCallback {
        void testResultLorryNext(int position);
    }

    public void toast(int id) {
        Toast.makeText(getActivity(), id, Toast.LENGTH_SHORT).show();
    }
}
