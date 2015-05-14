package com.thinkdo.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.thinkdo.activity.R;
import com.thinkdo.db.CustomDbUtil;

import java.util.List;
import java.util.Map;

public class PickCusCarFragment extends Fragment {
    public static final String colInfo = "info";
    public static final String colID = "ID";
    private ListView listView;
    private CusManufacturerCallback callback;
    private List<Map<String, String>> data;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        listView = new ListView(getActivity());

        new Thread(new Runnable() {
            @Override
            public void run() {
                data = new CustomDbUtil().queryAllManufacturer();
                if (data == null) return;

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        listView.setAdapter(new MyAdapter());
                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                if (callback != null) {
                                    Map<String, String> map = data.get(i);
                                    callback.onCusManSelected(map.get(colID), map.get(colInfo), null, 1);
                                }
                            }
                        });
                    }
                });
            }
        }).start();

        return listView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            callback = (CusManufacturerCallback) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException("Activity must implement CusManufacturerCallback.");
        }
    }

    public interface CusManufacturerCallback {
        void onCusManSelected(String manId, String manInfo, String pyIndex, int dbIndex);
    }

    class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int position, View view, ViewGroup viewGroup) {
            Holder holder;
            if (view == null) {
                holder = new Holder();
                view = LayoutInflater.from(getActivity()).inflate(R.layout.template_expand_listview, viewGroup, false);
                holder.info = (TextView) view.findViewById(R.id.textView1);
                holder.id = (TextView) view.findViewById(R.id.textView2);
                view.setTag(holder);
            } else {
                holder = (Holder) view.getTag();
            }
            holder.id.setVisibility(View.GONE);

            holder.id.setText(data.get(position).get(colID));
            holder.info.setText(data.get(position).get(colInfo));
            return view;
        }

        class Holder {
            TextView info;
            TextView id;
        }
    }
}
