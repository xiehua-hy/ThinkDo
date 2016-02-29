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

import com.thinkdo.application.MainApplication;
import com.thinkdo.db.DbUtil;
import com.thinkdo.entity.LorryDataItem;
import com.thinkdo.entity.LorryReferData;
import com.thinkdo.fragment.DataPrintLorryFragment.DataPrintCallback;
import com.thinkdo.fragment.DataPrintLorryFragment;
import com.thinkdo.fragment.FrontAxleLorryFragment;
import com.thinkdo.fragment.FrontAxleLorryFragment.FrontAxleLorryCallback;
import com.thinkdo.fragment.KingpinFragment;
import com.thinkdo.fragment.KingpinFragment.KinPingCallback;
import com.thinkdo.fragment.ManufacturerFragment;
import com.thinkdo.fragment.ManufacturerFragment.ManufacturerCallback;
import com.thinkdo.fragment.PickCarFragment;
import com.thinkdo.fragment.PickCarFragment.VehicleCallbacks;
import com.thinkdo.fragment.PickCusCarFragment;
import com.thinkdo.fragment.PickCusCarFragment.CusManufacturerCallback;
import com.thinkdo.fragment.PushCarFragment;
import com.thinkdo.fragment.PushCarFragment.PushCarCallback;
import com.thinkdo.fragment.TestResultLorryFragment;
import com.thinkdo.fragment.TestResultLorryFragment.TestResultLorryCallback;
import com.thinkdo.fragment.VehicleInfoShow;
import com.thinkdo.fragment.VehicleInfoShow.VehicleInfoCallback;
import com.thinkdo.net.NetQuest;
import com.thinkdo.net.SocketClient;

