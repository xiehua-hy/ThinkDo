package com.thinkdo.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.thinkdo.entity.GloVariable;

public class HostSetActivity extends Activity implements View.OnClickListener {
    private EditText et_ip, et_port;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_host_set);
        init();
    }

    private void init() {
        if (getActionBar() != null) {
            getActionBar().setDisplayHomeAsUpEnabled(true);
        }

        et_ip = (EditText) findViewById(R.id.et_ip);
        et_port = (EditText) findViewById(R.id.et_port);

        SharedPreferences shared = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String ip = shared.getString(GloVariable.hostIpKey, GloVariable.defaultIp);
        String port = String.valueOf(shared.getInt(GloVariable.hostPortKey, GloVariable.defaultPort));

        et_ip.setText(ip);
        et_port.setText(port);

        Button reset = (Button) findViewById(R.id.btn_reset);
        Button save = (Button) findViewById(R.id.btn_save);
        reset.setOnClickListener(this);
        save.setOnClickListener(this);

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
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_reset:
                et_ip.setText(GloVariable.emptyString);
                et_port.setText(GloVariable.emptyString);
                break;
            case R.id.btn_save:
                new AlertDialog.Builder(this)
                        .setMessage(R.string.tip_add_data)
                        .setTitle(R.string.sure)
                        .setCancelable(false)
                        .setNegativeButton(R.string.cancel, null)
                        .setPositiveButton(R.string.sure, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String ip = et_ip.getText().toString();
                                String port = et_port.getText().toString();

                                if (ip.equals(GloVariable.emptyString) || port.equals(GloVariable.emptyString)) {
                                    Toast.makeText(getApplicationContext(), R.string.tip_data_cannot_null, Toast.LENGTH_SHORT).show();
                                } else {
                                    int newPort = Integer.parseInt(port);
                                    SharedPreferences.Editor edit = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit();
                                    edit.putString(GloVariable.hostIpKey, ip);
                                    edit.putInt(GloVariable.hostPortKey, newPort);
                                    edit.apply();
                                    GloVariable.ip = ip;
                                    GloVariable.port = newPort;
                                    Toast.makeText(getApplicationContext(), R.string.tip_data_success_save, Toast.LENGTH_SHORT).show();
                                    finish();
                                }

                            }
                        })
                        .create()
                        .show();
        }
    }
}
