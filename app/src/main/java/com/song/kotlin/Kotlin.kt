package com.song.kotlin

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

/**
 * 幕后字段 field、幕后属性 _table
 */
open class M(b: Boolean) {

    var m = 0
        set(value) {
            if (value > 0) field = value
        }
        get() {
            return if (field > 0) field else -field
        }


    private var _table: Map<String, Int>? = null
    open val table: Map<String, Int>
        get() {
            if (_table == null) {
                _table = HashMap() // 类型参数已推断出
            }
            return _table ?: throw AssertionError("Set to null by another thread")
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
    get() = size - 1

/**
 * Kotlin 使用类似 () -> Unit 的函数类型来处理函数的声明
 * (() -> Unit) 可以看作一个整体。
 * 其中 () 中可以携带参数 如 (View) -> Unit,(View,String) -> Unit
 *
 */
typealias IntTransformer = (String) -> Int

class StringToInt : IntTransformer {
    override fun invoke(x: String): Int = x.toIntOrNull() ?: -1
}

val intFunction: IntTransformer = StringToInt()

val sum: (Int, Int) -> Int = { x: Int, y: Int -> x + y }

/**
 * 匿名函数
 */
val sum1 = fun(x: Int, y: Int): Int = x + y
/**
 * 匿名函数，带有接收者的函数字面值
 * 在这样的函数字面值内部，传给调用的接收者对象成为隐式的this，
 * 以便访问接收者对象的成员而无需任何额外的限定符，亦可使用 this 表达式 访问接收者对象。
 */
val sum2 = fun Int.(other: Int): Int = this + other