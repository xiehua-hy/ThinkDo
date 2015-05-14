package com.thinkdo.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;

import android.util.DisplayMetrics;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.thinkdo.activity.R;
import com.thinkdo.db.VehicleDbUtil;
import com.thinkdo.entity.DataEnum;
import com.thinkdo.entity.GloVariable;


public class ManufacturerFragment extends Fragment implements AdapterView.OnItemClickListener, View.OnTouchListener {
    private GridView gridView;
    private final int bitmapDimen = 153;
    private final int countryCount = 3;
    private int count = 3000;
    private ManufacturerCallback callback;
    private GestureDetector gesture;

    private int[] manId_CN = {50000, 50072, 50099, 50001, 50002, 50037,
            50005, 50119, 50003, 50028, 50017, 50030, 50007, 50018, 50110,
            50008, 50032, 50015, 50016, 50058, 50073, 50108, 50019, 50114,
            50024, 50051, 50115, 50026, 50033, 50067, 50074, 50113, 50027,
            50009, 50029, 50106, 50041, 50036, 50075, 50105, 50091, 50109,
            50107, 50059, 50040, 50097, 50064, 50060, 50034, 50042, 50004,
            50088, 50043, 50118, 50044, 50045, 50038, 50047, 50095, 50101,
            50111, 50090, 50049, 50055, 50010, 50050, 50011, 50078, 50052,
            50053, 50054, 50023, 50089, 50020, 50056, 50082, 50093, 50035,
            50057, 50112, 50085, 50061, 50065, 50066, 50098, 50062, 50070,
            50092, 50094, 50013, 50071, 50063, 50006, 50087, 50021, 50096,
            50117, 50068, 50069, 50076, 50077, 50100, 50079, 50022, 50086,
            50080, 50012, 50014, 50031, 50081, 50039, 50103, 50083, 50084,
            51000, 51001, 51002, 51003, 51004, 51005, 51006, 51007, 51008,
            51009, 51010, 51011, 51012, 51013, 51014, 51015, 51016, 51017,
            51018, 51019};

    private int[] manId_EUP = {10000, 10001, 10002, 10003, 10004, 10005,
            10006, 10007, 10008, 10009, 10010, 10011, 10012, 10013, 10014,
            10015, 10016, 10017, 10018, 10019, 10020, 10021, 10022, 10023,
            10024, 10025, 10026, 10027, 10028, 10029, 10030, 10031, 10032,
            10033, 10034, 10035, 10036, 10037, 10038, 10039, 10040, 10041,
            10042, 10043, 10044, 10045, 10046, 10047, 10048, 10049, 10050,
            10051, 10052, 10053, 10054, 10055, 10056, 10057, 10058, 10059,
            10060, 10061, 10062, 10063};

    private int[] manId_NA = {30000, 30001, 30002, 30003, 30004, 30005,
            30006, 30007, 30008, 30009, 30010, 30011, 30012, 30013, 30014,
            30015, 30016, 30017, 30018, 30019, 30020, 30021, 30022, 30023,
            30024, 30025, 30026, 30027, 30028, 30029, 30030, 30031, 30032,
            30033, 30034, 30035, 30036, 30037, 30038, 30039, 30040, 30041,
            30042, 30043, 30044, 30045, 30046, 30047, 30048, 30049, 30050,
            30051, 30052, 30053, 30054, 30055, 30056, 30057, 30058, 30059,
            30060, 30061};

