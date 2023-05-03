package dutjava.tracnghiem.model.utils;

import java.lang.reflect.Field;
import java.util.ArrayList;

import dutjava.tracnghiem.util.database.EntityMapper;
import dutjava.tracnghiem.util.database.Primary;
import dutjava.tracnghiem.util.database.Type;

public class Repository<TEntity, TPrimary> {

    private String tableName;
    private final Class<TEntity> EntityClass;
    private final Class<TPrimary> PrimaryClass;
    private final EntityMapper<TEntity> mapper = new EntityMapper<>();

    public Repository(Class<TEntity> EntityClass, Class<TPrimary> PrimaryClass) {
        this.EntityClass = EntityClass;
        this.PrimaryClass = PrimaryClass;
        this.tableName = this.EntityClass.getSimpleName();
        createTable();
    }

    private void createTable() {
        // String query = "CREATE TABLE IF NOT EXISTS " + this.tableName + "(";
        ArrayList<Type> schema = mapper.getTableSchema();
        String query = "CREATE TABLE IF NOT EXISTS " + this.tableName + "("
            + String.join(",", (CharSequence) schema.stream().map(e -> e.TypeName + " " + e.TypeProp)) + ")";
        DBUtils.instance.executeUpdate(query);
    }
}
