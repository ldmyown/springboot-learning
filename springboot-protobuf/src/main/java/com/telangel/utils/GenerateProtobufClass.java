package com.telangel.utils;

import java.io.File;
import java.io.IOException;

/**
 * @version 1.0.0
 * @项目名称： springboot-protobuf
 * @类名称： GenerateProtobufClass
 * @类描述： protobuf 生成java类
 * @author： lid
 * @date： 2019/3/12 17:54
 */
public class GenerateProtobufClass {

    public static void main(String[] args) {
        String protoFile = "user.proto";
        File file1 = new File("./src/main/resources/protobuf/protoc.exe");
        File file2 = new File("./src/main/resources/protobuf"+ protoFile);
        file1.exists();
        file2.exists();
        String strCmd = "./src/main/resources/protobuf/protoc.exe --java_out=./src/main/java ./src/main/resources/protobuf/"+ protoFile;
        try {
            Runtime.getRuntime().exec(strCmd);
        } catch (IOException e) {
            e.printStackTrace();
        }//通过执行cmd命令调用protoc.exe程序
    }
}
