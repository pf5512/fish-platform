package com.ippteam.fish.util.email;

/**
 * Created by isunimp on 16/11/17.
 */

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;

/**
 * 简单邮件（不带附件的邮件）发送器
 */
public class UserAgent {

    class Authenticator extends javax.mail.Authenticator {
        String userName = null;
        String password = null;

        public Authenticator() {
        }

        public Authenticator(String username, String password) {
            this.userName = username;
            this.password = password;
        }

        protected PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication(userName, password);
        }
    }

    private ServerInfo serverInfo;
    private Session sendMailSession;
    private Address from;
    private String nick;

    public UserAgent(ServerInfo serverInfo) throws UnsupportedEncodingException, AddressException {
        this.serverInfo = serverInfo;
        Authenticator authenticator = new Authenticator(serverInfo.getUserName(), serverInfo.getPassword());
        Properties p = new Properties();
        p.put("mail.transport.protocol", "smtp");
        p.put("mail.smtp.host", serverInfo.getServerHost());
        p.put("mail.smtp.port", serverInfo.getServerPort());
        p.put("mail.smtp.auth", "true");
        sendMailSession = Session.getDefaultInstance(p, authenticator);
        nick = javax.mail.internet.MimeUtility.encodeText(serverInfo.getNick());
        from = new InternetAddress(nick + "<" + serverInfo.getUserName() + ">");
    }

    public void sendTextMail(EmailInfo emailInfo) throws MessagingException {
        Address to = new InternetAddress(emailInfo.getToAddress());
        String mailContent = emailInfo.getContent();

        Message mailMessage = new MimeMessage(sendMailSession);
        mailMessage.setFrom(from);
        mailMessage.setRecipient(Message.RecipientType.TO, to);
        mailMessage.setSubject(emailInfo.getSubject());
        mailMessage.setSentDate(new Date());
        mailMessage.setText(mailContent);
        Transport.send(mailMessage);
    }

    public void sendHtmlMail(EmailInfo mailInfo) throws MessagingException {
        Address to = new InternetAddress(mailInfo.getToAddress());
        BodyPart html = new MimeBodyPart();
        html.setContent(mailInfo.getContent(), "text/html; charset=utf-8");
        Multipart mainPart = new MimeMultipart();
        mainPart.addBodyPart(html);

        Message mailMessage = new MimeMessage(sendMailSession);
        mailMessage.setFrom(from);
        mailMessage.setRecipient(Message.RecipientType.TO, to);
        mailMessage.setSubject(mailInfo.getSubject());
        mailMessage.setSentDate(new Date());
        mailMessage.setContent(mainPart);
        Transport.send(mailMessage);
    }
}