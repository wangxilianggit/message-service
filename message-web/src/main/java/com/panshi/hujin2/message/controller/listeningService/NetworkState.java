package com.panshi.hujin2.message.controller.listeningService;

import com.panshi.hujin2.base.common.enmu.ApplicationEnmu;
import com.panshi.hujin2.base.service.Context;
import com.panshi.hujin2.message.service.message.IMsgDBService;
import com.panshi.hujin2.message.service.message.ISendMsgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * create by shenjiankang on 2018/9/11 10:17
 *
 *
 * 检测mexico服务器是否正常
 */

@RestController
public class NetworkState implements Runnable{

    Context context = new Context();

    private int sendCount = 0;

    @Autowired
    @Qualifier("tianyihongService")
    private ISendMsgService tianyihongService;

    @Autowired
    private IMsgDBService msgDBService;



    public static void main(String[] args) {
        // TODO Auto-generated method stub
//        NetworkState ns = new NetworkState();
//        new Thread(ns).start();//启动线程
    }

//    @RequestMapping("/listening")
    public void listeningMexicoService(){
//        NetworkState ns = new NetworkState();
        new Thread(this).start();//启动线程
    }

    // 判断网络状态
    public void isConnect() {
        Runtime runtime = Runtime.getRuntime();
        try {
//            Process process = runtime.exec("ping " + "www.google.ca");
            Process process = runtime.exec("ping " + "54.177.238.11");//墨西哥eloan生产环境
            InputStream is = process.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            String line = null;
            StringBuffer sb = new StringBuffer();
            while ((line = br.readLine()) != null) {
                sb.append(line);
                // System.out.println("返回值为:"+line);
            }
            is.close();
            isr.close();
            br.close();

            if (null != sb && !sb.toString().equals("")) {
                String logString = "";
                if (sb.toString().indexOf("TTL") > 0) {
                    // 网络畅通
                    logString = "网络正常，时间 " + this.getCurrentTime();
                  System.out.println(logString);
                } else {
                    // 网络不畅通
                    logString = "网络断开，时间 " + this.getCurrentTime();
                  System.out.println(logString);

                  if(sendCount < 2){
                      boolean sendRes = tianyihongService.sendInternationalMsg(ApplicationEnmu.WU_YOU_DAI,
                              "8613777400292",
                              "【eloan】墨西哥eloan生产环境挂了"+logString,
                              context);
                      System.out.println("--------发送结果 sendRes = " + sendRes+"  发送次数："+sendCount);
                      sendCount ++;
                  }

                }
                this.writeIntoLog(logString);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 获得当前时间
    public String getCurrentTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = sdf.format(new Date());
        return time;
    }

    // 将信息写入日志文件
    public void writeIntoLog(String logString) {
        File file = null;
        FileWriter fw = null;
        BufferedWriter bw = null;
        try {
            file = new File("D:\\serviceLogs\\netWorkState.log");
            if (!file.exists()) {
                file.createNewFile();// 如果不存在该文件，则创建
                String sets="attrib +H \""+file.getAbsolutePath()+"\"";
                Runtime.getRuntime().exec(sets);//将日志文件隐藏
            }
            fw = new FileWriter(file, true);
            bw = new BufferedWriter(fw);
            fw.append(logString + "\r\n");// 换行
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        } finally {
            try {
                bw.close();
                fw.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

    }

    @Override
    public void run() {
        // TODO Auto-generated method stub
        while (true) {
            this.isConnect();
            try {
                // 每隔3秒钟测试一次网络是否连通
                Thread.sleep(60000);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
}
