package cz._8pit.key;


import static cz._8pit.key.KeyType.PRESSED;
import static cz._8pit.key.KeyType.RELEASED;

import javax.swing.*;


/**
 * Abstractions of {@link KeyStroke} which is very low level. To add new key action add field with id, name and type of key stroke.
 *
 * @author Darius Kryszczuk
 */
public enum KeyAction {

    LEFT_PRESSED("left", "left", PRESSED),
    LEFT_RELEASED("left_re", "left", RELEASED),
    RIGHT_PRESSED("right", "right", PRESSED),
    RIGHT_RELEASED("right_re", "right", RELEASED);

    private String id;

    private String name;

    private KeyType keyType;

    /**
     * All arg constructor.
     *
     * @param id id of key action
     * @param name name of key action
     * @param keyType type of key action
     */
    KeyAction(String id, String name, KeyType keyType) {
        this.id = id;
        this.name = name;
        this.keyType = keyType;
    }

    /**
     * Returns {@link KeyStroke} representation of {@link KeyAction}.
     *
     * @return key stroke
     */
    public KeyStroke getKeyStroke() {
        final KeyStroke keyStroke = KeyStroke.getKeyStroke(getName().toUpperCase());
        if (getKeyType() == KeyType.PRESSED) {
            return KeyStroke.getKeyStroke(keyStroke.getKeyCode(), 0, false);
        } else {
            return KeyStroke.getKeyStroke(keyStroke.getKeyCode(), 0, true);
        }
    }

    /**
     * Returns name of key action.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Returns type of key action.
     *
     * @return the type
     */
    public KeyType getKeyType() {
        return keyType;
    }

    /**
     * Returns id of key action.
     *
     * @return the id
     */
    public String getId() {
        return id;
    }
}