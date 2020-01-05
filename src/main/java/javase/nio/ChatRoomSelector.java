package javase.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
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
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        while (selector.select() > 0){
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            for (SelectionKey selectionKey : selectionKeys){
                selectionKey.
                SelectableChannel channel = selectionKey.channel();
                channel.
            }

            SocketChannel socketChannel = serverSocketChannel.accept();
            // 将SocketChannel注册到 Selector上去
            socketChannel.register(selector,SelectionKey.OP_READ);
            // 启动子线程
            socketChannel.configureBlocking(false);


        }

    }







}
