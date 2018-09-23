package cz._8pit.projector;


import java.awt.*;
import java.util.Set;

import javax.swing.*;


/**
 * {@code Projector} is used for visualisation and manipulation of the {@code java.awt.Component} objects.
 *
 * @author Darius Kryszczuk
 */
public interface Projector {

    int WIDTH = 960;

    int HEIGHT = 640;

    /**
     * Removes every {@code java.awt.Component} from the projecting field.
     */
    void removeAll();

    /**
     * Removes every {@code java.awt.Component} of type {@code type} from the projecting field.
     */
    <T extends Component> void removeAll(Class<T> type);

    /**
     * Removes components from the projecting field.
     *
     * @param components the components
     */
    void remove(Component... components);

    /**
     * Sets background for the projecting field.
     *
     * @param panel background
     */
    void setBackground(JPanel panel);

    /**
     * Projects {@code components}.
     *
     * @param components the components
     */
    void project(Component... components);

    /**
     * Return {@code true} if component {@code of} is in collision with a class {@code with}.
     *
     * @param of the component
     * @param with the class
     * @param <T> type of class
     * @return true if is collision
     */
    <T extends Component> boolean isCollision(Component of, Class<T> with);

    /**
     * Returns set of components which are in collision with {@code component}.
     *
     * @param component the component
     * @param with type of components in collision
     * @param <T> type of components in collision
     * @return the set of components
     */
    <T extends Component> Set<T> getCollisions(Component component, Class<T> with);

    /**
     * Syncs rendered components with projecting field. Use this method if the animation lags.
     */
    void sync();
}
