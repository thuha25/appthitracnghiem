package dutjava.tracnghiem.view.page;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import dutjava.tracnghiem.model.model.QuestionModel;
import dutjava.tracnghiem.model.model.TestModel;
import dutjava.tracnghiem.view.design.Size;

public class TestResultPage extends JFrame {

    private TestModel test;
    private Integer[] selections;

    private int totalCount;
    private int sumScore;
    private double totalScore;
    private double scale10;

    private JPanel topPanel;
    private JLabel totalCountL;
    private JLabel totalScoreL;
    private JLabel scale10ScoreL;
    
    private JTable table;
    private JScrollPane scrollPane;

    private JButton okButton;

    private void calculateScore() {
        totalCount = 0;
        totalScore = 0;
        for(int i = 0; i < test.getQuestions().size(); i++) {
            QuestionModel q = test.getQuestions().get(i);
            sumScore += q.getPoint();
            if(selections[i] < 0)
                continue;
            if(q.getAnswers().get(selections[i]).isCorrect()) {
                totalCount++;
                totalScore += q.getPoint();
            }
        }
        scale10 = (totalScore / sumScore) * 10;
    }

    private JPanel buildTopPanel() {
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
        totalCountL = new JLabel("Correct: " + totalCount + "/" + test.getQuestions().size());
        totalScoreL = new JLabel("Score: " + new DecimalFormat("0.00").format(totalScore) + "/" + new DecimalFormat("0.00").format(sumScore));
        scale10ScoreL = new JLabel("Scaled to 10: " + new DecimalFormat("0.00").format(scale10));

        Font f = new Font(Font.DIALOG, Font.PLAIN, 20);
        totalCountL.setFont(f);
        totalScoreL.setFont(f);
        scale10ScoreL.setFont(f);

        topPanel.add(totalCountL);
        topPanel.add(totalScoreL);
        topPanel.add(scale10ScoreL);

        // topPanel.setSize(200, 100);
        topPanel.setPreferredSize(new Dimension(200, 100));
        return topPanel;
    }

    private JTable buildQuestionAnswerTable() {
        String[] columns = {"Id", "Question", "Correct Answer", "Answer"};
        ArrayList<String[]> data = new ArrayList<>();
        for(int i = 0; i < test.getQuestions().size(); i++)
            data.add(new String[] {String.valueOf(i + 1), test.getQuestions().get(i).getQuestionDescription(), test.getQuestions().get(i).getCorrectAnswer().getAnswer(), selections[i] < 0 ? "" : test.getQuestions().get(i).getAnswers().get(selections[i]).getAnswer()});
        JTable table = new JTable(data.toArray(new Object[0][]), columns);
        table.getColumnModel().getColumn(0).setPreferredWidth(15);
        // table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        return table;
    }

    private void init() {
        this.setTitle("Result");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(Size.RESULT_PAGE_WIDTH, Size.RESULT_PAGE_HEIGHT);
        this.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 20)); 

        calculateScore();

        topPanel = buildTopPanel();

        table = buildQuestionAnswerTable();
        scrollPane = new JScrollPane(table);
        
        okButton = new JButton("OK");
        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                close();
            }
        });
        this.add(topPanel, Component.CENTER_ALIGNMENT);
        this.add(scrollPane, Component.CENTER_ALIGNMENT);
        this.add(okButton,Component.CENTER_ALIGNMENT);
    }

    private void close() {
        this.setVisible(false);
    }

    public TestResultPage(TestModel test, Integer[] selections) {
        this.test = test;
        this.selections = selections;
        init();
    }    
}
