package javase.socket;

import java.io.IOException;
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

        // 创建ServerSocket
        InetAddress ipAddress = Inet4Address.getByName("192.168.126.1");
        ServerSocket serverSocket = new ServerSocket(65533, 10, ipAddress);
        int count = 0;
        while (true) {
            count ++;
            Socket accept = serverSocket.accept();
            // 每一个客户端连接过来 Socket，都会用一个单独的线程去跑
            new Thread(new ChatRoomSocketServer(accept),"I am Thread " + count).start();
        }

    }



}
