package com.aib906.freyr.core.loader;

import com.aib906.freyr.common.exception.FreyrPluginException;
import com.aib906.freyr.common.plugin.Pluginable;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author: zjs
 * @Date: 2021/2/2 11:58 上午
 */
public class PluginLoaderManager {

    private static final Map<String, PluginClassLoader> loaderMap = new ConcurrentHashMap<>();

    public Pluginable getPlugin(String identity, String jarPath, String className) throws MalformedURLException, ClassNotFoundException, IllegalAccessException, InstantiationException {
        PluginClassLoader pluginClassLoader;
        if (loaderMap.containsKey(identity)) {
            pluginClassLoader = loaderMap.get(identity);
        } else {
            pluginClassLoader = loadPath(identity, jarPath);
        }
//        PluginClassLoader pluginClassLoader =  new PluginClassLoader(new URL[]{new URL(jarPath)}, classLoader,identity);
        Class<?> aClass = pluginClassLoader.findClass(className);
        Pluginable pluginable = (Pluginable) aClass.newInstance();
        return pluginable;
    }

    public PluginClassLoader loadPath(String identity, String jarPath) {
        if (loaderMap.containsKey(identity)) {
            loaderMap.remove(identity);
        }
        try {
            PluginClassLoader pluginClassLoader;
            if (jarPath.endsWith(".jar")) {
                pluginClassLoader = new PluginClassLoader(identity, new URL[]{new URL("file:" +jarPath)});
            }else {
                File jarFiles = new File(jarPath);

                File[] jarFilesArr = jarFiles.listFiles();
                URL[] jarFilePathArr = new URL[jarFilesArr.length];
                int i = 0;
                for (File jarfile : jarFilesArr) {
                    String jarname = jarfile.getName();
                    if (jarname.indexOf(".jar") < 0) {
                        continue;
                    }
                    String jarFilePath = "file:" + jarPath + File.separator
                            + jarname;
                    jarFilePathArr[i] = new URL(jarFilePath);
                    i++;
                }

                pluginClassLoader = new PluginClassLoader("", jarFilePathArr);
            }
            loaderMap.put(identity, pluginClassLoader);
            return pluginClassLoader;
        } catch (Exception e) {
            throw new FreyrPluginException(e);
        }
    }

    public PluginClassLoader getPluginClassLoader(String identity) {
        if (loaderMap.containsKey(identity)) {
            PluginClassLoader pluginClassLoader = loaderMap.get(identity);
            return pluginClassLoader;
        }
        return null;
    }

}
