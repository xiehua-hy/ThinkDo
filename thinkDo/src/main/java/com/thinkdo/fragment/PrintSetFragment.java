package com.thinkdo.fragment;

import android.app.Fragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.thinkdo.activity.R;
import com.thinkdo.application.MainApplication;

/**
 * Created by xh on 15/5/7.
 */
public class PrintSetFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_print_set, container, false);
        init(rootView);
        return rootView;
    }

    private void init(View view) {
        RadioGroup radioGroup = (RadioGroup) view.findViewById(R.id.radioGroup1);
        RadioButton mini = (RadioButton) view.findViewById(R.id.radio_mini);
        RadioButton standard = (RadioButton) view.findViewById(R.id.radio_standard);

        SharedPreferences shared = PreferenceManager.getDefaultSharedPreferences(MainApplication.context);
        int currentValues = shared.getInt(MainApplication.printSetKey, 0);

        switch (currentValues) {
            case 0:
                standard.setChecked(true);
                break;
            case 1:
                mini.setChecked(true);
                break;
            default:
                standard.setChecked(true);
        }

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                SharedPreferences shared = PreferenceManager.getDefaultSharedPreferences(MainApplication.context);
                SharedPreferences.Editor edit = shared.edit();

                switch (checkedId) {
                    case R.id.radio_standard:
                        edit.putInt(MainApplication.printSetKey, 0);
                        break;
                    case R.id.radio_mini:
                        edit.putInt(MainApplication.printSetKey, 1);
                        break;
                }
                edit.apply();
            }
        });
    }
}
