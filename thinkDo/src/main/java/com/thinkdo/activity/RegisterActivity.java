package com.thinkdo.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.thinkdo.application.MainApplication;
import com.thinkdo.net.NetSingleConnect;
import com.thinkdo.util.CommonUtil;


/**
 * Created by Administrator on 2015/6/8.
 */
public class RegisterActivity extends Activity {
    private NetSingleConnect socketClient;
    private EditText et;
    private boolean transFlag = true;
    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            socketClient.close();
            if (!transFlag) return true;
            switch (msg.what) {
                case -1:
                    Toast.makeText(RegisterActivity.this, R.string.tip_connect_failed, Toast.LENGTH_SHORT).show();
                    break;
                case 1:
                    String reply = msg.getData().getString(MainApplication.head);
                    if (reply == null) return true;
                    int backCode = CommonUtil.getQuestCode(reply);
                    if (backCode != MainApplication.registerUrl) return true;

                    int days = getRegisterDays(reply);
                    if (days < 0) {
                        String str = getString(R.string.tip_cdk_error);
                        switch (days) {
                            case -1:
                                str = getString(R.string.tip_cdk_length_error);
                                break;
                            case -2:
                                str = String.format("%s ,error code:%d", str, days);
                                break;
                            case -3:
                                str = String.format("%s ,error code:%d", str, days);
                                break;
                            case -4:
                                str = String.format("%s ,error code:%d", str, days);
                                break;
                        }
                        Toast.makeText(RegisterActivity.this, str, Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(RegisterActivity.this, R.string.tip_register_success, Toast.LENGTH_SHORT).show();
                        MainApplication.availableDay = days;
                        MainApplication.loginFlag = true;
                        setResult(RESULT_OK);
                        finish();
                    }
                    break;
            }
            return true;
        }
    });


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_register);

        if (getActionBar() != null) {
            getActionBar().setDisplayHomeAsUpEnabled(true);
        }

        EditText et_id = (EditText) findViewById(R.id.et_devices);
        et_id.setText(MainApplication.device);

        et = (EditText) findViewById(R.id.et_register);
        Button btn = (Button) findViewById(R.id.btn_register);

        InputFilter inputFilter = new InputFilter() {

            @Override
            public CharSequence filter(CharSequence source, int start, int end,
                                       Spanned dest, int dstart, int dend) {
                // TODO Auto-generated method stub
                if (end == 0) return null;
                String sou = source.toString().trim();
                String des = dest.toString().replace("-", "");

                int a = des.length() % 4;

                char[] array = sou.toCharArray();
                StringBuilder buff = new StringBuilder();

                for (int i = 0; i < array.length; i++) {
                    if ((a + i) % 4 == 0 && (i != 0 || des.length() != 0)) {
                        buff.append("-");
                    }
                    buff.append(array[i]);
                }

                return buff.toString();
            }

        };
        et.setFilters(new InputFilter[]{inputFilter});

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cdk = et.getText().toString().replace("-", "");
                if (cdk.equals("")) {
                    Toast.makeText(RegisterActivity.this, R.string.tip_data_cannot_null, Toast.LENGTH_SHORT).show();
                } else if (cdk.length() != 32) {
                    Toast.makeText(RegisterActivity.this, R.string.tip_cdk_length_error, Toast.LENGTH_SHORT).show();
                } else {
                    socketClient = new NetSingleConnect(handler, MainApplication.registerUrl, 0, cdk);
                }
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

    @Override
    protected void onResume() {
        super.onResume();
        transFlag = true;
    }

    @Override
    protected void onPause() {
        transFlag = false;
        if (socketClient != null) socketClient.close();
        super.onPause();
    }


    private int getRegisterDays(String msg) {
        if (msg.contains("&&")) {
            String[] arr = msg.split("&&");
            String[] array = arr[1].split("\\|");
            try {
                return Integer.parseInt(array[0]);
            } catch (NumberFormatException e) {
                return -1;
            }
        }
        return -1;
    }
}
