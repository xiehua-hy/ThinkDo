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
        return m.find();
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


}
