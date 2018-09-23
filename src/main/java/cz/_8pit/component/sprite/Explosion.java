package cz._8pit.component.sprite;


import java.util.concurrent.TimeUnit;

import javax.swing.*;

import cz._8pit.projector.Projector;
import cz._8pit.projector.ProjectorProvider;
import cz._8pit.resource.Sounds;
import cz._8pit.resource.Sprites;
import io.reactivex.Observable;


/**
 * Representation of explosion.
 *
 * @author Darius Kryszczuk
 */
public class Explosion extends JLabel {

    private static final Projector PROJECTOR = ProjectorProvider.get();

    public Explosion() {
        this.setOpaque(false);
        this.setIcon(Sprites.EXPLOSION);
    }

    /**
     * Explodes in [{@code x}, {@code y}] for duration of {@code durationMs} milliseconds.
     *
     * @param x the x coordinate
     * @param y the y coordinate
     * @param durationMs the duration in milliseconds
     * @return stream of exploding {@code Explosion}s
     */
    public Observable<Explosion> explode(int x, int y, long durationMs) {
        return Observable
                .just(this)
                .doOnNext(PROJECTOR::project)
                .delay(durationMs, TimeUnit.MILLISECONDS)
                .doOnNext(PROJECTOR::remove)
                .doOnSubscribe(disposable -> setBounds(x, y, 53, 103))
                .doOnSubscribe(disposable -> Sounds.EXPLOSION.play());
    }
}
