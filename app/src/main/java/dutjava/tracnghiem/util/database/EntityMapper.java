package dutjava.tracnghiem.util.database;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

public class EntityMapper<T> {
    private ITypeMapper typeMapper;
    private Optional<Class<?>> classData;

    public Optional<Type> primaryField;

    public void setClass(Class<?> classData) {
        this.classData = Optional.of(classData);
    }

    public void setTypeMapper(ITypeMapper mapper) {
        this.typeMapper = mapper;
    }

    public ArrayList<Type> getTableSchema() {
        ArrayList<Type> table = new ArrayList<>();
        for (Field field : classData.get().getDeclaredFields()) {
            if(field.isAnnotationPresent(OneToOne.class)) {
                
                // DO SOME RELATIONSHIP THING HERE
                continue;
            }
            if(field.isAnnotationPresent(OneToMany.class)) {
                if(!Collection.class.isAssignableFrom(field.getType()))
                    throw new RuntimeException("@OneToMany property must be Collection<T>");
                
                // DO SOME RELATIONSHIP THING HERE
                continue;
            }
            if(field.isAnnotationPresent(ManyToMany.class)) {
                if(!Collection.class.isAssignableFrom(field.getType()))
                    throw new RuntimeException("@OneToMany property must be Collection<T>");
                // DO SOME RELATIONSHIP THING HERE
                continue;
            }
            if(field.isAnnotationPresent(ManyToOne.class)) {
                
                // DO SOME RELATIONSHIP THING HERE
                continue;
            }
            Type type = typeMapper.getType(field);
            if(type == null)
                throw new RuntimeException("Field " + field.getName() + " with types " + field.getClass().getName() + " cannot be mapped!");
            if(field.isAnnotationPresent(Primary.class))
                type.TypeProp += " PRIMARY KEY";
            if(field.isAnnotationPresent(AutoIncrement.class))
                type.TypeProp += " AUTO_INCREMENT";
            table.add(type);
            if(type.isPrimary())
                primaryField = Optional.of(type);
        }
        return table;
    }

    public T getFromRecord(ArrayList<String> records) {
        ArrayList<Type> schema = getTableSchema();
        ArrayList<Object> args = new ArrayList<>();

        for(int i = 0; i < classData.get().getDeclaredFields().length; i++)
            args.add(typeMapper.getValue(schema.get(i), records.get(i)));
        Constructor<?>[] constructors = classData.get().getDeclaredConstructors();
        for(Constructor<?> con : constructors) {
            try {
                T t = (T) con.newInstance(args.toArray());
                return t;
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public ArrayList<String> toRecord(T object) {
        ArrayList<String> record = new ArrayList<>();
        for(Field field : classData.get().getDeclaredFields())
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
