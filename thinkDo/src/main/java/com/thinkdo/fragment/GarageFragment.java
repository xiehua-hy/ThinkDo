package com.thinkdo.fragment;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.thinkdo.activity.R;
import com.thinkdo.application.MainApplication;

/**
 * Created by Administrator on 2015/5/7.
 */
public class GarageFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_garage, container, false);
        init(rootView);
        return rootView;
    }

    private void init(View view) {
        final EditText name = (EditText) view.findViewById(R.id.et_garageName);
        final EditText address = (EditText) view.findViewById(R.id.et_garageAddress);
        final EditText contactNum = (EditText) view.findViewById(R.id.et_garageContactNumber);
        final EditText post = (EditText) view.findViewById(R.id.et_garagePostcode);
        final EditText fax = (EditText) view.findViewById(R.id.et_garageFax);
        final EditText repairMan = (EditText) view.findViewById(R.id.et_garageRepairman);

        SharedPreferences shared = PreferenceManager.getDefaultSharedPreferences(MainApplication.context);

        name.setText(shared.getString(MainApplication.garageNameKey, MainApplication.emptyString));
        address.setText(shared.getString(MainApplication.garageAddressKey, MainApplication.emptyString));
        contactNum.setText(shared.getString(MainApplication.garageTelKey, MainApplication.emptyString));
        repairMan.setText(shared.getString(MainApplication.garageRepairManKey, MainApplication.emptyString));
        fax.setText(shared.getString(MainApplication.garageFaxKey, MainApplication.emptyString));
        post.setText(shared.getString(MainApplication.garagePostCodeKey, MainApplication.emptyString));

        Button btn = (Button) view.findViewById(R.id.btn_save);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String garageName = name.getText().toString();
                String garageAddress = address.getText().toString();
                String garageContactNum = contactNum.getText().toString();

                SharedPreferences shared = PreferenceManager.getDefaultSharedPreferences(MainApplication.context);
                final SharedPreferences.Editor edit = shared.edit();
                edit.putString(MainApplication.garageNameKey, garageName);
                edit.putString(MainApplication.garageAddressKey, garageAddress);
                edit.putString(MainApplication.garageTelKey, garageContactNum);
                edit.putString(MainApplication.garageRepairManKey, repairMan.getText().toString());
                edit.putString(MainApplication.garagePostCodeKey, post.getText().toString());
                edit.putString(MainApplication.garageFaxKey, fax.getText().toString());

                new AlertDialog.Builder(getActivity())
                        .setTitle(R.string.sure)
                        .setMessage(R.string.tip_add_data)
                        .setNegativeButton(R.string.cancel, null)
                        .setPositiveButton(R.string.sure, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                edit.apply();
                                Toast.makeText(MainApplication.context, R.string.tip_data_success_save, Toast.LENGTH_SHORT).show();
                                getActivity().finish();
                            }
                        })
                        .create()
                        .show();
            }
        });
    }
}
