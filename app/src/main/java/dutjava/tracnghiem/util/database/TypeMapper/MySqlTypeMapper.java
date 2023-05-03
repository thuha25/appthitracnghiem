package dutjava.tracnghiem.util.database.TypeMapper;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;


import dutjava.tracnghiem.util.database.ITypeMapper;
import dutjava.tracnghiem.util.database.Type;

interface TypeParser<T> {
    T parse(String s);
}

public class MySqlTypeMapper implements ITypeMapper {
    HashMap<Class<?>, String> table = new HashMap<>();
    HashMap<Class<?>, TypeParser<?>> parsers = new HashMap<>();

    public MySqlTypeMapper() {
        table.put(int.class, "INTEGER");
        table.put(Integer.class, "INTEGER");
        table.put(String.class, "TEXT");
        table.put(double.class, "DOUBLE");
        table.put(Double.class, "DOUBLE");
        table.put(float.class, "FLOAT");
        table.put(Float.class, "FLOAT");
        table.put(Date.class, "DATETIME");

        parsers.put(int.class, new TypeParser<Integer>() {
            @Override
            public Integer parse(String s) {
                return Integer.parseInt(s);
            }
        });
        parsers.put(Integer.class, new TypeParser<Integer>() {
            @Override
            public Integer parse(String s) {
                return Integer.parseInt(s);
            }
        });
        parsers.put(String.class, new TypeParser<String>() {
            @Override
            public String parse(String s) {
                return s;
            }
        });
        parsers.put(double.class, new TypeParser<Double>() {
            @Override
            public Double parse(String s) {
                return Double.parseDouble(s);
            }
        });
        parsers.put(Double.class, new TypeParser<Double>() {
            @Override
            public Double parse(String s) {
                return Double.parseDouble(s);
            }
        });
        parsers.put(float.class, new TypeParser<Float>() {
            @Override
            public Float parse(String s) {
                return Float.parseFloat(s);
            }
        });
        parsers.put(Float.class, new TypeParser<Float>() {
            @Override
            public Float parse(String s) {
                return Float.parseFloat(s);
            }
        });
        parsers.put(Date.class, new TypeParser<Date>() {
            @Override
            public Date parse(String s) {
                try {
                    return new SimpleDateFormat("YYYY-MM-DD HH:MM:SS").parse(s);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                return null;
            }
        });
    }

    @Override
    public Type getType(Field field) {
        if(!table.containsKey(field.getType()))
            throw new RuntimeException("Cannot map type " + field.getType().getName() + " to MySQL type");
        Type type = new Type();
        type.TypeName = field.getName();
        type.TypeProp = table.get(field.getType());
        type.origin = field.getType();
        return type;
    }

    @Override
    public Object getValue(Type type, String value) {
        return parsers.get(type.origin).parse(value);
    }

}
