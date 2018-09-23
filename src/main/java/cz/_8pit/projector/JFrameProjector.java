package cz._8pit.projector;

import static java.util.Arrays.asList;
import static javax.swing.JFrame.EXIT_ON_CLOSE;

import java.awt.*;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.swing.*;

import cz._8pit.util.CollisionUtil;


/**
 * {@code JFrame} implementation of {@code Projector}.
 *
 * @author Darius Kryszczuk
 */
public class JFrameProjector implements Projector {

    private final JFrame frame;

    private final Container contentPane;

    private JPanel mainPanel = new JPanel();

    JFrameProjector() {
        frame = new JFrame();
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        frame.setSize(new Dimension(WIDTH, HEIGHT));
        contentPane = frame.getContentPane();
        mainPanel.setLayout(null);
        this.init();
    }

    @Override
    public void removeAll() {
        mainPanel.removeAll();
        mainPanel.repaint();

        contentPane.removeAll();
        contentPane.repaint();
        frame.repaint();
    }

    @Override
    public <T extends Component> void removeAll(Class<T> type) {
        Arrays.stream(mainPanel.getComponents())
                .filter(component -> component.getClass().equals(type))
                .forEach(component -> {
                    component.setBounds(0, 0, 0, 0);
                    mainPanel.remove(component);
                });
        mainPanel.repaint();
        frame.repaint();
    }

    @Override
    public void remove(Component... components) {
        asList(components).forEach(component -> {
            component.setBounds(0, 0, 0, 0);
            mainPanel.remove(component);
        });
        mainPanel.repaint();
        frame.repaint();
    }

    @Override
    public void setBackground(JPanel panel) {
        final Component[] components = mainPanel.getComponents();
        this.removeAll();
        mainPanel = panel;
        mainPanel.setLayout(null);
        this.init();
        this.project(components);
    }

    @Override
    public void project(Component... component) {
        asList(component).forEach(mainPanel::add);
        frame.repaint();
    }

    @Override
    public <T extends Component> boolean isCollision(Component component, Class<T> with) {
        for (Component c : mainPanel.getComponents()) {
            if (CollisionUtil.isCollision(component, c) && c.getClass().equals(with)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public <T extends Component> Set<T> getCollisions(Component component, Class<T> with) {
        final Set<T> result = new HashSet<>();

        for (Component c : mainPanel.getComponents()) {
            if (CollisionUtil.isCollision(component, c) && c.getClass().equals(with)) {
                result.add((T) c);
            }
        }

        return result;
    }

    @Override
    public void sync() {
        Toolkit.getDefaultToolkit().sync();
    }

    private void init() {
        contentPane.add(mainPanel);
        frame.pack();
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }
}
