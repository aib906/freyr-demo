package com.aib906.freyr.controller;

import com.aib906.freyr.common.bean.PluginChain;
import com.aib906.freyr.common.bean.RestResult;
import com.aib906.freyr.common.plugin.Pluginable;
import com.aib906.freyr.core.container.FreyrCommonContainer;
import com.aib906.freyr.core.loader.PluginClassLoader;
import com.aib906.freyr.core.loader.PluginLoaderManager;
import com.aib906.freyr.core.manager.container.MyContainerManager;
import com.aib906.freyr.core.manager.plugin.MyPluginManager;
import com.aib906.freyr.core.manager.plugin.PluginManager;
import com.aib906.freyr.utils.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.MalformedURLException;
import java.net.URLClassLoader;

/**
 * @Author: zjs
 * @Date: 2021/1/29 11:37 上午
 */
@RestController
public class FreyrCommonController {

    @Autowired
    private MyContainerManager containerManager;

    @GetMapping("/")
    public RestResult testBegin(){
        /**
         * 创建流程:
         * 包含input/filter/output 三个plugin
         * 一系列的流程应该绑定在一起传递
         * 请求pluginManager获取需要的plugin链
         * 获取到初始化好的plugin链后交给容器定时执行
         */
        try {
            MyPluginManager pluginManager = new MyPluginManager();
            Configuration configuration = new Configuration();
            //todo: configuration赋值
            PluginChain pluginChain = pluginManager.getPluginChain(configuration);
            FreyrCommonContainer freyrCommonContainer = containerManager.provideContainer("identity-01", pluginChain);
            freyrCommonContainer.start();
        } catch (Exception e) {
            e.printStackTrace();
            return RestResult.error(e.getMessage());
        }
        return RestResult.success();
    }

    @GetMapping("/end")
    public RestResult testEnd(){
        try {

//            FreyrCommonContainer freyrCommonContainer = containerManager.obtainContainer("identity-01");
//            freyrCommonContainer.destroy();
            containerManager.destroy("identity-01");
            return RestResult.success();
        } catch (Exception e) {
            e.printStackTrace();
            return RestResult.error(e.getMessage());
        }

    }

    public RestResult testClassLoad() throws ClassNotFoundException {
        Class<?> aClass = Class.forName("com.aib906.freyr.common.plugin.AbstractPlugin");
        return RestResult.success();
    }

    public static void main(String[] args) throws ClassNotFoundException, MalformedURLException, InstantiationException, IllegalAccessException {

        PluginLoaderManager pluginLoaderManager = new PluginLoaderManager();
//        Pluginable abc = pluginLoaderManager.getPlugin("abc", "file:" + "/Users/zhangjianshi/aib906/plugin-demo-1.0-SNAPSHOT.jar", "com.aib906.freyr.common.plugin.CommonFilterPlugin", (URLClassLoader) Thread.currentThread().getContextClassLoader());
        PluginClassLoader pluginClassLoader = pluginLoaderManager.loadPath("plugin",  "/Users/zhangjianshi/aib906/plugin");
        Pluginable plugin = pluginClassLoader.getPlugin("com.aib906.freyr.common.plugin.CommonInputPlugin");
        Object abc1 = plugin.run("abc");
        System.out.println(abc1);

    }

    @GetMapping("/test")
    public RestResult test(){
        try {
            MyPluginManager pluginManager = new MyPluginManager();
            PluginChain pluginChain = pluginManager.getPluginChain(new Configuration());
            PluginChain.PluginChainNode first = pluginChain.getFirst();
            Pluginable plugin = first.getPlugin();
            Object run = plugin.run("1");
            System.out.println(run);
            return RestResult.success(run);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return RestResult.error("");
    }

}