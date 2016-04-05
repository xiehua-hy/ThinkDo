package com.thinkdo.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.WindowManager;

import com.thinkdo.application.MainApplication;
import com.thinkdo.fragment.TestResultFragment;
import com.thinkdo.net.NetQuest;

/**
 * Created by Administrator on 2015/5/6.
 */
public class FastTestActivity extends Activity implements TestResultFragment.TestResultCallback {
    private boolean checked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.frame);
        init();
    }

    private void init() {
        if (getActionBar() != null) {
            getActionBar().setDisplayHomeAsUpEnabled(true);
            getActionBar().setTitle(R.string.fast_measure);
        }
        getFragmentManager().beginTransaction().replace(R.id.frameLayout, new TestResultFragment()).commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    public void finish() {
        if (checked) {
            warn();
            return;
        }
        super.finish();
        new NetQuest(MainApplication.homeUrl);
    }

    public void setRaise(boolean check) {
        checked = check;
    }


    private void warn() {
        new AlertDialog.Builder(this, AlertDialog.THEME_HOLO_LIGHT)
                .setIconAttribute(android.R.attr.alertDialogIcon)
                .setTitle(R.string.tip_raiseBtn_down).setPositiveButton(R.string.sure, null)
                .create().show();
    }

    @Override
    public void TestResultNext(int position) {

    }
}
