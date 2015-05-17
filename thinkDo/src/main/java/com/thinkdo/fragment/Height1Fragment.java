package com.thinkdo.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.content.res.Resources;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.thinkdo.activity.R;
import com.thinkdo.entity.HeightParam;

import java.io.IOException;

/**
 * Created by Administrator on 2015/5/16.
 */
public class Height1Fragment extends Fragment {
    private Heightfrag1Callback callback;

    public static Height1Fragment newInstance(HeightParam heightParam) {
        Height1Fragment fragment = new Height1Fragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("HeightParam", heightParam);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_height1, container, false);

        HeightParam heightParam = (HeightParam) getArguments().getSerializable("HeightParam");
        if (heightParam == null) return view;

        LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.linearLayout1);
        TextView str = (TextView) view.findViewById(R.id.tv_frontString);
        TextView min = (TextView) view.findViewById(R.id.tv_frontMin);
        TextView max = (TextView) view.findViewById(R.id.tv_frontMax);

        if (heightParam.getFrontHeight() != null) {
            linearLayout.setVisibility(View.VISIBLE);
            str.setText(heightParam.getFrontHeight().getExplain());
            min.setText(heightParam.getFrontHeight().getMin());
            max.setText(heightParam.getFrontHeight().getMax());
        }

        linearLayout = (LinearLayout) view.findViewById(R.id.linearLayout2);
        str = (TextView) view.findViewById(R.id.tv_rearString);
        min = (TextView) view.findViewById(R.id.tv_rearMin);
        max = (TextView) view.findViewById(R.id.tv_rearMax);

        if (heightParam.getRearHeight() != null) {
            linearLayout.setVisibility(View.VISIBLE);
            str.setText(heightParam.getRearHeight().getExplain());
            min.setText(heightParam.getRearHeight().getMin());
            max.setText(heightParam.getRearHeight().getMax());
        }

        if (heightParam.getHeightPicPath() != null) {
            ImageView iv = (ImageView) view.findViewById(R.id.imageView);
            Resources res = getResources();

            try {
                iv.setImageDrawable(new BitmapDrawable(res, res.getAssets().open(heightParam.getHeightPicPath())));
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        Button btn = (Button) view.findViewById(R.id.btn_next);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callback != null) callback.next1();
            }
        });
        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            callback = (Heightfrag1Callback) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException("Activity must implement Heightfrag1Callback");
        }
    }

    public interface Heightfrag1Callback {
        void next1();
    }

}
