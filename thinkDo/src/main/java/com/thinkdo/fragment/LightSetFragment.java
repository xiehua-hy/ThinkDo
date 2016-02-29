package com.thinkdo.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.thinkdo.activity.R;
import com.thinkdo.view.NumberSeekBar;

/**
 * Created by Administrator on 2015/6/23.
 */
public class LightSetFragment extends Fragment {
    private NumberSeekBar bar1, bar2, bar3, bar4, bar5;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_light_set, container, false);
        init(view);
        return view;
    }

    private void init(View view) {
        bar1 = (NumberSeekBar) view.findViewById(R.id.bar1);
        bar2 = (NumberSeekBar) view.findViewById(R.id.bar2);
        bar3 = (NumberSeekBar) view.findViewById(R.id.bar3);
        bar4 = (NumberSeekBar) view.findViewById(R.id.bar4);
        bar5 = (NumberSeekBar) view.findViewById(R.id.bar5);

        Button btn = (Button) view.findViewById(R.id.btn_submit);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
