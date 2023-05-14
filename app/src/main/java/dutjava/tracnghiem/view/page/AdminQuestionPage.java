package dutjava.tracnghiem.view.page;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.function.Consumer;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import dutjava.tracnghiem.model.model.AnswerModel;
import dutjava.tracnghiem.model.model.QuestionModel;
import dutjava.tracnghiem.view.design.Size;
import dutjava.tracnghiem.view.panel.AdminAnswerPanel;

public class AdminQuestionPage extends JFrame {

    private Consumer<QuestionModel> callback;
    private QuestionModel question;

    private JLabel questionL;
    private JTextArea questionF;

    private JLabel pointL;
    private JTextField pointF;

    private JButton addAnswerButton;
    private JButton removeAnswerButton;

    private JButton okButton;
    private JButton cancelButton;

    private JPanel buildFieldPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        JPanel questionP = new JPanel(new FlowLayout());
        questionL = new JLabel("Question: ");
        questionF = new JTextArea(question.getQuestionDescription());
        questionF.setPreferredSize(new Dimension(300, 100));
        questionF.getDocument().addDocumentListener(new DocumentListener() {

            @Override
            public void changedUpdate(DocumentEvent e) {
                question.setQuestionDescription(questionF.getText());
            }

            @Override
            public void insertUpdate(DocumentEvent e) {
                changedUpdate(e);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                changedUpdate(e);
            }
            
        });
        questionP.add(questionL);
        questionP.add(questionF);
        JScrollPane questionScrollPane = new JScrollPane(questionP);

        JPanel pointP = new JPanel(new FlowLayout());
        pointL = new JLabel("Point: ");
        pointF = new JTextField(new DecimalFormat("0.00").format(question.getPoint()));
        pointF.setPreferredSize(new Dimension(300, 30));
        pointF.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void changedUpdate(DocumentEvent e) {
                try {
                    question.setPoint(Float.parseFloat(pointF.getText()));
                } catch(NumberFormatException er) {
                    er.printStackTrace();
                }
            }

            @Override
            public void insertUpdate(DocumentEvent e) {
                changedUpdate(e);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                changedUpdate(e);
            }
        });
        pointP.add(pointL);
        pointP.add(pointF);

        panel.add(questionScrollPane);
        panel.add(pointP);
        panel.setPreferredSize(new Dimension(300, 150));
        panel.setSize(new Dimension(300, 150));
        return panel;
    }

    private JPanel buildARButtonPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.TRAILING));
        addAnswerButton = new JButton("+");
        addAnswerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                question.getAnswers().add(new AnswerModel("", false));
                reset();
            }
        });
        removeAnswerButton = new JButton("-");
        removeAnswerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                question.getAnswers().remove(question.getAnswers().size() - 1);
                reset();
            }
            
        });
        panel.add(addAnswerButton);
        panel.add(removeAnswerButton);
        panel.setPreferredSize(new Dimension(500, 30));
        panel.setSize(new Dimension(500, 30));
        return panel;
    }

    private ArrayList<JRadioButton> answerButton = new ArrayList<>();

    private JPanel buildAnswerPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        answerButton.clear();
        for(int i = 0; i < question.getAnswers().size(); i++) {
            AdminAnswerPanel answerPanel = new AdminAnswerPanel(question.getAnswers().get(i));
            panel.add(answerPanel);
            answerButton.add(answerPanel.isCorrectButton);
            answerPanel.isCorrectButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JRadioButton b = (JRadioButton) e.getSource();
                    if(b.isSelected())
                        for(int i = 0; i < answerButton.size(); i++)
                            if(answerButton.get(i) != b)
                                answerButton.get(i).setSelected(false);
                }
            });
        }
        return panel;
    }

    private void reset() {
        setVisible(false);
        init();
        setVisible(true);
    }

    private void init() {
        this.getContentPane().removeAll();
        this.repaint();
        this.setTitle("Admin: Question");
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setSize(Size.ADMIN_PAGE_WIDTH, Size.ADMIN_PAGE_HEIGHT);
        this.setPreferredSize(new Dimension(Size.ADMIN_PAGE_WIDTH, Size.ADMIN_PAGE_HEIGHT));
        this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));

        JPanel fieldPanel = buildFieldPanel();
        JPanel aRButtonPanel = buildARButtonPanel();
        JPanel answerPanel = buildAnswerPanel();

        okButton = new JButton("OK");
        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    float point = Float.parseFloat(pointF.getText());
                    question.setPoint(point);
                    question.setQuestionDescription(questionF.getText());
                    callback.accept(question);
                    setVisible(false);
                } catch(NumberFormatException er) {
                    er.printStackTrace();
                }
            }
        });

        cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                callback.accept(null);
                setVisible(false);
            }
        });
        this.add(fieldPanel);
        this.add(aRButtonPanel);
        this.add(answerPanel);
        this.add(okButton);
        this.add(cancelButton);
    }

    public AdminQuestionPage(QuestionModel question, Consumer<QuestionModel> callback) {
        this.question = question;
        this.callback = callback;
        init();
    }
}
