package cz._8pit.resource;

import java.awt.*;

import cz._8pit.util.ResourceLoader;


/**
 * Static access to the font resources.
 *
 * @author Darius Kryszczuk
 */
public class Fonts {

    private static final String BASE_PATH = "/font";

    private Fonts() {
        throw new IllegalStateException("Fonts should not be instantiated.");
    }

    public static final Font PIXEL_FONT = ResourceLoader.fromPath(BASE_PATH, "PressStart2P.ttf").asFont();
}
