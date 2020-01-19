package com.telangel.netty.utils;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
class Dgram {

    public static DatagramPacket toDatagram(String s, InetAddress destIA,
                                            int destPort) {
        byte[] buf = new byte[s.length() + 1];
        s.getBytes(0, s.length(), buf, 0);
        return new DatagramPacket(buf, buf.length, destIA, destPort);
    }

    public static String toString(DatagramPacket p) {
        return new String(p.getData(), 0, p.getLength());
    }
}

class ChatterServer {
    static final int INPORT = 50021;
    private byte[] buf = new byte[1000];
    private DatagramPacket dp = new DatagramPacket(buf, buf.length);
    private DatagramSocket socket;

    public ChatterServer() {
        try {
            socket = new DatagramSocket(INPORT, InetAddress.getByName("0.0.0.0"));//
            System.out.println("Server started");
            while (true) {
                socket.receive(dp);

                String rcvd = Dgram.toString(dp) + ",from address:"
                        + dp.getAddress() + ",port:" + dp.getPort();
                System.out.println("From Client:"+rcvd);
                //dp.getData()获取消息内容
                MAC_struct mac_data = new MAC_struct(dp.getData());
                mac_data.parseFrame();
                String echoString = "From Server Echoed:" + rcvd;
                DatagramPacket echo = Dgram.toDatagram(echoString,
                        dp.getAddress(), dp.getPort());

                socket.send(echo);
            }
        } catch (SocketException e) {
            System.err.println("Can't open socket");
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Communication error");
            e.printStackTrace();
        }
    }
}
class MAC_struct{
    byte[] recv = new byte[2048];
    public int nodeID;
    public int nodeID2;
    public int stampFlags;
    public long tmp;
    public MAC_struct(byte[] recv){
        this.recv = recv;
    }
    /**
     解析收到的结构体消息。
     */
    public void parseFrame(){
        InputStream in_withcode;
        try {
            for(int i=0;i<24;i++)
                System.out.println("source["+i+"]="+recv[i]);
            in_withcode = new ByteArrayInputStream(recv);
            DataInputStream inputStream  =  new DataInputStream(in_withcode);
            int read=0;
            int updateFlg=0;
            int nodeID=0;
            int[] Bits_4 = new int[4];
            read = inputStream.readUnsignedByte();//读取一个无符号字节
            System.out.println("read= "+read);
            inputStream.skipBytes(3);//跳过N个字节
            updateFlg = inputStream.readInt();//readInt()读取一个4个字节。
            System.out.println("updateFlg= "+int_EndianBigtoLittle(updateFlg));
            nodeID = inputStream.readInt();
            System.out.println("nodeID= "+int_EndianBigtoLittle(nodeID));
            int slotBitMap = inputStream.readUnsignedShort();//读取2个无符号字节。
            System.out.println("slotBitMap= "+uShort_EndianBigtoLittle(slotBitMap));
            inputStream.skipBytes(2);
            System.out.println("Long_num= "+Long_EndianBigtoLittle(inputStream.readInt()));
            for(int i=0;i<4;i++)
                Bits_4[i] = inputStream.readUnsignedByte();
            System.out.println("Float_num= "+Float_EndianBigtoLittle(Bits_4));
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    /**
     * 将byte[]数组转为String
     * @param valArr 待转换byte数组，
     * @param startpoint 待转换byte数组开始位置
     * @param maxLen 待转换byte数组长度
     * @return
     */
    private static String toStr(byte[] valArr,int startpoint,int maxLen) {
        int index = 0;
        while(index + startpoint < valArr.length && index < maxLen) {
            if(valArr[index+startpoint] == 0) {
                break;
            }
            index++;
        }
        byte[] temp = new byte[index];
        System.arraycopy(valArr, startpoint, temp, 0, index);
        return new String(temp);
    }
    /**
     * 读取4位，并转化为无符号的4位数据
     * @param value
     * @return 将读取的数据转为小端模式下存储的数据
     */
    private static long Long_EndianBigtoLittle(int value)
    {
        byte[] src = new byte[4];
        src[0] = (byte) ((value>>24)&0xFF);
        src[1] = (byte) ((value>>16)&0xFF);
        src[2] = (byte) ((value>>8)&0xFF);
        src[3] = (byte) ((value)&0xFF);
        return (long) ((src[0]&0xFF)|((src[1]&0xFF)<<8)|((src[2]&0xFF)<<16)|((src[3]&0xFF)<<24))&0xFFFFFFFFl;
    }
    /**
     * 大端数据转小端数据
     * @param value
     * @return 将读取的数据转为小端模式下的数据，
     */
    private static float Float_EndianBigtoLittle(int[] value)
    {
        if (value == null || value.length != 4) {
            throw new IllegalArgumentException("value数组必须不为空,并且是4位!");
        }
        int i = ((value[0]&0xFF)|((value[1]&0xFF)<<8)|((value[2]&0xFF)<<16)|((value[3]&0xFF)<<24));
        return Float.intBitsToFloat(i);
    }
    /**
     * 大端数据转小端数据
     * @param value
     * @return 将读取的数据转为小端模式下的数据，
     */
    private static int int_EndianBigtoLittle(int value)
    {
        byte[] src = new byte[4];
        src[0] = (byte) ((value>>24)&0xFF);
        src[1] = (byte) ((value>>16)&0xFF);
        src[2] = (byte) ((value>>8)&0xFF);
        src[3] = (byte) ((value)&0xFF);
        return (int) ((src[0]&0xFF)|((src[1]&0xFF)<<8)|((src[2]&0xFF)<<16)|((src[3]&0xFF)<<24));
    }
    /**
     * 读物2位数据，转为无符号数据
     * @param value
     * @return 将读取的数据转为小端模式下的数据，
     */
    private static int uShort_EndianBigtoLittle(int value)
    {
        byte[] src = new byte[2];
        src[0] = (byte) ((value>>8)&0xFF);
        src[1] = (byte) ((value)&0xFF);
        return (int) ((src[0]&0xFF)|((src[1]&0xFF)<<8));
    }
    /**
     *
     */
    public static int vtolh(byte[] bArr) {
        int n = 0;
        for(int i=0;i<bArr.length&&i<4;i++){
            int left = i*8;
            n+= (bArr[i] << left);
        }
        return n;
    }
}
public class JavaCstruct {
    public static void main(String[] args){
        new ChatterServer();
    }
}