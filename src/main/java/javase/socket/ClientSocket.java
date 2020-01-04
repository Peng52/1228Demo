package javase.socket;

import org.junit.Test;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

/**
 * @author : peng
 * @Description : 监听ServerSockey
 * @Date : 2019-12-22
 */
public class ClientSocket {
    // ClientSocket
    @Test
    public void testClientSocket() throws IOException {
        Socket socket = new Socket("192.168.1.228",65533);
        InetSocketAddress inetSocketAddress = new InetSocketAddress("192.168.126.1",65533);
        //socket.connect(inetSocketAddress);
        InputStream inputStream = socket.getInputStream();
        // 将字节流 转化为 字符输出流
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        // 一次读取一行内容
        String s = bufferedReader.readLine();
        System.out.println(s);

    }




}
