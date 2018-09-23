package cz._8pit.component.sprite;

import java.util.concurrent.TimeUnit;

import javax.swing.*;

import cz._8pit.projector.Projector;
import cz._8pit.projector.ProjectorProvider;
import cz._8pit.resource.Sprites;
import io.reactivex.Observable;


/**
 * Representation of naval mine.
 *
 * @author Darius Kryszczuk
 */
public class Mine extends JLabel {

    private static final Projector PROJECTOR = ProjectorProvider.get();

    public Mine() {
        this.setOpaque(false);
        this.setIcon(Sprites.NAVAL_MINE);
        this.setBounds(-300, -300, 48, 48);
    }

    /**
     * Diving (falling) of naval mine in [{@code x}, {@code y}] for {@code delayMs} of delay between [{@code x}, {@code y}] and
     * [{@code x} - 1, {@code y}].
     *
     * @param x the x coordinate
     * @param y the y coordinate
     * @param delayMs the delay in milliseconds
     * @return stream of falling {@code Mine}
     */
    public Observable<Mine> dive(int x, int y, long delayMs) {
        return Observable
                .range(0, Projector.HEIGHT)
                .concatMap(i -> Observable.just(i).delay(delayMs, TimeUnit.MILLISECONDS))
                .doOnNext(next -> setLocation(getX(), getY() + 1))
                .doOnNext(i -> PROJECTOR.sync())
                .concatMap(i -> Observable.just(this))
                .doOnSubscribe(disposable -> setLocation(x, y))
                .doOnSubscribe(disposable -> PROJECTOR.project(this))
                .doOnComplete(() -> PROJECTOR.remove(this));
    }

    /**
     * Removes {@code this} from the projecting field and renders {@link Explosion#explode(int, int, long)} instead.
     *
     * @return stream of exploding {@code Explosion}
     */
    public Observable<Explosion> explode() {
        return new Explosion()
                .explode(this.getX(), this.getY(), 2_000)
                .doOnSubscribe(disposable -> PROJECTOR.remove(this));
    }
}
