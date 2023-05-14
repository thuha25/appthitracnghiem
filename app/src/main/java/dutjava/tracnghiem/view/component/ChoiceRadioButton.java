package dutjava.tracnghiem.view.component;

import javax.swing.JRadioButton;

import dutjava.tracnghiem.model.model.AnswerModel;

public class ChoiceRadioButton extends JRadioButton {
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
