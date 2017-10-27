package org.lightquark.mailkeeper;

/**
 * Created by Light Quark.
 */
public class AppCommand
{
    public static final String CHECK = "check";
    public static final String ENCRYPT = "encrypt";

    public static boolean checkCommand(String value)
    {
        return value.equals(CHECK) || value.equals(ENCRYPT);
    }
}
