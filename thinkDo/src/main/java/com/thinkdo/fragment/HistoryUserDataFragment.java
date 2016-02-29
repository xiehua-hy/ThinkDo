package com.thinkdo.fragment;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.thinkdo.activity.CustomCarDetail;
import com.thinkdo.activity.DataPrintActivity;
import com.thinkdo.activity.DataPrintLorryActivity;
import com.thinkdo.activity.R;
import com.thinkdo.application.MainApplication;
import com.thinkdo.db.CustomDbUtil;
import com.thinkdo.entity.FormMetaModel;

import java.util.Collections;
import java.util.List;

/**
 * Created by Administrator on 2015/6/2.
 */
public class HistoryUserDataFragment extends Fragment {
    public static String clientIdKey = "ClientIdKey";
    public static String testNoKey = "TestNoKey";
    public static String customNameKey = "customNameKey";

    private List<FormMetaModel> data;
    private ListView listView;
    private TextView textView;
    private String clientId;
    private boolean desc = true;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_history, container, false);
        listView = (ListView) view.findViewById(R.id.listView1);
        textView = (TextView) view.findViewById(R.id.sort);
        new LoadDataThread().start();
        return view;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getActivity().getMenuInflater().inflate(R.menu.menu_custom_detail, menu);
        menu.setHeaderTitle("select");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        final AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            case R.id.detail:
                Intent intent = new Intent(getActivity(), CustomCarDetail.class);
                intent.putExtra(clientIdKey, data.get(info.position).getCustomerId());
                intent.putExtra(testNoKey, data.get(info.position).getTestNo());
                intent.putExtra(customNameKey, data.get(info.position).getName());
                startActivity(intent);
                return true;

            case R.id.delete:
                new AlertDialog.Builder(getActivity())
                        .setTitle(R.string.sure)
                        .setMessage(R.string.tip_sure_delete)
                        .setNegativeButton(R.string.cancel, null)
                        .setPositiveButton(R.string.sure, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        CustomDbUtil dbTool = new CustomDbUtil();
                                        FormMetaModel model = data.get(info.position);
                                        dbTool.deleteCustomerVehicle(model.getCustomerId(), model.getTestNo());
                                        dbTool.deleteCustomerTestData(model.getCustomerId(), model.getTestNo());
                                    }
                                }).start();
                                data.remove(info.position);
                                ((MyAdapter) listView.getAdapter()).notifyDataSetChanged();
                            }
                        }).create().show();
                return true;

        }
        return super.onContextItemSelected(item);
    }

    public void setCustomerId(String customerId) {
        this.clientId = customerId;
    }

    class LoadDataThread extends Thread {
        @Override
        public void run() {
            data = new CustomDbUtil().queryFormMeta(clientId);
            Collections.sort(data);

            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    init();
                }
            });
        }
    }

    private void init() {
        listView.setAdapter(new MyAdapter());
        registerForContextMenu(listView);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                Intent intent = MainApplication.isCar
                        ? new Intent(getActivity(), DataPrintActivity.class)
                        : new Intent(getActivity(), DataPrintLorryActivity.class);

                intent.putExtra(clientIdKey, data.get(position).getCustomerId());
                intent.putExtra(testNoKey, data.get(position).getTestNo());
                startActivity(intent);
            }
        });

        textView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Drawable drawable;

                drawable = desc
                        ? getResources().getDrawable(R.drawable.ic_action_collapse)
                        : getResources().getDrawable(R.drawable.ic_action_expand);

                textView.setCompoundDrawablesWithIntrinsicBounds(null, null, drawable, null);
                Collections.reverse(data);
                ((MyAdapter) listView.getAdapter()).notifyDataSetChanged();
                desc = !desc;

            }
        });
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
        public View getView(int position, View convertView, ViewGroup parent) {
            Holder holder;
            if (convertView == null) {
                holder = new Holder();
                convertView = LayoutInflater.from(getActivity()).inflate(R.layout.template_fragment_userdata, parent, false);
                holder.name = (TextView) convertView.findViewById(R.id.name);
                holder.manufacturer = (TextView) convertView.findViewById(R.id.manufacturer);
                holder.plateNo = (TextView) convertView.findViewById(R.id.plateNo);
                holder.date = (TextView) convertView.findViewById(R.id.date);
                holder.clientId = (TextView) convertView.findViewById(R.id.id);
                holder.testNo = (TextView) convertView.findViewById(R.id.testNo);
                convertView.setTag(holder);
            } else {
                holder = (Holder) convertView.getTag();
            }

            holder.name.setText(data.get(position).getName());
            holder.manufacturer.setText(data.get(position).getVehicle());
            holder.plateNo.setText(data.get(position).getPlateNo());
            holder.date.setText(data.get(position).getDate());
            holder.clientId.setText(data.get(position).getCustomerId());
            holder.testNo.setText(data.get(position).getTestNo());

            return convertView;
        }

        class Holder {
            TextView name;
            TextView manufacturer;
            TextView plateNo;
            TextView date;
            TextView clientId;
            TextView testNo;
        }
    }
}
