package cz._8pit.util;

import static java.lang.String.format;

import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.*;
import java.io.InputStream;
import java.net.URL;

import javax.swing.*;

import com.google.common.io.Resources;


/**
 * Loader of resources.
 *
 * @author Darius Kryszczuk
 */
public final class ResourceLoader {

    private ResourceLoader() {
        throw new IllegalStateException("ResourceLoader should not be instantiated.");
    }

    /**
     * Representation of every file in resources.
     */
    public static final class Resource {

        private final URL resourceUrl;

        private Resource(final URL resourceUrl) {
            this.resourceUrl = resourceUrl;
        }

        /**
         * Returns resource as an {@code Image}.
         *
         * @return the image
         */
        public Image asImage() {
            try {
                return Toolkit.getDefaultToolkit().createImage(resourceUrl);
            } catch (Exception e) {
                throw new IllegalArgumentException(format("Unable to get image resource from %s", resourceUrl), e);
            }
        }

        /**
         * Returns resource as an {@code ImageIcon}.
         *
         * @return the image icon
         */
        public ImageIcon asImageIcon() {
            try {
                return new ImageIcon(resourceUrl);
            } catch (Exception e) {
                throw new IllegalArgumentException(format("Unable to get image icon resource from %s", resourceUrl), e);
            }
        }

        /**
         * Returns resource as a {@code Font}.
         *
         * @return the font
         */
        public Font asFont() {
            try (final InputStream font = openStream()) {
                return Font.createFont(Font.TRUETYPE_FONT, font);
            } catch (Exception e) {
                throw new IllegalArgumentException(format("Unable to get font resource from %s", resourceUrl), e);
            }
        }

        /**
         * Returns resource as an {@code Audio}.
         *
         * @return the audio
         */
        public AudioClip asAudio() {
            try {
                return Applet.newAudioClip(resourceUrl);
            } catch (Exception e) {
                throw new IllegalArgumentException(format("Unable to get audio resource from %s", resourceUrl), e);
            }
        }

        /**
         * Opens stream to the {@link Resource}.
         *
         * @return the opened stream
         */
        public InputStream openStream() {
            try {
                return resourceUrl.openStream();
            } catch (Exception e) {
                throw new IllegalArgumentException(format("Unable to open stream of resource from %s", resourceUrl), e);
            }
        }
    }

    /**
     * Returns {@link Resource} with name {@code resourceName} relative to classpath root {@code path}.
     *
     * @param resourceName the resource name
     * @param basePath the path
     * @return the resource
     */
    public static Resource fromPath(final String basePath, final String resourceName) {
        return relativeToClass(getPath(basePath, resourceName), ResourceLoader.class);
    }

    /**
     * Returns {@link Resource} with name {@code resourceName} relative to class {@code relativeTo}.
     *
     * @param resourceName the name
     * @param relativeTo the class
     * @return the resource
     */
    private static Resource relativeToClass(final String resourceName, final Class relativeTo) {
        final URL url = Resources.getResource(relativeTo, resourceName);
        return new Resource(url);
    }

    private static String getPath(final String basePath, final String resourceName) {
        if (!basePath.endsWith("/")) {
            return basePath + "/" + resourceName;
        }
        return basePath + resourceName;
    }
}
