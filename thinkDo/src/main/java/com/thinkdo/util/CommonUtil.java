package com.thinkdo.util;

import com.thinkdo.entity.GloVariable;

import java.text.DecimalFormat;
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
        while (m.find()) {
            return true;
        }
        return false;
    }

    public static String findSpecialChar(String msg) {
        if (msg != null) {
            for (int i = 0; i < msg.length(); i++) {
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

                }
            }
        }
        return " ";
    }

    /**
     *  四舍五入格式化数值
     *
     *  @param num 小位点位数 1表示带一位; 2表示表两位小数; 其他值表示取整,即0位小数
     * */
    public static String format(float f, int num){
        String style;
        switch(num){
            case 2:
                style="0.00";
                break;
            case 1:
                style="0.0";
                break;
            default:
                style="0";
        }
        return new DecimalFormat(style).format(f);
    }


}
