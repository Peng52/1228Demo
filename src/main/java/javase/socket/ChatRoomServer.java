package javase.socket;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author : peng
 * @Description : 聊天室 - 服务器端
 * @Date : 2019-12-22
 */
public class ChatRoomServer {
    /**
     * 实现用户登陆、私聊、群聊思路：
     * 1.定义特殊的字符标记用户的消息结构
     * 2.每一个Socket连接单独使用一个线程
     * 3.根据消息的定义，实现 私聊、群聊消息的转发
     */

    // 主线程
    public static void main(String[] args) throws IOException {
        ChatRoomServer chatRoomServer = new ChatRoomServer();

        // 创建ServerSocket
        InetAddress ipAddress = Inet4Address.getByName("192.168.126.1");
        ServerSocket serverSocket = new ServerSocket(65533, 10, ipAddress);
        while (true) {
            Socket accept = serverSocket.accept();
            //new Thread(new ChatRoomSocketServer()).start();
            // 每一个客户端连接过来 Socket，都会用一个单独的线程去跑

        }

    }
    // 实现一个HashMap : key 与 Value 都有唯一性


}
