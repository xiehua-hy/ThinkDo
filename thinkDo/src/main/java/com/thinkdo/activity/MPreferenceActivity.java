package com.thinkdo.activity;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.MenuItem;

import com.thinkdo.entity.OperOftenDataTotalModel;
import com.thinkdo.fragment.DefCarDataFragment;
import com.thinkdo.fragment.DefCarInfoFragment;
import com.thinkdo.fragment.GarageFragment;
import com.thinkdo.fragment.PrintSetFragment;
import com.thinkdo.fragment.SettingFragment;
import com.thinkdo.fragment.UnitFragment;
import com.thinkdo.fragment.DefCarInfoFragment.DefCarInfoCallback;

/**
 * Created by Administrator on 2015/5/7.
 */
public class MPreferenceActivity extends Activity implements DefCarInfoCallback {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frame);
        init();
    }

    private void init() {
        ActionBar actionBar = getActionBar();
        if (actionBar == null) {
            return;
        }
        actionBar.setDisplayHomeAsUpEnabled(true);

        int flag = getIntent().getIntExtra(SettingFragment.extraIntKey, 1);
        switch (flag) {
            case 1:
                actionBar.setTitle(R.string.unit_set);
                fragmentCommit(new UnitFragment());
                break;
            case 2:
                actionBar.setTitle(R.string.garage_set);
                fragmentCommit(new GarageFragment());
                break;
            case 3:
                actionBar.setTitle(R.string.print_set);
                fragmentCommit(new PrintSetFragment());
                break;
            case 4:
                actionBar.setTitle(R.string.add_car);
                fragmentCommit(new DefCarInfoFragment());
                break;
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

    public void fragmentCommit(Fragment fragment) {
        getFragmentManager().beginTransaction().replace(R.id.frameLayout, fragment).commit();
    }

    @Override
    public void onDefineInfoNext(OperOftenDataTotalModel dataTotal, int unit, int toeUnit) {
        DefCarDataFragment fragment = new DefCarDataFragment();
        fragment.setAllParam(dataTotal, unit, toeUnit);
        fragmentCommit(fragment);
    }
}
