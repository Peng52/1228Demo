package javase.socket;

import javax.swing.*;
import java.io.*;
import java.net.Socket;

/**
 * @author : peng
 * @Description : 聊天室 - 客户端
 * @Date : 2019-12-22
 */
public class ChatRoomClient {


    public static void main(String[] str) throws IOException {
        Socket socket = new Socket("192.168.1.228", 65533);
        //  输入流
        InputStream inputStream = socket.getInputStream();
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        // 输出流
        OutputStream outputStream = socket.getOutputStream();
        PrintStream printStream = new PrintStream(outputStream);
        // 控制台的用户输入流
        BufferedReader sysIn = new BufferedReader(new InputStreamReader(System.in));

        // 用户登录的逻辑
        String tip = "";
        while (true) {
            String readLine = JOptionPane.showInputDialog(tip + "请输入用户名");
            printStream.println(readLine);
            String response = bufferedReader.readLine();
            // 用户登录失败,则重试
            if (response != null && response.equals(CustomMessage.USER_LOGIN_REPEAT)) {
                continue;
            }
            // 登录成功
            if (response != null && response.equals(CustomMessage.USER_LOGIN_SUCCESS)) {
                break;
            }
        }

        // 发送私聊或者公聊消息
        String userMessage = null;
        while ((userMessage = sysIn.readLine()) != null) {
            printStream.println(userMessage);
        }


        // 一次读取一行内容
        int count = 1;
        while (true) {
            String readLine = bufferedReader.readLine();
            System.out.println(bufferedReader.readLine());
            count++;
            // 最多循环一百行
            if (readLine == null || count > 100) {
                break;
            }
        }


    }


    private String getRealMessage(String message, String prefix, String suffix) {
        return message.substring(prefix.length(), message.length() - suffix.length());

    }


}
