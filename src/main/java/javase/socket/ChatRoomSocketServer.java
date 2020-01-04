package javase.socket;

import java.io.*;
import java.net.Socket;
import java.util.Map;
import java.util.Set;

import static javase.socket.CustomMessage.QUIT_NOTICE_MSG;

/**
 * @author : peng
 * @Description : 每一个客户端对应一个Socket ，对应一个单独的子线程去跑
 * @Date : 2019-12-27
 */
public class ChatRoomSocketServer implements Runnable {

    private Socket socket;
    private MyHashMap<Long, PrintStream> myHashMap = new MyHashMap<>();

    public ChatRoomSocketServer(Socket socket) {
        this.socket = socket;
    }

    /**
     * Q :
     * <1> 基本的输入出入流，不太会用。
     */

    //线程执行体
    @Override
    public void run() {

        try {
            System.out.println("进入了线程.....");
            // 获取socket的输入流
            InputStream inputStream = socket.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            // 获取 socket的输出流
            OutputStream outputStream = socket.getOutputStream();
            PrintStream printStream = new PrintStream(outputStream);

            // 通过读取首行，判断用户消息的类型
            String readLineMSG = null;

            while ((readLineMSG = bufferedReader.readLine()) != null) {
                // 用户登陆操作
                if (readLineMSG.startsWith(UserLable.USER_LOGIN_PREFIX) && readLineMSG.endsWith(UserLable.USER_LOGIN_SUFFIX)) {
                    String userIdMsg = getRealMessage(readLineMSG, UserLable.USER_LOGIN_PREFIX, UserLable.USER_LOGIN_SUFFIX);
                    Long userIdLogin = Long.parseLong(userIdMsg);
                    if (userIdLogin < 0) {
                        return;
                        // TODO: 2019/12/28 用户输入的ID不合法
                    }
                    // 判断用户是否重复登录
                    PrintStream value = myHashMap.getValue(userIdLogin);
                    if (value != null) {
                        value.print(CustomMessage.USER_LOGIN_REPEAT);
                    } else {
                        // 保存用户的socket
                        myHashMap.put(userIdLogin, printStream);
                        printStream.println(CustomMessage.USER_LOGIN_SUCCESS);
                        System.out.println("用户登录成功 ：" + userIdLogin);
                    }

                }
                // 1. 私聊消息@userId:
                else if (readLineMSG.startsWith(UserLable.SINGLE_MSG_PREFIX) && readLineMSG.endsWith(UserLable.SINGLE_MSG_SUFFIX)) {
                    String singleUserId = getRealMessage(readLineMSG, UserLable.SINGLE_MSG_PREFIX, UserLable.SINGLE_MSG_SUFFIX);
                    // 读取消息发给私聊用户
                    PrintStream singleUserPrintStream = myHashMap.getValue(Long.getLong(singleUserId));
                    char[] buffer = new char[10];
                    while (bufferedReader.read(buffer) > 0) {
                        // 将消息发送给私聊对象
                        singleUserPrintStream.print(buffer);
                    }
                }
                // 用户退出聊天室
                else if (readLineMSG.endsWith(UserLable.USER_QUIT)) {
                    String userIdQuit = getRealMessage(readLineMSG, "", UserLable.USER_QUIT);
                    // 从MyHashMap中删除，并通知其他的聊天室成员
                    Map<Long, PrintStream> hashMap = myHashMap.getHashMap();
                    Set<Map.Entry<Long, PrintStream>> entries = hashMap.entrySet();
                    for (Map.Entry<Long, PrintStream> entrySet : entries) {
                        PrintStream printStream1 = entrySet.getValue();
                        printStream1.print(String.format(QUIT_NOTICE_MSG, userIdQuit));
                    }
                    //关闭退出群聊用户的Socket
                    myHashMap.remove(Long.valueOf(userIdQuit));
                }
                // 发送群聊消息，遍历所有的Socket,并转发消息
                else {
                    Map<Long, PrintStream> hashMap = myHashMap.getHashMap();
                    Set<Map.Entry<Long, PrintStream>> entries = hashMap.entrySet();
                    for (Map.Entry<Long, PrintStream> entrySet : entries) {
                        PrintStream printWriterGroup = entrySet.getValue();
                        char[] buffer = new char[10];
                        while (bufferedReader.read(buffer) > 0) {
                            // 将消息发送给私聊对象
                            printWriterGroup.print(buffer);
                        }
                    }
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getRealMessage(String message, String prefix, String suffix) {
        return message.substring(prefix.length(), message.length() - suffix.length());

    }
}
