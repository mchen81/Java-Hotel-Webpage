package common;

import java.security.SecureRandom;
import java.util.Random;

/**
 * Generate Random Number, for review id and password salt
 */
public class RandomNumberUtil {

    private static SecureRandom random = new SecureRandom();

    private static String randomCharCollection = "1234567890qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM";

    public static String generateRandomString(int length) {
        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < length; i++) {
            stringBuilder.append(randomCharCollection.charAt(random.nextInt(randomCharCollection.length())));
        }
        return stringBuilder.toString();
    }


}
