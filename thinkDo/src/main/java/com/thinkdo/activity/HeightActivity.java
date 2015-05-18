package com.thinkdo.activity;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.WindowManager;

import com.thinkdo.db.VehicleDbUtil;
import com.thinkdo.entity.ReferData;
import com.thinkdo.entity.SpecialParams;
import com.thinkdo.fragment.Height1Fragment;
import com.thinkdo.fragment.Height1Fragment.Heightfrag1Callback;
import com.thinkdo.fragment.Height2Fragment;
import com.thinkdo.fragment.Height2Fragment.Heightfrag2Callback;

/**
 * Created by Administrator on 2015/5/16.
 */
public class HeightActivity extends Activity implements Heightfrag1Callback, Heightfrag2Callback {
    private SpecialParams data;
    private ReferData referData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.frame);
        init();
    }

    private void init() {
        ActionBar actionBar = getActionBar();
        if (actionBar != null) {
            getActionBar().setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(R.string.height_test);
        }

        data = (SpecialParams) getIntent().getSerializableExtra("SpecialParams");
        if (data == null && data.getHeightParam() == null) return;

        if (data.getHeightParam().getHeightFlag().equals("1")) {
            getFragmentManager().beginTransaction().replace(R.id.frameLayout, Height1Fragment.newInstance(data.getHeightParam())).commit();
        } else {
            getFragmentManager().beginTransaction().replace(R.id.frameLayout, Height2Fragment.newInstance(data.getHeightParam())).commit();
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void back() {
        Intent intent = new Intent();
        intent.putExtra("SpecialParams", data);
        intent.putExtra("ReferData", referData);
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void next1() {
        back();
    }

    @Override
    public void next2(final String leftFront, final String rightFront, final String leftRear, final String rightRear) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                referData = new VehicleDbUtil().queryHeightData(data.getHeightParam().getVehicleId(),
                        leftFront, rightFront, leftRear, rightRear, data.getHeightParam().getHeightFlag());
                back();
            }
        }).start();
    }
}
