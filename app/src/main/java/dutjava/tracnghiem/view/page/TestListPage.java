package dutjava.tracnghiem.view.page;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

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
    }

    public void lateInit() {
        centerPanel = buildCenterPanel();
        this.add(centerPanel, BorderLayout.CENTER);
    }

    public TestListPage() {
        init();
    }
}
