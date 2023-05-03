package dutjava.tracnghiem.util.database;

public class Type {
    public String TypeName;
    public String TypeProp;
    public Class<?> origin;

    public boolean isPrimary() {
        return TypeProp.contains("PRIMARY KEY");
    }
}
