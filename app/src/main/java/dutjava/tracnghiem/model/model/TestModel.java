package dutjava.tracnghiem.model.model;

import java.util.List;

public class TestModel {
    private int id;
    private String name;
    private String description;
    private List<QuestionModel> questions;
    
    public TestModel(String name, String description, List<QuestionModel> questions) {
        this.name = name;
        this.description = description;
        this.questions = questions;
    }
    
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    
    public List<QuestionModel> getQuestions() {
        return questions;
    }
    public void setQuestions(List<QuestionModel> questions) {
        this.questions = questions;
    }
}
