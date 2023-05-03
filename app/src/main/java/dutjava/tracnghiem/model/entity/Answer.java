package dutjava.tracnghiem.model.entity;

import dutjava.tracnghiem.util.database.Entity;
import dutjava.tracnghiem.util.database.Primary;

@Entity
public class Answer {
    @Primary
    private Integer id;
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    private String answer;
    public String getAnswer() {
        return answer;
    }
    public void setAnswer(String answer) {
        this.answer = answer;
    }
    private boolean isCorrect;
    public Answer(Integer id, String answer, boolean isCorrect) {
        this.id = id;
        this.answer = answer;
        this.isCorrect = isCorrect;
    }
    public boolean isCorrect() {
        return isCorrect;
    }
    public void setCorrect(boolean isCorrect) {
        this.isCorrect = isCorrect;
    }
}
