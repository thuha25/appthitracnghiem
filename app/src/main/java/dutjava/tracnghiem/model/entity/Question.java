package dutjava.tracnghiem.model.entity;

import dutjava.tracnghiem.model.model.QuestionModel;
import dutjava.tracnghiem.util.database.AutoIncrement;
import dutjava.tracnghiem.util.database.Entity;
import dutjava.tracnghiem.util.database.Primary;

@Entity
public class Question {
    @Primary @AutoIncrement
    private int id;
    private String questionDescription;
    private float point;
    public Question(int id, String questionDescription, float point) {
        this.id = id;
        this.questionDescription = questionDescription;
        this.point = point;
    }
    public Question(QuestionModel model) {
        this(model.getId(), model.getQuestionDescription(), model.getPoint());
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getQuestionDescription() {
        return questionDescription;
    }
    public void setQuestionDescription(String questionDescription) {
        this.questionDescription = questionDescription;
    }
    public float getPoint() {
        return point;
    }
    public void setPoint(float point) {
        this.point = point;
    }
}
