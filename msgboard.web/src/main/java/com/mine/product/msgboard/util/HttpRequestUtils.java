package com.mine.product.msgboard.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
public class HttpRequestUtils {

    private HttpRequestUtils(){}


    /**
     * 给定义URL 然后就访问数据了
     * @param urlPath
     * @return
     */
    public static String sendGet(String urlPath){
        return HttpRequestUtils.sendGet(urlPath,null);
    }

    /**
     * 向指定URL发送GET方法的请求
     * 
     * @param url
     *            发送请求的URL
     * @param param
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return URL 所代表远程资源的响应结果
     */
    public static String sendGet(String urlPath, String param){
          String result = "";
          BufferedReader buff = null;

         try {
             String urlPathStr = null;
            if(param  == null  || "".equals(param.trim())){
                urlPathStr = urlPath;
            }else{
                urlPathStr = urlPath + "?" + param;
            }
            //获取URL
            URL url = new URL(urlPathStr);
            //获取连接
            URLConnection conn  = url.openConnection();
            //设定连接的属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("conn", "Keep-Alive");
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");

            //设定 ip 设定成百度的爬虫Ip
            conn.setRequestProperty("X-Forwarded-For", "117.28.255.37");
            conn.setRequestProperty("Client-Ip", "117.28.255.37");
            //打开连接
            conn.connect();
            //获取结果
            buff  = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            //设定结果
           String line  = null;
            while((line = buff.readLine())!= null){
                result +=line;
            }
            return result;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }finally{
            try {
                //关闭连接
                if(buff != null){
                    buff.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 向指定 URL 发送POST方法的请求
     * 
     * @param url
     *            发送请求的 URL
     * @param param
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return 所代表远程资源的响应结果
     */
    public static String sendPost(String urlPath, String param){
          String result = "";
          BufferedReader buff = null;
          //向别的服务器些数据
          PrintWriter writer = null;
          try {
            URL url = new URL(urlPath);

             URLConnection conn =url.openConnection();
                //设定连接的属性
                conn.setRequestProperty("accept", "*/*");
                conn.setRequestProperty("conn", "Keep-Alive");
                conn.setRequestProperty("user-agent",
                        "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");

                // 发送POST请求必须设置如下两行
                conn.setDoOutput(true);
                conn.setDoInput(true);

                //通过 PrintWriter写数据到 服务器中
                writer = new PrintWriter( conn.getOutputStream());
                //添加参数
                writer.print(param);
                writer.flush();

                //获取结果
                //获取结果
                buff  = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                //设定结果
               String line  = null;
                while((line = buff.readLine())!= null){
                    result +=line;
                }
                return result;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }finally{
            try {
                if(writer != null){
                    writer.close();
                }

                if(buff != null){
                    buff.close();
                }
            } catch (Exception e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        }
    }
    /**
     * 给定一个URL 进行post访问
     * @param urlPath
     * @return
     */
    public static String sendPost(String urlPath){
        return HttpRequestUtils.sendPost(urlPath,null);
    }
}