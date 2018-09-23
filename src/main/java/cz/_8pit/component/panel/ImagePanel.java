package cz._8pit.component.panel;

import java.awt.*;

import javax.swing.*;

import cz._8pit.util.ResourceLoader;


/**
 * Panel for image.
 *
 * @author Darius Kryszczuk
 */
public class ImagePanel extends JPanel {

    private final transient Image image;

    public ImagePanel(final Image image) {
        this.image = image;
        this.setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        if (image != null) {
            graphics.drawImage(image, 0, 0, this);
        }
    }
}
