package com.aib906.freyr.core.loader;

import java.lang.reflect.Method;

/**
 * @Author: zjs
 * @Date: 2021/2/1 9:00 下午
 */
public class Startup {

    // 规定目录名
    private static final String BIN = "bin";
    private static final String LIB = "lib";

    /**
     * 获取HOME文件路径
     *
     * @param mark
     * @return
     */
    private static String homePath(String mark) {
        String path = Startup.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        if(mark!=null || "".equals(mark)){
            mark = BIN;
        }
        if (path.contains(mark)) {
            path = path.substring(0, path.lastIndexOf(mark));
        }
        return path;
    }


    /**
     * 获取启动类
     *
     * @param args
     * @return
     */
    private static String getLaunchClass(String[] args) {
        if(args!=null && args.length >= 1){
            if(args[0].contains(".")){
                return args[0].substring(0, args[0].lastIndexOf("."));
            }
        }
        return "";
    }


    /**
     * 获取启动方法
     *
     * @param args
     * @return
     */
    private static String getLaunchMethod(String[] args) {
        if(args!=null && args.length >= 1){
            if(args[0].contains(".")){
                return args[0].substring(args[0].lastIndexOf(".") + 1);
            }
        }
        return "";
    }


    /**
     * 获取启动参数
     *
     * @param args
     * @return
     */
    private static String getLaunchParam(String[] args) {
        if(args!=null && args.length >= 2){
            return args[1];
        }
        return "";
    }

    /**
     * @param args
     * @return
     */
    private static String getCommandParam(String[] args) {
        if(args!=null && args.length >= 3){
            return args[2];
        }
        return "";
    }

    /**
     * ClassLoader
     *
     * @param libHome
     */
    private static void loadLib(String libHome) {
        StartClassLoader.loadLib(libHome);
    }

    /**
     * 启动
     *
     * @param clazz
     * @param method
     * @param params
     */
    private static void startup(String clazz, String method,
                                String[] params) {
        try{
            Class<? extends Object> c = Class.forName(clazz);
            Object o = c.newInstance();
            Method m = c.getDeclaredMethod(method,new Class[]{String[].class});
            m.setAccessible(true);
            m.invoke(o, new Object[]{params});
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 启动
     *
     * @param args
     */
    private static void launch(String[] args){
        // 当前应用路径
        String homePath = homePath(BIN);
        System.out.println("homePath="+homePath);
        String launchClass = getLaunchClass(args);
        if("".equals(launchClass)){
            System.err.println("launch class is null,Please enter launch class.");
            return ;
        }
        String launchMethod = getLaunchMethod(args);
        if("".equals(launchClass)){
            System.err.println("launch method is null,Please enter launch class contains method.");
            return ;
        }
        String[] params = new String[3];
        params[0] = homePath;
        params[1] = getLaunchParam(args);
        params[2] = getCommandParam(args);
        loadLib(homePath + LIB);
        startup(launchClass,launchMethod,params);
    }


    /**
     * @param args
     */
    public static void main(String[] args) {
        launch(args);
    }

}
