package com.aib906.freyr.core.manager.plugin;

import com.aib906.freyr.common.bean.PluginChain;
import com.aib906.freyr.common.exception.FreyrPluginException;
import com.aib906.freyr.common.plugin.Pluginable;
import com.aib906.freyr.core.loader.PluginClassLoader;
import com.aib906.freyr.core.loader.PluginLoader;
import com.aib906.freyr.core.loader.PluginLoaderManager;
import com.aib906.freyr.utils.Configuration;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Component;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * @Author: zjs
 * @Date: 2021/1/29 11:56 上午
 */
@Component
public class MyPluginManager extends AbstractPluginManager {

    private PluginLoader pluginLoader;

    public PluginChain getPluginChain(Configuration configuration) throws MalformedURLException {
        //根据configuration加载plugin
        //构建对应的plugin
        //调用pluginLoader加载
        // "/Users/zhangjianshi/aib906/plugin-demo-1.0-SNAPSHOT.jar"
        String jarPath = "file:" + "/Users/zhangjianshi/aib906/plugin";
        PluginLoaderManager pluginLoaderManager = new PluginLoaderManager();
        PluginClassLoader pluginClassLoader = pluginLoaderManager.loadPath("", jarPath);
//        URL url = new URL(pathClass);
//        PluginClassLoader pluginClassLoader = new PluginClassLoader("", new URL[]{url});

        try {
            Pluginable input = pluginClassLoader.getPlugin("com.aib906.freyr.common.plugin.CommonInputPlugin");
            Pluginable filter = pluginClassLoader.getPlugin("com.aib906.freyr.common.plugin.CommonFilterPlugin");
            Pluginable output = pluginClassLoader.getPlugin("com.aib906.freyr.common.plugin.CommonOutputPlugin");
            PluginChain pluginChain;
            if (ObjectUtils.isEmpty(filter)) {
                pluginChain = PluginChain.getPluginChainForPlugins(input, output);
            }else {
                pluginChain = PluginChain.getPluginChainForPlugins(input, filter, output);
            }
            return pluginChain;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new FreyrPluginException(e);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            throw new FreyrPluginException(e);
        } catch (InstantiationException e) {
            e.printStackTrace();
            throw new FreyrPluginException(e);
        }
    }

}
