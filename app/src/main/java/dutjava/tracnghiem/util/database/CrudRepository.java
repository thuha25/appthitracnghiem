package dutjava.tracnghiem.util.database;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
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

    private int next_increament_id() {
        File f = new File("./" + EntityClass.getSimpleName());
        if(!f.exists() || f.isDirectory()) {
            try {
                f.createNewFile();
                FileOutputStream fou = new FileOutputStream(f);
                fou.write(0);
                fou.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return 0;
        }
        int id = 0;
        try(FileInputStream fin = new FileInputStream(f)) {
            id = fin.read() + 1;
            FileOutputStream fou = new FileOutputStream(f);
            fou.write(id);
            fou.close();
        } catch(IOException e) {
            e.printStackTrace();
        }
        return id;
    }

    public TEntity save(TEntity entity) {
        Optional<TEntity> oentity = findById(getPrimaryValue(entity));
        if(oentity.isEmpty()) {
            ArrayList<String> record = mapper.toRecord(entity);
            if(PrimaryClass == int.class || PrimaryClass == Integer.class) {
                int id = next_increament_id();
                record.set(0, String.valueOf(id));
                setPrimaryValue(entity, id);
            } else {
                throw new RuntimeException("@Primary field must be Integer (for now)");
            }
            customQuery("INSERT INTO " + tableName + " VALUES (" +
            String.join(",", record.stream().map(e -> "'" + e + "'").toList())    
            + ")");
            return entity;
        }
        // Perform update

        return oentity.get();
    }
    public void delete(TEntity entity) {
        deleteById(getPrimaryValue(entity));
    }
    public void deleteById(TPrimary id) {
        customQuery("DELETE FROM " + tableName + " WHERE " + mapper.primaryField.get().TypeName + "=" + id);
    }
    protected Collection<TEntity> customQuery(String query) {
        if(!DBUtils.instance.testQuery(query)) {
            // DBUtils.instance.executeUpdate(query);
            return null;
        }
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
