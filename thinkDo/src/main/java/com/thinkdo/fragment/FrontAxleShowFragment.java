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

public class FrontAxleShowFragment extends Fragment {
    private WindowRealTime leftFrontCamber, leftFrontToe, leftCaster,
            frontTotalToe, rightFrontToe, rightCaster, rightFrontCamber;
    private ButtonRaise raiseBtn;

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


    }


}
