import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;

public class MainPanel extends JPanel {
    private static final int SCREEN_WIDTH = 600;
    private static final int SCREEN_HEIGHT = 1000;
    private static final int TASKS_PANEL_HEIGHT = (int)(SCREEN_HEIGHT*0.9);
    private static final int FOOTER_HEIGHT = (int)(SCREEN_HEIGHT*0.1);
    private final ArrayList<Tasks> tasks = new ArrayList<>();
    private int sx=SCREEN_WIDTH-15, sy=0, sw=100, sh=SCREEN_HEIGHT;
    protected int changeY=0;
    protected int multiplyY=-2;
    public double scrollSize = TASKS_PANEL_HEIGHT;
    JPanel tasksPanel;
    MultipleDialog multipleDialog;

    public MainPanel(){
        this.setLayout(null);
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(new Color(170,170,170));
        this.setFocusable(true);

        int buttonSize = 100;
        addButton((SCREEN_WIDTH-buttonSize)/2, TASKS_PANEL_HEIGHT, buttonSize);

        tasksPanel = new JPanel();
        tasksPanel.setBounds(0,0,SCREEN_WIDTH,TASKS_PANEL_HEIGHT);
        tasksPanel.setBackground(Color.lightGray);

        DragButton dragButton = new DragButton("Drag Me");
        dragButton.setBounds(sx,sy,sw,(int)scrollSize);
        dragButton.setBackground(new Color(150,150,150));
        dragButton.setVisible(true);

        this.add(dragButton);
        this.add(tasksPanel);
        displayTasks();
    }

    private void addButton(int x, int y, int size) {
        JButton button = new JButton("+");
        button.setFont(new Font("Ink Free", Font.BOLD, (int)(size*0.75)));
        button.setSize(size, size);
        button.setLocation(x, y);
        button.setBackground(new Color(100,100,100));
        button.setVisible(true);

        button.addActionListener(e -> {
            multipleDialog = new MultipleDialog();
            if(multipleDialog.window()){
                createNewTask();
            }
        });

        this.add(button);
    }

    private void createNewTask(){
        String title = multipleDialog.getTitle();
        String descrition = multipleDialog.getdescriptionField();
        String date = multipleDialog.getdateField();
        boolean isFinished = multipleDialog.getisFinished();
        Tasks newTask = new Tasks(title, descrition, date, isFinished);
        tasks.add(newTask);
        displayTasks();

        System.out.println("-=-=-=-=-=-=- ADD TASK -=-=-=-=-=-=-");
        for(Tasks t : tasks){
            t.print();
        }
    }