    private int[] ImageId_CN = {
            R.drawable.manu_search,
            R.drawable.manu_1, R.drawable.manu_2,
            R.drawable.manu_3, R.drawable.manu_4, R.drawable.manu_5,
            R.drawable.manu_6, R.drawable.manu_7, R.drawable.manu_8,
            R.drawable.manu_9, R.drawable.manu_10, R.drawable.manu_11,
            R.drawable.manu_12, R.drawable.manu_13, R.drawable.manu_14,
            R.drawable.manu_15, R.drawable.manu_16, R.drawable.manu_17,
            R.drawable.manu_18, R.drawable.manu_19, R.drawable.manu_20,
            R.drawable.manu_21, R.drawable.manu_22, R.drawable.manu_23,
            R.drawable.manu_24, R.drawable.manu_25, R.drawable.manu_26,
            R.drawable.manu_27, R.drawable.manu_28, R.drawable.manu_29,
            R.drawable.manu_30, R.drawable.manu_31, R.drawable.manu_32,
            R.drawable.manu_33, R.drawable.manu_34, R.drawable.manu_35,
            R.drawable.manu_36, R.drawable.manu_37, R.drawable.manu_38,
            R.drawable.manu_39, R.drawable.manu_40, R.drawable.manu_41,
            R.drawable.manu_42, R.drawable.manu_43, R.drawable.manu_44,
            R.drawable.manu_45, R.drawable.manu_46, R.drawable.manu_47,
            R.drawable.manu_48, R.drawable.manu_49, R.drawable.manu_50,
            R.drawable.manu_51, R.drawable.manu_52, R.drawable.manu_53,
            R.drawable.manu_54, R.drawable.manu_55, R.drawable.manu_56,
            R.drawable.manu_57, R.drawable.manu_58, R.drawable.manu_59,
            R.drawable.manu_60, R.drawable.manu_61, R.drawable.manu_62,
            R.drawable.manu_63, R.drawable.manu_64, R.drawable.manu_65,
            R.drawable.manu_66, R.drawable.manu_67, R.drawable.manu_68,
            R.drawable.manu_69, R.drawable.manu_70, R.drawable.manu_71,
            R.drawable.manu_72, R.drawable.manu_73, R.drawable.manu_74,
            R.drawable.manu_75, R.drawable.manu_76, R.drawable.manu_77,
            R.drawable.manu_78, R.drawable.manu_79, R.drawable.manu_80,
            R.drawable.manu_81, R.drawable.manu_82, R.drawable.manu_83,
            R.drawable.manu_84, R.drawable.manu_85, R.drawable.manu_86,
            R.drawable.manu_87, R.drawable.manu_88, R.drawable.manu_89,
            R.drawable.manu_90, R.drawable.manu_91, R.drawable.manu_92,
            R.drawable.manu_93, R.drawable.manu_94, R.drawable.manu_95,
            R.drawable.manu_96, R.drawable.manu_97, R.drawable.manu_98,
            R.drawable.manu_99, R.drawable.manu_100, R.drawable.manu_101,
            R.drawable.manu_102, R.drawable.manu_103, R.drawable.manu_104,
            R.drawable.manu_105, R.drawable.manu_106, R.drawable.manu_107,
            R.drawable.manu_108, R.drawable.manu_109, R.drawable.manu_110,
            R.drawable.manu_111, R.drawable.manu_112, R.drawable.manu_113,
            R.drawable.manu_114, R.drawable.manu_116, R.drawable.manu_117,
            R.drawable.manu_118, R.drawable.manu_119, R.drawable.manu_120,
            R.drawable.manu_121, R.drawable.manu_122, R.drawable.manu_123,
            R.drawable.manu_124, R.drawable.manu_125, R.drawable.manu_126,
            R.drawable.manu_127, R.drawable.manu_128, R.drawable.manu_129,
            R.drawable.manu_130, R.drawable.manu_131, R.drawable.manu_132,
            R.drawable.manu_133, R.drawable.manu_134, R.drawable.manu_135};

    private int[] ImageId_EUP = {
            R.drawable.manu_search,
            R.drawable.eur_manu_1, R.drawable.eur_manu_2,
            R.drawable.eur_manu_3, R.drawable.eur_manu_4,
            R.drawable.eur_manu_5, R.drawable.eur_manu_6,
            R.drawable.eur_manu_7, R.drawable.eur_manu_8,
            R.drawable.eur_manu_9, R.drawable.eur_manu_10,
            R.drawable.eur_manu_11, R.drawable.eur_manu_12,
            R.drawable.eur_manu_13, R.drawable.eur_manu_14,
            R.drawable.eur_manu_15, R.drawable.eur_manu_16,
            R.drawable.eur_manu_17, R.drawable.eur_manu_18,
            R.drawable.eur_manu_19, R.drawable.eur_manu_20,
            R.drawable.eur_manu_21, R.drawable.eur_manu_22,
            R.drawable.eur_manu_23, R.drawable.eur_manu_24,
            R.drawable.eur_manu_25, R.drawable.eur_manu_26,
            R.drawable.eur_manu_27, R.drawable.eur_manu_28,
            R.drawable.eur_manu_29, R.drawable.eur_manu_30,
            R.drawable.eur_manu_31, R.drawable.eur_manu_32,
            R.drawable.eur_manu_33, R.drawable.eur_manu_34,
            R.drawable.eur_manu_35, R.drawable.eur_manu_36,
            R.drawable.eur_manu_37, R.drawable.eur_manu_38,
            R.drawable.eur_manu_39, R.drawable.eur_manu_40,
            R.drawable.eur_manu_41, R.drawable.eur_manu_42,
            R.drawable.eur_manu_43, R.drawable.eur_manu_44,
            R.drawable.eur_manu_45, R.drawable.eur_manu_46,
            R.drawable.eur_manu_47, R.drawable.eur_manu_48,
            R.drawable.eur_manu_49, R.drawable.eur_manu_50,
            R.drawable.eur_manu_51, R.drawable.eur_manu_52,
            R.drawable.eur_manu_53, R.drawable.eur_manu_54,
            R.drawable.eur_manu_55, R.drawable.eur_manu_56,
            R.drawable.eur_manu_57, R.drawable.eur_manu_58,
            R.drawable.eur_manu_59, R.drawable.eur_manu_60,
            R.drawable.eur_manu_61, R.drawable.eur_manu_62,
            R.drawable.eur_manu_63, R.drawable.eur_manu_64};

