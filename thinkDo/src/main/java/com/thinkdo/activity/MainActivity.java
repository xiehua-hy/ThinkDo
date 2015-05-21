package com.thinkdo.activity;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.RadioButton;
import android.widget.TextView;

import com.thinkdo.db.CustomDbUtil;
import com.thinkdo.db.VehicleDbUtil;
import com.thinkdo.entity.GloVariable;
import com.thinkdo.entity.ReferData;
import com.thinkdo.entity.SpecialParams;
import com.thinkdo.fragment.DataPrintFragment;
import com.thinkdo.fragment.FrontAxletShowFragment;
import com.thinkdo.fragment.KingpinFragment;
import com.thinkdo.fragment.KingpinFragment.KinPingCallback;
import com.thinkdo.fragment.ManufacturerFragment;
import com.thinkdo.fragment.ManufacturerFragment.ManufacturerCallback;
import com.thinkdo.fragment.PickCarFragment;
import com.thinkdo.fragment.PickCarFragment.VehicleCallbacks;
import com.thinkdo.fragment.PickCusCarFragment;
import com.thinkdo.fragment.PickCusCarFragment.CusManufacturerCallback;
import com.thinkdo.fragment.PushCarFragment;
import com.thinkdo.fragment.RearAxleShowFragment;
import com.thinkdo.fragment.TestResultFragment;
import com.thinkdo.fragment.VehicleInfoShow;
import com.thinkdo.fragment.VehicleInfoShow.VehicleInfoCallback;

