package com.thinkdo.entity;

import android.content.Context;
import android.util.SparseArray;

/**
 * Created by Administrator on 2015/5/7.
 */
public class GloVariable {
    //key
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

    public static final int errorUrl = -2;
    public static final int erroDiss = -88;

    public static final String carSqliteName = "SKDDATA.db";
    public static final String customSqliteName = "SKDSELFDATA.db";
    public static final String head = "head";
    public static final String simpleBitmap = "Bitmap";

    public static final int stadb = 0;
    public static final int cusdb = 1;

    public static Context context;
    public static final String emptyString = "";
    public static final String initValue = "99.99";
    public static String ip = defaultIp;
    public static int port = defaultPort;

    public static UnitEnum unit;
    public static UnitEnum toeUnit;


    protected static SparseArray<String> errorMsg = new SparseArray<String>() {
        {
            put(77, "未知错误!");
            put(-77, "图像信号错误!");
            put(-69, "图像离左边缘太近!");
            put(-68, "图像离右边缘太近!");
            put(-67, "图像离上边缘太近!");

            put(-66, "图像离下边缘太近!");
            put(-65, "图像太亮!");
            put(-64, "图像太暗!");
            put(-63, "边缘点太少!");
            put(-62, "边缘太模糊!");
            put(-61, "图像粘连!");
            put(-60, "找到的圆少于要求数量!");
            put(-59, "找到的圆多于要求数量!");
            put(-58, "坐标无效!");
            put(-57, "边框不完整!");

            put(-56, "图象不稳定!");
            put(-55, "采集卡或者相机错误");
            put(18, "左前轮: ");
            put(19, "右前轮: ");
            put(20, "左后轮: ");
            put(21, "右后轮: ");
            put(22, "转太前");
            put(23, "转太后");
        }
    };

    public static String getErrorInfo(int position) {
        String msg = errorMsg.get(position);
        return msg == null ? "图像采集失败!" : msg;
    }
}
