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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.thinkdo.activity.R;
import com.thinkdo.entity.GloVariable;
import com.thinkdo.entity.HeightParam;

import java.io.IOException;

/**
 * Created by Administrator on 2015/5/16.
 */
public class Height2Fragment extends Fragment {
    private Heightfrag2Callback callback;

    public static Height2Fragment newInstance(HeightParam heightParam) {
        Height2Fragment fragment = new Height2Fragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("HeightParam", heightParam);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_height2, container, false);

        final HeightParam heightParam = (HeightParam) getArguments().getSerializable("HeightParam");
        if (heightParam == null) return view;

        final EditText leftFront = (EditText) view.findViewById(R.id.et_leftFront);
        final EditText rightFront = (EditText) view.findViewById(R.id.et_rightFront);
        final EditText leftRear = (EditText) view.findViewById(R.id.et_leftRear);
        final EditText rightRear = (EditText) view.findViewById(R.id.et_rightRear);

        TextView str = (TextView) view.findViewById(R.id.tv_leftFrontString);
        TextView min = (TextView) view.findViewById(R.id.tv_leftFrontMin);
        TextView mid = (TextView) view.findViewById(R.id.tv_leftFront);
        TextView max = (TextView) view.findViewById(R.id.tv_leftFrontMax);

        if (heightParam.getFrontHeight() != null) {
            leftFront.setVisibility(View.VISIBLE);
            rightFront.setVisibility(View.VISIBLE);
            leftFront.setHint(heightParam.getFrontHeight().getMid());
            rightFront.setHint(heightParam.getFrontHeight().getMid());

            str.setText(heightParam.getFrontHeight().getExplain());
            min.setText(heightParam.getFrontHeight().getMin());
            mid.setText(heightParam.getFrontHeight().getMid());
            max.setText(heightParam.getFrontHeight().getMax());

            str = (TextView) view.findViewById(R.id.tv_rightFrontString);
            min = (TextView) view.findViewById(R.id.tv_rightFrontMin);
            mid = (TextView) view.findViewById(R.id.tv_rightFront);
            max = (TextView) view.findViewById(R.id.tv_rightFrontMax);

            str.setText(heightParam.getFrontHeight().getExplain());
            min.setText(heightParam.getFrontHeight().getMin());
            mid.setText(heightParam.getFrontHeight().getMid());
            max.setText(heightParam.getFrontHeight().getMax());
        }

        str = (TextView) view.findViewById(R.id.tv_leftRearString);
        min = (TextView) view.findViewById(R.id.tv_leftRearMin);
        mid = (TextView) view.findViewById(R.id.tv_leftRear);
        max = (TextView) view.findViewById(R.id.tv_leftRearMax);

        if (heightParam.getRearHeight() != null) {
            leftRear.setVisibility(View.VISIBLE);
            rightRear.setVisibility(View.VISIBLE);
            leftRear.setHint(heightParam.getRearHeight().getMid());
            rightRear.setHint(heightParam.getRearHeight().getMid());

            str.setText(heightParam.getRearHeight().getExplain());
            min.setText(heightParam.getRearHeight().getMin());
            mid.setText(heightParam.getRearHeight().getMid());
            max.setText(heightParam.getRearHeight().getMax());

            str = (TextView) view.findViewById(R.id.tv_rightRearString);
            min = (TextView) view.findViewById(R.id.tv_rightRearMin);
            mid = (TextView) view.findViewById(R.id.tv_rightRear);
            max = (TextView) view.findViewById(R.id.tv_rightRearMax);

            str.setText(heightParam.getRearHeight().getExplain());
            min.setText(heightParam.getRearHeight().getMin());
            mid.setText(heightParam.getRearHeight().getMid());
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
                String sLeftFront = leftFront.getText().toString();
                String sRightFront = rightFront.getText().toString();
                String sLeftRear = leftRear.getText().toString();
                String sRightRear = rightRear.getText().toString();

                if (leftFront.getVisibility() == View.VISIBLE) {
                    if (sLeftFront.equals("")) sLeftFront = heightParam.getFrontHeight().getMid();
                    else if (isInvalid(sLeftFront, heightParam.getFrontHeight().getMin(), heightParam.getFrontHeight().getMax())) {
                        warn();
                        return;
                    }

                    if (sRightFront.equals("")) sRightFront = heightParam.getFrontHeight().getMid();
                    else if (isInvalid(sRightFront, heightParam.getFrontHeight().getMin(), heightParam.getFrontHeight().getMax())) {
                        warn();
                        return;
                    }
                }

                if (leftRear.getVisibility() == View.VISIBLE) {
                    if (sLeftRear.equals("")) sLeftRear = heightParam.getRearHeight().getMid();
                    else if (isInvalid(sLeftRear, heightParam.getRearHeight().getMin(), heightParam.getRearHeight().getMax())) {
                        warn();
                        return;
                    }

                    if (sRightRear.equals("")) sRightRear = heightParam.getRearHeight().getMid();
                    else if (isInvalid(sRightRear, heightParam.getRearHeight().getMin(), heightParam.getRearHeight().getMax())) {
                        warn();
                        return;
                    }
                }

                if(sLeftFront.equals("")){
                    sLeftFront = GloVariable.initValue;
                    sRightFront = GloVariable.initValue;
                }

                if(sLeftRear.equals("")){
                    sLeftRear = GloVariable.initValue;
                    sRightRear = GloVariable.initValue;
                }

                if (callback != null)
                    callback.next2(sLeftFront, sRightFront, sLeftRear, sRightRear);

            }
        });

        return view;
    }

    private boolean isInvalid(String value, String min, String max) {
        return Float.parseFloat(min) > Float.parseFloat(value)
                || Float.parseFloat(max) < Float.parseFloat(value);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            callback = (Heightfrag2Callback) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException("Activity must implement Heightfrag2Callback");
        }
    }

    private void warn() {
        Toast.makeText(getActivity(), R.string.tip_data_error, Toast.LENGTH_SHORT).show();
    }

    public interface Heightfrag2Callback {
        void next2(String leftFront, String rightFront, String leftRear, String rightRear);
    }
}
