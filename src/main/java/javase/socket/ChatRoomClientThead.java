package javase.socket;

import java.io.BufferedReader;
import java.io.IOException;

/**
 * @author : peng
 * @Description : 客户端的子线程(不断的循环读取服务器端的响应)
 * @Date : 2020-01-05
 */
public class ChatRoomClientThead implements Runnable {

    /**
     * 输入流
     */
    private BufferedReader bufferedReader;

    public ChatRoomClientThead(BufferedReader bufferedReader) {
        System.out.println("启动客户端读取线程 Success....");
        this.bufferedReader = bufferedReader;
    }


    @Override
    public void run() {

        while (true) {

            String response = null;
            try {
                response = bufferedReader.readLine();
            } catch (Exception e) {
                System.out.println("客户端子线程出现异常.....");
            }
            System.out.println(response);
        }
    }
}
