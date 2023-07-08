package dutjava.tracnghiem.view.panel;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import dutjava.tracnghiem.model.model.AnswerModel;

public class AdminAnswerPanel extends JPanel {
    private AnswerModel answer;

    private JLabel answerL;
    private JTextField answerF;

    public JRadioButton isCorrectButton;

    private void init() {
        this.setLayout(new FlowLayout());
        this.answerL = new JLabel("Answer: ");
        this.answerF = new JTextField(answer.getAnswer());
        this.answerF.setPreferredSize(new Dimension(300, 30));
        this.answerF.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void changedUpdate(DocumentEvent e) {
                answer.setAnswer(answerF.getText());
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

        this.isCorrectButton = new JRadioButton();
        this.isCorrectButton.setSelected(answer.isCorrect());
        this.isCorrectButton.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if(e.getStateChange() == ItemEvent.DESELECTED) {
                    answer.setCorrect(false);
                } else if(e.getStateChange() == ItemEvent.SELECTED) {
                    answer.setCorrect(true);
                }
            }
        });

        this.add(answerL);
        this.add(answerF);
        this.add(isCorrectButton);
    }
    
    public AdminAnswerPanel(AnswerModel answer) {
        this.answer = answer;
        init();
    }
}
