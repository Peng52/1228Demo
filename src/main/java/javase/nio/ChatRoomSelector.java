package javase.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.channels.*;
import java.util.Set;

/**
 * @author : peng
 * @Description : SelectableChannel对象的多路复用器
 * @Date : 2020-01-05
 */
public class ChatRoomSelector {

    private static String homeIPAddress = "192.168.126.1";
    private static String companyIPAddress = "192.168.1.228";

    public static void main(String[] args) throws IOException {
        // 获取Channel多路复用器
        Selector selector = Selector.open();
        // 服务器端 SeverSocket
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        InetSocketAddress inetSocketAddress = new InetSocketAddress(homeIPAddress, 65533);
        serverSocketChannel.bind(inetSocketAddress);
        // 设置非阻塞式IO
        serverSocketChannel.configureBlocking(false);
        // 注册到 Selector 上去
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        while (selector.select() > 0){
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            for (SelectionKey selectionKey : selectionKeys){
                // 从selector上的被选择的SelectionKey集合中删除正在处理的SelectionKey
                selector.selectedKeys().remove(selectionKey);
                SelectableChannel channel = selectionKey.channel();
                // ServerSocket 有客户端连接进来
                if (channel.validOps() == SelectionKey.OP_ACCEPT){
                    ServerSocketChannel ServerSocketChannel = (ServerSocketChannel) channel;
                    SocketChannel socketChannel = ServerSocketChannel.accept();
                    // 设置成非阻塞模式
                    socketChannel.configureBlocking(false);
                    // 将socketChannel注册到 Selector 上去 (可读可写)
                    socketChannel.register(selector,SelectionKey.OP_READ & SelectionKey.OP_WRITE);
                    // 将serverSocketChannel 重新设置成
                    selectionKey.interestOps(SelectionKey.OP_ACCEPT);
                }


            }

            SocketChannel socketChannel = serverSocketChannel.accept();
            // 将SocketChannel注册到 Selector上去
            socketChannel.register(selector,SelectionKey.OP_READ);
            // 启动子线程
            socketChannel.configureBlocking(false);


        }

    }







}
