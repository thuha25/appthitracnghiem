package dutjava.tracnghiem.model.repository;

import dutjava.tracnghiem.model.entity.Test;
import dutjava.tracnghiem.util.database.CrudRepository;

public class TestRepository extends CrudRepository<Test, Integer> {
    public TestRepository() {
        super(Test.class, Integer.class);
    }
}
