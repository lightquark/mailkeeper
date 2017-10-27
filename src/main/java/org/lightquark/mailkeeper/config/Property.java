package org.lightquark.mailkeeper.config;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.xml.bind.annotation.*;

/**
 * Created by Light Quark.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "")
public class Property
{
    @XmlAttribute
    protected String key;
    @XmlValue
    protected String value;

    public String getKey()
    {
        return key;
    }

    public void setKey(String key)
    {
        this.key = key;
    }

    public String getValue()
    {
        return value;
    }

    public void setValue(String value)
    {
        this.value = value;
    }

    @Override
    public String toString()
    {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SIMPLE_STYLE);
    }
}
