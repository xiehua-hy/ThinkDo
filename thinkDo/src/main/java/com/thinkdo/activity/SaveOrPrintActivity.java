package com.thinkdo.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.thinkdo.db.DbUtil;
import com.thinkdo.entity.CustomerModel;
import com.thinkdo.entity.GloVariable;
import com.thinkdo.entity.ReferData;
import com.thinkdo.entity.TestVehicleInfoModel;
import com.thinkdo.net.SocketClient;
import com.thinkdo.util.CommonUtil;
import com.thinkdo.util.XmlInit;

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
                            SocketClient client = new SocketClient(null, null, false);
                            client.send(GloVariable.hostSaveDataUrl, getPrintData(customerInfo, vehicleInfo, MainActivity.referData));
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
                        SocketClient client = new SocketClient(null, null, false);
                        client.send(GloVariable.printContent, getPrintData(customerInfo, vehicleInfo, MainActivity.referData));
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

    public static String getPrintData(CustomerModel custom, TestVehicleInfoModel info, ReferData data) {
        if (custom == null || info == null || data == null) return null;

        SharedPreferences shared = PreferenceManager.getDefaultSharedPreferences(GloVariable.context);
        String initValue = "";

        String garageName = shared.getString(GloVariable.garageNameKey, initValue);
        String garageAddress = shared.getString(GloVariable.garageAddressKey, initValue);
        String garageTel = shared.getString(GloVariable.garageTelKey, initValue);
        String garagePostCode = shared.getString(GloVariable.garagePostCodeKey, initValue);
        String garageFax = shared.getString(GloVariable.garageFaxKey, initValue);

        String array[] = new String[175];
        array[0] = initValue;

        array[1] = XmlInit.getResourceNodeValue("String1600");
        array[2] = XmlInit.getResourceNodeValue("String5012");
        array[3] = XmlInit.getResourceNodeValue("String1612");
        array[4] = XmlInit.getResourceNodeValue("String1221");
        array[5] = XmlInit.getResourceNodeValue("String1222");
        array[6] = XmlInit.getResourceNodeValue("String1223");
        array[7] = XmlInit.getResourceNodeValue("String1613");

        array[8] = initValue;

        array[9] = custom.getName();
        array[10] = info.getPlateNum();
        array[11] = info.getManInfo();
        array[12] = info.getModel();
        array[13] = info.getStartYear();
        array[14] = info.getEndYear();
        array[15] = info.getCurUnit();

        array[16] = initValue;
        array[17] = XmlInit.getResourceNodeValue("String3001");
        array[18] = XmlInit.getResourceNodeValue("String3002");
        array[19] = initValue;
        array[20] = XmlInit.getResourceNodeValue("String3004");
        array[21] = initValue;

        array[22] = info.getTestDate();
        array[23] = info.getOperator();

        array[24] = initValue;
        array[25] = info.getMalfunction();
        array[26] = XmlInit.getResourceNodeValue("String3006");
        array[27] = info.getTestDate();
        array[28] = garageName;
        array[29] = garageTel;
        array[30] = XmlInit.getResourceNodeValue("String1601");

        array[31] = custom.getCompany();
        array[33] = custom.getAddress();

        array[32] = XmlInit.getResourceNodeValue("String1602");
        array[34] = garageAddress;
        array[35] = XmlInit.getResourceNodeValue("String1233");
        array[36] = info.getRemark();
        array[37] = initValue;
        array[38] = initValue;
        array[39] = initValue;
        array[40] = garageTel;

        array[41] = garageFax;
        array[42] = garagePostCode;

        array[43] = initValue;
        array[44] = initValue;
        array[45] = initValue;
        array[46] = initValue;
        array[47] = initValue;
        array[48] = initValue;
        array[49] = initValue;

        array[50] = GloVariable.context.getString(R.string.parameters);
        array[51] = GloVariable.context.getString(R.string.before);
        array[52] = GloVariable.context.getString(R.string.min);
        array[53] = GloVariable.context.getString(R.string.max);
        array[54] = GloVariable.context.getString(R.string.after);

        array[55] = GloVariable.context.getString(R.string.front_total_toe);
        array[60] = GloVariable.context.getString(R.string.left_front_toe);
        array[65] = GloVariable.context.getString(R.string.right_front_toe);
        array[70] = GloVariable.context.getString(R.string.left_front_camber);
        array[75] = GloVariable.context.getString(R.string.right_front_camber);
        array[80] = GloVariable.context.getString(R.string.left_caster);
        array[85] = GloVariable.context.getString(R.string.right_caster);
        array[90] = GloVariable.context.getString(R.string.left_kpi);
        array[95] = GloVariable.context.getString(R.string.right_kpi);
        array[100] = GloVariable.context.getString(R.string.rear_total_toe);
        array[105] = GloVariable.context.getString(R.string.left_rear_toe);
        array[110] = GloVariable.context.getString(R.string.right_rear_toe);
        array[115] = GloVariable.context.getString(R.string.left_rear_camber);
        array[120] = GloVariable.context.getString(R.string.right_rear_camber);
        array[125] = GloVariable.context.getString(R.string.thrust_angle);
        array[130] = GloVariable.context.getString(R.string.wheelbase_diff);
        array[135] = GloVariable.context.getString(R.string.wheel_diff);
        array[140] = GloVariable.context.getString(R.string.left_include_angle);
        array[145] = GloVariable.context.getString(R.string.right_include_angle);
        array[150] = GloVariable.context.getString(R.string.left_turn_angle);
        array[155] = GloVariable.context.getString(R.string.right_turn_angle);
        array[160] = GloVariable.context.getString(R.string.left_max_turn_angle);
        array[165] = GloVariable.context.getString(R.string.right_max_turn_angle);

        array[56] = data.getFrontTotalToe().getPreReal();
        array[57] = data.getFrontTotalToe().getMin();
        array[58] = data.getFrontTotalToe().getMax();
        array[59] = data.getFrontTotalToe().getReal();

        array[61] = data.getLeftFrontToe().getPreReal();
        array[62] = data.getLeftFrontToe().getMin();
        array[63] = data.getLeftFrontToe().getMax();
        array[64] = data.getLeftFrontToe().getReal();

        array[66] = data.getRightFrontToe().getPreReal();
        array[67] = data.getRightFrontToe().getMin();
        array[68] = data.getRightFrontToe().getMax();
        array[69] = data.getRightFrontToe().getReal();

        array[71] = data.getLeftFrontCamber().getPreReal();
        array[72] = data.getLeftFrontCamber().getMin();
        array[73] = data.getLeftFrontCamber().getMax();
        array[74] = data.getLeftFrontCamber().getReal();

        array[76] = data.getRightFrontCamber().getPreReal();
        array[77] = data.getRightFrontCamber().getMin();
        array[78] = data.getRightFrontCamber().getMax();
        array[79] = data.getRightFrontCamber().getReal();

        array[81] = data.getLeftCaster().getPreReal();
        array[82] = data.getLeftCaster().getMin();
        array[83] = data.getLeftCaster().getMax();
        array[84] = data.getLeftCaster().getReal();

        array[86] = data.getRightCaster().getPreReal();
        array[87] = data.getRightCaster().getMin();
        array[88] = data.getRightCaster().getMax();
        array[89] = data.getRightCaster().getReal();

        array[91] = data.getLeftKpi().getPreReal();
        array[92] = data.getLeftKpi().getMin();
        array[93] = data.getLeftKpi().getMax();
        array[94] = data.getLeftKpi().getReal();

        array[96] = data.getRightKpi().getPreReal();
        array[97] = data.getRightKpi().getMin();
        array[98] = data.getRightKpi().getMax();
        array[99] = data.getRightKpi().getReal();

        array[101] = data.getRearTotalToe().getPreReal();
        array[102] = data.getRearTotalToe().getMin();
        array[103] = data.getRearTotalToe().getMax();
        array[104] = data.getRearTotalToe().getReal();

        array[106] = data.getLeftRearToe().getPreReal();
        array[107] = data.getLeftRearToe().getMin();
        array[108] = data.getLeftRearToe().getMax();
        array[109] = data.getLeftRearToe().getReal();

        array[111] = data.getRightRearToe().getPreReal();
        array[112] = data.getRightRearToe().getMin();
        array[113] = data.getRightRearToe().getMax();
        array[114] = data.getRightRearToe().getReal();

        array[116] = data.getLeftRearCamber().getPreReal();
        array[117] = data.getLeftRearCamber().getMin();
        array[118] = data.getLeftRearCamber().getMax();
        array[119] = data.getLeftRearCamber().getReal();

        array[121] = data.getRightRearCamber().getPreReal();
        array[122] = data.getRightRearCamber().getMin();
        array[123] = data.getRightRearCamber().getMax();
        array[124] = data.getRightRearCamber().getReal();

        array[126] = data.getMaxThrust().getPreReal();
        array[127] = data.getMaxThrust().getMin();
        array[128] = data.getMaxThrust().getMax();
        array[129] = data.getMaxThrust().getReal();

        array[131] = data.getWheelbaseDiff().getPreReal();
        array[132] = data.getWheelbaseDiff().getMin();
        array[133] = data.getWheelbaseDiff().getMax();
        array[134] = data.getWheelbaseDiff().getReal();

        array[136] = data.getWheelDiff().getPreReal();
        array[137] = data.getWheelDiff().getMin();
        array[138] = data.getWheelDiff().getMax();
        array[139] = data.getWheelDiff().getReal();

        array[141] = data.getLeftIncludeAngle().getPreReal();
        array[142] = data.getLeftIncludeAngle().getMin();
        array[143] = data.getLeftIncludeAngle().getMax();
        array[144] = data.getLeftIncludeAngle().getReal();

        array[146] = data.getRightIncludeAngle().getPreReal();
        array[147] = data.getRightIncludeAngle().getMin();
        array[148] = data.getRightIncludeAngle().getMax();
        array[149] = data.getRightIncludeAngle().getReal();

        array[151] = data.getLeftTurnAngle().getPreReal();
        array[152] = data.getLeftTurnAngle().getMin();
        array[153] = data.getLeftTurnAngle().getMax();
        array[154] = data.getLeftTurnAngle().getReal();

        array[156] = data.getRightTurnAngle().getPreReal();
        array[157] = data.getRightTurnAngle().getMin();
        array[158] = data.getRightTurnAngle().getMax();
        array[159] = data.getRightTurnAngle().getReal();

        array[161] = data.getLeftMaxTurnAngle().getPreReal();
        array[162] = data.getLeftMaxTurnAngle().getMin();
        array[163] = data.getLeftMaxTurnAngle().getMax();
        array[164] = data.getLeftMaxTurnAngle().getReal();

        array[166] = data.getRightMaxTurnAngle().getPreReal();
        array[167] = data.getRightMaxTurnAngle().getMin();
        array[168] = data.getRightMaxTurnAngle().getMax();
        array[169] = data.getRightMaxTurnAngle().getReal();

        array[171] = GloVariable.unit.getValue();
        array[172] = "500.00";
        array[173] = info.getWheelBase();
        array[174] = info.getFrontTread();

        StringBuilder buff = new StringBuilder();
        for (int i = 0; i < array.length; i++) {
            if (array[i] == null) array[i] = "";
            buff.append(String.format("%s|", array[i]));
        }

        return buff.toString();
    }
}
