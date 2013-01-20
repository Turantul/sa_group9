package sa12.group9.common.util;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Encrypter
{
    /**
     * Generates an MD5Hash of the input string
     * 
     * @param string the input to be encrypted
     * @return the encrypted string
     */
    public static String encryptString(String string)
    {
        MessageDigest mdEnc;
        String md5sum = "";

        try
        {
            mdEnc = MessageDigest.getInstance("MD5");
            mdEnc.update(string.getBytes(), 0, string.length());
            md5sum = new BigInteger(1, mdEnc.digest()).toString(16); // Encrypted

        }
        catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }

        return md5sum;
    }
}
