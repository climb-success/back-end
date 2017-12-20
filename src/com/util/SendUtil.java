/*
 * Copyright (c) 2017 Du Tengfei. All Rights Reserved.
 */
package com.util;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

/**
 * SendUtil.
 * @author <A HREF="mailto:dtfgongzuo@163.com">Du Tengfei</A>
 * @version 1.0, $Revision: 0$, $Date: Dec 20, 2017$
 * @since 1.0
 */
public class SendUtil
{
    public static String sendPost(String urlString, String param) 
    {
        DataOutputStream out = null;
        BufferedReader in = null;
        String result = "";
        try 
        {
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setRequestMethod("POST");
            connection.setUseCaches(false);
            connection.setInstanceFollowRedirects(true);
            connection.setRequestProperty("Content-Type",
                    "application/json;charset=UTF-8");

            connection.connect();

            //POST����
            out = new DataOutputStream(connection.getOutputStream());
            out.write(param.getBytes("UTF-8"));
            out.flush();
            out.close();
            // ����BufferedReader����������ȡURL����Ӧ
            in = new BufferedReader(
                    new InputStreamReader(connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) 
            {
                result += line;
            }
        } 
        catch (Exception e) 
        {
            System.out.println("���� POST ��������쳣��"+e);
            e.printStackTrace();
        }
        //ʹ��finally�����ر��������������
        finally
        {
            try
            {
                if(out!=null)
                {
                    out.close();
                }
                
                if(in!=null)
                {
                    in.close();
                }
            }
            catch(IOException ex)
            {
                ex.printStackTrace();
            }
        }
        return result;
    }    

    public static String sendGet(String url) 
    {
        String result = "";
        BufferedReader in = null;
        try 
        {
            URL realUrl = new URL(url);
            // �򿪺�URL֮�������
            URLConnection connection = realUrl.openConnection();
            // ����ͨ�õ���������
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            connection.setRequestProperty("Content-Type",
                    "application/json;charset=UTF-8");
            // ����ʵ�ʵ�����
            connection.connect();
            // ��ȡ������Ӧͷ�ֶ�
            Map<String, List<String>> map = connection.getHeaderFields();
            // �������е���Ӧͷ�ֶ�
            for (String key : map.keySet()) 
            {
                System.out.println(key + "--->" + map.get(key));
            }
            // ���� BufferedReader����������ȡURL����Ӧ
            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream(), "utf-8"));
            String line;
            while ((line = in.readLine()) != null) 
            {
                result += line;
            }
        } 
        catch (Exception e) 
        {
            System.out.println("����GET��������쳣��" + e);
            e.printStackTrace();
        }
        // ʹ��finally�����ر�������
        finally 
        {
            try 
            {
                if (in != null) 
                {
                    in.close();
                }
            } 
            catch (Exception e2) 
            {
                e2.printStackTrace();
            }
        }
        return result;
    }
}
