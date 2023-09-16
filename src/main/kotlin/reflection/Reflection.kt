package reflection

import kotlin.reflect.KClass
import kotlin.reflect.KFunction
import kotlin.reflect.cast
import kotlin.reflect.full.createInstance
import kotlin.reflect.full.createType
import kotlin.reflect.full.hasAnnotation

fun main() {
//    executeAll(Reflection())
//    val kClass: KClass<Reflection> = Reflection::class
//
//    val ref = Reflection()
//    val kClass2: KClass<out Reflection> = ref::class
//
//    val kClass3: KClass<out Any> = Class.forName("reflection.Reflection").kotlin

//    val goldFish = GoldFish("금붕어")
//    goldFish::class.members.first { it.name == "print" }.call(goldFish)

    executeAll(Reflection())

}

@Target(AnnotationTarget.CLASS)
annotation class Executable

@Executable
class Reflection {
    fun a() {
        println("A입니다.")
    }
    fun b(n: Int) {
        println("B입니다.")
    }
}

fun executeAll(obj: Any) {
    val kClass: KClass<out Any> = obj::class
    if (!kClass.hasAnnotation<Executable>()) {
        return
    }

    val callableFunctions = kClass.members.filterIsInstance<KFunction<*>>()
        .filter { it.returnType == Unit::class.createType() }
        .filter { it.parameters.size == 1 && it.parameters[0].type == kClass.createType() }

    callableFunctions.forEach { function ->
//        function.call(kClass.createInstance()) // 파라미터가 없는 생성자(기본 생성자)를 찾아서 생성한다.
        function.call(obj)
    }
}

class GoldFish(val name: String) {
    fun print() {
        println("금붕어 이름은 ${name}입니다.")
    }
}

fun castToGoldFish(obj: Any): GoldFish {
    return GoldFish::class.cast(obj)
}

