package com.mouxianyu.studentsociety.common.util;


import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import java.util.Properties;

/**
 * @Description 邮件工具类
 * @Author 张桂树 guishu.zhang@luckincoffee.com
 * @Date 2019/8/29 14:07
 */
public final class MailUtils {
    /**
     * 发件人称号，同邮箱地址
     */
    private static final String USER = "kingsme@yeah.net";

    /**
     * 邮箱授权码
     */
    private static final String PASSWORD = "VOWEYBUXACJMVXJK";

    /**
     * 发送邮件
     *
     * @param to    收件人邮箱
     * @param text  正文
     * @param title 标题
     * @return boolean
     */
    public static boolean sendMail(String to, String text, String title) {
        try {
            final Properties props = new Properties();
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.host", "smtp.yeah.net");

            props.put("mail.user", USER);
            props.put("mail.password", PASSWORD);

            Authenticator authenticator = new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    String usrName = props.getProperty("mail.user");
                    String password = props.getProperty("mail.password");
                    return new PasswordAuthentication(usrName, password);
                }
            };
            // 使用环境属性和授权信息，创建邮件会话
            Session mailSession = Session.getInstance(props, authenticator);
            // 创建邮件消息
            MimeMessage message = new MimeMessage(mailSession);
            // 设置发件人
            String username = props.getProperty("mail.user");
            InternetAddress form = new InternetAddress(username);
            message.setFrom(form);

            // 设置收件人
            InternetAddress [] toAddress = new InternetAddress[2];
            toAddress[0]=new InternetAddress(USER);
            toAddress[1]=new InternetAddress(to);
            message.setRecipients(Message.RecipientType.TO, toAddress);

            // 设置邮件标题
            message.setSubject(title);

            // 设置邮件的内容体
            message.setContent(text, "text/html;charset=UTF-8");
            // 发送邮件
            Transport.send(message);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean sendCheckCodeMail(String address,String checkCode){
        String content = "您的学生社团管理系统验证码为："+checkCode+"，请不要告诉任何人！";
        return sendMail(address,content,"学生社团系统验证码");
    }
}
