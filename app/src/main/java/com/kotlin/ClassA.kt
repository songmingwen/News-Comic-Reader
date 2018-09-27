package com.kotlin

import android.content.Context
import android.util.AttributeSet
import android.view.View

/**
 * @author songmingwen
 * @since 2018/9/3
 */
open class ClassA(sex: Int) {
    init {
        println("" + sex)
    }

    open fun funA() {}

    fun funB() {}
}

open class ClassB private constructor() {
    init {
        println("")
    }
}

open class ClassC : ClassA(1) {
    init {
        println("")
    }

    override fun funA() {}
}

class ClassD : ClassC() {
    final override fun funA() {}
}

class ViewA : View {
    constructor(ctx: Context) : super(ctx)
    constructor(ctx: Context, attrs: AttributeSet) : super(ctx, attrs)
}

interface Foo {
    val count: Int
}

class FooA(override val count: Int) : Foo

class FooB : Foo {
    override val count: Int = 0
}

class FooC : Foo {
    override val count: Int
        get() = 0
}

open class SuperA {
    open fun funX() {
        println("A")
    }
}

interface SuperB {
    open fun funX() {
        println("B")
    }
}

open class SuperC {
    open fun funX() {
        println("C")
    }
}

class SuperD : SuperA(), SuperB {
    override fun funX() {
        super<SuperA>.funX()
        super<SuperB>.funX()
    }
}

class M(b: Boolean) {
    var m = 0
        set(value) {
            if (value > 0) field = value
        }
}

/**
 * 扩展函数
 */
fun Context.dp2px(dp: Int): Int {
    val scale = this.resources.displayMetrics.density
    return (dp * scale + 0.5f).toInt()
}

/**
 * 扩展属性
 */
val <E> List<E>.lastIndex: Int
    get()= size -1