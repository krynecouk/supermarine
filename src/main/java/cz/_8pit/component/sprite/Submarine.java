package cz._8pit.component.sprite;

import static cz._8pit.key.KeyAction.LEFT_PRESSED;
import static cz._8pit.key.KeyAction.LEFT_RELEASED;
import static cz._8pit.key.KeyAction.RIGHT_PRESSED;
import static cz._8pit.key.KeyAction.RIGHT_RELEASED;

import java.util.HashSet;
import java.util.Set;

import javax.swing.*;

import cz._8pit.key.KeyAction;
import cz._8pit.key.KeyBinder;
import cz._8pit.projector.Projector;
import cz._8pit.projector.ProjectorProvider;
import cz._8pit.resource.Sprites;
import io.reactivex.Observable;


/**
 * Represents movable submarine.
 *
 * @author Darius Kryszczuk
 */
public class Submarine extends JLabel {

    private static final Projector PROJECTOR = ProjectorProvider.get();

    private Set<KeyAction> pressedKeys = new HashSet<>();

    public Submarine() {
        this.setOpaque(false);
        this.setIcon(Sprites.SUBMARINE);
        this.setBounds(400, 580, 128, 40);
        initKeyBinding();
    }

    /**
     * Binds left, right arrow for moving of {@code Submarine} left and right, respectively.
     *
     * @return the stream of moving {@link Submarine}
     */
    public Observable<Submarine> move() {
        this.setLocation(400, 580);
        return Observable
                .generate(
                        () -> pressedKeys,
                        (key, emitter) -> {

                            Thread.sleep(5);

                            if (pressedKeys.contains(RIGHT_PRESSED) && getX() <= Projector.WIDTH - this.getWidth()) {
                                this.setLocation(getX() + 1, getY());
                            }

                            if (pressedKeys.contains(LEFT_PRESSED) && getX() >= 0) {
                                this.setLocation(getX() - 1, getY());
                            }

                            PROJECTOR.sync();
                            emitter.onNext(this);
                        });
    }

    private void initKeyBinding() {
        KeyBinder.bind(this, LEFT_PRESSED, (actionEvent -> pressedKeys.add(LEFT_PRESSED)));
        KeyBinder.bind(this, LEFT_RELEASED, (actionEvent -> pressedKeys.remove(LEFT_PRESSED)));
        KeyBinder.bind(this, RIGHT_PRESSED, (actionEvent -> pressedKeys.add(RIGHT_PRESSED)));
        KeyBinder.bind(this, RIGHT_RELEASED, (actionEvent -> pressedKeys.remove(RIGHT_PRESSED)));
    }
}
