package javase.socket;

import java.io.*;
import java.net.Socket;
import java.util.Date;
import java.util.Map;
import java.util.Set;

import static javase.socket.CustomMessage.QUIT_MYSELF_MSG;
import static javase.socket.CustomMessage.QUIT_NOTICE_MSG;

/**
 * @author : peng
 * @Description : 每一个客户端对应一个Socket ，对应一个单独的子线程去跑
 * @Date : 2019-12-27
 */
public class ChatRoomSocketServer implements Runnable {

    private Socket socket;
    private MyHashMap<Long, PrintStream> myHashMap;

    public ChatRoomSocketServer(Socket socket, MyHashMap<Long, PrintStream> myHashMap) {
        this.socket = socket;
        this.myHashMap = myHashMap;
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
            // 用户登陆成功后保存ID
            Long loginUserId = null;
            // 消息前缀123
            String sendMsgPre = null;

            while ((readLineMSG = bufferedReader.readLine()) != null) {
                // 用户登陆操作
                if (readLineMSG.startsWith(UserLabel.USER_LOGIN_PREFIX) && readLineMSG.endsWith(UserLabel.USER_LOGIN_SUFFIX)) {
                    String userIdMsg = getRealMessage(readLineMSG, UserLabel.USER_LOGIN_PREFIX, UserLabel.USER_LOGIN_SUFFIX);
                    Long userIdLogin = Long.parseLong(userIdMsg);
                    if (userIdLogin < 0) {
                        return;
                    }
                    // 判断用户是否重复登录
                    PrintStream value = myHashMap.getValue(userIdLogin);
                    if (value != null) {
                        printStream.println(CustomMessage.USER_LOGIN_REPEAT);
                    } else {
                        // 保存用户的socket
                        myHashMap.put(userIdLogin, printStream);
                        printStream.println(CustomMessage.USER_LOGIN_SUCCESS);
                        System.out.println("用户: " + userIdLogin + "登录服务器成功...");
                        loginUserId = userIdLogin;
                        sendMsgPre = "用户" + loginUserId + ": ";
                    }

                }
                // 1. 私聊消息@userId:
                else if (readLineMSG.startsWith(UserLabel.SINGLE_MSG_PREFIX)) {
                    String singleUserId = getUserId(readLineMSG, UserLabel.SINGLE_MSG_PREFIX, UserLabel.SINGLE_MSG_CONTAINS);
                    // 读取消息发给私聊用户
                    PrintStream singleUserPrintStream = myHashMap.getValue(Long.parseLong(singleUserId));
                    // 将消息发送给私聊对象
                    singleUserPrintStream.println(sendMsgPre + readLineMSG);
                    // 服务器端添加日志记录
                    System.out.println("给用户：" + singleUserId + "发送消息 ：" + readLineMSG);

                }
                // 用户退出聊天室
                else if (readLineMSG.endsWith(UserLabel.USER_QUIT)) {
                    // 从MyHashMap中删除，并通知其他的聊天室成员
                    Map<Long, PrintStream> hashMap = myHashMap.getHashMap();
                    Long userId = myHashMap.getKey(printStream);
                    // 获取登录用户
                    Set<Map.Entry<Long, PrintStream>> entries = hashMap.entrySet();
                    for (Map.Entry<Long, PrintStream> entrySet : entries) {
                        PrintStream printStream1 = entrySet.getValue();
                        if (printStream1.equals(printStream)) {
                            printStream1.println(QUIT_MYSELF_MSG);
                        } else {
                            printStream1.println("系统消息：" + String.format(QUIT_NOTICE_MSG, userId));
                        }
                    }
                    //关闭退出群聊用户的Socket
                    myHashMap.remove(userId);
                    // 服务器端添加日志记录
                    System.out.println("用户：" + userId + "退出聊天室....");
                }
                // 发送群聊消息，遍历所有的Socket,并转发消息
                else {
                    Map<Long, PrintStream> hashMap = myHashMap.getHashMap();
                    Set<Map.Entry<Long, PrintStream>> entries = hashMap.entrySet();
                    for (Map.Entry<Long, PrintStream> entrySet : entries) {
                        PrintStream printWriterGroup = entrySet.getValue();
                        if (!printStream.equals(printWriterGroup)) {
                            // 将消息发送给(除自己以外)每一个客户端
                            printWriterGroup.println(sendMsgPre + readLineMSG + String.format(CustomMessage.SEND_MSG_TIME,new Date()));
                            System.out.println("客户端ID "+ loginUserId + "：给ID " +
                                    entrySet.getKey() + " 发送群聊消息：" + readLineMSG + String.format(CustomMessage.SEND_MSG_TIME,new Date()));
                        }
                    }
                }

            }
        } catch (
                IOException e) {
            e.printStackTrace();
        }

    }

    private String getRealMessage(String message, String prefix, String suffix) {
        return message.substring(prefix.length(), message.length() - suffix.length());

    }

    private String getUserId(String message, String prefix, String suffix) {
        String[] split = message.split(suffix);
        String s = split[0];
        String userId = s.substring(prefix.length());
        return userId;
    }

}
