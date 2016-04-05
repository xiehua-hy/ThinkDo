package com.thinkdo.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.thinkdo.activity.MaintenanceActivity;
import com.thinkdo.activity.R;

public class MaintenanceFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mainenance, container, false);
//        TextView tv = (TextView) view.findViewById(R.id.textView1);
//        tv.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(getActivity(), MaintenanceActivity.class));
//            }
//        });
        return view;
    }
}
