package com.thinkdo.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.thinkdo.activity.R;
import com.thinkdo.db.DBUtil;
import com.thinkdo.entity.GloVariable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PickCarFragment extends Fragment {
    private String manId, manInfo, pyIndex;
    private ExpandableListView expand;
    private VehicleCallbacks callback;
    private List<String> data;
    private int dbIndex = GloVariable.stadb;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (manId == null) return null;
        View rootView = inflater.inflate(R.layout.fragment_pick_car, container, false);
        expand = (ExpandableListView) rootView.findViewById(R.id.expandableListView);

        new Thread(new Runnable() {
            @Override
            public void run() {
                data = new DBUtil().queryAllCar(manId, pyIndex, dbIndex);
                getActivity().runOnUiThread(
                        new Runnable() {
                            @Override
                            public void run() {
                                initView();
                            }
                        }
                );
            }
        }
        ).start();

        return rootView;
    }

    public void initView() {
        expand.setAdapter(new MyAdapter());
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            callback = (VehicleCallbacks) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException("Activity must implement VehicleIDCallbacks.");
        }
    }

    public void setParams(String manID, String manInfo, String pyIndex, int dbIndex) {
        setManID(manID);
        setManInfo(manInfo);
        setPyIndex(pyIndex);
        setDbIndex(dbIndex);
    }

    public void setManID(String manId) {
        this.manId = manId;
    }

    public void setManInfo(String manInfo) {
        this.manInfo = manInfo;
    }

    public void setPyIndex(String pyIndex) {
        this.pyIndex = pyIndex;
    }

    public void setDbIndex(int dbIndex) {
        this.dbIndex = dbIndex;
    }

    public interface VehicleCallbacks {
        /**
         * 选中车辆
         *
         * @param vehicleID 车型ID
         * @param year      年份
         */
        void onVehicleSelected(String manId, String manInfo, String vehicleID, String year, int dbIndex);
    }

    class MyAdapter extends BaseExpandableListAdapter {
        private final String idKey = "id";
        private final String carKey = "car";
        private List<String> groupData;
        private SparseIntArray groupPos;

        private SparseArray<List<String>> childData;
        private SparseArray<SparseIntArray> childPos;

        public MyAdapter() {
            handleGroupData();
            childData = new SparseArray<>();
            childPos = new SparseArray<>();
        }

        @Override
        public int getGroupCount() {
            return groupData.size();
        }

        @Override
        public int getChildrenCount(int groupPosition) {
            if (childData.get(groupPosition) == null) handleChildData(groupPosition);
            return childData.get(groupPosition).size();
        }

        private void handleGroupData() {
            groupData = new ArrayList<>();
            groupPos = new SparseIntArray();
            String last = "";
            for (int i = 0; i < data.size(); i++) {
                String[] array = data.get(i).split(";");
                if (!array[0].equals(last)) {
                    last = array[0];
                    groupData.add(array[0]);
                    groupPos.put(groupPos.size(), i);
                }
            }
            groupPos.put(groupPos.size(), data.size());
        }

        private void handleChildData(int groupPosition) {
            SparseIntArray pos = new SparseIntArray();
            List<String> mData = new ArrayList<>();

            int start = groupPos.get(groupPosition);
            int end = groupPos.get(groupPosition + 1);
            String last = "";
            for (int i = start; i < end; i++) {
                String[] array = data.get(i).split(";");
                if (!array[1].equals(last)) {
                    last = array[1];
                    pos.put(pos.size(), i);
                    mData.add(array[1]);
                }
            }

            childPos.put(groupPosition, pos);
            childData.put(groupPosition, mData);
        }

        private List<Map<String, String>> handleGrandData(int groupPosion, int childPosition) {
            int start = childPos.get(groupPosion).get(childPosition);
            int end = childPosition == childPos.get(groupPosion).size() - 1 ?
                    groupPos.get(groupPosion + 1) :
                    childPos.get(groupPosion).get(childPosition + 1);

            List<Map<String, String>> mgrand = new ArrayList<>();
            for (int i = start; i < end; i++) {
                String[] array = data.get(i).split(";");
                Map<String, String> map = new HashMap<>();
                map.put(carKey, array[2]);
                map.put(idKey, array[3]);
                mgrand.add(map);
            }
            return mgrand;
        }

        @Override
        public Object getGroup(int groupPosition) {
            return null;
        }

        @Override
        public Object getChild(int groupPosition, int childPosition) {
            return null;
        }

        @Override
        public long getGroupId(int groupPosition) {
            return 0;
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return 0;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }

        @Override
        public View getGroupView(int groupPosition, boolean isExpanded,
                                 View convertView, ViewGroup parent) {
            TextView tv;
            if (convertView == null) {
                convertView = LayoutInflater.from(getActivity()).inflate(
                        R.layout.template_expand_listview_group, parent, false);
                tv = (TextView) convertView.findViewById(R.id.textView1);
                convertView.setTag(tv);
            } else {
                tv = (TextView) convertView.getTag();
            }
            tv.setText(groupData.get(groupPosition));
            return convertView;
        }

        @Override
        public View getChildView(final int groupPosition, final int childPosition,
                                 boolean isLastChild, View convertView, ViewGroup parent) {

            convertView = LayoutInflater.from(getActivity())
                    .inflate(R.layout.template_expand_listview_child, parent, false);
            final TextView tv = (TextView) convertView.findViewById(R.id.textView1);
            final ImageView iv = (ImageView) convertView.findViewById(R.id.imageView1);
            LinearLayout layout = (LinearLayout) convertView.findViewById(R.id.linearLayout2);
            final LinearLayout parentLay = (LinearLayout) convertView.findViewById(R.id.linearLayout1);
            tv.setText(childData.get(groupPosition).get(childPosition));

            layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (parentLay.getChildCount() < 2) {
                        ListView listView = new ListView(getActivity());
                        SimpleAdapter adapter = new SimpleAdapter(getActivity(),
                                handleGrandData(groupPosition, childPosition),
                                R.layout.template_expand_listview,
                                new String[]{carKey, idKey}, new int[]{R.id.textView1, R.id.textView2});
                        listView.setAdapter(adapter);
                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                TextView textView2 = (TextView) view.findViewById(R.id.textView2);
                                String year = tv.getText().toString();
                                String vehicleID = textView2.getText().toString();
                                if (callback != null) {
                                    callback.onVehicleSelected(manId, manInfo, vehicleID, year, dbIndex);
                                }
                            }
                        });

                        setListViewHeightBasedOnChildren(listView);
                        parentLay.addView(listView);
                        iv.setImageResource(R.drawable.ic_action_collapse);
                    } else if (parentLay.getChildAt(1).getVisibility() == View.VISIBLE) {
                        parentLay.getChildAt(1).setVisibility(View.GONE);
                        iv.setImageResource(R.drawable.ic_action_expand);
                    } else {
                        parentLay.getChildAt(1).setVisibility(View.VISIBLE);
                        iv.setImageResource(R.drawable.ic_action_collapse);
                    }
                }
            });

            return convertView;
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return false;
        }

        /**
         *  计算 listView 的高度
         */
        public void setListViewHeightBasedOnChildren(ListView listView) {
            SimpleAdapter listAdapter = (SimpleAdapter) listView.getAdapter();
            if (listAdapter == null) {
                return;
            }

            int totalHeight = 0;
            for (int i = 0; i < listAdapter.getCount(); i++) {
                View listItem = listAdapter.getView(i, null, listView);
                listItem.measure(0, 0);
                totalHeight += listItem.getMeasuredHeight();
            }

            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    totalHeight);

            params.height = totalHeight
                    + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
            listView.setLayoutParams(params);
        }
    }
}
