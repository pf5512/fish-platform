package com.ippteam.fish.util;

import com.sun.xml.internal.ws.api.ha.StickyFeature;
import jdk.internal.org.objectweb.asm.tree.AbstractInsnNode;
import jdk.internal.org.objectweb.asm.tree.analysis.Value;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;
import java.net.*;

/**
 * Created by isunimp on 16/11/1.
 */
public class HTTP {


    /**
     * 执行一次http请求
     * param为url中的键值对
     * body中的参数不确定为JSON，故只接受String类型，若为JSON对象请自行转换
     *
     * @param uri
     * @param param
     * @param body
     */
    public static String doPost(String uri, Map<String, Object> param, String body) throws MalformedURLException, IOException {
        URL url = new URL(MakeURL(uri, param));
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

        httpURLConnection.setDoInput(true);
        httpURLConnection.setDoOutput(true);        // 设置该连接是可以输出的
        httpURLConnection.setRequestMethod("POST"); // 设置请求方式
        httpURLConnection.setRequestProperty("charset", "utf-8");

        PrintWriter pw = new PrintWriter(new BufferedOutputStream(httpURLConnection.getOutputStream()));
        // 向连接中输出数据（相当于发送数据给服务器）
        pw.write(body);
        pw.flush();
        pw.close();

        return HTTP.readResponseBody(httpURLConnection.getInputStream());
    }

    public static String doGet(String uri, Map<String, Object> param) throws MalformedURLException, IOException {
        URL url = new URL(MakeURL(uri, param));
        URLConnection urlConnection = url.openConnection();
        return HTTP.readResponseBody(urlConnection.getInputStream());
    }

    private static String MakeURL(String uri, Map<String, Object> param) {
        StringBuffer sb = new StringBuffer();
        sb.append(uri);

        if (param != null && !param.isEmpty()) {
            sb.append("?");
            for (String key : param.keySet()) {
                Object obj = param.get(key);
                if (obj instanceof String) {
                    sb.append(key + "=" + "\"" + obj + "\"" + "&");
                } else if (obj instanceof Number) {
                    sb.append(key + "=" + obj + "&");
                }
            }
        }
        return sb.substring(0, sb.length() - 1).toString();
    }


    // 读取输入流中的数据
    private static String readResponseBody(InputStream inputStream) throws IOException {
        BufferedReader in = new BufferedReader(
                new InputStreamReader(inputStream, "utf-8"));
        String inputLine;
        StringBuffer stringBuilder = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            stringBuilder.append(inputLine);
        }
        in.close();
        return stringBuilder.toString();
    }
}
