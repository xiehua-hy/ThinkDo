package com.thinkdo.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.thinkdo.activity.R;
import com.thinkdo.application.MainApplication;
import com.thinkdo.entity.ReferData;
import com.thinkdo.net.NetQuest;
import com.thinkdo.view.BarPrint;

public class DataPrintFragment extends Fragment implements View.OnClickListener {
    private DataPrintCallback callback;
    private int saveBtnVisible = View.VISIBLE;
    private boolean connect = true;
    private ReferData data;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (connect) new NetQuest(MainApplication.printUrl);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_data_print, container, false);
        init(rootView);

        Button btn = (Button) rootView.findViewById(R.id.btn_save);
        btn.setVisibility(saveBtnVisible);
        btn.setOnClickListener(this);
        btn = (Button) rootView.findViewById(R.id.btn_print);
        btn.setOnClickListener(this);
        return rootView;
    }

    private void init(View view) {
        BarPrint item = (BarPrint) view.findViewById(R.id.barPrint_param);
        item.setBeforeText(R.string.before);
        item.setAfterText(R.string.after);
        item.setMinText(R.string.min);
        item.setMaxText(R.string.max);

        if (data == null) return;
        data.unitConvert();
        item = (BarPrint) view.findViewById(R.id.barPrint_frontTotalToe);
        item.setAllValues(data.getFrontTotalToe());

        item = (BarPrint) view.findViewById(R.id.barPrint_leftFrontToe);
        item.setAllValues(data.getLeftFrontToe());

        item = (BarPrint) view.findViewById(R.id.barPrint_rightFrontToe);
        item.setAllValues(data.getRightFrontToe());

        item = (BarPrint) view.findViewById(R.id.barPrint_leftFrontCamber);
        item.setAllValues(data.getLeftFrontCamber());

        item = (BarPrint) view.findViewById(R.id.barPrint_rightFrontCamber);
        item.setAllValues(data.getRightFrontCamber());

        item = (BarPrint) view.findViewById(R.id.barPrint_leftCaster);
        item.setAllValues(data.getLeftCaster());

        item = (BarPrint) view.findViewById(R.id.barPrint_rightCaster);
        item.setAllValues(data.getRightCaster());

        item = (BarPrint) view.findViewById(R.id.barPrint_leftKpi);
        item.setAllValues(data.getLeftKpi());

        item = (BarPrint) view.findViewById(R.id.barPrint_rightKpi);
        item.setAllValues(data.getRightKpi());

        item = (BarPrint) view.findViewById(R.id.barPrint_rearTotalToe);
        item.setAllValues(data.getRearTotalToe());

        item = (BarPrint) view.findViewById(R.id.barPrint_leftRearToe);
        item.setAllValues(data.getLeftRearToe());

        item = (BarPrint) view.findViewById(R.id.barPrint_rightRearToe);
        item.setAllValues(data.getRightRearToe());

        item = (BarPrint) view.findViewById(R.id.barPrint_leftRearCamber);
        item.setAllValues(data.getLeftRearCamber());

        item = (BarPrint) view.findViewById(R.id.barPrint_rightRearCamber);
        item.setAllValues(data.getRightRearCamber());

        item = (BarPrint) view.findViewById(R.id.barPrint_thrust);
        item.setAllValues(data.getMaxThrust());

        item = (BarPrint) view.findViewById(R.id.barPrint_wheelbaseDiff);
        item.setAllValues(data.getWheelbaseDiff());

        item = (BarPrint) view.findViewById(R.id.barPrint_wheelDiff);
        item.setAllValues(data.getWheelDiff());

        item = (BarPrint) view.findViewById(R.id.barPrint_leftInclude);
        item.setAllValues(data.getLeftIncludeAngle());

        item = (BarPrint) view.findViewById(R.id.barPrint_rightInclude);
        item.setAllValues(data.getRightIncludeAngle());

        item = (BarPrint) view.findViewById(R.id.barPrint_leftTurn);
        item.setAllValues(data.getLeftTurnAngle());

        item = (BarPrint) view.findViewById(R.id.barPrint_rightTurn);
        item.setAllValues(data.getRightTurnAngle());

        item = (BarPrint) view.findViewById(R.id.barPrint_leftMaxTurn);
        item.setAllValues(data.getLeftMaxTurnAngle());

        item = (BarPrint) view.findViewById(R.id.barPrint_rightMaxTurn);
        item.setAllValues(data.getRightMaxTurnAngle());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_save:
                if (callback != null) callback.dataPrintNext(0);
                break;
            case R.id.btn_print:
                if (callback != null) callback.dataPrintNext(1);
                break;
        }
    }

    public void setReferData(ReferData referData) {
        this.data = referData;
    }

    public void setSaveBtnVisible(int saveBtnVisible) {
        this.saveBtnVisible = saveBtnVisible;
    }


    public void setConnect(boolean connect) {
        this.connect = connect;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            callback = (DataPrintCallback) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException("Activity must implement DataPrintCallback.");
        }
    }

    public interface DataPrintCallback {
        /**
         * @param position
         *        0 save   <br/>
         *       1 print
         *
         * **/
        void dataPrintNext(int position);
    }

}
