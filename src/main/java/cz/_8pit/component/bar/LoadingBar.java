package cz._8pit.component.bar;

import java.awt.*;
import java.util.concurrent.TimeUnit;

import javax.swing.*;

import cz._8pit.projector.Projector;
import cz._8pit.projector.ProjectorProvider;
import io.reactivex.Completable;
import io.reactivex.Observable;


/**
 * Representation of loading bar.
 *
 * @author Darius Kryszczuk
 */
public class LoadingBar extends JProgressBar {

    private static final Projector PROJECTOR = ProjectorProvider.get();

    public LoadingBar() {
        this.setBounds(50, 20, 255, 30);
        this.setMinimum(0);
        this.setMaximum(this.getWidth());
        this.setValue(this.getWidth());
        this.setForeground(Color.red);
    }

    /**
     * Loads the loading bar for {@code durationMs} time and emits when load is completed.
     *
     * @param durationMs the load time in milliseconds
     * @return the completed event
     */
    public Completable load(long durationMs) {
        final long delay = (durationMs / this.getWidth());

        final Observable load = Observable
                .range(0, this.getWidth() + 1)
                .concatMap(i -> Observable.just(i).delay(delay, TimeUnit.MILLISECONDS))
                .doOnNext(this::setValue)
                .doOnNext(next -> PROJECTOR.sync())
                .doOnSubscribe(disposable -> this.setValue(0));

        return Completable.fromObservable(load);
    }

    /**
     * Check if load bar is fully loaded or not.
     *
     * @return {@code true} if load bar is fully loaded
     */
    public boolean isLoaded() {
        return this.getValue() == this.getWidth();
    }
}
