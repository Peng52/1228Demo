package javase.socket;

/**
 * @author : peng
 * @Description : 自定义的消息
 * @Date : 2019-12-30
 */
public interface CustomMessage {

    String QUIT_NOTICE_MSG = "用户%s已退出聊天室！";

    String USER_LOGIN_REPEAT = "您的用户名已经被占用，换一个试试！";

    String USER_LOGIN_SUCCESS = "恭喜登录成功，开始畅快的聊天吧！";

}
