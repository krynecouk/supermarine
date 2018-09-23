package cz._8pit.component.panel;


import java.awt.*;
import java.util.concurrent.atomic.AtomicInteger;

import javax.swing.*;

import cz._8pit.projector.Projector;
import cz._8pit.resource.Fonts;


/**
 * Panel for integer counter.
 *
 * @author Darius Kryszczuk
 */
public class CounterPanel extends JLabel {

    private final AtomicInteger counter;

    public CounterPanel() {
        this.counter = new AtomicInteger(0);
        this.setBounds(Projector.WIDTH - 150, 25, 400, 32);
        this.setText(String.valueOf(counter.get()));
        this.setForeground(Color.black);
        this.setFont(Fonts.PIXEL_FONT.deriveFont(32f));
    }

    /**
     * Adds {@code toAdd} to the {@code counter}.
     *
     * @param toAdd to add
     */
    public void add(int toAdd) {
        counter.addAndGet(toAdd);
        this.setText(String.valueOf(counter.get()));
    }

    /**
     * Resets the {@code counter} to 0.
     */
    public void reset() {
        counter.set(0);
        this.setText(String.valueOf(counter.get()));
    }
}
