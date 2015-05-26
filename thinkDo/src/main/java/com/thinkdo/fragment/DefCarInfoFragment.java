package com.thinkdo.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.thinkdo.activity.R;
import com.thinkdo.db.CustomDbUtil;
import com.thinkdo.entity.OperOftenDataTotalModel;
import com.thinkdo.util.CommonUtil;

/**
 * Created by Administrator on 2015/5/21.
 */
public class DefCarInfoFragment extends Fragment {
    private DefCarInfoCallback callback;
    private int toeUnit = 1, unit = 1;
    private boolean lockMM = false, lockIn = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_def_add_info, container, false);
        init(view);
        return view;
    }

    private void init(View view) {
        final EditText manInfo = (EditText) view.findViewById(R.id.et_manufacturer);
        final EditText vehicle = (EditText) view.findViewById(R.id.et_model);
        final EditText startY = (EditText) view.findViewById(R.id.et_startYear);
        final EditText endY = (EditText) view.findViewById(R.id.et_endYear);

        final EditText wheelModel1 = (EditText) view.findViewById(R.id.et_wheelModel1);
        final EditText wheelModel2 = (EditText) view.findViewById(R.id.et_wheelModel2);
        final EditText wheelModel3 = (EditText) view.findViewById(R.id.et_wheelModel3);
        final EditText diameterMM = (EditText) view.findViewById(R.id.et_diameterMM);
        final EditText diameterInch = (EditText) view.findViewById(R.id.et_diameterInch);

        Button btn = (Button) view.findViewById(R.id.btn_next);
        RadioGroup radioGroup1 = (RadioGroup) view.findViewById(R.id.radioGroup_toeSet);
        RadioGroup radioGroup2 = (RadioGroup) view.findViewById(R.id.radioGroup_unitSet);

        final TextView sign1 = (TextView) view.findViewById(R.id.textView_sign1);
        final TextView sign2 = (TextView) view.findViewById(R.id.textView_sign2);

        InputFilter lengthFilter = new InputFilter() {

            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                String dValue = dest.toString();
                String[] splitArray = dValue.split("//.");
                if (splitArray.length > 1) {
                    String dotValue = splitArray[1];
                    int diff = dotValue.length();
                    if (diff > 0) {
                        return source.subSequence(dstart, end - diff);
                    }
                }
                return source;
            }
        };

        diameterInch.setFilters(new InputFilter[]{lengthFilter});

        diameterMM.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                lockMM = true;
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!lockIn) {
                    if ("".equals(s.toString())) {
                        diameterInch.setText("");
                    } else {
                        diameterInch.setText(CommonUtil.format(
                                Float.parseFloat(s.toString()) / 25.4f, 1));
                    }

                }

                lockMM = false;
            }
        });

        diameterInch.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                lockIn = true;
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!lockMM) {
                    if ("".equals(s.toString())) {
                        diameterMM.setText("");
                    } else {
                        diameterMM.setText(CommonUtil.format(
                                Float.parseFloat(s.toString()) * 25.4f, 0));
                    }
                }
                lockIn = false;
            }
        });

        manInfo.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().equals("")) {
                    sign1.setText("*");
                } else {
                    sign1.setText("");
                }
            }
        });

        vehicle.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().equals("")) {
                    sign2.setText("*");
                } else {
                    sign2.setText("");
                }
            }
        });

        radioGroup1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radio0:
                        toeUnit = 1;
                        break;
                    case R.id.radio1:
                        toeUnit = 0;
                        break;
                    case R.id.radio2:
                        toeUnit = 2;
                        break;
                    case R.id.radio3:
                        toeUnit = 3;
                        break;
                    default:
                        toeUnit = 0;
                        break;
                }
            }
        });

        radioGroup2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radio4:
                        unit = 1;
                        break;
                    case R.id.radio5:
                        unit = 0;
                        break;
                    default:
                        unit = 0;
                        break;
                }
            }
        });

        wheelModel1.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                int f1, f2, f3;
                if ("".equals(s.toString())) {
                    f1 = 0;
                } else {
                    f1 = Integer.parseInt(s.toString());
                }

                if ("".equals(wheelModel2.getText().toString())) {
                    f2 = 0;
                } else {
                    f2 = Integer.parseInt(wheelModel2.getText().toString());
                }

                if ("".equals(wheelModel3.getText().toString())) {
                    f3 = 0;
                } else {
                    f3 = Integer.parseInt(wheelModel3.getText().toString());
                }

                diameterMM.setText(CommonUtil.format(f1 * f2 * 2 / 100 + f3
                        * 25.4f, 0));

            }
        });

        wheelModel2.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                int f1, f2, f3;
                if ("".equals(wheelModel1.getText().toString())) {
                    f1 = 0;
                } else {
                    f1 = Integer.parseInt(wheelModel1.getText().toString());
                }

                if ("".equals(s.toString())) {
                    f2 = 0;
                } else {
                    f2 = Integer.parseInt(s.toString());
                }

                if ("".equals(wheelModel3.getText().toString())) {
                    f3 = 0;
                } else {
                    f3 = Integer.parseInt(wheelModel3.getText().toString());
                }

                diameterMM.setText(CommonUtil.format(f1 * f2 * 2 / 100 + f3
                        * 25.4f, 0));
            }
        });

        wheelModel3.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                int f1, f2, f3;
                if ("".equals(wheelModel1.getText().toString())) {
                    f1 = 0;
                } else {
                    f1 = Integer.parseInt(wheelModel1.getText().toString());
                }

                if ("".equals(wheelModel2.getText().toString())) {
                    f2 = 0;
                } else {
                    f2 = Integer.parseInt(wheelModel2.getText().toString());
                }

                if ("".equals(s.toString())) {
                    f3 = 0;
                } else {
                    f3 = Integer.parseInt(s.toString());
                }

                diameterMM.setText(CommonUtil.format(f1 * f2 * 2 / 100 + f3 * 25.4f, 0));
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if (manInfo.getText().toString().equals("") || vehicle.getText().toString().equals("")) {
                    warn();
                } else {
                    OperOftenDataTotalModel dataTotal = new OperOftenDataTotalModel();
                    CustomDbUtil databaseTool = new CustomDbUtil();

                    String sManufacturer = manInfo.getText().toString();
                    String sModel = vehicle.getText().toString();
                    String sStartYear = startY.getText().toString();
                    String sEndYear = endY.getText().toString();

                    String sManufacturerID = databaseTool
                            .getManId(sManufacturer);
                    String sModelID = databaseTool.getVehicleId();

                    String wheelType = wheelModel1.getText().toString() + getString(R.string.division)
                            + wheelModel2.getText().toString() + "R"
                            + wheelModel3.getText().toString();

                    if (sStartYear.equals("")) {
                        sStartYear = getString(R.string.hint_start_year);
                    }

                    if (sEndYear.equals("")) {
                        sEndYear = getString(R.string.hint_end_year);
                    }

                    String mDiameterMM = diameterMM.getText().toString();
                    String mDiameterInch = diameterInch.getText().toString();

                    dataTotal.setManId(sManufacturerID);
                    dataTotal.setManInfo(sManufacturer);

                    dataTotal.setVehicleId(sModelID);
                    dataTotal.setVehicleInfo(sModel);

                    dataTotal.setStartYear(sStartYear);
                    dataTotal.setEndYear(sEndYear);
                    dataTotal.setDate(CommonUtil.getCurTime());

                    dataTotal.setWheelType(wheelType);
                    dataTotal.setDiameterMM(mDiameterMM);
                    dataTotal.setDiameterInch(mDiameterInch);
                    dataTotal.setPyIndex(CommonUtil.getPinYinHeadChar(sModel));

                    if (callback != null) {
                        callback.onDefineInfoNext(dataTotal, unit, toeUnit);
                    }
                }
            }
        });

    }

    private void warn() {
        new AlertDialog.Builder(getActivity(), AlertDialog.THEME_HOLO_LIGHT)
                .setMessage(R.string.tip_data_error).setPositiveButton(R.string.sure, null)
                .create().show();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            callback = (DefCarInfoCallback) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException("Activity must implement DefCarInfoCallback");
        }
    }

    public interface DefCarInfoCallback {
        void onDefineInfoNext(OperOftenDataTotalModel dataTotal, int unit, int toeUnit);
    }
}
