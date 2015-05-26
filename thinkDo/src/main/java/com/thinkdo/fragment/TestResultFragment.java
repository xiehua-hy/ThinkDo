package com.thinkdo.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.thinkdo.activity.MainActivity;
import com.thinkdo.activity.R;
import com.thinkdo.entity.ReferData;
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

        raiseBtn = (ButtonRaise) view.findViewById(R.id.btn_raise);
        loadData();
    }

    private void loadData() {
        if (MainActivity.referData == null) return;
        ReferData copy = MainActivity.referData.copy();
        copy.unitConvert();

        frontTotalToe.setAllValues(copy.getFrontTotalToe());
        leftFrontToe.setAllValues(copy.getLeftFrontToe());
        rightFrontToe.setAllValues(copy.getRightFrontToe(), false);

        leftFrontCamber.setAllValues(copy.getLeftFrontCamber());
        rightFrontCamber.setAllValues(copy.getRightFrontCamber(), false);

        leftCaster.setAllValues(copy.getLeftCaster());
        rightCaster.setAllValues(copy.getRightCaster());

        leftKPI.setAllValues(copy.getLeftKpi());
        rightKPI.setAllValues(copy.getRightKpi(), false);

        rearTotalToe.setAllValues(copy.getRearTotalToe());
        leftRearToe.setAllValues(copy.getLeftRearToe());
        rightRearToe.setAllValues(copy.getRightRearToe(), false);

        leftRearCamber.setAllValues(copy.getLeftRearCamber());
        rightRearCamber.setAllValues(copy.getRightRearCamber(), false);

        thrustAngle.setMiddleText(copy.getMaxThrust());
    }
}
