package com.thinkdo.util;

import com.thinkdo.application.MainApplication;

import net.sourceforge.pinyin4j.PinyinHelper;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by xh on 15/5/9.
 */
public class CommonUtil {
    public static boolean isChinese(String str) {
        String regEx = "[\\u4e00-\\u9fa5]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.find();
    }

    public static String findSpecialChar(String msg) {
        if (msg != null) {
            for (int i = 1; i < msg.length(); i++) {
                char ch = msg.charAt(i);
                switch (ch) {
                    case ' ':
                        return " ";
                    case ',':
                        return ",";
                    case '(':
                        return "\\(";
                    case '/':
                        return "/";
                    case '[':
                        return "\\[";
                }
            }
        }
        return " ";
    }

    /**
     * 四舍五入格式化数值
     *
     * @param num 小位点位数
     */
    public static String format(float f, int num) {
        StringBuilder style = new StringBuilder("0");

        if (num > 0)
            style.append(".");

        for (int i = num; i > 0; i--) {
            style.append("0");
        }

        return new DecimalFormat(style.toString()).format(f);
    }


    public static String getCurTime() {
        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd kk:mm", Locale.getDefault());
        return sDateFormat.format(new Date());
    }

    public static String getPinYinHeadChar(String str) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            char word = str.charAt(i);
            String[] pinyinArray = PinyinHelper.toHanyuPinyinStringArray(word);
            if (pinyinArray != null) {
                builder.append(pinyinArray[0].charAt(0));
            } else {
                builder.append(word);
            }
        }
        return builder.toString().toUpperCase();
    }

    public static String getIp(String hostName) {
        if (isIP(hostName)) return hostName;
        return new NetworkTool().hostToIpfromLpLink(hostName);
    }

    public static boolean isIP(String str) {
        String pattern = "\\d{1,3}.\\d{1,3}.\\d{1,3}.\\d{1,3}";
        Pattern pat = Pattern.compile(pattern);
        return pat.matcher(str).matches();
    }

    public static int getQuestCode(String msg) {
        String[] array = msg.split("&");
        try {
            return Integer.parseInt(array[0]);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    public static int getStatusCode(String msg) {
        String[] array = msg.split("&");
        try {
            return Integer.parseInt(array[1]);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    public static String getErrorString(int status, String str) {
        StringBuilder buff = new StringBuilder();
        if (str.contains("&&")) {
            String[] data = str.split("&&");
            String[] array = data[1].split("\\|");
            if (bitCount(status, 0x01)) {
                //左前
                buff.append(MainApplication.getErrorInfo(18));
                buff.append(String.format("%s\n", MainApplication.getErrorInfo(Integer.parseInt(array[0]))));
            }
            if (bitCount(status, 0x02)) {
                //右前
                buff.append(MainApplication.getErrorInfo(19));
                buff.append(String.format("%s\n", MainApplication.getErrorInfo(Integer.parseInt(array[1]))));
            }
            if (bitCount(status, 0x04)) {
                //左后
                buff.append(MainApplication.getErrorInfo(20));
                buff.append(String.format("%s\n", MainApplication.getErrorInfo(Integer.parseInt(array[2]))));
            }
            if (bitCount(status, 0x08)) {
                //右后
                buff.append(MainApplication.getErrorInfo(21));
                buff.append(String.format("%s\n", MainApplication.getErrorInfo(Integer.parseInt(array[3]))));
            }

            if (bitCount(status, 0x100)) {
                //中部图像被挡
                buff.append(MainApplication.getErrorInfo(16));
            }
        }
        return buff.toString();
    }

    public static boolean bitCount(int count, int bit) {
        return (count & bit) > 0;
    }
}
