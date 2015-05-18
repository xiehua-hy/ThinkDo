package com.thinkdo.activity;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.thinkdo.entity.SpecialParams;
import com.thinkdo.entity.WeightParam;

/**
 * Created by Administrator on 2015/5/16.
 */
public class WeightActivity extends Activity {
    private SpecialParams data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.fragment_weight);
        init();
    }

    private void init() {
        ActionBar actionBar = getActionBar();
        if (actionBar != null) {
            getActionBar().setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(R.string.weight_test);
        }

        Button btn = (Button) findViewById(R.id.btn_next);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                back();
            }
        });

        data = (SpecialParams) getIntent().getSerializableExtra("SpecialParams");
        if (data == null || data.getWeightParam() == null) return;
        WeightParam weight = data.getWeightParam();

        TextView tv = (TextView) findViewById(R.id.tv_leftFront);
        if (!weight.getLeftFront().equals("0")) tv.setText(weight.getLeftFront());

        tv = (TextView) findViewById(R.id.tv_rightFront);
        if (!weight.getRightFront().equals("0")) tv.setText(weight.getRightFront());

        tv = (TextView) findViewById(R.id.tv_leftRear);
        if (!weight.getLeftRear().equals("0")) tv.setText(weight.getLeftRear());

        tv = (TextView) findViewById(R.id.tv_rightRear);
        if (!weight.getRightRear().equals("0")) tv.setText(weight.getRightRear());

        tv = (TextView) findViewById(R.id.tv_rear);
        if (!weight.getRearSeat().equals("0")) tv.setText(weight.getRearSeat());

        tv = (TextView) findViewById(R.id.tv_trunk);
        if (!weight.getTrunk().equals("0")) tv.setText(weight.getTrunk());
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
        setResult(RESULT_OK, intent);
        finish();
    }
}
