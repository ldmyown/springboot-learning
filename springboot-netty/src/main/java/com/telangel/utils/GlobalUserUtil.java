package com.telangel.utils;

import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

/**
 * @version 1.0.0
 * @项目名称： springboot-netty
 * @类名称： GlobalUserUtil
 * @类描述：
 * @author： lid
 * @date： 2019/3/13 16:33
 */
public class GlobalUserUtil {

    /**
     * 保存全局的  连接上服务器的客户
     */
    public static ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
}
