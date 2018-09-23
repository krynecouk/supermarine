package cz._8pit.resource;

import java.applet.AudioClip;

import cz._8pit.util.ResourceLoader;


/**
 * Static access to the audio resources.
 *
 * @author Darius Kryszczuk
 */
public class Sounds {

    private static final String BASE_PATH = "/sound";

    private Sounds() {
        throw new IllegalStateException("Sounds should not be instantiated.");
    }

    public static final AudioClip ROCKET_FLYING = ResourceLoader.fromPath(BASE_PATH, "rocket.wav").asAudio();

    public static final AudioClip EXPLOSION = ResourceLoader.fromPath(BASE_PATH, "explosion.wav").asAudio();

    public static final AudioClip ARCADE_MUSIC = ResourceLoader.fromPath(BASE_PATH, "arcade_music.wav").asAudio();
}
