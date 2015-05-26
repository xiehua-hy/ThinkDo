package com.thinkdo.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.thinkdo.activity.R;
import com.thinkdo.view.ButtonRaise;
import com.thinkdo.view.WindowRealTime;

/**
 * Created by Administrator on 2015/5/7.
 */
public class FastTestFragment extends Fragment {
    private WindowRealTime leftFrontCamber, leftRearCamber, leftFrontToe, leftRearToe,
            frontTotalToe, rearTotalToe, rightFrontToe, rightRearToe,
            rightFrontCamber, rightRearCamber;
    private ButtonRaise raiseBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_fast_test, container, false);
        init(rootView);
        return rootView;
    }

    private void init(View view) {
        frontTotalToe = (WindowRealTime) view.findViewById(R.id.window_frontTotalToe);
        rearTotalToe = (WindowRealTime) view.findViewById(R.id.window_rearTotalToe);

        leftFrontToe = (WindowRealTime) view.findViewById(R.id.window_leftFrontToe);
        rightFrontToe = (WindowRealTime) view.findViewById(R.id.window_rightFrontToe);

        leftFrontCamber = (WindowRealTime) view.findViewById(R.id.window_leftFrontCamber);
        rightFrontCamber = (WindowRealTime) view.findViewById(R.id.window_rightFrontCamber);

        leftRearCamber = (WindowRealTime) view.findViewById(R.id.window_leftRearCamber);
        rightRearCamber = (WindowRealTime) view.findViewById(R.id.window_rightRearCamber);

        leftRearToe = (WindowRealTime) view.findViewById(R.id.window_leftRearToe);
        rightRearToe = (WindowRealTime) view.findViewById(R.id.window_rightRearToe);
        raiseBtn = (ButtonRaise) view.findViewById(R.id.btn_raise);
    }
}
