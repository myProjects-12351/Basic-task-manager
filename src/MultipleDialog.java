import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class MultipleDialog {
    Set<String> set = new HashSet<>();

    private JTextField titleField;
    private JTextField dateField;
    private JTextArea descriptionArea;
    private JCheckBox isFinished;
    public boolean window() {
        titleField = new JTextField();
        dateField = new JTextField();
        isFinished  = new JCheckBox();

        descriptionArea = new JTextArea();
        descriptionArea.setPreferredSize(new Dimension(200,200));
        JScrollPane scrollPane = new JScrollPane(descriptionArea);


        Object[] message = {
                "Name:", titleField,
                "Date:", dateField,
                "Description:", scrollPane,
                "Finished:", isFinished,
        };

        int option = JOptionPane.showConfirmDialog(null, message, "Answer the Questions", JOptionPane.OK_CANCEL_OPTION);

        if (option == JOptionPane.OK_OPTION && !titleField.getText().isEmpty() && set.add(titleField.getText())) {
            if(dateField.getText().isEmpty()) {
                LocalDate date = LocalDate.now();
                date = date.plusDays(7);
                String[] arr= String.valueOf(date).split("-");
                String newS = "";
                for(int i=arr.length-1; i>=0; i--) {
                    newS += arr[i];
                    if(i!=0) newS+=".";
                }
                dateField.setText(newS);
            }
            System.out.println(set);
            return true;
        } else {
            if (!titleField.getText().isEmpty()) {
                set.remove(titleField.getText());
            }
            System.out.println("Anulowano.");
            return false;
        }
    }

    public String getTitle(){
        return titleField.getText();
    }

    public String getdateField() {
        return dateField.getText();
    }

    public String getdescriptionField() {
        return descriptionArea.getText();
    }

    public boolean getisFinished() {
        return isFinished.isSelected();
    }
}
