package org.lightquark.mailkeeper.mail;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lightquark.mailkeeper.config.MailBoxConfig;
import org.lightquark.mailkeeper.config.MailProviderConfig;
import org.lightquark.mailkeeper.config.Property;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * Created by Light Quark.
 */
public class MailBox
{
    private static final Logger LOG = LogManager.getLogger();

    private JavaMailSenderImpl mailSender;

    public MailBox(MailBoxConfig config)
    {
        MailProviderConfig providerConfig = config.getProvider();

        this.mailSender = new JavaMailSenderImpl();
        this.mailSender.setHost(providerConfig.getHost());
        this.mailSender.setPort(providerConfig.getPort());
        this.mailSender.setProtocol(providerConfig.getProtocol());
        this.mailSender.setUsername(config.getUser());
        this.mailSender.setPassword(config.getPass());

        Properties properties = new Properties();
        for (Property p : providerConfig.getProps())
            properties.setProperty(p.getKey(), p.getValue());
        this.mailSender.setJavaMailProperties(properties);
    }

    public String getUsername()
    {
        return this.mailSender.getUsername();
    }

    //TODO: implement 'attachments'
    public MimeMessage createMail(String to, String subject, String msg)
    {
        try
        {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setFrom(this.mailSender.getUsername());
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(msg, true);
            return message;
        }
        catch (MessagingException e)
        {
            LOG.error(e.getMessage() + "\n" + ExceptionUtils.getStackTrace(e));
        }
        return null;
    }

    public void send(MimeMessage message)
    {
        mailSender.send(message);
    }
}
