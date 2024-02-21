import javax.swing.*;

public class AppFrame{
    public AppFrame() {
        JFrame frame = new JFrame();
        frame.add(new MainPanel());
        frame.setTitle("Task Manager");
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.pack();
        frame.setVisible(true);
    }
}
