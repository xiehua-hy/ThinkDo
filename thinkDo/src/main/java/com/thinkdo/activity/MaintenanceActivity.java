package com.thinkdo.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.thinkdo.application.MainApplication;
import com.thinkdo.fragment.SamplePicFragment;
import com.thinkdo.fragment.SamplePicFragment.SamplePicCallback;
import com.thinkdo.net.NetQuest;

/**
 * Created by Administrator on 2015/6/4.
 */
public class MaintenanceActivity extends Activity implements SamplePicCallback {
    private final String defaultPassword = "12345";

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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_light_set, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.action_light:

//                final SharedPreferences shared = PreferenceManager.getDefaultSharedPreferences(MainApplication.context);
//                final String password = shared.getString(MainApplication.photoPasswordKey, MainApplication.emptyString);
//
//                if (password.equals("")) {
//                    //设置相机密码
//                    PhotoPasswordSetDiaFragment diaFragment = new PhotoPasswordSetDiaFragment();
//                    diaFragment.show(getFragmentManager(), "photo password set");
//                } else {
//                    //输入相机密码
//                    final EditText et = new EditText(this);
//                    et.setHint(R.string.tip_photo_password);
//                    new AlertDialog.Builder(this)
//                            .setMessage("")
//                            .setView(et)
//                            .setNegativeButton(R.string.cancel, null)
//                            .setPositiveButton(R.string.sure, new DialogInterface.OnClickListener() {
//                                @Override
//                                public void onClick(DialogInterface dialog, int which) {
//                                    if (et.getText().toString().equals("")) {
//                                        Toast.makeText(MaintenanceActivity.this, R.string.tip_data_cannot_null, Toast.LENGTH_SHORT).show();
//                                    } else if (et.getText().toString().equals(password)) {
//                                        //OK
//                                        startActivity(new Intent(MaintenanceActivity.this, LightSetActivity.class));
//                                    } else {
//                                        Toast.makeText(MaintenanceActivity.this, R.string.password_error, Toast.LENGTH_SHORT).show();
//                                    }
//                                }
//                            })
//                            .create().show();
//                }

                final EditText et = new EditText(this);
                et.setHint(R.string.tip_photo_password);
                new AlertDialog.Builder(this)
                        .setMessage("")
                        .setView(et)
                        .setNegativeButton(R.string.cancel, null)
                        .setPositiveButton(R.string.sure, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (et.getText().toString().equals("")) {
                                    Toast.makeText(MaintenanceActivity.this, R.string.tip_data_cannot_null, Toast.LENGTH_SHORT).show();
                                } else if (et.getText().toString().equals(defaultPassword)) {
                                    //OK
                                    startActivity(new Intent(MaintenanceActivity.this, LightSetActivity.class));
                                } else {
                                    Toast.makeText(MaintenanceActivity.this, R.string.password_error, Toast.LENGTH_SHORT).show();
                                }
                            }
                        })
                        .create().show();

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void finish() {
        super.finish();
        new NetQuest(MainApplication.homeUrl);
    }

    @Override
    public void SamplePicNext(int position) {
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
            case MainApplication.specialTestUrl:
                it = new Intent(this, SpecialTestActivity.class);
                it.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
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
