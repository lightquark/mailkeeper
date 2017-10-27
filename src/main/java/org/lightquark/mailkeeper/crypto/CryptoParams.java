package org.lightquark.mailkeeper.crypto;

/**
 * Created by Light Quark.
 */
public class CryptoParams
{
    private String key;
    private String initVector;

    public CryptoParams(String key, String initVector)
    {
        this.key = key;
        this.initVector = initVector;
    }

    public String getKey()
    {
        return key;
    }

    public void setKey(String key)
    {
        this.key = key;
    }

    public String getInitVector()
    {
        return initVector;
    }

    public void setInitVector(String initVector)
    {
        this.initVector = initVector;
    }
}
