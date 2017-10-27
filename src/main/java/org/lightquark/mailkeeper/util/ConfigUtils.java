package org.lightquark.mailkeeper.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;

/**
 * Created by Light Quark.
 */
public class ConfigUtils
{
    private static final Logger LOG = LogManager.getLogger();

    private String userDir;

    public ConfigUtils()
    {
        userDir = System.getProperty("user.dir") + File.separator;
        LOG.info("userDir=" + userDir);
    }

    public String getFullFileName(String fileName)
    {
        return (userDir + fileName).replace('/', File.separatorChar);
    }
}
