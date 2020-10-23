package com.tkz.service.impl;

import com.tkz.service.SpiService;

public class SpiServiceB implements SpiService {

    static {
        System.out.println("SpiServiceB 静态代码块");
    }

    public SpiServiceB() {
        System.out.println("SpiServiceB 构造方法");
    }

    @Override
    public void hello() {
        System.out.println("SpiServiceBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB.hello");
    }
}
