package dutjava.tracnghiem.util.database;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

public class CrudRepository<TEntity, TPrimary> extends Repository<TEntity, TPrimary> {
    public CrudRepository(Class<TEntity> EntityClass, Class<TPrimary> PrimaryClass) {
        super(EntityClass, PrimaryClass);
    }

    public Collection<TEntity> findAll() {
        return customQuery("SELECT * FROM " + tableName);
    }
    public Optional<TEntity> findById(TPrimary id) {
        ArrayList<TEntity> entities = (ArrayList<TEntity>) customQuery("SELECT * FROM " + tableName + " WHERE " + mapper.primaryField.get().TypeName + "=" + id);
        if(entities.size() < 1)
            return Optional.empty();
        return Optional.of(entities.get(0));
    }
    public void save(TEntity entity) {

    }
    public void delete(TEntity entity) {

    }
    public void deleteById(TPrimary id) {

    }
    private Collection<TEntity> customQuery(String query) {
        if(!DBUtils.instance.testQuery(query))
            DBUtils.instance.executeUpdate(query);
        ResultSet result = DBUtils.instance.executeQuery(query);
        if(result == null)
            return null;
        try {
            result.beforeFirst();
            Collection<TEntity> entities = new ArrayList<>();
            while(result.next()) {
                ArrayList<String> record = new ArrayList<>();
                ResultSetMetaData meta = result.getMetaData();
                for(int i = 0; i < meta.getColumnCount(); i++) {
                    // System.out.println(result.getString(schema.get(i).TypeName));
                    record.add(result.getString(schema.get(i).TypeName));
                }
                Optional<TEntity> entity = Optional.empty();
                try {
                    TEntity _entity = mapper.getFromRecord(record);
                    entity = Optional.of(_entity);
                } catch(Exception e) {
                    e.printStackTrace();
                }
                if(entity.isPresent())
                    entities.add(entity.get());
            }
            return entities;
        } catch(SQLException e) {
            System.out.println(e.getSQLState());
            e.printStackTrace();
        } finally {
            try {
                if(result != null)
                    result.close();
            } catch (SQLException e) {
                System.out.println(e.getSQLState());
                e.printStackTrace();
            }
        }
        return null;
    }
}
