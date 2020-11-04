package com.tkz;

import com.tkz.service.SpiService;
import org.junit.Test;

import java.util.Iterator;
import java.util.ServiceLoader;

public class SpiTest {

    @Test
    public void testSpi() {

        ServiceLoader<SpiService> serviceLoader = ServiceLoader.load(SpiService.class);

        Iterator<SpiService> iterator = serviceLoader.iterator();

        // iterator.hasNext() 最终调用了 ServiceLoader.LazyIterator 的 hasNextService 方法
        while (iterator.hasNext()) {
            SpiService spiService = iterator.next();  // iterator.next() 最终调用了 ServiceLoader.LazyIterator 的 nextService 方法初始化实现类然后放到 providers LinkedHashMap<String,S> 里面
            spiService.hello();                       // 在 nextService() 方法里面有一个点要注意 Class<?> c = Class.forName(cn, false, loader); 并不会初始化该实体类
            // 注意: 还有一个点，并不是在 ServiceLoader.load(SpiService.class); 这一步就开始初始化的，也不是在 serviceLoader.iterator() 获取迭代器这一步开始初始化的，而是在 serviceLoader.iterator() 这一步
        }


        Iterator<SpiService> iterator2 = serviceLoader.iterator();
        while (iterator2.hasNext()) {
            SpiService spiService = iterator2.next(); // 如果上面已经调用过了 iterator.next() 方法,则 providers 已经存放了实例,那么这一步只会从 providers 里面直接获取,并不会重新初始化该实现类
            spiService.hello();
        }
    }

//    JDK SPI 在查找扩展实现类的过程中，需要遍历 SPI 配置文件中定义的所有实现类，该过程中会将这些实现类全部实例化。如果 SPI 配置文件中定义了多个实现类，而我们只需要使用其中一个实现类时，就会生成不必要的对象。

    @Test
    public void test2() throws Exception {
        ClassLoader cl = Thread.currentThread().getContextClassLoader();
        Class<?> c = Class.forName("com.tkz.service.impl.SpiServiceA", false, cl);
        Object o = c.newInstance();
    }

    @Test
    public void test3() throws Exception {
        ClassLoader cl = Thread.currentThread().getContextClassLoader();
        Class<?> c = Class.forName("com.tkz.service.impl.SpiServiceA", true, cl);  // true 只会执行静态代码块,不会执行构造方法,这就是为啥 DriverManager.loadInitialDrivers() 方法里面为 true 的原因
        Object o = c.newInstance();
    }

//    String[] driversList = drivers.split(":");
//    println("number of Drivers:" + driversList.length);
//        for (String aDriver : driversList) {
//        try {
//            println("DriverManager.Initialize: loading " + aDriver);
//            Class.forName(aDriver, true,
//                    ClassLoader.getSystemClassLoader());      // 为 true 的原因是为了执行 Driver 的静态代码块将 new Driver() 放入到 registeredDrivers 里面,然后在 getConnection() 获取连接的时候再取出驱动器
//        } catch (Exception ex) {
//            println("DriverManager.Initialize: load failed: " + ex);
//        }
//    }

//    public class Driver extends NonRegisteringDriver implements java.sql.Driver {
//        public Driver() throws SQLException {
//        }
//
//        static {
//            try {
//                DriverManager.registerDriver(new Driver());
//            } catch (SQLException var1) {
//                throw new RuntimeException("Can't register driver!");
//            }
//        }
//    }

//    public static synchronized void registerDriver(java.sql.Driver driver,
//                                                   DriverAction da)
//            throws SQLException {
//
//        /* Register the driver if it has not already been added to our list */
//        if(driver != null) {
//            registeredDrivers.addIfAbsent(new DriverInfo(driver, da));
//        } else {
//            // This is for compatibility with the original DriverManager
//            throw new NullPointerException();
//        }
//
//        println("registerDriver: " + driver);
//
//    }

    // List of registered JDBC drivers
//    private final static CopyOnWriteArrayList<DriverInfo> registeredDrivers = new CopyOnWriteArrayList<>();

    // // http://dubbo.apache.org/zh-cn/docs/dev/SPI.html
    // ExtensionLoader.getExtension(String name) ，拓展加载器。这是 Dubbo SPI 的核心。
}
