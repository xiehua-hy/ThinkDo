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
    public static final int pushcarUrl =1;
    public static final int kingpinUrl =2;
    public static final int fastTestUrl =3;
    public static final int samplePictureUrl =6;
    public static final int specialTestUrl=7;
    public static final int synchShowUrl =8;
    public static final int frontShowUrl =9;
    public static final int rearShowUrl =10;
    public static final int printUrl=11;
    public static final int testDataUrl=12;
    public static final int printContent=13;
    public static final int hostSaveDataUrl=14;
    public static final int homeUrl =15;
    public static final int synchCar=16;
    public static final int upCar = 17;
    public static final int downCar = 18;

    public static final int errorUrl =-2;
    public static final int erroDiss=-88;

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
            put(77, "δ֪����!");
            put(-77, "ͼ���źŴ���!");
            put(-69, "ͼ�������Ե̫��!");
            put(-68, "ͼ�����ұ�Ե̫��!");
            put(-67, "ͼ�����ϱ�Ե̫��!");

            put(-66, "ͼ�����±�Ե̫��!");
            put(-65, "ͼ��̫��!");
            put(-64, "ͼ��̫��!");
            put(-63, "��Ե��̫��!");
            put(-62, "��Ե̫ģ��!");
            put(-61, "ͼ��ճ��!");
            put(-60, "�ҵ���Բ����Ҫ������!");
            put(-59, "�ҵ���Բ����Ҫ������!");
            put(-58, "������Ч!");
            put(-57, "�߿�����!");

            put(-56, "ͼ���ȶ�!");
            put(-55, "�ɼ��������������");
            put(18, "��ǰ��: ");
            put(19, "��ǰ��: ");
            put(20, "�����: ");
            put(21, "�Һ���: ");
            put(22, "ת̫ǰ");
            put(23, "ת̫��");
        }
    };

    public static String getErrorInfo(int position) {
        String msg = errorMsg.get(position);
        return msg == null ? "ͼ��ɼ�ʧ��" : msg;
    }
}
