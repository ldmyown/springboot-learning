package com.telangel.springbootmail.service;

/**
 *
 * @类描述：邮件发送类
 * @项目名称： springboot-mail
 * @类名称： MailService
 * @author： lid
 * @date： 2019/3/7 11:31
 * @version 1.0.0
 */
public interface MailService {

    /**
     * 发送简单邮件
     * @param toUser
     * @param subject
     * @param content
     */
    void sendSimpleMail(String toUser, String subject, String content);

    /**
     * 发送html格式的邮件
     * @param toUser
     * @param subject
     * @param content
     */
    void sendHtmlMail(String toUser, String subject, String content);

    /**
     * 发送带附件的邮件
     * @param toUser
     * @param subject
     * @param content
     */
    void sendAttachmentsMail(String toUser, String subject, String content, String filePath);

    /**
     * 发送带静态资源的邮件
     * @param toUser
     * @param subject
     * @param content
     * @param rscPath
     * @param rscId
     */
    void sendInlineResourceMail(String toUser, String subject, String content, String rscPath, String rscId);

}
