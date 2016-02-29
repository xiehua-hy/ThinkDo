package com.thinkdo.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.thinkdo.view.ClickScreenToReload;


/**
 * Created by Administrator on 2015/6/24.
 */
public class LightSetActivity extends Activity {
    private ClickScreenToReload load;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_light);
        if (getActionBar() != null) {
            getActionBar().setDisplayHomeAsUpEnabled(true);
            getActionBar().setTitle(R.string.photo_light_set);
        }
        load = (ClickScreenToReload) findViewById(R.id.clickScreenToReload);
        load.setOnSubmitListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                load.questHost();
                finish();
            }
        });
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
