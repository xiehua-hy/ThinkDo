package com.thinkdo.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.thinkdo.activity.R;
import com.thinkdo.db.VehicleDbUtil;
import com.thinkdo.entity.ReferData;
import com.thinkdo.view.BarReferData;

/**
 * Created by xh on 15/5/10.
 */
public class VehicleInfoShow extends Fragment {
    private VehicleInfoCallback callback;
    public static ReferData data;
    private String vehicleId;
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_vehicle_info_show, container, false);
        new DataLoadThread().start();
        return view;
    }

    private void initView() {
        BarReferData bar;

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
        bar.setLeftMinText(data.getMaxThrust());
    }

    public void setVehicleId(String vehicleId) {
        this.vehicleId = vehicleId;
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
        void vehicleInfoNext();
    }

    class DataLoadThread extends Thread {
        @Override
        public void run() {
            data = new VehicleDbUtil().queryReferData(vehicleId);

            getActivity().runOnUiThread(
                    new Runnable() {
                        @Override
                        public void run() {
                            initView();
                        }
                    }
            );
        }
    }
}
