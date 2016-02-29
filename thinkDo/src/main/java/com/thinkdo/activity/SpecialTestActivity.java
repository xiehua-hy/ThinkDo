package com.thinkdo.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.thinkdo.application.MainApplication;
import com.thinkdo.fragment.SpecialTestFragment;
import com.thinkdo.fragment.SpecialTestFragment.SpecialTestCallback;
import com.thinkdo.net.NetQuest;

/**
 * Created by Administrator on 2015/5/7.
 */
public class SpecialTestActivity extends Activity implements SpecialTestCallback {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frame);
        init();
    }

    private void init() {
        if (getActionBar() != null) {
            getActionBar().setDisplayHomeAsUpEnabled(true);
        }

        getFragmentManager().beginTransaction().replace(R.id.frameLayout, new SpecialTestFragment()).commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void finish() {
        super.finish();
        new NetQuest(MainApplication.homeUrl);
    }

    @Override
    public void specialTest(int position) {
        switch (position) {
            case MainApplication.pushcarUrl:
                redirect(position);
                break;
            case MainApplication.kingpinUrl:
                redirect(position);
                break;
            case MainApplication.testDataUrl:
                redirect(position);
                break;
            case MainApplication.rearShowUrl:
                redirect(position);
                break;
            case MainApplication.frontShowUrl:
                redirect(position);
                break;
            case MainApplication.homeUrl:
                Intent it = new Intent(this, MenuActivity.class);
                startActivity(it);
                break;
        }

    }

    public void redirect(int position) {
        Intent it = new Intent(this, MainActivity.class);
        it.putExtra("position", position);
        it.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(it);
    }
}
