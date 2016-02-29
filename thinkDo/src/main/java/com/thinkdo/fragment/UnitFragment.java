package com.thinkdo.fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;

import com.thinkdo.activity.R;
import com.thinkdo.application.MainApplication;
import com.thinkdo.entity.UnitEnum;


/**
 * Created by xh on 15/5/7.
 */
public class UnitFragment extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.fragment_unit);
        loadSummary(MainApplication.unitKey);
        loadSummary(MainApplication.toeUnitKey);
        getPreferenceManager().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);

    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        loadSummary(key);
    }

    public void loadSummary(String key) {
        String[] array = getResources().getStringArray(R.array.toeUnitEntry);
        SharedPreferences shared = PreferenceManager.getDefaultSharedPreferences(MainApplication.context);
        if (array == null || shared == null) return;
        ListPreference listPre = (ListPreference) findPreference(key);

        UnitEnum unit = UnitEnum.getUnitFromValue(shared.getString(key, "1"));

        switch (unit) {
            case degreeSecond:
                listPre.setSummary(array[0]);
                break;
            case degree:
                listPre.setSummary(array[1]);
                break;
            case mm:
                listPre.setSummary(array[2]);
                break;
            case inch:
                listPre.setSummary(array[3]);
                break;
            default:
                break;
        }
        recordCurUnit(key, unit);
    }

    public void recordCurUnit(String key, UnitEnum unit) {
        switch (key) {
            case MainApplication.unitKey:
                MainApplication.unit = unit;
                break;
            case MainApplication.toeUnitKey:
                MainApplication.toeUnit = unit;
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getPreferenceManager().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
    }
}
