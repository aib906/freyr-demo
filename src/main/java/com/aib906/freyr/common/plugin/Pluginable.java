package com.aib906.freyr.common.plugin;

/**
 * @Author: zjs
 * @Date: 2021/1/29 11:08 上午
 */
public interface Pluginable extends BasePluginInfo{

    void init();

    void destroy();

    Object run (Object o);

}

