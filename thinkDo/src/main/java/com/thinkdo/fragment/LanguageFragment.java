package com.thinkdo.fragment;

import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;

import com.thinkdo.activity.MPreferenceActivity;
import com.thinkdo.activity.R;
import com.thinkdo.application.MainApplication;

import java.util.Locale;

/**
 * Created by Administrator on 2015/8/7.
 */
public class LanguageFragment extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.fragment_language);
        loadSummary(MainApplication.languageKey);
        getPreferenceManager().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        loadSummary(key);
        updateConfig(key);
    }

    public void loadSummary(String key) {
        String[] entry = MainApplication.context.getResources().getStringArray(R.array.languageEntry);
        SharedPreferences shared = PreferenceManager.getDefaultSharedPreferences(MainApplication.context);
        if (entry == null || shared == null) return;
        ListPreference listPre = (ListPreference) findPreference(key);
        String value = shared.getString(key, "");

        if (value.equals("")) {
            switch (getResources().getConfiguration().locale.toString()) {
                case "zh_CN":
                    listPre.setSummary(entry[0]);
                    return;
                case "zh_TW":
                    listPre.setSummary(entry[1]);
                    return;
                default:
                    listPre.setSummary(entry[2]);
                    return;
            }
        }

        switch (value) {
            case "1":
                listPre.setSummary(entry[0]);
                break;
            case "2":
                listPre.setSummary(entry[1]);
                break;
            case "3":
                listPre.setSummary(entry[2]);
                break;
            default:

        }
    }

    public void updateConfig(String key) {
        SharedPreferences shared = PreferenceManager.getDefaultSharedPreferences(MainApplication.context);
        if (shared == null) return;
        String value = shared.getString(key, "1");
        Configuration config = getResources().getConfiguration();
        switch (value) {
            case "1":
                config.locale = Locale.CHINA;
                break;
            case "2":
                config.locale = new Locale("zh", "TW");
                break;
            case "3":
                config.locale = Locale.ENGLISH;
                break;
        }
        ((MPreferenceActivity) getActivity()).updata = true;
        getResources().updateConfiguration(config, getResources().getDisplayMetrics());
    }
}
