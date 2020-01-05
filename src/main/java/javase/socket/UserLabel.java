package javase.socket;

/**
 * @author : peng
 * @Description : 用户聊天的关键标识
 * @Date : 2019-12-27
 */
public interface UserLabel {

    /**** 私聊消息的前缀 *****/
    String SINGLE_MSG_PREFIX = "@";

    /**** 私聊消息的后缀 *****/
    String SINGLE_MSG_CONTAINS = ":";

    /**** 用户登陆 *****/
    String USER_LOGIN_PREFIX = "##";
    String USER_LOGIN_SUFFIX = "$$";

    /**** 用户退出 *****/
    String USER_QUIT = ":q!";











}
