package com.thinkdo.activity;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.thinkdo.db.VehicleDbUtil;
import com.thinkdo.entity.GloVariable;
import com.thinkdo.entity.LevelParam;
import com.thinkdo.entity.ReferData;
import com.thinkdo.entity.SpecialParams;
import com.thinkdo.util.CommonUtil;

/**
 * Created by Administrator on 2015/5/16.
 */
public class LevelActivity extends Activity {
    private SpecialParams data;
    private ReferData referData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.fragment_level);
        init();

    }

    private void init() {
        ActionBar actionBar = getActionBar();
        if (actionBar != null) {
            getActionBar().setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(R.string.level_test);
        }

        data = (SpecialParams) getIntent().getSerializableExtra("SpecialParams");
        if (data == null || data.getLevelParam() == null) return;

        final EditText leftFront = (EditText) findViewById(R.id.et_leftFront);
        final EditText rightFront = (EditText) findViewById(R.id.et_rightFront);
        final EditText leftRear = (EditText) findViewById(R.id.et_leftRear);
        final EditText rightRear = (EditText) findViewById(R.id.et_rightRear);

        TextView str = (TextView) findViewById(R.id.tv_leftFrontString);
        TextView min = (TextView) findViewById(R.id.tv_leftFrontMin);
        TextView mid = (TextView) findViewById(R.id.tv_leftFront);
        TextView max = (TextView) findViewById(R.id.tv_leftFrontMax);

        if (data.getLevelParam().getFrontLevel() != null) {
            leftFront.setVisibility(View.VISIBLE);
            rightFront.setVisibility(View.VISIBLE);
            leftFront.setHint(data.getLevelParam().getFrontLevel().getMid());
            rightFront.setHint(data.getLevelParam().getFrontLevel().getMid());

            str.setText(data.getLevelParam().getFrontLevel().getExplain());
            min.setText(data.getLevelParam().getFrontLevel().getMin());
            mid.setText(data.getLevelParam().getFrontLevel().getMid());
            max.setText(data.getLevelParam().getFrontLevel().getMax());

            str = (TextView) findViewById(R.id.tv_rightFrontString);
            min = (TextView) findViewById(R.id.tv_rightFrontMin);
            mid = (TextView) findViewById(R.id.tv_rightFront);
            max = (TextView) findViewById(R.id.tv_rightFrontMax);

            str.setText(data.getLevelParam().getFrontLevel().getExplain());
            min.setText(data.getLevelParam().getFrontLevel().getMin());
            mid.setText(data.getLevelParam().getFrontLevel().getMid());
            max.setText(data.getLevelParam().getFrontLevel().getMax());
        }

        str = (TextView) findViewById(R.id.tv_leftRearString);
        min = (TextView) findViewById(R.id.tv_leftRearMin);
        mid = (TextView) findViewById(R.id.tv_leftRear);
        max = (TextView) findViewById(R.id.tv_leftRearMax);

        if (data.getLevelParam().getRearLevel() != null) {
            leftRear.setVisibility(View.VISIBLE);
            rightRear.setVisibility(View.VISIBLE);
            leftRear.setHint(data.getLevelParam().getRearLevel().getMid());
            rightRear.setHint(data.getLevelParam().getRearLevel().getMid());

            str.setText(data.getLevelParam().getRearLevel().getExplain());
            min.setText(data.getLevelParam().getRearLevel().getMin());
            mid.setText(data.getLevelParam().getRearLevel().getMid());
            max.setText(data.getLevelParam().getRearLevel().getMax());

            str = (TextView) findViewById(R.id.tv_rightRearString);
            min = (TextView) findViewById(R.id.tv_rightRearMin);
            mid = (TextView) findViewById(R.id.tv_rightRear);
            max = (TextView) findViewById(R.id.tv_rightRearMax);

            str.setText(data.getLevelParam().getRearLevel().getExplain());
            min.setText(data.getLevelParam().getRearLevel().getMin());
            mid.setText(data.getLevelParam().getRearLevel().getMid());
            max.setText(data.getLevelParam().getRearLevel().getMax());
        }

        Button btn = (Button) findViewById(R.id.btn_next);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sLeftFront = leftFront.getText().toString();
                String sRightFront = rightFront.getText().toString();
                String sLeftRear = leftRear.getText().toString();
                String sRightRear = rightRear.getText().toString();

                LevelParam levelParam = data.getLevelParam();
                if (leftFront.getVisibility() == View.VISIBLE) {
                    if (sLeftFront.equals("")) sLeftFront = levelParam.getFrontLevel().getMid();
                    else if (isInvalid(sLeftFront, levelParam.getFrontLevel().getMin(), levelParam.getFrontLevel().getMax())) {
                        warn();
                        return;
                    }

                    if (sRightFront.equals("")) sRightFront = levelParam.getFrontLevel().getMid();
                    else if (isInvalid(sRightFront, levelParam.getFrontLevel().getMin(), levelParam.getFrontLevel().getMax())) {
                        warn();
                        return;
                    }
                }

                if (leftRear.getVisibility() == View.VISIBLE) {
                    if (sLeftRear.equals("")) sLeftRear = levelParam.getRearLevel().getMid();
                    else if (isInvalid(sLeftRear, levelParam.getRearLevel().getMin(), levelParam.getRearLevel().getMax())) {
                        warn();
                        return;
                    }

                    if (sRightRear.equals("")) sRightRear = levelParam.getRearLevel().getMid();
                    else if (isInvalid(sRightRear, levelParam.getRearLevel().getMin(), levelParam.getRearLevel().getMax())) {
                        warn();
                        return;
                    }
                }

                if (sLeftFront.equals("")) {
                    sLeftFront = GloVariable.initValue;
                    sRightFront = GloVariable.initValue;
                }

                if (sLeftRear.equals("")) {
                    sLeftRear = GloVariable.initValue;
                    sRightRear = GloVariable.initValue;
                }

                final String fFront = CommonUtil.format((Float.parseFloat(sLeftFront) + Float.parseFloat(sRightFront)) / 2, 2);
                final String fRear = CommonUtil.format((Float.parseFloat(sLeftRear) + Float.parseFloat(sRightRear)) / 2, 2);

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        referData = new VehicleDbUtil().queryLevelData(data.getLevelParam().getVehicleId(), fFront, fRear);
                        back();
                    }
                }).start();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private boolean isInvalid(String value, String min, String max) {
        return Float.parseFloat(min) > Float.parseFloat(value)
                || Float.parseFloat(max) < Float.parseFloat(value);
    }

    private void back() {
        Intent intent = new Intent();
        intent.putExtra("SpecialParams", data);
        intent.putExtra("ReferData", referData);
        setResult(RESULT_OK, intent);
        finish();
    }

    private void warn() {
        Toast.makeText(this, R.string.tip_data_error, Toast.LENGTH_SHORT).show();
    }
}
