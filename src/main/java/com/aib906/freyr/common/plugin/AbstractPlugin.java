package com.aib906.freyr.common.plugin;

import lombok.Data;

/**
 * @Author: zjs
 * @Date: 2021/1/29 11:23 上午
 */
@Data
public abstract class AbstractPlugin implements Pluginable {

    private String pluginName;

    private String pluginDesc;

}