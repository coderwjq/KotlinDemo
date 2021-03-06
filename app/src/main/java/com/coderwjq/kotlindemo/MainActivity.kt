package com.coderwjq.kotlindemo

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class MainActivity : AppCompatActivity() {
    val TAG = javaClass.simpleName

    private val items = listOf(
            "Mon 6/23 - Sunny - 31/17",
            "Tue 6/24 - Foggy - 21/8",
            "Wed 6/25 - Cloudy - 22/17",
            "Thurs 6/26 - Rainy - 18/11",
            "Fri 6/27 - Foggy - 21/10",
            "Sat 6/28 - TRAPPED IN WEATHERSTATION - 23/18",
            "Sun 6/29 - Sunny - 20/7"
    )

    // 如果不指定函数的返回值，会返回Unit，与Java中的void类似，但是Unit是一个真正的对象
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


//        toast("不带时间参数")
//        toast("携带时间参数", Toast.LENGTH_LONG)
        niceToast(message = "Nice Toast", length = Toast.LENGTH_LONG)

        forecastList.layoutManager = LinearLayoutManager(this)

        /**
         * Kotlin中的基本类型：
         *
         * 1. Kotlin中数字类型不会自动转换，不同数字类型直接转换必须要做明确类型转换
         * 2. Char不能直接作为数字来处理
         * 3. 位运算使用 and 和 or
         * 4. 可以省略变量类型，让编译器自己去推断
         */

        /**
         * Kotlin中的变量：
         *
         * 1. var，可修改变量
         * 2. val，不可修改变量（建议尽量使用val来修饰）
         */

        /**
         * Kotlin中的属性：
         *
         * 属性会在字段上加上setter和getter方法，当然也可以修改为自定义代码，并且不修改存在的代码
         */
        val person = Person()
        person.name = "yoda"
        Log.i(TAG, person.name)

        /**
         * 扩展函数：不需要在调用方法的时候把整个对象作为参数传入
         * 可以使用this关键字和调用所有public方法
         *
         * 扩展函数也可以是一个属性，例如TextView的text属性
         */
        toast("扩展函数")

        doAsync {
            Request("http://www.baidu.com").run()

            uiThread {
                niceToast("Request performed")
            }
        }

        doAsync {
            val result = RequestForecastCommand(94043).execute()

            uiThread {
                forecastList.adapter = ForecastListAdapter(result) { niceToast(it.date) }
            }
        }

        /**
         * 一个lambda表达式通过参数的形式被定义在箭头的左边（被圆括号包围），然后在箭头的右边返回结果值
         *
         * with函数的内部实现
         *
         * inline fun <T> with(t: T, body: T.() -> Unit) { t.body() }
         *
         * 内联函数：会在编译的时候被替换掉，而不是真正的方法调用
         * 这在一些情况下可以减少内存分配和运行时开销
         */

        /**
         * Kotlin中默认的修饰符是public
         *
         * 1. private
         * 2. protected
         * 3. internal:对整个module可见
         * 4. public
         *
         * 所有构造函数默认都是public的，可以使用private constructor修饰把构造函数修改为private
         */

        /**
         * 委托属性：使用by关键词指定一个委托对象
         * 标准委托：Lazy、Observable、Vetoable（真正保存数据之前进行判断）、Not Null、从Map中映射值
         */
    }

    /**
     * backing field包括field和value，只能在属性访问器内部访问
     */
    class Person {
        var name: String = ""
            get() = field.toUpperCase()
            set(value) {
                field = "Name = $value"
            }
    }

    fun add(x: Int, y: Int): Int {
        return x + y
    }

    // 如果返回的结果可以使用一个表达式计算出来，你可以不使用括号而是使用等号
    fun add(x: Int, y: Int, z: Int): Int = x + y + z

//    fun toast(message: String, length: Int = Toast.LENGTH_SHORT) {
//        Toast.makeText(this, message, length).show()
//    }

    fun niceToast(message: String, tag: String = MainActivity::class.java.simpleName, length: Int = Toast.LENGTH_SHORT) {
        // 如果模板表达式比较复杂，可以使用一对大括号括起来：e.g Your name is ${user.name}
        Toast.makeText(this, "[$localClassName] $message", length).show()
    }

    fun Context.toast(message: String, duration: Int = Toast.LENGTH_SHORT) {
        Toast.makeText(this, message, duration).show()
    }

    object DelegatesExt {
        fun <T> notNullSingleValue() = NotNullSingleValueVar<T>()
    }

    /**
     * 自定义委托，只能初始化value一次，多次设置value抛出异常，获取value时如果为空抛出异常，如果不为空直接返回value的值
     */
    public class NotNullSingleValueVar<T>() : ReadWriteProperty<Any?, T> {
        private var value: T? = null

        override fun getValue(thisRef: Any?, property: KProperty<*>): T {
            return value ?: throw IllegalStateException("value not initialized")
        }

        override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
            this.value = if (this.value == null) value else throw IllegalStateException("value already initialized")
        }

    }

    /**
     * 集合与函数操作符
     *
     * 1. 总数操作符
     * 1.1 any 如果至少有一个元素符合给出的判断条件，则返回true
     * 1.2 all 如果全部元素符合给出的判断条件，则返回true
     * 1.3 count 返回符合判断条件元素的总数
     * 1.4 fold 在一个初始值的基础上从第一项到最后一项通过一个函数累计所有的元素。
     * 1.5 foldRight 和fold一样，但是顺序是从最后一项到第一项
     * 1.6 forEach 遍历所有元素，并执行给定的操作
     * 1.7 forEachIndexed 和forEach相同，但是可以同时得到元素的index
     * 1.8 max 返回最大一项，如果没有返回null
     * 1.9 maxBy 根据给定函数，返回最大一项，如果没有返回null
     * 1.10 min、minBy
     * 1.11 none 如果没有任何元素和给定函数匹配，则返回true
     * 1.12 reduce 和fold一样，但是没有初始值，reduceRight同foldRight
     * 1.13 sum 返回每一项通过给定函数转换后的数据的总和
     *
     * 2. 过滤操作符
     * 2.1 drop 返回去掉前n个元素的所有元素的列表
     * 2.2 dropWhile 返回去掉指定元素的列表（从第一个元素开始判断）
     * 2.3 dropLastWhild 同dropWhile，但是从最后一个元素开始判断
     * 2.4 filter 过滤所有符合给定函数条件的元素，filterNot过滤所有不符合...，filterNotNull过滤所有不是null的元素
     * 2.5 slice 过滤list中指定index的元素
     * 2.6 take 返回从第一个开始的n个元素，takeLast返回从最后一个开始的n个元素，takeWhile返回从第一个开始符合条件的元素
     *
     * 3. 映射操作符
     * 3.1 flatMap 遍历所有的元素，为每一个创建一个集合，最后把所有的集合放在一个集合中。
     * 3.2 groupBy 返回一个根据给定函数分组后的map
     * 3.3 map 返回一个每一个元素根据给定的函数转换所组成的List
     * 3.4 mapIndexed 返回一个每一个元素根据给定的包含元素index的函数转换所组成的List
     * 3.5 mapNotNull 返回一个每一个非null元素根据给定的函数转换所组成的List
     *
     * 4. 元素操作符
     * 5. 生产操作符
     * 6. 顺序操作符
     */
}
