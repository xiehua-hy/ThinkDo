package com.thinkdo.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.thinkdo.db.CustomDbUtil;
import com.thinkdo.entity.CustomerModel;
import com.thinkdo.entity.GloVariable;
import com.thinkdo.entity.ReferData;
import com.thinkdo.entity.TestVehicleInfoModel;
import com.thinkdo.fragment.DataPrintFragment;
import com.thinkdo.fragment.HistoryUserDataFragment;
import com.thinkdo.fragment.DataPrintFragment.DataPrintCallback;
import com.thinkdo.net.NetQuest;

/**
 * Created by Administrator on 2015/6/3.
 */
public class DataPrintActivity extends Activity implements DataPrintCallback {
    private String clientId;
    private String testNo;
    private ProgressDialog dialog;
    private ReferData referData;
    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    if (dialog != null) dialog.cancel();
                    Toast.makeText(DataPrintActivity.this, R.string.tip_data_printing, Toast.LENGTH_SHORT).show();
                    break;
                case 1:
                    fragmentCommit();
                    break;
            }
            return true;
        }
    });

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

        clientId = getIntent().getStringExtra(HistoryUserDataFragment.clientIdKey);
        testNo = getIntent().getStringExtra(HistoryUserDataFragment.testNoKey);

        new QueryThread(clientId, testNo).start();
    }

    private void fragmentCommit() {
        DataPrintFragment fragment = new DataPrintFragment();
        fragment.setSaveBtnVisible(View.GONE);
        fragment.setConnect(false);
        fragment.setReferData(referData);
        getFragmentManager().beginTransaction().replace(R.id.frameLayout, fragment).commit();
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
    public void dataPrintNext(int position) {
        dialog = ProgressDialog.show(this, "Prompt", "wait...");
        new PrintConnectThread().start();
    }

    class QueryThread extends Thread {
        private String clientId, testNo;

        public QueryThread(String clientId, String testNo) {
            this.clientId = clientId;
            this.testNo = testNo;
        }

        @Override
        public void run() {
            referData = new CustomDbUtil().queryTestData(clientId, testNo);
            handler.sendEmptyMessage(1);
        }
    }

    class PrintConnectThread extends Thread {
        @Override
        public void run() {
            CustomDbUtil db = new CustomDbUtil();
            CustomerModel customer = db.queryCustomer(clientId);
            TestVehicleInfoModel info = db.queryTestVeclieInfo(clientId, testNo);
            ReferData data = db.queryTestData(clientId, testNo);

            if (customer == null || info == null || data == null) return;
            new NetQuest(GloVariable.printContent, 0, SaveOrPrintActivity
                    .getPrintData(customer, info, data)).start();
            handler.sendEmptyMessage(0);
        }
    }

}
