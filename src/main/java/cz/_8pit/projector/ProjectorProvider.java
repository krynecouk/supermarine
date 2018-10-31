package cz._8pit.projector;


/**
 * Lazy-loaded thread safe singleton provider of {@link Projector}.
 *
 * @author Darius Kryszczuk
 */
public class ProjectorProvider {

    private ProjectorProvider(){}

    private static class LazyHolder {
        private static final JFrameProjector PROJECTOR = new JFrameProjector();
    }

    /**
     * Returns instance of {@link Projector}.
     *
     * @return the instance
     */
    public static Projector get() {
        return LazyHolder.PROJECTOR;
    }
}