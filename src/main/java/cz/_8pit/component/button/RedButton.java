package cz._8pit.component.button;

import java.awt.*;
import java.awt.event.ActionEvent;

import javax.swing.*;

import cz._8pit.projector.Projector;
import cz._8pit.resource.Sprites;
import io.reactivex.Observable;


/**
 * Much bigger and more powerful nuclear button.
 *
 * @author Darius Kryszczuk
 */
public class RedButton extends JButton {

    public RedButton() {
        this.setOpaque(false);
        this.setBackground(Color.white);
        this.setIcon(Sprites.BUTTON);
        this.setPressedIcon(Sprites.BUTTON_PRESSED);
        this.setBounds(Projector.WIDTH - 190, Projector.HEIGHT - 190, 190, 190);
        this.setBorder(null);
        this.setContentAreaFilled(false);
    }

    /**
     * Returns stream that emits {@link ActionEvent} per click.
     *
     * @return the click stream
     */
    public Observable<ActionEvent> getClickStream() {
        return Observable.create(emitter -> addActionListener(emitter::onNext));
    }
}
