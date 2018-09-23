package cz._8pit.util;

import java.awt.*;


/**
 * Util class for collisions.
 *
 * @author Darius Kryszczuk
 */
public class CollisionUtil {

    private CollisionUtil() {
        throw new IllegalStateException("CollisionUtil should not be instantiated.");
    }

    /**
     * Returns {@code true} if {@code what} is in collision with {@code with}.
     *
     * @param what the component
     * @param with the component
     * @return true if is collision
     */
    public static boolean isCollision(final Component what, final Component with) {
        if (what == null || with == null) {
            return false;
        }
        return what.getBounds().intersects(with.getBounds());
    }
}
