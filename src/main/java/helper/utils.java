package helper;

import org.apache.commons.lang3.RandomStringUtils;

public class utils {

    /**
     * Generate random numeric string with given length
     * Can be used for amount generation, so usage of '0' is restricted to avoid leading 0
     * <p/>
     *
     * @param length Length of generated string
     * @return Random string
     */
    public static String getRandomNumeric(String length) {
        return RandomStringUtils.random(Integer.parseInt(length.trim()), "123456789");
    }
}
