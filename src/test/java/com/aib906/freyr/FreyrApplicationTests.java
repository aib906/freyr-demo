package com.aib906.freyr;

import com.aib906.freyr.common.plugin.Pluginable;
import com.aib906.freyr.core.loader.PluginLoaderManager;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.net.MalformedURLException;

@SpringBootTest
class FreyrApplicationTests {

    @Test
    void contextLoads() throws ClassNotFoundException, MalformedURLException, InstantiationException, IllegalAccessException {
        PluginLoaderManager pluginLoaderManager = new PluginLoaderManager();
        Pluginable plugin = pluginLoaderManager.getPlugin("plugin-01", "/Users/zhangjianshi/aib906/plugin", "com.aib906.freyr.common.plugin.CommonInputPlugin");
        Object run = plugin.run("");
        System.out.println(run);
    }

}
