package com.thinkdo.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.thinkdo.application.MainApplication;
import com.thinkdo.db.DbUtil;
import com.thinkdo.entity.CustomerModel;
import com.thinkdo.entity.TestVehicleInfoModel;
import com.thinkdo.net.SocketClient;
import com.thinkdo.util.CommonUtil;

/**
 * Created by Administrator on 2015/5/29.
 */
public class SaveOrPrintActivity extends Activity implements View.OnClickListener {
    private static TestVehicleInfoModel vehicleInfo;
    private static CustomerModel customerInfo;
    private EditText et_plateNo, et_customName, et_travelMileage, et_manufacturer, et_model, et_startYear, et_endYear, et_notes, et_operator;
    private CheckBox cb1, cb2, cb3, cb4, cb5;
    private boolean isPrint = false;
    private ProgressDialog dialog;

    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {

            switch (msg.what) {
                case 1:
                    Toast.makeText(SaveOrPrintActivity.this, R.string.tip_data_success_save, Toast.LENGTH_SHORT).show();
                    if (dialog != null) dialog.cancel();
                    break;
                case 2:
                    Toast.makeText(SaveOrPrintActivity.this, R.string.tip_data_printing, Toast.LENGTH_SHORT).show();
                    if (dialog != null) dialog.cancel();
                    break;
                case 3:
                    Toast.makeText(SaveOrPrintActivity.this, R.string.tip_data_fail_save, Toast.LENGTH_SHORT).show();
                    if (dialog != null) dialog.cancel();
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
        setContentView(R.layout.activity_print);
        init();
    }

    private void init() {
        if (getActionBar() != null) getActionBar().setDisplayHomeAsUpEnabled(true);
        if (getIntent() != null) {
            isPrint = getIntent().getBooleanExtra("print", false);
        }

        et_plateNo = (EditText) findViewById(R.id.et_plateNo);
        et_customName = (EditText) findViewById(R.id.et_customName);
        et_travelMileage = (EditText) findViewById(R.id.et_travelMileage);
        et_manufacturer = (EditText) findViewById(R.id.et_manufacturer);
        et_model = (EditText) findViewById(R.id.et_model);
        et_startYear = (EditText) findViewById(R.id.et_startYear);
        et_endYear = (EditText) findViewById(R.id.et_endYear);
        et_notes = (EditText) findViewById(R.id.et_others);
        et_operator = (EditText) findViewById(R.id.et_operator);
        cb1 = (CheckBox) findViewById(R.id.checkBox1);
        cb2 = (CheckBox) findViewById(R.id.checkBox2);
        cb3 = (CheckBox) findViewById(R.id.checkBox3);
        cb4 = (CheckBox) findViewById(R.id.checkBox4);
        cb5 = (CheckBox) findViewById(R.id.checkBox5);

        Button btn = (Button) findViewById(R.id.btn_save);
        if (isPrint) btn.setText(R.string.print);
        btn.setOnClickListener(this);
        btn = (Button) findViewById(R.id.btn_add);
        btn.setOnClickListener(this);

        //读取保存数据
        if (vehicleInfo != null && customerInfo != null) {
            et_plateNo.setText(vehicleInfo.getPlateNum());
            et_customName.setText(customerInfo.getName());
            et_manufacturer.setText(vehicleInfo.getManInfo());
            et_model.setText(vehicleInfo.getModel());
            et_startYear.setText(vehicleInfo.getStartYear());
            et_endYear.setText(vehicleInfo.getEndYear());
            et_travelMileage.setText(vehicleInfo.getCurUnit());
            et_operator.setText(vehicleInfo.getOperator());
            et_notes.setText(vehicleInfo.getRemark());

            cb1.setChecked(vehicleInfo.isCb1());
            cb2.setChecked(vehicleInfo.isCb2());
            cb3.setChecked(vehicleInfo.isCb3());
            cb4.setChecked(vehicleInfo.isCb4());
            cb5.setChecked(vehicleInfo.isCb5());

            et_plateNo.setEnabled(false);
            et_customName.setEnabled(false);
            et_travelMileage.setEnabled(false);
            cb1.setEnabled(false);
            cb2.setEnabled(false);
            cb3.setEnabled(false);
            cb4.setEnabled(false);
            cb5.setEnabled(false);
            et_operator.setEnabled(false);
            et_notes.setEnabled(false);
            btn.setEnabled(false);
        } else if (MainActivity.referData != null) {
            et_startYear.setText(MainActivity.referData.getStartYear());
            et_endYear.setText(MainActivity.referData.getEndYear());
            et_model.setText(MainActivity.referData.getVehicleInfo());
            et_manufacturer.setText(MainActivity.referData.getManInfo());
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 0 && resultCode == RESULT_OK && data != null) {
            customerInfo = (CustomerModel) data.getSerializableExtra("CustomerModel");
            et_customName.setText(customerInfo.getName());
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_add:
                Intent it = new Intent(this, PrintCustomAddActivity.class);
                startActivityForResult(it, 0);
                break;
            case R.id.btn_save:
                if (et_plateNo.getText().toString().equals("") || et_customName.getText().toString().equals("")) {
                    Toast.makeText(this, R.string.tip_printOrSave_data, Toast.LENGTH_SHORT).show();
                } else if (MainActivity.referData == null || MainActivity.referData.getFrontTotalToe() == null || MainActivity.referData.getFrontTotalToe().getPreReal() == null) {
                    Toast.makeText(this, R.string.tip_data_cannot_null, Toast.LENGTH_SHORT).show();
                } else if (MainActivity.referData.isSaved() && !isPrint) {
                    Toast.makeText(this, R.string.tip_data_saved, Toast.LENGTH_SHORT).show();
                } else {
                    dialog = ProgressDialog.show(this, "", "wait...");
                    new Thread(new DataSaveOrPrint()).start();
                }
                break;
        }
    }

    class DataSaveOrPrint implements Runnable {

        @Override
        public void run() {
            if (vehicleInfo == null) vehicleInfo = new TestVehicleInfoModel();

            StringBuilder buff = new StringBuilder();

            if (cb1.isChecked()) {
                buff.append(String.format("%s ", getString(R.string.malfunction1)));
                vehicleInfo.setCb1(true);
            }
            if (cb2.isChecked()) {
                buff.append(String.format("%s ", getString(R.string.malfunction2)));
                vehicleInfo.setCb2(true);
            }
            if (cb3.isChecked()) {
                buff.append(String.format("%s ", getString(R.string.malfunction3)));
                vehicleInfo.setCb3(true);
            }
            if (cb4.isChecked()) {
                buff.append(String.format("%s ", getString(R.string.malfunction4)));
                vehicleInfo.setCb4(true);
            }
            if (cb5.isChecked()) {
                buff.append(String.format("%s ", getString(R.string.other)));
                vehicleInfo.setCb5(true);
            }

            String time = CommonUtil.getCurTime();

            if (customerInfo == null) {
                customerInfo = new CustomerModel();
                customerInfo.setName(et_customName.getText().toString());
                customerInfo.setDate(time);
            }


            vehicleInfo.setPlateNum(et_plateNo.getText().toString());
            vehicleInfo.setTestDate(time);
            vehicleInfo.setOperator(et_operator.getText().toString());
            vehicleInfo.setMalfunction(buff.toString());
            vehicleInfo.setManInfo(et_manufacturer.getText().toString());
            vehicleInfo.setModel(et_model.getText().toString());
            vehicleInfo.setStartYear(et_startYear.getText().toString());
            vehicleInfo.setEndYear(et_endYear.getText().toString());
            vehicleInfo.setCurUnit(et_travelMileage.getText().toString());
            vehicleInfo.setRemark(et_notes.getText().toString());

            if (MainActivity.referData.getWheelbase() != null) {

                vehicleInfo.setWheelBase(MainActivity.referData.getWheelbase().getMid());
                vehicleInfo.setFrontTread(MainActivity.referData.getFrontWheel().getMid());
                vehicleInfo.setRearTread(MainActivity.referData.getRearWheel().getMid());
            }


            if (!MainActivity.referData.isSaved()) {
                DbUtil util = new DbUtil();

                boolean flag = true;
                if (customerInfo.getId() == null) {
                    flag = util.addCustomer(customerInfo);
                }

                vehicleInfo.setCustomerId(customerInfo.getId());
                flag &= util.addTestVehicleInfo(vehicleInfo);
                flag &= util.addTestVehicleData(MainActivity.referData, customerInfo.getId(), vehicleInfo.getPlateNum(), vehicleInfo.getTestSerial(), time);
                if (flag) MainActivity.referData.setSaved(true);

                if (!isPrint) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            SocketClient client = new SocketClient(null, false);
                            client.send(MainApplication.hostSaveDataUrl, MainActivity.referData.getPrintData(customerInfo, vehicleInfo));
                            client.onStop();

                        }
                    }).start();

                    if (flag) handler.sendEmptyMessage(1);
                    else handler.sendEmptyMessage(3);
                }

            }


            if (isPrint) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        SocketClient client = new SocketClient(null, false);
                        client.send(MainApplication.printContent, MainActivity.referData.getPrintData(customerInfo, vehicleInfo));
                        client.onStop();
                        handler.sendEmptyMessage(2);
                    }
                }).start();
            }
        }
    }


    //在选车以及退回主界面时
    public static void clear() {
        vehicleInfo = null;
        customerInfo = null;
    }

}
