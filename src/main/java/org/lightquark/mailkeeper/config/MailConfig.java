package org.lightquark.mailkeeper.config;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Light Quark.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "")
@XmlRootElement(name = "config")
public class MailConfig
{
    @XmlElement(name = "provider")
    private List<MailProviderConfig> providers;
    @XmlElement(name = "mailbox")
    private List<MailBoxConfig> mailBoxes;

    public List<MailProviderConfig> getProviders()
    {
        if (providers == null)
            providers = new ArrayList<>();
        return this.providers;
    }

    public List<MailBoxConfig> getMailBoxes()
    {
        if (mailBoxes == null)
            mailBoxes = new ArrayList<>();
        return this.mailBoxes;
    }

    private MailProviderConfig getProviderById(String id)
    {
        if (providers == null)
            return null;

        for (MailProviderConfig p : providers)
        {
            if (p.getId().equals(id))
                return p;
        }
        return null;
    }

    public void prepare()
    {
        if (providers != null && mailBoxes != null)
        {
            for (MailBoxConfig m : mailBoxes)
            {
                MailProviderConfig p = getProviderById(m.getProviderId());
                if (p != null)
                    m.setProvider(p);
            }
        }
    }

    @Override
    public String toString()
    {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SIMPLE_STYLE);
    }
}
