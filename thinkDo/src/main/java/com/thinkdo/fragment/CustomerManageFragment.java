package com.thinkdo.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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

import com.thinkdo.activity.R;
import com.thinkdo.db.CustomDbUtil;
import com.thinkdo.entity.CustomerModel;

import java.util.List;

/**
 * Created by Administrator on 2015/6/2.
 */
public class CustomerManageFragment extends Fragment {
    private AdapterView.OnItemClickListener itemClickListener;
    private CustomerDeleteCallback callback;
    private List<CustomerModel> data;
    private ListView listView;

    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    if (listView.getAdapter() != null) {
                        ((MyAdapter) listView.getAdapter()).notifyDataSetChanged();
                    } else {
                        init();
                    }

                    break;
                case 1:
                    if (callback != null) callback.edit(2, null);
                    break;
            }
            return true;
        }
    });

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_customer_manager, container, false);
        listView = (ListView) view.findViewById(R.id.listView);
        new HandleDataThread().start();
        return view;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        getActivity().getMenuInflater().inflate(R.menu.menu_customer, menu);
        menu.setHeaderTitle("select");
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        final AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            case R.id.edit:
                if (callback != null && data != null) callback.edit(1, data.get(info.position));
                return true;
            case R.id.delete:
                new AlertDialog.Builder(getActivity())
                        .setMessage(R.string.tip_sure_delete)
                        .setTitle(R.string.sure)
                        .setNegativeButton(R.string.cancel, null)
                        .setPositiveButton(R.string.sure, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                new HandleDataThread(data.get(info.position).getId()).start();
                                data.remove(info.position);
                                ((MyAdapter) listView.getAdapter()).notifyDataSetChanged();
                            }
                        }).create().show();
                return true;
        }

        return super.onContextItemSelected(item);
    }

    private void init() {
        MyAdapter adapter = new MyAdapter();
        listView.setAdapter(adapter);
        registerForContextMenu(listView);
        if (itemClickListener != null) listView.setOnItemClickListener(itemClickListener);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0 && resultCode == Activity.RESULT_OK) {
            new HandleDataThread().start();
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            callback = (CustomerDeleteCallback) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException("Activity must implement CustomerDeleteCallback.");
        }
    }

    public void setListViewItemClick(AdapterView.OnItemClickListener OnItemClickListener) {
        this.itemClickListener = OnItemClickListener;
    }

    public interface CustomerDeleteCallback {
        //1 表示修改 2 代表删除
        void edit(int i, CustomerModel data);
    }

    class HandleDataThread extends Thread {
        private String customerId;

        public HandleDataThread() {
        }

        public HandleDataThread(String customerId) {
            this.customerId = customerId;
        }

        @Override
        public void run() {
            if (customerId == null) {
                //查询所有车
                data = new CustomDbUtil().queryAllCustomer();
                handler.sendEmptyMessage(0);
            } else {
                //delete
                CustomDbUtil util = new CustomDbUtil();
                util.deleteCustomer(customerId);
                util.deleteCustomerVehicle(customerId);
                util.deleteCustomerTestData(customerId);
                handler.sendEmptyMessage(1);
            }
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
        public View getView(int position, View view, ViewGroup parent) {
            Holder holder;
            if (view == null) {
                view = LayoutInflater.from(getActivity()).inflate(R.layout.template_fragment_customer_listview,
                        parent, false);
                holder = new Holder();
                holder.id = (TextView) view.findViewById(R.id.id);
                holder.name = (TextView) view.findViewById(R.id.name);
                holder.company = (TextView) view.findViewById(R.id.company);
                holder.tel = (TextView) view.findViewById(R.id.tel);
                holder.address = (TextView) view.findViewById(R.id.address);
                view.setTag(holder);
            } else {
                holder = (Holder) view.getTag();
            }
            CustomerModel customer = data.get(position);
            holder.id.setText(customer.getId());
            holder.name.setText(customer.getName());
            holder.company.setText(customer.getCompany());
            holder.tel.setText(customer.getTel());
            holder.address.setText(customer.getAddress());

            return view;
        }

        class Holder {
            TextView id;
            TextView name;
            TextView company;
            TextView tel;
            TextView address;
        }
    }
}
