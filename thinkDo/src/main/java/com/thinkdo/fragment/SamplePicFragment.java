package com.thinkdo.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.thinkdo.activity.R;
import com.thinkdo.application.MainApplication;
import com.thinkdo.net.NetConnect;
import com.thinkdo.net.NetSingleConnect;
import com.thinkdo.net.SocketClient;
import com.thinkdo.util.CommonUtil;
import com.thinkdo.util.MyDialog;

/**
 * Created by Administrator on 2015/6/4.
 */
public class SamplePicFragment extends Fragment {
    private SamplePicCallback callback;
    private boolean transFlag = false;
    private NetConnect socketClient;
    private NetSingleConnect loginConnect;
    private MyDialog myDialog;
    private ImageView iv1, iv2, iv3, iv4, iv5;
    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            if (!transFlag) return true;
            String reply = msg.getData().getString(MainApplication.head);
            if (reply == null) return true;
            int backCode = CommonUtil.getQuestCode(reply);
            int statusCode = CommonUtil.getStatusCode(reply);

            if (backCode == MainApplication.samplePictureUrl) {
                if (myDialog.isShow()) myDialog.dismiss();

                Bitmap bitmap = msg.getData().getParcelable(MainApplication.simpleBitmap);
                switch (statusCode) {
                    case 0:
                        iv1.setVisibility(View.VISIBLE);
                        iv1.setImageBitmap(bitmap);
                        break;
                    case 1:
                        iv2.setVisibility(View.VISIBLE);
                        iv2.setImageBitmap(bitmap);
                        break;
                    case 2:
                        iv3.setVisibility(View.VISIBLE);
                        iv3.setImageBitmap(bitmap);
                        break;
                    case 3:
                        iv4.setVisibility(View.VISIBLE);
                        iv4.setImageBitmap(bitmap);
                        break;
                    case 4:
                        iv5.setVisibility(View.VISIBLE);
                        iv5.setImageBitmap(bitmap);
                        break;
                }
            } else if (backCode == MainApplication.loginUrl) {
                String[] data = SocketClient.parseData(reply);
                if (data != null && data.length == 2) {
                    int i;
                    try {
                        i = Integer.parseInt(data[1]);
                    } catch (NumberFormatException e) {
                        i = 0;
                    }
                    MainApplication.device = data[0];
                    MainApplication.availableDay = i;
                    MainApplication.loginFlag = true;
                    startConnect();
                }
            } else if (backCode == MainApplication.errorUrl && statusCode != MainApplication.erroDiss) {
                myDialog.show(CommonUtil.getErrorString(statusCode, reply));
            } else if (callback != null) {
                callback.SamplePicNext(backCode);
            }
            return true;
        }
    });

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sample_pic, container, false);
        iv1 = (ImageView) view.findViewById(R.id.imageView1);
        iv2 = (ImageView) view.findViewById(R.id.imageView2);
        iv3 = (ImageView) view.findViewById(R.id.imageView3);
        iv4 = (ImageView) view.findViewById(R.id.imageView4);
        iv5 = (ImageView) view.findViewById(R.id.imageView5);
        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myDialog = new MyDialog(getActivity());

//        Message message = handler.obtainMessage(1);
//        Bundle bundle = message.getData() == null
//                ? new Bundle()
//                : message.getData();
//
//        bundle.putString(MainApplication.head, "15&0&0");
//        message.setData(bundle);
//        handler.sendMessageDelayed(message, 4000);
    }

    @Override
    public void onResume() {
        super.onResume();
        transFlag = true;
        startConnect();
    }

    public void startConnect() {
        if (MainApplication.availableDay > 0) {
            socketClient = new NetConnect(handler, MainApplication.samplePictureUrl, true);
        } else if (!MainApplication.loginFlag) {
            loginConnect = new NetSingleConnect(handler, MainApplication.loginUrl);
        } else {
            Toast.makeText(MainApplication.context, R.string.tip_recharge, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        transFlag = false;
        myDialog.dismiss();
        if (loginConnect != null) loginConnect.close();
        if (socketClient != null) socketClient.close();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            callback = (SamplePicCallback) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException("Activity must implement SamplePicCallback.");
        }
    }

    public interface SamplePicCallback {
        void SamplePicNext(int i);
    }
}
