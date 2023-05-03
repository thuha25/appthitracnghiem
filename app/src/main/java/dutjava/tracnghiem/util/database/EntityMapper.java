package dutjava.tracnghiem.util.database;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.ArrayList;

import dutjava.tracnghiem.model.utils.Entity;
import dutjava.tracnghiem.model.utils.SqlTypeMapper;
import dutjava.tracnghiem.util.dependency_injection.Inject;

public class EntityMapper<T> {
    @Inject
    private ITypeMapper typeMapper;

    @Inject
    private Class<T> classData;

    public ArrayList<Type> getTableSchema() {
        ArrayList<Type> table = new ArrayList<>();
        for (Field field : classData.getDeclaredFields()) {
            if(field.isAnnotationPresent(Entity.class)) {
                // DO SOME RELATIONSHIP THING HERE
                continue;
            }
            String fieldName = field.getName();
            String fieldProperty = SqlTypeMapper.GetSQLType(field.getType().getName());
            if(field.isAnnotationPresent(Primary.class))
                fieldProperty += " PRIMARY KEY";
            Type type = new Type();
            type.TypeName = fieldName;
            type.TypeProp = fieldProperty;
            type.origin = field.getType();
            table.add(type);
        }
        return table;
    }

    public T getFromRecord(ArrayList<String> records) {
        ArrayList<Type> schema = getTableSchema();
        ArrayList<Object> args = new ArrayList<>();

        for(int i = 0; i < classData.getDeclaredFields().length; i++)
            args.add(typeMapper.getValue(schema.get(i), records.get(i)));
        Constructor<?>[] constructors = classData.getDeclaredConstructors();
        for(Constructor<?> con : constructors) {
            try {
                T t = (T) con.newInstance(args);
                return t;
            } catch(Exception e) {}
        }
        return null;
    }

    public ArrayList<String> toRecord(T object) {
        ArrayList<String> record = new ArrayList<>();
        for(Field field : classData.getDeclaredFields())
            try {
                field.setAccessible(true);
                record.add(field.get(object).toString());
                field.setAccessible(false);
            } catch (IllegalArgumentException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        return record;
    }
}
