package dutjava.tracnghiem.view.panel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import dutjava.tracnghiem.model.model.AnswerModel;
import dutjava.tracnghiem.model.model.QuestionModel;

class ChoiceRadioButton extends JRadioButton {
    private int id;
    private AnswerModel answer;

    public ChoiceRadioButton() {
        super();
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public AnswerModel getAnswer() {
        return answer;
    }
    public void setAnswer(AnswerModel answer) {
        this.answer = answer;
        this.setText(answer.getAnswer());
    }
}

public class QuestionPanel extends JPanel {    
    private QuestionModel question;

    private JLabel descL;
    private JPanel choicePanel;
    private ChoiceRadioButton[] choice;

    private int selectedIndex = -1;

    private JPanel buildChoicePanel() {
        JPanel choicePanel = new JPanel();
        choicePanel.setLayout(new BoxLayout(choicePanel, BoxLayout.Y_AXIS));
        choice = new ChoiceRadioButton[question.getAnswers().size()];
        for(int i = 0; i < question.getAnswers().size(); i++) {
            choice[i] = new ChoiceRadioButton();
            choice[i].setId(i);
            choice[i].setAnswer(question.getAnswers().get(i));
            choice[i].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    ChoiceRadioButton b = (ChoiceRadioButton) e.getSource();
                    if(b.isSelected()) {
                        for(int i = 0; i < question.getAnswers().size(); i++)
                            if(i != b.getId())
                                choice[i].setSelected(false);
                        selectedIndex = b.getId();
                    }
                }
            });
            choicePanel.add(choice[i]);
        }
        return choicePanel;
    }

    private void init() {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        descL = new JLabel(question.getQuestionDescription());
        choicePanel = buildChoicePanel();
        this.add(descL);
        this.add(choicePanel);
        // System.out.println(question.getQuestionDescription());
    }

    public boolean isCorrect() {
        if(selectedIndex < 0)
            return false;
        return question.getAnswers().get(selectedIndex).isCorrect();
    }

    public double getPoint() {
        return question.getPoint() * (isCorrect() ? 1 : 0);
    }

    public int getSelectedIndex() {
        return selectedIndex;
    }

    public QuestionPanel(QuestionModel question) {
        this.question = question;
        init();
    }
}
