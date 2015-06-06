package com.thinkdo.fragment;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.thinkdo.activity.R;
import com.thinkdo.db.CustomDbUtil;
import com.thinkdo.entity.OperOftenDataTotalModel;
import com.thinkdo.entity.ReferData;
import com.thinkdo.entity.UnitEnum;
import com.thinkdo.entity.ValuesPair;
import com.thinkdo.view.BarDefAdd;

/**
 * Created by Administrator on 2015/5/21.
 */
public class DefCarDataFragment extends Fragment {
    private OperOftenDataTotalModel carInfo;
    private int toeUnit = 2, unit = 2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_def_add_data, container, false);
        init(view);
        return view;
    }

    private void init(View view) {
        BarDefAdd title = (BarDefAdd) view.findViewById(R.id.barDefAdd_title);
        final BarDefAdd frontTotalToe = (BarDefAdd) view.findViewById(R.id.barDefAdd_frontTotalToe);
        final BarDefAdd leftFrontCamber = (BarDefAdd) view.findViewById(R.id.barDefAdd_leftFrontCamber);
        final BarDefAdd rightFrontCamber = (BarDefAdd) view.findViewById(R.id.barDefAdd_rightFrontCamber);
        final BarDefAdd rearTotalToe = (BarDefAdd) view.findViewById(R.id.barDefAdd_rearTotalToe);
        final BarDefAdd leftRearCamber = (BarDefAdd) view.findViewById(R.id.barDefAdd_leftRearCamber);
        final BarDefAdd rightRearCamber = (BarDefAdd) view.findViewById(R.id.barDefAdd_rightRearCamber);
        final BarDefAdd leftCaster = (BarDefAdd) view.findViewById(R.id.barDefAdd_leftCaster);
        final BarDefAdd rightCaster = (BarDefAdd) view.findViewById(R.id.barDefAdd_rightCaster);
        final BarDefAdd leftKpi = (BarDefAdd) view.findViewById(R.id.barDefAdd_leftKpi);
        final BarDefAdd rightKpi = (BarDefAdd) view.findViewById(R.id.barDefAdd_rightKpi);
        final BarDefAdd wheelbase = (BarDefAdd) view.findViewById(R.id.barDefAdd_wheelbase);
        final BarDefAdd frontWheel = (BarDefAdd) view.findViewById(R.id.barDefAdd_front_wheel);
        final BarDefAdd rearWheel = (BarDefAdd) view.findViewById(R.id.barDefAdd_rearWheel);

        title.setContextTextColor(Color.BLACK);
        title.setMinText(R.string.min);
        title.setMaxText(R.string.max);
        wheelbase.setMaxEnabled(false);
        frontWheel.setMaxEnabled(false);
        rearWheel.setMaxEnabled(false);

        Button btn = (Button) view.findViewById(R.id.btn_next);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(getActivity())
                        .setTitle(R.string.sure)
                        .setMessage(R.string.tip_add_data)
                        .setCancelable(false)
                        .setNegativeButton(R.string.cancel, null)
                        .setPositiveButton(R.string.sure, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                if (carInfo != null) {
                                    new Thread(new Runnable() {
                                        @Override
                                        public void run() {
                                            ReferData data = new ReferData();

                                            data.setManId(carInfo.getManId());
                                            data.setVehicleId(carInfo.getVehicleId());
                                            data.setStartYear(carInfo.getStartYear());
                                            data.setEndYear(carInfo.getEndYear());

                                            data.setFrontTotalToe(frontTotalToe.getAllValues(UnitEnum.getUnitFromValue(toeUnit)));
                                            data.setLeftFrontToe(data.getFrontTotalToe().generateSingleToe());
                                            data.setRightFrontToe(data.getLeftFrontToe().copy());

                                            data.setLeftFrontCamber(leftFrontCamber.getAllValues(UnitEnum.getUnitFromValue(unit)));
                                            data.setRightFrontCamber(rightFrontCamber.getAllValues(UnitEnum.getUnitFromValue(unit)));

                                            data.setLeftCaster(leftCaster.getAllValues(UnitEnum.getUnitFromValue(unit)));
                                            data.setRightCaster(rightCaster.getAllValues(UnitEnum.getUnitFromValue(unit)));

                                            data.setLeftKpi(leftKpi.getAllValues(UnitEnum.getUnitFromValue(unit)));
                                            data.setRightKpi(rightKpi.getAllValues(UnitEnum.getUnitFromValue(unit)));

                                            data.setRearTotalToe(rearTotalToe.getAllValues(UnitEnum.getUnitFromValue(toeUnit)));
                                            data.setLeftRearToe(data.getRearTotalToe().generateSingleToe());
                                            data.setRightRearToe(data.getLeftRearToe().copy());

                                            data.setLeftRearCamber(leftRearCamber.getAllValues(UnitEnum.getUnitFromValue(unit)));
                                            data.setRightRearCamber(rightRearCamber.getAllValues(UnitEnum.getUnitFromValue(unit)));

                                            data.setWheelbase(new ValuesPair(wheelbase.getMinText(), wheelbase.getMinText(), wheelbase.getMinText()));
                                            data.setFrontWheel(new ValuesPair(frontWheel.getMinText(), frontWheel.getMinText(), frontWheel.getMinText()));
                                            data.setRearWheel(new ValuesPair(rearWheel.getMinText(), rearWheel.getMinText(), frontWheel.getMinText()));

                                            //�保存到数据库��ݵ����ݿ���
                                            CustomDbUtil dbUtil = new CustomDbUtil();
                                            if (dbUtil.insertVehicleInfo(carInfo) && dbUtil.insertVehicleData(data)) {
                                                getActivity().runOnUiThread(new Runnable() {

                                                    @Override
                                                    public void run() {
                                                        Toast.makeText(getActivity(), R.string.tip_data_success_save, Toast.LENGTH_SHORT).show();
                                                        getActivity().finish();
                                                    }
                                                });
                                            }
                                        }
                                    }).start();
                                }

                            }
                        }).create().show();
            }
        });
    }

    public void setAllParam(OperOftenDataTotalModel carInfo, int unit, int toeUnit) {
        this.carInfo = carInfo;
        this.unit = unit;
        this.toeUnit = toeUnit;
    }
}
