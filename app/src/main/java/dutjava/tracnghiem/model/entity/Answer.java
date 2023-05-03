package dutjava.tracnghiem.model.entity;

import dutjava.tracnghiem.util.database.Entity;
import dutjava.tracnghiem.util.database.Primary;

@Entity
public class Answer {
    @Primary
    private Integer id;
    private String answer;
    private boolean isCorrect; 
}
