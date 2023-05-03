package dutjava.tracnghiem.util.database;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Optional;

import dutjava.tracnghiem.util.database.TypeMapper.MySqlTypeMapper;
import dutjava.tracnghiem.util.dependency_injection.Inject;

public class Repository<TEntity, TPrimary> {

    protected String tableName;
    protected Class<?> EntityClass;
    protected Class<?> PrimaryClass;
    protected ArrayList<Type> schema;

    protected final EntityMapper<TEntity> mapper;

    public Repository(Class<?> EntityClass, Class<?> PrimaryClass) {
        this.EntityClass = EntityClass;
        this.PrimaryClass = PrimaryClass;
        mapper = new EntityMapper<>();
        mapper.setClass(EntityClass);
        mapper.setTypeMapper(new MySqlTypeMapper());
        this.tableName = this.EntityClass.getSimpleName();
        createTable();
    }

    private void createTable() {
        // String query = "CREATE TABLE IF NOT EXISTS " + this.tableName + "(";
        schema = mapper.getTableSchema();
        String query = "CREATE TABLE IF NOT EXISTS " + this.tableName + "("
            + String.join(",", schema.stream().map(e -> e.TypeName + " " + e.TypeProp).toList()) + ")";
        DBUtils.instance.executeUpdate(query);
    }
}
