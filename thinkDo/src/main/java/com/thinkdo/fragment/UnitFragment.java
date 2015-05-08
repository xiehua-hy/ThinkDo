package com.thinkdo.fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;

import com.thinkdo.activity.R;
import com.thinkdo.entity.GloVariable;


/**
 * Created by xh on 15/5/7.
 */
public class UnitFragment extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.fragment_unit);
        loadSummary(GloVariable.unitKey);
        loadSummary(GloVariable.toeUnitKey);
        getPreferenceManager().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);

    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        loadSummary(key);
    }

     public void loadSummary(String key){
         String[] array = getResources().getStringArray(R.array.toeUnitEntry);
         SharedPreferences shared = PreferenceManager.getDefaultSharedPreferences(GloVariable.context);
         if (array == null || shared == null) return;
         ListPreference listPre= (ListPreference) findPreference(key);
         switch (shared.getString(key , "0")){
             case "0":
                 listPre.setSummary(array[0]);
                 break;
             case "1":
                 listPre.setSummary(array[1]);
                 break;
             case "2":
                 listPre.setSummary(array[2]);
                 break;
             case "3":
                 listPre.setSummary(array[3]);
                 break;
             default:
                 break;
         }
     }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getPreferenceManager().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
    }
}
