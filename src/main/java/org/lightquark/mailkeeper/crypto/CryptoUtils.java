package org.lightquark.mailkeeper.crypto;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Properties;

/**
 * Created by Light Quark.
 */
public class CryptoUtils
{
    private static final Logger LOG = LogManager.getLogger();

    private static final String DEFAULT_ALG = "AES";
    private static final String DEFAULT_CIPHER = "AES/CBC/PKCS5PADDING";
    private static final String UTF8 = "UTF-8";

    private Cipher cipher;

    public CryptoUtils() throws NoSuchAlgorithmException, NoSuchPaddingException
    {
        this.cipher = Cipher.getInstance(DEFAULT_CIPHER);
    }

    public String encrypt(String plainText, String key, String initVector) throws Exception
    {
        try
        {
            IvParameterSpec iv = new IvParameterSpec(initVector.getBytes(UTF8));
            SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(UTF8), DEFAULT_ALG);
            //Cipher cipher = Cipher.getInstance(DEFAULT_CIPHER);
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, iv);
            byte[] encryptedBytes = cipher.doFinal(plainText.getBytes(UTF8));
            return Base64.getEncoder().encodeToString(encryptedBytes);
        }
        catch (Exception e)
        {
            LOG.error(e.getMessage() + "\n" + ExceptionUtils.getStackTrace(e));
            throw new Exception(e);
        }
    }

    public String decrypt(String encryptedText, String key, String initVector) throws Exception
    {
        try
        {
            IvParameterSpec iv = new IvParameterSpec(initVector.getBytes(UTF8));
            SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(UTF8), DEFAULT_ALG);
            //Cipher cipher = Cipher.getInstance(DEFAULT_CIPHER);
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, iv);
            byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedText));
            return new String(decryptedBytes);
        }
        catch (Exception e)
        {
            LOG.error(e.getMessage() + "\n" + ExceptionUtils.getStackTrace(e));
            throw new Exception(e);
        }
    }

    public CryptoParams readCryptoParams(String keyFilePath) throws Exception
    {
        try (FileInputStream fis = new FileInputStream(new File(keyFilePath)))
        {
            Properties props = new Properties();
            props.loadFromXML(fis);
            return new CryptoParams(props.getProperty("key"), props.getProperty("initVector"));
        }
        catch (Exception e)
        {
            LOG.error(e.getMessage() + "\n" + ExceptionUtils.getStackTrace(e));
            throw new Exception(e);
        }
    }

    private char[] readFile(String sourceFilePath) throws Exception
    {
        try
        {
            char[] buf;
            File sourceFile = new File(sourceFilePath);
            try (Reader in = new BufferedReader(new InputStreamReader(new FileInputStream(sourceFile), StandardCharsets.UTF_8)))
            {
                int size = (int) sourceFile.length();
                buf = new char[size];
                int readSize = in.read(buf);
                if (size != readSize)
                {
                    LOG.error("ERROR: Error while reading source file. File size " + size + " but actually read " + readSize + " bytes.");
                    return null;
                }
            }

            return buf;
        }
        catch (Exception e)
        {
            LOG.error(e.getMessage() + "\n" + ExceptionUtils.getStackTrace(e));
            throw new Exception(e);
        }
    }

    private void writeFile(String sourceFilePath, String value) throws Exception
    {
        try (Writer w = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(sourceFilePath)), StandardCharsets.UTF_8)))
        {
            w.write(value);
        }
        catch (Exception e)
        {
            LOG.error(e.getMessage() + "\n" + ExceptionUtils.getStackTrace(e));
            throw new Exception(e);
        }
    }

    public void encryptFile(String sourceFilePath, String destFilePath, String key, String initVector) throws Exception
    {
        try
        {
            char[] buf = readFile(sourceFilePath);
            if (buf != null)
            {
                String encrypted = encrypt(new String(buf), key, initVector);
                writeFile(destFilePath, encrypted);
            }
        }
        catch (Exception e)
        {
            LOG.error(e.getMessage() + "\n" + ExceptionUtils.getStackTrace(e));
            throw new Exception(e);
        }
    }

    public String decryptFile(String sourceFilePath, String key, String initVector) throws Exception
    {
        try
        {
            char[] buf = readFile(sourceFilePath);
            if (buf != null)
            {
                return decrypt(new String(buf), key, initVector);
            }
        }
        catch (Exception e)
        {
            LOG.error(e.getMessage() + "\n" + ExceptionUtils.getStackTrace(e));
            throw new Exception(e);
        }
        return null;
    }
}
