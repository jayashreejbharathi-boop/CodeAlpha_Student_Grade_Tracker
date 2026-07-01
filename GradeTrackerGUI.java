import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

class Student {
    String name;
    double grade;

    Student(String name, double grade) {
        this.name = name;
        this.grade = grade;
    }
}

public class GradeTrackerGUI extends JFrame {
    private ArrayList<Student> students = new ArrayList<>();
    private DefaultTableModel tableModel;
    private JLabel avgLabel, highLabel, lowLabel;

    public GradeTrackerGUI() {
        // Apply Nimbus Look-and-Feel for modern UI
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            System.out.println("Nimbus not available, using default.");
        }

        setTitle("🎓 Student Grade Tracker");
        setSize(700, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Table with custom styling
        tableModel = new DefaultTableModel(new Object[]{"Name", "Grade"}, 0);
        JTable table = new JTable(tableModel);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.setRowHeight(25);
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 15));
        JScrollPane scrollPane = new JScrollPane(table);

        // Input Panel
        JPanel inputPanel = new JPanel(new FlowLayout());
        inputPanel.setBackground(new Color(230, 240, 250));

        JTextField nameField = new JTextField(12);
        JTextField gradeField = new JTextField(6);
        JButton addButton = new JButton("➕ Add Student");
        addButton.setBackground(new Color(70, 130, 180));
        addButton.setForeground(Color.WHITE);
        addButton.setFont(new Font("Segoe UI", Font.BOLD, 14));

        inputPanel.add(new JLabel("Name:"));
        inputPanel.add(nameField);
        inputPanel.add(new JLabel("Grade:"));
        inputPanel.add(gradeField);
        inputPanel.add(addButton);

        // Summary Panel
        JPanel summaryPanel = new JPanel(new GridLayout(3, 1));
        summaryPanel.setBackground(new Color(245, 245, 245));
        avgLabel = new JLabel("Average: ");
        highLabel = new JLabel("Highest: ");
        lowLabel = new JLabel("Lowest: ");

        Font summaryFont = new Font("Segoe UI", Font.BOLD, 14);
        avgLabel.setFont(summaryFont);
        highLabel.setFont(summaryFont);
        lowLabel.setFont(summaryFont);

        summaryPanel.add(avgLabel);
        summaryPanel.add(highLabel);
        summaryPanel.add(lowLabel);

        // Add components
        add(inputPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(summaryPanel, BorderLayout.SOUTH);

        // Button Action
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    String name = nameField.getText().trim();
                    double grade = Double.parseDouble(gradeField.getText().trim());
                    if (name.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Name cannot be empty.");
                        return;
                    }
                    students.add(new Student(name, grade));
                    tableModel.addRow(new Object[]{name, grade});
                    updateSummary();
                    nameField.setText("");
                    gradeField.setText("");
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Please enter a valid grade.");
                }
            }
        });
    }

    private void updateSummary() {
        if (students.isEmpty()) return;

        double sum = 0, highest = Double.MIN_VALUE, lowest = Double.MAX_VALUE;
        String topStudent = "", lowStudent = "";

        for (Student s : students) {
            sum += s.grade;
            if (s.grade > highest) {
                highest = s.grade;
                topStudent = s.name;
            }
            if (s.grade < lowest) {
                lowest = s.grade;
                lowStudent = s.name;
            }
        }

        double average = sum / students.size();
        avgLabel.setText("Average: " + String.format("%.2f", average));
        highLabel.setText("Highest: " + highest + " (" + topStudent + ")");
        lowLabel.setText("Lowest: " + lowest + " (" + lowStudent + ")");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new GradeTrackerGUI().setVisible(true);
        });
    }
}
 