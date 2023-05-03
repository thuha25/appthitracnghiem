package dutjava.tracnghiem.model.utils;

import java.util.Hashtable;

public class SqlTypeMapper {
    private static Hashtable<String, String> map = new Hashtable<>() {
        {
            put("int", "INTEGER");
            put("java.lang.Integer", "INTEGER");
            put("java.lang.String", "TEXT");
            put("double", "DOUBLE");
            put("java.lang.Double", "DOUBLE");
            put("float", "FLOAT");
            put("java.lang.Float", "FLOAT");
            put("java.util.Date", "DATETIME");
        }
    };

    public static String GetSQLType(String typename) {
        return map.get(typename);
    }
}
