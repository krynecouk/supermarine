package cz._8pit.key;

import java.awt.event.ActionEvent;
import java.util.function.Consumer;

import javax.swing.*;


/**
 * Binder for binding {@link JComponent} with {@link KeyAction} and {@link ActionEvent}.
 *
 * @author Darius Kryszczuk
 */
public class KeyBinder {

    private KeyBinder() {
        throw new IllegalStateException("KeyBinder should not be instantiated.");
    }

    /**
     * Binds {@code component} with {@code keyAction}. For every one {@code keyAction} one event is handled by {@code eventHandler}.
     *
     * @param component the component
     * @param keyAction the key action
     * @param eventHandler the event handler
     */
    public static void bind(JComponent component, KeyAction keyAction, Consumer<ActionEvent> eventHandler) {
        final InputMap inputMap = component.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        inputMap.put(keyAction.getKeyStroke(), keyAction.getId());

        final ActionMap actionMap = component.getActionMap();
        actionMap.put(keyAction.getId(), new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                eventHandler.accept(actionEvent);
            }
        });
    }
}
