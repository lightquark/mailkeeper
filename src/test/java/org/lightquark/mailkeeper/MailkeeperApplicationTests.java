package org.lightquark.mailkeeper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.lightquark.mailkeeper.crypto.CryptoUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MailkeeperApplicationTests
{
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

    @Test
    public void contextLoads()
    {
        printDebugInfo();
        System.out.println("Test MailkeeperApplication ... OK");
    }

    private void printDebugInfo()
    {
        System.out.println("configFilePath=" + configFilePath);
        System.out.println("encryptedConfigFilePath=" + encryptedConfigFilePath);
        System.out.println("keyFilePath=" + keyFilePath);
        System.out.println("cryptoUtils=" + cryptoUtils);
        System.out.println("context=" + context);

        System.out.println("List of beans provided by Spring Boot:");
        String[] beanNames = context.getBeanDefinitionNames();
        Arrays.sort(beanNames);
        for (String beanName : beanNames)
            System.out.println("\t" + beanName);
        System.out.println("List of beans provided by Spring Boot finished.");
    }
}
