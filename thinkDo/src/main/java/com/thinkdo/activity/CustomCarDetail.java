package com.thinkdo.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.thinkdo.db.CustomDbUtil;
import com.thinkdo.entity.TestVehicleInfoModel;
import com.thinkdo.fragment.HistoryUserDataFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/6/3.
 */
public class CustomCarDetail extends Activity {
    private String name;
    private ListView listView;
    private TestVehicleInfoModel infoModel;
    private List<SparseArray<String>> data;
    private final int col1 = 1;
    private final int col2 = 2;
    private final int col3 = 3;
    private final int col4 = 4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customcar_show);
        init();
    }

    private void init() {
        if (getActionBar() != null) {
            getActionBar().setDisplayHomeAsUpEnabled(true);
            getActionBar().setTitle(R.string.vehicle_info);
        }
        String clientId = getIntent().getStringExtra(HistoryUserDataFragment.clientIdKey);
        String testNo = getIntent().getStringExtra(HistoryUserDataFragment.testNoKey);
        name = getIntent().getStringExtra(HistoryUserDataFragment.customNameKey);
        listView = (ListView) findViewById(R.id.listView);
        new QueryDataAsyThread(clientId, testNo).start();
    }

    public void loadData() {
        data = new ArrayList<>();
        SparseArray<String> map = new SparseArray<>();
        //row0
        map.put(col1, getString(R.string.customer_name));
        map.put(col2, name);
        data.add(map);

        //row1
        map = new SparseArray<>();
        map.put(col1, getString(R.string.plate_number));
        map.put(col2, infoModel.getPlateNum());
        data.add(map);

        //row2
        map = new SparseArray<>();
        map.put(col1, getString(R.string.manufacturer));
        map.put(col2, infoModel.getManInfo());
        data.add(map);

        //row3
        map = new SparseArray<>();
        map.put(col1, getString(R.string.model));
        map.put(col2, infoModel.getModel());
        data.add(map);

        //row4
        map = new SparseArray<>();
        map.put(col1, getString(R.string.start_year));
        map.put(col2, infoModel.getStartYear());
        map.put(col3, getResources().getString(R.string.end_year));
        map.put(col4, infoModel.getEndYear());
        data.add(map);

        //row5
        map = new SparseArray<>();
        map.put(col1, getString(R.string.travelling_mileage));
        map.put(col2, infoModel.getCurUnit());
        data.add(map);

        //row6
        map = new SparseArray<>();
        map.put(col1, getString(R.string.malfunction));
        map.put(col2, infoModel.getMalfunction());
        data.add(map);

        //row7
        map = new SparseArray<>();
        map.put(col1, getString(R.string.repair_man));
        map.put(col2, infoModel.getOperator());
        data.add(map);

        //row8
        map = new SparseArray<>();
        map.put(col1, getString(R.string.notes));
        map.put(col2, infoModel.getRemark());
        data.add(map);
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

    class QueryDataAsyThread extends Thread {
        private String clientId, testNo;

        public QueryDataAsyThread(String clientId, String testNo) {
            this.clientId = clientId;
            this.testNo = testNo;
        }

        @Override
        public void run() {
            infoModel = new CustomDbUtil().queryTestVeclieInfo(clientId, testNo);
            loadData();
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    listView.setAdapter(new MyAdapter());
                }
            });
        }
    }

    class MyAdapter extends BaseAdapter {

        public MyAdapter() {

        }

        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            convertView = LayoutInflater.from(CustomCarDetail.this)
                    .inflate(R.layout.template_customcar_show_listview, parent, false);
            TextView tv1 = (TextView) convertView.findViewById(R.id.textView1);
            TextView tv2 = (TextView) convertView.findViewById(R.id.textView2);

            switch (position) {
                case 0:
                    convertView.setPadding(0, 32, 0, 0);
                    break;
                case 4:
                    View view = LayoutInflater.from(CustomCarDetail.this)
                            .inflate(R.layout.template_customcar_show_listview, parent, false);
                    TextView child1 = (TextView) view.findViewById(R.id.textView1);
                    TextView child2 = (TextView) view.findViewById(R.id.textView2);
                    child1.setText(data.get(position).get(col3));
                    child2.setText(data.get(position).get(col4));
                    ((ViewGroup) convertView).addView(view);
                    break;
                //data.size();
                case 8:
                    convertView.setPadding(0, 0, 0, 32);
                    break;
            }

            SparseArray<String> sparse = data.get(position);
            tv1.setText(sparse.get(col1));
            tv2.setText(sparse.get(col2));
            return convertView;
        }

    }

}
