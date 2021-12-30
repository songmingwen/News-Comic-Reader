package com.song.sunset.hook.utils;


import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ClassUtil {
    /**
     * 生产一个构造方法带参的实例
     *
     * @param classParam  :是参数类型：类类型
     * @param paramValues ：是参数值
     */
    public static Object getInstance(Class c, Class[] classParam, Object[] paramValues) throws Exception {
        Object obj = null;
        //调用构造方法,创建对象的Constructor对象，用他来获取构造方法的信息：即用其调用构造方法创建实例
        Constructor con;
        try {
            con = c.getConstructor(classParam);
        } catch (NoSuchMethodException e) {
            con = c.getDeclaredConstructor(classParam);
            con.setAccessible(true);
        }
        //调用构造方法并创建实例
        obj = con.newInstance(paramValues);
        return obj;
    }

    /**
     * 生产一个构造方法带参的实例
     */
    public static Object getInstance(Class c) throws Exception {
        Object obj = null;
        //调用构造方法,创建对象的Constructor对象，用他来获取构造方法的信息：即用其调用构造方法创建实例
        Constructor con;
        try {
            con = c.getConstructor(new Class[]{});
        } catch (NoSuchMethodException e) {
            con = c.getDeclaredConstructor(new Class[]{});
            con.setAccessible(true);
        }
        //调用构造方法并创建实例
        obj = con.newInstance(new Object[]{});
        return obj;
    }

    /**
     * 调用静态方法
     *
     * @param methodName      方法名
     * @param methodType      方法参数type
     * @param methodParameter 方法参数
     */
    public static Object invokeStaticMethod(Class clazz, String methodName, Class[] methodType, Object[] methodParameter) throws Exception {
        Method gf = clazz.getDeclaredMethod(methodName, methodType);
        gf.setAccessible(true);
        return gf.invoke(null, methodParameter);
    }

    /**
     * 调用静态方法
     *
     * @param methodName 方法名
     */
    public static Object invokeStaticMethod(Class clazz, String methodName) throws Exception {
        Method gf = clazz.getDeclaredMethod(methodName);
        gf.setAccessible(true);
        return gf.invoke(null);
    }

    /**
     * 调用方法
     *
     * @param obj             调用的对象
     * @param methodName      方法名
     * @param methodType      方法参数type
     * @param methodParameter 方法参数
     */
    public static Object invokeMethod(Object obj, String methodName, Class[] methodType, Object[] methodParameter) throws Exception {
        Object[] objects = new Object[1];
        invokeMethod(obj, methodName, methodType, methodParameter, objects);
        return objects[0];
    }

    /**
     * 调用方法
     *
     * @param obj        调用的对象
     * @param methodName 方法名
     */
    public static Object invokeMethod(Object obj, String methodName) throws Exception {
        Object[] objects = new Object[1];
        invokeMethod(obj, methodName, new Class[]{}, new Object[]{}, objects);
        return objects[0];
    }

    /**
     * 得到成员
     *
     * @param obj       调用的对象
     * @param fieldName 属性名
     * @return
     * @throws Exception
     */
    public static Object getFieldValue(Object obj, String fieldName) throws Exception {
        Field qMv = getField(obj, fieldName);
        qMv.setAccessible(true);
        return qMv.get(obj);
    }

    /**
     * 得到成员-链式调用
     *
     * @param obj        调用的对象
     * @param fieldNames 属性名数组
     * @return
     * @throws Exception
     */
    public static Object getFieldChainValue(Object obj, String... fieldNames) throws Exception {
        for (String fieldName : fieldNames) {
            obj = getFieldValue(obj, fieldName);
        }
        return obj;
    }

    /**
     * @param obj       调用的对象
     * @param fieldName 属性名
     * @return
     */
    public static Object getFieldValueIgnoreException(Object obj, String fieldName) {
        try {
            Field qMv = getFieldThrow(obj, fieldName);
            qMv.setAccessible(true);
            return qMv.get(obj);
        } catch (NoSuchFieldException e) {
            return null;
        } catch (IllegalAccessException e) {
            return null;
        }
    }

    /**
     * 递归查找声明的属性，一直递归到（但不包括）Object class。
     *
     * @param object    指定对象
     * @param fieldName 属性名
     * @return 属性
     */
    public static Field getFieldThrow(Object object, String fieldName) throws NoSuchFieldException {
        Field field;

        Class<?> theClass = object.getClass();
        for (; theClass != Object.class; theClass = theClass.getSuperclass()) {
            try {
                field = theClass.getDeclaredField(fieldName);
                return field;
            } catch (NoSuchFieldException e) {  //SUPPRESS CHECKSTYLE
                if (theClass.getSuperclass() == Object.class) {
                    throw e;
                }
            } catch (SecurityException e) {
                throw new IllegalArgumentException(theClass.getName() + "." + theClass, e);
            }
        }
        return null;
    }


    /**
     * @param fieldName 属性名
     * @return
     * @throws Exception
     */
    public static Object getStaticFieldValue(Class clazz, String fieldName) throws Exception {
        Field qMv = clazz.getDeclaredField(fieldName);
        qMv.setAccessible(true);
        return qMv.get(null);
    }

    /**
     * 得到成员-链式调用
     *
     * @param clazz      class
     * @param fieldNames 属性名数组
     * @return
     * @throws Exception
     */
    public static Object getStaticFieldChainValue(Class clazz, String... fieldNames) throws Exception {
        Object obj = null;
        for (int i = 0; i < fieldNames.length; i++) {
            if (i == 0) {
                getStaticFieldValue(clazz, fieldNames[i]);
            } else {
                obj = getFieldValue(obj, fieldNames[i]);
            }
        }
        return obj;
    }


    /**
     * @param obj       调用的对象
     * @param value     设置的对象
     * @param fieldName 属性名
     * @return
     * @throws Exception
     */
    public static void setFieldValue(Object obj, Object value, String fieldName) throws Exception {
        Field qMv = getField(obj, fieldName);
        qMv.setAccessible(true);
        qMv.set(obj, value);
    }


    /**
     * @param value     设置的对象
     * @param fieldName 属性名
     * @return
     * @throws Exception
     */
    public static void setStaticField(Class clazz, Object value, String fieldName) throws Exception {
        Field qMv = clazz.getDeclaredField(fieldName);
        qMv.setAccessible(true);
        qMv.set(null, value);
    }

    /**
     * 递归查找声明的属性，一直递归到（但不包括）Object class。
     *
     * @param object    指定对象
     * @param fieldName 属性名
     * @return 属性
     */
    public static Field getField(Object object, String fieldName) {
        Field field;

        Class<?> theClass = object.getClass();
        for (; theClass != Object.class; theClass = theClass.getSuperclass()) {
            try {
                field = theClass.getDeclaredField(fieldName);
                return field;
            } catch (NoSuchFieldException e) {  //SUPPRESS CHECKSTYLE

            } catch (SecurityException e) {
                throw new IllegalArgumentException(theClass.getName() + "." + theClass, e);
            }
        }

        return null;
    }

    /**
     * 反射调用（包括非public的）方法。在寻找该方法时，会一直递归到（但不包括）Object class。
     *
     * @param obj        调用该对象所在类的非public方法
     * @param methodName 方法名
     * @param paramTypes 该方法所有参数的类型
     * @param params     调用该方法需要的参数
     * @param result     如果该方法有返回值，则将返回值放入result[0]中；否则将result[0]置为null
     * @return 如果成功找到并调用该方法，返回true，否则返回false
     */
    public static boolean invokeMethod(Object obj, String methodName, Class<?>[] paramTypes, Object[] params, // SUPPRESS CHECKSTYLE
                                       Object[] result) throws NoSuchMethodException { // SUPPRESS CHECKSTYLE
        if (obj == null) {
            return false;
        }

        boolean retval = false;

        Method method = getMethod(obj, methodName, paramTypes);
        // invoke
        if (method != null) {
            method.setAccessible(true);
            try {
                final Object invocationResult = method.invoke(obj, params);
                if (result != null && result.length > 0) {
                    result[0] = invocationResult;
                }
                retval = true;
            } catch (IllegalAccessException iae) {
                throw new IllegalArgumentException(methodName, iae);
            } catch (InvocationTargetException ite) {
                throw new IllegalArgumentException(methodName, ite);
            } catch (ExceptionInInitializerError eiie) {
                throw new IllegalArgumentException(methodName, eiie);
            }
        }

        return retval;
    }

    /**
     * 递归查找声明的方法，一直递归到（但不包括）Object class。
     *
     * @param object     指定对象
     * @param methodName 方法名
     * @param paramTypes 参数类型
     * @return 指定对象的方法
     */
    public static Method getMethod(Object object, String methodName, Class<?>[] paramTypes) throws NoSuchMethodException {
        Method method;

        Class<?> theClass = object.getClass();
        for (; theClass != Object.class; theClass = theClass.getSuperclass()) {
            try {
                method = theClass.getDeclaredMethod(methodName, paramTypes);
                return method;
            } catch (NoSuchMethodException e) {  //SUPPRESS CHECKSTYLE

            } catch (SecurityException e) {
                throw new IllegalArgumentException(theClass.getName() + "." + methodName, e);
            }
        }
        throw new NoSuchMethodException("没有找到指定方法: " + methodName);
    }
}
