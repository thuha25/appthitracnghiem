package dutjava.tracnghiem.util.database;

import java.lang.reflect.Field;

public class Type {
    public String TypeName;
    public String TypeProp;
    public Field origin;

    public boolean isPrimary() {
        return TypeProp.contains("PRIMARY KEY");
    }
}
