package org.lightquark.mailkeeper;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lightquark.mailkeeper.config.MailConfig;
import org.lightquark.mailkeeper.crypto.CryptoParams;
import org.lightquark.mailkeeper.crypto.CryptoUtils;
import org.lightquark.mailkeeper.util.ConfigUtils;
import org.lightquark.mailkeeper.util.LoggingUtils;
import org.lightquark.mailkeeper.mail.MailController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.Banner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.crypto.NoSuchPaddingException;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import java.io.StringReader;
import java.security.NoSuchAlgorithmException;

@Configuration
@SpringBootApplication
public class MailkeeperApplication implements CommandLineRunner
{
    private static final Logger LOG = LogManager.getLogger();

    @Value("${configFilePath}")
    private String configFilePath;
    @Value("${encryptedConfigFilePath}")
    private String encryptedConfigFilePath;
    @Value("${keyFilePath}")
    private String keyFilePath;

    @Autowired
    private ApplicationContext context;
    @Autowired
    private CryptoUtils cryptoUtils;
    @Autowired
    private ConfigUtils configUtils;

    public static void main(String[] args)
    {
        //SpringApplication.run(MailkeeperApplication.class, args);
        SpringApplication app = new SpringApplication(MailkeeperApplication.class);
        app.setAdditionalProfiles();
        app.setBannerMode(Banner.Mode.OFF);
        app.run(args);
    }

    @Override
    public void run(String... args) throws Exception
    {
        LOG.info("START");
        if (args == null || args.length != 2)
        {
            printHelp("Invalid args");
            return;
        }

        String command = args[0];
        if (!AppCommand.checkCommand(command))
        {
            printHelp("Invalid command: " + command);
            return;
        }

        String logLevel = args[1];
        LoggingUtils.setLogLevel(logLevel);

        if (command.equals(AppCommand.CHECK))
        {
            LOG.info("Start decryption ...");
            CryptoParams cp = cryptoUtils.readCryptoParams(configUtils.getFullFileName(keyFilePath));
            if (cp == null)
            {
                LOG.error("ERROR: Error loading crypto params.");
                return;
            }
            String encrypted = cryptoUtils.decryptFile(configUtils.getFullFileName(encryptedConfigFilePath), cp.getKey(), cp.getInitVector());
            LOG.info("Decryption completed.");

            LOG.info("Loading config ...");
            MailConfig cfg = loadConfig(encrypted);
            if (cfg == null)
            {
                LOG.error("ERROR: Error loading XML config.");
                return;
            }

            LOG.info("Start mailing ...");
            MailController mailController = new MailController(cfg);
            mailController.checkAll();
            LOG.info("Mailing complete.");
        }
        else if (command.equals(AppCommand.ENCRYPT))
        {
            LOG.info("Start encryption ...");
            CryptoParams cp = cryptoUtils.readCryptoParams(configUtils.getFullFileName(keyFilePath));
            if (cp == null)
            {
                LOG.error("ERROR: Error loading crypto params.");
                return;
            }
            cryptoUtils.encryptFile(configUtils.getFullFileName(configFilePath), configUtils.getFullFileName(encryptedConfigFilePath), cp.getKey(), cp.getInitVector());
            LOG.info("Encryption completed.");
        }

        LOG.info("FINISH");
    }

    private MailConfig loadConfig(String config)
    {
        try
        {
            MailConfig cfg = (MailConfig) JAXBContext.newInstance(MailConfig.class).createUnmarshaller().unmarshal(new StringReader(config));
            cfg.prepare();
            return cfg;
        }
        catch (JAXBException e)
        {
            LOG.error(e.getMessage() + "\n" + ExceptionUtils.getStackTrace(e));
        }
        return null;
    }

    private static void printHelp(String errorMessage)
    {
        LOG.fatal(errorMessage);
        LOG.fatal("USAGE: java -jar mailkeeper.jar <command> <log-level>");
    }

    @Bean
    public CryptoUtils getCryptoUtils() throws NoSuchAlgorithmException, NoSuchPaddingException
    {
        return new CryptoUtils();
    }

    @Bean
    public ConfigUtils getConfigUtils()
    {
        return new ConfigUtils();
    }
}