    private void displayTasks(){
        tasksPanel.removeAll();

        int y=10, x=50;
        int panelLength = SCREEN_WIDTH-(2*x);
        for(Tasks t : tasks){
            JLabel titleLabel = new JLabel();
            titleLabel.setFont(new Font("Ink Free", Font.BOLD, 20));
            titleLabel.setBackground(Color.LIGHT_GRAY);
            titleLabel.setOpaque(true);
            titleLabel.setText(t.getTitle());
            titleLabel.setBounds(15,5,450,25);

            JLabel descriptionLabel = new JLabel();
            String textWithHTML = t.getDescription().replace("\n", "<br>");
            descriptionLabel.setFont(new Font("Ink Free", Font.BOLD, 20));
            descriptionLabel.setText("<html>" + textWithHTML + "</html>");
            int BRs = countBR(textWithHTML);
            descriptionLabel.setBackground(Color.LIGHT_GRAY);
            descriptionLabel.setOpaque(true);
            descriptionLabel.setVerticalAlignment(SwingConstants.TOP);
            descriptionLabel.setBounds(15,35,450,BRs*25);

            int dateLabelY = titleLabel.getHeight() + descriptionLabel.getHeight()+15;
            JLabel dateLabel = new JLabel();
            dateLabel.setBackground(Color.LIGHT_GRAY);
            dateLabel.setOpaque(true);
            dateLabel.setText(t.getDate());
            dateLabel.setHorizontalAlignment(SwingConstants.CENTER);
            dateLabel.setBounds(panelLength/2+5,dateLabelY,panelLength-panelLength/2-15,25);

            JButton finishedButton = new JButton();
//            if(t.isFinished()) finishedButton.setBackground(Color.GREEN);
//            else finishedButton.setBackground(Color.red);
            finishedButton.setBackground(Color.LIGHT_GRAY);
            finishedButton.setOpaque(true);
            finishedButton.setText("Is finished: "+String.valueOf(t.isFinished()));
            finishedButton.setHorizontalAlignment(SwingConstants.CENTER);
            finishedButton.setBounds(15,dateLabelY,panelLength/2-15,25);
            finishedButton.addActionListener(e -> {
                markAsFinished(titleLabel.getText());
            });

            ImageIcon imageIcon = new ImageIcon(getClass().getResource("/del1.png"));
            JButton trashButton = new JButton(imageIcon);
            trashButton.setBounds(panelLength-30, 5, 25,25);
            trashButton.addActionListener(e -> {
                    deleteTask(titleLabel.getText());
                });

            int height = finishedButton.getHeight()+descriptionLabel.getHeight()+ titleLabel.getHeight()+20;
            JPanel panel = new JPanel();
            panel.setBounds(x, y + changeY, panelLength, height);
            panel.setBackground(new Color(255,255,255));
            panel.add(finishedButton);
            panel.add(trashButton);
            panel.add(titleLabel);
            panel.add(descriptionLabel);
            panel.add(dateLabel);

            tasksPanel.setPreferredSize(new Dimension(600, y));
            tasksPanel.add(panel);
            y+=height+10;
        }

        if(y > TASKS_PANEL_HEIGHT){
            double multiplier = (double)TASKS_PANEL_HEIGHT / y;
            scrollSize = multiplier * TASKS_PANEL_HEIGHT;
        }

        repaint();
    }

    private void deleteTask(String title){
        for(int i=0; i<tasks.size(); i++){
            Tasks t = tasks.get(i);
            if(t.getTitle().equals(title)){
                tasks.remove(i);
                break;
            }
        }
        System.out.println("=-=-=-=-= DELETING -=-=-=-=-=");
        for (Tasks t : tasks)
            t.print();
        displayTasks();
    }

    private int countBR(String s){
        int count=1;
        for(int i=0; i<s.length()-3; i++){
            if(s.charAt(i)=='<' && s.charAt(i+1)=='b' && s.charAt(i+2)=='r' && s.charAt(i+3)=='>'){
                count++;
            }
        }
        return count;
    }

    private void markAsFinished(String title){
        for(int i=0; i<tasks.size(); i++){
            Tasks t = tasks.get(i);
            if(t.getTitle().equals(title)){
                Tasks curr = tasks.get(i);
                curr.setFinished();
                break;
            }
        }
        System.out.println("=-=-=-=-= MARKING -=-=-=-=-=");
        for (Tasks t : tasks)
            t.print();
        displayTasks();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        //fotter
        g.setColor(new Color(140,140,140));
        g.fillRect(0,TASKS_PANEL_HEIGHT,SCREEN_WIDTH,FOOTER_HEIGHT);
    }

    class DragButton extends JButton {
        private boolean isDragging = false;
        private Point clickPoint;

        public DragButton(String text) {
            super(text);

            addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    isDragging = true;
                    clickPoint = e.getPoint();
                }

                @Override
                public void mouseReleased(MouseEvent e) {
                    isDragging = false;
                }
            });

            addMouseMotionListener(new MouseMotionAdapter() {
                @Override
                public void mouseDragged(MouseEvent e) {
                    if (isDragging) {
                        //int dx = e.getX() - clickPoint.x;
                        int dy = e.getY() - clickPoint.y;

                        int newY = getLocation().y + dy;

                        // Sprawdź, czy nowa pozycja nie przekracza granic ekranu
                        if (newY >= 0 && newY <= TASKS_PANEL_HEIGHT - getHeight()) {
                            setLocation(getLocation().x, newY);
                            changeY = newY*multiplyY;
                            displayTasks();
                        }
                        // Tutaj możesz wywołać odpowiednią logikę w zależności od przesunięcia
                        double newSize = scrollSize * (double) TASKS_PANEL_HEIGHT / TASKS_PANEL_HEIGHT;
                        setSize(getWidth(), (int) newSize);
                    }
                }
            });
        }
    }
}
