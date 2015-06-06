package com.thinkdo.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;

/**
 * @author 谢荣华
 */

public class NetworkTool {
    private final String login_user = "admin";
    private final String url = "http://192.168.100.253/userRpm/AssignedIpAddrListRpm.htm";
    private String login_pw = "11111111";
    private String hostName = "rrr";

    public NetworkTool() {
    }

    /**
     * @param login_pw TPLink 用户密码
     */
    public NetworkTool(String login_pw) {
        this.login_pw = login_pw;
    }

    /**
     * 从LpLink获得ip地址
     *
     * @return 返回目标ip地址
     */
    public String hostToIpfromLpLink() {
        return this.hostToIpfromLpLink(hostName);
    }

    /**
     * 从LpLink获得ip地址
     *
     * @param hostName 主机名
     * @return 返回目标ip地址
     */
    public String hostToIpfromLpLink(String hostName) {
        String ip_start = "var DHCPDynList = new Array(";
        String ip_end = "0,0 );";

        String auth = "Basic " + getBASE64(login_user + ":" + login_pw);
        HttpClient httpClient = new DefaultHttpClient();
        HttpUriRequest request = new HttpGet(url);

        // 添加验证信息
        StringBuilder builder = new StringBuilder();
        builder.append(String.format("Authorization=%s;", auth));
        builder.append("path=/");
        request.addHeader("cookie", builder.toString());

        try {
            // 发送请求，返回响应
            HttpResponse response = httpClient.execute(request);
            HttpEntity entity = response.getEntity();

            if (response.getStatusLine().getStatusCode() == 200) {
                BufferedReader br = new BufferedReader(new InputStreamReader(
                        entity.getContent(), "gb2312"));
                String line;
                StringBuilder ipList = new StringBuilder();
                while ((line = br.readLine()) != null) {
                    if (line.equals(ip_start)) {
                        while ((line = br.readLine()) != null
                                && !line.equals(ip_end)) {
                            ipList.append(line);
                        }
                        break;
                    }
                }
                br.close();
                return getIp(ipList.toString(), hostName);
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * javascript的escape方法
     */
    private String escape(String src) {
        int i;
        char j;
        StringBuilder tmp = new StringBuilder();
        tmp.ensureCapacity(src.length() * 6);
        for (i = 0; i < src.length(); i++) {
            j = src.charAt(i);
            if (Character.isDigit(j) || Character.isLowerCase(j)
                    || Character.isUpperCase(j))
                tmp.append(j);
            else if (j < 256) {
                tmp.append("%");
                if (j < 16)
                    tmp.append("0");
                tmp.append(Integer.toString(j, 16));
            } else {
                tmp.append("%u");
                tmp.append(Integer.toString(j, 16));
            }
        }
        return tmp.toString();
    }

    /**
     * 从字符串解析ip
     *
     * @param ipList 要解析的字符
     * @param name   主机名
     */
    private String getIp(String ipList, String name) {
        if (ipList == null)
            return null;

        ipList = ipList.replace("\"", "");
        String[] array = ipList.split(",");

        for (int i = 0; i < array.length; i++) {
            if (array[i].trim().equals(name) && (i + 2) < array.length) {
                return array[i + 2].trim();
            }
        }
        return null;
    }

    /**
     * 加密
     */
    private String getBASE64(String str) {
        if (str == null)
            return null;
        str = (new BASE64Encoder()).encode(str.getBytes());
        return escape(str);
    }

}
