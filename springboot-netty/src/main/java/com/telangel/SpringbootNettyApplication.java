package com.telangel;

import com.telangel.service.HttpFileServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author lid
 */
@SpringBootApplication
public class SpringbootNettyApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootNettyApplication.class, args);

        String[] argList = args;
        System.out.println("+++++++++++++Simple Netty HttpFileServer+++++++++++++++");
        System.out.println("+            VERSION 1.0.1                            +");
        System.out.println("+            AUTHER:LID                                +");
        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        if (args.length == 0) {
            System.out.println("Usage: java -jar thisPackageName.jar [-options][args...]");
            System.out.println("Use -h for more infomation");
            System.out.println("default port is 8080 , webRoot is /root ");
        } else {
            for (int i = 0; i < argList.length; i++) {
                if (argList[i].equalsIgnoreCase("-h")) {
                    System.out.println("-p your Listern port");
                    System.out.println("-f your webRoot path");
                    System.out.println("Example:java -jar netty-0.0.1-SNAPSHOT.jar -p 80 -f /root");
                    return;
                } else {
                    if (argList[i].equalsIgnoreCase("-p")) {
                        try {
                            HttpFileServer.PORT = Integer.valueOf(argList[i + 1]);
                        } catch (NumberFormatException e) {
                            System.out.println("wrong number for you port");
                            System.out.println("Use -h for more infomation");
                            e.printStackTrace();
                            return;
                        }
                    }
                    if (argList[i].equalsIgnoreCase("-f")) {
                        try {
                            HttpFileServer.WEBROOT = argList[i + 1];
                        } catch (Exception e) {
                            System.out.println("wrong path for you webRoot");
                            System.out.println("Use -h for more infomation");
                            e.printStackTrace();
                            return;
                        }
                    }
                }
            }
        }

        try {
            HttpFileServer.main();
        } catch (InterruptedException e) {
            e.printStackTrace();
    }

    }

}
