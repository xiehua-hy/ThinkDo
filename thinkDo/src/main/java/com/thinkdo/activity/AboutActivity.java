package com.thinkdo.activity;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

/**
 * Created by xiehua on 2015/4/27.
 */

public class AboutActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        init();

    }

    private void init() {
        if (getActionBar() != null) {
            getActionBar().setDisplayHomeAsUpEnabled(true);
        }

        TextView textView = (TextView) findViewById(R.id.textView1);
        try {
            String version = getResources().getString(R.string.current_version)
                    + getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
            textView.setText(version);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }


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
}
