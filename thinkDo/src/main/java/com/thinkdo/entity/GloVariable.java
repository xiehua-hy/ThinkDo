package com.thinkdo.entity;

import android.content.Context;

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
    public static final String carSqliteName = "SKDDATA.db";
    public static final String customSqliteName = "SKDSELFDATA.db";

    public static Context context;
    public static final String emptyString = "";
    public static final String initValue = "99.99";
    public static String ip = defaultIp;
    public static int port = defaultPort;

    public static UnitEnum unit;
    public static UnitEnum toeUnit;

}
