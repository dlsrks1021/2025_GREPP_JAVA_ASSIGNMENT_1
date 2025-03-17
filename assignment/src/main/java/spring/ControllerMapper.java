package spring;

import lombok.RequiredArgsConstructor;
import org.reflections.Reflections;
import org.reflections.scanners.Scanners;
import org.reflections.util.ConfigurationBuilder;
import spring.annotation.Controller;
import spring.annotation.GetMapping;
import spring.annotation.RequestParam;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.net.MalformedURLException;
import java.util.*;

@RequiredArgsConstructor
public class ControllerMapper {

    private final BeanFactory beanFactory;

    public void call(String url) throws MalformedURLException {
        URL parsedUrl = parseUrl(url);
        MethodInfo methodInfo = getMethodInfoForUrl(parsedUrl);
        Method method = methodInfo.method;
        Object[] paramValues = getParamValues(method, parsedUrl);

        try {
            Object bean = beanFactory.getBean(methodInfo.clazz.getName());
            method.invoke(bean, paramValues);
        } catch (Exception e) {
            throw new MalformedURLException(e.getMessage());
        }
    }

    private Object[] getParamValues(Method method, URL parsedUrl) {
        Parameter[] parameters = method.getParameters();
        List<String> paramValues = new ArrayList<>();
        for (Parameter parameter : parameters) {
            RequestParam requestParam = parameter.getAnnotation(RequestParam.class);
            paramValues.add(parsedUrl.params.get(requestParam.value()));
        }
        return paramValues.toArray();
    }

    private MethodInfo getMethodInfoForUrl(URL url) throws MalformedURLException {
        Reflections reflections = new Reflections(new ConfigurationBuilder().forPackages(""));

        Set<Class<?>> controllerClasses = reflections.get(
                Scanners.TypesAnnotated.with(Controller.class).asClass());

        for (Class<?> controllerClass : controllerClasses) {
            Method[] methods = controllerClass.getMethods();
            for (Method method : methods) {
                GetMapping annotation = method.getAnnotation(GetMapping.class);
                if (annotation != null && annotation.value().equals(url.path)) {
                    return new MethodInfo(controllerClass, method);
                }
            }
        }

        throw new MalformedURLException(url.path + " not found");
    }

    private URL parseUrl(String url) throws MalformedURLException {
        Map<String, String> params = new HashMap<>();
        String path = null;

        try {
            String[] tokens = url.split("[?=]");
            for (int i = 0; i < tokens.length; i++) {
                if (i == 0) {
                    path = tokens[i];
                } else {
                    params.put(tokens[i], tokens[++i]);
                }
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new MalformedURLException(url);
        }

        return new URL(path, params);
    }

    @RequiredArgsConstructor
    static class URL {
        private final String path;
        private final Map<String, String> params;
    }

    @RequiredArgsConstructor
    static class MethodInfo {
        private final Class<?> clazz;
        private final Method method;
    }
}
