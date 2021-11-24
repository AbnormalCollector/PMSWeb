package com.sy.util;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
/**
 * 通过反射可以直接访问普通类，普通类可以间接的变成Servlet类，但是要配合RequestMapping注解使用
 * */
@WebServlet("*.do")
public class DispathCherServlet extends HttpServlet {

    @Override
    public void service(HttpServletRequest req, HttpServletResponse response) throws ServletException, IOException {
        String controllerPath = this.findControllerPath();
        String methodPath = this.findMethodPath();
        List<Class> controllerClass = this.findControllerClass();
        Class targetClass = this.findTargetClass(controllerPath, controllerClass);
        Method targetMethod = this.findTargetMethod(methodPath, targetClass);
        //2.获取自定义Controller.判断Contrller中是否有RequestMapping,并判断value值，如/user
        //还需进一步判断对方法是否有RequestMapping，并判断value值，如/add.do
        //3.创建该Controller类对像,并调用其方法
        try {
            if(targetClass == null || targetMethod == null){
                response.setStatus(404);
                response.getWriter().write("404:资源找不到！");
                return;
            }
            Object object = targetClass.newInstance();
            Object invoke = targetMethod.invoke(object,null);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    //找到controllerPath所映射的Controller类
    public Class findTargetClass(String path,List<Class> controllerClass){
        for (Class controller : controllerClass){
            RequestMapping annotation = (RequestMapping)controller.getAnnotation(RequestMapping.class);
            String value = annotation.value();
            if(path.equalsIgnoreCase(value)){
                return controller;
            }
        }
        return null;
    }
    //找到Controller类中的对应方法
    public Method findTargetMethod(String path, Class targetClass){
        Method[] methods = targetClass.getMethods();
        for (Method method : methods){
            if(method.isAnnotationPresent(RequestMapping.class)){
                RequestMapping annotation = method.getAnnotation(RequestMapping.class);
                String value = annotation.value();
                if(path.equalsIgnoreCase(value)){
                    return method;
                }
            }
        }
        return null;
    }

    private String findControllerPath(){
        HttpServletRequest request = RequestContextUtil.getRequest();
        String requestURI = request.getRequestURI();
        String[] paths = requestURI.split("/");
        String path = paths[paths.length - 2];
        return "/"+path;
    }

    private String findMethodPath(){
        HttpServletRequest request = RequestContextUtil.getRequest();
        String requestURI = request.getRequestURI();
        String[] paths = requestURI.split("/");
        String path = paths[paths.length - 1];
        return "/" + path;
    }

    private List<Class> findControllerClass(){
        //找到classes目录
        URL url = this.getClass().getResource("/");
        String path = url.getPath();
        File file = new File(path);
        List<Class> targetClass = new ArrayList<>();
        this.findAllClass(file,targetClass);
        List<Class> controllerClass = this.findControllerClass(targetClass);
        return controllerClass;
    }

    private List<Class> findControllerClass(List<Class> targetClass){
        List<Class> list = new ArrayList<>();
        for (Class clazz : targetClass){
            boolean annotationPresent = clazz.isAnnotationPresent(RequestMapping.class);
            if(annotationPresent){
                list.add(clazz);
            }
        }
        return list;
    }


    private void findAllClass(File file, List<Class> targetClass){
        File[] files = file.listFiles();
        for (File f:files){
            if(f.isFile()){
                //把文件转化为字节码
                Class tagert = this.convertFileToClass(f);
                if(tagert != null){
                    targetClass.add(tagert);
                }
            }else {
                this.findAllClass(f,targetClass);
            }
        }
    }


    private Class convertFileToClass(File file){
        String path = file.getPath();
        if(path.endsWith(".class")){
            String classPath = path.split("classes")[1];
            String substring = classPath.substring(1,classPath.length() - 6);
            //完全限定名
            String referPath = substring.replaceAll("\\\\",".");
            try {
                Class clazz = Class.forName(referPath);
                return clazz;
            }catch (Exception e){
                e.printStackTrace();
            }

        }
        return null;
    }
}
