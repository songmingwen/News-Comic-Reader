package com.song.sunset.utils;

/**
 * @author songmingwen
 * @description 类的加载顺序
 * 1、有父类先加载父类静态域「静态域包含：静态代码块、静态属性」
 * 2、本类的静态域
 * 3、构造块
 * 4、构造方法
 * @since 2020/12/14
 */

class A {
    public A() {
//        System.out.println("A构造方法");
    }

    {
        System.out.println("A构造块");
    }

    public static A t1 = new A();

    static {
        System.out.println("A静态块");
    }

    public static A t2 = new A();
}

class B extends A {

    public B() {
//        System.out.println("B构造方法");
    }

    {
        System.out.println("B构造块");
    }

    public static B t1 = new B();

    static {
        System.out.println("B静态块");
    }

    public static B t2 = new B();
}

class C {
    public static void main(String[] args) {
        B t = null;
        t = new B();
    }
}
