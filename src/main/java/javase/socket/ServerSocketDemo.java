package javase.socket;

import org.junit.Test;

import java.io.*;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author : peng
 * @Description : 监听ServerSockey
 * @Date : 2019-12-22
 */
public class ServerSocketDemo {


    // 服务端SeverSockt
    @Test
    public void test() throws IOException {
        //Inet4Address.getByAddress(new byte[]{192,168,126,1});
        InetAddress ipAddress = Inet4Address.getByName("192.168.126.1");
        ServerSocket serverSocket = new ServerSocket(65533,10,ipAddress);
        Socket socket = serverSocket.accept();
        OutputStream outputStream = socket.getOutputStream();
        PrintStream printStream = new PrintStream(outputStream);
        printStream.println("SeverSocket ： 2020,新年快乐...");
        printStream.close();
        serverSocket.close();


    }

    //  printStream : 字节输出流
    // printWriter ： 字符输出流
    // 所以Print 是输出流  ； 是没有输入流的
    @Test
    public void testPrintStream() throws FileNotFoundException {
        FileOutputStream fileOutputStream = new FileOutputStream("C:\\Users\\PENG\\Desktop\\test.txt");
        //new PrintStream(fileInputStream);
        PrintWriter printWriter = new PrintWriter(fileOutputStream);
        printWriter.println("闪避");
        printWriter.close();

    }



}
