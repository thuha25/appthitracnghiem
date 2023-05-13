package dutjava.tracnghiem.view.page;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.ImageIcon;

import dutjava.tracnghiem.model.model.TestModel;
import dutjava.tracnghiem.model.service.implement.TestService;
import dutjava.tracnghiem.util.dependency_injection.Inject;
import dutjava.tracnghiem.view.design.Size;
import dutjava.tracnghiem.view.panel.TestSelectPanel;

public class TestListPage extends JFrame {
    @Inject
    private QuizPage quizPage;

    @Inject
    private TestService testService;

    private JScrollPane scrollPane;
    private JPanel container;
    private JPanel centerPanel;

    private JScrollPane buildScrollPane() {
        container = new JPanel();

        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
        JLabel imageLabel = new JLabel();
        ImageIcon imageIcon = new ImageIcon("./src/main/java/dutjava/tracnghiem/assets/logoALTP.png");
        java.awt.Image image = imageIcon.getImage().getScaledInstance(150, 130, java.awt.Image.SCALE_SMOOTH);
        ImageIcon scaledImageIcon = new ImageIcon(image);
        imageLabel.setIcon(scaledImageIcon);
        imageLabel.setText(" Hệ Thống Thi Trắc Nghiệm");
        container.add(imageLabel, BorderLayout.CENTER);
       
        List<TestModel> tests = testService.getAll();
        tests.forEach((test) -> {
            TestSelectPanel panel = new TestSelectPanel(test);
            panel.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    quizPage.setTest(test);
                    quizPage.setVisible(true);
                }
            });
            container.add(panel);
        });
        JScrollPane scrollPane = new JScrollPane(container);
        scrollPane.setBackground(Color.gray);

        scrollPane.setPreferredSize(new Dimension(Size.LIST_PANE_WIDTH, Size.LIST_PANE_HEIGHT));
        return scrollPane;
    }

    private JPanel buildCenterPanel() {
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new FlowLayout());
        scrollPane = buildScrollPane();
        centerPanel.add(scrollPane);
        return centerPanel;
    }

    private void init() {
        this.setTitle("Test");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(Size.LIST_TEST_WIDTH, Size.LIST_TEST_HEIGHT);
        this.setPreferredSize(new Dimension(Size.LIST_TEST_WIDTH, Size.LIST_TEST_HEIGHT));
        this.setLayout(new BorderLayout());
        this.setBackground(Color.lightGray);
    }

    public void lateInit() {
        centerPanel = buildCenterPanel();
        centerPanel.setBackground(Color.black);
        ;
        this.add(centerPanel, BorderLayout.CENTER);
    }

    public TestListPage() {
        init();
    }
}
