package com.song.sunset.activitys.temp

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.song.sunset.R
import java.lang.reflect.Constructor

class ReflectionActivity : AppCompatActivity() {

    companion object {

        private const val TAG = "ReflectionActivity"

        fun start(context: Context) {
            val starter = Intent(context, ReflectionActivity::class.java)
            context.startActivity(starter)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reflection)
    }

    fun loadClass(view: View) {
        val clazz = Class.forName("com.song.sunset.activitys.temp.Reflection")
        Log.e(TAG, clazz.toString())
    }

    fun loadConstructor(view: View) {
        val constructor = getClazz()?.getConstructor()
        constructor?.isAccessible = true
        val instance = constructor?.newInstance()
        Log.e(TAG, instance.toString())
    }

    fun loadParamConstructor(view: View) {
        val priConstructor = getClazz()?.getDeclaredConstructor(String::class.java, Float::class.javaPrimitiveType)
        priConstructor?.isAccessible = true
        val instance = priConstructor?.newInstance("参数", 1f)
        Log.e(TAG, instance.toString())
    }

    fun loadMethod(view: View) {
        val method = getClazz()?.getMethod("run")
        method?.isAccessible = true
        method?.invoke(getInstance())
    }

    fun loadParamMethod(view: View) {
        val method = getClazz()?.getDeclaredMethod("getPriFiled", String::class.java)
        method?.isAccessible = true
        val result = method?.invoke(getInstance(), "extra")
        Log.e(TAG, result as String?)
    }

    fun loadFiled(view: View) {
        val clazz = getClazz()
        val field = clazz?.getField("pubField")
        val instance = clazz?.newInstance()
        field?.set(instance, 7.77f)
        val value = field?.get(instance)
        val type = field?.type
        Log.e(TAG, value.toString() + "-------" + type.toString())
    }

    //----------------------------------------------------------------------------------------------

    fun getClazz(): Class<*>? {
        return Class.forName("com.song.sunset.activitys.temp.Reflection")
    }

    fun getParamConstructor(): Constructor<*>? {
        return getClazz()?.getDeclaredConstructor(String::class.java, Float::class.javaPrimitiveType)
    }

    fun getInstance(): Any? {
        val priConstructor = getParamConstructor()
        priConstructor?.isAccessible = true
        return priConstructor?.newInstance("参数", 1f)
    }
}
