package dutjava.tracnghiem.util.database;

import java.lang.reflect.Field;

public interface ITypeMapper {
    Type getType(Field field);
    Object getValue(Type type, String value);
}