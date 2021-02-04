package com.aib906.freyr.core.manager.container;

import com.aib906.freyr.common.bean.PluginChain;
import com.aib906.freyr.common.exception.FreyrContainerException;
import com.aib906.freyr.common.exception.FreyrPluginException;
import com.aib906.freyr.core.container.AbstractContainer;
import com.aib906.freyr.core.container.FreyrCommonContainer;
import com.google.inject.Singleton;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author: zjs
 * @Date: 2021/2/1 7:21 下午
 */
@Component()
public class MyContainerManager extends AbstractContainerManager {

    private final ConcurrentHashMap<String, AbstractContainer> registeredContainer = new ConcurrentHashMap<>();

    public FreyrCommonContainer provideContainer(String identity, PluginChain chain) {
        if (registeredContainer.containsKey(identity)) {
            throw new FreyrContainerException("该容器已被占用");
        }
        FreyrCommonContainer commonContainer = new FreyrCommonContainer(chain);
        registeredContainer.put(identity, commonContainer);
        return commonContainer;
    }

    public FreyrCommonContainer obtainContainer(String identity) {
        if (registeredContainer.containsKey(identity)) {
            return (FreyrCommonContainer) registeredContainer.get(identity);
        }else {
            throw new FreyrContainerException("未找到该容器");
        }
    }

    public void destroy(String identity) throws Exception{
        if (registeredContainer.containsKey(identity)) {
            FreyrCommonContainer freyrCommonContainer = (FreyrCommonContainer) registeredContainer.remove(identity);
            freyrCommonContainer.destroy();
        }else {
            throw new FreyrContainerException("未找到该容器");
        }
    }

}
