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
        Socket socket = new Socket("192.168.126.1", 65533);
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
            System.out.println("输入用户名....等待Server回应.....");
            String response = bufferedReader.readLine();
            System.out.println("回应" + response);
            // 用户登录失败,则重试
            if (response != null && response.equals(CustomMessage.USER_LOGIN_REPEAT)) {
                continue;
            }
            // 登录成功
            if (response != null && response.equals(CustomMessage.USER_LOGIN_SUCCESS)) {
                System.out.println("真的成功啦.....");
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
