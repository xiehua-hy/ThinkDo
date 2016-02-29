package com.thinkdo.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.thinkdo.activity.MPreferenceActivity;
import com.thinkdo.activity.R;
import com.thinkdo.view.BarItem;

public class SettingFragment extends Fragment implements View.OnClickListener {
    public static final String extraIntKey = "ExtraIntKey";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_preferences, container, false);

        BarItem barItem = (BarItem) rootView.findViewById(R.id.bar_unitSet);
        barItem.setOnClickListener(this);

        barItem = (BarItem) rootView.findViewById(R.id.bar_garageSet);
        barItem.setOnClickListener(this);

        barItem = (BarItem) rootView.findViewById(R.id.bar_printSet);
        barItem.setOnClickListener(this);

        barItem = (BarItem) rootView.findViewById(R.id.bar_language);
        barItem.setOnClickListener(this);

        barItem = (BarItem) rootView.findViewById(R.id.bar_addcar);
        barItem.setOnClickListener(this);

        return rootView;
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(getActivity(), MPreferenceActivity.class);
        switch (v.getId()) {
            case R.id.bar_unitSet:
                intent.putExtra(extraIntKey, 1);
                break;
            case R.id.bar_garageSet:
                intent.putExtra(extraIntKey, 2);
                break;
            case R.id.bar_printSet:
                intent.putExtra(extraIntKey, 3);
                break;
            case R.id.bar_addcar:
                intent.putExtra(extraIntKey, 4);
                break;
            case R.id.bar_language:
                intent.putExtra(extraIntKey, 5);
                break;
            default:
                intent = null;
        }
        if (intent != null) startActivity(intent);
    }
}
