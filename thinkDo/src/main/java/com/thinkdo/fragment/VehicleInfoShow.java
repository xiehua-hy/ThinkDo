package com.thinkdo.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.thinkdo.activity.R;
import com.thinkdo.entity.ReferData;
import com.thinkdo.view.BarReferData;

public class VehicleInfoShow extends Fragment {
    private VehicleInfoCallback callback;
    private ReferData data;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_vehicle_info_show, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        if (data == null) return;

        data.unitConvert();
        BarReferData bar;
        TextView tv = (TextView) view.findViewById(R.id.tv_title);
        tv.setText(String.format("%s-%s-%s", data.getManInfo(), data.getRealYear(), data.getVehicleInfo()));

        bar = (BarReferData) view.findViewById(R.id.bar_frontTotalToe);
        bar.setAllValues(data.getFrontTotalToe(), null);

        bar = (BarReferData) view.findViewById(R.id.bar_frontSingleToe);
        bar.setAllValues(data.getLeftFrontToe(), data.getRightFrontToe());

        bar = (BarReferData) view.findViewById(R.id.bar_frontCamber);
        bar.setAllValues(data.getLeftFrontCamber(), data.getRightFrontCamber());

        bar = (BarReferData) view.findViewById(R.id.bar_caster);
        bar.setAllValues(data.getLeftCaster(), data.getRightCaster());

        bar = (BarReferData) view.findViewById(R.id.bar_kpi);
        bar.setAllValues(data.getLeftKpi(), data.getRightKpi());

        bar = (BarReferData) view.findViewById(R.id.bar_rearTotalToe);
        bar.setAllValues(data.getRearTotalToe(), null);

        bar = (BarReferData) view.findViewById(R.id.bar_rearSingleToe);
        bar.setAllValues(data.getLeftRearToe(), data.getRightRearToe());

        bar = (BarReferData) view.findViewById(R.id.bar_rearCamber);
        bar.setAllValues(data.getLeftRearCamber(), data.getRightRearCamber());

        bar = (BarReferData) view.findViewById(R.id.bar_maxThrust);
        if (data.getMaxThrust() != null) bar.setLeftMinText(data.getMaxThrust().getMid());

        bar = (BarReferData) view.findViewById(R.id.bar_wheelbase);
        if (data.getWheelbase() != null) bar.setLeftMinText(data.getWheelbase().getMid());

        bar = (BarReferData) view.findViewById(R.id.bar_frontWheel);
        if (data.getFrontWheel() != null) bar.setLeftMinText(data.getFrontWheel().getMid());

        bar = (BarReferData) view.findViewById(R.id.bar_rearWheel);
        if (data.getRearWheel() != null) bar.setLeftMinText(data.getRearWheel().getMid());

        Button btn = (Button) view.findViewById(R.id.btn_next);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (callback != null)
                    callback.onVehicleInfoNext();
            }
        });
    }

    public void setReferData(ReferData referData) {
        data = referData;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            callback = (VehicleInfoCallback) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException("Activity must implement VehicleInfoCallback.");
        }
    }

    public interface VehicleInfoCallback {
        void onVehicleInfoNext();
    }
}
