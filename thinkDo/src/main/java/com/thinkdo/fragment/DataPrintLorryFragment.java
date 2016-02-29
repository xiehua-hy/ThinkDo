package com.thinkdo.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.thinkdo.activity.R;
import com.thinkdo.application.MainApplication;
import com.thinkdo.entity.LorryDataItem;
import com.thinkdo.entity.ValuesPair;
import com.thinkdo.net.NetQuest;
import com.thinkdo.view.BarPrint;

import java.util.List;
import java.util.Map;


/**
 * Created by Administrator on 2015/9/11.
 */
public class DataPrintLorryFragment extends Fragment {
    private LorryDataItem data;
    private DataPrintCallback callback;
    private int saveBtnVisible = View.VISIBLE;
    private boolean connect = true;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (connect) new NetQuest(MainApplication.printUrl);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_data_print_lorry, container, false);
        init(rootView);

        return rootView;
    }

    private void init(View view) {
        ListView listView = (ListView) view.findViewById(R.id.listView);
        //
        float titleWidth = getResources().getDimension(R.dimen.bar_print_title_width);
        float contentWidth = 4 * getResources().getDimension(R.dimen.bar_print_content_width);
        float gapWidth = 4 * getResources().getDisplayMetrics().density;
        float windowWidth = getResources().getDisplayMetrics().widthPixels;

        int padding = (int) ((windowWidth - titleWidth - gapWidth - contentWidth) / 2);

        listView.setPadding(padding, 0, padding, 0);
        listView.setAdapter(new MyAdapter(data));

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            callback = (DataPrintCallback) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException("Activity must implement DataPrintCallback.");
        }
    }

    public void setLorryData(LorryDataItem data) {
        this.data = data;
    }

    public void setConnect(boolean connect){
        this.connect = connect;
    }

    public void setSaveBtnVisible(int visible){
        this.saveBtnVisible = visible;
    }

    public interface DataPrintCallback {
        /**
         * @param position 0 save   <br/>
         *                 1 print
         **/
        void dataPrintNext(int position);
    }

    class MyAdapter extends BaseAdapter {
        private List<Map> list;

        public MyAdapter(LorryDataItem lorryData) {
            if (lorryData != null) {
                data.unitConvert();
                list = data.generatePrintDataMap();
            }
        }

        @Override
        public int getCount() {
            if (list != null) return list.size() + 2;
            return 0;
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
            if (position != list.size() + 1) {

                if (view == null || view.getTag() == null) {
                    view = new BarPrint(getActivity());
                    view.setTag("bar");
                }

                if (position == 0) {
                    //form title
                    ((BarPrint) view).setTitleText(R.string.parameters);
                    ((BarPrint) view).setBeforeText(R.string.before);
                    ((BarPrint) view).setAfterText(R.string.after);
                    ((BarPrint) view).setMinText(R.string.min);
                    ((BarPrint) view).setMaxText(R.string.max);
                    ((BarPrint) view).setContextBg(Color.WHITE);
                } else {
                    Map map = list.get(position - 1);

                    String title = (String) map.get(LorryDataItem.titleKey);
                    ValuesPair pair = (ValuesPair) map.get(LorryDataItem.valuePairKey);
                    Integer backColor = (Integer) map.get(LorryDataItem.backgroundColorKey);

                    ((BarPrint) view).setTitleText(title);
                    ((BarPrint) view).setContextBg(backColor);

                    if (pair != null) {
                        //set test value
                        ((BarPrint) view).setBeforeText(pair.getPreReal());
                        ((BarPrint) view).setAfterText(pair.getPreReal());
                        ((BarPrint) view).setMinText(pair.getMin());
                        ((BarPrint) view).setMaxText(pair.getMax());
                    } else {
                        ((BarPrint) view).setBeforeText("");
                        ((BarPrint) view).setAfterText("");
                        ((BarPrint) view).setMinText("");
                        ((BarPrint) view).setMaxText("");
                    }
                }

            } else {

                if (view == null || view.getTag() != null) {
                    view = LinearLayout.inflate(getActivity(), R.layout.include_print_btn, null);

                    //btn
                    Button btn = (Button) view.findViewById(R.id.btn_save);
                    btn.setVisibility(saveBtnVisible);
                    btn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //save
                            if (callback != null) callback.dataPrintNext(0);
                        }
                    });

                    btn = (Button) view.findViewById(R.id.btn_print);
                    btn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //print
                            if (callback != null) callback.dataPrintNext(1);

                        }
                    });
                }
            }
            return view;
        }
    }

}
