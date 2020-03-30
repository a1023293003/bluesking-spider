package cn.bluesking.spider;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.util.stream.Collectors;

import javax.net.ssl.HttpsURLConnection;

/**
 * 爬虫启动类。
 * 
 * @author 随心 2020年3月23日
 */
public class Launcher {

    public static void main(String[] args) throws Exception {
        long begin = System.currentTimeMillis();
        URL url = new URL(
                "https://www.lagou.com/jobs/list_java?labelWords=&fromSearch=true&suginput=?labelWords=hot");
        HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
        connection.setRequestProperty("Connection", "close");
        connection.setRequestProperty("User-Agent",
                "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/80.0.3987.149 Safari/537.36 Edg/80.0.361.69");
        connection.connect();
        try (InputStream is = connection.getInputStream();
                InputStreamReader isr = new InputStreamReader(is);
                BufferedReader reader = new BufferedReader(isr);) {
            String line;
            StringBuilder buf = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                buf.append(line)
                   .append("\r\n");
            }
//            System.out.println(buf.toString());
        }
        String cookie = connection.getHeaderFields()
                                  .get("Set-Cookie")
                                  .stream()
                                  .map(header -> header.substring(0, header.indexOf(";") + 1))
                                  .collect(Collectors.joining());
        System.out.println(cookie);
        
        url = new URL(
                "https://www.lagou.com/jobs/positionAjax.json?px=default&city=%E5%B9%BF%E5%B7%9E&needAddtionalResult=true");
        connection = (HttpsURLConnection) url.openConnection();
        connection.setRequestProperty("Connection", "close");
        connection.setRequestProperty("User-Agent",
                "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/80.0.3987.149 Safari/537.36 Edg/80.0.361.69");
        connection.setRequestProperty("X-Anit-Forge-Code", "0");
        connection.setRequestProperty("X-Anit-Forge-Token", "None");
        connection.setRequestProperty("X-Requested-With", "XMLHttpRequest");
        connection.setRequestProperty("Referer", "https://www.lagou.com/jobs/list_java/p-city_213?px=default");
        connection.setRequestProperty("Cookie", cookie);
        connection.setRequestMethod("POST");
        connection.setDoOutput(true);
        try (PrintWriter writer = new PrintWriter(connection.getOutputStream())) {
            writer.print("first=false&pn=199&kd=java");
            writer.close();
        }
        
        connection.connect();
        try (InputStream is = connection.getInputStream();
                InputStreamReader isr = new InputStreamReader(is);
                BufferedReader reader = new BufferedReader(isr);) {
            String line;
            StringBuilder buf = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                buf.append(line)
                   .append("\r\n");
            }
            System.out.println(buf.toString());
        }
        
        url = new URL(
                "https://www.lagou.com/jobs/6864777.html?show=4e892090880f4193998e09392c9f8901");
        connection = (HttpsURLConnection) url.openConnection();
        connection.setRequestProperty("Connection", "close");
        connection.setRequestProperty("User-Agent",
                "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/80.0.3987.149 Safari/537.36 Edg/80.0.361.69");
        connection.setRequestProperty("Cookie", cookie);
        connection.connect();
        try (InputStream is = connection.getInputStream();
                InputStreamReader isr = new InputStreamReader(is);
                BufferedReader reader = new BufferedReader(isr);) {
            String line;
            StringBuilder buf = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                buf.append(line)
                   .append("\r\n");
            }
            System.out.println(buf.toString());
        }
        
        System.out.println(System.currentTimeMillis() - begin);
        
    }

}
