package cz._8pit.component.sprite;

import java.util.concurrent.TimeUnit;

import javax.swing.*;

import cz._8pit.projector.Projector;
import cz._8pit.projector.ProjectorProvider;
import cz._8pit.resource.Sounds;
import cz._8pit.resource.Sprites;
import io.reactivex.Observable;


/**
 * Represents flyable rocket.
 *
 * @author Darius Kryszczuk
 */
public class Rocket extends JLabel {

    private static final Projector PROJECTOR = ProjectorProvider.get();

    public Rocket() {
        this.setOpaque(false);
        this.setIcon(Sprites.ROCKET);
        this.setBounds(-300, -300, 25, 48);
    }

    /**
     * Launches rocket in in [{@code x}, {@code y}] for {@code delayMs} of delay between [{@code x}, {@code y}] and
     * [{@code x} - 1, {@code y}].
     *
     * @param x the x coordinate
     * @param y the y coordinate
     * @param delayMs the delay in milliseconds
     * @return stream of flying {@code Rocket}
     */
    public Observable<Rocket> launch(int x, int y, long delayMs) {
        return Observable
                .range(0, Projector.HEIGHT)
                .concatMap(i -> Observable.just(i).delay(delayMs, TimeUnit.MILLISECONDS))
                .doOnNext(i -> setLocation(x, getY() - 1))
                .map(i -> this)
                .doOnSubscribe(disposable -> setLocation(x, y))
                .doOnSubscribe(disposable -> PROJECTOR.project(this))
                .doOnSubscribe(disposable -> Sounds.ROCKET_FLYING.play())
                .doOnComplete(() -> PROJECTOR.remove(this));
    }
}
