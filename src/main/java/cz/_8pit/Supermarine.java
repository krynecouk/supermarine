package cz._8pit;

import java.awt.*;

import javax.swing.*;

import cz._8pit.component.bar.LoadingBar;
import cz._8pit.component.button.RedButton;
import cz._8pit.component.panel.CounterPanel;
import cz._8pit.component.sprite.Explosion;
import cz._8pit.component.sprite.Mine;
import cz._8pit.component.sprite.Rocket;
import cz._8pit.component.sprite.Ship;
import cz._8pit.component.sprite.Ship.Direction;
import cz._8pit.component.sprite.Submarine;
import cz._8pit.projector.Projector;
import cz._8pit.projector.ProjectorProvider;
import cz._8pit.resource.Sounds;
import cz._8pit.resource.Sprites;
import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


/**
 * Supermarine is a game where the primary goal is to gain as many points as possible by shooting enemy ships and falling naval mines. If player
 * (submarine) gets in the contact with the naval mine then the player looses and the game is restarted.
 *
 * @see <a href="https://github.com/dariuskryszczuk/supermarine">Supermarine repository</a>
 * @author Darius Kryszczuk
 */
public class Supermarine {

    private static Projector projector;

    private static RedButton button;

    private static Submarine submarine;

    private static Ship redShip;

    private static Ship greenShip;

    private static Ship yellowShip;

    private static LoadingBar loadingBar;

    private static CounterPanel counter;

    public static void main(String[] args) {
        projector = ProjectorProvider.get();
        button = new RedButton();
        submarine = new Submarine();
        redShip = new Ship(Sprites.RED_SHIP_REVERSED, new Dimension(64, 24), Direction.LEFT);
        greenShip = new Ship(Sprites.GREEN_SHIP_REVERSED, new Dimension(98, 37), Direction.LEFT);
        yellowShip = new Ship(Sprites.YELLOW_SHIP, new Dimension(128, 48), Direction.RIGHT);
        loadingBar = new LoadingBar();
        counter = new CounterPanel();
        Sounds.ARCADE_MUSIC.loop();
        SwingUtilities.invokeLater(new Supermarine()::load);
    }

    private void load() {
        // init projecting field
        projector.removeAll();
        projector.setBackground(Sprites.SEA);
        projector.project(button, submarine, redShip, greenShip, yellowShip, loadingBar, counter);

        // make submarine move
        Disposable movingPlayer = submarine
                .move()
                .subscribeOn(Schedulers.io())
                .subscribe();

        // make ships move
        Disposable movingShips = Observable
                .merge(
                        redShip.sail(5),
                        greenShip.sail(7),
                        yellowShip.sail(8))
                .subscribeOn(Schedulers.io())
                .subscribe();

        // create naval mine stream
        Observable<Mine> mineStream = Observable
                .merge(
                        redShip.dropMines(900, 5),
                        greenShip.dropMines(800, 7),
                        yellowShip.dropMines(700, 20)
                ).cast(Mine.class)
                .share();

        // create rocket stream
        Observable<Rocket> rocketStream = button
                .getClickStream()
                .filter(click -> loadingBar.isLoaded())
                .doOnNext(click -> loadingBar.load(650).subscribeOn(Schedulers.io()).subscribe())
                .map(click -> new Rocket())
                .flatMap(rocket -> rocket.launch(submarine.getX() + 50, submarine.getY(), 5))
                .share();

        // make naval mine explodes when it collides with a rocket
        Disposable mineRocketColliding = mineStream
                .filter(mine -> projector.isCollision(mine, Rocket.class))
                .doOnNext(mine -> counter.add(10))
                .doOnNext(mine -> projector.getCollisions(mine, Rocket.class).forEach(projector::remove))
                .flatMap(Mine::explode)
                .subscribeOn(Schedulers.io())
                .subscribe();

        // make enemy explodes when it collides with a rocket
        Disposable rocketEnemyColliding = rocketStream
                .filter(rocket -> projector.isCollision(rocket, Ship.class))
                .doOnNext(rocket -> counter.add(100))
                .doOnNext(rocket -> projector.getCollisions(rocket, Ship.class).forEach(ship -> {
                    new Explosion().explode(ship.getX(), ship.getY(), 2_000).subscribeOn(Schedulers.io()).subscribe();
                    ship.reset();
                }))
                .doOnNext(projector::remove)
                .subscribeOn(Schedulers.io())
                .subscribe();

        // restart game when submarine collides with a naval mine
        mineStream
                .takeWhile(mine -> !movingPlayer.isDisposed())
                .filter(mine -> projector.isCollision(mine, Submarine.class))
                .doOnNext(mine -> movingPlayer.dispose())
                .flatMap(Mine::explode)
                .doOnNext(explosion -> {
                    movingShips.dispose();
                    mineRocketColliding.dispose();
                    rocketEnemyColliding.dispose();
                    counter.reset();
                })
                .doOnComplete(this::load)
                .subscribeOn(Schedulers.io())
                .subscribe();
    }
}
