package com.thinkdo.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.thinkdo.db.CustomDbUtil;
import com.thinkdo.db.VehicleDbUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2015/5/20.
 */
public class SearchActivity extends Activity {
    public static final String manufacturerId = "manufacturerId";
    public static final String manufacturerInfo = "manufacturerInfo";
    public static final String pyIndex = "pyIndex";
    public static final String dbIndex = "dbIndex";

    private Handler backHandler;
    private List<Map<String, String>> data = new ArrayList<>();
    private VehicleDbUtil utility;
    private CustomDbUtil utilityDef;
    private MyAdapter adapter;
    private String pinyinIndex;

    private Handler handler = new Handler(new Handler.Callback() {

        @Override
        public boolean handleMessage(Message msg) {
            if (msg.what == 0) {
                if (data != null) {
                    adapter.notifyDataSetChanged();
                }
            }
            return true;
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        init();
    }

    private void init() {
        HandlerThread handlerThread = new HandlerThread("handlerThread");
        handlerThread.start();

        ImageView iv_back = (ImageView) findViewById(R.id.imageView_back);
        ListView listView = (ListView) findViewById(R.id.listView1);
        final EditText et_search = (EditText) findViewById(R.id.et_search);
        final ImageView iv_remove = (ImageView) findViewById(R.id.imageView_remove);

        iv_remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et_search.setText("");
            }
        });

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Map<String, String> map = data.get(position);
                if (map.get("id") != null) {
                    Intent intent = new Intent();
                    intent.putExtra(pyIndex, pinyinIndex);
                    intent.putExtra(manufacturerInfo, map.get("name"));
                    intent.putExtra(manufacturerId, map.get("id"));
                    intent.putExtra(dbIndex, Integer.parseInt(map.get("dbIndex")));
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        });

        et_search.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                pinyinIndex = s.toString();

                if (pinyinIndex.equals("")) {
                    iv_remove.setVisibility(View.INVISIBLE);
                } else {
                    iv_remove.setVisibility(View.VISIBLE);
                }

                backHandler.sendEmptyMessage(0);
            }
        });


        Map<String, String> map = new HashMap<String, String>() {
            /**
             *
             */
            private static final long serialVersionUID = 1L;

            {
                put("name", "");
                put("vehicle", "");

            }
        };

        data.add(map);
        adapter = new MyAdapter();
        listView.setAdapter(adapter);

        backHandler = new Handler(handlerThread.getLooper(), new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                if (msg.what == 0) {
                    data.clear();
                    if (pinyinIndex.equals("")) {
                        Map<String, String> map = new HashMap<>();
                        map.put("name", "");
                        map.put("vehicle", "");
                        data.add(map);
                    } else {
                        List<Map<String, String>> temp = utility.getManufactures(pinyinIndex);
                        if (temp != null)
                            data.addAll(temp);

                        temp = utilityDef.getManufactures(pinyinIndex);
                        if (temp != null)
                            data.addAll(temp);

                        if (data.size() == 0) {
                            Map<String, String> map = new HashMap<>();
                            map.put("name", getString(R.string.search_no_result));
                            map.put("vehicle", "");
                            data.add(map);
                        }
                    }

                    handler.sendEmptyMessage(0);
                    return true;
                }
                return false;
            }
        });

        utility = new VehicleDbUtil();
        utilityDef = new CustomDbUtil();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        backHandler.getLooper().quit();
    }

    class MyAdapter extends BaseAdapter {

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
        public View getView(int position, View view, ViewGroup parent) {
            Holder holder;
            if (view == null) {
                holder = new Holder();
                view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.template_search_listview, parent, false);
                holder.name = (TextView) view.findViewById(R.id.textView1);
                holder.id = (TextView) view.findViewById(R.id.textView2);
                view.setTag(holder);
            } else {
                holder = (Holder) view.getTag();
            }
            Map<String, String> map = data.get(position);
            holder.name.setText(String.format("%s  %s", map.get("name"), map.get("vehicle")));
            holder.id.setText(map.get("id"));
            return view;
        }

        class Holder {
            TextView name;
            TextView id;
        }

    }
}
