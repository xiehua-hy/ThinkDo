package com.thinkdo.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.thinkdo.activity.R;

public class KingpinFragment extends Fragment {
    private KinPingCallback callback;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_kingpin, container, false);
        initView(rootView);
        return rootView;
    }

    private void initView(View view) {
        Button btn = (Button) view.findViewById(R.id.btn_next);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (callback != null)
                    callback.kinPingNext();
            }
        });
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            callback = (KinPingCallback) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException("Activity must implement KinPingCallback");
        }

    }

    public interface KinPingCallback {
        void kinPingNext();
    }
}
