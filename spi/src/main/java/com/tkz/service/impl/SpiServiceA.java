package com.tkz.service.impl;

import com.tkz.service.SpiService;

public class SpiServiceA implements SpiService {

    static {
        System.out.println("SpiServiceA 静态代码块");
    }

    public SpiServiceA() {
        System.out.println("SpiServiceA 构造方法");
    }

    @Override
    public void hello() {
        System.out.println("SpiServiceAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA.hello");
    }
}
