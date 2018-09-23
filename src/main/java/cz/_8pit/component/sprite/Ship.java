package cz._8pit.component.sprite;

import static cz._8pit.util.RandomUtil.randomInt;

import java.awt.*;
import java.util.concurrent.TimeUnit;

import javax.swing.*;

import cz._8pit.projector.Projector;
import cz._8pit.projector.ProjectorProvider;
import io.reactivex.Observable;


/**
 * Represents enemy ship sailing from one side.
 *
 * @author Darius Kryszczuk
 */
public class Ship extends JLabel {

    private static final Projector PROJECTOR = ProjectorProvider.get();

    /**
     * Direction of ship sailing.
     */
    public enum Direction {
        LEFT, RIGHT
    }

    private final Dimension size;

    private final Direction direction;

    public Ship(ImageIcon image, Dimension size, Direction direction) {
        this.size = size;
        this.direction = direction;
        this.setIcon(image);
        this.setOpaque(false);
    }

    /**
     * Sailing of ship in specific {@code direction} and delay in milliseconds between [{@code x}, {@code y}] and
     * [{@code x} -/+ 1, {@code y}].
     *
     * @param delayMs the delay in milliseconds
     * @return stream of sailing {@code Ship}
     */
    public Observable<Ship> sail(int delayMs) {
        int x = direction == Direction.LEFT ? Projector.WIDTH : 0;
        return Observable
                .range(0, Projector.WIDTH)
                .concatMap(i -> Observable.just(i).delay(delayMs, TimeUnit.MILLISECONDS))
                .doOnNext(next -> {
                            if (direction == Direction.LEFT) {
                                setLocation(getX() - 1, getY());
                            } else {
                                setLocation(getX() + 1, getY());
                            }
                        }
                )
                .doOnNext(next -> PROJECTOR.sync())
                .map(i -> this)
                .takeWhile(enemy -> {
                            if (direction == Direction.RIGHT) {
                                return enemy.getX() <= Projector.WIDTH;
                            } else {
                                return enemy.getX() >= 0;
                            }
                        })
                .doOnSubscribe(disposable -> setBounds(x, randomInt(170, 240), size.width, size.height))
                .repeat();
    }

    /**
     * Drops mines in location of {@code Ship} in {@code intervalMs} and with {@code delayMs} between [{@code mine.x}, {@code mine.y}] and
     * [{@code mine.x}, {@code mine.y} + 1].
     *
     * @param intervalMs the interval
     * @param delayMs the delay
     * @return the stream of falling {@link Mine}
     */
    public Observable<Mine> dropMines(int intervalMs, int delayMs) {
        return Observable
                .interval(intervalMs, TimeUnit.MILLISECONDS)
                .map(next -> new Mine())
                .doOnNext(PROJECTOR::project)
                .flatMap(mine -> mine.dive(this.getX(), this.getY(), delayMs));
    }

    /**
     * If ship goes {@link Direction#RIGHT} then move ship to the end of {@link Projector#WIDTH}.
     * Otherwise move ship to the start of the {@link Projector#WIDTH}.
     */
    public void reset() {
        if (direction == Direction.RIGHT) {
            this.setLocation(Projector.WIDTH, this.getY());
        } else {
            this.setLocation((int) (0 - size.getWidth()), this.getY());
        }
    }
}
