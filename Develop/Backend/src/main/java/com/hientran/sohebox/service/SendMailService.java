package com.hientran.sohebox.service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.hientran.sohebox.utils.VelocityUtils;
import com.hientran.sohebox.vo.SendEmailContentVO;

/**
 * @author hientran
 */
@Service
public class SendMailService extends BaseService {

    private static final long serialVersionUID = 1L;

    @Value("${spring.mail.username}")
    private String emailService;

    @Autowired
    private JavaMailSender gmailSender;

    @Autowired
    private VelocityUtils velocityUtils;

    public void sendMail(SendEmailContentVO vo) throws JsonProcessingException, MessagingException {
        MimeMessage message = gmailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        // Set mail from
        if (StringUtils.isNotBlank(vo.getMailFrom())) {
            helper.setFrom(vo.getMailFrom());
        } else {
            helper.setFrom(emailService);
        }

        // Set mail to
        helper.setTo(vo.getListMailTo());

        // Set subject
        if (StringUtils.isNotBlank(vo.getSubject())) {
            helper.setSubject(vo.getSubject());
        }

        // Set text body
        if (StringUtils.isNotBlank(vo.getTextBody())) {
            helper.setText(vo.getTextBody());
        } else {
            String textBody = velocityUtils.getTextByVmTemplate(vo.getTemplatePath(), vo.getParameters());
            helper.setText(textBody);
        }

        // Set attachments
        if (CollectionUtils.isNotEmpty(vo.getPathToAttachments())) {
            for (String filePath : vo.getPathToAttachments()) {
                FileSystemResource file = new FileSystemResource(filePath);
                helper.addAttachment(file.getFilename(), file);
            }
        }

        // Send mail
        gmailSender.send(message);
    }
}
