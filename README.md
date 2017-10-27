# Welcome to Mail Keeper

Mail Keeper is a robot that automatically checks mailboxes and sends emails to each other.
It uses encrypted configuration XML-file in which is described mailboxes settings.

## Configuration XML-file

Path to configuration XML-file is in 'application.properties', parameter name is 'configFilePath'.
Path to encrypted configuration XML-file is in 'application.properties', parameter name is 'encryptedConfigFilePath'.
Path to XML-file that is describes crypto parameters is in 'application.properties', parameter name is 'keyFilePath'.
All these files should be stored in the safe place protected by the operating system.

Examples of these configuration files are stored in '.config_sample' directory.

An internal structure of configuration XML-file is simple. There are two entities: 'mailbox' and 'provider'. Mailbox describes parameters specific to the mailbox. Provider describes parameters that required to connect to the mail provider.

## How to use

The application runs from the command line and accepts two arguments.
The first argument is a command that describes what application should do. Allowed values are 'check' and 'encrypt'.
The second argument is log level. Allowed values are 'ERROR', 'WARN', 'INFO', 'DEBUG', 'ALL'.

At example, to run the application with maximal log level, you should execute the following command:
java -jar mailkeeper.jar check ALL

If you want to run the application that encrypts configuration XML file with standard log level, you should execute the following command:
java -jar mailkeeper.jar encrypt WARN