public class MainActivity extends Activity implements OnClickListener, ManufacturerCallback, VehicleCallbacks, CusManufacturerCallback, VehicleInfoCallback,
        KinPingCallback {
    private int preCheckedRadio = R.id.radio_pick;
    private ReferData referData;
    private int weightHeightLevelFlag;

    public final int WeightFlag = 0x01;
    public final int HeightFlag = 0x02;
    public final int LevelFlag = 0x04;
    public static final int searchFlag = 0x08;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        TextView back = (TextView) findViewById(R.id.tv_back);
        back.setOnClickListener(this);

        RadioButton rb = (RadioButton) findViewById(R.id.radio_pick);
        rb.setOnClickListener(this);

        rb = (RadioButton) findViewById(R.id.radio_pull);
        rb.setOnClickListener(this);

        rb = (RadioButton) findViewById(R.id.radio_kingpin);
        rb.setOnClickListener(this);

        rb = (RadioButton) findViewById(R.id.radio_fast);
        rb.setOnClickListener(this);

        rb = (RadioButton) findViewById(R.id.radio_rear);
        rb.setOnClickListener(this);

        rb = (RadioButton) findViewById(R.id.radio_front);
        rb.setOnClickListener(this);

        rb = (RadioButton) findViewById(R.id.radio_print);
        rb.setOnClickListener(this);

        fragmentCommit(new ManufacturerFragment());

    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.tv_back) {
            finish();
        } else {
            radioButtonCheckedChange(v.getId());
        }
    }

    public void radioButtonCheckedChange(int checkId) {
        if (preCheckedRadio == checkId) return;

        switch (checkId) {
            case R.id.radio_pick:
                fragmentCommit(new ManufacturerFragment());
                break;
            case R.id.radio_pull:
                fragmentCommit(new PushCarFragment());
                break;
            case R.id.radio_fast:
                fragmentCommit(new TestResultFragment());
                break;
            case R.id.radio_kingpin:
                fragmentCommit(new KingpinFragment());
                break;
            case R.id.radio_rear:
                fragmentCommit(new RearAxleShowFragment());
                break;
            case R.id.radio_front:
                fragmentCommit(new FrontAxletShowFragment());
                break;
            case R.id.radio_print:
                fragmentCommit(new DataPrintFragment());
                break;
        }

        changeRadioBtnTextColor(checkId);
        preCheckedRadio = checkId;
    }

    public void changeRadioBtnTextColor(int checkId) {
        Resources res = getResources();
        if (res == null) return;
        RadioButton rb = (RadioButton) findViewById(preCheckedRadio);
        rb.setTextColor(res.getColor(R.color.font_black));

        rb = (RadioButton) findViewById(checkId);
        rb.setTextColor(res.getColor(R.color.font_red_holo));
    }

    public void fragmentCommit(Fragment fragment) {
        getFragmentManager().beginTransaction().replace(R.id.frameLayout, fragment).commit();
    }

    @Override
    public void onManufacturerSelected(String manId, String manInfo, int dbIndex) {
        if (manId.equals("0") && dbIndex == GloVariable.cusdb) {
            //进入自定义车型的界面
            fragmentCommit(new PickCusCarFragment());
            return;
        }
        referData = new ReferData();
        referData.setManId(manId);
        referData.setManInfo(manInfo);

        PickCarFragment fragment = new PickCarFragment();
        fragment.setParams(manId, manInfo, null, dbIndex);
        fragmentCommit(fragment);
    }

    @Override
    public void onCusManSelected(String manId, String manInfo, int dbIndex) {
        onManufacturerSelected(manId, manInfo, dbIndex);
    }

    @Override
    public void onVehicleSelected(final String manId, final String manInfo, final String vehicleID, final String year, final int dbIndex) {

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                //自定义数据
                if (dbIndex == GloVariable.cusdb) {
                    CustomDbUtil util = new CustomDbUtil();
                    referData = util.queryReferData(vehicleID);
                    if (referData != null) {
                        referData.setManId(manId);
                        referData.setManInfo(manInfo);
                        referData.setVehicleId(vehicleID);
                        referData.setRealYear(year);
                    }
                    startWeightHeightLevel(null);
                    return;
                }

                VehicleDbUtil util = new VehicleDbUtil();
                referData = util.queryReferData(vehicleID);

                if (referData != null) {
                    referData.setManId(manId);
                    referData.setManInfo(manInfo);
                    referData.setVehicleId(vehicleID);
                    referData.setRealYear(year);
                }

                SpecialParams specialParams = util.querySpecParam(vehicleID);
                weightHeightLevelFlag = 0;
                if (specialParams != null) {
                    if (specialParams.getWeightParam() != null)
                        weightHeightLevelFlag |= WeightFlag;
                    if (specialParams.getHeightParam() != null)
                        weightHeightLevelFlag |= HeightFlag;
                    if (specialParams.getLevelParam() != null)
                        weightHeightLevelFlag |= LevelFlag;
                }

                startWeightHeightLevel(specialParams);
            }
        };

        new Thread(runnable, "specialThread").start();
    }

    @Override
    public void onVehicleInfoNext() {
        RadioButton rb = (RadioButton) findViewById(R.id.radio_pull);
        rb.performClick();
    }

    @Override
    public void kinPingNext() {
        RadioButton rb = (RadioButton) findViewById(R.id.radio_fast);
        rb.performClick();
    }

    private void startWeightHeightLevel(SpecialParams specialParams) {
        if (weightHeightLevelFlag == 0) {
            VehicleInfoShow fragment = new VehicleInfoShow();
            fragment.setReferData(referData.copy());
            fragmentCommit(fragment);
            return;
        }

        Intent intent = null;
        int questCode = 0;
        if ((weightHeightLevelFlag & WeightFlag) != 0) {
            intent = new Intent(this, WeightActivity.class);
            intent.putExtra("SpecialParams", specialParams);
            questCode = WeightFlag;
        } else if ((weightHeightLevelFlag & HeightFlag) != 0) {
            intent = new Intent(this, HeightActivity.class);
            intent.putExtra("SpecialParams", specialParams);
            questCode = HeightFlag;
        } else if ((weightHeightLevelFlag & LevelFlag) != 0) {
            intent = new Intent(this, LevelActivity.class);
            intent.putExtra("SpecialParams", specialParams);
            questCode = LevelFlag;
        }

        if (intent != null)
            startActivityForResult(intent, questCode);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == WeightFlag && resultCode == RESULT_OK && data != null) {
            SpecialParams specialParams = (SpecialParams) data.getSerializableExtra("SpecialParams");
            weightHeightLevelFlag &= 0xFF - WeightFlag;
            startWeightHeightLevel(specialParams);
        } else if (requestCode == HeightFlag && resultCode == RESULT_OK && data != null) {
            SpecialParams specialParams = (SpecialParams) data.getSerializableExtra("SpecialParams");
            referData.combine((ReferData) data.getSerializableExtra("ReferData"));
            weightHeightLevelFlag &= 0xFF - HeightFlag;
            startWeightHeightLevel(specialParams);
        } else if (requestCode == LevelFlag && resultCode == RESULT_OK && data != null) {
            SpecialParams specialParams = (SpecialParams) data.getSerializableExtra("SpecialParams");
            referData.combine((ReferData) data.getSerializableExtra("ReferData"));
            weightHeightLevelFlag &= 0xFF - LevelFlag;
            startWeightHeightLevel(specialParams);
        } else if (requestCode == searchFlag && resultCode == RESULT_OK && data != null) {
            String manId = data.getStringExtra(SearchActivity.manufacturerId);
            String manInfo = data.getStringExtra(SearchActivity.manufacturerInfo);
            String pyIndex = data.getStringExtra(SearchActivity.pyIndex);
            int dbIndex = data.getIntExtra(SearchActivity.dbIndex, GloVariable.stadb);

            PickCarFragment fragment = new PickCarFragment();
            fragment.setParams(manId, manInfo, pyIndex, dbIndex);
            fragmentCommit(fragment);
        }
    }
}
