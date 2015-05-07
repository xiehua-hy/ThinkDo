package com.thinkdo.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.MenuItem;

import com.thinkdo.fragment.SpecialTestFragment;

/**
 * Created by Administrator on 2015/5/7.
 */
public class SpecialTestActivity extends Activity {
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
}
