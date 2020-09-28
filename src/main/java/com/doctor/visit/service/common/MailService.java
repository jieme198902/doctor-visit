package com.doctor.visit.service.common;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;
import java.io.File;
import java.util.Date;

/**
 * @author wangkuan
 * 发送邮件的服务
 */
@Service
public class MailService {

    private final JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String username;

    public MailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    /**
     * 发送邮件
     *
     * @param to         收件人
     * @param subject    主题
     * @param text       内容
     * @param html       是否html
     * @param attachFile 附件
     * @throws Exception
     */
    public void sendEmail(String to, String subject, String text, boolean html, File attachFile) throws Exception {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
        //发件人
        mimeMessageHelper.setFrom(username);
        //收件人
        mimeMessageHelper.setTo(to);
        //主题
        mimeMessageHelper.setSubject(subject);
        //内容
        mimeMessageHelper.setText(text, html);
        //发送时间
        mimeMessageHelper.setSentDate(new Date());

        if (null != attachFile && attachFile.exists()) {
            //添加附件
            mimeMessageHelper.addAttachment(MimeUtility.encodeWord(attachFile.getName(),"utf-8","B"), attachFile);
        }
        //发送
        javaMailSender.send(mimeMessage);
    }

    /**
     * 发送text格式的邮件，带附件
     *
     * @param to
     * @param subject
     * @param text
     * @param attachFile
     * @throws Exception
     */
    public void sendEmailText(String to, String subject, String text, File attachFile) throws Exception {
        sendEmail(to, subject, text, false, attachFile);
    }

    /**
     * 发送text格式的邮件，无附件
     *
     * @param to
     * @param subject
     * @param text
     * @throws Exception
     */
    public void sendEmailText(String to, String subject, String text) throws Exception {
        sendEmail(to, subject, text, false, null);
    }

    /**
     * 发送html格式的邮件，带附件
     *
     * @param to
     * @param subject
     * @param text
     * @param attachFile
     * @throws Exception
     */
    public void sendEmailHtml(String to, String subject, String text, File attachFile) throws Exception {
        sendEmail(to, subject, text, true, attachFile);
    }

    /**
     * 发送html格式的邮件，无附件
     *
     * @param to
     * @param subject
     * @param text
     * @throws Exception
     */
    public void sendEmailHtml(String to, String subject, String text) throws Exception {
        sendEmail(to, subject, text, true, null);
    }

}
