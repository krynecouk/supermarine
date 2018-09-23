package cz._8pit.resource;

import javax.swing.*;

import cz._8pit.component.panel.ImagePanel;
import cz._8pit.util.ResourceLoader;


/**
 * Static access to the sprite resources.
 *
 * @author Darius Kryszczuk
 */
public class Sprites {

    private static final String BASE_PATH = "/image";

    private Sprites() {
        throw new IllegalStateException("Sprites should not be instantiated.");
    }

    public static final ImageIcon BUTTON = ResourceLoader.fromPath(BASE_PATH, "button_up_190x190.png").asImageIcon();

    public static final ImageIcon BUTTON_PRESSED = ResourceLoader.fromPath(BASE_PATH, "button_down_190x190.png").asImageIcon();

    public static final ImageIcon SUBMARINE = ResourceLoader.fromPath(BASE_PATH, "submarine_128x40.png").asImageIcon();

    public static final ImageIcon ROCKET = ResourceLoader.fromPath(BASE_PATH, "rocket_blue_25x48.gif").asImageIcon();

    public static final ImageIcon NAVAL_MINE = ResourceLoader.fromPath(BASE_PATH, "naval_mine_48x48.png").asImageIcon();

    public static final ImageIcon EXPLOSION = ResourceLoader.fromPath(BASE_PATH, "explosion_48x48.gif").asImageIcon();

    public static final ImageIcon YELLOW_SHIP = ResourceLoader.fromPath(BASE_PATH, "yellow_enemy_ship_128x48.png").asImageIcon();

    public static final ImageIcon GREEN_SHIP_REVERSED = ResourceLoader.fromPath(BASE_PATH, "green_enemy_ship_reversed_98x37.png").asImageIcon();

    public static final ImageIcon RED_SHIP_REVERSED = ResourceLoader.fromPath(BASE_PATH, "red_enemy_ship_reversed_64x24.png").asImageIcon();

    public static final ImagePanel SEA = new ImagePanel(ResourceLoader.fromPath(BASE_PATH, "sea_960x640.png").asImage());
}
