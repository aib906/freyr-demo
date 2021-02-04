package com.aib906.freyr.common.bean;

import com.aib906.freyr.common.plugin.Pluginable;
import lombok.Data;

/**
 * @Author: zjs
 * @Date: 2021/1/29 1:44 下午
 */
@Data
public class PluginChain implements Runnable{

    private PluginChainNode first;

    private String name;

    public PluginChain(PluginChainNode first) {
        this.first = first;
    }

    public PluginChain(Pluginable plugin) {
        PluginChainNode pluginChainNode = new PluginChainNode(plugin);
        this.first = pluginChainNode;
    }

    public static PluginChain getPluginChainForPlugins(Pluginable input, Pluginable filter, Pluginable output) {
        PluginChainNode inputPluginNode = new PluginChainNode(input,new PluginChainNode(filter, new PluginChainNode(output)));
        return new PluginChain(inputPluginNode);
    }

    public static PluginChain getPluginChainForPlugins(Pluginable input, Pluginable output) {
        PluginChainNode inputPluginNode = new PluginChainNode(input,new PluginChainNode(output));
        return new PluginChain(inputPluginNode);
    }

    @Override
    public void run(){
        Object firstResult = first.getPlugin().run("null");
        System.out.println(firstResult);
        PluginChainNode filterNode = first.getNext();
        Object filterResult = filterNode.plugin.run(firstResult);
        System.out.println(filterResult);
        PluginChainNode outputNode = filterNode.getNext();
        Object run = outputNode.plugin.run(filterResult);
        System.out.println(run);
    }

    /**
     * unchecked
     */
    public void destroy(){
        first.getPlugin().destroy();
        PluginChainNode filter = first.getNext();
        filter.getPlugin().destroy();
        PluginChainNode out = filter.getNext();
        out.getPlugin().destroy();
    }

    @Data
    public static class PluginChainNode {

        private Pluginable plugin;

        private PluginChainNode next;

        public PluginChainNode(Pluginable plugin) {
            this.plugin = plugin;
        }

        public PluginChainNode(Pluginable plugin, PluginChainNode next) {
            this.plugin = plugin;
            this.next = next;
        }

        public boolean hasNext() {
            if (this.next == null) {
                return false;
            } else {
                return true;
            }
        }


    }
}
