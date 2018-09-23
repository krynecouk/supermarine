package cz._8pit.util;

import java.util.Random;


/**
 * Util class for generating random numbers.
 *
 * @author Darius Kryszczuk
 */
public class RandomUtil {

    private RandomUtil() {
        throw new IllegalStateException("RandomUtil should not be instantiated.");
    }

    /**
     * Returns random integer in range {@code min} - {@code max}.
     *
     * @param min the min
     * @param max the max
     * @return the random integer
     */
    public static int randomInt(int min, int max) {
        return new Random().nextInt(max + 1 - min) + min;
    }

}
