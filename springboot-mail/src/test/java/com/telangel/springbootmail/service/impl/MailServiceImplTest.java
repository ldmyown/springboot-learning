package com.telangel.springbootmail.service.impl;

import com.telangel.springbootmail.service.MailService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MailServiceImplTest {

    @Autowired
    private MailService mailService;

    @Test
    public void sendSimpleMail() throws Exception {
        mailService.sendSimpleMail("lid@koal.com", "测试邮件", "this is just a test");
    }

    @Test
    public void sendHtmlMail() throws Exception {
        String content = "<html>\n" +
                "<body>\n" +
                "    <h3>测试html邮件， 这是一封Html邮件!</h3>\n" +
                "</body>\n" +
                "</html>";

        mailService.sendHtmlMail("lid@koal.com", "测试邮件", content);
    }

    @Test
    public void sendAttachmentsMail() throws Exception {
        String content = "<html>\n" +
                "<body>\n" +
                "    <h3>测试html邮件， 这是一封Html邮件!</h3>\n" +
                "</body>\n" +
                "</html>";

        String file = "D:\\ssr.md";
        mailService.sendAttachmentsMail("lid@koal.com", "测试邮件", content, file);
    }

    @Test
    public void sendInlineResourceMail() {
        String rscId = "tel001";
        String content = "<html><body>这是有图片的邮件：<img src=\'cid:" + rscId + "\' ></body></html>";
        String imgPath = "D:\\1.png";

        mailService.sendInlineResourceMail("lid@koal.com", "主题：这是有图片的邮件", content, imgPath, rscId);
    }
}