package dutjava.tracnghiem.model.model;

import dutjava.tracnghiem.model.entity.Answer;

public class AnswerModel {
    private Integer id;
    private String answer;
    private boolean isCorrect;
    
    public AnswerModel(String answer, boolean isCorrect) {
        this.id = -1;
        this.answer = answer;
        this.isCorrect = isCorrect;
    }

    public AnswerModel(Answer entity) {
        this.id = entity.getId();
        this.answer = entity.getAnswer();
        this.isCorrect = entity.getCorrect() == 1;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }


    public boolean isCorrect() {
        return isCorrect;
    }

    public void setCorrect(boolean isCorrect) {
        this.isCorrect = isCorrect;
    }
}
