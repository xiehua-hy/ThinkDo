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
import com.thinkdo.entity.GloVariable;

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

        SharedPreferences shared = PreferenceManager.getDefaultSharedPreferences(GloVariable.context);

        name.setText(shared.getString(GloVariable.garageNameKey, GloVariable.emptyString));
        address.setText(shared.getString(GloVariable.garageAddressKey, GloVariable.emptyString));
        contactNum.setText(shared.getString(GloVariable.garageTelKey, GloVariable.emptyString));
        repairMan.setText(shared.getString(GloVariable.garageRepairManKey, GloVariable.emptyString));
        fax.setText(shared.getString(GloVariable.garageFaxKey, GloVariable.emptyString));
        post.setText(shared.getString(GloVariable.garagePostCodeKey, GloVariable.emptyString));

        Button btn = (Button) view.findViewById(R.id.btn_save);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String garageName = name.getText().toString();
                String garageAddress = address.getText().toString();
                String garageContactNum = contactNum.getText().toString();

                SharedPreferences shared = PreferenceManager.getDefaultSharedPreferences(GloVariable.context);
                final SharedPreferences.Editor edit = shared.edit();
                edit.putString(GloVariable.garageNameKey, garageName);
                edit.putString(GloVariable.garageAddressKey, garageAddress);
                edit.putString(GloVariable.garageTelKey, garageContactNum);
                edit.putString(GloVariable.garageRepairManKey, repairMan.getText().toString());
                edit.putString(GloVariable.garagePostCodeKey, post.getText().toString());
                edit.putString(GloVariable.garageFaxKey, fax.getText().toString());

                new AlertDialog.Builder(getActivity())
                        .setTitle(R.string.sure)
                        .setMessage(R.string.tip_add_data)
                        .setNegativeButton(R.string.cancel, null)
                        .setPositiveButton(R.string.sure, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                edit.apply();
                                Toast.makeText(GloVariable.context, R.string.tip_data_success_save, Toast.LENGTH_SHORT).show();
                                getActivity().finish();
                            }
                        })
                        .create()
                        .show();
            }
        });
    }
}
