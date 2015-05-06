package com.thinkdo.activity;

import android.app.Activity;
import android.app.Fragment;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;

import com.thinkdo.fragment.DataPrintFragment;
import com.thinkdo.fragment.FrontAxletShowFragment;
import com.thinkdo.fragment.KingpinFragment;
import com.thinkdo.fragment.ManufacturerFragment;
import com.thinkdo.fragment.PushCarFragment;
import com.thinkdo.fragment.RearAxleShowFragment;
import com.thinkdo.fragment.TestResultFragment;

public class MainActivity extends Activity implements View.OnClickListener {
    private int preCheckedRadio = R.id.radio_pick;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

        getFragmentManager().beginTransaction().add(R.id.frameLayout, new ManufacturerFragment()).commit();
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

}
