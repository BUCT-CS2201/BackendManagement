package com.example.backgroundmanagementsystem.utils;

import com.example.backgroundmanagementsystem.enums.ResponseCodeEnum;
import com.example.backgroundmanagementsystem.exceptions.BaseException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;

@Slf4j
@Component
public class DataBaseUtils {
    /**
     * host
     */
    @Value("${service.database.host}")
    private String host;
    /**
     * port
     */
    @Value("${service.database.port}")
    private Integer port;

    /**
     * 用户名
     */
    @Value("${service.database.username}")
    private String userName;

    /**
     * 密码
     */
    @Value("${service.database.password}")
    private String password;

    /**
     * 数据库名字
     */
    @Value("${service.database.dbName}")
    private String dbName;
    /**
     * windows环境下备份目录
     */
    @Value("${service.database.windows-backup-folder}")
    private String windowsBackupFolder;
    /**
     * linux环境下备份目录
     */
    @Value("${service.database.linux-backup-folder}")
    private String linuxBackupFolder;


    public String getDefaultPath(){
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-sss");
        String backName = dateFormat.format(date) + ".sql";
        String pathSql;
        //判断系统
        String osName = System.getProperties().getProperty("os.name");
        if (osName.equals("Linux")) {
            pathSql = linuxBackupFolder + File.separator + backName;
        } else {
            pathSql = windowsBackupFolder + File.separator + backName;
        }
        return pathSql;
    }

    /**
     * 备份数据库db
     * @param path
     */
    public Boolean dbBackUp(String path){
        /*Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-sss");
        String backName = dateFormat.format(date) + ".sql";
        String pathSql;*/
        //输出流文件
        File fileSql;
        try {
            //判断系统
            String osName = System.getProperties().getProperty("os.name");
            /*if (osName.equals("Linux")) {
                pathSql = linuxBackupFolder + File.separator + backName;
            } else {
                pathSql = windowsBackupFolder + File.separator + backName;

            }*/
            //输出流文件
            fileSql = new File(path);
            //创建备份sql文件
            if (!fileSql.exists()) {
                fileSql.createNewFile();
            }

            //mysqldump -hlocalhost -Pport -uroot -p123456 db > /home/back.sql  整个库进行备份sql文件
            //mysqldump -hlocalhost -Pport -uroot -p123456 数据库名  数据表名 > /home/back.sql  单张表备份sql文件
            StringBuilder sb = new StringBuilder();
            sb.append("mysqldump");
            sb.append(" -h").append(host);
            sb.append(" -P").append(port);
            sb.append(" -u").append(userName);
            sb.append(" -p").append(password);
            sb.append(osName.equals("Linux")?" --set-charset=UTF8":"");
            sb.append(" ").append(dbName).append(" ");
            sb.append("-r");//java中必须使用"-r"替代">"
            sb.append(path);

            Process process;
            //判断系统
            if (osName.equals("Linux")) {
                BufferedReader bufferedReader;
                PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(new FileOutputStream(fileSql), StandardCharsets.UTF_8));
                process = Runtime.getRuntime().exec(sb.toString());
                InputStreamReader inputStreamReader = new InputStreamReader(process.getInputStream(), StandardCharsets.UTF_8);
                bufferedReader = new BufferedReader(inputStreamReader);
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    printWriter.println(line);
                }
                printWriter.flush();
            } else {
               // System.out.println("cmd命令为：" + "cmd /c " + sb);
                process = Runtime.getRuntime().exec("cmd /c " + sb);//执行语句
            }
            process.waitFor();//等待上述命令执行完毕后打印下面log

            //输出错误信息
            InputStream is = process.getErrorStream();
            InputStreamReader isr = new InputStreamReader(is, "gbk");//读取
            log.error(isr.getEncoding());
            BufferedReader bufr = new BufferedReader(isr);//缓冲
            String line;
            while ((line = bufr.readLine()) != null) {
                log.error("error: "+line);
            }
            isr.close();
            return process.exitValue() == 0 ;
        } catch (Exception e) {
            log.error("数据库备份错误");
            throw new BaseException(ResponseCodeEnum.CODE_500);
        }
    }

        /**
         * 恢复数据库
         * mysql -hlocalhost -uroot -proot dbname < /home/back.sql
         */
    public void dbRestore (String filePath) {
        StringBuilder sb = new StringBuilder();
        sb.append("mysql");
        sb.append(" -h").append(host);
        sb.append(" -P").append(port);
        sb.append(" -u").append(userName);
        sb.append(" -p").append(password);
        sb.append(" " + dbName + " <");
        sb.append(filePath);
        System.out.println("cmd命令为：" + sb);
        Runtime runtime = Runtime.getRuntime();
        System.out.println("开始还原数据");
        try {
            Process process = runtime.exec("cmd /c" + sb);

            //输出错误信息
            FileInputStream errorStream = (FileInputStream) process.getErrorStream();
            InputStreamReader isr = new InputStreamReader(errorStream, "gbk");//读取
            System.out.println(isr.getEncoding());
            BufferedReader bufr = new BufferedReader(isr);//缓冲
            String line;
            while ((line = bufr.readLine()) != null) {
                System.out.println("error:" + line);
            }
            isr.close();


            InputStream is = process.getInputStream();
            BufferedReader bf = new BufferedReader(new InputStreamReader(is, "utf8"));
            while ((line = bf.readLine()) != null) {
                System.out.println(line);
            }
            is.close();
            bf.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("还原成功！");
    }
}
