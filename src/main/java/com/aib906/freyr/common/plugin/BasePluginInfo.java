package com.aib906.freyr.common.plugin;

import java.util.Map;

/**
 * @Author: zjs
 * @Date: 2021/2/1 5:01 下午
 */
public interface BasePluginInfo {

    String getPluginName();

    String getIdentity();

    String getPluginDesc();

    Map<String, String> getAttributes();
}
