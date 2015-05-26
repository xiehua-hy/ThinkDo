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
 * Created by Administrator on 2015/4/30.
 */
public class RearAxleShowFragment extends Fragment {
    private WindowRealTime rearTotalToe, leftRearCamber, leftRearToe, rightRearToe,rightRearCamber;
    private ButtonRaise raiseBtn;

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
        loadData();
    }

    private void loadData() {
        if (MainActivity.referData == null) return;
        ReferData copy = MainActivity.referData.copy();
        copy.unitConvert();

        rearTotalToe.setAllValues(copy.getRearTotalToe());
        leftRearToe.setAllValues(copy.getLeftRearToe());
        rightRearToe.setAllValues(copy.getRightRearToe(), false);

        leftRearCamber.setAllValues(copy.getLeftRearCamber());
        rightRearCamber.setAllValues(copy.getRightRearCamber(), false);
    }


}
