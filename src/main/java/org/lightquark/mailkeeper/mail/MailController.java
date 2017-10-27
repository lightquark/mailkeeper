package org.lightquark.mailkeeper.mail;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lightquark.mailkeeper.config.MailBoxConfig;
import org.lightquark.mailkeeper.config.MailConfig;

import javax.mail.internet.MimeMessage;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Light Quark.
 */
public class MailController
{
    private static final Logger LOG = LogManager.getLogger();

    private List<MailBox> mailBoxes;

    public MailController(MailConfig cfg)
    {
        this.mailBoxes = new ArrayList<>();
        for (MailBoxConfig mbc : cfg.getMailBoxes())
            this.mailBoxes.add(new MailBox(mbc));
    }

    public void checkAll()
    {
        LOG.info("Checking mailboxes has started.");
        for (MailBox mbFrom : mailBoxes)
        {
            LOG.info("Checking mailbox [" + mbFrom.getUsername() + "]");
            for (MailBox mbTo : mailBoxes)
            {
                if (mbFrom != mbTo)
                {
                    LOG.info("Sending mail from [" + mbFrom.getUsername() + "] to [" + mbTo.getUsername() + "] ...");
                    MimeMessage mail = mbFrom.createMail(mbTo.getUsername(), "MailKeeper auto mail", "This mail is created by MailKeeper.");
                    mbFrom.send(mail);
                    LOG.info("Mail sent OK");
                }
            }
        }
        LOG.info("Checking mailboxes has finished.");
    }
}
