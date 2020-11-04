package com.tkz.zookeeper;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.state.ConnectionState;
import org.apache.curator.framework.state.ConnectionStateListener;
import org.apache.curator.retry.ExponentialBackoffRetry;

/**
 * @author tkz
 */
public class Main3 {
    public static void main(String[] args) throws Exception {
        String address = "127.0.0.1:2181";

        // 重试策略，如果连接不上ZooKeeper集群，会重试三次，重试间隔会递增
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);

        // 创建Curator Client并启动，启动成功之后，就可以与Zookeeper进行交互了
        CuratorFramework client = CuratorFrameworkFactory.newClient(address, retryPolicy);
        client.start();

        client.getConnectionStateListenable().addListener(new ConnectionStateListener() {
            @Override
            public void stateChanged(CuratorFramework client, ConnectionState newState) {
                switch (newState) {
                    case CONNECTED:
                        // 第一次成功连接到ZooKeeper之后会进入该状态。
                        // 对于每个CuratorFramework对象，此状态仅出现一次
                        System.out.println("CONNECTED:");
                        break;
                    case SUSPENDED:
                        // ZooKeeper的连接丢失
                        System.out.println("SUSPENDED:");
                        break;
                    case RECONNECTED:
                        // 丢失的连接被重新建立
                        System.out.println("RECONNECTED:");
                        break;
                    case LOST:
                        // 当Curator认为会话已经过期时，则进入此状态
                        System.out.println("LOST:");
                        break;
                    case READ_ONLY:
                        // 连接进入只读模式
                        System.out.println("READ_ONLY:");
                        break;
                    default:
                        System.out.println("default");
                }
            }
        });

        System.in.read();
    }
}
