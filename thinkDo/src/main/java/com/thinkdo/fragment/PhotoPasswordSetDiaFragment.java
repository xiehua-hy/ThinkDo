package com.thinkdo.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.thinkdo.activity.R;
import com.thinkdo.application.MainApplication;

/**
 * Created by Administrator on 2015/6/24.
 */
public class PhotoPasswordSetDiaFragment extends DialogFragment {
    private EditText et1, et2;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_photo_password_set, null);
        et1 = (EditText) view.findViewById(R.id.et1);
        et2 = (EditText) view.findViewById(R.id.et2);

        builder.setView(view)
                .setNegativeButton(R.string.cancel, null)
                .setPositiveButton(R.string.sure, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String str1 = et1.getText().toString();
                        String str2 = et2.getText().toString();

                        if (str1.equals("") || str2.equals("")) {
                            Toast.makeText(getActivity(), "password cannot be null", Toast.LENGTH_SHORT).show();
                        } else if (!str1.equals(str2)) {
                            Toast.makeText(getActivity(), "Inconsistent password", Toast.LENGTH_SHORT).show();
                        } else {
                            SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(MainApplication.context).edit();
                            editor.putString(MainApplication.photoPasswordKey, str1);
                            editor.apply();
                            dismiss();
                        }

                    }
                });

        return builder.create();

//        return super.onCreateDialog(savedInstanceState);
    }


}
