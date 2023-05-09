package dutjava.tracnghiem.model.entity;

import dutjava.tracnghiem.util.database.AutoIncrement;
import dutjava.tracnghiem.util.database.Entity;
import dutjava.tracnghiem.util.database.Primary;

@Entity
public class Question_Answers {
    @Primary @AutoIncrement
    private int _id;

    private int question_id;
    private int answer_id;
    
    public Question_Answers(int _id, int question_id, int answer_id) {
        this._id = _id;
        this.question_id = question_id;
        this.answer_id = answer_id;
    }
    public Question_Answers(int question_id, int answer_id) {
        this._id = -1;
        this.question_id = question_id;
        this.answer_id = answer_id;
    }
    public int getQuestion_id() {
        return question_id;
    }
    public void setQuestion_id(int question_id) {
        this.question_id = question_id;
    }
    public int getAnswer_id() {
        return answer_id;
    }
    public void setAnswer_id(int answer_id) {
        this.answer_id = answer_id;
    }

    @Override
    public boolean equals(Object obj) {
        Question_Answers qa = (Question_Answers) obj;
        return this.answer_id == qa.answer_id && this.question_id == qa.question_id;
    }
}
