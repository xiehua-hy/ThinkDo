package com.thinkdo.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.thinkdo.db.CustomDbUtil;
import com.thinkdo.db.dbUtil;
import com.thinkdo.entity.CustomerModel;
import com.thinkdo.util.CommonUtil;

/**
 * Created by Administrator on 2015/6/2.
 */
public class AddCustomActivity extends Activity {
    private CustomerModel customer;
    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    Toast.makeText(AddCustomActivity.this, R.string.tip_data_success_save, Toast.LENGTH_SHORT).show();
                    break;
                case 2:
                    Toast.makeText(AddCustomActivity.this, R.string.tip_data_success_update, Toast.LENGTH_SHORT).show();
                    break;
            }

            Intent it = new Intent();
            it.putExtra("CustomModel", customer);
            setResult(RESULT_OK, it);
            finish();
            return true;
        }
    });


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_edit_customer);
        init();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void init() {
        if (getActionBar() != null) getActionBar().setDisplayHomeAsUpEnabled(true);
        customer = (CustomerModel) getIntent().getSerializableExtra("CustomerModel");

        final EditText et_name = (EditText) findViewById(R.id.et_name);
        final EditText et_company = (EditText) findViewById(R.id.et_company);
        final EditText et_tel = (EditText) findViewById(R.id.et_tel);
        final EditText et_address = (EditText) findViewById(R.id.et_address);

        if (customer != null) {
            et_name.setText(customer.getName());
            et_tel.setText(customer.getTel());
            et_company.setText(customer.getCompany());
            et_address.setText(customer.getAddress());
        }

        Button btn = (Button) findViewById(R.id.btn_save);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = et_name.getText().toString();
                if (name.equals("")) {
                    Toast.makeText(AddCustomActivity.this, R.string.tip_customer_cannt_null, Toast.LENGTH_SHORT).show();
                    return;
                }

                if (customer == null) {
                    customer = new CustomerModel();
                    customer.setName(name);
                    customer.setTel(et_tel.getText().toString());
                    customer.setCompany(et_company.getText().toString());
                    customer.setAddress(et_address.getText().toString());
                    customer.setDate(CommonUtil.getCurTime());

                    //添加插入
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            dbUtil util = new dbUtil();
                            if (util.addCustomer(customer))
                                handler.sendEmptyMessage(1);
                        }
                    }).start();

                } else {
                    customer.setName(name);
                    customer.setTel(et_tel.getText().toString());
                    customer.setCompany(et_company.getText().toString());
                    customer.setAddress(et_address.getText().toString());
                    customer.setDate(CommonUtil.getCurTime());

                    //修改
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            CustomDbUtil util = new CustomDbUtil();
                            if (util.updateCustomer(customer))
                                handler.sendEmptyMessage(2);
                        }
                    }).start();
                }
            }
        });
    }
}
