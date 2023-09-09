package delegate

import kotlin.properties.Delegates.vetoable
import kotlin.properties.PropertyDelegateProvider
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

class Person3 {

    val name: String by LazyInitProperty {
        Thread.sleep(2000)
        "유녕진"
    }

    var age: Int by vetoable(20) {
            _, _, newValue,
        ->
        newValue >= 1
    }

}

fun main() {
    val p = Person3()
    p.age = -10
    println(p.age)
}

class LazyInitProperty<T>(val init: () -> T) : ReadOnlyProperty<Any, T> {
    private var _value: T? = null // backing property
    private val value: T
        get() {
            if (_value == null) {
                this._value = init()
            }
            return this._value!!
        }


    override fun getValue(thisRef: Any, property: KProperty<*>): T {
        TODO("Not yet implemented")
    }
}

class Person5 {
    val name by DelegateProvider("유녕진") // 정상동작
    val country by DelegateProvider("한국") // 정상동작 X
}

class DelegateProvider(
    private val initValue: String,
) : PropertyDelegateProvider<Any, DelegateProperty> {
    override fun provideDelegate(thisRef: Any, property: KProperty<*>): DelegateProperty {
        if (property.name != "name") {
            throw IllegalArgumentException("name만 연결 가능합니다.")
        }
        return DelegateProperty(initValue)
    }
}

class DelegateProperty(
    private val initValue: String,
) : ReadOnlyProperty<Any, String> {
    override fun getValue(thisRef: Any, property: KProperty<*>): String {
        return initValue
    }
}

interface Fruit {
    val name: String
    val color: String
    fun bite()
}

class Apple : Fruit {
    override val name: String
        get() = "사과"
    override val color: String
        get() = "빨간색"

    override fun bite() {
        println("...")
    }
}

class GreenApple(
    private val apple: Apple,
) : Fruit by apple {
    override val color: String
        get() = "초록색"
}
