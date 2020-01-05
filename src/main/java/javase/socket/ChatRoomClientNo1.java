package javase.socket;

import javax.swing.*;
import java.io.*;
import java.net.Socket;

/**
 * @author : peng
 * @Description : 聊天室 - 客户端
 * @Date : 2019-12-22
 */
public class ChatRoomClientNo1 {


    private static String homeIPAddress = "192.168.126.1";
    private static String companyIPAddress = "192.168.1.228";

    public static void main(String[] str) throws IOException {
        Socket socket = new Socket(homeIPAddress, 65533);
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
            String readLine = JOptionPane.showInputDialog(tip + "请 001 号客户端，请输入用户名");
            readLine = UserLabel.USER_LOGIN_PREFIX + readLine + UserLabel.USER_LOGIN_SUFFIX;
            printStream.println(readLine);
            String response = bufferedReader.readLine();
            // 用户登录失败,则重试
            if (response != null && response.equals(CustomMessage.USER_LOGIN_REPEAT)) {
                System.out.println(response);
                continue;
            }
            // 登录成功
            if (response != null && response.equals(CustomMessage.USER_LOGIN_SUCCESS)) {
                System.out.println(response);
                break;
            }
        }
        //
        new Thread(new ChatRoomClientThead(bufferedReader),"客户端读取线程").start();
        // 发送私聊或者公聊消息
        String userMessage = null;
        while ((userMessage = sysIn.readLine()) != null) {
            printStream.println(userMessage);
        }

    }


    private String getRealMessage(String message, String prefix, String suffix) {
        return message.substring(prefix.length(), message.length() - suffix.length());

    }


    /**
     * 删除代码：
     */

    /*
    *  // 一次读取一行内容
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
    *
    *
    *
    *
    *
    *
    *
    *
    *
    * */



}
