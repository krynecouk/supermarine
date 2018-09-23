package cz._8pit.projector;


/**
 * Singleton and thread safe provider of {@link Projector}.
 *
 * @author Darius Kryszczuk
 */
public class ProjectorProvider {

    private static volatile Projector projector = new JFrameProjector();

    private ProjectorProvider(){}

    /**
     * Returns instance of {@link Projector}.
     *
     * @return the instance
     */
    public static Projector get() {
        return projector;
    }
}