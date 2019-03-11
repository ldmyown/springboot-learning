package com.telangel.springbootmail.service.impl;

import com.telangel.springbootmail.service.MailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;

/**
 * @version 1.0.0
 * @项目名称： springboot-mail
 * @类名称： MailServiceImpl
 * @类描述：邮件发送服务实现类
 * @author： lid
 * @date： 2019/3/7 11:32
 */
@Service
public class MailServiceImpl implements MailService{

    private final Logger logger = LoggerFactory.getLogger(MailServiceImpl.class);

    @Autowired
    private JavaMailSender mailSender;

    @Value("${mail.fromMail.addr}")
    private String from;

    @Override
    public void sendSimpleMail(String toUser, String subject, String content) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(toUser);
        message.setSubject(subject);
        message.setText(content);
        try {
            mailSender.send(message);
            logger.info("用户{} 发送给{} 的简单邮件发送成功", from, toUser);
        } catch (MailException e) {
            logger.error("用户{} 发送给{} 的简单邮件发送失败", from, toUser, e);
        }

    }

    @Override
    public void sendHtmlMail(String toUser, String subject, String content) {
        MimeMessage message = mailSender.createMimeMessage();
        try {
            //true表示需要创建一个multipart message
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(from);
            helper.setTo(toUser);
            helper.setSubject(subject);
            // 此处参数为true表示邮件为html格式的，为false表示普通的文本
            helper.setText(content, true);
            mailSender.send(message);
            logger.info("用户{} 发送给{} 的html邮件发送成功", from, toUser);
        } catch (MailException e) {
            logger.error("用户{} 发送给{} 的html邮件发送失败", from, toUser, e);
        } catch (MessagingException e) {
            logger.error("用户{} 发送给{} 的html邮件发送失败", from, toUser, e);
        }

    }

    @Override
    public void sendAttachmentsMail(String toUser, String subject, String content, String filePath) {
        MimeMessage message = mailSender.createMimeMessage();
        try {
            //true表示需要创建一个multipart message
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(from);
            helper.setTo(toUser);
            helper.setSubject(subject);
            // 此处参数为true表示邮件为html格式的，为false表示普通的文本
            helper.setText(content, true);

            // 添加附件
            FileSystemResource file = new FileSystemResource(new File(filePath));
            String fileName = file.getFile().getName();
            helper.addAttachment(fileName, file);

            mailSender.send(message);
            logger.info("用户{} 发送给{} 的带附件邮件发送成功", from, toUser);
        } catch (MailException e) {
            logger.error("用户{} 发送给{} 的带附件邮件发送失败", from, toUser, e);
        } catch (MessagingException e) {
            logger.error("用户{} 发送给{} 的带附件邮件发送失败", from, toUser, e);
        }
    }

    @Override
    public void sendInlineResourceMail(String toUser, String subject, String content, String rscPath, String rscId) {
        MimeMessage message = mailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(from);
            helper.setTo(toUser);
            helper.setSubject(subject);
            helper.setText(content, true);

            FileSystemResource res = new FileSystemResource(new File(rscPath));
            helper.addInline(rscId, res);

            mailSender.send(message);
            logger.info("用户{} 发送给{} 的嵌入静态资源的邮件已经发送。");
        } catch (MessagingException e) {
            logger.error("用户{} 发送给{} 的发送嵌入静态资源的邮件时发生异常！", e);
        }
    }


}