    private int[] ImageId_NA = {
            R.drawable.manu_search,
            R.drawable.na_manu_1, R.drawable.na_manu_2,
            R.drawable.na_manu_3, R.drawable.na_manu_4,
            R.drawable.na_manu_5, R.drawable.na_manu_6,
            R.drawable.na_manu_7, R.drawable.na_manu_8,
            R.drawable.na_manu_9, R.drawable.na_manu_10,
            R.drawable.na_manu_11, R.drawable.na_manu_12,
            R.drawable.na_manu_13, R.drawable.na_manu_14,
            R.drawable.na_manu_15, R.drawable.na_manu_16,
            R.drawable.na_manu_17, R.drawable.na_manu_18,
            R.drawable.na_manu_19, R.drawable.na_manu_20,
            R.drawable.na_manu_21, R.drawable.na_manu_22,
            R.drawable.na_manu_23, R.drawable.na_manu_24,
            R.drawable.na_manu_25, R.drawable.na_manu_26,
            R.drawable.na_manu_27, R.drawable.na_manu_28,
            R.drawable.na_manu_29, R.drawable.na_manu_30,
            R.drawable.na_manu_31, R.drawable.na_manu_32,
            R.drawable.na_manu_33, R.drawable.na_manu_34,
            R.drawable.na_manu_35, R.drawable.na_manu_36,
            R.drawable.na_manu_37, R.drawable.na_manu_38,
            R.drawable.na_manu_39, R.drawable.na_manu_40,
            R.drawable.na_manu_41, R.drawable.na_manu_42,
            R.drawable.na_manu_43, R.drawable.na_manu_44,
            R.drawable.na_manu_45, R.drawable.na_manu_46,
            R.drawable.na_manu_47, R.drawable.na_manu_48,
            R.drawable.na_manu_49, R.drawable.na_manu_50,
            R.drawable.na_manu_51, R.drawable.na_manu_52,
            R.drawable.na_manu_53, R.drawable.na_manu_54,
            R.drawable.na_manu_55, R.drawable.na_manu_56,
            R.drawable.na_manu_57, R.drawable.na_manu_58,
            R.drawable.na_manu_59, R.drawable.na_manu_60,
            R.drawable.na_manu_61, R.drawable.na_manu_62};

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            callback = (ManufacturerCallback) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(
                    "Activity must implement ManufacturerCallback.");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_manfacturer, container, false);
        init(rootView);
        return rootView;
    }

    private void init(View view) {
        gridView = (GridView) view.findViewById(R.id.gridView);

        DisplayMetrics metrics = getResources().getDisplayMetrics();

        int windowWidth = metrics.widthPixels;
        int divider = (int) Math.ceil(metrics.density);
        int bitmapWidth = (int) (bitmapDimen * metrics.density);
        int padding = (windowWidth % (bitmapWidth + divider)) / 2;
        gridView.setPadding(padding, 0, padding, 0);
        gridView.setAdapter(new MGridAdapter());
        gridView.setOnItemClickListener(this);
        gridView.setOnTouchListener(this);
        gesture = new GestureDetector(getActivity(), new MyGestureListener());

    }

    public String getTip() {
        switch (count % countryCount) {
            case 0:
                return getResources().getString(R.string.tip_chinese_vehicle);
            case 1:
                return getResources().getString(R.string.tip_american_vehicle);
            default:
                return getResources().getString(R.string.tip_european_vehicle);
        }
    }

    public int[] getManuID() {
        switch (count % countryCount) {
            case 0:
                return manId_CN;
            case 1:
                return manId_NA;
            default:
                return manId_EUP;
        }
    }


    public int[] getImageId() {
        switch (count % countryCount) {
            case 0:
                return ImageId_CN;
            case 1:
                return ImageId_NA;
            default:
                return ImageId_EUP;
        }
    }


    //点击事件
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (position == 0) {
            //进入搜索栏
            return;
        }

        DataEnum dbIndex = DataEnum.standard;
        int manId = getManuID()[position - 1];
        String info = new VehicleDbUtil().queryManufacturerInfo(manId);

        if (callback != null) {
            callback.onManufacturerSelected(String.valueOf(manId), info, null, dbIndex);
        }

    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        return gesture.onTouchEvent(motionEvent);
    }


    class MGridAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return getImageId().length;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View view, ViewGroup parent) {
            ImageView imageView;
            if (view == null) {
                imageView = new ImageView(getActivity());
                imageView.setScaleType(ImageView.ScaleType.CENTER);
            } else {
                imageView = (ImageView) view;
            }
            imageView.setImageResource(getImageId()[position]);
            return imageView;
        }
    }

    class MyGestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            float yRel = Math.abs(e1.getY() - e2.getY());
            float xRel = e1.getX() - e2.getX();
            if (Math.abs(xRel) > 80) {
                if (xRel - yRel > 0) {
                    count--;
                    gridDataChanged();
                    return true;
                } else if (xRel + yRel < 0) {
                    count++;
                    gridDataChanged();
                    return true;
                }
            }
            return super.onFling(e1, e2, velocityX, velocityY);
        }
    }

    public void gridDataChanged() {
        ((BaseAdapter) gridView.getAdapter()).notifyDataSetChanged();
        Toast.makeText(GloVariable.context, getTip(), Toast.LENGTH_SHORT).show();
    }

    public interface ManufacturerCallback {
        void onManufacturerSelected(String manId, String manInfo, String pyIndex,DataEnum dataEnum);
    }


}
