package dutjava.tracnghiem.view.page;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import dutjava.tracnghiem.model.model.TestModel;
import dutjava.tracnghiem.model.service.implement.TestService;
import dutjava.tracnghiem.util.dependency_injection.Inject;
import dutjava.tracnghiem.view.design.Size;

public class AdminPage extends JFrame {

    @Inject
    private TestService testService;

    private List<TestModel> tests;

    private JPanel topPanel;
    private JButton addButton;
    private JButton editButton;
    private JButton deleteButton;

    private JTable testTable;
    private JScrollPane scrollPane;
    
    private void init() {
        this.getContentPane().removeAll();
        this.repaint();

        this.setTitle("Admin: Test");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(Size.ADMIN_PAGE_WIDTH, Size.ADMIN_PAGE_HEIGHT);
        this.setPreferredSize(new Dimension(Size.ADMIN_PAGE_WIDTH, Size.ADMIN_PAGE_HEIGHT));
        this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));

        if(testService == null)
            return;

        lateInit();
    }

    public void reset() {
        init();
    }

    private JPanel buildTopPanel() {
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout(FlowLayout.TRAILING));
        addButton = new JButton("Add test");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AdminTestPage questionPage = new AdminTestPage();
                questionPage.setCallback(test -> {
                    if(test != null)
                        testService.save(test);
                    reset();
                    setVisible(true);
                });
                questionPage.setVisible(true);
                setVisible(false);
            }
        });

        editButton = new JButton("Edit");
        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AdminTestPage questionPage = new AdminTestPage(testService.getAll().get(testTable.getSelectedRow()));
                questionPage.setCallback(test -> {
                    if(test != null)
                        testService.save(test);
                    reset();
                    setVisible(true);
                });
                questionPage.setVisible(true);
                setVisible(false);
            }
        });
        editButton.setVisible(false);
        deleteButton = new JButton("Delete");
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for(int i : testTable.getSelectedRows()) {
                    TestModel test = tests.get(i);
                    testService.delete(test);
                }
                setVisible(false);
                reset();
                setVisible(true);
            }
        });
        deleteButton.setVisible(false);
        topPanel.add(editButton);
        topPanel.add(deleteButton);
        topPanel.add(addButton);
        return topPanel;
    }

    private JTable buildTestTable() {
        String[] columns = {"Id", "Name", "Questions count"};
        ArrayList<String[]> data = new ArrayList<>();

        tests.forEach((test) -> {
            data.add(new String[] {String.valueOf(test.getId()), test.getName(), String.valueOf(test.getQuestions().size())});
        });

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

    public void lateInit() {
        tests = testService.getAll();
        topPanel = buildTopPanel();
        testTable = buildTestTable();
        scrollPane = new JScrollPane(testTable);

        this.add(topPanel, Component.CENTER_ALIGNMENT);
        this.add(scrollPane, Component.CENTER_ALIGNMENT);
    }

    public AdminPage() {
        init();
    }
}
