package dutjava.tracnghiem.model.entity;

import dutjava.tracnghiem.model.model.AnswerModel;
import dutjava.tracnghiem.util.database.AutoIncrement;
import dutjava.tracnghiem.util.database.Entity;
import dutjava.tracnghiem.util.database.Primary;

@Entity
public class Answer {
    @Primary @AutoIncrement
    private Integer id;
    private String answer;
    private int isCorrect;

    public Answer(Integer id, String answer, int isCorrect) {
        this.id = id;
        this.answer = answer;
        this.isCorrect = isCorrect;
    }
    public Answer(AnswerModel model) {
        this.id = model.getId();
        this.answer = model.getAnswer();
        this.isCorrect = model.isCorrect() ? 1 : 0;
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
    public int getCorrect() {
        return isCorrect;
    }
    public void setCorrect(int isCorrect) {
        this.isCorrect = isCorrect;
    }
}
