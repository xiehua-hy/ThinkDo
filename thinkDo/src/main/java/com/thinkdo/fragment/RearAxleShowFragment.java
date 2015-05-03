package com.thinkdo.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.thinkdo.activity.R;

/**
 * Created by Administrator on 2015/4/30.
 */
public class RearAxleShowFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_rearaxle_show, container, false);
        return rootView;
    }
}
