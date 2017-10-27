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
public class MailProviderConfig
{
    @XmlAttribute
    protected String id;
    @XmlElement
    protected String host;
    @XmlElement
    protected int port;
    @XmlElement
    protected String protocol;
    @XmlElement(name = "prop")
    protected List<Property> props;

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getHost()
    {
        return host;
    }

    public void setHost(String host)
    {
        this.host = host;
    }

    public int getPort()
    {
        return port;
    }

    public void setPort(int port)
    {
        this.port = port;
    }

    public String getProtocol()
    {
        return protocol;
    }

    public void setProtocol(String protocol)
    {
        this.protocol = protocol;
    }

    public List<Property> getProps()
    {
        if (props == null)
            props = new ArrayList<>();
        return props;
    }

    public void setProps(List<Property> props)
    {
        this.props = props;
    }

    public String getPropValueByKey(String key)
    {
        if (props == null)
            return null;

        for (Property p : props)
        {
            if (p.getKey().equals(key))
                return p.getValue();
        }
        return null;
    }

    @Override
    public String toString()
    {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SIMPLE_STYLE);
    }
}
