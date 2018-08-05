package org.mySite.common.util;

import org.mySite.common.constant.SSCConstants;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class MailUtil {
    private static String mail_host = SSCConstants.mail_host;
    private static String mail_transport_protocol = SSCConstants.mail_transport_protocol;
    private static String mail_smtp_auth = SSCConstants.mail_smtp_auth;
    private static String mail_send_to = SSCConstants.mail_send_to;
    private static String mail_send_from = SSCConstants.mail_send_from;

    public static void sendSSCAcountMail(String content) {
        try {
            sendMail("账户监控",content, mail_send_to);
        } catch (Exception e) {
            System.out.println("邮件发送失败");
            e.printStackTrace();
        }
    }

    public static boolean sendMail(String subject, String content, String sendTo) throws Exception {
        Properties prop = new Properties();
        prop.setProperty("mail.host", mail_host);
        prop.setProperty("mail.transport.protocol", mail_transport_protocol);
        prop.setProperty("mail.smtp.auth", mail_smtp_auth);
        //使用JavaMail发送邮件的5个步骤
        //1、创建session
        Session session = Session.getInstance(prop);
        //开启Session的debug模式，这样就可以查看到程序发送Email的运行状态
        session.setDebug(true);
        //2、通过session得到transport对象
        Transport ts = session.getTransport();
        //3、使用邮箱的用户名和密码连上邮件服务器，发送邮件时，发件人需要提交邮箱的用户名和密码给smtp服务器，用户名和密码都通过验证之后才能够正常发送邮件给收件人。
        ts.connect(mail_host, mail_send_from, "liaowenhua5512");
        //4、创建邮件
        Message message = createSimpleMail(session, subject, content, sendTo);
        //5、发送邮件
        System.out.println("开始发送邮件...");
        ts.sendMessage(message, message.getAllRecipients());
        ts.close();
        System.out.println("邮件发送完成！");
        return true;
    }

    public static void main( String[] args ) throws Exception {
        sendMail("账户信息","test",mail_send_to);
    }

    /**
     * @Description: 创建一封只包含文本的邮件
     */
    private static MimeMessage createSimpleMail(Session session, String subject, String content, String sendTo)
            throws Exception {
        //创建邮件对象
        MimeMessage message = new MimeMessage(session);
        //指明邮件的发件人
        message.setFrom(new InternetAddress(mail_send_from));
        //指明邮件的收件人，现在发件人和收件人是一样的，那就是自己给自己发
        message.setRecipient(Message.RecipientType.TO, new InternetAddress(sendTo));
        //邮件的标题
        message.setSubject(subject);
        //邮件的文本内容
        message.setContent(content, "text/html;charset=UTF-8");
        //返回创建好的邮件对象
        return message;
    }
}
