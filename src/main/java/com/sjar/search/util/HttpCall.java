package com.sjar.search.util;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.InputStreamRequestEntity;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class HttpCall {

    private static Logger logger = LoggerFactory.getLogger(HttpCall.class);
    /**
     * 格式化编码字符集，当参数不合法或为空时默认UTF-8
     * @param encodeType
     * @return
     */
    public static String formatEncodeType(String encodeType){
        String[] encodeTypes = {"GB2312","GBK","UTF-8"};
        String fmtEncodeType = "UTF-8";
        if(StringUtil.isNotEmpty(encodeType)){
            String upEncodeType = encodeType.trim().toUpperCase();
            for(int i=0 ; i < encodeTypes.length ; i++){
                if(upEncodeType.equals(encodeTypes[i])){
                    fmtEncodeType = encodeTypes[i];
                }
            }
        }
        return fmtEncodeType;
    }

    /**
     * 向指定URL发送POST方法的请求
     * @param url  发送请求的URL
     * @param param 请求参数，请求参数应该是name1=value1&name2=value2的形式。
     * @return URL所代表远程资源的响应
     */
    public static String sendPost(String url , String param , String encodeType , String sessionId) {
        System.out.println("HttpCall.sendPost() ==> " + url + (param != null && !"".equals(param) ? "?" + param : ""));
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        String fmtEncodeType = formatEncodeType(encodeType);
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            HttpURLConnection conn = (HttpURLConnection) realUrl.openConnection();

            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

            if (StringUtil.isNotEmpty(sessionId)){
                conn.setRequestProperty("cookie", "JSESSIONID="+sessionId.trim());
            }

            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setRequestMethod("POST");
            conn.setUseCaches(false);


            out = new PrintWriter(new OutputStreamWriter(conn.getOutputStream(), fmtEncodeType));
            // 发送请求参数
            if (param != null && !"".equals(param)) {
                out.print(param);
            }
            out.flush();

            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(conn.getInputStream(), fmtEncodeType));
            String line;
            while ((line = in.readLine()) != null) {
                result += "\n" + line;
            }
        } catch (Exception e) {
            System.out.println("发送POST请求出现异常！url:" + url + ", params:" + param + "; " + e);
            logger.error(e.getMessage(),e);
        }
        finally {
            try {
                if (out != null) { out.close(); }
                if (in != null) { in.close(); }
            } catch (IOException ex) {
                logger.error(ex.getMessage(),ex);
            }
        }

        return result == null ? null : result.trim();
    }

    /**
     * 向指定URL发送GET方法的请求
     *
     * @param url 发送请求的URL
     * @param param 请求参数，请求参数应该是name1=value1&name2=value2的形式。
     * @return URL所代表远程资源的响应
     */
    public static String sendGet(String url, String param , String encodeType , String sessionId) {
        String result = "";
        BufferedReader in = null;
        String fmtEncodeType = formatEncodeType(encodeType);
        try {
            String urlName = url + (param != null && !"".equals(param) ? "?" + param : "");
            System.out.println("HttpCall.sendGet() ==> " + urlName);
            URL realUrl = new URL(urlName);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");

            if(StringUtil.isNotEmpty(sessionId)){
                conn.setRequestProperty("cookie", "JSESSIONID="+sessionId.trim());
            }

            // 建立实际的连接
            conn.connect();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(conn.getInputStream(), fmtEncodeType));
            String line;
            while ((line = in.readLine()) != null) {
                result += "\n" + line;
            }
        } catch (Exception e) {
            System.out.println("发送GET请求出现异常！url:" + url + ", params:" + param + "; " + e);
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                logger.error(ex.getMessage(),ex);
            }
        }

        return result == null ? null : result.trim();
    }

    /**
     * 向指定URL发送POST方法的请求
     *
     * @param url  	发送请求的URL
     * @param param 请求参数，请求参数应该是name1=value1&name2=value2的形式。
     * @encodeType  指定编码
     * @return 		URL所代表远程资源的响应
     */
    public static String sendPost(String url, String param, String encodeType) {

        String result = sendPost(url, param, encodeType, null);

        return result == null ? null : result.trim();
    }

    public static String sendPostBody(String urlPath, String json,String encodeType) throws Exception {
        try{
            String fmtEncodeType = formatEncodeType(encodeType);
            URL url = new URL(urlPath);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            // 设置doOutput属性为true表示将使用此urlConnection写入数据
            urlConnection.setDoOutput(true);
            // 定义待写入数据的内容类型，我们设置为application/x-www-form-urlencoded类型
            urlConnection.setRequestProperty("content-type", "application/x-www-form-urlencoded");
            // 得到请求的输出流对象
            OutputStreamWriter out = new OutputStreamWriter(urlConnection.getOutputStream(), fmtEncodeType);
            // 把数据写入请求的Body
            out.write(json);
            out.flush();
            out.close();

            // 从服务器读取响应
            String body = IOUtils.toString(urlConnection.getInputStream(), fmtEncodeType);
            if(urlConnection.getResponseCode()==200){
                return body;
            } else {
                throw new Exception(body);
            }
        } catch (IOException e){
            logger.error(e.getMessage(),e);
        }
        return null;
    }

    /**
     * 向指定URL发送GET方法的请求
     *
     * @param url	发送请求的URL
     * @param param	请求参数，请求参数应该是name1=value1&name2=value2的形式。
     * @encodeType  指定编码
     * @return 		URL所代表远程资源的响应
     */
    public static String sendGet(String url, String param, String encodeType) {

        String result = sendGet(url, param, encodeType, null);

        return result == null ? null : result.trim();
    }

    /**
     *
     * @param url 发送请求的URL
     * @param param 请求参数，请求参数应该是name1=value1&name2=value2的形式。
     * @return
     */
    public static String sendPost(String url, String param) {

        String result = sendPost(url, param, "UTF-8", null);

        return result == null ? null : result.trim();
    }

    /**
     *
     * @param url 发送请求的URL
     * @param param 请求参数，请求参数应该是name1=value1&name2=value2的形式。
     * @return
     */
    public static String sendGet(String url, String param) {

        String result = sendGet(url, param, "UTF-8", null);

        return result == null ? null : result.trim();
    }

    /**
     * 发送文件二进流
     * @param url
     * @param in
     * @return
     */
    public static String sendFile(String url, InputStream in){
        String result = "";
        HttpClient httpClient = new HttpClient();
        PostMethod postMethod = new PostMethod(url);
        RequestEntity requestEntity = new InputStreamRequestEntity(in);
        postMethod.setRequestEntity(requestEntity);
        try {
            //执行getMethod
            int statusCode = httpClient.executeMethod(postMethod);
            if (statusCode == HttpStatus.SC_OK) {
                //读取内容
                result = postMethod.getResponseBodyAsString().trim();
            }else{
                System.out.println("Method failed: " + postMethod.getStatusLine());
            }
        } catch (HttpException e) {
            //发生致命的异常，可能是协议不对或者返回的内容有问题
            System.out.println("附件上传失败！");
            logger.error(e.getMessage(),e);
        } catch (IOException e) {
            //发生网络异常
            logger.error(e.getMessage(),e);
        } finally {
            //释放连接
            postMethod.releaseConnection();
            try {
                if (in != null){
                    in.close();
                }
            } catch (IOException e) {
                logger.error(e.getMessage(),e);
            }
        }

        return result == null ? null : result.trim();
    }

    /**
     *
     *
     * @author 刘俊 2015年4月15日
     * @param is			输入流
     * @param urlStr		远程url
     * @param fileName		文件名
     * @param contentType	文件http类型
     */
    public static String sendPic(InputStream is, String urlStr, String fileName, String contentType) {
        String res = "";

        // boundary就是request头和上传文件内容的分隔符
        final String BOUNDARY = "---------------------------123821742118716";

        HttpURLConnection conn = null;
        try {
            URL url = new URL(urlStr);
            conn = (HttpURLConnection) url.openConnection();
            //设置http头信息
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(30000);
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 6.1; zh-CN; rv:1.9.2.6)");
            conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + BOUNDARY);

            OutputStream out = new DataOutputStream(conn.getOutputStream());
            if (contentType == null || contentType.equals("")) {
                contentType = "application/octet-stream";
            }

            StringBuffer hreader = new StringBuffer();
            hreader.append("\r\n").append("--").append(BOUNDARY).append("\r\n");
            hreader.append("Content-Disposition: form-data; name=\"" + fileName + "\"; filename=\"" + fileName + "\"\r\n");
            hreader.append("Content-Type:" + contentType + "\r\n\r\n");

            //
            out.write(hreader.toString().getBytes());

            //写文件
            DataInputStream in = new DataInputStream(is);
            int bytes = 0;
            byte[] bufferOut = new byte[1024];
            while ((bytes = in.read(bufferOut)) != -1) {
                out.write(bufferOut, 0, bytes);
            }
            in.close();

            byte[] endData = ("\r\n--" + BOUNDARY + "--\r\n").getBytes();
            out.write(endData);
            out.flush();
            out.close();

            // 读取返回数据
            StringBuffer strBuf = new StringBuffer();
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = null;
            while ((line = reader.readLine()) != null) {
                strBuf.append(line).append("\n");
            }
            res = strBuf.toString();
            reader.close();
            reader = null;
        } catch (Exception e) {
            System.out.println("发送POST请求出错。" + urlStr);
            logger.error(e.getMessage(),e);
        } finally {
            if (conn != null) {
                conn.disconnect();
                conn = null;
            }
        }

        return res;
    }

    /**
     *获取URL的文件
     * @param serverUrl
     * @return
     */
    public static byte[] getFileByUrl(String serverUrl){
        HttpURLConnection conn = null;
        byte[] buff = null;
        try {
            //URL url = new URL(encodeChinese(this.serverUrl));
            System.out.println("【通过URL获取附件信息】"+serverUrl);
            //System.out.println("【通过URL编码后获取附件信息】"+encodeChinese(serverUrl));
            URL url = new URL(encodeChinese(serverUrl));
            //设置此类是否应该自动执行 HTTP 重定向（响应代码为 3xx 的请求）。
            HttpURLConnection.setFollowRedirects(false);

            //到 URL 所引用的远程对象的连接
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(60 * 1000);
            conn.setReadTimeout(60 * 1000);
            System.setProperty("sun.net.client.defaultConnectTimeout", "60000");
            System.setProperty("sun.net.client.defaultReadTimeout", "60000");
            conn.setDoInput(true);
            if(conn.getResponseCode() == HttpURLConnection.HTTP_OK){

                InputStream is = conn.getInputStream();
                buff = readInputStream(is);
                is.close();

            }

        } catch (Exception e) {
            System.out.println("出现异常附件："+serverUrl);
            logger.error(e.getMessage(),e);
        }finally{
            try{
                if (conn != null) {
                    conn.disconnect();
                }
            }catch(Exception ex){
                logger.error(ex.getMessage(),ex);
            }
        }
        return buff;
    }

    public static byte[] readInputStream(InputStream inStream) throws Exception{
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        //创建一个Buffer字符串
        byte[] buffer = new byte[3100];
        //每次读取的字符串长度，如果为-1，代表全部读取完毕
        int len = 0;
        //使用一个输入流从buffer里把数据读取出来
        while( (len=inStream.read(buffer)) != -1 ){
            //用输出流往buffer里写入数据，中间参数代表从哪个位置开始读，len代表读取的长度
            outStream.write(buffer, 0, len);
        }
        //关闭输入流
        inStream.close();
        //把outStream里的数据写入内存
        return outStream.toByteArray();
    }

    // 中文汉字和符号URL编码
    public static String encodeChinese(String url) {
        char[] ch = url.toCharArray();
        String newurl = "";
        for (int i = 0; i < ch.length; i++) {
            if(Character.isWhitespace(ch[i])){
                newurl += "%20";
            }else if(ch[i] == ':' || ch[i] == '/' || ch[i] == '.' || ch[i] == '&' || ch[i] == '?' || ch[i] == '='){
                newurl += String.valueOf(ch[i]);
            }else{
                try{
                    newurl += URLEncoder.encode(String.valueOf(ch[i]),"UTF-8");
                }catch(Exception ex){
                    logger.error(ex.getMessage(),ex);
                }
            }
        }
        return newurl;
    }
}
