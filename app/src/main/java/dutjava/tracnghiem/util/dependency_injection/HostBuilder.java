package dutjava.tracnghiem.util.dependency_injection;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.HashMap;

public class HostBuilder {
    private Host host = new HostImpl();
    private HashMap<Class<?>, Object> registerContainer = new HashMap<>();

    private HashMap<Class<?>, Class<?>> interfaceMapper = new HashMap<>();

    public HostBuilder add(Class<?> typeClass, Object keyObject) {
        registerContainer.put(typeClass, keyObject);
        return this;
    }

    private void initialize(Object keyObject, Class<?> typeClass) {
        try {
            Class.forName(typeClass.getName());
            Constructor<?> constructor = typeClass.getDeclaredConstructor();
            constructor.setAccessible(true);
            Object object = constructor.newInstance();
            constructor.setAccessible(false);
            host.register(keyObject, object);
            System.out.println("Constructed " + typeClass.getName());
            for(Field field : typeClass.getDeclaredFields()) {
                // System.out.println(field.getName());
                if(!field.isAnnotationPresent(Inject.class))
                    continue;
                field.setAccessible(true);
                Class<?> classField = field.getType();
                if(interfaceMapper.containsKey(classField))
                    classField = interfaceMapper.get(classField);
                if(!registerContainer.containsKey(classField))
                    throw new RuntimeException("Class with name " + classField.getName() + " is not registered");
                Object fieldKey = registerContainer.get(classField);
                if(!host.isPresent(fieldKey))
                    initialize(fieldKey, field.getType());
                field.set(host.get(keyObject), host.get(fieldKey));
                field.setAccessible(false);
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    private void createInterfacesMapper(Class<?> current, Class<?> root) {
        for(Class<?> inter : current.getInterfaces()) {
            interfaceMapper.put(inter, root);
            createInterfacesMapper(inter, root);
        }
    }

    public Host build() {
        registerContainer.forEach((c, k) -> {
            createInterfacesMapper(c, c);
        });
        registerContainer.forEach((c, k) -> {
            if(host.isPresent(k))
                return;
            initialize(k, c);
        });

        return host;
    }
}
