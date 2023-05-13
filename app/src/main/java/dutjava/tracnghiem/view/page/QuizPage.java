package dutjava.tracnghiem.view.page;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import dutjava.tracnghiem.model.model.QuestionModel;
import dutjava.tracnghiem.model.model.TestModel;
import dutjava.tracnghiem.view.design.Size;
import dutjava.tracnghiem.view.panel.QuestionPanel;

public class QuizPage extends JFrame {

    private TestModel test;

    private JLabel testNameL;
    private JLabel testDescL;
    private JLabel testNumOfQuestionL;
    private JPanel topPanel;

    private JTabbedPane questionTabbedPane;

    private JButton submitButton;
    private JPanel bottomPanel;

    public void setTest(TestModel test) {
        this.test = test;
        init();
    }

    private JPanel buildTopPanel() {
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
        // topPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));

        testNameL = new JLabel("Test: " + test.getName());
        testDescL = new JLabel(test.getDescription());
        testNumOfQuestionL = new JLabel("Number of questions: " + String.valueOf(test.getQuestions().size()));

        testNameL.setAlignmentX(Component.CENTER_ALIGNMENT);
        testDescL.setAlignmentX(Component.CENTER_ALIGNMENT);
        testNumOfQuestionL.setAlignmentX(Component.CENTER_ALIGNMENT);

        topPanel.add(Box.createVerticalGlue());

        topPanel.add(testNameL);
        topPanel.add(Box.createVerticalStrut(10));
        topPanel.add(testDescL);
        topPanel.add(Box.createVerticalStrut(10));
        topPanel.add(testNumOfQuestionL);

        topPanel.add(Box.createVerticalGlue());
        return topPanel;
    }

    private JTabbedPane buildQuestionTabbedPane() {
        JTabbedPane questionTabbedPane = new JTabbedPane();
        for (int i = 1; i <= test.getQuestions().size(); i++) {
            QuestionModel question = test.getQuestions().get(i - 1);
            questionTabbedPane.addTab(String.valueOf(i), new QuestionPanel(question));
        }
        return questionTabbedPane;
    }

    private JPanel buildBottomPanel() {
        JPanel bottomPanel = new JPanel();
        submitButton = new JButton("Submit");
        submitButton.setSize(300, 200);
        submitButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                ArrayList<Integer> selections = new ArrayList<>();
                for (Component c : questionTabbedPane.getComponents()) {
                    QuestionPanel qPanel = (QuestionPanel) c;
                    selections.add(qPanel.getSelectedIndex());
                }
                TestResultPage testResult = new TestResultPage(test, selections.toArray(new Integer[0]));
                testResult.setVisible(true);
                close();
                // double sum = 0;
                // for(int i = 0; i < test.getQuestions().size(); i++) {
                // QuestionPanel qPanel = (QuestionPanel) questionTabbedPane.getComponentAt(i);
                // sum += qPanel.getPoint();
                // }
                // System.out.println(sum);
            }

        });
        bottomPanel.add(submitButton);
        return bottomPanel;
    }

    private void close() {
        this.setVisible(false);
    }

    private void init() {
        this.getContentPane().removeAll();
        this.repaint();
        this.setTitle("Quiz");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(Size.QUIZ_PAGE_WIDTH, Size.QUIZ_PAGE_HEIGHT);
        this.setPreferredSize(new Dimension(Size.QUIZ_PAGE_WIDTH, Size.QUIZ_PAGE_HEIGHT));
        this.setLayout(new BorderLayout());

        if (test == null)
            return;

        topPanel = buildTopPanel();
        topPanel.setBackground(Color.white);
        questionTabbedPane = buildQuestionTabbedPane();
        bottomPanel = buildBottomPanel();
        bottomPanel.setBackground(Color.black);
        this.add(topPanel, BorderLayout.NORTH);
        this.add(questionTabbedPane, BorderLayout.CENTER);

        this.add(bottomPanel, BorderLayout.SOUTH);
    }

    public QuizPage() {
        init();
    }
}
