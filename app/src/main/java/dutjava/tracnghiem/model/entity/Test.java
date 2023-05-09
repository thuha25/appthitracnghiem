package dutjava.tracnghiem.model.entity;

import dutjava.tracnghiem.model.model.TestModel;
import dutjava.tracnghiem.util.database.AutoIncrement;
import dutjava.tracnghiem.util.database.Entity;
import dutjava.tracnghiem.util.database.Primary;

@Entity
public class Test {
    @Primary @AutoIncrement
    private int id;
    private String name;
    private String description;
    public Test(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }
    public Test(TestModel model) {
        this(model.getId(), model.getName(), model.getDescription());
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
}
