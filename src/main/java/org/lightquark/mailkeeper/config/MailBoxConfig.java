package org.lightquark.mailkeeper.config;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

/**
 * Created by Light Quark.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "")
public class MailBoxConfig
{
    @XmlAttribute
    protected String user;
    @XmlAttribute
    protected String pass;
    @XmlAttribute(name = "provider")
    protected String providerId;
    protected MailProviderConfig provider;

    public String getProviderId()
    {
        return providerId;
    }

    public void setProviderId(String providerId)
    {
        this.providerId = providerId;
    }

    public String getUser()
    {
        return user;
    }

    public void setUser(String user)
    {
        this.user = user;
    }

    public String getPass()
    {
        return pass;
    }

    public void setPass(String pass)
    {
        this.pass = pass;
    }

    public MailProviderConfig getProvider()
    {
        return provider;
    }

    public void setProvider(MailProviderConfig provider)
    {
        this.provider = provider;
    }

    @Override
    public String toString()
    {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SIMPLE_STYLE);
    }
}
