package com.tkz.zookeeper;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.api.BackgroundCallback;
import org.apache.curator.framework.api.CuratorEvent;
import org.apache.curator.framework.api.CuratorListener;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;

/**
 * @author tkz
 */
public class Main2 {
    public static void main(String[] args) throws Exception {
        String address = "127.0.0.1:2181";

        // 重试策略，如果连接不上ZooKeeper集群，会重试三次，重试间隔会递增
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);

        // 创建Curator Client并启动，启动成功之后，就可以与Zookeeper进行交互了
        CuratorFramework client = CuratorFrameworkFactory.newClient(address, retryPolicy);
        client.start();

        client.getCuratorListenable().addListener(new CuratorListener() {
            @Override
            public void eventReceived(CuratorFramework client, CuratorEvent event) throws Exception {
                switch (event.getType()) {
                    case CREATE:
                        System.out.println("CREATE:" + event.getPath());
                        break;
                    case DELETE:
                        System.out.println("DELETE:" + event.getPath());
                        break;
                    case EXISTS:
                        System.out.println("EXISTS:" + event.getPath());
                        break;
                    case GET_DATA:
                        System.out.println("GET_DATA:" + event.getPath() + ",\t" + new String(event.getData()));
                        break;
                    case SET_DATA:
                        System.out.println("SET_DATA:" + new String(event.getData()));
                        break;
                    case CHILDREN:
                        System.out.println("CHILDREN:" + event.getPath());
                        break;
                    default:
                        System.out.println("default");
                }
            }
        });

        client.create().withMode(CreateMode.PERSISTENT)
                .inBackground().forPath("/user", "test".getBytes());
        client.checkExists().inBackground().forPath("/user");
        client.setData().inBackground().forPath("/usre", "setData-Test".getBytes());
        client.getData().inBackground().forPath("/user");

        for (int i = 0; i < 3; i++) {
            client.create().withMode(CreateMode.EPHEMERAL_SEQUENTIAL).inBackground().forPath("/user/child-");
        }

        client.getChildren().inBackground().forPath("/user");

        client.getChildren().inBackground(new BackgroundCallback() {
            @Override
            public void processResult(CuratorFramework client, CuratorEvent event) throws Exception {
                System.out.println("in background:" + event.getType() + "," + event.getPath());
            }
        }).forPath("/user");

        client.delete().deletingChildrenIfNeeded().inBackground().forPath("/user");

        System.in.read();
    }
}
