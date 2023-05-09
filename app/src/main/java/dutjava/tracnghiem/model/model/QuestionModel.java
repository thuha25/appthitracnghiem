package dutjava.tracnghiem.model.model;

import java.util.List;

public class QuestionModel {
    private int id;
    private String questionDescription;
    private float point;
    private List<AnswerModel> answers;

    public QuestionModel(String questionDescription, float point, List<AnswerModel> answers) {
        this.id = -1;
        this.questionDescription = questionDescription;
        this.point = point;
        this.answers = answers;
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
    public List<AnswerModel> getAnswers() {
        return answers;
    }
    public void setAnswers(List<AnswerModel> answers) {
        this.answers = answers;
    }
}
