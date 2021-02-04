package com.aib906.freyr.core.container;

import com.aib906.freyr.common.bean.PluginChain;

/**
 * @Author: zjs
 * @Date: 2021/1/29 2:21 下午
 */
public abstract class AbstractContainer {

    private PluginChain pluginChain;

    public abstract void start();

    public abstract void destroy();

    public AbstractContainer(PluginChain pluginChain) {
        this.pluginChain = pluginChain;
    }

    public PluginChain getPluginChain() {
        return pluginChain;
    }
}
