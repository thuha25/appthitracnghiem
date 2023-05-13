package dutjava.tracnghiem.view.page;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowStateListener;
import java.util.ArrayList;
import java.util.function.Consumer;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import dutjava.tracnghiem.model.model.QuestionModel;
import dutjava.tracnghiem.model.model.TestModel;
import dutjava.tracnghiem.view.design.Size;

public class AdminQuestionPage extends JFrame {

    public TestModel test;

    private Consumer<TestModel> callback;

    private JPanel topPanel;
    private JPanel fieldPanel;

    private JPanel testNameP;
    private JLabel testNameL;
    private JTextField testNameF;

    private JPanel testDescriptionP;
    private JLabel testDescriptionL;
    private JTextArea testDescriptionF;

    private JPanel buttonPanel;
    private JButton addQuestion;
    private JButton editButton;
    private JButton deleteButton;

    private JTable questionTable;
    private JScrollPane scrollPane;

    private JButton okButton;
    private JButton cancelButton;

    private JPanel buildFieldPanel() {
        JPanel fieldPanel = new JPanel();
        fieldPanel.setLayout(new BoxLayout(fieldPanel, BoxLayout.Y_AXIS));

        testNameP = new JPanel(new FlowLayout());
        testNameL = new JLabel("Name: ");
        testNameF = new JTextField(test.getName());
        testNameF.setPreferredSize(new Dimension(500, 30));
        testNameP.add(testNameL);
        testNameP.add(testNameF);

        testDescriptionP = new JPanel(new FlowLayout());
        testDescriptionL = new JLabel("Description: ");
        testDescriptionF = new JTextArea(test.getDescription());
        testDescriptionF.setPreferredSize(new Dimension(1000, 300));
        testDescriptionP.add(testDescriptionL);
        testDescriptionP.add(testDescriptionF);

        fieldPanel.add(testNameP);
        fieldPanel.add(testDescriptionP);
        return fieldPanel;
    }

    private JPanel buildTopPanel() {
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
        fieldPanel = buildFieldPanel();
        buttonPanel = new JPanel(new FlowLayout());
        addQuestion = new JButton("Add question");
        editButton = new JButton("Edit");
        deleteButton = new JButton("Delete");

        buttonPanel.add(deleteButton);
        buttonPanel.add(editButton);
        buttonPanel.add(addQuestion);

        topPanel.add(fieldPanel);
        topPanel.add(buttonPanel);
        return topPanel;
    }

    private JTable buildQuestionTable() {
        String[] columns = {"ID", "Question"};
        ArrayList<String[]> data = new ArrayList<>();
        for(int i = 0; i < test.getQuestions().size(); i++) {
            QuestionModel q = test.getQuestions().get(i);
            data.add(new String[] {String.valueOf(i + 1), q.getQuestionDescription()});
        }
        JTable table = new JTable(data.toArray(new Object[0][]), columns);
        table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if(table.getSelectedRow() == -1) {
                    editButton.setVisible(false);
                    deleteButton.setVisible(false);
                } else {
                    editButton.setVisible(true);
                    deleteButton.setVisible(true);
                }
            }
        });
        return table;
    }

    private void init() {
        this.setTitle("Admin: Question");
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.addWindowStateListener(new WindowStateListener() {
            @Override
            public void windowStateChanged(WindowEvent e) {
                callback.accept(null);
            }
        });
        this.setSize(Size.ADMIN_PAGE_WIDTH, Size.ADMIN_PAGE_HEIGHT);
        this.setPreferredSize(new Dimension(Size.ADMIN_PAGE_WIDTH, Size.ADMIN_PAGE_HEIGHT));
        this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
        topPanel = buildTopPanel();
        questionTable = buildQuestionTable();
        scrollPane = new JScrollPane(questionTable);

        okButton = new JButton("OK");
        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                test.setName(testNameF.getText());
                test.setDescription(testDescriptionF.getText());
                
                close();
            }
        });

        cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                test = null;
                close();
            } 
        });
        
        this.add(topPanel);
        this.add(scrollPane);
        this.add(okButton);
        this.add(cancelButton);
    }

    private void close() {
        setVisible(false);
        callback.accept(test);
    }

    public AdminQuestionPage() {
        test = new TestModel("", "", new ArrayList<>());
        init();
    }

    public AdminQuestionPage(TestModel test) {
        this.test = test;
        init();
    }

    public void setCallback(Consumer<TestModel> callback) {
        this.callback = callback;
    }
}
