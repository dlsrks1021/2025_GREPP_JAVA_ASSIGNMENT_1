package framework;

import exception.NoSuchBoardException;
import lombok.RequiredArgsConstructor;
import org.reflections.Reflections;
import org.reflections.scanners.Scanners;
import org.reflections.util.ConfigurationBuilder;
import exception.NoSuchPostException;
import framework.annotation.Controller;
import framework.annotation.GetMapping;
import framework.annotation.RequestParam;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.net.MalformedURLException;
import java.util.*;

@RequiredArgsConstructor
public class ControllerMapper {

    private final BeanFactory beanFactory;

    public void call(Request request) throws MalformedURLException {
        try {
            MethodInfo methodInfo = getMethodInfoForUrl(request.getRequestURL());
            Method method = methodInfo.method;
            Object[] paramValues = getParamValues(method, request);
            Object bean = beanFactory.getBean(methodInfo.clazz.getName());

            method.invoke(bean, paramValues);
        } catch (NoSuchPostException | NoSuchBoardException | InvocationTargetException e){
            if (e.getCause() instanceof NoSuchPostException){
                throw (NoSuchPostException) e.getCause();
            } else if (e.getCause() instanceof NoSuchBoardException){
                throw (NoSuchBoardException) e.getCause();
            }

            throw new MalformedURLException(e.getCause().getMessage());
        } catch (Exception e) {
            throw new MalformedURLException(e.getMessage());
        }
    }

    private Object[] getParamValues(Method method, Request request) {
        Parameter[] parameters = method.getParameters();
        List<Object> paramValues = new ArrayList<>();
        for (Parameter parameter : parameters) {
            if (parameter.getType() == Session.class){
                paramValues.add(request.getSession());
                continue;
            }
            RequestParam requestParam = parameter.getAnnotation(RequestParam.class);
            String userParam = request.getRequestURL().getParams().get(requestParam.value());
            Object convertedParam = typeConvert(parameter.getType(), userParam);
            paramValues.add(convertedParam);
        }
        return paramValues.toArray();
    }

    private Object typeConvert(Class<?> clazz, String value) {
        if (clazz == Long.class || clazz == long.class) {
            try {
                return Long.parseLong(value);
            } catch (NumberFormatException e) {
                throw new NumberFormatException("파라미터 형식이 잘못되었습니다 : " + value);
            }
        }

        return value;
    }

    private MethodInfo getMethodInfoForUrl(URL url) throws MalformedURLException {
        Reflections reflections = new Reflections(new ConfigurationBuilder().forPackages(""));

        Set<Class<?>> controllerClasses = reflections.get(
                Scanners.TypesAnnotated.with(Controller.class).asClass());

        for (Class<?> controllerClass : controllerClasses) {
            Method[] methods = controllerClass.getMethods();
            for (Method method : methods) {
                GetMapping annotation = method.getAnnotation(GetMapping.class);
                if (annotation != null && annotation.value().equals(url.getTarget())) {
                    return new MethodInfo(controllerClass, method);
                }
            }
        }

        throw new MalformedURLException(url.getTarget() + " not found");
    }

    @RequiredArgsConstructor
    static class MethodInfo {
        private final Class<?> clazz;
        private final Method method;
    }
}
