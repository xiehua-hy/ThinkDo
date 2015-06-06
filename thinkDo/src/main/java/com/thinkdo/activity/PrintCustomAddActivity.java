package com.thinkdo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.thinkdo.entity.CustomerModel;
import com.thinkdo.fragment.CustomerManageFragment;
import com.thinkdo.fragment.CustomerManageFragment.CustomerDeleteCallback;

/**
 * Created by Administrator on 2015/6/2.
 */
public class PrintCustomAddActivity extends FragmentActivity implements CustomerDeleteCallback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frame);
        init();
    }

    private void init() {
        if (getActionBar() != null) {
            getActionBar().setDisplayHomeAsUpEnabled(true);
            getActionBar().setTitle(R.string.back);
        }

        CustomerManageFragment fragment = new CustomerManageFragment();
        fragment.setListViewItemClick(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView tvId = (TextView) view.findViewById(R.id.id);
                TextView tvName = (TextView) view.findViewById(R.id.name);
                TextView tvAdd = (TextView) view.findViewById(R.id.address);
                TextView tvTel = (TextView) view.findViewById(R.id.tel);
                TextView tvComp = (TextView) view.findViewById(R.id.company);

                CustomerModel model = new CustomerModel();
                model.setId(tvId.getText().toString());
                model.setName(tvName.getText().toString());
                model.setCompany(tvComp.getText().toString());
                model.setTel(tvTel.getText().toString());
                model.setAddress(tvAdd.getText().toString());

                Intent it = new Intent();
                it.putExtra("CustomerModel", model);
                setResult(RESULT_OK, it);
                finish();
            }
        });

        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, fragment).commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add:
                Intent it = new Intent(this, AddCustomActivity.class);
                startActivityForResult(it, 0);
                return true;
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0 && resultCode == RESULT_OK) {
            init();
        }
    }

    @Override
    public void edit(int i, CustomerModel data) {

    }
}
