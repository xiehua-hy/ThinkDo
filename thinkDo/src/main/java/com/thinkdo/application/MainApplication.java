package com.thinkdo.application;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.preference.PreferenceManager;
//import android.util.Log;
import android.util.SparseArray;

import com.thinkdo.activity.R;
import com.thinkdo.entity.UnitEnum;
import com.thinkdo.util.XmlInit;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Locale;

/**
 * Created by xh on 15/5/8.
 */
public class MainApplication extends Application {
    public static final boolean isCar = false;
    //key
    public static final String curVersion = "version";
    public static final String hostIpKey = "hostIpKey";
    public static final String hostPortKey = "hostPortKey";
    public static final String garageNameKey = "garageNameKey";
    public static final String garageAddressKey = "garageAddressKey";
    public static final String garageTelKey = "garageTelKey";
    public static final String garageRepairManKey = "repairManKey";
    public static final String garagePostCodeKey = "garagePostCodeKey";
    public static final String garageFaxKey = "garageFaxKey";
    public static final String printSetKey = "printSetKey";
    public static final String unitKey = "unitKey";
    public static final String toeUnitKey = "toeUnitKey";
    public static final String photoPasswordKey = "photoPasswordKey";
    public static final String languageKey = "languageKey";

    //defaultValues
    public static final String defaultIp = "192.168.100.4";
    public static final int defaultPort = 6000;

    //other
    public static final int pushcarUrl = 1;
    public static final int kingpinUrl = 2;
    public static final int fastTestUrl = 3;
    public static final int samplePictureUrl = 6;
    public static final int specialTestUrl = 7;
    public static final int synchShowUrl = 8;
    public static final int frontShowUrl = 9;
    public static final int rearShowUrl = 10;
    public static final int printUrl = 11;
    public static final int testDataUrl = 12;
    public static final int printContent = 13;
    public static final int hostSaveDataUrl = 14;
    public static final int homeUrl = 15;
    public static final int synchCar = 16;
    public static final int upCar = 17;
    public static final int downCar = 18;
    public static final int loginUrl = 19;
    public static final int registerUrl = 20;
    public static final int shineSet = 21;
    public static final int getShine = 22;
    public static final int setAxle = 23;

    public static final int errorUrl = -2;
    public static final int erroDiss = -88;

    public static final String emptyString = "";
    public static final String initValue = "99.99";

    public static final String carSqliteName = "SKDDATA.db";
    public static final String customSqliteName = "SKDSELFDATA.db";
    public static final String head = "head";

    public static final String simpleBitmap = "Bitmap";
    public static final int stadb = 0;
    public static final int cusdb = 1;

    public static boolean loginFlag = false;
    public static String device;
    public static int availableDay = 0;

    public static Context context;
    public static String ip = defaultIp;
    public static int port = defaultPort;

    public static UnitEnum unit;
    public static UnitEnum toeUnit;

    public static SparseArray<String> errorMsg;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        init();
    }

    private void init() {
        SharedPreferences shared = PreferenceManager.getDefaultSharedPreferences(this);
        ip = shared.getString(hostIpKey, defaultIp);
        port = shared.getInt(hostPortKey, defaultPort);

        unit = UnitEnum.getUnitFromValue(shared.getString(unitKey, "1"));
        toeUnit = UnitEnum.getUnitFromValue(shared.getString(toeUnitKey, "1"));
        String versionName = shared.getString(curVersion, "");
        updateLanguage();
        new XmlInit(this);
        errorMsg = new SparseArray<String>() {
            {
                put(77, getString(R.string.error_1));
                put(-77, getString(R.string.error_77));
                put(-69, getString(R.string.error_69));
                put(-68, getString(R.string.error_68));
                put(-67, getString(R.string.error_67));
                put(-66, getString(R.string.error_66));
                put(-65, getString(R.string.error_65));
                put(-64, getString(R.string.error_64));
                put(-63, getString(R.string.error_63));
                put(-62, getString(R.string.error_62));
                put(-61, getString(R.string.error_61));
                put(-60, getString(R.string.error_60));
                put(-59, getString(R.string.error_59));
                put(-58, getString(R.string.error_58));
                put(-57, getString(R.string.error_57));
                put(-56, getString(R.string.error_56));
                put(-55, getString(R.string.error_55));
                put(16, getString(R.string.error_16));
                put(18, getString(R.string.error_18));
                put(19, getString(R.string.error_19));
                put(20, getString(R.string.error_20));
                put(21, getString(R.string.error_21));
                put(22, getString(R.string.error_22));
                put(23, getString(R.string.error_23));
            }
        };


        File sqliteCar = getDatabasePath(carSqliteName);
        File sqliteCustom = getDatabasePath(customSqliteName);
        try {
            if (!sqliteCar.exists() || !versionName.equals(getPackageManager().getPackageInfo(getPackageName(), 0).versionName)) {

                copy(carSqliteName, sqliteCar);

                SharedPreferences.Editor editor = shared.edit();
                editor.putString(curVersion, getPackageManager().getPackageInfo(getPackageName(), 0).versionName);
                editor.apply();
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        if (!sqliteCustom.exists()) {
            copy(customSqliteName, sqliteCustom);
        }
    }

    public void updateLanguage() {
        SharedPreferences shared = PreferenceManager.getDefaultSharedPreferences(this);
        if (shared == null) return;
        String language = shared.getString(MainApplication.languageKey, "");
        Configuration config = getResources().getConfiguration();
        switch (language) {
            case "1":
                config.locale = Locale.CHINA;
                break;
            case "2":
                config.locale = new Locale("zh", "TW");
                break;
            case "3":
                config.locale = Locale.ENGLISH;
                break;

        }
        getResources().updateConfiguration(config, getResources().getDisplayMetrics());
    }


    public static String getErrorInfo(int position) {
        if (errorMsg == null) return null;
        String msg = errorMsg.get(position);
        return msg == null ? context.getString(R.string.error_2) : msg;
    }

    private void copy(String assetsFileName, File des) {
        try {
            des.getParentFile().mkdirs();
            InputStream in = getAssets().open(assetsFileName);
            OutputStream out = new FileOutputStream(des);

            byte[] bytes = new byte[1024];
            int len;

            while ((len = in.read(bytes)) != -1) {
                out.write(bytes, 0, len);
            }

            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
