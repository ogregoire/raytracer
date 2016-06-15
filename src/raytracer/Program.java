package raytracer;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Program {

    private boolean alive;

    public static void main(String[] args) {
        new Program().run();
    }

    private void run() {
        Controller controller = new Controller(new Viewer(480, 272), new Scene());
        Thread tick = new Thread(() -> {
            alive = true;
            while (alive) {
                controller.step();
            }
        });

        java.awt.EventQueue.invokeLater(() -> {
            JFrame frame = new JFrame();
            frame.setResizable(false);
            frame.setTitle("Ray Tracer");
            frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            frame.getContentPane().add(controller.getView());
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
            frame.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    super.windowClosing(e);
                    alive = false;
                }
            });
            tick.start();
        });
    }
}
