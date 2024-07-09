package com.ethan.util;

import com.ethan.annotation.Controller;
import com.ethan.annotation.RequestMapping;

import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;

public class Util {
    /**
     * 判断该方法是否带有RequestMapping注解
     * @param method 类的Class对象
     * @return
     */
    public static boolean isRequestMapping(Method method) {
        Annotation annotation = method.getAnnotation(RequestMapping.class);
        return annotation != null;
    }

    /**
     * 判断该类是否带有RequestMapping注解
     * @param clazz 类的Class对象
     * @return
     */
    public static boolean isRequestMapping(Class<?> clazz) {
        Annotation annotation = clazz.getAnnotation(RequestMapping.class);
        return annotation != null;
    }

    /**
     * 判断该类是否带有Controller注解
     * @param clazz 类的Class对象
     * @return
     */
    public static boolean isController(Class<?> clazz) {
        Annotation annotation = clazz.getAnnotation(Controller.class);
        return annotation != null;
    }

    /**
     * 获取类的注解
     * @param clazz
     * @return
     */
    public static String getRequestMapping(Class<?> clazz) {
        return clazz.getAnnotation(RequestMapping.class).value();
    }

    /**
     * 获取类的注解
     * @param method
     * @return
     */
    public static String getRequestMapping(Method method) {
        return method.getAnnotation(RequestMapping.class).value();
    }

    /**
     * 遍历指定路径下的所有文件
     * @param path  指定路径
     * @return      返回文件名的字符串列表
     */
    public static List<String> traverseFolder(String path) {

        List<String> classFiles = new ArrayList<>();
        LinkedList<File> list = new LinkedList<>();
        list.add(new File(path));
        File[] files;
        File tempFile;

        while (! list.isEmpty()) {
            tempFile = list.removeFirst();
            files = tempFile.listFiles();
            for (File file1 : files) {
                if (file1.isDirectory()) {
                    list.add(file1);
                } else {
                    classFiles.add(file1.getAbsolutePath());
                }
            }
        }
        return classFiles;
    }

    /**
     * 类的绝对路径转为类名
     * @param path          类文件的绝对路径
     * @param classPath     Main.java 所在的目录
     * @param packageName   包名
     * @return              返回全类名
     */
    public static String classPathToClassName(String path, String classPath, String packageName) {
        path = path.substring(classPath.length()-1);
        return packageName
                + "."
                + path.replaceAll(Matcher.quoteReplacement(File.separator), ".")
                .split(".class")[0];
    }



}
