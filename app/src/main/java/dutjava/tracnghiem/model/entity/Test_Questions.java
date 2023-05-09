package dutjava.tracnghiem.model.entity;

import dutjava.tracnghiem.util.database.AutoIncrement;
import dutjava.tracnghiem.util.database.Primary;

public class Test_Questions {
    @Primary @AutoIncrement
    private int _id;

    private int test_id;
    private int question_id;
    public Test_Questions(int _id, int test_id, int question_id) {
        this._id = _id;
        this.test_id = test_id;
        this.question_id = question_id;
    }
    public Test_Questions(int test_id, int question_id) {
        this._id = -1;
        this.test_id = test_id;
        this.question_id = question_id;
    }
    public int getTest_id() {
        return test_id;
    }
    public void setTest_id(int test_id) {
        this.test_id = test_id;
    }
    public int getQuestion_id() {
        return question_id;
    }
    public void setQuestion_id(int question_id) {
        this.question_id = question_id;
    }

    @Override
    public boolean equals(Object obj) {
        Test_Questions tq = (Test_Questions) obj;
        return this.question_id == tq.question_id && this.test_id == tq.test_id;
    }
}
