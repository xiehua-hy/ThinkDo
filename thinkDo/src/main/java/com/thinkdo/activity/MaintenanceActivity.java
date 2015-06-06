package com.thinkdo.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.WindowManager;

import com.thinkdo.entity.GloVariable;
import com.thinkdo.fragment.SamplePicFragment;
import com.thinkdo.fragment.SamplePicFragment.SamplePicCallback;
import com.thinkdo.net.NetQuest;

/**
 * Created by Administrator on 2015/6/4.
 */
public class MaintenanceActivity extends Activity implements SamplePicCallback {

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
        }

        getFragmentManager().beginTransaction().replace(R.id.frameLayout, new SamplePicFragment()).commit();
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
        super.onBackPressed();
        finish();
    }

    @Override
    public void finish() {
        super.finish();
        new NetQuest(GloVariable.homeUrl).start();
    }

    @Override
    public void SamplePicNext(int position) {
        switch (position) {
            case GloVariable.pushcarUrl:
                redirect(position);
                break;
            case GloVariable.kingpinUrl:
                redirect(position);
                break;
            case GloVariable.testDataUrl:
                redirect(position);
                break;
            case GloVariable.rearShowUrl:
                redirect(position);
                break;
            case GloVariable.frontShowUrl:
                redirect(position);
                break;
            case GloVariable.homeUrl:
                Intent it = new Intent(this, MenuActivity.class);
                it.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(it);
                break;
        }
    }

    public void redirect(int position) {
        Intent it = new Intent(this, MainActivity.class);
        it.putExtra("position", position);
        it.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(it);
    }
}
