package spring;

import org.reflections.Reflections;
import org.reflections.scanners.Scanners;
import org.reflections.util.ConfigurationBuilder;
import spring.annotation.ComponentScan;

import java.lang.reflect.Constructor;
import java.util.*;

public class BeanFactory {

    private final Map<String, Object> beanMap;

    private final Set<Class<?>> componentClasses;

    public BeanFactory() {
        beanMap = new HashMap<>();
        Reflections reflections = new Reflections(new ConfigurationBuilder().forPackages(""));
        componentClasses = reflections.get(
                Scanners.TypesAnnotated.with(ComponentScan.class).asClass());
        for (Class<?> componentClass : componentClasses) {
            if (componentClass.isAnnotation()){
                continue;
            }
            createBean(componentClass.getName());
        }
    }

    public Object getBean(String beanName) {
        return beanMap.get(beanName);
    }

    private Object createBean(String name) {
        if (beanMap.containsKey(name)) {
            return beanMap.get(name);
        }

        for (Class<?> componentClass : componentClasses) {
            if (componentClass.getName().equals(name)) {
                try {
                    Constructor<?> constructor = componentClass.getDeclaredConstructors()[0];
                    Class<?>[] parameterTypes = constructor.getParameterTypes();
                    List<Object> beanParams = new ArrayList<>();
                    for (Class<?> parameterType : parameterTypes) {
                        Object bean = beanMap.get(parameterType.getName());
                        if (bean == null) {
                            bean = createBean(parameterType.getName());
                        }
                        beanParams.add(bean);
                    }

                    Object newBean = constructor.newInstance(beanParams.toArray());
                    beanMap.put(componentClass.getName(), newBean);
                    return newBean;
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }

        throw new RuntimeException("Could not create bean : " + name);
    }
}
