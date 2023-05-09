package dutjava.tracnghiem.util.database;

import java.util.ArrayList;

import dutjava.tracnghiem.util.database.TypeMapper.MySqlTypeMapper;

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

    protected TPrimary getPrimaryValue(TEntity entity) {
        try {
            mapper.primaryField.get().origin.setAccessible(true);
            TPrimary ret = (TPrimary) mapper.primaryField.get().origin.get(entity);
            mapper.primaryField.get().origin.setAccessible(false);
            return ret;
        } catch (IllegalArgumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    protected void setPrimaryValue(TEntity entity, Integer value) {
        try {
            mapper.primaryField.get().origin.setAccessible(true);
            mapper.primaryField.get().origin.set(entity, value);
            mapper.primaryField.get().origin.setAccessible(false);
        } catch (IllegalArgumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
