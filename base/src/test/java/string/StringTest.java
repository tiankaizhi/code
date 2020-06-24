package java.string;


import org.junit.Test;

/**
 * @author tiankaizhi
 * @since 2020/04/06
 */
public class StringTest {

    @Test
    public void test1() {
        String str1 = "abcd";                      // 先检查字符串常量池中有没有 "abcd"，如果字符串常量池中没有，则创建一个，然后 str1 指向字符串常量池中的对象，如果有，则直接将 str1 指向"abcd""；
        String str2 = new String("abcd"); // 堆中创建一个新的对象
        String str3 = new String("abcd"); // 堆中创建一个新的对象
        System.out.println(str1 == str2);         // false
        System.out.println(str2 == str3);         // false
    }

    /**
     * 如果不是用双引号声明的 String 对象，可以使用 String 提供的 intern 方法。String.intern() 是一个 Native 方法。
     * 它的作用是：如果运行时常量池中已经包含一个等于此 String 对象内容的字符串，则返回常量池中该字符串的引用；
     * 如果没有，JDK1.7之前（不包含1.7）的处理方式是在常量池中创建与此 String 内容相同的字符串，并返回常量池中创建的字符串的引
     * 用，JDK1.7以及之后的处理方式是在常量池中记录此字符串的引用，并返回该引用。
     */
    @Test
    public void test2() {
        String s1 = new String("计算机");  // 1.首先在常量池中创建字符串 "计算机"，然后再在堆空间中创建字符串 "计算机"，将堆中的字符串 "计算机" 的地址赋给 s1。所以，这一步实际上是创建了两个字符串。
        String s2 = s1.intern();   // 去 ConstantPool 里面去查询发现有字符串 "计算机" 。在这里会把常量池里面的字符串 "计算机" 的地址赋给 s2，所以 s1 指向的是堆中的 "计算机"，而 s2 指向的是常量池中的 "计算机"。
        String s3 = "计算机";      // 执行这一行代码的时候发现常量池中已经有 1 创建的 "计算机" 了，所以，直接会把常量池中的 "计算机" 的地址赋给 s3，而不会在常量池中再创建一个 "计算机" 字符串，所以其实 s2 和 s3 都是指向的是常量池中的地址。
        System.out.println(s2);         // 计算机
        System.out.println(s1 == s2);   // false，因为一个是堆内存中的 String 对象一个是常量池中的 String 对象，
        System.out.println(s3 == s2);   // true，因为两个都是常量池中的 String 对象
    }

    /**
     * 字符串拼接
     * 尽量避免多个字符串拼接，因为这样会重新创建对象。如果需要改变字符串的话，可以使用 StringBuilder 或者 StringBuffer。
     */
    @Test
    public void test3() {
        String str1 = "str";
        String str2 = "ing";

        String str3 = "str" + "ing";        //常量池中的对象
        String str4 = str1 + str2;          //在堆上创建的新的对象
        String str5 = "java/string";             //常量池中的对象
        System.out.println(str3 == str4);   //false
        System.out.println(str3 == str5);   //true
        System.out.println(str4 == str5);   //false

    }

    /**
     * String s1 = new String("ab"); 这行代码一共创建了几个字符串对象？
     * <p>
     * 将创建 1 或 2 个字符串。如果池中已存在字符串常量“ab”，则只会在堆空间创建一个字符串常量“ab”。
     * 如果池中没有字符串常量“ab”，那么它将首先在池中创建，然后在堆空间中创建，因此将创建总共 2 个字符串对象。
     */
    @Test
    public void test4() {
        String s1 = new String("ab");
        String s2 = "ab";
        System.out.println(s1 == s2);  // 输出 false,因为一个是堆内存，一个是常量池的内存，故两者是不同的。但是，这里要注意一点，s2 指向的 "ab" 字符串并不是第二步创建的，而是第一步创建的。

    }

    /**
     * 注意：test5 整个过程只会创建一个对象
     */
    @Test
    public void test5() {
        String a = new String("a") + new String("b");  // 这种形式产生的结果是只会在堆中创建一个 "ab" 字符串对象，不同于 String a = new String("ab");
        a.intern();  // 发现 StringTable 里面并没有 "ab"，所以，这里会持有 "ab" 地址（其实也就是 a 的值）将其保存一份到 StringTable 中。
        String b = "ab";  // 发现 StringTable 中已经有了 "ab" 对应的地址，于是这里不会创建对象，会将 a 的值赋给 b。
        System.out.println(a == b);  // true
    }

    /**
     *
     */
    @Test
    public void test6() {
        String a = new String("a");  // 创建了两个对象，一个在堆中，一个在常量池里面。同时会将存放到常量池里面的 "a" 的地址向 StringTable 里面存入能够标识 "a" 字符串存放记录的东西及其 "a" 字符串的地址，后面如果有 "a" 字符串来了，就直接将该地址给他就行了。
        a.intern();
        String b = "a";
        System.out.println(a == b);  // false
    }

    @Test
    public void test7() {
        String h = new String("12") + new String("3");
        String h2 = h.intern();
        String h3 = "123";

        System.out.println(h == h2);   // true
        System.out.println(h2 == h3);  // true
        System.out.println(h == h3);   // true
    }

    @Test
    public void test8() {
        String h = new String("12") + new String("3");
        String h1 = new String("1") + new String("23");

        String h2 = h.intern();
        String h3 = h1.intern();

        String h4 = "123";

        System.out.println(h == h1);   // false
        System.out.println(h == h2);   // true
        System.out.println(h1 == h3);  // false  这里注意, h1 != h3, 因为 h1.intern 发现 h1 的值是 "123" 而刚刚 h 的值是 "123" 已经将 h 引用放入到 StringTable 中了, 所以, 此时发现只会把 h 的引用赋给 h3、
        System.out.println(h2 == h3);  // true
        System.out.println(h == h4);   // true
    }

}