public class MainActivityLorry extends Activity implements OnClickListener, ManufacturerCallback, VehicleCallbacks, CusManufacturerCallback, VehicleInfoCallback,
        KinPingCallback, PushCarCallback, DataPrintCallback, FrontAxleLorryCallback, TestResultLorryCallback {

    private int preCheckedRadio = R.id.radio_pick;
    public static LorryReferData lorryReferData;
    public static LorryDataItem printData;

    private boolean backChoice = true;
    private boolean transFlag = false;

    public static final int searchFlag = 0x08;
    private int redirect = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_main_lorry);
        init();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (getIntent() != null) {
            redirect = getIntent().getIntExtra("position", -1);
        }
    }

    private void init() {
        if (getIntent() != null) {
            redirect = getIntent().getIntExtra("position", -1);
        }
        TextView back = (TextView) findViewById(R.id.tv_back);
        back.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (backChoice) {
                    finish();
                } else {
                    backChoice = true;
                    fragmentCommit(new ManufacturerFragment());
                }
            }
        });

        RadioButton rb = (RadioButton) findViewById(R.id.radio_pick);
        rb.setOnClickListener(this);

        rb = (RadioButton) findViewById(R.id.radio_push);
        rb.setOnClickListener(this);

        rb = (RadioButton) findViewById(R.id.radio_kingpin);
        rb.setOnClickListener(this);

        rb = (RadioButton) findViewById(R.id.radio_fast);
        rb.setOnClickListener(this);

        rb = (RadioButton) findViewById(R.id.radio_front);
        rb.setOnClickListener(this);

        rb = (RadioButton) findViewById(R.id.radio_print);
        rb.setOnClickListener(this);

        fragmentCommit(new ManufacturerFragment());
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        radioButtonCheckedChange(id);
    }

    @Override
    protected void onResume() {
        super.onResume();
        transFlag = true;
        if (redirect != -1) {
            synch(redirect);
            redirect = -1;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        transFlag = false;
    }


    @Override
    public void finish() {
        super.finish();
        lorryReferData = null;
        printData = null;
        SaveOrPrintLorryActivity.clear();
        new NetQuest(MainApplication.homeUrl);
    }

    public void radioButtonCheckedChange(int checkId) {
        if (preCheckedRadio == checkId) return;

        switch (checkId) {
            case R.id.radio_pick:
                fragmentCommit(new ManufacturerFragment());
                break;
            case R.id.radio_push:
                fragmentCommit(new PushCarFragment());
                break;
            case R.id.radio_fast:
                fragmentCommit(new TestResultLorryFragment());
                break;
            case R.id.radio_front:
                fragmentCommit(new FrontAxleLorryFragment());
                break;
            case R.id.radio_kingpin:
                fragmentCommit(new KingpinFragment());
                break;
            case R.id.radio_print:
                DataPrintLorryFragment printFragment = new DataPrintLorryFragment();
                if (printData != null) printFragment.setLorryData(printData.copy());
                fragmentCommit(printFragment);
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
        if (manId.equals("0") && dbIndex == MainApplication.cusdb) {
            //进入自定义车型的界面
            fragmentCommit(new PickCusCarFragment());
            return;
        }

        backChoice = false;

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
        backChoice = true;
        SaveOrPrintLorryActivity.clear();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {

                lorryReferData = new LorryReferData();
                printData = null;
                DbUtil dbUtil = new DbUtil();
                lorryReferData.initWithReferData(dbUtil.queryReferData(vehicleID, dbIndex));

                if (lorryReferData != null) {
                    lorryReferData.setManId(manId);
                    lorryReferData.setManInfo(manInfo);
                    lorryReferData.setVehicleId(vehicleID);
                    lorryReferData.setRealYear(year);

                    final LorryReferData tem = (LorryReferData)lorryReferData.copy();

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            SocketClient client = new SocketClient(null, false);
                            client.send(MainApplication.synchCar, tem.getSynchData());
                        }
                    }).start();
                }


                VehicleInfoShow fragment = new VehicleInfoShow();
                fragment.setReferData(lorryReferData.copy());
                fragmentCommit(fragment);
            }
        };

        new Thread(runnable, "specialThread").start();
    }

    @Override
    public void onVehicleInfoNext() {
        RadioButton rb = (RadioButton) findViewById(R.id.radio_push);
        rb.performClick();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == searchFlag && resultCode == RESULT_OK && data != null) {
            String manId = data.getStringExtra(SearchActivity.manufacturerId);
            String manInfo = data.getStringExtra(SearchActivity.manufacturerInfo);
            String pyIndex = data.getStringExtra(SearchActivity.pyIndex);
            int dbIndex = data.getIntExtra(SearchActivity.dbIndex, MainApplication.stadb);

            PickCarFragment fragment = new PickCarFragment();
            fragment.setParams(manId, manInfo, pyIndex, dbIndex);
            fragmentCommit(fragment);
        }
    }

    @Override
    public void pushCarNext(int position) {
        synch(position);
    }

    @Override
    public void kinPingNext(int position) {
        synch(position);
    }

    @Override
    public void frontAxleLorryNext(int position) {
        synch(position);
    }

    @Override
    public void testResultLorryNext(int position) {
        synch(position);
    }

    @Override
    public void dataPrintNext(int position) {
//        Intent it = new Intent(this, SaveOrPrintActivity.class);
//        if (position == 1) it.putExtra("print", true);
//        startActivity(it);
        Intent it = new Intent(this, SaveOrPrintLorryActivity.class);
        if (position == 1) it.putExtra("print", true);
        startActivity(it);
    }

    private void synch(int i) {
        int radioId = -1;

        switch (i) {
            case MainApplication.pushcarUrl:
                radioId = R.id.radio_push;
                break;

            case MainApplication.kingpinUrl:
                radioId = R.id.radio_kingpin;
                break;

            case MainApplication.fastTestUrl:
                radioId = R.id.radio_fast;
                break;

            case MainApplication.testDataUrl:
                radioId = R.id.radio_fast;
                break;

            case MainApplication.frontShowUrl:
                radioId = R.id.radio_front;
                break;

            case MainApplication.printUrl:
                radioId = R.id.radio_print;
                break;

            case MainApplication.samplePictureUrl:
                Intent intent = new Intent(this, MaintenanceActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(intent);
                break;

            case MainApplication.specialTestUrl:
                intent = new Intent(this, SpecialTestActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(intent);

            case MainApplication.homeUrl:
                Intent it = new Intent(this, MenuActivity.class);
                startActivity(it);
                break;
        }

        if (radioId != -1 && transFlag) {
            RadioButton radioBtn = (RadioButton) findViewById(radioId);
            radioBtn.performClick();
        }
    }

}
