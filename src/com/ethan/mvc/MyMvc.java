package com.ethan.mvc;

import com.ethan.Main;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

import static com.ethan.util.Util.*;

public class MyMvc {

    private static final HashMap<String, Map<String, Method>> requestMap = new HashMap<>();
    private static final HashMap<String, Object> objMap = new HashMap<>();

    static{
        String path = Objects.requireNonNull(Main.class.getResource("")).getPath();
        String packageName = Main.class.getPackage().getName();
        MyMvc.scanner(path, packageName);
    }

    public static void execution(String classPath, String methodPath) {
        Object obj = objMap.get(classPath);
        if (obj != null) {
            Method method = requestMap.get(classPath).get(methodPath);
            if (method != null) {
                try {
                    method.invoke(obj);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("没有这个方法 404");
            }
        } else {
            System.out.println("没有这个类 404");
        }
    }

    private static void scanner(String path, String packageName){
        List<String> paths = traverseFolder(path);
        for (String p : paths) {
            String className = classPathToClassName(p, path, packageName);
            try {
                Class<?> clazz = ClassLoader.getSystemClassLoader().loadClass(className);
                if (isController(clazz)) {
                    if (isRequestMapping(clazz)) {
                        String classRequest = getRequestMapping(clazz);
                        if (requestMap.containsKey(classRequest)) {
                            throw new RuntimeException("出现重复RequestMapping的类: " + classRequest);
                        } else {
                            requestMap.put( classRequest, new HashMap<>() );
                            objMap.put(classRequest, clazz.newInstance());
                        }
                        Method[] methods = clazz.getDeclaredMethods();
                        for (Method method : methods) {
                            if (isRequestMapping(method)){
                                String methodRequest = getRequestMapping(method);
                                if (requestMap.get(classRequest).containsKey(methodRequest)) {
                                    throw new RuntimeException("出现重复RequestMapping的方法: " + methodRequest);
                                } else {
                                    requestMap.get(classRequest).put(methodRequest, method);
                                }
                            }
                        }
                    } else {
                        throw new RuntimeException("缺少RequestMapping");
                    }
                }
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }


}
