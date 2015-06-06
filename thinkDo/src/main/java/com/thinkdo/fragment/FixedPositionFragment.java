package com.thinkdo.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.thinkdo.activity.FastTestActivity;
import com.thinkdo.activity.MainActivity;
import com.thinkdo.activity.MaintenanceActivity;
import com.thinkdo.activity.R;
import com.thinkdo.activity.SpecialTestActivity;
import com.thinkdo.activity.UserDataActivity;

public class FixedPositionFragment extends Fragment implements View.OnClickListener {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_fixedposition, container, false);
        Button btn = (Button) rootView.findViewById(R.id.btn_start);
        btn.setOnClickListener(this);

        btn = (Button) rootView.findViewById(R.id.btn_fast);
        btn.setOnClickListener(this);

        btn = (Button) rootView.findViewById(R.id.btn_special);
        btn.setOnClickListener(this);

        btn = (Button) rootView.findViewById(R.id.btn_photo);
        btn.setOnClickListener(this);

        btn = (Button) rootView.findViewById(R.id.btn_user);
        btn.setOnClickListener(this);
        return rootView;
    }

    @Override
    public void onClick(View v) {
        if (getActivity() == null) return;
        switch (v.getId()) {
            case R.id.btn_start:
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_fast:
                intent = new Intent(getActivity(), FastTestActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_special:
                intent = new Intent(getActivity(), SpecialTestActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_photo:
                intent = new Intent(getActivity(), MaintenanceActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_user:
                intent = new Intent(getActivity(), UserDataActivity.class);
                startActivity(intent);
                break;
        }
    }
}
