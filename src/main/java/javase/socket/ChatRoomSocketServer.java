package javase.socket;

import java.io.*;
import java.net.Socket;

/**
 * @author : peng
 * @Description : 每一个客户端对应一个Socket ，对应一个单独的子线程去跑
 * @Date : 2019-12-27
 */
public class ChatRoomSocketServer implements Runnable {

    private Socket socket;
    private MyHashMap<Long, PrintWriter> myHashMap = new MyHashMap<>();

    public ChatRoomSocketServer(Socket socket) {
        this.socket = socket;
    }
    /**
     * Q :
     * <1> 基本的输入出入流，不太会用。
     *
     *
     */

    //线程执行体
    @Override
    public void run() {

        try {
            // 获取socket的输入流
            InputStream inputStream = socket.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            // 获取 socket的输出流
            OutputStream outputStream = socket.getOutputStream();
            PrintWriter printWriter = new PrintWriter(outputStream);

            // 通过读取首行，判断用户消息的类型
            String readLineMSG = bufferedReader.readLine();
            // 通过 PrintStream流 首先判断用户是否已经登陆，没有登陆则报错，提示信息
            Long userId = myHashMap.getKey(printWriter);
            if (userId == null || userId < 0 ){
                // 用户未登陆，提示用户先执行登陆操作
                // TODO: 2019/12/28
                return;
            }
            // 用户登陆操作
            if (readLineMSG.startsWith(UserLable.USER_LOGIN_PREFIX) && readLineMSG.endsWith(UserLable.USER_LOGIN_SUFFIX)){
                String userIdMsg = getRealMessage(readLineMSG, UserLable.USER_LOGIN_PREFIX, UserLable.USER_LOGIN_SUFFIX);
                Long userIdLogin = Long.getLong(userIdMsg);
                if (userIdLogin == null || userIdLogin < 0 ){
                    return;
                    // TODO: 2019/12/28 用户输入的ID不合法
                }
                // 保存用户的信息
                myHashMap.put(userIdLogin,printWriter);
            }
            // 1. 私聊消息@userId:
            if (readLineMSG.startsWith(UserLable.SINGLE_MSG_PREFIX)&&readLineMSG.endsWith(UserLable.SINGLE_MSG_SUFFIX)){
                String singleUserId = getRealMessage(readLineMSG, UserLable.SINGLE_MSG_PREFIX, UserLable.SINGLE_MSG_SUFFIX);
                // 读取消息发给私聊用户
                PrintWriter singleUserPrintWriter = myHashMap.getValue(Long.getLong(singleUserId));
                char[] buffer = new char[10 ];
                int hasRead = 0;
                while((hasRead = bufferedReader.read(buffer)) > 0){
                    // 写给私聊对象
                    singleUserPrintWriter.print(buffer);
                    bufferedReader.read(buffer)
                }




            }



        } catch (IOException e) {
            e.printStackTrace();
        }
        //myHashMap.put();
    }

    private String getRealMessage(String message,String prefix,String suffix){
        return message.substring(prefix.length(), message.length() - suffix.length());

    }
}
