package reflection

import org.reflections.Reflections
import kotlin.reflect.KClass
import kotlin.reflect.KFunction
import kotlin.reflect.KParameter
import kotlin.reflect.cast

class DI
object ContainerV1 {
    // 등록한 클래스 보관 -> KClass를 보관

    private val registeredClasses = mutableSetOf<KClass<*>>()

    fun register(clazz: KClass<*>) {
        registeredClasses.add(clazz)
    }

    // T는 null이 될 수 없기 때문에 Any로 만든다.
    fun <T : Any> getInstance(type: KClass<T>): T {
        return registeredClasses.firstOrNull { clazz -> clazz == type }
            ?.let { clazz -> clazz.constructors.first().call() as T }
            ?: throw IllegalArgumentException("해당 인스턴스 타입을 찾을 수 없습니다")
    }

}

fun start(clazz: KClass<*>) {
    val reflections = Reflections(clazz.packageName) // 패캐지와 클래스를 가져온다. ex) reflection.AService
    val jClasses = reflections.getTypesAnnotatedWith(MyClass::class.java)
    jClasses.forEach { jClass -> ContainerV2.register(jClass.kotlin) }
}

private val KClass<*>.packageName: String
    get() {
        val qualifiedName = this.qualifiedName
            ?: throw IllegalArgumentException("익명 객체입니다.")
        val hierarchy = qualifiedName.split(".") // [reflection, AService] 중에 클래스 명은 제외한 패키지명만 가져온다.
        return hierarchy.subList(0, hierarchy.lastIndex).joinToString(".")
    }

object ContainerV2 {
    val registeredClasses = mutableSetOf<KClass<*>>()

    // 매번 생성자를 통해 생성되는 인스턴스를 캐싱하기 위함
    val cachedInstance = mutableMapOf<KClass<*>, Any>()

    fun register(clazz: KClass<*>) {
        registeredClasses.add(clazz)
    }

    // T는 null이 될 수 없기 때문에 Any로 만든다.
    fun <T : Any> getInstance(type: KClass<T>): T {
        if (type in cachedInstance) {
            return type.cast(cachedInstance[type])
        }
        val instance = registeredClasses.firstOrNull { clazz -> clazz == type }
            ?.let { clazz -> instantiate(clazz) as T }
            ?: throw IllegalArgumentException("해당 인스턴스 타입을 찾을 수 없습니다")

        cachedInstance[type] = instance
        return instance
    }

    private fun <T : Any> instantiate(kClass: KClass<T>): T {
        val constructor = findUsableConstructor(kClass) // 생성자의 매개변수가 모두 컨테이너에 등록된 생성자
        val params = constructor.parameters// 선별된 생성자에서 모든 파라미터를 가져온다.
            .map { param -> getInstance(param.type.classifier as KClass<*>) } // 파라미터를 하나하나 모두 인스턴스화 시킨다.
            .toTypedArray()
        return constructor.call(*params) // 인스턴스화된 파라미터들을 매개변수로 넣어서 생성자를 호출한다.
    }

    // clazz의 constructors들 중, 사용할 수 있는 constructor
    // 사용할 수 있는 constructor -> constructor에 넣어야 하는 타입들이 모두 등록된 경우 (컨테이너에서 관리하고 있는 경우)
    private fun <T : Any> findUsableConstructor(clazz: KClass<T>): KFunction<T> {
        return clazz.constructors.firstOrNull { constructor -> constructor.parameters.isAllRegistered }
            ?: throw IllegalArgumentException("사용할 수 있는 생성자가 없습니다.")
    }

    private val List<KParameter>.isAllRegistered: Boolean
        get() = this.all { it.type.classifier in registeredClasses }
}


fun main() {
    start(DI::class) // DI 클래스가 있는 패키지를 기준으로 하위 클래스까지 모두 모아서 컨테이너에 자동등록된다.
    val bService = ContainerV2.getInstance(BService::class)
    bService.print()
}

annotation class MyClass

@MyClass
class AService {
    fun print() {
        println("A Service 입니다.")
    }
}

@MyClass
class CService

@MyClass
class BService(
    private val aService: AService,
    private val cService: CService?,
) {

    constructor(aService: AService) : this(aService, null)

    fun print() {
        this.aService.print()
    }
}