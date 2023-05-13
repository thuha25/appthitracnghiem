package dutjava.tracnghiem.util.dependency_injection;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;

public class HostBuilder {
    private Host host = new HostImpl();
    private HashMap<Class<?>, Object> registerContainer = new HashMap<>();

    private List<Class<?>> clazzes;

    private HashMap<Class<?>, Class<?>> interfaceMapper = new HashMap<>();

    public HostBuilder add(Class<?> typeClass, Object key) {
        registerContainer.put(typeClass, key);
        return this;
    }

    public HostBuilder addClass(Class<?> clazz, Object key) {
        host.registerClass(key, clazz);
        clazzes.add(clazz);
        return this;
    }

    private void initialize(Object keyObject, Class<?> typeClass) {
        try {
            Class.forName(typeClass.getName());
            Constructor<?> constructor = typeClass.getDeclaredConstructor();
            Object object = constructor.newInstance();
            host.register(keyObject, object);
            System.out.println("Constructed " + typeClass.getName());
            for(Field field : typeClass.getDeclaredFields()) {
                // System.out.println(field.getName());
                field.setAccessible(true);
                if(!field.isAnnotationPresent(Inject.class))
                    continue;
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
        } catch(InvocationTargetException e) {
            System.out.println(e.getMessage()); 
            e.printStackTrace();
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
