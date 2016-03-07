package com.thinkdo.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.RadioButton;
import android.widget.TextView;

import com.thinkdo.application.MainApplication;
import com.thinkdo.db.DbUtil;
import com.thinkdo.entity.ReferData;
import com.thinkdo.entity.SpecialParams;
import com.thinkdo.fragment.DataPrintFragment;
import com.thinkdo.fragment.DataPrintFragment.DataPrintCallback;
import com.thinkdo.fragment.FrontAxleShowFragment;
import com.thinkdo.fragment.FrontAxleShowFragment.FrontAxleCallback;
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
import com.thinkdo.fragment.RearAxleShowFragment;
import com.thinkdo.fragment.RearAxleShowFragment.RearAxleCallback;
import com.thinkdo.fragment.TestResultFragment;
import com.thinkdo.fragment.TestResultFragment.TestResultCallback;
import com.thinkdo.fragment.VehicleInfoShow;
import com.thinkdo.fragment.VehicleInfoShow.VehicleInfoCallback;
import com.thinkdo.net.NetQuest;
import com.thinkdo.net.SocketClient;

public class MainActivity extends Activity implements OnClickListener, ManufacturerCallback, VehicleCallbacks, CusManufacturerCallback, VehicleInfoCallback,
        KinPingCallback, RearAxleCallback, FrontAxleCallback, TestResultCallback, PushCarCallback, DataPrintCallback {
    private int preCheckedRadio = R.id.radio_pick;
    public static ReferData referData;
    private int weightHeightLevelFlag;
    private boolean autoDown = true;
    private boolean backChoice = true;
    private boolean transFlag = false;

    public final int WeightFlag = 0x01;
    public final int HeightFlag = 0x02;
    public final int LevelFlag = 0x04;
    public static final int searchFlag = 0x08;
    private int redirect = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_main);
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
                if (!autoDown) {
                    warn();
                } else if (backChoice) {
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
        int id = v.getId();
        if (!autoDown && id != R.id.radio_fast && id != R.id.radio_rear && id != R.id.radio_front && id != R.id.radio_print) {
            warn();
            return;
        }
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
    public void onBackPressed() {
        if (autoDown) finish();
        else warn();
    }

    @Override
    public void finish() {
        super.finish();
        referData = null;
        SaveOrPrintActivity.clear();
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
                fragmentCommit(new TestResultFragment());
                break;
            case R.id.radio_kingpin:
                fragmentCommit(new KingpinFragment());
                break;
            case R.id.radio_rear:
                RearAxleShowFragment rearFragment = new RearAxleShowFragment();
                rearFragment.setRaiseBtnStatus(!autoDown);
                fragmentCommit(rearFragment);
                break;
            case R.id.radio_front:
                fragmentCommit(new FrontAxleShowFragment());
                break;
            case R.id.radio_print:
                DataPrintFragment printFragment = new DataPrintFragment();
                if (referData != null) printFragment.setReferData(referData.copy());
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

    public void setRaise(boolean enabled) {
        autoDown = !enabled;
    }

    @Override
    public void onManufacturerSelected(String manId, String manInfo, int dbIndex) {
        if (manId.equals("0") && dbIndex == MainApplication.cusdb) {
            //进入自定义车型的界面
            fragmentCommit(new PickCusCarFragment());
            return;
        }

        backChoice = false;
//        referData = new ReferData();
//        referData.setManId(manId);
//        referData.setManInfo(manInfo);

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
        SaveOrPrintActivity.clear();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                weightHeightLevelFlag = 0;

                DbUtil dbUtil = new DbUtil();
                referData = dbUtil.queryReferData(vehicleID, dbIndex);

                if (dbIndex == MainApplication.cusdb) {
                    startWeightHeightLevel(null);
                    return;
                }

                if (referData != null) {
                    referData.setManId(manId);
                    referData.setManInfo(manInfo);
                    referData.setVehicleId(vehicleID);
                    referData.setRealYear(year);

                    final ReferData tem = referData.copy();

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            SocketClient client = new SocketClient(null, false);
                            client.send(MainApplication.synchCar, tem.copy().getSynchData());
                        }
                    }).start();
                }

                SpecialParams specialParams = dbUtil.querySpecParam(vehicleID);
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
        RadioButton rb = (RadioButton) findViewById(R.id.radio_push);
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
    public void TestResultNext(int position) {
        synch(position);
    }

    @Override
    public void rearAxleNext(int position) {
        synch(position);
    }

    @Override
    public void frontAxleNext(int position) {
        synch(position);
    }

    @Override
    public void dataPrintNext(int position) {

        if (position > 0) {
            synch(position);
            return;
        }

        if (position < -2) return;
        Intent it = new Intent(this, SaveOrPrintActivity.class);
        if (position == -1) it.putExtra("print", true);
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

            case MainApplication.testDataUrl:
                radioId = R.id.radio_fast;
                break;

            case MainApplication.rearShowUrl:
                radioId = R.id.radio_rear;
                break;

            case MainApplication.frontShowUrl:
                radioId = R.id.radio_front;
                break;

            case MainApplication.printUrl:
                radioId = R.id.radio_print;
                break;

            case MainApplication.samplePictureUrl:
                transFlag = false;
                Intent intent = new Intent(this, MaintenanceActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(intent);
                break;

            case MainApplication.specialTestUrl:
                transFlag = false;
                intent = new Intent(this, SpecialTestActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(intent);
                break;
            case MainApplication.homeUrl:
                transFlag = false;
                Intent it = new Intent(this, MenuActivity.class);
                startActivity(it);
                break;
        }

        if (radioId != -1 && transFlag) {
            RadioButton radioBtn = (RadioButton) findViewById(radioId);
            radioBtn.performClick();
        }
    }

    private void warn() {
        new AlertDialog.Builder(this, AlertDialog.THEME_HOLO_LIGHT)
                .setIconAttribute(android.R.attr.alertDialogIcon)
                .setTitle(R.string.tip_raiseBtn_down).setPositiveButton(R.string.sure, null)
                .create().show();
    }
}
