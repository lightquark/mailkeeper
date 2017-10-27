package org.lightquark.mailkeeper;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.lightquark.mailkeeper.crypto.CryptoParams;
import org.lightquark.mailkeeper.crypto.CryptoUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by Light Quark.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class CryptoTests
{
    @Value("${configFilePath}")
    private String configFilePath;
    @Value("${encryptedConfigFilePath}")
    private String encryptedConfigFilePath;
    @Value("${keyFilePath}")
    private String keyFilePath;

    @Autowired
    private CryptoUtils cryptoUtils;

    @Test
    public void base()
    {
        String key = "0123456789ABCDEF"; // 128 bit key
        String initVector = "RandomInitVector"; // 16 bytes IV
        String plainText = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><config></config>";

        try
        {
            String encrypted = cryptoUtils.encrypt(plainText, key, initVector);
            String result = cryptoUtils.decrypt(encrypted, key, initVector);

            //System.out.println("plainText=[" + plainText + "] encrypted=[" + encrypted + "] result=[" + result + "] ");
            Assert.assertEquals(plainText, result);
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage() + "\n" + ExceptionUtils.getStackTrace(e));
        }
    }

    @Test
    public void cryptoParams()
    {
        try
        {
            CryptoParams cp = cryptoUtils.readCryptoParams(keyFilePath);

            Assert.assertNotNull(cp);
            Assert.assertNotNull(cp.getKey());
            Assert.assertNotNull(cp.getInitVector());
            //System.out.println("key=[" + cp.getKey() + "] initVector=[" + cp.getInitVector());
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage() + "\n" + ExceptionUtils.getStackTrace(e));
        }
    }
}
